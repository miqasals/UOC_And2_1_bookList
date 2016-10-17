package com.uoc.miquel.uocpac1app.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.model.BookContent;

import java.text.SimpleDateFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookDetailFragment extends Fragment {

    private BookContent.BookItem book;

    public BookDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            book = BookContent.ITEMS.get(args.getInt("position"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        TextView data = (TextView) view.findViewById(R.id.book_detail_data);
        TextView autor = (TextView) view.findViewById(R.id.book_detail_autor);
        TextView desc = (TextView) view.findViewById(R.id.book_detail_desc);
        ImageView img = (ImageView) view.findViewById(R.id.book_detail_img);
        if (book != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            data.setText(dateFormat.format(book.getDataPublicacio()));
            autor.setText(book.getAutor());
            desc.setText(book.getDescripcio());
            Picasso.with(view.getContext()).load(book.getImgUrl()).fit().centerInside().into(img);
        }
        return view;
    }

}
