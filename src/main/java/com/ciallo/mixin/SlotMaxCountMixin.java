package com.ciallo.mixin;

import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMaxCountMixin {
	@Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
	public void modifyMaxItemCount(CallbackInfoReturnable<Integer> cir) {
		if (cir.getReturnValue() == 99) {
			cir.setReturnValue(Integer.MAX_VALUE);
		}
	}
}
