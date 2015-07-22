package com.example.patiencebayogha.talentmapwithCouchbase.service;

/**
 * Created by patiencebayogha on 26/05/15.
 */

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;


/**
 * Le service gère le pool de connexions au Web Service.
 */
public class ConnectionService {

    /**
     * L'instance unique du service.
     */
    private static ConnectionService SINGLETON = new ConnectionService();
    private String cookie;
    /**
     * Le gestionnaire de connexion.
     */
    private ClientConnectionManager connexionManager;

    /**
     * Les paramètres HTTP.
     */
    private HttpParams httpParameters;

    /**
     * Le constructeur privé du service.
     */
    private ConnectionService() {

        this.httpParameters = new BasicHttpParams();
        HttpProtocolParams
                .setVersion(this.httpParameters, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(this.httpParameters, "UTF-8");
        this.httpParameters.setBooleanParameter(
                "http.protocol.expect-continue", false);
        int timeoutConnection = 10000;
        HttpConnectionParams.setConnectionTimeout(this.httpParameters,
                timeoutConnection);
        int timeoutSocket = 30000;
        final SSLSocketFactory sslSocketFactory = SSLSocketFactory
                .getSocketFactory();
        sslSocketFactory
                .setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        HttpConnectionParams.setSoTimeout(this.httpParameters, timeoutSocket);

        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        registry.register(new Scheme("https", sslSocketFactory, 443));

        this.connexionManager = new ThreadSafeClientConnManager(httpParameters,
                registry);

    }

    /**
     * Méthode statique qui retourne l'instance unique du service.
     *
     * @return Le service de connexion.
     */
    public static ConnectionService getConnectionService() {
        return SINGLETON;
    }

    /**
     * Retourne un client de connexion issue du pool du connexion.
     *
     * @return Un client HTTP issue du pool de connexion.
     */
    public HttpClient getHttpClient() {
        return new DefaultHttpClient(this.connexionManager, this.httpParameters);
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
