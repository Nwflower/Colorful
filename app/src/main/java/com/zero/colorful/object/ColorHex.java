package com.zero.colorful.object;

import org.litepal.crud.LitePalSupport;

public class ColorHex extends LitePalSupport {
    private String name;
    private String colorHex;

    public ColorHex(String name, String colorHex) {
        this.name = name;
        this.colorHex = colorHex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }
}
