package com.viseo.talentmap.modifications_skills.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by patiencebayogha on 13/02/15.
 * * This class permit to initialize some values. Later Jackson to convert this to/from JSON
 */
public class UsersAddSkills {

    @JsonProperty("volatile")
    private String isVolatile;


    @JsonProperty("category")
    private String category;


    @JsonProperty("name")
    private String name;


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
        if (!(o instanceof UsersAddSkills)) return false;

        UsersAddSkills that = (UsersAddSkills) o;

        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "UsersAddSkills{" +
                "category='" + category + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

