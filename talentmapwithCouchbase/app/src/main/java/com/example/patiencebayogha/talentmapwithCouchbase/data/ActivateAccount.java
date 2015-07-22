package com.example.patiencebayogha.talentmapwithCouchbase.data;

/**
 * Created by patiencebayogha on 27/04/15.
 */
public class ActivateAccount {



    private String activationId;


    private String email;

    public ActivateAccount(String email, String activationId) {
        super();
        this.email = email;
        this.activationId = activationId;
    }

    public String getActivationId() {
        return activationId;
    }

    public void setActivationId(String activationId) {
        this.activationId = activationId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActivateAccount)) return false;

        ActivateAccount that = (ActivateAccount) o;

        if (activationId != null ? !activationId.equals(that.activationId) : that.activationId != null)
            return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activationId != null ? activationId.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActivateAccount{" +
                "activationId='" + activationId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
