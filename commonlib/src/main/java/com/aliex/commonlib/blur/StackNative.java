package com.aliex.commonlib.blur;

import android.graphics.Bitmap;

/**
 * author: Aliex <br/>
 * created on: 2017/2/28 <br/>
 * description: <br/>
 */

class StackNative {

    static {
        System.loadLibrary("genius_blur");
    }

    /**
     * Blur Image By Pixels
     *
     * @param img
     *            Img pixel array
     * @param w
     *            Img width
     * @param h
     *            Img height
     * @param r
     *            Blur radius
     */
    protected static native void blurPixels(int[] img, int w, int h, int r);

    /**
     * Blur Image By Bitmap
     *
     * @param bitmap
     *            Img Bitmap
     * @param r
     *            Blur radius
     */
    protected static native void blurBitmap(Bitmap bitmap, int r);
}
