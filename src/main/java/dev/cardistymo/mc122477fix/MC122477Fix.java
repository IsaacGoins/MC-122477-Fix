package dev.cardistymo.mc122477fix;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MC122477Fix implements ModInitializer {
	public static final String MOD_ID = "mc-122477-fix";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static long pollCount = 0;

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized MC-122477 Fix");
	}
}