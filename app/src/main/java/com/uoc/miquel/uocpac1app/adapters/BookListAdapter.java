package com.uoc.miquel.uocpac1app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.model.BookContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mucl on 10/10/2016.
 *
 * s'encarrega de mostrar per pantalla la llista de llibres amb les dades
 * que volem incloure. Utilitzat en el disseny de l'aplicació en l'EXERCICI 1.
 */

public class BookListAdapter extends ArrayAdapter<BookContent> {

    // Creem un Array de llibres per tal de poder accedir després des del mètode getView.
    private ArrayList<BookContent> books;

    // Constructor per defecte afegint la assignació de la llista de llibres a la propietat books
    public BookListAdapter(Context context, int resource, List<BookContent> objects) {
        super(context, resource, objects);
        this.books = (ArrayList<BookContent>) objects;
    }

    /*
    Es sobreescriu getView per tal que, quan es fa la crida implicita a aquest mètode
    amb la comanda setAdapter sobre la llista, tots els elements de la llista otbinguin
    les seves dades corresponents.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        /*
        Es comprova que no existeixi ja una View en memòria de l'item de la llista. Si
        no existeix, es crea una View i la instancia de ViewHolder i s'obté la referencia
        dels elements de la vista en el holder per poder actuar sobre ells. En cas que ja
        existeixi una vista i tingui guardat la referència del holder com a tag, es recupera
        i s'actua sobre els elements modificant el text.
         */
        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_item_exercici1, parent, false);
            holder.bookId = (TextView) convertView.findViewById(R.id.book_item_id);
            holder.bookName = (TextView) convertView.findViewById(R.id.book_item_name);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BookContent book = books.get(position);

        //holder.bookId.setText(String.valueOf(book.getId()));
        //holder.bookName.setText(book.getName());              // Al modificar la classe book getName i getId s'anulen.

        return convertView;
    }


    /*
    ViewHolder es una classe estatica que ens evita realitzar més d'una instancia en memoria
    de cada element visual per tal d'evitar successives crides a metodes de cerca com
    findViewById que consumeix bastants recursos.
     */
    static class ViewHolder {
        protected TextView bookId;
        protected TextView bookName;
    }

}
