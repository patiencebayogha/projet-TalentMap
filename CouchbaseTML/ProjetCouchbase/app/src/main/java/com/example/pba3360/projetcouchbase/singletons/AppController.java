package com.example.pba3360.projetcouchbase.singletons;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.pba3360.projetcouchbase.utils.LruBitmapCache;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Created by patiencebayogha on 26/05/15.
 * this class permit to create a singleton that that will
 * return on demand an instance of RequestQueue and one of ImageLoader.
 */
public class AppController extends Application {

    /**
     * Log or request TAG
     */
    public static final String TAG = AppController.class.getSimpleName();

    /*
    *http client instance
    */
    DefaultHttpClient mHttpClient;

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    /*
    *for imageLoader
    */
    private ImageLoader mImageLoader;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static AppController mInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        // initialize the singleton
        mInstance = this;
    }

    /**
     * @return ApplicationController singleton instance
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */

    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
        // Create an instance of the Http client.
        // We need this in order to access the cookie store
            mHttpClient = new DefaultHttpClient();
        // create the request queue
            mRequestQueue = Volley.newRequestQueue(this, new HttpClientStack(mHttpClient));

        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }


    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }


    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }


    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
