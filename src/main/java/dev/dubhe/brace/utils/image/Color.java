package dev.dubhe.brace.utils.image;

public class Color {
    private final int r;
    private final int g;
    private final int b;
    private final int a;

    public Color(int[] color) {
        this(color[0], color[1], color[2], color.length == 4 ? color[3] : 0xFF);
    }

    public Color(int r, int g, int b) {
        this(r, g, b, 0xFF);
    }

    public Color(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(this.r, this.g, this.b, this.a);
    }

    public static Color parseColor(String color) {
        if (color.startsWith("#")) color = color.substring(1);
        int r = Integer.parseInt(color.substring(0, 2), 16);
        int g = Integer.parseInt(color.substring(2, 4), 16);
        int b = Integer.parseInt(color.substring(4, 6), 16);
        int a = 0xFF;
        if (color.length() == 8) a = Integer.parseInt(color.substring(6, 8), 16);
        return new Color(r, g, b, a);
    }

    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return b;
    }

    public int getAlpha() {
        return a;
    }

    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }
}
