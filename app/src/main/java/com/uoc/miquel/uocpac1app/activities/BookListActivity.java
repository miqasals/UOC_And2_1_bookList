package com.uoc.miquel.uocpac1app.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.uoc.miquel.uocpac1app.fragments.BookListFragment;
import com.uoc.miquel.uocpac1app.model.BookContent;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.R;


/*
    Fonts consultades:
        - FragmentBasics sample de Android Developers.
        - https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */


public class BookListActivity extends AppCompatActivity {

    private final int NUM_ITEMS = 15;
    private View detailFrag;
    private boolean twoFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Es comprova si hi ha guardada un estat de l'activity.
        if (savedInstanceState != null) {
            return;
        }

        //Es crea instancia de l'activity que controlarà el fragment
        BookListFragment bookListFrag = new BookListFragment();

        //Es passa les dades que pugui necessitar el fragment.
        bookListFrag.setArguments(getIntent().getExtras());

        //S'obté la instancia de Fragment manager i s'enllaça
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.frag_book_list, bookListFrag)
                .commit();


        /*
         Comprovem si en el layout que s'ha carregat existeix el fragment
         amb id frag_book_detail. En cas que hi sigui, es carrega també
         el layout corresponent als detalls del llibre mitjançant l'objecte
         BookDetailFragment i el FrameLayout amb id frag_book_detail.
         */
        if (findViewById(R.id.frag_book_detail) != null) {
            twoFragments = true;

            //Es crea instancia de l'activity que controlarà el fragment
            BookDetailFragment bookDetailFrag = new BookDetailFragment();

            //Es passa les dades que pugui necessitar el fragment.
            //   bookDetailFrag.setArguments(getIntent().getExtras());

            //Enllaça el fragment bookDetailFrag al contenidor frag_book_detail.
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frag_book_detail, bookDetailFrag)
                    .commit();
        }
    }
}
