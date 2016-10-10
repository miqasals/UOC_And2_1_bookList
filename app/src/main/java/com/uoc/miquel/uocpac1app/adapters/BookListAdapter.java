package com.uoc.miquel.uocpac1app.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.entities.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mucl on 10/10/2016.
 */

public class BookListAdapter extends ArrayAdapter<Book> {

    private ArrayList<Book> books;

    public BookListAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
        this.books = (ArrayList<Book>) objects;
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

        Book book = books.get(position);

        holder.bookId.setText(String.valueOf(book.getId()));
        holder.bookName.setText(book.getName());

        return convertView;
    }



    static class ViewHolder {
        protected TextView bookId;
        protected TextView bookName;
    }

}
