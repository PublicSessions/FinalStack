package com.ciallo.mixin;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.item.ItemStack$3")
public class ItemStack3Mixin {
	@Redirect(
		method = "decode",
		at = @At(
			value = "INVOKE",
			target = "Lcom/mojang/serialization/Codec;encodeStart(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"
		),
		require = 1
	)
	private DataResult<?> skipRoundTripValidation(com.mojang.serialization.Codec<?> codec, DynamicOps<?> ops, Object stack) {
		return DataResult.success(ops.empty());
	}
}
