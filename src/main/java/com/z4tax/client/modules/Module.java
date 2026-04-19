package com.z4tax.client.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public abstract class Module {

    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;

    public enum Category {
        RENDER,
        HUD,
        PERFORMANCE
    }

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.enabled = false;
    }

    public void onEnable() {}
    public void onDisable() {}

    public void onTick(MinecraftClient client) {}

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {}

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        boolean wasEnabled = this.enabled;
        this.enabled = enabled;
        if (!wasEnabled && enabled) {
            onEnable();
        } else if (wasEnabled && !enabled) {
            onDisable();
        }
    }
}
