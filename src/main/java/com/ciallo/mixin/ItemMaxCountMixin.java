package com.ciallo.mixin;

import com.ciallo.config.ConfigManager;
import com.ciallo.config.FinalStackConfig;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMaxCountMixin {
	@Inject(method = "getMaxCount", at = @At("RETURN"), cancellable = true)
	private void modifyMaxCount(CallbackInfoReturnable<Integer> cir) {
		int original = cir.getReturnValue();
		FinalStackConfig config = ConfigManager.getConfig();
		if (original >= 64) {
			cir.setReturnValue(config.normalItemMaxStack);
		} else if (original == 16) {
			cir.setReturnValue(config.sixteenItemMaxStack);
		} else if (original == 1) {
			cir.setReturnValue(config.unstackableItemMaxStack);
		}
	}
}
