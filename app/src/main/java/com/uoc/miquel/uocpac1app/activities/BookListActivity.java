package com.uoc.miquel.uocpac1app.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.adapters.RecyclerAdapter;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.model.BookContent;


/*
 * Gestiona l'activiy de la llista de Books.
 */
public class BookListActivity extends AppCompatActivity {

    private static final String TAG = "BookListActivity . . .";
    private boolean twoFragments = false;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Inicialitzem les variables corresponents a Firebase.
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        twoFragments = findViewById(R.id.frag_book_detail) != null;

        //Inicialitzem les variables d'autenticacio i base de dades Firebase.
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase. getInstance();

        //Autentiquem l'aplicacio a la base de dades.
        mAuth.signInWithEmailAndPassword("miqasals@gmail.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(BookListActivity.this, "Autenticació fallida",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookListActivity.this, "Autenticació completada",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });





        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(this, BookContent.ITEMS, twoFragments);
        mRecyclerView.setAdapter(mAdapter);

        //Inicialitzem les variables d'autenticacio i base de dades Firebase.
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase. getInstance();

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
