package com.stuintech.socketwrench.socket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ExtensionSocket extends Socket.BlockActionSocket {
    private final Socket socket;

    public ExtensionSocket(Socket socket) {
        this.socket = socket;
    }

    public ExtensionSocket(Identifier key) {
        this.socket = SocketSetManager.getSocketSet(key);
    }

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException {
        return (!player.isSneaking() || socket.checksSneaking()) && socket.onFasten(player, world, pos.offset(dir.getOpposite()), hit, dir);
    }

    @Override
    public boolean checksSneaking() {
        return true;
    }
}
