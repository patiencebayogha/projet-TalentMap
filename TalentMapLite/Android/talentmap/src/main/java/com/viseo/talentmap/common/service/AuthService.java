package com.viseo.talentmap.common.service;

import com.viseo.talentmap.common.exception.TalentMapException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by patiencebayogha on 18/02/15.
 */
public class AuthService extends AbstractService {

    private final static String AUTH = "/auth";
    private final static String METHOD_LOGIN = "/login";
    private final static String METHOD_ISLOGGED = "/islogged";
    private final static String METHOD_GETME = "/getme";

    public AuthService() {
        super();
    }

    public void login(String param) throws TalentMapException {

        connexionService.setCookie(null);
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(AUTH);
        urlBuilder.append(METHOD_LOGIN);

        try {

            URI uri = new URI(urlBuilder.toString());
            postData(uri, param).close();
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        } catch (IOException e) {
            throw new TalentMapException(e);
        }
    }


    public InputStream isLogged() throws TalentMapException {

        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(AUTH);
        urlBuilder.append(METHOD_ISLOGGED);

        try {

            URI uri = new URI(urlBuilder.toString());
            return getData(uri);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }


    public InputStream getMe() throws TalentMapException {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(SERVER_URL_API);
        urlBuilder.append(AUTH);
        urlBuilder.append(METHOD_GETME);

        try {

            URI uri = new URI(urlBuilder.toString());
            return getData(uri);
        } catch (URISyntaxException e) {
            throw new TalentMapException("Uri not valid : " + urlBuilder, e);
        }
    }

}
