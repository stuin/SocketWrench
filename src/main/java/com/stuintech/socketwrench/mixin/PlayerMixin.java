package com.stuintech.socketwrench.mixin;

import com.stuintech.socketwrench.item.CancelBlockInteraction;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "shouldCancelInteraction()Z", cancellable = true)
    public void shouldCancelInteraction(CallbackInfoReturnable<Boolean> cir) {
        if(getStackInHand(Hand.MAIN_HAND).getItem() instanceof CancelBlockInteraction) {
            ItemStack stack = getStackInHand(Hand.MAIN_HAND);
            if(((CancelBlockInteraction)stack.getItem()).shouldCancelInteraction(stack))
                cir.setReturnValue(true);
        }
        if(getStackInHand(Hand.OFF_HAND).getItem() instanceof CancelBlockInteraction) {
            ItemStack stack = getStackInHand(Hand.OFF_HAND);
            if(((CancelBlockInteraction)stack.getItem()).shouldCancelInteraction(stack))
                cir.setReturnValue(true);
        }
    }
}
