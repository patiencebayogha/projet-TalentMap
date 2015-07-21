package com.viseo.talentmap.common.loader;

import android.app.Activity;
import android.content.AsyncTaskLoader;

/**
 * Loader abstrait qui contient les actions communes aux loaders.
 *
 * @param <D> La classe de retour du loader.
 */
public abstract class AbstractLoader<D> extends AsyncTaskLoader<D> {

    /**
     * Le résultat du loader
     */
    protected D result;

    /**
     * Le contexte applicatif.
     */
    protected Activity activity;

    /**
     * Constructeur par défaut.
     *
     * @param activity Le contexte
     */
    public AbstractLoader(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    /*
     * (non-Javadoc)
     *
     * @see android.support.v4.content.Loader#onStartLoading()
     */
    @Override
    protected void onStartLoading() {
        if (result != null) {
            deliverResult(result);
        }

        if (takeContentChanged() || result == null) {
            forceLoad();
        }
    }


}
