package com.uoc.miquel.uocpac1app.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
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
import com.uoc.miquel.uocpac1app.model.CommonConstants;


import java.util.ArrayList;


/*
 * Gestiona l'activiy de la llista de Books.
 */
public class BookListActivity extends AppCompatActivity {

    private boolean twoFragments = false;   //Indica si hi ha el fragment de detalls.
    private boolean isAuth = false;         //Indica si l'autenticació a Firebase ha estat correcta.
    private boolean isNewData = false;      //Indica si s'ha rebut noves dades de Firebase.

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

    private NetworkInfo activeNetwork;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        
        /*
        if (getIntent() != null && getIntent().getAction() != null) {
            if (getIntent().getAction().equals(CommonConstants.ACTION_DETAIL)
                    && getIntent().hasExtra(CommonConstants.POSITION_KEY)) {
                int receivedPosition = getIntent().getIntExtra(CommonConstants.POSITION_KEY, -1);
                if (receivedPosition > -1 && receivedPosition < BookContent.getBooks().size()) {
                    viewBook(receivedPosition);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.cancelAll();
                }
            } else if (getIntent().getAction().equals(CommonConstants.ACTION_ERASE)
                    && getIntent().hasExtra(CommonConstants.POSITION_KEY)) {
                int receivedPosition = getIntent().getIntExtra(CommonConstants.POSITION_KEY, -1);
                if (receivedPosition > -1 && receivedPosition < BookContent.getBooks().size()) {
                    removeBook(receivedPosition);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.cancelAll();
                }
            }
        }
        */



        //Obtenim un informador de l'estat de connexió del dispositiu.
        //https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html#DetermineType
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();

