package com.ciallo.keybind;

import com.ciallo.screen.FinalStackConfigScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeybindManager {
	private static KeyBinding openConfigKey;

	public static void register() {
		openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.finalstack.open_config",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_G,
			KeyBinding.Category.MISC
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openConfigKey.wasPressed()) {
				FinalStackConfigScreen.open(client.currentScreen);
			}
		});
	}

	public static String getKeybindTranslation() {
		if (openConfigKey != null) {
			return openConfigKey.getBoundKeyLocalizedText().getString();
		}
		return "Not set";
	}
}
