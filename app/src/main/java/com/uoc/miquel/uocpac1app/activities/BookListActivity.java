package com.uoc.miquel.uocpac1app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uoc.miquel.uocpac1app.adapters.BookListAdapter;
import com.uoc.miquel.uocpac1app.entities.Book;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.R;

import java.util.ArrayList;

/*
    Fonts consultades:
        - FragmentBasics sample de Android Developers.
        - https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */


public class BookListActivity extends AppCompatActivity {

    private final int NUM_ITEMS = 15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);


        // Obtenim la referencia de la llista.
        ListView bookList = (ListView) findViewById(R.id.book_list);

        // Es crea una coleccio de llibres provisional per omplir la llista
        ArrayList<Book> books = new ArrayList<>(populatesSampleList());

        // Inicialitzem l'adaptador de la llista.
        BookListAdapter bookListAdapter = new BookListAdapter(this, R.id.book_list, books);

        /*
            Es vincula l'adaptador a la llista i es mostra a la pantalla carregant el contingut
            amb l'adaptador.
          */

        bookList.setAdapter(bookListAdapter);


        /**************** w900dp FRAGMENT *********************
        Comprovem si en el layout que s'ha carregat existeix el fragment
        amb id frag_book_detail. En cas que hi sigui, es carregarà el
        fragment amb l'activity i el seu propi layout. Si no, sol es carregara
        la llista que ocuparà tota la pantalla.
        */
        if (findViewById(R.id.frag_book_detail) != null) {


            //Es crea instancia de l'activity que controlarà el fragment
            BookDetailFragment bookDetailFrag = new BookDetailFragment();

            //Es passa les dades que pugui necessitar el fragment.
            bookDetailFrag.setArguments(getIntent().getExtras());

            //Enllaça el fragment bookDetailFrag al contenidor frag_book_detail.
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag_book_detail, bookDetailFrag)
                    .commit();
        }
        ///////////////////

    }

    private ArrayList<Book> populatesSampleList() {
        ArrayList<Book> list = new ArrayList<>();
        for (int i=0;i<NUM_ITEMS;i++){
            list.add(new Book(i+1,getString(R.string.book_item_name_sample) + (i+1),null));
        }
        return list;
    }
}
