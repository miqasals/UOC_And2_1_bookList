package com.uoc.miquel.uocpac1app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uoc.miquel.uocpac1app.adapters.BookListAdapter;
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

    private boolean twoFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Es comprova si hi ha guardada un estat de l'activity.
        if (savedInstanceState != null) {
            return;
        }

        twoFragments = findViewById(R.id.frag_book_detail) != null;

        // Obtenim la referencia de la llista.
        ListView bookList = (ListView) findViewById(R.id.book_list);
        // Es crea una coleccio de llibres provisional per omplir la llista
        ArrayList<BookContent> books = new ArrayList<>(populatesSampleList());
        // Inicialitzem l'adaptador de la llista.
        BookListAdapter bookListAdapter = new BookListAdapter(this, R.id.book_list, books);
        // Es vincula l'adaptador a la llista i es mostra a la pantalla carregant el contingut
        // amb l'adaptador.
        bookList.setAdapter(bookListAdapter);

        if (twoFragments) {


            //Es crea instancia de l'activity que controlarà el fragment
            BookDetailFragment bookDetailFrag = new BookDetailFragment();

            Bundle posArg = new Bundle();
            posArg.putInt("position",0);

            //Enllaça el fragment bookDetailFrag al contenidor frag_book_detail.
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frag_book_detail, bookDetailFrag)
                    .commit();
        }

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

    }



    private ArrayList<BookContent> populatesSampleList() {
        ArrayList<BookContent> list = new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add(new BookContent(i+1,getString(R.string.book_item_name_sample) + (i+1),null));
        }
        return list;
    }
}
