package com.andres_k.utils.configs;


/**
 * Created by andres_k on 11/03/2015.
 */
public class WindowConfig {
    private static float sizeX;
    private static float sizeY;

    public static void initWindow1() {
        sizeX = 1280;
        sizeY = 800;
    }

    public static void initWindow2() {
        sizeX = 1280;
        sizeY = 800;
    }

    public static float getSizeX() {
        return sizeX;
    }

    public static float getSizeY() {
        return sizeY;
    }

    public static int getIntSizeX() {
        return (int) sizeX;
    }

    public static int getIntSizeY() {
        return (int) sizeY;
    }
}
