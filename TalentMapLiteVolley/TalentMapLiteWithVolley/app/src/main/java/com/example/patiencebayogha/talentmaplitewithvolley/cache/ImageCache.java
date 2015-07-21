package com.example.patiencebayogha.talentmaplitewithvolley.cache;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that manages the images cache
 */
public class ImageCache {

    private static ImageCache _instance = null;
    private final Object _lock = new Object();
    private HashMap<String, WeakReference<Drawable>> _cache;
    private HashMap<String, List<ImageCallback>> _callbacks;

    public ImageCache() {
        _cache = new HashMap<String, WeakReference<Drawable>>();
        _callbacks = new HashMap<String, List<ImageCallback>>();
    }

    public synchronized static ImageCache getInstance() {
        if (_instance == null) {
            _instance = new ImageCache();
        }
        return _instance;
    }

    private String getHash(String url) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(url.getBytes());
            return new BigInteger(digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            return url;
        }
    }

    private Drawable drawableFromCache(String url, String hash) {
        Drawable d = null;
        synchronized (_lock) {
            if (_cache.containsKey(hash)) {
                WeakReference<Drawable> ref = _cache.get(hash);
                if (ref != null) {
                    d = ref.get();
                    if (d == null)
                        _cache.remove(hash);
                }
            }
        }
        return d;
    }

    private Drawable loadSync(String url, String hash, Context context) {
        Drawable d = null;
        try {
            d = drawableFromCache(url, hash);
            File f = new File(context.getCacheDir(), hash);
            if (d == null) {

                if (!f.exists()) {
                    InputStream is = new ImageService().getResult(url);

                    if (f.createNewFile()) {
                        FileOutputStream fo = new FileOutputStream(f);
                        byte[] buffer = new byte[256];
                        int size;
                        while ((size = is.read(buffer)) > 0)
                            fo.write(buffer, 0, size);
                        fo.flush();
                        fo.close();
                    }
                }
                d = Drawable.createFromPath(f.getAbsolutePath());
                synchronized (_lock) {
                    _cache.put(hash, new WeakReference<Drawable>(d));
                }
            }
        } catch (Exception ex) {
            return null;
        } catch (OutOfMemoryError ex) {
            return null;
        }
        return d;
    }

    public void loadAsync(final String url, final ImageCallback callback,
                          final Context context) {
        final String hash = getHash(url);
        synchronized (_lock) {
            List<ImageCallback> callbacks = _callbacks.get(hash);
            if (callbacks != null) {
                if (callback != null)
                    callbacks.add(callback);
                return;
            }

            callbacks = new ArrayList<ImageCallback>();
            if (callback != null)
                callbacks.add(callback);
            _callbacks.put(hash, callbacks);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Drawable d = loadSync(url, hash, context);
                List<ImageCallback> callbacks;
                synchronized (_lock) {
                    callbacks = _callbacks.remove(hash);
                }
                for (ImageCallback iter : callbacks) {
                    if (d != null)
                        iter.onImageLoaded(d, url);
                    else
                        iter.onDownloadFailed(d, url);
                }
            }
        }, "ImageCache loader: " + url).start();
    }

    public static interface ImageCallback {
        /**
         * Treatment when the download succeed
         *
         * @param image the drawable
         * @param url
         */
        void onImageLoaded(Drawable image, String url);

        /**
         * Treatment when the download failed
         *
         * @param image the drawable
         * @param url   the url
         */
        void onDownloadFailed(Drawable image, String url);
    }
}