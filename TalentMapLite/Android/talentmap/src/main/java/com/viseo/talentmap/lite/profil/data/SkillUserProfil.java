package com.viseo.talentmap.lite.profil.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by patiencebayogha on 12/03/15.
 * This class permit to initialize some values. Later Jackson to convert this to/from JSON
 */
public class SkillUserProfil {

    @JsonProperty("category")
    private String category;

    @JsonProperty("level")
    private int level;

    @JsonProperty("skill")
    private String skill;

    @JsonProperty("_id")
    private String id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SkillUserProfil)) return false;

        SkillUserProfil that = (SkillUserProfil) o;

        if (level != that.level) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        if (skill != null ? !skill.equals(that.skill) : that.skill != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + level;
        result = 31 * result + (skill != null ? skill.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SkillUserProfil{" +
                "category='" + category + '\'' +
                ", level='" + level + '\'' +
                ", skill='" + skill + '\'' +
                ", _id='" + id + '\'' +
                '}';
    }


}