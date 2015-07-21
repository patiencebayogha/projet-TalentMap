package com.viseo.talentmap.common.service;

import com.viseo.talentmap.common.exception.TalentMapException;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by patiencebayogha on 26/02/15.
 */
public class SkillsService extends AbstractService {

    private final static String SKILLS = "/skills";
    private final static String METHOD_GET = "/get/";
    private final static String METHOD_LIST = "/list";
    private final static String METHOD_LIST_CATEGORIE = "/list/";


    public InputStream get(String parameters) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(SKILLS);
        urlBuilder.append(METHOD_GET);
        urlBuilder.append(parameters);
        try {

            URI uri = new URI(urlBuilder.toString());
            return getData(uri);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }


    public InputStream list() throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(SKILLS);
        urlBuilder.append(METHOD_LIST);

        try {

            URI uri = new URI(urlBuilder.toString());
            return getData(uri);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }


    public InputStream listCategory(String category) throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(SKILLS);
        urlBuilder.append(METHOD_LIST_CATEGORIE);
        urlBuilder.append(category);
        try {

            URI uri = new URI(urlBuilder.toString());
            return getData(uri);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }
}
