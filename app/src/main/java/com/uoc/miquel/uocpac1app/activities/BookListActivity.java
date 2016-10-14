package com.uoc.miquel.uocpac1app.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.adapters.RecyclerAdapter;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.model.BookContent;


/*
    Fonts consultades:
        - FragmentBasics sample de Android Developers.
        - https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */


public class BookListActivity extends AppCompatActivity {

    private static final int DEFAULT_ELEMENT = 0;
    private boolean twoFragments = false;

    // https://developer.android.com/training/material/lists-cards.html?hl=es
    // Variables globals per la llista RecyclerView.
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        /* EXERCICI1 - Es comprova si hi ha guardat un estat de l'activity.
        if (savedInstanceState != null) {
            return;
        }*/

        // indiquem en el boolea si tindrem dos panells o no.
        twoFragments = findViewById(R.id.frag_book_detail) != null;


        //Es busca, primerament, l'element RecyclerView del layout.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_list);
        //Es crea el gestor de layout.
        mLayoutManager = new LinearLayoutManager(this);
        //S'assigna el gestor de layout a la llista que tenim.
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Es crea l'adaptador passant el context i la llista d'elements per parametre.
        mAdapter = new RecyclerAdapter(this, BookContent.ITEMS, twoFragments);
        //S'assigna l'adaptador a la llista.
        mRecyclerView.setAdapter(mAdapter);





/* EXERCICI 1 ---------------------
        // Obtenim la referencia de la llista.
        ListView bookList = (ListView) findViewById(R.id.book_list);
        // Es crea una coleccio de llibres provisional per omplir la llista
        ArrayList<BookContent> books = new ArrayList<>(populatesSampleList());
        // Inicialitzem l'adaptador de la llista.
        BookListAdapter bookListAdapter = new BookListAdapter(this, R.id.book_list, books);
        // Es vincula l'adaptador a la llista i es mostra a la pantalla carregant el contingut
        // amb l'adaptador.
        bookList.setAdapter(bookListAdapter);
------------------------------------*/


        /*
        Si twoFragments = true, vol dir que el panell del FrameLayour de detalls s'ha carregat
        i, per tant, ens trobem amb una pantalla de mes de 900dp. Al existir aquest panell s'ha
        de crear també l'activity que el gestiona (BookDetailFragment) i iniciar el fragment amb
        l'ajuda de FragmentManager.
         */
        if (twoFragments) {
            //Es crea instancia de l'activity que controlarà el fragment
            BookDetailFragment bookDetailFrag = new BookDetailFragment();

            /*  NO FA FALTA.....
            //Es crea un bundle per afegir al fragemnt un element per mostrar per defecte.
            Bundle posArg = new Bundle();
            /* Per defecte indicarem que es mostri el primer element de la llista ja que si ho
             * fem per identificador es possible que es modifiques la llista i aquest no existis.
             *
            posArg.putInt(BOOK_ID,BookContent.ITEMS.get(DEFAULT_ELEMENT).getIdentificador());
            bookDetailFrag.setArguments(posArg);
*/


            //Enllaça el fragment bookDetailFrag al contenidor frag_book_detail.
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag_book_detail, bookDetailFrag)
                    .commit();
        }
/* EXERCICI 1 -----------------------
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (twoFragments){
                    BookDetailFragment bookDetailFragment = new BookDetailFragment();

                    Bundle posArg = new Bundle();
                    posArg.putInt("position",i);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frag_book_detail,bookDetailFragment)
                            .addToBackStack(null)
                            .commit();
                } else {
                    Intent intent = new Intent(getBaseContext(), BookDetailActivity.class);
                    intent.putExtra("position", i);
                    startActivity(intent);
                }
            }
        });
---------------------------------------*/
    }



 /* EXERCICI 1
    populatesSampleList es una funció que solament insereix a la llista de books uns valors per mostrar.

    private ArrayList<BookContent> populatesSampleList() {
        ArrayList<BookContent> list = new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add(new BookContent(i+1,getString(R.string.book_item_name_sample) + (i+1),null));
        }
        return list;
    }
*/
}
