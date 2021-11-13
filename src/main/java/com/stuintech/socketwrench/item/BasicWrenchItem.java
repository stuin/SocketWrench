package com.stuintech.socketwrench.item;

import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class BasicWrenchItem extends Item implements CancelBlockInteraction {
    protected Identifier socketSetKey = SocketSetManager.WRENCH_MASTER_KEY;

    public BasicWrenchItem(Settings settings) {
        super(settings);
    }

    public BasicWrenchItem(Settings settings, Identifier socketSetKey) {
        super(settings);
        this.socketSetKey = socketSetKey;
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        //Try fastening entity
        if(SocketSetManager.tryFasten(getSocketSet(stack), player, entity)) {
            postFasten(stack, player);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();

        //Try fastening block
        if(SocketSetManager.tryFasten(getSocketSet(stack), player, context.getWorld(), context.getBlockPos(), context.getHitPos(), context.getSide())) {
            postFasten(stack, player);
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public boolean shouldCancelInteraction(ItemStack item) {
        return true;
    }

    public void postFasten(ItemStack stack, PlayerEntity player) {

    }

    public Identifier getSocketSet(ItemStack stack) {
        return socketSetKey;
    }
}
