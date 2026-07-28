package com.ciallo;

import com.ciallo.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinalStack implements ModInitializer {
	public static final String MOD_ID = "finalstack";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ConfigManager.load();
		LOGGER.info("FinalStack initialized!");
	}
}
