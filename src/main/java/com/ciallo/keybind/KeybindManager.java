package com.ciallo.keybind;

import com.ciallo.screen.FinalStackConfigScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeybindManager {
	private static KeyMapping openConfigKey;

	public static void register() {
		openConfigKey = new KeyMapping(
			"key.finalstack.open_config",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_G,
			KeyMapping.Category.MISC
		);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openConfigKey.consumeClick()) {
				FinalStackConfigScreen.open(client.gui.screen());
			}
		});
	}

	public static String getKeybindTranslation() {
		if (openConfigKey != null) {
			return openConfigKey.getTranslatedKeyMessage().getString();
		}
		return "Not set";
	}
}
