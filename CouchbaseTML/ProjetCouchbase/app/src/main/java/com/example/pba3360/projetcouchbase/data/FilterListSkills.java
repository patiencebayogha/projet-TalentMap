package com.example.pba3360.projetcouchbase.data;

/**
 * Created by patiencebayogha on 02/04/15.
 */

/**
 * Created by patiencebayogha on 30/03/15.
 * 01/04/15
 * In the user interface there has to be a spinner which contains some
 * names (the names are visible) and each name has its own ID (the IDs are
 * not equal to display sequence). When the user selects the name from the list
 * the variable currentID has to be changed.
 */
public class FilterListSkills {


    private String isVolatile;
    private String id;
    private String category;
    private String name;


    public String getIsVolatile() {

        return isVolatile;
    }

    public void setIsVolatile(String isVolatile) {
        this.isVolatile = isVolatile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FilterListSkills)) return false;

        FilterListSkills that = (FilterListSkills) o;

        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isVolatile != null ? !isVolatile.equals(that.isVolatile) : that.isVolatile != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = isVolatile != null ? isVolatile.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilterlistSkills{" +
                "isVolatile='" + isVolatile + '\'' +
                ", id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
