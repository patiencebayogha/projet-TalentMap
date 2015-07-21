package com.example.novedia.realmpoc.services;

import android.content.Context;

import com.example.novedia.realmpoc.dao.PersonCRUD;
import com.example.novedia.realmpoc.model.Person;

import java.util.ArrayList;

import io.realm.Realm;

/**
 * This class manage the instance of Realm DB and allow CRUD services
 * For Persons
 * Created by l.olarte on 13/11/2014.
 * modified P.Bayogha 7/05/2015
 */
public class RealmPOCService {
    private Realm realm;
    private PersonCRUD personCRUD;


    /**
     * Get the single instance of the Realm Data base service
     *
     * @param context
     * @return the
     * <p/>
     * The singleton.
     */
    private static RealmPOCService SINGLETON;

    /**
     * The Constant __synchonizedObject.
     */
    private static final Object __synchonizedObject = new Object();

    /**
     * Instantiate ans returns the retriever.
     *
     * @param context Application context.
     * @return The unique instance of the retriever.
     */
    public static RealmPOCService getInstance(Context context) {

        if (SINGLETON == null) {
            synchronized (__synchonizedObject) {
                if (SINGLETON == null) {
                    SINGLETON = new RealmPOCService(context);
                }
            }
        }
        return SINGLETON;

    }

    /**
     * Get the Realm instance for the application Thread
     *
     * @param context
     */
    public RealmPOCService(Context context) {
        realm = Realm.getInstance(context);
        personCRUD = new PersonCRUD();

    }

    /**
     * Create the person on DB
     *
     * @param personToCreate
     * @return the created object
     */
    public Person createPerson(Person personToCreate) {
        return personCRUD.create(personToCreate, realm);
    }

    public void deleteAllPerson() {
        personCRUD.deleteAll(realm);
    }

    /**
     * get all people in DB.
     *
     * @return The ArrayList with all people in DB or an empty arrayList in case of no elements.
     */
    public ArrayList<Person> getAllPerson() {
        return personCRUD.getAll(realm);
    }

    /*
    //no used
    public void createPersonByQuantity(int quantity) {
        for (int i = 0; i < quantity; i++) {
            Person person = new Person();
            person.setName("Person" + (new Random()).nextInt(500));
            person.setAge((new Random()).nextInt(100));
            personCRUD.create(person, realm);
        }

    }*/

    public ArrayList<Person> getByName(String name) {
        return personCRUD.getByName(name, realm);
    }

    public ArrayList<Person> getByAge(int age) {
        return personCRUD.getByAge(age, realm);

    }

    public void delete(Person selectedPerson) {
        personCRUD.delete(selectedPerson, realm);
    }


}
