package com.stuintech.socketwrench.socket;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface Socket {
    boolean onFasten(PlayerEntity player, LivingEntity entity) throws CancelFasteningException;
    boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException;

    abstract class BlockActionSocket implements Socket {
        public boolean onFasten(PlayerEntity player, LivingEntity entity) {
            return false;
        }
    }

    abstract class EntityActionSocket implements Socket {
        public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) {
            return false;
        }
    }


}
