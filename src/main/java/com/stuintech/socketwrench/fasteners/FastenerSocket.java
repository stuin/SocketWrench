package com.stuintech.socketwrench.fasteners;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import com.stuintech.socketwrench.socket.Socket;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FastenerSocket implements Socket {
    @Override
    public boolean onFasten(PlayerEntity player, LivingEntity entity) throws CancelFasteningException {
        if(entity instanceof FastenerEntity)
            return ((FastenerEntity) entity).onFasten(player, entity);
        return false;
    }

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException {
        Block block = world.getBlockState(pos).getBlock();
        if(block instanceof FastenerBlock)
            return ((FastenerBlock) block).onFasten(player, world, pos, hit, dir);
        return false;
    }
}
