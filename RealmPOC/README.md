RealmPOC
===

##Description
The porpose of this project is to discover functionality of Realm Data Base. In this way, this is an application which has two activities: 
* MainActivity: This activity calls the basic CRUD operations for an object "Person". 
By default the application will load all people into ListView bellow.
You can get all people, insert a new person (by default with name "Maria" and random age), inser several people (name "Person "+RandomNumber and random age), delete a person (the first one on DB), delete all people and simple queries as get a person by name or get a person by age.  
The blue label shows the selected person.
To access to this activity you can press the fakeTab "RealmPoc" at left upper corner.

* RelationActivity: Test the relation between several Objects. We thought a relation one to many between object "Person" and "ModelObject". Thus, one person have zero or many ModelObjects and one ModelObject is owned only for one Person.
By default the application will load all objects into ListView bellow.
You can get all objects, get the objects of a selected person (by default the first one) or search the objects related with a person, filtering by person name, object name or price.
The blue label shows the selected Person.
To access to this activity you can press the fakeTab "Relation" at right upper corner.

##Architecture
The application flow is done as bellow: 
   Activity -> Services -> dao.
   
Activities call the ApplicationPOCService which is a singleton class with the Realm DB instance (Note we use the same instance for doing all operations).
The service uses the Realm instance as parameter to call dao operations.
In Dao, to perform each operation you must start a transaction, implement functionality and commit at the end of operation in order to save changes at DB.

##Evolution
Actually, the application uses the mapped objects (objects in model) thought all layers. It should be better use POJOs instead them.
General CRUD is an abstract class which may be used for all classes in "dao", in order to avoid redoing same implementation 
The application has a unique service which does all operations. May be better separate the services?

For further information about Realm go to http://realm.io/
