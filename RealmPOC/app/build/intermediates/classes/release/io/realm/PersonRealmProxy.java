package io.realm;


import com.example.novedia.realmpoc.model.*;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.internal.ColumnType;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.*;

public class PersonRealmProxy extends Person {

    @Override
    public String getName() {
        return (java.lang.String) row.getString(Realm.columnIndices.get("Person").get("name"));
    }

    @Override
    public void setName(String value) {
        row.setString(Realm.columnIndices.get("Person").get("name"), (String) value);
    }

    @Override
    public int getAge() {
        return (int) row.getLong(Realm.columnIndices.get("Person").get("age"));
    }

    @Override
    public void setAge(int value) {
        row.setLong(Realm.columnIndices.get("Person").get("age"), (long) value);
    }

    @Override
    public RealmList<com.example.novedia.realmpoc.model.ModelObject> getObjects() {
        return new RealmList(ModelObject.class, row.getLinkList(Realm.columnIndices.get("Person").get("objects")), realm);
    }

    @Override
    public void setObjects(RealmList<com.example.novedia.realmpoc.model.ModelObject> value) {
        LinkView links = row.getLinkList(Realm.columnIndices.get("Person").get("objects"));
        if (value == null) {
            return;
        }
        for (RealmObject linkedObject : (RealmList<? extends RealmObject>) value) {
            links.add(linkedObject.row.getIndex());
        }
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if(!transaction.hasTable("class_Person")) {
            Table table = transaction.getTable("class_Person");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.INTEGER, "age");
            if (!transaction.hasTable("class_ModelObject")) {
                ModelObjectRealmProxy.initTable(transaction);
            }
            table.addColumnLink(ColumnType.LINK_LIST, "objects", transaction.getTable("class_ModelObject"));
            return table;
        }
        return transaction.getTable("class_Person");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if(transaction.hasTable("class_Person")) {
            Table table = transaction.getTable("class_Person");
            if(table.getColumnCount() != 3) {
                throw new IllegalStateException("Column count does not match");
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for(long i = 0; i < 3; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }
            if (!columnTypes.containsKey("name")) {
                throw new IllegalStateException("Missing column 'name'");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new IllegalStateException("Invalid type 'String' for column 'name'");
            }
            if (!columnTypes.containsKey("age")) {
                throw new IllegalStateException("Missing column 'age'");
            }
            if (columnTypes.get("age") != ColumnType.INTEGER) {
                throw new IllegalStateException("Invalid type 'int' for column 'age'");
            }
            if(!columnTypes.containsKey("objects")) {
                throw new IllegalStateException("Missing column 'objects'");
            }
            if(columnTypes.get("objects") != ColumnType.LINK_LIST) {
                throw new IllegalStateException("Invalid type 'ModelObject' for column 'objects'");
            }
            if (!transaction.hasTable("class_ModelObject")) {
                throw new IllegalStateException("Missing table 'class_ModelObject' for column 'objects'");
            }
        }
    }

    public static List<String> getFieldNames() {
        return Arrays.asList("name", "age", "objects");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Person = [");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("} ");
        stringBuilder.append("{age:");
        stringBuilder.append(getAge());
        stringBuilder.append("} ");
        stringBuilder.append("{objects:");
        stringBuilder.append(getObjects());
        stringBuilder.append("} ");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int result = 17;
        String aString_0 = getName();
        result = 31 * result + (aString_0 != null ? aString_0.hashCode() : 0);
        result = 31 * result + getAge();
        io.realm.RealmList<com.example.novedia.realmpoc.model.ModelObject> temp_2 = getObjects();
        result = 31 * result + (temp_2 != null ? temp_2.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonRealmProxy aPerson = (PersonRealmProxy)o;
        if (getName() != null ? !getName().equals(aPerson.getName()) : aPerson.getName() != null) return false;
        if (getAge() != aPerson.getAge()) return false;
        if (getObjects() != null ? !getObjects().equals(aPerson.getObjects()) : aPerson.getObjects() != null) return false;
        return true;
    }

}
