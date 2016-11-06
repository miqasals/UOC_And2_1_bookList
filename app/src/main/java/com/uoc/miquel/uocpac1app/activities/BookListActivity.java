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
    private boolean twoFragments = false;
    private boolean isConnected = false;
    private boolean isAuthenticated = false;
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeContainer;

    //Inicialitzem les variables corresponents a Firebase.
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mBookReference;

    ValueEventListener valueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        //https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        ////////////////////////////////////////////////////////////////////// ----¿Necessari?

        twoFragments = findViewById(R.id.frag_book_detail) != null;
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id. swipeContainer);


        //Es crea el listener ValueEventListener que s'afegirà a la DatabaseReference.
        valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<ArrayList<BookContent.BookItem>> t =
                        new GenericTypeIndicator<ArrayList<BookContent.BookItem>>() {};
                ArrayList<BookContent.BookItem> fbBooks = dataSnapshot.getValue(t);
                if (fbBooks != null) {
                    for (BookContent.BookItem book : fbBooks) {
                        if (!BookContent.exists(book)) {
                            book.save(); //TODO: Si hi ha algun canvi en algun llibre no s'esta guardant.
                        }
                        Log.i(TAG,book.toString());
                    }
                    setUI(fbBooks);
                    Toast.makeText(BookListActivity.this, "Llista de llibres obtinguda de Firebase",
                            Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                setUI(BookContent.getBooks());
            }
        };


        mAdapter = new RecyclerAdapter(this, BookContent.getBooks(), twoFragments);

        //Inicialitzem les variables d'autenticacio i base de dades Firebase.
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mBookReference = mDatabase.getReference("books");

        //Autentiquem l'aplicacio a la base de dades
        mAuth.signInWithEmailAndPassword("miqasals@gmail.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful() && isConnected){
                            Log.i(TAG,"CONECTAT I AUTENTICAT");

                            mBookReference.addValueEventListener(valueEventListener);
                        } else {
                            //bookItemList = BookContent.getBooks();
                            setUI(BookContent.getBooks());
                            Toast.makeText(BookListActivity.this, "Llista de llibres obtinguda de SugarORM db",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        swipeContainer .setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


            }
        });
    }



////////////////////////////////////////////////////////////////////////////////////////////////////



    private void setUI(List<BookContent.BookItem> books) {

        mAdapter.setItems(books);

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
}
