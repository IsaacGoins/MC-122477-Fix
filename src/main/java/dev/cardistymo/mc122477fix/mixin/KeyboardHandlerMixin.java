package dev.cardistymo.mc122477fix.mixin;

import dev.cardistymo.mc122477fix.MC122477Fix;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
//? if <=1.21.11 {
/*import org.lwjgl.glfw.GLFWCharModsCallbackI;
*///? } else {
import org.lwjgl.glfw.GLFWCharCallbackI;
//? }
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
	@Shadow
	@Final
	private Minecraft minecraft;

	@Shadow
	protected abstract void keyPress(long handle, int action, KeyEvent event);

	@Shadow
	protected abstract void charTyped(long handle, CharacterEvent event);

	@Unique
	private long mc122477fix$chatOpenedFromGamePoll = 0;

	//? if <=1.21.11 {
	/*@ModifyArgs(method = "setup", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;setupKeyboardCallbacks(Lcom/mojang/blaze3d/platform/Window;Lorg/lwjgl/glfw/GLFWKeyCallbackI;Lorg/lwjgl/glfw/GLFWCharModsCallbackI;)V"))
    *///?} else
	@ModifyArgs(method = "setup", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/InputConstants;setupKeyboardCallbacks(Lcom/mojang/blaze3d/platform/Window;Lorg/lwjgl/glfw/GLFWKeyCallbackI;Lorg/lwjgl/glfw/GLFWCharCallbackI;Lorg/lwjgl/glfw/GLFWPreeditCallbackI;Lorg/lwjgl/glfw/GLFWIMEStatusCallbackI;)V"))
	private void injectSetupKeyboardCallbacks(Args args) {
		args.set(1, (GLFWKeyCallbackI) (window, keysym, scancode, action, mods) -> {
			KeyEvent event = new KeyEvent(keysym, scancode, mods);
			if (action == GLFW.GLFW_PRESS && this.minecraft.screen == null && (this.minecraft.options.keyChat.matches(event) || this.minecraft.options.keyCommand.matches(event))) {
				mc122477fix$chatOpenedFromGamePoll = MC122477Fix.pollCount;
			}

			this.minecraft.execute(() -> this.keyPress(window, action, event));
		});

		//? if <=1.21.11 {
			/*args.set(2, (GLFWCharModsCallbackI) (window, codepoint, mods) -> {
				// cancel the next CharCallback after the chat was opened
				if (MC122477Fix.pollCount - mc122477fix$chatOpenedFromGamePoll <= 3) {
					mc122477fix$chatOpenedFromGamePoll = 0;
					return;
				}

				CharacterEvent characterEvent = new CharacterEvent(codepoint, mods);
				this.minecraft.execute(() -> this.charTyped(window, characterEvent));
			});
		*///?} else {
		args.set(2, (GLFWCharCallbackI) (window, codepoint) -> {
			// cancel the next CharCallback after the chat was opened
			if (MC122477Fix.pollCount - mc122477fix$chatOpenedFromGamePoll <= 3) {
				mc122477fix$chatOpenedFromGamePoll = 0;
				return;
			}

			CharacterEvent event = new CharacterEvent(codepoint);
			this.minecraft.execute(() -> this.charTyped(window, event));
		});
		//?}
	}
}