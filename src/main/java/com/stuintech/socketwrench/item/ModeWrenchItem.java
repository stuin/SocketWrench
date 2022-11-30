package com.stuintech.socketwrench.item;

import com.stuintech.socketwrench.ModSounds;
import com.stuintech.socketwrench.SocketWrench;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class ModeWrenchItem extends Item implements CancelBlockInteraction {
    public static final String MODE = "currentMode";
    public final List<Identifier> modes;

    public ModeWrenchItem(Settings settings, List<Identifier> modes) {
        super(settings);
        this.modes = modes;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getStackInHand(hand);
        cycleModes(itemStack, playerEntity, 1);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        //Try fastening entity
        if(SocketSetManager.tryFasten(getSocketSet(stack), player, entity)) {
            postFasten(stack, player);
            return ActionResult.SUCCESS;
        }

        //Cycle modes
        if(player.isSneaking()) {
            cycleModes(stack, player, 1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        //Get item properties
        ItemStack stack = context.getStack();
        PlayerEntity player = context.getPlayer();

        //Try fastening block
        if(SocketSetManager.tryFasten(getSocketSet(stack), player, context.getWorld(), context.getBlockPos(), context.getHitPos(), context.getSide())) {
            postFasten(stack, player);
            return ActionResult.SUCCESS;
        }

        //Cycle modes
        if(player != null && player.isSneaking()) {
            cycleModes(stack, player, 1);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;
    }

    public boolean hasDurability(PlayerEntity player, ItemStack stack) {
        return player.world.getGameRules().getBoolean(SocketWrench.SOCKET_WRENCH_DURABILITY);
    }

    public void playFastenSound(PlayerEntity player, ItemStack stack) {
        if(!player.world.isClient)
            player.playSound(ModSounds.wrenchSound, SoundCategory.PLAYERS, 0.6f, 1.0f);
    }

    @Override
    public boolean shouldCancelInteraction(ItemStack item) {
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

    public void onModeChange(ItemStack stack, PlayerEntity player, int mode) {
        playFastenSound(player, stack);
    }

    public Identifier getSocketSet(ItemStack stack) {
        if(modes.size() == 0)
            return SocketSetManager.NULL_SET_KEY;
        return modes.get(stack.getOrCreateNbt().getInt(MODE));
    }

    public Identifier cycleModes(ItemStack stack, PlayerEntity player, int i) {
        //Set default mode
        if(!stack.hasNbt() || stack.getNbt() == null && modes.size() > 0) {
            stack.getOrCreateNbt().putInt(MODE, 0);
            onModeChange(stack, player, 0);
            return modes.get(0);
        }

        //Move to next mode
        if(modes.size() > 1) {
            int current = (stack.getNbt().getInt(MODE) + i) % modes.size();
            stack.getNbt().putInt(MODE, current);
            onModeChange(stack, player, current);
            return modes.get(current);
        }

        return SocketSetManager.NULL_SET_KEY;
    }

    public int getMode(ItemStack stack) {
        return stack.getOrCreateNbt().getInt(MODE);
    }

    //Get display name for current mode
    public TranslatableTextContent getModeName(ItemStack stack) {
        Identifier socketSet = getSocketSet(stack);
        return new TranslatableTextContent("mode." + socketSet.getNamespace() + "." + socketSet.getPath());
    }
}
