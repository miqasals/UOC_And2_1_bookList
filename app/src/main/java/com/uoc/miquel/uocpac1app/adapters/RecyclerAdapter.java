package com.uoc.miquel.uocpac1app.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.activities.BookDetailActivity;
import com.uoc.miquel.uocpac1app.fragments.BookDetailFragment;
import com.uoc.miquel.uocpac1app.model.BookContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador que se encarga de rellenar y mostrar por pantalla todos los elementos de la lista
 * RecyclerView. Se crea la clase para el EX2 siguiendo la pauta de la documentacion de la
 * asignatura.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int EVEN = 0;
    private final static int ODD = 1;

    private ArrayList<BookContent.BookItem> items;
    private Context mContext;
    private boolean twoFragments;



    public RecyclerAdapter(Context context, List<BookContent.BookItem> o, boolean num) {
        this.mContext = context;
        this.items = new ArrayList<>(o);
        this.twoFragments = num;
    }


    /*
    onCrateViewHolder se encarga de inflar la vista del elemento y la devuelve para que
    es muestre p0r pantalla.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Se crea una instancia del LayoutInflater y una vista.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;

        //Segun si el elemento es par o impar se infla el layout correspondiente.
        switch (viewType) {
            case EVEN:
                view = inflater.inflate(R.layout.book_item_even, parent, false);
                break;
            case ODD:
                view = inflater.inflate(R.layout.book_item_odd, parent, false);
                break;
            default:
                break;
        }
        //Se devuelve el viewHolder de la vista.
        return new ViewHolderItem(view);
    }


    /*
     * Se encarga de mostrar los diferentes elementos cuando la RecyclerView lo necesite, modificando
     * el conjunto de elementos en funcion de la posicion.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //Se crea un elemento viewHolder vacio para capturar los datos del book correspondiente.
        final ViewHolderItem viewHolder = (ViewHolderItem) holder;

        //Actualizamos los valores del viewHolder con el elemento correspondiente de la lista.
        viewHolder.item = items.get(position);
        viewHolder.titol.setText(items.get(position).getTitol());
        viewHolder.autor.setText(items.get(position).getAutor());
// ============ INICI CODI A COMPLETAR ===============
// Guarda la posició actual a la vista del holder

        viewHolder.position = position;

// ============ FI CODI A COMPLETAR =================
        viewHolder.view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos;
// ============ INICI CODI A COMPLETAR ===============
// Obtindre la posició del llibre on s’ha fet click de la vista

                currentPos = position;

// ============ FI CODI A COMPLETAR =================
                if ( twoFragments ) {
// ============ INICI CODI A COMPLETAR ===============
// Iniciar el fragment corresponent a tablet, enviant l’argument de la posició seleccionada

                    /*
                     * En caso que la pantalla sea mayor que 900dp de ancho se sabrà por la variable
                     * twoFragments. En caso que sea cierta se carga el fragment pasando por argumento
                     * la posicion del book en la lista de books.
                     * En lugar de realizar un add() se realiza un replace() porque en este caso ya
                     * existe el fragment cargado y lo que queremos hacer es cambiar-lo por otro.
                     */
                    BookDetailFragment bookDetailFragment = new BookDetailFragment();
                    Bundle posArg = new Bundle();
                    posArg.putInt("position",currentPos);
                    bookDetailFragment.setArguments(posArg);

                    FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.frag_book_detail,bookDetailFragment)
                            .commit();

// ============ FI CODI A COMPLETAR =================
                } else {
// ============ INICI CODI A COMPLETAR ===============
// Iniciar l’activitat corresponent a mòbil, enviant l’argument de la posició seleccionada

                    /*
                     * En caso que sea una pantalla pequeña, twoFragments sera falso y se
                     * realizara la llamada a la activity BookDetailActivity con un intent.
                     * Se pasa por parametro extra la posicion que sera catpturada en la activity.
                     */
                    Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
                    intent.putExtra("position", currentPos);
                    mContext.startActivity(intent);

// ============ FI CODI A COMPLETAR =================
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {

        //Si la posicion es par, se devuelve el type como EVEN, sino se devuelve ODD.
        int type = ODD;
        if (position % 2 == 0) type = EVEN;
        return type;
    }






    ///////////////////////// ViewHolderItem //////////////////////
    /*
     * Se crea la classe ViewHolder subclasse de RecyclerView.ViewHolder. Se guarda las referencias
     * a los elementos del layout titol y autor y del conjunto de la vista del item. Tambien se guarda
     * el book concreto y la posicion.
     */

    public class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View view;
        public TextView titol,autor;
        public BookContent.BookItem item;
        public int position;

        //En el constructor del viewHolder se obtiene la referencia a los elementos del layout guardados.
        public ViewHolderItem(View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.book_list_card);
            titol = (TextView) itemView.findViewById(R.id.book_item_title);
            autor = (TextView) itemView.findViewById(R.id.book_item_autor);
        }



        @Override
        public void onClick(View v) {
        }
    }


}
