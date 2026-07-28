package com.ciallo.mixin;

import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public class SlotMaxCountMixin {
	@Inject(method = "getMaxItemCount", at = @At("RETURN"), cancellable = true)
	public void modifyMaxItemCount(CallbackInfoReturnable<Integer> cir) {
		if (cir.getReturnValue() == 99) {
			cir.setReturnValue(Integer.MAX_VALUE);
		}
	}
}
