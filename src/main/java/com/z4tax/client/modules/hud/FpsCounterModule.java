package com.z4tax.client.modules.hud;

import com.z4tax.client.modules.Module;
import com.z4tax.client.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class FpsCounterModule extends Module {

    private int fps = 0;
    private int tickCounter = 0;

    public FpsCounterModule() {
        super("FPS Counter", "Displays the current FPS in the corner.", Category.HUD);
    }

    @Override
    public void onTick(MinecraftClient client) {
        tickCounter++;
        if (tickCounter >= 5) {
            tickCounter = 0;
            fps = client.getCurrentFps();
        }
    }

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getDebugHud().shouldShowDebugHud()) return;

        String fpsText = fps + " FPS";
        int color = getFpsColor(fps);

        int x = 4;
        int y = 4;

        RenderUtils.drawRoundedRect(context, x - 2, y - 2, 42, 12, 3, 0xCC0d0d0d);
        RenderUtils.drawTextWithGlow(context, client.textRenderer, fpsText, x, y, color, 0x40000000);
    }

    private int getFpsColor(int fps) {
        if (fps >= 60) return 0xFF00FF44;
        if (fps >= 30) return 0xFFFFAA00;
        return 0xFFFF1A1A;
    }
}
