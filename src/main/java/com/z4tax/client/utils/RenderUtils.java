package com.z4tax.client.utils;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class RenderUtils {

    public static void drawRoundedRect(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + radius, y + height - radius, color);
        context.fill(x + width - radius, y + radius, x + width, y + height - radius, color);

        drawFilledCircleQuarter(context, x + radius, y + radius, radius, color, 0);
        drawFilledCircleQuarter(context, x + width - radius, y + radius, radius, color, 1);
        drawFilledCircleQuarter(context, x + width - radius, y + height - radius, radius, color, 2);
        drawFilledCircleQuarter(context, x + radius, y + height - radius, radius, color, 3);
    }

    public static void drawRoundedRectOutline(DrawContext context, int x, int y, int width, int height, int radius, int color) {
        context.fill(x + radius, y, x + width - radius, y + 1, color);
        context.fill(x + radius, y + height - 1, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + 1, y + height - radius, color);
        context.fill(x + width - 1, y + radius, x + width, y + height - radius, color);
    }

    private static void drawFilledCircleQuarter(DrawContext context, int cx, int cy, int radius, int color, int quadrant) {
        int r2 = radius * radius;
        for (int dy = 0; dy <= radius; dy++) {
            for (int dx = 0; dx <= radius; dx++) {
                if (dx * dx + dy * dy <= r2) {
                    int px, py;
                    switch (quadrant) {
                        case 0 -> { px = cx - dx; py = cy - dy; }
                        case 1 -> { px = cx + dx; py = cy - dy; }
                        case 2 -> { px = cx + dx; py = cy + dy; }
                        default -> { px = cx - dx; py = cy + dy; }
                    }
                    context.fill(px, py, px + 1, py + 1, color);
                }
            }
        }
    }

    public static void drawTextWithGlow(DrawContext context, TextRenderer textRenderer, String text, int x, int y, int color, int glowColor) {
        context.drawText(textRenderer, text, x + 1, y + 1, glowColor, false);
        context.drawText(textRenderer, text, x - 1, y + 1, glowColor, false);
        context.drawText(textRenderer, text, x + 1, y - 1, glowColor, false);
        context.drawText(textRenderer, text, x - 1, y - 1, glowColor, false);
        context.drawText(textRenderer, text, x, y, color, false);
    }

    public static int interpolateColor(int color1, int color2, float progress) {
        int a1 = (color1 >> 24) & 0xFF;
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;

        int a2 = (color2 >> 24) & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;

        int a = (int) (a1 + (a2 - a1) * progress);
        int r = (int) (r1 + (r2 - r1) * progress);
        int g = (int) (g1 + (g2 - g1) * progress);
        int b = (int) (b1 + (b2 - b1) * progress);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int applyAlpha(int color, float alpha) {
        int a = Math.min(255, (int) (((color >> 24) & 0xFF) * alpha));
        return (color & 0x00FFFFFF) | (a << 24);
    }
}
