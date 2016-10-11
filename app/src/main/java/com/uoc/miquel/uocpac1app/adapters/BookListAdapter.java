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
 */

public class BookListAdapter extends ArrayAdapter<BookContent> {

    private ArrayList<BookContent> books;

    public BookListAdapter(Context context, int resource, List<BookContent> objects) {
        super(context, resource, objects);
        this.books = (ArrayList<BookContent>) objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
            holder.bookId = (TextView) convertView.findViewById(R.id.book_item_id);
            holder.bookName = (TextView) convertView.findViewById(R.id.book_item_name);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BookContent book = books.get(position);

        holder.bookId.setText(String.valueOf(book.getId()));
        holder.bookName.setText(book.getName());

        return convertView;
    }



    static class ViewHolder {
        protected TextView bookId;
        protected TextView bookName;
    }

}
