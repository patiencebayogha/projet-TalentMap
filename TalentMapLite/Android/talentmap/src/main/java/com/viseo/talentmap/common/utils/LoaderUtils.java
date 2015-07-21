package com.viseo.talentmap.common.utils;


/**
 * Classe regroupant des méthodes utiles à la gestion des loaders.
 *
 * @author j.varin
 */
public class LoaderUtils {

    /**
     * Variable de test de synchronisation du singleton.
     */
    private static final Object __synchonizedObject = new Object();
    /**
     * Instance de LoaderUtils.
     */
    private static LoaderUtils sInstance = null;
    /**
     * Id du loader en cours.
     */
    private static int loaderId = 0;

    private LoaderUtils() {
    }

    /**
     * Retourne l'instance de LoaderUtils.
     *
     * @return le singleton.
     */
    public final static LoaderUtils getInstance() {
        if (sInstance == null) {
            synchronized (__synchonizedObject) {
                if (sInstance == null) {
                    sInstance = new LoaderUtils();
                }
            }
        }
        return sInstance;
    }

    /**
     * Retourne un id de loader pas encore utilisé.
     *
     * @return un id de loader.
     */
    public int getLoaderId() {
        return loaderId++;
    }
}
