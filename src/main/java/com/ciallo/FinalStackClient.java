package com.ciallo;

import com.ciallo.command.FinalStackCommand;
import com.ciallo.keybind.KeybindManager;
import net.fabricmc.api.ClientModInitializer;

public class FinalStackClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FinalStackCommand.register();
		KeybindManager.register();
		FinalStack.LOGGER.info("FinalStack client initialized!");
	}
}
