package com.z4tax.client.modules.performance;

import com.z4tax.client.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.option.ParticlesMode;

public class FpsBoostModule extends Module {

    private GraphicsMode prevGraphicsMode;
    private ParticlesMode prevParticlesMode;

    public FpsBoostModule() {
        super("FPS Boost", "Optimizes settings to improve FPS on low-end PCs.", Category.PERFORMANCE);
    }

    @Override
    public void onEnable() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options == null) return;

        prevGraphicsMode = client.options.getGraphicsMode().getValue();
        prevParticlesMode = client.options.getParticles().getValue();

        client.options.getParticles().setValue(ParticlesMode.MINIMAL);
        client.options.getEntityShadows().setValue(false);
        client.options.getVsync().setValue(false);
        client.options.getMaxFps().setValue(260);
    }

    @Override
    public void onDisable() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options == null) return;

        if (prevGraphicsMode != null) {
            client.options.getGraphicsMode().setValue(prevGraphicsMode);
        }
        if (prevParticlesMode != null) {
            client.options.getParticles().setValue(prevParticlesMode);
        }
        client.options.getEntityShadows().setValue(true);
    }

    @Override
    public void onTick(MinecraftClient client) {
    }
}
