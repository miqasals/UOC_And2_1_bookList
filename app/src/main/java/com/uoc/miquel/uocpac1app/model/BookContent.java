package com.uoc.miquel.uocpac1app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mucl on 10/10/2016.
 */
public class BookContent {

    public static final List<BookItem> ITEMS = new ArrayList<>();
    static {
        BookItem book1 = new BookItem( 0 , "Title1" , "Author1" , new Date(), "Description" , null );
        BookItem book2 = new BookItem( 1 , "Title2" , "Author2" , new Date(), "Description 2" , null );
        ITEMS.add(book1);
        ITEMS.add(book2);
    }


    public BookContent() {
    }


    private static class BookItem {
        private int identificador;
        private String titol;
        private String autor;
        private Date dataPublicacio;
        private String descripcio;
        private String imgUrl;

        public BookItem(int identificador, String titol, String autor, Date dataPublicacio, String descripcio, String imgUrl) {
            this.identificador = identificador;
            this.titol = titol;
            this.autor = autor;
            this.dataPublicacio = dataPublicacio;
            this.descripcio = descripcio;
            this.imgUrl = imgUrl;
        }
    }


}