        //Es comprova si hi ha el segon panell de detalls.
        twoFragments = findViewById(R.id.frag_book_detail) != null;
        //Obtenim la referència al swipe layout.
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id. swipeContainer);
        //Inicialitzem l'adaptador.
        mAdapter = new RecyclerAdapter(this, twoFragments);


        //Inicialitzem les variables d'autenticacio i base de dades Firebase.
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mBookReference = mDatabase.getReference("books");   //Passem com a referència el nivell de dades que volem que ens passi amb l'snapshot
        mBookReference.addValueEventListener(getValueEventListener());



        //Autentiquem l'aplicacio a la base de dades i definim la variable isAuth
        signInFirebaseDatabase();

        //S'afegeix el listener del SwipeRefreshLayout per tal que es pugui actualitzar la llista.
        swipeContainer .setOnRefreshListener(getSwipeContainerListener(activeNetwork));

        //Inflem el layout de l'activity.
        setUI();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //super.onNewIntent(intent);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals(CommonConstants.ACTION_DETAIL)
                    && intent.hasExtra(CommonConstants.POSITION_KEY)) {
                int receivedPosition = intent.getIntExtra(CommonConstants.POSITION_KEY, -1);
                if (receivedPosition > -1 && receivedPosition < BookContent.getBooks().size()) {
                    Toast.makeText(this, "ACTION_DETAIL", Toast.LENGTH_SHORT).show();
                    viewBook(receivedPosition);
                    nm.cancelAll();
                }
            } else if (intent.getAction().equals(CommonConstants.ACTION_ERASE)
                    && intent.hasExtra(CommonConstants.POSITION_KEY)) {
                int receivedPosition = intent.getIntExtra(CommonConstants.POSITION_KEY, -1);
                if (receivedPosition > -1 && receivedPosition < BookContent.getBooks().size()) {
                    Toast.makeText(this, "ACTION_ERASE", Toast.LENGTH_SHORT).show();
                    removeBook(receivedPosition);
                    nm.cancelAll();
                }
            }
        }
        
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
     * setUI() s'encarrega de actualitzar les dades de l'adaptador i d'inflar la llista de tarjetes.
     */
    private void setUI() {

        mAdapter.setItems(BookContent.getBooks());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
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


    /*
     * Indica si el dispositiu disposa d'algun tipus de connexió a la xarxa.
     */
    private boolean isConnected(NetworkInfo activeNetwork) {
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }




    /*
     * Retorna un objecte ValueEventListener. Aquest listener s'encarrega d'escoltar la base de dades
     * Firebase i en el moment que es detecta un canvi s'executa el mètode onDataChange(). Aquest mètode
     * transmet per parametre un objecte DataSnapshot que conté una "vista" de la base de dades en el moment
     * dels canvis. Dins d'aquest mètode guardem les noves dades a la variable global newData i activem
     * el flag isNewData per tal que quan fem un refresh de la llista s'actualitzi amb noves dades.
     * En cas que no s'hagin pogut descarregar les dades ja sigui per problemes de connexió d'autenticació
     * a la base de dades es crida onCancelled, que únicament ens indicara que no ha pogut accedir a Firebase.
     */
    @NonNull
    private ValueEventListener getValueEventListener() {
        return new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

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


    /*
     * Rep per paramtre un snapshot de Firebase. En cas que hi hagi nous elements a la base de dades
     * Firebase els copiarà a la base de dades local i indicarà que s'ha actualitzat la llista.
     */
    private void updateBooksFromFirebase(DataSnapshot dataSnapshot) {
        boolean newData = false;
        //Es rep l'objecte DataSnapshot i es transforma en un ArrayList de llibres.
        GenericTypeIndicator<ArrayList<BookContent.BookItem>> t =
                new GenericTypeIndicator<ArrayList<BookContent.BookItem>>() {};
        ArrayList<BookContent.BookItem> fbBooks = dataSnapshot.getValue(t);
        //Comprovem que la llista de llibres rebuda de Firebase no sigui buida.
        if (fbBooks != null) {
            for (BookContent.BookItem book : fbBooks) {
                //Per cada element de la llista comprovem si existeix ja en la base de dades local.
                if (!BookContent.exists(book)) {
                    //Si no existeix s'afegeix i s'indica que hi ha hagut canvis a la llista
                    book.save(); //ATENCIÓ: Només es detecta els canvis en els llibres si es canvia titol i/o autor.
                    newData = true;
                }
            }
            //S'actualitza la llista mostrada a l'usuari
            setUI();
            //Si hi ha hagut canvis s'indica a l'usuari.
            if (newData) {
                Toast.makeText(BookListActivity.this, "S'ha trobat i afegit nous llibres.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    /*
     * Autentiquem l'usuari a la base de dades Firebase. Si s'autentica correctament s'indica
     * en el boolea isAuth.
     */
    private void signInFirebaseDatabase() {

        mAuth.signInWithEmailAndPassword("miqasals@gmail.com", "123456")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(CommonConstants.TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()){
                            isAuth = true;
                            Toast.makeText(BookListActivity.this, "Autenticació a Firebase completada correctament",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(BookListActivity.this, "No s'ha pogut autenticar a Firebase",
                                    Toast.LENGTH_SHORT).show();
                            isAuth = false;
                        }

                    }
                });
    }


    /*
     * Retorna un listener per al Swipe layout. Aquest listener controla quan l'usuari estira la llista cap baix
     * per tal que cridi a la funció onRefresh.
     * El mètode onRefresh comprova si hi ha noves dades rebudes de Firebase amb el flag isNewData. Si hi han
     * noves dades es comprova que es disposi de connexió i l'usuari estigui autenticat per poder accedir a Firebase.
     * Si tot es correcte es crida la funció updateBooksFromFirebase amb el DataSnapshot global newData, on s'haura guardat la vista
     * en el mateix moment que s'ha activat el flag isNewData, i s'actualitza la llista.
     */
    @NonNull
    private SwipeRefreshLayout.OnRefreshListener getSwipeContainerListener(final NetworkInfo activeNetwork) {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNewData){
                    if (isConnected(activeNetwork) && isAuth){

                        updateBooksFromFirebase(newData);
                        //Després d'actualitzar la llista es para la barra de carrega
                        if (swipeContainer.isRefreshing()) {
                            swipeContainer.setRefreshing(false);
                        }
                        isNewData = false;
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
                    Toast.makeText(BookListActivity.this, "No s'han trobat nous llibres", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }


    public void viewBook(int position) {
        if ( twoFragments ) {
            BookDetailFragment bookDetailFragment = new BookDetailFragment();
            Bundle posArg = new Bundle();
            posArg.putInt("position",position);
            bookDetailFragment.setArguments(posArg);

            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.frag_book_detail,bookDetailFragment)
                    .commit();

        } else {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("position", position);
            this.startActivity(intent);
        }
    }

    public void removeBook (int position) {
        BookContent.BookItem book = BookContent.getBooks().get(position);
        book.delete();
        setUI();
    }
}
