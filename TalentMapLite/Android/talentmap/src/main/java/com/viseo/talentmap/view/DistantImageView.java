package com.viseo.talentmap.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;


/**
 * View that downloads and show image from internet
 */
public class DistantImageView extends ImageView implements ImageCache.ImageCallback {


    /**
     * the url image to download
     */
    private String url = null;

    /**
     * The default image
     */
    private Drawable defaultImage;

    /**
     * The handler to post runnables in the main thread
     */
    private Handler handler;


    /**
     * Constructor
     *
     * @param context the context
     */
    public DistantImageView(Context context) {
        super(context);
    }

    /**
     * Constructor
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public DistantImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor
     *
     * @param context  the context
     * @param attrs    the attributes
     * @param defStyle style definition
     */
    public DistantImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Gets the handler
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * Sets the handler
     *
     * @param handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * Sets the url of the image to download
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
        if ((this.url != null) && (this.url.length() > 0)) {
            ImageCache.getInstance().loadAsync(this.url, this,
                    this.getContext());

        } else {
            if (defaultImage != null) {
                this.setImageDrawable(defaultImage);
            }
        }
    }

    /**
     * The default image
     *
     * @param defaultImage the default image
     */
    public void setDefaultImage(Drawable defaultImage) {
        this.defaultImage = defaultImage;
    }

    /**
     * Treatment when the dowload succeed
     *
     * @param image the drawable
     * @param url
     */
    public void onImageLoaded(final Drawable image, String url) {

        if (getHandler() == null) {
            return;
        }

        if (this.url.equalsIgnoreCase(url)) {
            getHandler().post(new Runnable() {
                public void run() {

                    // layout_width == MATCH_PARENT && scaleType fitXY
                    if (getScaleType() == ImageView.ScaleType.FIT_XY) {
                        float height = image.getIntrinsicHeight();
                        final float width = getWidth();

                        LayoutParams layoutParams = getLayoutParams();

                        final float imageWidth = image.getIntrinsicWidth();

                        height *= width / imageWidth;

                        layoutParams.height = (int) height;
                    }

                    setImageDrawable(image);
                }
            });
        }
    }

    @Override
    public void onDownloadFailed(Drawable image, String url) {

    }

    /**
     * Treatment when the download failed
     */
    public void onDownloadFailed() {
        final DistantImageView imageView = this;
        if (this.url.equals(url)) {
            getHandler().post(new Runnable() {
                public void run() {
                    imageView.setImageDrawable(defaultImage);

                }
            });
        }
    }


}
