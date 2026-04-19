package com.z4tax.client.core;

import com.z4tax.client.modules.Module;
import com.z4tax.client.modules.hud.FpsCounterModule;
import com.z4tax.client.modules.hud.KeystrokesModule;
import com.z4tax.client.modules.performance.FpsBoostModule;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<Module> modules = new ArrayList<>();

    public void registerModules() {
        register(new FpsCounterModule());
        register(new KeystrokesModule());
        register(new FpsBoostModule());
    }

    private void register(Module module) {
        modules.add(module);
    }

    public Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public List<Module> getModules() {
        return modules;
    }

    public List<Module> getModulesByCategory(Module.Category category) {
        List<Module> result = new ArrayList<>();
        for (Module m : modules) {
            if (m.getCategory() == category) result.add(m);
        }
        return result;
    }
}
