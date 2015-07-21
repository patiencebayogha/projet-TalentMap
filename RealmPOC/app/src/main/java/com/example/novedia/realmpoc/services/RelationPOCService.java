package com.example.novedia.realmpoc.services;

import android.content.Context;

import com.example.novedia.realmpoc.dao.ObjectCRUD;
import com.example.novedia.realmpoc.dao.PersonCRUD;
import com.example.novedia.realmpoc.model.ModelObject;
import com.example.novedia.realmpoc.model.Person;

import java.util.ArrayList;

import io.realm.Realm;

/**
 *
 * Created by patiencebayogha on 12/05/15.
 */
public class RelationPOCService {
    private Realm realm;
    private ObjectCRUD objectCRUD;
    private PersonCRUD personCRUD;

    /**
     * Get the single instance of the Realm Data base service
     *
     * @param context
     * @return the
     * <p/>
     * The singleton.
     */
    private static RelationPOCService SINGLETON;

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
    public static RelationPOCService getInstance(Context context) {

        if (SINGLETON == null) {
            synchronized (__synchonizedObject) {
                if (SINGLETON == null) {
                    SINGLETON = new RelationPOCService(context);
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
    public RelationPOCService(Context context) {
        realm = Realm.getInstance(context);
        objectCRUD = new ObjectCRUD();
        personCRUD = new PersonCRUD();
    }

    public ModelObject createBook(ModelObject modelObject) {
        return objectCRUD.create(modelObject, realm);
    }

    public ModelObject addBookToPerson(Person person, ModelObject modelObject) {
        ModelObject object = createBook(modelObject);
        personCRUD.addObject(person, object, realm);
        return object;
    }

    public void addArrayBooksToPerson(Person person, ArrayList<ModelObject> modelObjects) {
        ArrayList<ModelObject> objects = createBooks(modelObjects);
        personCRUD.addArrayObjects(person, objects, realm);
    }

    private ArrayList<ModelObject> createBooks(ArrayList<ModelObject> modelObjects) {
        return objectCRUD.create(modelObjects, realm);
    }

    public ArrayList<ModelObject> getAllObjects() {
        return objectCRUD.getAll(realm);
    }

    public ArrayList<ModelObject> getObjectsByCriteria(String personName, String objectName, String price) throws NumberFormatException {
        return personCRUD.getObjectsByCriteria(personName, objectName, price, realm);
    }

    /**
     * get all people in DB.
     *
     * @return The ArrayList with all people in DB or an empty arrayList in case of no elements.
     */
    public ArrayList<Person> getAllPerson() {
        return personCRUD.getAll(realm);
    }


    public void delete(ModelObject modelObjects) {
        objectCRUD.delete(modelObjects, realm);
    }


    public void deleteAllObject() {
        objectCRUD.deleteAll(realm);
    }

    public ArrayList<ModelObject> getByPrice(int price) {
        return objectCRUD.getByPrice(price, realm);

    }

    public ArrayList<ModelObject> getByName(String name) {
        return objectCRUD.getByName(name, realm);
    }

}
