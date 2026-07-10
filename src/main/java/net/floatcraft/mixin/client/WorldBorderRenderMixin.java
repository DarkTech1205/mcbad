package net.floatcraft.mixin.client;

import net.floatcraft.config.FloatcraftConfig;
import net.minecraft.client.render.WorldBorderRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * OPTIONAL / BEST-EFFORT. The actual functional removal of the world border
 * (so you can walk/build past it) is handled safely in FloatcraftMod via
 * WorldBorder#setSize and does not depend on this class at all.
 *
 * This mixin only tries to additionally hide the red-and-white warning wall
 * texture/particles that still render as you approach the (now enormous)
 * border. If WorldBorderRenderer's method name doesn't match your exact
 * Yarn build, this specific file is the one to fix or delete - everything
 * else in the mod works fine without it, since this config is registered
 * as "required": false in floatcraft.client.optional.mixins.json.
 */
@Mixin(WorldBorderRenderer.class)
public abstract class WorldBorderRenderMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void floatcraft$hideBorder(CallbackInfo ci) {
        if (FloatcraftConfig.get().hideWorldBorderVisual) {
            ci.cancel();
        }
    }
}
