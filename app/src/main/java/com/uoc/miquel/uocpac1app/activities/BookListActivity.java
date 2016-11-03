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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private FirebaseDatabase mDatabase;
    private DatabaseReference mBookReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        twoFragments = findViewById(R.id.frag_book_detail) != null;

        //Inicialitzem les variables d'autenticacio i base de dades Firebase.
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mBookReference = mDatabase.getReference().child("books");
        /*
         * Obtenim una referencia a la base de dades a nivell de Book per tal que quan hi hagi un
         * canvi en un element ens passara l'objecte complet a la variable snapshot i no la llista
         * completa d'elements (books) de la base de dades firebase.
         */


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

         mBookReference.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //S'obte l'objecte BookItem del snapshot
                BookContent.BookItem book = dataSnapshot.getValue(BookContent.BookItem.class);
                //En cas que no existeixi el guardem a la base de dades local.
                if (!BookContent.exists(book)) {
                    book.save();
                //En cas que existeixi, comprovem si hi ha algun canvi en les dades
                } else {
                    //Obtenim el book guardat a la base de dades local
                    BookContent.BookItem storedBook = BookContent.BookItem.findById(
                            BookContent.BookItem.class,book.getIdentificador());
                    //El comparem amb el rebut i, si no es exacte es copia i es torna a guardar.
                    if (!book.equals(storedBook)){
                        //TODO: Crear un mètode que copii els objectes.
                        storedBook = book;
                        storedBook.save();
                    }


                    /*
                     * TODO: Revisar la comprovació de si el book existeix ja que el findById comprova el id
                     * i els guardats a firebase no tenen aquest id. Provablement el metode exists ha de
                     * comprovar un a un tots els elements comprovant la propietat identificador.
                     */
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });



        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Canviem l'origen de dades de BookContent.ITEMS a BookContent.getBooks().
        mAdapter = new RecyclerAdapter(this, BookContent.getBooks(), twoFragments);
        mRecyclerView.setAdapter(mAdapter);


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
