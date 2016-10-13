package com.uoc.miquel.uocpac1app.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.uoc.miquel.uocpac1app.activities.BookListActivity;
import com.uoc.miquel.uocpac1app.model.BookContent;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.*;

/**
 * Created by mucl on 13/10/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int EVEN = 0;
    private final static int ODD = 1;

    private ArrayList<Object> items;
    private Context mContext;



    public RecyclerAdapter(Context context, Object o) {
        this.mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;


        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }



}
