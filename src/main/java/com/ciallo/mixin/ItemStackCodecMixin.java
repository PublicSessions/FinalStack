package com.ciallo.mixin;

import com.mojang.serialization.DataResult;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackCodecMixin {
	@Inject(method = "validateStrict", at = @At("HEAD"), cancellable = true)
	private static void skipMaxCountValidation(ItemStack stack, CallbackInfoReturnable<DataResult<ItemStack>> cir) {
		cir.setReturnValue(DataResult.success(stack));
	}
}
