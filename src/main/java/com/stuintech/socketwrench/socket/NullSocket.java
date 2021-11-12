package com.stuintech.socketwrench.socket;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NullSocket extends SocketSet {
    public static final SocketSet INSTANCE = new NullSocket();

    @Override
    public boolean onFasten(PlayerEntity player, LivingEntity entity) throws CancelFasteningException {
        return false;
    }

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException {
        return false;
    }
}
