package com.stuintech.socketwrenchitem.compat;

import com.stuintech.socketwrench.socket.Socket;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import reborncore.api.IToolDrop;
import reborncore.common.BaseBlockEntityProvider;
import reborncore.common.misc.ModSounds;
import reborncore.common.util.ItemHandlerUtils;

public class RebornCore extends Socket.BlockActionSocket implements SocketSetLoader {

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d vec3d, Direction direction) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(player.isSneaking() && blockEntity instanceof IToolDrop) {
            ItemStack drop = ((IToolDrop) blockEntity).getToolDrop(player);
            if (drop == null) {
                return false;
            }

            boolean dropContents = true;
            Block block = blockEntity.getCachedState().getBlock();
            if (block instanceof BaseBlockEntityProvider) {
                ItemStack blockEntityDrop = ((BaseBlockEntityProvider) block).getDropWithContents(world, pos, drop).orElse(ItemStack.EMPTY);
                if (!blockEntityDrop.isEmpty()) {
                    dropContents = false;
                    drop = blockEntityDrop;
                }
            }

            if (!world.isClient) {
                if (dropContents)
                    ItemHandlerUtils.dropContainedItems(world, pos);
                if (!drop.isEmpty())
                    net.minecraft.util.ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), drop);

                world.removeBlockEntity(pos);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.BLOCK_DISMANTLE,
                    SoundCategory.BLOCKS, 0.6F, 1F);
            return true;
        }

        return false;
    }

    @Override
    public void registerSockets() {
        SocketSetManager.addSocket(this, SocketSetManager.ADDON_SET_KEY);
    }
}
