package com.z4tax.client;

import com.z4tax.client.config.ConfigManager;
import com.z4tax.client.core.KeybindManager;
import com.z4tax.client.core.ModuleManager;
import com.z4tax.client.events.EventBus;
import com.z4tax.client.gui.ClickGUI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Z4TaxClient implements ClientModInitializer {

    public static final String MOD_ID = "z4tax";
    public static final String MOD_NAME = "Z4Tax Client";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static Z4TaxClient instance;

    private ModuleManager moduleManager;
    private KeybindManager keybindManager;
    private ConfigManager configManager;
    private ClickGUI clickGUI;
    private EventBus eventBus;

    @Override
    public void onInitializeClient() {
        instance = this;
        LOGGER.info("[Z4Tax] Initializing Z4Tax Client...");

        eventBus = new EventBus();
        moduleManager = new ModuleManager();
        keybindManager = new KeybindManager();
        configManager = new ConfigManager();
        clickGUI = new ClickGUI();

        moduleManager.registerModules();
        keybindManager.registerKeybinds();
        configManager.loadConfig();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            keybindManager.onTick(client);
            eventBus.onTick(client);
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            eventBus.onHudRender(drawContext, tickCounter);
        });

        LOGGER.info("[Z4Tax] Z4Tax Client initialized successfully.");
    }

    public static Z4TaxClient getInstance() {
        return instance;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public KeybindManager getKeybindManager() {
        return keybindManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ClickGUI getClickGUI() {
        return clickGUI;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
