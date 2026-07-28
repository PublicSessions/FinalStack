package com.ciallo.command;

import com.ciallo.screen.FinalStackConfigScreen;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.text.Text;

public class FinalStackCommand {
	public static void register() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			dispatcher.register(
				ClientCommandManager.literal("finalstack")
					.then(ClientCommandManager.literal("gui")
						.executes(ctx -> {
							FinalStackConfigScreen.open(null);
							return 1;
						})
					)
					.executes(ctx -> {
						ctx.getSource().sendFeedback(Text.literal("Usage: /finalstack gui - Open the configuration GUI"));
						return 1;
					})
			);
		});
	}
}
