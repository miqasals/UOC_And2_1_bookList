package com.uoc.miquel.uocpac1app.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
 * Created by Miquel Casals on 13/10/2016.
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
    onCrateViewHolder s'encarrega de inflar la vista de l'element i la retornem per tal que
    es mostri per pantalla. Serà cridat implicitament per onBindViewHolder.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;

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
        return new ViewHolderItem(view);
    }


    /*
    onBindViewHolder s'encarrega de mostrar els diferents elements quan la RecyclerView ho
    necessita modificant el contingut de l'element en funció de la posició.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolderItem viewHolder = (ViewHolderItem) holder;

        viewHolder.item = items.get(position);
        viewHolder.titol.setText(items.get(position).getTitol());
        viewHolder.autor.setText(items.get(position).getAutor());
// ============ INICI CODI A COMPLETAR ===============
// Guarda la posició actual a la vista del holder

        //viewHolder.position = position;  //??????????????????????????? pa que??



// ============ FI CODI A COMPLETAR =================
        viewHolder.view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos;
// ============ INICI CODI A COMPLETAR ===============
// Obtindre la posició del llibre on s’ha fet click de la vista
                currentPos = position; //???????????????
// ============ FI CODI A COMPLETAR =================
                if ( twoFragments ) {
// ============ INICI CODI A COMPLETAR ===============
// Iniciar el fragment corresponent a tablet, enviant l’argument de la posició seleccionada
                    BookDetailFragment bookDetailFragment = new BookDetailFragment();
                    //Bundle posArg = new Bundle();
                    //posArg.putInt("position",position);
                    bookDetailFragment.updateFragment(v.getContext(),position);


// ============ FI CODI A COMPLETAR =================
                } else {
// ============ INICI CODI A COMPLETAR ===============
// Iniciar l’activitat corresponent a mòbil, enviant l’argument de la posició seleccionada
                    Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
                    intent.putExtra("position", position);
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

        int type = ODD;
        if (position % 2 == 0) type = EVEN;
        return type;
    }






    ///////////////////////// ViewHolderItem //////////////////////

    public class ViewHolderItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View view;
        public TextView titol,autor;
        public BookContent.BookItem item;
        //public int position;

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
