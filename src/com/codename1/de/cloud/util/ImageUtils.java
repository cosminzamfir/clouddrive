package com.codename1.de.cloud.util;

import com.codename1.ui.RGBImage;

public class ImageUtils {

    /**
     * Creates a colored rectangle, filled with the given color, with the 
     * specified width/height
     * @param color - the AARRGGBB color scheme (see {@link Colors#RED_AA} for an example
     * @return
     */
    public static RGBImage createColouredRectangle(int color, int height, int width) {
        int[] rgbArray = new int[height * width];
        for (int i = 0; i < rgbArray.length; i++) {
            rgbArray[i] = color;
        }
        return new RGBImage(rgbArray, width, height);
    }
}
