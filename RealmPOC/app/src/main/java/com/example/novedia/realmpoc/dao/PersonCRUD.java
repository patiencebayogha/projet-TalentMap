package com.example.novedia.realmpoc.dao;

import android.util.Log;

import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.model.Person;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by l.olarte on 13/11/2014.
 */
public class PersonCRUD {

    public Person create(Person objectToCreate, Realm realm) {
        // All writes must be wrapped in a transaction to facilitate safe multi threading
        realm.beginTransaction();
        Person object = realm.createObject(Person.class);
        buildObject(object, objectToCreate);
        // When the write transaction is committed, all changes a synced to disk.
        realm.commitTransaction();
        return object;
    }

    public void update(Person objectToUpdate, Realm realm) {
        Person object;
        object = realm.where(Person.class).findFirst();
        realm.beginTransaction();
        buildObject(object, objectToUpdate);
        realm.commitTransaction();
    }

    public void deleteAll(Realm realm) {
        // Delete all
        realm.beginTransaction();
        realm.allObjects(Person.class).clear();
        realm.commitTransaction();
    }

    protected void buildObject(Person person, Person attributes) {
        person.setName(attributes.getName());
        person.setAge(attributes.getAge());
    }

    public ArrayList<Person> getAll(Realm realm) {
        RealmResults<Person> resultList = realm.allObjects(Person.class);
        return new ArrayList<Person>(resultList);
    }

    /**
     * Make a query searching by name
     *
     * @param name  name of the person; It should be exactly the same
     * @param realm realm instance db
     * @return all people called @param name
     */
    public ArrayList<Person> getByName(String name, Realm realm) {
        RealmResults<Person> resultList = realm.where(Person.class).equalTo("name", name).findAll();
        return new ArrayList<Person>(resultList);
    }

    public ArrayList<Person> getByAge(int age, Realm realm) {
        RealmResults<Person> resultList = realm.where(Person.class).equalTo("age", age).findAll();
        return new ArrayList<Person>(resultList);
    }

    public void delete(Person selectedPerson, Realm realm) {
        RealmResults<Person> resultList = realm.allObjects(Person.class);
        for (int i = 0; i < resultList.size(); i++) {
            Person personToDelete = resultList.get(i);

            if (selectedPerson.equals(personToDelete)) {
                realm.beginTransaction();
                personToDelete.removeFromRealm();
                realm.commitTransaction();
                break;
            }

        }
    }

    public void addObject(Person person, ModelObject object, Realm realm) {
        realm.beginTransaction();
        person.getObjects().add(object);
        realm.commitTransaction();
    }

    public void addArrayObjects(Person person, ArrayList<ModelObject> objects, Realm realm) {
        realm.beginTransaction();
        for (ModelObject modelObject : objects)
            person.getObjects().add(modelObject);
        realm.commitTransaction();
    }

    /**
     * list according to the filter. In case of empty criteria, it will add all elements.
     *
     * @param personName
     * @param objectName
     * @param price
     * @param realm
     * @return
     * @throws NumberFormatException
     */
    public ArrayList<ModelObject> getObjectsByCriteria(String personName, String objectName, String price, Realm realm) throws NumberFormatException {
        boolean filterByName = !objectName.isEmpty();
        boolean filterByPrice = !price.isEmpty();
        RealmResults<Person> contacts = null;
        float floatPrice = (filterByPrice) ? Float.parseFloat(price) : 0;

        //Filter By name
        if (!personName.isEmpty())
            contacts = realm.where(Person.class).equalTo("name", personName).findAll();
        else
            contacts = realm.where(Person.class).findAll();
        Log.e(getClass().getCanonicalName(), "" + contacts);
        //Object filters
        ArrayList<ModelObject> objects = new ArrayList<ModelObject>();
        for (Person person : contacts) {
            if (person.getObjects() != null && person.getObjects().size() > 0)//has objects?
                if (!filterByName && !filterByPrice)//Are we filtering ?
                    objects.addAll(person.getObjects());//no filters
                else {
                    for (ModelObject modelObject : person.getObjects()) {//Add by filter
                        if ((filterByName && modelObject.getName().equals(objectName)) || (filterByPrice && modelObject.getPrice() == floatPrice))
                            objects.add(modelObject);
                    }
                }
        }

        return objects;
    }
}
