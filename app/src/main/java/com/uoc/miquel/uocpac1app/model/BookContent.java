package com.uoc.miquel.uocpac1app.model;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * La classe BookContent gestiona l'accés a la base de dades local de l'aplicació. S'implementa la classe
 * interna BookItem com a model i la clase contenedora disposa dels mètodes per obtenir administrar les dades.
 * La gestió de la base de dades es realitza amb la llibreria SugarORM heredant la classe SugarRecord.
 */
public class BookContent {


    public BookContent() {
    }

    //Si la cerca del titol + autor dona com a resultat una llista de un o mes elements retorna true.
    public static boolean exists(BookItem bookItem){
        return ((BookItem.find(BookItem.class, "title = ? and author = ?",
                String.valueOf(bookItem.getTitle()),bookItem.getAuthor()).size())>0);
    }



    //Es retorna la llista completa de llibres guardats a la base de dades local.
    public static List<BookItem> getBooks(){
        return BookItem.listAll(BookItem.class);
    }


    /*
     * Clase estatica correspondiente a un elemento Book.
     */
    public static class BookItem extends SugarRecord {
        private String title;
        private String author;
        private String publication_date;
        private String description;
        private String url_image;


        public BookItem() {
        }

        public BookItem(String title, String author, String publication_date,
                        String description, String url_image) {
            this.title = title;
            this.author = author;
            this.publication_date = publication_date;
            this.description = description;
            this.url_image = url_image;
        }


        // GETTERS & SETTERS
        public void setTitle(String title) {
            this.title = title;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setPublication_date(String publication_date) {
            this.publication_date = publication_date;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setUrl_image(String url_image) {
            this.url_image = url_image;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getPublication_date() {
            return publication_date;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl_image() {
            return url_image;
        }

        @Override
        public String toString() {
            return "Llista de Books: \n" +
                    "Book " + this.getId() + "-------------\n" +
                    "Titol: " + this.getTitle() + "\n" +
                    "Autor: " + this.getAuthor() + "\n" +
                    "Data de publicació: " + this.getPublication_date() + "\n" +
                    "Descripció: " + this.getDescription() + "\n" +
                    "Url imatge: " + this.getUrl_image() + "\n";
        }
    }

}
