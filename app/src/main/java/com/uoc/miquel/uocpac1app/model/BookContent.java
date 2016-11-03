package com.uoc.miquel.uocpac1app.model;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Se crea la clase BookContent que simularia la obtencion de datos de un sistema de persistencia.
 */
public class BookContent {

    //public static final List<BookItem> ITEMS = new ArrayList<>();

    /* Codi PAC1
    private static final String URL = "http://www.gigamesh.com/assets/images/catalog/danza-de-dragones-3.jpg";

    // Se sustituye el codigo propuesto para introducir mas elementos en la lista.
    static {
        for (int i = 1; i < 10; i++) {
            BookItem book = new BookItem( i , "Title" + i , "Author" + i , new Date(), "Description" , URL );
            ITEMS.add(book);
        }
    }
    */

    public BookContent() {
    }

    //TODO: REVISAR!!!!!!!!!!!!!!!!!! No el trobarem amb el mateix id. Mirar metode find().
    public static boolean exists(BookItem bookItem){
        // Obtenim l'element
        BookItem book = BookItem.findById(BookItem.class,bookItem.getIdentificador());
        return (book != null);
    }


    public static List<BookItem> getBooks(){
        return BookItem.listAll(BookItem.class);
    }


    /*
     * Clase estatica correspondiente a un elemento Book.
     */
    public static class BookItem extends SugarRecord {
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



        //TODO: Comprovar si es necessari sobreescriure equals. (BookListActivity.class)
        @Override
        public boolean equals(Object obj) {
            BookItem book = (BookItem) obj;
            if (book == null) return false;
            return (this.identificador == book.getIdentificador()&&
                    this.titol.equals(book.getTitol()) &&
                    this.autor.equals(book.getAutor()) &&
                    this.dataPublicacio.equals(book.getDataPublicacio()) &&
                    this.descripcio.equals(book.getDescripcio()) &&
                    this.imgUrl.equals(book.getImgUrl()));
        }
    }

}
