package com.ciallo.mixin;

import com.ciallo.config.ConfigManager;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DrawContext.class)
public class DrawContextMixin {
	@Redirect(
		method = "drawStackCount(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/String;valueOf(I)Ljava/lang/String;"
		),
		require = 1
	)
	private String abbreviateStackCount(int count) {
		if (!ConfigManager.getConfig().enableAbbreviation) {
			return String.valueOf(count);
		}
		return abbreviateNumber(count);
	}

	private static String abbreviateNumber(int value) {
		if (value < 1000) {
			return String.valueOf(value);
		}
		double scaled = value;
		int tier = 0;
		while (scaled >= 1000 && tier < 4) {
			scaled /= 1000.0;
			tier++;
		}
		String suffix = switch (tier) {
			case 1 -> "k";
			case 2 -> "M";
			case 3 -> "B";
			default -> "T";
		};
		if (scaled >= 100) {
			return String.format("%.0f%s", scaled, suffix);
		} else if (scaled >= 10) {
			return String.format("%.1f%s", scaled, suffix);
		} else {
			return String.format("%.1f%s", scaled, suffix);
		}
	}
}
