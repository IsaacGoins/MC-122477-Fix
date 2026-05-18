package dev.cardistymo.mc122477fix;

import net.fabricmc.api.ModInitializer;

import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class MC122477Fix implements ModInitializer {
	public static final String MOD_ID = "mc-122477-fix";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static final ArrayList<Runnable> delayedKeyPresses = new ArrayList<>();

	public static void addDelayedKeyPress(Runnable delayedKeyPress) {
		delayedKeyPresses.add(delayedKeyPress);
	}

	public static void performDelayedKeyPresses() {
		for (Runnable delayedKeyPress : delayedKeyPresses) {
			Minecraft.getInstance().execute(delayedKeyPress);
		}
		delayedKeyPresses.clear();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized MC-122477 Fix");
	}
}