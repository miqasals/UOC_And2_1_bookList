package com.uoc.miquel.uocpac1app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uoc.miquel.uocpac1app.adapters.BookListAdapter;
import com.uoc.miquel.uocpac1app.adapters.RecyclerAdapter;
import com.uoc.miquel.uocpac1app.model.BookContent;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.R;

import java.util.ArrayList;


/*
    Fonts consultades:
        - FragmentBasics sample de Android Developers.
        - https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */


public class BookListActivity extends AppCompatActivity {

    private static final int DEFAULT_ELEMENT = 0;
    private boolean twoFragments = false;

    // https://developer.android.com/training/material/lists-cards.html?hl=es
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Es comprova si hi ha guardat un estat de l'activity.
        if (savedInstanceState != null) {
            return;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_book_list);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerAdapter(this, BookContent.ITEMS);
        mRecyclerView.setAdapter(mAdapter);

        // indiquem en el boolea si tindrem dos panells o no.
        twoFragments = findViewById(R.id.frag_book_detail) != null;



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



        if (twoFragments) {


            //Es crea instancia de l'activity que controlarà el fragment
            BookDetailFragment bookDetailFrag = new BookDetailFragment();

            //Es crea un bundle per afegir al fragemnt un element per mostrar per defecte.
            Bundle posArg = new Bundle();
            posArg.putInt("position",DEFAULT_ELEMENT);

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
