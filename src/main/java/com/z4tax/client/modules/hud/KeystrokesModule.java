package com.z4tax.client.modules.hud;

import com.z4tax.client.modules.Module;
import com.z4tax.client.utils.RenderUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.RenderTickCounter;

public class KeystrokesModule extends Module {

    public KeystrokesModule() {
        super("Keystrokes", "Displays WASD and mouse click keystrokes on screen.", Category.HUD);
    }

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        GameOptions options = client.options;

        boolean w = options.forwardKey.isPressed();
        boolean a = options.leftKey.isPressed();
        boolean s = options.backKey.isPressed();
        boolean d = options.rightKey.isPressed();
        boolean lmb = options.attackKey.isPressed();
        boolean rmb = options.useKey.isPressed();

        int baseX = client.getWindow().getScaledWidth() - 70;
        int baseY = client.getWindow().getScaledHeight() - 80;
        int keySize = 18;
        int gap = 2;

        drawKey(context, client, "W", baseX + keySize + gap, baseY, w);
        drawKey(context, client, "A", baseX, baseY + keySize + gap, a);
        drawKey(context, client, "S", baseX + keySize + gap, baseY + keySize + gap, s);
        drawKey(context, client, "D", baseX + (keySize + gap) * 2, baseY + keySize + gap, d);

        int lmbY = baseY + (keySize + gap) * 2 + 4;
        drawMouseButton(context, client, "LMB", baseX, lmbY, lmb);
        drawMouseButton(context, client, "RMB", baseX + keySize + gap + 2, lmbY, rmb);
    }

    private void drawKey(DrawContext context, MinecraftClient client, String label, int x, int y, boolean pressed) {
        int bgColor = pressed ? 0xCCff6600 : 0xCC1a1a1a;
        int textColor = pressed ? 0xFFFFFFFF : 0xFFAAAAAA;
        int borderColor = pressed ? 0xFFff6600 : 0xFF333333;

        RenderUtils.drawRoundedRect(context, x, y, 18, 18, 3, bgColor);
        RenderUtils.drawRoundedRectOutline(context, x, y, 18, 18, 3, borderColor);

        int textX = x + 9 - client.textRenderer.getWidth(label) / 2;
        int textY = y + 5;
        context.drawText(client.textRenderer, label, textX, textY, textColor, false);
    }

    private void drawMouseButton(DrawContext context, MinecraftClient client, String label, int x, int y, boolean pressed) {
        int bgColor = pressed ? 0xCCff1a1a : 0xCC1a1a1a;
        int textColor = pressed ? 0xFFFFFFFF : 0xFFAAAAAA;
        int borderColor = pressed ? 0xFFff1a1a : 0xFF333333;

        int width = client.textRenderer.getWidth(label) + 8;
        RenderUtils.drawRoundedRect(context, x, y, width, 14, 2, bgColor);
        RenderUtils.drawRoundedRectOutline(context, x, y, width, 14, 2, borderColor);

        context.drawText(client.textRenderer, label, x + 4, y + 3, textColor, false);
    }
}
