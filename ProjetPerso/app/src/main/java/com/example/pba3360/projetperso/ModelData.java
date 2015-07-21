package com.example.pba3360.projetperso;

/**
 * Created by PBA3360 on 25/06/2015.
 */
public class ModelData {
    private String nom;
    private String text;


        public ModelData(String title,String imageUrl){

            this.nom = title;
            this.text = imageUrl;
        }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ModelData{" +
                "nom='" + nom + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelData modelData = (ModelData) o;

        if (nom != null ? !nom.equals(modelData.nom) : modelData.nom != null) return false;
        return !(text != null ? !text.equals(modelData.text) : modelData.text != null);

    }

    @Override
    public int hashCode() {
        int result = nom != null ? nom.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
