package com.viseo.talentmap.common.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Traite les objets pour les convertir en flux JSON.
 * Convertit les objets en JSON et mes sauvegarde dans le fichier JSON
 */
public class JSONStreamUtils {

    /**
     * L'objet permettant le mapping.
     */
    private ObjectMapper mapper;

    /**
     * Le constructeur.
     */
    public JSONStreamUtils() {

        this.mapper = new ObjectMapper();

        this.mapper
                .configure(
                        DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,
                        true);
        this.mapper.configure(
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        this.mapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true); // true
        this.mapper.configure(
                DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);


    }

    /**
     * Convertit l'objet passé en paramètre en flux JSON. Si une erreur se
     * produit, la méthode renvoie une chaine vide.
     *
     * @param object L'objet à convertir.
     * @return Le flux JSON associé à l'objet.
     */
    public String getJSON(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (JsonGenerationException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot generate JSON.", e);
        } catch (JsonMappingException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot map object to JSON.",
                    e);
        } catch (IOException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot read object.", e);
        }

        return new String();
    }

    /**
     * Convertit le flux JSON passé en paramètre en objet métier.
     *
     * @param <T>         the generic type
     * @param inputStream Flux JSON.
     * @param objectType  Type de l'objet.
     * @return L'objet associé au JSON.
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(InputStream inputStream, TypeReference<T> objectType) {

        try {
            return (T) this.mapper.readValue(inputStream, objectType);
        } catch (JsonParseException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot parse json stream.", e);
        } catch (JsonMappingException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot map object to JSON.",
                    e);
        } catch (IOException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot read object.", e);
        }
        return null;
    }

    /**
     * Convertit le flux JSON passé en paramètre en objet métier.
     *
     * @param <T>        the generic type
     * @param string     the string
     * @param objectType Type de l'objet.
     * @return L'objet associé au JSON.
     */
    public <T> T getObject(String string, TypeReference<T> objectType) {

        try {
            return (T) this.mapper.readValue(string, objectType);
        } catch (JsonParseException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot parse json stream.", e);
        } catch (JsonMappingException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot map object to JSON.",
                    e);
        } catch (IOException e) {
            LogUtils.getInstance().appendLog(getClass().getCanonicalName(), "Cannot read object.", e);
        }

        return null;
    }

}
