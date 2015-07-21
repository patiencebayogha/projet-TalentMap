package com.example.novedia.realmpoc.dao;

import com.example.novedia.realmpoc.model.ModelObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by l.olarte on 13/11/2014.
 */
public class ObjectCRUD {

    public ModelObject create(ModelObject objectToCrete, Realm realm) {
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.beginTransaction();
        ModelObject object = realm.createObject(ModelObject.class);
        buildObject(object, objectToCrete);
        // When the write transaction is committed, all changes a synced to disk.
        realm.commitTransaction();
        return object;
    }

    public void update(ModelObject objectToUpdate, Realm realm) {
        ModelObject object = realm.where(ModelObject.class).findFirst();
        realm.beginTransaction();
        buildObject(object, objectToUpdate);
        realm.commitTransaction();
    }

    public void deleteAll(Realm realm) {
        // Delete all
        realm.beginTransaction();
        realm.allObjects(ModelObject.class).clear();
        realm.commitTransaction();
    }

    protected void buildObject(ModelObject modelObject, ModelObject attributes) {
        modelObject.setName(attributes.getName());
        modelObject.setPrice(attributes.getPrice());
    }

    public ArrayList<ModelObject> getAll(Realm realm) {
        RealmResults<ModelObject> resultList = realm.allObjects(ModelObject.class);
        return new ArrayList<ModelObject>(resultList);
    }

    /**
     * Make a query searching by name
     *
     * @param name  name of the object; It should be exactly the same
     * @param realm realm instance db
     * @return all people called @param name
     */
    public ArrayList<ModelObject> getByName(String name, Realm realm) {
        RealmResults<ModelObject> resultList = realm.where(ModelObject.class).equalTo("name", name).findAll();
        return new ArrayList<ModelObject>(resultList);
    }

    public ArrayList<ModelObject> getByPrice(int price, Realm realm) {
        RealmResults<ModelObject> resultList = realm.where(ModelObject.class).equalTo("price", price).findAll();
        return new ArrayList<ModelObject>(resultList);
    }

    public void delete(ModelObject selectedModelObject, Realm realm) {
        RealmResults<ModelObject> resultList = realm.allObjects(ModelObject.class);
        for (int i = 0; i < resultList.size(); i++) {
            ModelObject objectToDelete = resultList.get(i);
            if (selectedModelObject.equals(objectToDelete)) {
                realm.beginTransaction();
                objectToDelete.removeFromRealm();
                realm.commitTransaction();
                break;
            }
        }
    }

    /**
     * @param modelObjectsToCreate
     * @param realm
     * @return empty arrayList if no elements
     */
    public ArrayList<ModelObject> create(ArrayList<ModelObject> modelObjectsToCreate, Realm realm) {
        ArrayList<ModelObject> modelObjects = new ArrayList<ModelObject>();
        realm.beginTransaction();
        for (ModelObject modelObject : modelObjectsToCreate) {
            ModelObject object = realm.createObject(ModelObject.class);
            buildObject(object, modelObject);
            modelObjects.add(object);
        }
        realm.commitTransaction();
        return modelObjects;
    }
}
