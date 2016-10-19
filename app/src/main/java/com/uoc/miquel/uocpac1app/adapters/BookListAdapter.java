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
 * Se encarga de mostrar por pantalla la lista de libros con los datos que queremos
 * introducir. Utilizado en el disseño de la aplicacion del EJERCICIO 1.
 */

public class BookListAdapter extends ArrayAdapter<BookContent> {

    // Creamos un array de libros para poder acceder mas tarde desde el metodo getView().
    private ArrayList<BookContent> books;

    // Constructor por defecto asociando la lista de libros recibida con la variable global de la clase.
    public BookListAdapter(Context context, int resource, List<BookContent> objects) {
        super(context, resource, objects);
        this.books = (ArrayList<BookContent>) objects;
    }

    /*
     * Se sobreescribe getView para que, cuando se realiza la llamada implicita a este metodo
     * con el comando setAdapter(...) sobre la lista, todos los elementos de la lista obtengan
     * los datos que les corresponde.
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        /*
         * Se comprueba si el elemento que se va a cargar esta guardado en memoria o no. En caso que
         * ya exista una version en memoria del item, se carga el objeto ViewHolder gracias a la referencia
           guardada en el tag de la vista. Sino, se crea la vista y se inicializa el viewholder para guardarlo
           luego en el tag de la vista. En ambos casos se obtiene la referencia de los elementos de pantalla
           de la lista y se modifican con los datos correspondientes a la posicion del libro.
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
        // Comentado para que no de error de compilacion ya que en el EX2 ha habido cambios en la clase.
        //holder.bookId.setText(String.valueOf(book.getId()));
        //holder.bookName.setText(book.getName());

        return convertView;
    }


    /*
     * ViewHolder es una clase estatica que nos evita realizar mas de una instancia en memoria
     * de cada elemento visual para ahorar-nos reiteradas llamadas a metodos de localizacion como
     * findViewById, que consume muchos recursos.
     */
    static class ViewHolder {
        protected TextView bookId;
        protected TextView bookName;
    }

}
