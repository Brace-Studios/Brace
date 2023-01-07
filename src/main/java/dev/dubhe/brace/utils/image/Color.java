package dev.dubhe.brace.utils.image;

public class Color {
    private final byte r;
    private final byte g;
    private final byte b;
    private final byte a;

    public Color(byte[] color) {
        this(color[0], color[1], color[2], color.length == 4 ? color[3] : (byte) 0xFF);
    }

    public Color(byte r, byte g, byte b) {
        this(r, g, b, (byte) 0xFF);
    }

    public Color(byte r, byte g, byte b, byte a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(this.r, this.g, this.b, this.a);
    }

    public Color parseColor(String color) {
        if (color.startsWith("#")) color = color.substring(1);
        byte r = (byte) Integer.parseInt(color.substring(0, 2), 16);
        byte g = (byte) Integer.parseInt(color.substring(2, 4), 16);
        byte b = (byte) Integer.parseInt(color.substring(4, 6), 16);
        byte a = (byte) 0xFF;
        if (color.length() == 8) a = (byte) Integer.parseInt(color.substring(6, 8), 16);
        return new Color(r, g, b, a);
    }

    public byte getRed() {
        return r;
    }

    public byte getGreen() {
        return g;
    }

    public byte getBlue() {
        return b;
    }

    public byte getAlpha() {
        return a;
    }

    public String toString() {
        return getClass().getName() + "[r=" + getRed() + ",g=" + getGreen() + ",b=" + getBlue() + "]";
    }
}
