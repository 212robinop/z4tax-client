package com.z4tax.client.mixin;

import com.z4tax.client.Z4TaxClient;
import com.z4tax.client.modules.Module;
import com.z4tax.client.modules.performance.FpsBoostModule;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Inject(at = @At("HEAD"), method = "tick", cancellable = true)
    private void onParticleTick(CallbackInfo ci) {
        if (Z4TaxClient.getInstance() == null) return;
        Module fpsBoost = Z4TaxClient.getInstance().getModuleManager().getModuleByName("FPS Boost");
        if (fpsBoost != null && fpsBoost.isEnabled()) {
        }
    }
}
