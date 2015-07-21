package com.example.patiencebayogha.talentmaplitewithvolley.data;

/**
 * Created by patiencebayogha on 12/02/15.
 * modification:13/02/2015
 * POJO class for conversion to JSON
 * Same Model that JSON Email and password of the user
 * an user object is initialized with some values. We use Jackson
 * later to convert this object JSON
 * <p/>
 * * This class permit to initialize some values. Later Jackson to convert this to/from JSON
 */
public class LoginUser {

    /**
     * User login
     */
    public String email;

    /**
     * User password
     */
    public String password;


    /**
     * Base construct getters, setters
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginUser)) return false;

        LoginUser loginUser = (LoginUser) o;

        if (email != null ? !email.equals(loginUser.email) : loginUser.email != null) return false;
        if (password != null ? !password.equals(loginUser.password) : loginUser.password != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
