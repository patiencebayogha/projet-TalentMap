package com.example.novedia.realmpoc.model;

import java.io.Serializable;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by l.olarte on 13/11/2014.
 * model off all persons
 */
public class Person extends RealmObject implements Serializable {
    private String name;
    private int age;
    private RealmList<ModelObject> objects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public RealmList<ModelObject> getObjects() {
        return objects;
    }

    public void setObjects(RealmList<ModelObject> objects) {
        this.objects = objects;
    }


}
