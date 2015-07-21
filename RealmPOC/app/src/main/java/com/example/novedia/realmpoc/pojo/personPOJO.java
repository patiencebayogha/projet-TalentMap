package com.example.novedia.realmpoc.pojo;

import com.example.novedia.realmpoc.model.ModelObject;

import io.realm.RealmList;

/**
 * Created by l.olarte on 13/11/2014.
 */
public class personPOJO {

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
