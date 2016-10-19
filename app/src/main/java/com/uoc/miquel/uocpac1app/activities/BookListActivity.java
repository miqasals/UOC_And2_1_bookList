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
 * BookListActivity se encarga de comprobar si el dispositivo es mas ancho de 900dp y, en caso
 * que sea mas pequeño, se mostrara unicamente la lista gestionando los clicks. Si es mas grande
 * de este tamaño la lista ocupara una tercera parte de la pantalla y los detalles se mostraran
 * al lado con un fragment.
 */
public class BookListActivity extends AppCompatActivity {

    private boolean twoFragments = false;

    // Fuente: "https://developer.android.com/training/material/lists-cards.html?hl=es"
    // Variables globales para el objeto RecyclerView. Se crean globales porque en
    // la documentacion se indica asi y es posible que se requieran globales mas adelante.
    // En este momento podrian haverse creado locales en onCreate sin problemas.
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        /* EXERCICI1 - Con recycler view no es necesario.
        //Es comprova si hi ha guardat un estat de l'activity.
        if (savedInstanceState != null) {
            return;
        }*/

        // Guardamos en un booleano si la pantalla es de mas de 900dp de ancho y ha cargado el
        // el layout del fragment.
        twoFragments = findViewById(R.id.frag_book_detail) != null;


        //Buscamos el elemento del layout correspondiente al recyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_list);
        //Creamos el gestor de vista.
        mLayoutManager = new LinearLayoutManager(this);
        //Asociamos el gestor del layout con la lista.
        mRecyclerView.setLayoutManager(mLayoutManager);
        //Creamos el adaptador pasando la lista de elementos y el booleano que indica si hay el fragment.
        mAdapter = new RecyclerAdapter(this, BookContent.ITEMS, twoFragments);
        //Se asocia el adaptador a la lista. Este hincha la lista y la muestra por pantalla.
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
         * Si twoFragments = true, indica que el panel FrameLayout de detalles se ha cargado y, por
         * lo tanto se trata de una pantalla de mas de 900dp de ancho. Al existir este panel se deberá
         * crear iniciar la informacion del fragment.
         */
        if (twoFragments) {
            //Se crea instancia de la clase que gestiona el fragmen.
            BookDetailFragment bookDetailFrag = new BookDetailFragment();

            //Inicializamos el fragment sobre el layout correspondiente y se muestra por pantalla.
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
