package com.stuintech.socketwrenchitem.compat;

import aztech.modern_industrialization.api.WrenchableBlockEntity;
import com.stuintech.socketwrench.socket.Socket;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModernIndustrialization extends Socket.BlockActionSocket implements SocketSetLoader {

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d vec3d, Direction direction) {
        if(player.isSneaking() && world.getBlockEntity(pos) instanceof WrenchableBlockEntity wrenchBlockEntity)
            return wrenchBlockEntity.useWrench(player, player.getActiveHand(), new BlockHitResult(vec3d, direction, pos, false));

        return false;
    }

    @Override
    public void registerSockets() {
        SocketSetManager.addSocket(this, SocketSetManager.ADDON_SET_KEY);
    }
}
