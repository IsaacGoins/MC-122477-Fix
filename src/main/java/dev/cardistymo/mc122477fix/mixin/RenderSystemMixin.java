package dev.cardistymo.mc122477fix.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.cardistymo.mc122477fix.MC122477Fix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public  abstract class RenderSystemMixin {
    @Inject(at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwPollEvents()V"), method = "pollEvents")
    private static void injectPollEvents(CallbackInfo ci) {
        MC122477Fix.pollCount += 1;
    }
}
