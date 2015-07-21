package com.example.pba3360.projetcouchbase.data;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by patiencebayogha on 24/02/15.
 * 12/03/2015
 * * This class permit to initialize some values. Later Jackson to convert this to/from JSON
 */
public class SearchUser extends JSONObject implements Serializable {


    @SerializedName("skills")
    protected ArrayList<SearchSkillsUsers> skills;

    @SerializedName("email")
    private String email;

    @SerializedName("surname")
    private String surname;

    @SerializedName("active")
    private Boolean active;

    @SerializedName("photo")
    private String photo;

    @SerializedName("name")
    private String name;

    @SerializedName("password")
    private String password;


    public SearchUser() {
    }


    public SearchUser(String email, String photo, String surname, Boolean active, String name, String password,
                      ArrayList<SearchSkillsUsers> skills) {
        this.email = email;
        this.photo = photo;
        this.surname = surname;
        this.active = active;
        this.name = name;
        this.password = password;
        this.skills = skills;
    }

    //getters and setters
    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email) {
        this.email = email;
        return false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<SearchSkillsUsers> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<SearchSkillsUsers> skills) {
        this.skills = skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchUser)) return false;

        SearchUser that = (SearchUser) o;


        if (active != null ? !active.equals(that.active) : that.active != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null)
            return false;
        if (photo != null ? !photo.equals(that.photo) : that.photo != null) return false;
        if (skills != null ? !skills.equals(that.skills) : that.skills != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = skills != null ? skills.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        result = 31 * result + (photo != null ? photo.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SearchUser{" +
                "skills=" + skills +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", active=" + active +
                ", photo='" + photo + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

