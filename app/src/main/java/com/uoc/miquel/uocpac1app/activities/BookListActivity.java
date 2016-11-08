package com.uoc.miquel.uocpac1app.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.adapters.RecyclerAdapter;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.model.BookContent;


import java.util.ArrayList;
import java.util.List;


/*
 * Gestiona l'activiy de la llista de Books.
 */
public class BookListActivity extends AppCompatActivity {

    public static final String TAG = "-----PAC2-----";
    public static final String FIREBASE = "FIREBASE";
    public static final String SUGAR = "SUGAR";
    private boolean twoFragments = false;
    private boolean isAuth = false;
    private boolean isNewData = false;

    //Variables RecyclerView
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeContainer;

    //Variables Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mBookReference;
    private DataSnapshot newData;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        twoFragments = findViewById(R.id.frag_book_detail) != null;
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id. swipeContainer);
        mAdapter = new RecyclerAdapter(this, twoFragments);


        //Inicialitzem les variables d'autenticacio i base de dades Firebase.
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mBookReference = mDatabase.getReference("books");
        mBookReference.addValueEventListener(getValueEventListener());



        //Autentiquem l'aplicacio a la base de dades i definim la variable isAuth
        signInFirebaseDatabase();

        //S'afegeix el listener del SwipeRefreshLayout per tal que es pugui actualitzar la llista.
        swipeContainer .setOnRefreshListener(getSwipeContainerListener(activeNetwork));

        //Inflem el layout de l'activity.
        setUI();
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////


    //Genera la vista de l'activity a partir dels llibres guardats localment.
    private void setUI() {

        mAdapter.setItems(BookContent.getBooks());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //TODO: Provar si aixo es pot treure...
        //Es comprova si tenim el fragment present en pantalla.
        if (twoFragments) {
            BookDetailFragment bookDetailFrag = new BookDetailFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag_book_detail, bookDetailFrag)
                    .commit();
        }
    }


    private boolean isConnected(NetworkInfo activeNetwork) {
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }




    //Retorna un listener ValueEventListener
    @NonNull
    private ValueEventListener getValueEventListener() {
        return new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //updateBooks(dataSnapshot);
                newData = dataSnapshot;
                isNewData = true;

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(BookListActivity.this, "No s'ha pogut accedir a la base de dades Firebase.",
                        Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void updateBooks(DataSnapshot dataSnapshot) {
        boolean newData = false;
        GenericTypeIndicator<ArrayList<BookContent.BookItem>> t =
                new GenericTypeIndicator<ArrayList<BookContent.BookItem>>() {};
        ArrayList<BookContent.BookItem> fbBooks = dataSnapshot.getValue(t);
        if (fbBooks != null) {
            for (BookContent.BookItem book : fbBooks) {
                if (!BookContent.exists(book)) {
                    book.save(); //TODO: Si hi ha algun canvi en algun llibre no s'esta guardant.
                    newData = true;
                }
            }
            setUI();
            if (newData) {
                Toast.makeText(BookListActivity.this, "S'ha trobat i afegit nous llibres.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signInFirebaseDatabase() {

        mAuth.signInWithEmailAndPassword("miqasals@gmail.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            isAuth = true;
                            Toast.makeText(BookListActivity.this, "Autenticació a Firebase completada correctament",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookListActivity.this, "No s'ha pogut autenticar a Firebase",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }




    @NonNull
    private SwipeRefreshLayout.OnRefreshListener getSwipeContainerListener(final NetworkInfo activeNetwork) {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNewData){
                    if (isConnected(activeNetwork) && isAuth){

                        updateBooks(newData);
                        if (swipeContainer.isRefreshing()) {
                            swipeContainer.setRefreshing(false);
                        }
                        isNewData = false;

                        setUI();
                    } else {
                        if (swipeContainer.isRefreshing()) {
                            swipeContainer.setRefreshing(false);
                        }
                        Toast.makeText(BookListActivity.this, "No es pot connectar a Firebase", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (swipeContainer.isRefreshing()) {
                        swipeContainer.setRefreshing(false);
                    }
                    Toast.makeText(BookListActivity.this, "La llista de llibres ja esta actualitzada", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }
}
