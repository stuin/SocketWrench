package com.stuintech.socketwrench.item;

import com.stuintech.socketwrench.SocketWrench;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;

public class BasicWrenchItem extends Item implements CancelBlockInteraction {
    protected Identifier socketSetKey;

    public BasicWrenchItem(Settings settings) {
        this(settings, SocketSetManager.WRENCH_MASTER_KEY);
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

    public boolean hasDurability(PlayerEntity player, ItemStack stack) {
        return player.world.getGameRules().getBoolean(SocketWrench.SOCKET_WRENCH_DURABILITY);
    }

    public void playFastenSound(PlayerEntity player, ItemStack stack) {

    }

    @Override
    public boolean shouldCancelInteraction(ItemStack stack) {
        return true;
    }

    public void postFasten(ItemStack stack, PlayerEntity player) {
        if(hasDurability(player, stack)) {
            stack.damage(1, player, (p) -> {
                p.sendToolBreakStatus(player.getActiveHand());
            });
        }
        playFastenSound(player, stack);
        player.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    public Identifier getSocketSet(ItemStack stack) {
        return socketSetKey;
    }
}
