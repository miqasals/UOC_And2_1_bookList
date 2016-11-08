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



    public RecyclerAdapter(Context context, boolean num) {
        this.mContext = context;
        this.items = new ArrayList<>();
        this.twoFragments = num;
    }


    public void setItems(List<BookContent.BookItem> items) {
            this.items = new ArrayList<>(items);
    }


    /*
    onCrateViewHolder se encarga de inflar la vista del elemento y la devuelve para que
    es muestre por pantalla.
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
     * Se encarga de mostrar los diferentes elementos cuando la RecyclerView lo necesite, modificando
     * el conjunto de elementos en funcion de la posicion.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolderItem viewHolder = (ViewHolderItem) holder;
        viewHolder.item = items.get(position);
        viewHolder.titol.setText(items.get(position).getTitle());
        viewHolder.autor.setText(items.get(position).getAuthor());
        viewHolder.view.setTag(position);
        viewHolder.view.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPos = (int) v.getTag();

                if ( twoFragments ) {
                    BookDetailFragment bookDetailFragment = new BookDetailFragment();
                    Bundle posArg = new Bundle();
                    posArg.putInt("position",currentPos);
                    bookDetailFragment.setArguments(posArg);

                    FragmentManager manager = ((FragmentActivity)mContext).getSupportFragmentManager();
                    manager.beginTransaction()
                            .replace(R.id.frag_book_detail,bookDetailFragment)
                            .commit();

                } else {
                    Intent intent = new Intent(v.getContext(), BookDetailActivity.class);
                    intent.putExtra("position", currentPos);
                    mContext.startActivity(intent);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        if(items!=null) {
            return this.items.size();
        } else {
            return 0;
        }
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
