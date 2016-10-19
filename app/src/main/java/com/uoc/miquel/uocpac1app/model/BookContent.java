package com.uoc.miquel.uocpac1app.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Se crea la clase BookContent que simularia la obtencion de datos de un sistema de persistencia.
 */
public class BookContent {

    public static final List<BookItem> ITEMS = new ArrayList<>();
    private static final String URL = "http://www.gigamesh.com/assets/images/catalog/danza-de-dragones-3.jpg";

    /*
    static {
            BookItem book1 = new BookItem( 0 , "Title1" , "Author1" , new Date(), "Description" , URL );
            BookItem book2 = new BookItem( 1 , "Title2" , "Author2" , new Date(), "Description 2" , URL );
            ITEMS.add(book1);
            ITEMS.add(book2);
        }
        */

    // Se sustituye el codigo propuesto para introducir mas elementos en la lista.
    static {
        for (int i = 1; i < 10; i++) {
            BookItem book = new BookItem( i , "Title" + i , "Author" + i , new Date(), "Description" , URL );
            ITEMS.add(book);
        }
    }


    public BookContent() {
    }




    /*
     * Clase estatica correspondiente a un elemento Book.
     */
    public static class BookItem {
        private int identificador;
        private String titol;
        private String autor;
        private Date dataPublicacio;
        private String descripcio;
        private String imgUrl;

        public BookItem (){
        }


        public BookItem(int identificador, String titol, String autor, Date dataPublicacio, String descripcio, String imgUrl) {
            this.identificador = identificador;
            this.titol = titol;
            this.autor = autor;
            this.dataPublicacio = dataPublicacio;
            this.descripcio = descripcio;
            this.imgUrl = imgUrl;
        }

        public void setIdentificador(int identificador) {
            this.identificador = identificador;
        }

        public void setTitol(String titol) {
            this.titol = titol;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public void setDataPublicacio(Date dataPublicacio) {
            this.dataPublicacio = dataPublicacio;
        }

        public void setDescripcio(String descripcio) {
            this.descripcio = descripcio;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getIdentificador() {
            return identificador;
        }

        public String getTitol() {
            return titol;
        }

        public String getAutor() {
            return autor;
        }

        public Date getDataPublicacio() {
            return dataPublicacio;
        }

        public String getDescripcio() {
            return descripcio;
        }

        public String getImgUrl() {
            return imgUrl;
        }
    }


}
