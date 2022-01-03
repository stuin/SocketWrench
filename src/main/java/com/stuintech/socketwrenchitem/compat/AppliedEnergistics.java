package com.stuintech.socketwrenchitem.compat;

import appeng.blockentity.AEBaseBlockEntity;
import com.stuintech.socketwrench.socket.Socket;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AppliedEnergistics extends Socket.BlockActionSocket implements SocketSetLoader {

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d vec3d, Direction direction) {
        if(world.getBlockEntity(pos) instanceof AEBaseBlockEntity baseBlockEntity) {
            BlockHitResult hitResult = new BlockHitResult(vec3d, direction, pos, false);
            if(player.isSneaking())
                baseBlockEntity.disassembleWithWrench(player, world, hitResult);
            else
                baseBlockEntity.rotateWithWrench(player, world, hitResult);
            return true;
        }

        return false;
    }

    @Override
    public void registerSockets() {
        SocketSetManager.addSocket(this, SocketSetManager.ADDON_SET_KEY);
    }
}