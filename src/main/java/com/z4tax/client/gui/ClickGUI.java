package com.z4tax.client.gui;

import com.z4tax.client.Z4TaxClient;
import com.z4tax.client.modules.Module;
import com.z4tax.client.utils.ColorTheme;
import com.z4tax.client.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.List;

public class ClickGUI {

    private boolean open = false;

    private float animationProgress = 0f;
    private static final float ANIM_SPEED = 0.12f;

    private int guiX = 20;
    private int guiY = 30;
    private int guiWidth = 200;

    private boolean dragging = false;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    private final int HEADER_HEIGHT = 22;
    private final int MODULE_HEIGHT = 20;
    private final int PADDING = 5;

    private Module[] modulesCache = null;
    private float[] hoverAnimations = null;
    private long lastCacheTime = 0;

    public void toggle() {
        open = !open;
    }

    public boolean isOpen() {
        return open;
    }

    public void render(DrawContext context, RenderTickCounter tickCounter) {
        updateAnimation();
        if (animationProgress <= 0.01f) return;

        refreshModuleCache();

        handleMouseInput(context);

        float alpha = animationProgress;
        int totalHeight = HEADER_HEIGHT + PADDING + modulesCache.length * (MODULE_HEIGHT + 2) + PADDING;

        int drawX = guiX;
        int drawY = (int) (guiY + (1f - animationProgress) * -20);
        int drawW = guiWidth;
        int drawH = totalHeight;

        int bgColor = RenderUtils.applyAlpha(ColorTheme.BACKGROUND, alpha);
        int headerColor = RenderUtils.applyAlpha(ColorTheme.HEADER, alpha);

        RenderUtils.drawRoundedRect(context, drawX, drawY, drawW, drawH, 6, bgColor);

        renderHeader(context, drawX, drawY, drawW, alpha);

        int moduleY = drawY + HEADER_HEIGHT + PADDING;
        for (int i = 0; i < modulesCache.length; i++) {
            renderModule(context, modulesCache[i], drawX + PADDING, moduleY, drawW - PADDING * 2, alpha, i);
            moduleY += MODULE_HEIGHT + 2;
        }
    }

    private void renderHeader(DrawContext context, int x, int y, int width, float alpha) {
        MinecraftClient client = MinecraftClient.getInstance();
        int headerColor = RenderUtils.applyAlpha(ColorTheme.HEADER, alpha);
        RenderUtils.drawRoundedRect(context, x, y, width, HEADER_HEIGHT, 6, headerColor);

        context.fill(x, y + HEADER_HEIGHT - 6, x + width, y + HEADER_HEIGHT, headerColor);

        String title = "Z4Tax Client";
        int titleWidth = client.textRenderer.getWidth(title);
        int titleX = x + (width - titleWidth) / 2;
        int titleY = y + (HEADER_HEIGHT - 8) / 2;

        float t = (System.currentTimeMillis() % 2000) / 2000f;
        int gradColor = ColorTheme.gradientOrangeRed(t);
        int gradColorApplied = RenderUtils.applyAlpha(gradColor, alpha);

        RenderUtils.drawTextWithGlow(context, client.textRenderer, title, titleX, titleY, gradColorApplied, RenderUtils.applyAlpha(ColorTheme.GLOW, alpha * 0.6f));
    }

    private void renderModule(DrawContext context, Module module, int x, int y, int width, float alpha, int index) {
        MinecraftClient client = MinecraftClient.getInstance();

        float hover = hoverAnimations != null && index < hoverAnimations.length ? hoverAnimations[index] : 0f;

        int bgAlphaBase = module.isEnabled() ? 0xCC : 0x88;
        int bgColor = module.isEnabled()
                ? RenderUtils.applyAlpha(RenderUtils.interpolateColor(ColorTheme.DISABLED_BG, ColorTheme.ENABLED_BG, hover * 0.5f + 0.5f), alpha)
                : RenderUtils.applyAlpha(RenderUtils.interpolateColor(ColorTheme.DISABLED_BG, 0xFF222222, hover), alpha);

        RenderUtils.drawRoundedRect(context, x, y, width, MODULE_HEIGHT, 4, bgColor);

        if (module.isEnabled()) {
            int glowColor = RenderUtils.applyAlpha(ColorTheme.GLOW, alpha * 0.4f);
            context.fill(x, y + MODULE_HEIGHT - 1, x + width, y + MODULE_HEIGHT, glowColor);
        }

        int nameColor = RenderUtils.applyAlpha(module.isEnabled() ? ColorTheme.TEXT : ColorTheme.TEXT_SECONDARY, alpha);
        context.drawText(client.textRenderer, module.getName(), x + 5, y + (MODULE_HEIGHT - 8) / 2, nameColor, false);

        renderToggle(context, module, x + width - 24, y + (MODULE_HEIGHT - 8) / 2 - 1, alpha);
    }

