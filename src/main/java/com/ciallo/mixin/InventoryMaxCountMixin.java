package com.ciallo.mixin;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SimpleInventory.class)
public class InventoryMaxCountMixin {
	@Redirect(
		method = "transfer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/inventory/SimpleInventory;getMaxCount(Lnet/minecraft/item/ItemStack;)I"
		)
	)
	private int redirectGetMaxCount(SimpleInventory self, ItemStack stack) {
		return stack.getMaxCount();
	}
}
