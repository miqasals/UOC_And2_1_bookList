package com.uoc.miquel.uocpac1app.entities;

/**
 * Created by mucl on 10/10/2016.
 */
public class Book {

    private int id;
    private String name;
    private String desc;

    public Book() {
    }

    public Book(int id, String name, String desc) {
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
}