    private void renderToggle(DrawContext context, Module module, int x, int y, float alpha) {
        int trackColor = module.isEnabled()
                ? RenderUtils.applyAlpha(ColorTheme.TOGGLE_ON, alpha)
                : RenderUtils.applyAlpha(ColorTheme.TOGGLE_OFF, alpha);

        RenderUtils.drawRoundedRect(context, x, y, 20, 10, 5, trackColor);

        int knobX = module.isEnabled() ? x + 11 : x + 1;
        int knobColor = RenderUtils.applyAlpha(0xFFFFFFFF, alpha);
        RenderUtils.drawRoundedRect(context, knobX, y + 1, 8, 8, 4, knobColor);
    }

    private void handleMouseInput(DrawContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.mouse == null) return;

        double mouseX = client.mouse.getX() / client.getWindow().getScaleFactor();
        double mouseY = client.mouse.getY() / client.getWindow().getScaleFactor();
        boolean mouseDown = isMouseButtonDown(GLFW_LEFT);

        if (dragging) {
            if (mouseDown) {
                guiX = (int) (mouseX - dragOffsetX);
                guiY = (int) (mouseY - dragOffsetY);
            } else {
                dragging = false;
            }
        }

        if (hoverAnimations == null || hoverAnimations.length != modulesCache.length) {
            hoverAnimations = new float[modulesCache.length];
        }
        for (int i = 0; i < modulesCache.length; i++) {
            int mx = guiX + PADDING;
            int my = guiY + HEADER_HEIGHT + PADDING + i * (MODULE_HEIGHT + 2);
            boolean hovering = mouseX >= mx && mouseX <= mx + guiWidth - PADDING * 2
                    && mouseY >= my && mouseY <= my + MODULE_HEIGHT;
            hoverAnimations[i] = lerp(hoverAnimations[i], hovering ? 1f : 0f, 0.15f);
        }
    }

    private boolean headerHovered = false;

    public void onClick(double mouseX, double mouseY, int button) {
        if (!open) return;

        if (mouseX >= guiX && mouseX <= guiX + guiWidth
                && mouseY >= guiY && mouseY <= guiY + HEADER_HEIGHT) {
            dragging = true;
            dragOffsetX = (int) (mouseX - guiX);
            dragOffsetY = (int) (mouseY - guiY);
            return;
        }

        if (modulesCache == null) return;

        for (int i = 0; i < modulesCache.length; i++) {
            int mx = guiX + PADDING;
            int my = guiY + HEADER_HEIGHT + PADDING + i * (MODULE_HEIGHT + 2);
            if (mouseX >= mx && mouseX <= mx + guiWidth - PADDING * 2
                    && mouseY >= my && mouseY <= my + MODULE_HEIGHT) {
                modulesCache[i].toggle();
                Z4TaxClient.getInstance().getConfigManager().saveConfig();
                break;
            }
        }
    }

    public void onMouseRelease() {
        dragging = false;
    }

    private void updateAnimation() {
        float target = open ? 1f : 0f;
        animationProgress = lerp(animationProgress, target, ANIM_SPEED);
        if (animationProgress < 0.01f) animationProgress = 0f;
        if (animationProgress > 0.99f) animationProgress = 1f;
    }

    private void refreshModuleCache() {
        long now = System.currentTimeMillis();
        if (modulesCache == null || now - lastCacheTime > 500) {
            List<Module> modules = Z4TaxClient.getInstance().getModuleManager().getModules();
            modulesCache = modules.toArray(new Module[0]);
            lastCacheTime = now;
        }
    }

    private float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private boolean isMouseButtonDown(int button) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getWindow() == null) return false;
        return org.lwjgl.glfw.GLFW.glfwGetMouseButton(client.getWindow().getHandle(), button) == org.lwjgl.glfw.GLFW.GLFW_PRESS;
    }

    private static final int GLFW_LEFT = 0;
}
