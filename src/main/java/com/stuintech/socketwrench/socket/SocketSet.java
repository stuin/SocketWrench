package com.stuintech.socketwrench.socket;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class SocketSet implements Socket {
    private final ArrayList<Socket> uses = new ArrayList<>();

    public void addUse(Socket action) {
        uses.add(action);
    }

    public boolean contains(Socket action) {
        return uses.contains(action);
    }

    @Override
    public boolean onFasten(PlayerEntity player, LivingEntity entity) throws CancelFasteningException {
        for(Socket action : uses) {
            if(action.onFasten(player, entity))
                return true;
        }
        return false;
    }

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException {
        for(Socket action : uses) {
            if(action.onFasten(player, world, pos, hit, dir))
                return true;
        }
        return false;
    }
}
