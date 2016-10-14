package com.uoc.miquel.uocpac1app.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uoc.miquel.uocpac1app.model.BookContent;

/**
 * Created by mucl on 14/10/2016.
 */

public class BookDetailAdapter extends BaseAdapter {

    private BookContent.BookItem book;
    private int bookId;

    public BookDetailAdapter(BookContent.BookItem item, int itemId) {
        this.book = item;
        this.bookId = itemId;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
