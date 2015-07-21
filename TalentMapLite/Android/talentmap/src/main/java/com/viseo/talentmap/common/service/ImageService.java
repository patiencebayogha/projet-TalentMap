package com.viseo.talentmap.common.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import java.io.InputStream;
import java.net.URI;

/**
 * Service for download picture
 *
 * @author b.paques
 */
public class ImageService {

    public InputStream getResult(String url) throws Exception {
        HttpGet httpGet;

        try {
            URI uri = new URI(url.replace(" ", "%20"));
            httpGet = new HttpGet(uri);
            return callWebService(httpGet);
        } catch (Exception e) {
            return null;
        }
    }

    private InputStream callWebService(HttpRequestBase request) {

        try {

            HttpClient httpClient = ConnectionService.getConnectionService().getHttpClient();

            HttpResponse httpResponse = httpClient.execute(request);

            HttpEntity httpEntity = httpResponse.getEntity();

            InputStream inputStream = httpEntity.getContent();

            return inputStream;

        } catch (Exception e) {
            return null;
        }
    }



}
