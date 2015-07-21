package com.viseo.talentmap.common.service;

import com.viseo.talentmap.common.exception.TalentMapException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by patiencebayogha on 26/02/15.
 */
public class UsersService extends AbstractService {

    private final static String USERS = "/users";
    private final static String METHOD_GET = "/get/";
    private final static String METHOD_LIST = "/list";
    private final static String METHOD_ADD_SKILL = "/add-skill";
    private final static String METHOD_UPDATE_SKILL = "/update-skill";
    private final static String METHOD_UPDATE_NAME = "/update-name";
    private final static String METHOD_CREATE = "/create";
    private final static String METHOD_DELETE_SKILL = "/delete-skill";
    private final static String METHOD_ACTIVATE = "/activate";
    private final static String METHOD_UPDATE_PHOTO = "/update-photo";

    public UsersService() {
        super();
    }


    public void create(String parameters) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_CREATE);

        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, parameters);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }

    public InputStream list() throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_LIST);

        try {

            URI uri = new URI(urlBuilder.toString());
            return getData(uri);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }

    public void activate(String parameters) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_ACTIVATE);

        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, parameters);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }


    public void updateSkill(String parameters) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_UPDATE_SKILL);

        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, parameters);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }

    public void updateName(String parameters) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_UPDATE_NAME);
        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, parameters).close();
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        } catch (IOException e) {
            throw new TalentMapException(e);
        }
    }


    public InputStream get(String email) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_GET);
        urlBuilder.append(email);

        try {

            URI uri = new URI(urlBuilder.toString());
            return getData(uri);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }

    public void addSkill(String para) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_ADD_SKILL);

        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, para).close();
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        } catch (IOException e) {
            throw new TalentMapException(e);
        }
    }

    public void deleteSkill(String parameters) throws TalentMapException {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_DELETE_SKILL);

        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, parameters).close();
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        } catch (IOException e) {
            throw new TalentMapException(e);
        }
    }

    public void updatePhoto(String parameters) throws TalentMapException {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(USERS);
        urlBuilder.append(METHOD_UPDATE_PHOTO);

        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, parameters).close();
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        } catch (IOException e) {
            throw new TalentMapException(e);
        }
    }


}
