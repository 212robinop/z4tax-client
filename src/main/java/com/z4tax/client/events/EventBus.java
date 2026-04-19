package com.z4tax.client.events;

import com.z4tax.client.Z4TaxClient;
import com.z4tax.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

import java.util.List;

public class EventBus {

    public void onTick(MinecraftClient client) {
        List<Module> modules = Z4TaxClient.getInstance().getModuleManager().getModules();
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onTick(client);
            }
        }
    }

    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        List<Module> modules = Z4TaxClient.getInstance().getModuleManager().getModules();
        for (Module module : modules) {
            if (module.isEnabled()) {
                module.onHudRender(context, tickCounter);
            }
        }

        Z4TaxClient.getInstance().getClickGUI().render(context, tickCounter);
    }
}
