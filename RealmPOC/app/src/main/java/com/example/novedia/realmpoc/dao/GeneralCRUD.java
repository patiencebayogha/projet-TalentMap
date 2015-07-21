package com.example.novedia.realmpoc.dao;

/**
 * Created by l.olarte on 13/11/2014.
 */
public abstract class GeneralCRUD<E, T> {
//    protected String ID_NAME;
//    /**
//     *
//     * @param realm
//     * @return
//     */
//    public E create(E objectToCrete, Realm realm){
//        // All writes must be wrapped in a transaction to facilitate safe multi threading
//        realm.beginTransaction();
//
//        // Add a person
//        E object = realm.createObject(E.class);
//        buildObject(object, objectToCrete);
//        // When the write transaction is committed, all changes a synced to disk.
//        realm.commitTransaction();
//        return object;
//    }
//
//    public void update(E objectToUpdate, Realm realm){
//        E object;
//        object = realm.where(E.class).equalTo(ID_NAME,id).findFirst();
//        realm.beginTransaction();
//        buildObject(object, objectToUpdate);
//        realm.commitTransaction();
//    }
//
//    public void deleteAll(Realm realm){
//        // Delete all
//        realm.beginTransaction();
//        realm.allObjects(E.class).clear();
//        realm.commitTransaction();
//    }
//
//
//    protected abstract void buildObject(E object, E objectToCrete);

}
