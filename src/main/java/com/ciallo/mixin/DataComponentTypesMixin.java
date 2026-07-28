package com.ciallo.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.util.dynamic.Codecs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DataComponentTypes.class)
public class DataComponentTypesMixin {
	@Redirect(
		method = "method_58570",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/dynamic/Codecs;rangedInt(II)Lcom/mojang/serialization/Codec;"
		),
		require = 1
	)
	private static Codec<Integer> modifyMaxStackSizeRange(int min, int max) {
		return Codecs.rangedInt(min, Integer.MAX_VALUE);
	}
}
