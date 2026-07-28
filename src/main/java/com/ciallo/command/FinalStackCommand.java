package com.ciallo.command;

import com.ciallo.screen.FinalStackConfigScreen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.network.chat.Component;

public class FinalStackCommand {
	public static void register() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
				ClientCommands.literal("finalstack")
					.then(ClientCommands.literal("gui")
						.executes(ctx -> {
							FinalStackConfigScreen.open(null);
							return 1;
						})
					)
					.executes(ctx -> {
						ctx.getSource().sendFeedback(Component.literal("Usage: /finalstack gui - Open the configuration GUI"));
						return 1;
					})
			);
		});
	}
}
