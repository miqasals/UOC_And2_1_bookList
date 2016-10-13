package com.uoc.miquel.uocpac1app.adapters;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uoc.miquel.uocpac1app.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by mucl on 13/10/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int EVEN = 0;
    private final static int ODD = 1;

    private ArrayList<Object> items;
    private Context mContext;



    public RecyclerAdapter(Context context, List<Object> o) {
        this.mContext = context;
        this.items = new ArrayList<Object>(o);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = null;

        switch (viewType) {
            case EVEN:
                view = inflater.inflate(R.layout.book_item_even, parent, false);
                viewHolder = new ViewHolderEven(view);
                break;
            case ODD:
                view = inflater.inflate(R.layout.book_item_odd, parent, false);
                viewHolder = new ViewHolderOdd(view);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case EVEN:
                ViewHolderEven holderEven = (ViewHolderEven) holder;
                configureViewHolderEven(holderEven,position);
                break;
            case ODD:
                ViewHolderOdd holderOdd = (ViewHolderOdd) holder;
                configureViewHolderOdd(holderOdd,position);
        }

    }

    private void configureViewHolderOdd(ViewHolderOdd holderOdd, int position) {

    }

    private void configureViewHolderEven(ViewHolderEven viewHolder, int position) {

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






    ///////////////////////// ViewHolder //////////////////////

    public class ViewHolderEven extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titol,autor;

        public ViewHolderEven(View itemView) {
            super(itemView);

            titol = (TextView) itemView.findViewById(R.id.book_item_title);
            autor = (TextView) itemView.findViewById(R.id.book_item_autor);

            itemView.setOnClickListener(this);
        }

        public TextView getTitol() {
            return titol;
        }

        public TextView getAutor() {
            return autor;
        }

        public void setTitol(TextView titol) {
            this.titol = titol;
        }

        public void setAutor(TextView autor) {
            this.autor = autor;
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class ViewHolderOdd extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView titol,autor;

        public ViewHolderOdd(View viewItem) {
            super(viewItem);

            titol = (TextView) itemView.findViewById(R.id.book_item_title);
            autor = (TextView) itemView.findViewById(R.id.book_item_autor);

            itemView.setOnClickListener(this);
        }

        public TextView getTitol() {
            return titol;
        }

        public TextView getAutor() {
            return autor;
        }

        public void setTitol(TextView titol) {
            this.titol = titol;
        }

        public void setAutor(TextView autor) {
            this.autor = autor;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
