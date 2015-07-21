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

public class ModelObjectRealmProxy extends ModelObject {

    @Override
    public String getName() {
        return (java.lang.String) row.getString(Realm.columnIndices.get("ModelObject").get("name"));
    }

    @Override
    public void setName(String value) {
        row.setString(Realm.columnIndices.get("ModelObject").get("name"), (String) value);
    }

    @Override
    public int getPrice() {
        return (int) row.getLong(Realm.columnIndices.get("ModelObject").get("price"));
    }

    @Override
    public void setPrice(int value) {
        row.setLong(Realm.columnIndices.get("ModelObject").get("price"), (long) value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if(!transaction.hasTable("class_ModelObject")) {
            Table table = transaction.getTable("class_ModelObject");
            table.addColumn(ColumnType.STRING, "name");
            table.addColumn(ColumnType.INTEGER, "price");
            return table;
        }
        return transaction.getTable("class_ModelObject");
    }

    public static void validateTable(ImplicitTransaction transaction) {
        if(transaction.hasTable("class_ModelObject")) {
            Table table = transaction.getTable("class_ModelObject");
            if(table.getColumnCount() != 2) {
                throw new IllegalStateException("Column count does not match");
            }
            Map<String, ColumnType> columnTypes = new HashMap<String, ColumnType>();
            for(long i = 0; i < 2; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }
            if (!columnTypes.containsKey("name")) {
                throw new IllegalStateException("Missing column 'name'");
            }
            if (columnTypes.get("name") != ColumnType.STRING) {
                throw new IllegalStateException("Invalid type 'String' for column 'name'");
            }
            if (!columnTypes.containsKey("price")) {
                throw new IllegalStateException("Missing column 'price'");
            }
            if (columnTypes.get("price") != ColumnType.INTEGER) {
                throw new IllegalStateException("Invalid type 'int' for column 'price'");
            }
        }
    }

    public static List<String> getFieldNames() {
        return Arrays.asList("name", "price");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ModelObject = [");
        stringBuilder.append("{name:");
        stringBuilder.append(getName());
        stringBuilder.append("} ");
        stringBuilder.append("{price:");
        stringBuilder.append(getPrice());
        stringBuilder.append("} ");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        int result = 17;
        String aString_0 = getName();
        result = 31 * result + (aString_0 != null ? aString_0.hashCode() : 0);
        result = 31 * result + getPrice();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelObjectRealmProxy aModelObject = (ModelObjectRealmProxy)o;
        if (getName() != null ? !getName().equals(aModelObject.getName()) : aModelObject.getName() != null) return false;
        if (getPrice() != aModelObject.getPrice()) return false;
        return true;
    }

}
