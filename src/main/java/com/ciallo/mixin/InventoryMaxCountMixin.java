package com.ciallo.mixin;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SimpleContainer.class)
public class InventoryMaxCountMixin {
	@Redirect(
		method = "transfer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/Container;getMaxStackSize(Lnet/minecraft/world/item/ItemStack;)I"
		)
	)
	private int redirectGetMaxCount(net.minecraft.world.Container self, ItemStack stack) {
		return stack.getMaxStackSize();
	}
}
