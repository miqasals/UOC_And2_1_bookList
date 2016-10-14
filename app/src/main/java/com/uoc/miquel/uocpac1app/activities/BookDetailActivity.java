package com.uoc.miquel.uocpac1app.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.model.BookContent;

public class BookDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        //Es crea instancia de l'activity que controlarà el fragment
        BookDetailFragment bookDetailFrag = new BookDetailFragment();

        //Es crea un bundle per afegir al fragemnt un element per mostrar per defecte.
        Bundle posArg = new Bundle();

        int pos = getIntent().getIntExtra("position",0);
        posArg.putInt("position",pos);

        //Es modifica el titol del llibre
        TextView titol = (TextView) findViewById(R.id.book_detail_titol);
        titol.setText(BookContent.ITEMS.get(posArg.getInt("position")).getTitol());

        //Es recupera els EXTRAS de l'Intent rebut i es passen directament al fragment.
        bookDetailFrag.setArguments(posArg);

        //Enllaça el fragment bookDetailFrag al contenidor frag_book_detail.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frag_book_detail, bookDetailFrag)
                .commit();



    }
}
