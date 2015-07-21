package com.example.patiencebayogha.talentmaplitewithvolley.gsonRequest;

/**
 * Created by patiencebayogha on 01/06/15.
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Volley adapter for JSON requests that will be parsed into Java objects by Gson.
 */
public class GsonRequestGet <T> extends Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;   //relevant class object dor Gson's reflection
    private final Map<String, String> headers;   //Map of request headers
    private final Response.Listener<T> listener;
    private Map<String, String> postParams;
    private String postString = null; //Json String
    private final static String REQUEST_SET_COOKIE = "connect.sid	s%3AZh9jGtdn5Y22XqJqXOo95i9JNjnrKRQ8.JA8JsU1HSCrTJpgSM%2FCSKwp2Y2AyaPI%2BQn18IBozHOc"; //header request set cookies
    private final static String SETTING_COOKIE = "set-cookie";

    //Make Post Request and return parsed object from Json
    Map<String,String> params=null;
    public GsonRequestGet(int method, String url, Class<T> clazz, Map<String, String> headers, Map<String,String> params,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;


        if (method == Method.GET && params != null) {
            setRetryPolicy(new DefaultRetryPolicy(12000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            //params="{'email': 'string','password': 'string'}";
          //  postString = new GsonBuilder().create().toJson(params);
        }
    }

    public GsonRequestGet(String url, Class<T> clazz, Map<String, String> headers, JSONObject params,
                            Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.clazz = clazz;
        this.headers = headers;
        this.listener = listener;


    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("connect.sid", REQUEST_SET_COOKIE);
        Log.d(getClass().getCanonicalName(), REQUEST_SET_COOKIE + " : " + REQUEST_SET_COOKIE);
        VolleyLog.d(getClass().getCanonicalName(), SETTING_COOKIE + " : " + SETTING_COOKIE);

        return headers != null ? headers : super.getHeaders();

    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        return postString != null ? postString.getBytes(Charset
                .forName("UTF-8")) : super.getBody();
    }

    @Override
    public String getBodyContentType() {
        return postString != null ? "application/json; charset=utf-8" : super.getBodyContentType();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        return postParams != null ? postParams : super.getParams();
    }



}
