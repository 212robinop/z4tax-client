package com.z4tax.client.utils;

public class ColorTheme {

    public static final int BACKGROUND       = 0xEE0d0d0d;
    public static final int BACKGROUND_LIGHT = 0xEE1a1a1a;
    public static final int HEADER            = 0xFF0d0d0d;
    public static final int ORANGE            = 0xFFff6600;
    public static final int RED               = 0xFFff1a1a;
    public static final int ORANGE_DARK       = 0xFFcc5200;
    public static final int TEXT              = 0xFFFFFFFF;
    public static final int TEXT_SECONDARY    = 0xFFAAAAAA;
    public static final int ENABLED_BG        = 0xCC221100;
    public static final int DISABLED_BG       = 0xCC111111;
    public static final int GLOW              = 0x40ff6600;
    public static final int TOGGLE_ON         = 0xFFff6600;
    public static final int TOGGLE_OFF        = 0xFF444444;

    public static int gradientOrangeRed(float t) {
        int r1 = 0xFF, g1 = 0x66, b1 = 0x00;
        int r2 = 0xFF, g2 = 0x1A, b2 = 0x1A;
        int r = (int) (r1 + (r2 - r1) * t);
        int g = (int) (g1 + (g2 - g1) * t);
        int b = (int) (b1 + (b2 - b1) * t);
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
