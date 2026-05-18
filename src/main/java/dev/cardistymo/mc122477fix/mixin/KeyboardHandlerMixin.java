package dev.cardistymo.mc122477fix.mixin;

import dev.cardistymo.mc122477fix.MC122477Fix;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	protected abstract void keyPress(long handle, int action, KeyEvent event);

	//? if <=1.21.11 {
	/*@ModifyArgs(method = "setup", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;setupKeyboardCallbacks(Lcom/mojang/blaze3d/platform/Window;Lorg/lwjgl/glfw/GLFWKeyCallbackI;Lorg/lwjgl/glfw/GLFWCharModsCallbackI;)V"))
    *///?} else
	@ModifyArgs(method = "setup", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;setupKeyboardCallbacks(Lcom/mojang/blaze3d/platform/Window;Lorg/lwjgl/glfw/GLFWKeyCallbackI;Lorg/lwjgl/glfw/GLFWCharCallbackI;Lorg/lwjgl/glfw/GLFWPreeditCallbackI;Lorg/lwjgl/glfw/GLFWIMEStatusCallbackI;)V"))
	private void injectSetupKeyboardCallbacks(Args args) {
		args.set(1, (GLFWKeyCallbackI) (window1, keysym, scancode, action, mods) -> {
			KeyEvent event = new KeyEvent(keysym, scancode, mods);

			if (this.minecraft.options.keyChat.matches(event) || this.minecraft.options.keyCommand.matches(event)) {
				MC122477Fix.addDelayedKeyPress(() -> this.keyPress(window1, action, event));
			} else {
				this.minecraft.execute(() -> this.keyPress(window1, action, event));
			}
		});
	}
}