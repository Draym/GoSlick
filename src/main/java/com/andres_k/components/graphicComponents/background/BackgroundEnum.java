package com.andres_k.components.graphicComponents.background;

/**
 * Created by andres_k on 12/10/2015.
 */
public enum BackgroundEnum {
    BASIC("basic", "image/background/backgroundGame.png");

    private String name;
    private String path;

    BackgroundEnum(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }
}
