package com.stuintech.socketwrench.fasteners;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface FastenerBlock {
    //Called by the fastener subset when wrench used on block
    boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException;
}
