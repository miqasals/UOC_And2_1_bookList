package com.uoc.miquel.uocpac1app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.model.BookContent;

/**
 * La clase BookDetailActivity se encarga de gestionar, en caso que el dispositivo
 * sea de menos de 900dp de ancho, la vista de los detalles del libro seleccionado
 * de la lista.
 * Muestra los datos sobre un layout coordinatorLayout y un fragmentLayout donde se
 * cargaran los datos.
 */
public class BookDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_book_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        BookDetailFragment bookDetailFrag = new BookDetailFragment();
        Bundle posArg = new Bundle();
        int pos = getIntent().getIntExtra("position",0);
        posArg.putInt("position",pos);
        //El titol del llibre s'obté de la base de dades local
        toolbar.setTitle(BookContent.getBooks().get(posArg.getInt("position")).getTitle());
        bookDetailFrag.setArguments(posArg);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_book_detail, bookDetailFrag)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, BookListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
