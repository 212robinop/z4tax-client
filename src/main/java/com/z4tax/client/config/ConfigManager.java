package com.z4tax.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.z4tax.client.Z4TaxClient;
import com.z4tax.client.modules.Module;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final Path configFile;

    public ConfigManager() {
        configFile = FabricLoader.getInstance().getConfigDir().resolve("z4tax-client.json");
    }

    public void saveConfig() {
        JsonObject root = new JsonObject();
        JsonObject modules = new JsonObject();

        for (Module module : Z4TaxClient.getInstance().getModuleManager().getModules()) {
            modules.addProperty(module.getName(), module.isEnabled());
        }

        root.add("modules", modules);

        try (Writer writer = Files.newBufferedWriter(configFile)) {
            GSON.toJson(root, writer);
        } catch (IOException e) {
            Z4TaxClient.LOGGER.error("[Z4Tax] Failed to save config: {}", e.getMessage());
        }
    }

    public void loadConfig() {
        if (!Files.exists(configFile)) {
            Z4TaxClient.LOGGER.info("[Z4Tax] No config found, using defaults.");
            return;
        }

        try (Reader reader = Files.newBufferedReader(configFile)) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();

            if (root.has("modules")) {
                JsonObject modules = root.getAsJsonObject("modules");
                for (Module module : Z4TaxClient.getInstance().getModuleManager().getModules()) {
                    if (modules.has(module.getName())) {
                        boolean enabled = modules.get(module.getName()).getAsBoolean();
                        module.setEnabled(enabled);
                    }
                }
            }
            Z4TaxClient.LOGGER.info("[Z4Tax] Config loaded successfully.");
        } catch (IOException | IllegalStateException e) {
            Z4TaxClient.LOGGER.error("[Z4Tax] Failed to load config: {}", e.getMessage());
        }
    }
}
