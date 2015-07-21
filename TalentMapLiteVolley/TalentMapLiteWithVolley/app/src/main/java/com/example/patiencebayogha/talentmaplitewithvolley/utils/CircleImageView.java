package com.example.patiencebayogha.talentmaplitewithvolley.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by patiencebayogha on 15/06/15.
 */
public class CircleImageView extends NetworkImageView {

    private Bitmap frame;

    public CircleImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // frame = BitmapFactory.decodeResource(context.getResources(), R.drawable.chat_list_profile_frame);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // frame = BitmapFactory.decodeResource(context.getResources(), R.drawable.chat_list_profile_frame);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        if (b != null) {
            Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

            int w = getWidth(), h = getHeight();

            Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
            canvas.drawBitmap(roundBitmap, 0, 0, null);
            // canvas.drawBitmap(frame, 0, 0, null);
        }
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }



}
