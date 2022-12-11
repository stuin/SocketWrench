package com.stuintech.socketwrenchitem.compat;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.stuintech.socketwrench.socket.Socket;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Create extends Socket.BlockActionSocket implements SocketSetLoader {

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d vec3d, Direction direction) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if(block instanceof IWrenchable actor) {
            BlockHitResult hitResult = new BlockHitResult(vec3d, direction, pos, false);
            ItemUsageContext context = new ItemUsageContext(player, player.getActiveHand(), hitResult);

            if (player.isSneaking())
                return actor.onSneakWrenched(state, context).isAccepted();
            return actor.onWrenched(state, context).isAccepted();
        } else if(player.isSneaking() && canWrenchPickup(state))
            return onItemUseOnOther(player, world, pos, state);

        return false;
    }

    private static boolean canWrenchPickup(BlockState state) {
        return AllTags.AllBlockTags.WRENCH_PICKUP.matches(state);
    }

    private static boolean onItemUseOnOther(PlayerEntity player, World world, BlockPos pos, BlockState state) {
        if (!(world instanceof ServerWorld))
            return true;
        if (player != null && !player.isCreative())
            Block.getDroppedStacks(state, (ServerWorld) world, pos, world.getBlockEntity(pos), player, player.getActiveItem())
                    .forEach(itemStack -> player.getInventory().offerOrDrop(itemStack));
        state.onStacksDropped((ServerWorld) world, pos, ItemStack.EMPTY, true);
        world.breakBlock(pos, false);
        AllSoundEvents.WRENCH_REMOVE.playOnServer(world, pos, 1, com.simibubi.create.Create.RANDOM.nextFloat() * .5f + .5f);
        return true;
    }

    @Override
    public void registerSockets() {
        SocketSetManager.addSocket(this, SocketSetManager.ADDON_SET_KEY);
    }
}
