package com.example.novedia.realmpoc.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by l.olarte on 12/11/2014.
 * model of all books
 */
public class ModelObject extends RealmObject {
    private String name;
    private int price;

    @Ignore
    private int sessionId;


    // Getters and Setters :
    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }




}
