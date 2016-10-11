package com.uoc.miquel.uocpac1app.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.uoc.miquel.uocpac1app.R;
import com.uoc.miquel.uocpac1app.adapters.BookListAdapter;
import com.uoc.miquel.uocpac1app.model.BookContent;

import java.util.ArrayList;

public class BookListFragment extends Fragment {



    public BookListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        // Obtenim la referencia de la llista.
        ListView bookList = (ListView) view.findViewById(R.id.book_list);
        // Es crea una coleccio de llibres provisional per omplir la llista
        ArrayList<BookContent> books = new ArrayList<>(populatesSampleList());
        // Inicialitzem l'adaptador de la llista.
        BookListAdapter bookListAdapter = new BookListAdapter(view.getContext(), R.id.book_list, books);
        // Es vincula l'adaptador a la llista i es mostra a la pantalla carregant el contingut
        // amb l'adaptador.
        bookList.setAdapter(bookListAdapter);



        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

              //  Intent intent = new Intent(getActivity(), activityClass);
              //  startActivity(intent);
                Toast.makeText(view.getContext(), "CLIK........" + position, Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }








    private ArrayList<BookContent> populatesSampleList() {
        ArrayList<BookContent> list = new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add(new BookContent(i+1,getString(R.string.book_item_name_sample) + (i+1),null));
        }
        return list;
    }
}
