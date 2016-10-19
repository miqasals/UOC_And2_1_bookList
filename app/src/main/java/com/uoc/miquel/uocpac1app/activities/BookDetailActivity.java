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

        //Creamos el objeto toolbar para poder cambiar el titulo al nombre del book.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_book_detail);
        ActionBar actionBar = getSupportActionBar();
        //Activamos el boton de vuelta atras a la activity Book List.
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Se crea instancia de la clase que gestiona el fragment.
        BookDetailFragment bookDetailFrag = new BookDetailFragment();

        //Creamos un Bundle para poder enviar datos (posicion del book elegido) al fragment.
        Bundle posArg = new Bundle();
        int pos = getIntent().getIntExtra("position",0);
        posArg.putInt("position",pos);

        /*  Exercici 1 (primera versió de layout)
        //Es modifica el titol del llibre
        TextView titol = (TextView) findViewById(R.id.book_detail_titol);
        titol.setText(BookContent.ITEMS.get(posArg.getInt("position")).getTitol());
        */

        //Se modifica el titulo para que muestre el titulo del libro. Se hace aqui porque necesitamos
        //haver recibido de BookListActivity la posicion a través del Intent.
        toolbar.setTitle(BookContent.ITEMS.get(posArg.getInt("position")).getTitol());

        //Se pasa el bundle al fragment.
        bookDetailFrag.setArguments(posArg);

        //Enlazamos el layout y la instancia del fragment para que se inicie y se muestre por pantalla.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_book_detail, bookDetailFrag)
                .commit();



    }

    //Configuramos las acciones de la actionBar. En este caso solo hay la opcion de volver atras.
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
