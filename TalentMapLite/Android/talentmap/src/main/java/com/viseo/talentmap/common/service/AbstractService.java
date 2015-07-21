package com.viseo.talentmap.common.service;

import android.util.Log;

import com.viseo.talentmap.common.exception.TalentMapAuthenticateException;
import com.viseo.talentmap.common.exception.TalentMapConnectionException;
import com.viseo.talentmap.common.exception.TalentMapException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.URI;


/**
 * Le service permet de construire les différentes URLs d'appel aux Web Service Sisley et de récupérer le flux d'information associé.
 */
public abstract class AbstractService {

    protected final static String SERVER_URL_API = "http://tml.hubi.org:80/api/v1";
    private final static String SETTING_COOKIE = "set-cookie";
    private final static String REQUEST_SET_COOKIE = "COOKIE"; //header request set cookies
    protected ConnectionService connexionService;

    public AbstractService() {
        connexionService = ConnectionService.getConnectionService();
    }

    public HttpClient getHttpClient() {
        return connexionService.getHttpClient();
    }

    /**
     * Retrieve data.
     *
     * @param request the request
     * @param params  the params
     * @return the input stream
     * @throws TalentMapException the po s exception
     */
    protected InputStream retrieveData(HttpRequestBase request, String params) throws TalentMapException {

        InputStream result = null;
        HttpClient httpClient = getHttpClient();

        try {

            request.addHeader("Accept", "application/json");
            request.addHeader("Content-Type", "application/json;charset=utf-8");


            String sessionCookie = connexionService.getCookie();
            if (connexionService.getCookie() != null) {
                Log.d(getClass().getCanonicalName(), SETTING_COOKIE + " : " + sessionCookie);
                request.setHeader(REQUEST_SET_COOKIE, sessionCookie);

            } else {
                Log.i(getClass().getCanonicalName(), "Null session request get()");
            }

            if ((params != null) && (params.length() > 0)) {
                if (HttpPut.class.isInstance(request))
                    ((HttpPut) request).setEntity(new StringEntity(params, HTTP.UTF_8));
                if (HttpPost.class.isInstance(request))
                    ((HttpPost) request).setEntity(new StringEntity(params, HTTP.UTF_8));
            }
            Log.d("Tag", "===========>" + request.getURI().toString() + "," + params);

            // Récupération du résultat.
            HttpResponse httpResponse = httpClient.execute(request);
            Header[] headers = httpResponse.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                Header h = headers[i];
                if (h.getName().equals(SETTING_COOKIE)) {
                    HeaderElement[] elements = h.getElements();
                    for (int j = 0; j < elements.length; j++) {
                        HeaderElement element = elements[j];
                        if (element.getName().equals("connect.sid"))
                            connexionService.setCookie("connect.sid=" + element.getValue());

                    }
                }
            }

            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            if (statusCode != 200) {
                if (entity != null) {
                    entity.getContent().close();
                }
            }
            switch (statusCode) {
                case 200:
                    result = entity.getContent();
                    break;
                case 401:
                case 403:
                    throw new TalentMapAuthenticateException(httpResponse.getStatusLine().getReasonPhrase());
                case 404:
                    throw new TalentMapConnectionException(httpResponse.getStatusLine().getReasonPhrase());
                case 500:
                    throw new TalentMapConnectionException(httpResponse.getStatusLine().getReasonPhrase());
                default:
                    throw new TalentMapException(httpResponse.getStatusLine().getReasonPhrase());
            }
        } catch (ConnectTimeoutException e) {
            throw new TalentMapConnectionException("Connection time out.");
        } catch (SocketTimeoutException e) {
            throw new TalentMapConnectionException("Socket time out.");
        } catch (IOException e) {
            throw new TalentMapConnectionException("IO exception.");
        }

        return result;
    }

    /**
     * Renvoie les données retournées par l'URL passée en paramètre.
     *
     * @param uri L'URL à appeler
     * @return Le flux d'information contenu dans la réponse de l'URL.
     * @throws TalentMapException the pos exception
     */
    protected InputStream getData(URI uri) throws TalentMapException {

        HttpGet httpGet = new HttpGet(uri);
        return retrieveData(httpGet, null);
    }

    /**
     * Put data.
     *
     * @param uri    the uri
     * @param params the params
     * @return the input stream
     * @throws TalentMapException the po s exception
     */
    protected InputStream putData(URI uri, String params) throws TalentMapException {

        HttpPut httpPut = new HttpPut(uri);
        return retrieveData(httpPut, params);
    }

    /**
     * Post data.
     *
     * @param uri    the uri
     * @param params the params
     * @throws TalentMapException the po s exception
     */
    protected InputStream postData(URI uri, String params) throws TalentMapException {

        HttpPost httpPost = new HttpPost(uri);
        Log.e("params", params);
        return retrieveData(httpPost, params);

    }

    /**
     * Delete data.
     *
     * @param uri the uri
     * @throws TalentMapException the po s exception
     */
    protected void deleteData(URI uri) throws TalentMapException {

        HttpDelete httpDelete = new HttpDelete(uri);
        retrieveData(httpDelete, null);
    }

}
