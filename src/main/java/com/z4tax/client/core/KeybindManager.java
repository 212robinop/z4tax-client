package com.z4tax.client.core;

import com.z4tax.client.Z4TaxClient;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindManager {

    private KeyBinding openGUIKey;
    private boolean wasPressed = false;

    public void registerKeybinds() {
        openGUIKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.z4tax.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.z4tax.general"
        ));
    }

    public void onTick(MinecraftClient client) {
        if (openGUIKey.isPressed()) {
            if (!wasPressed) {
                wasPressed = true;
                Z4TaxClient.getInstance().getClickGUI().toggle();
            }
        } else {
            wasPressed = false;
        }
    }
}
