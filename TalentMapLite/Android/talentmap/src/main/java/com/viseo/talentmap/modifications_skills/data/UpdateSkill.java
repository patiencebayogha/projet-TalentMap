package com.viseo.talentmap.modifications_skills.data;

/**
 * Created by patiencebayogha on 30/03/15.
 * This class permit to initialize some values. Later Jackson to convert this to/from JSON
 */
public class UpdateSkill {


    private String level;

    private String skill;

    private String email;


    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
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
        if (!(o instanceof UpdateSkill)) return false;

        UpdateSkill that = (UpdateSkill) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (level != null ? !level.equals(that.level) : that.level != null) return false;
        if (skill != null ? !skill.equals(that.skill) : that.skill != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = level != null ? level.hashCode() : 0;
        result = 31 * result + (skill != null ? skill.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "UpdateSkill{" +
                "level='" + level + '\'' +
                ", skill='" + skill + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
