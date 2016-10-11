package com.uoc.miquel.uocpac1app.model;

import java.util.Date;

/**
 * Created by mucl on 10/10/2016.
 */
public class BookContent {

    private int id;
    private String name;
    private String desc;

    public BookContent() {
    }

    public BookContent(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    static class BookItem {
        public int identificador;
        public String titol;
        public String autor;
        public Date dataPublicacio;
        public String descripcio;
        public String imgUrl;
    }


}
