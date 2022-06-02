package com.stuintech.socketwrenchitem.immersiveportals;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import com.stuintech.socketwrench.socket.Socket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.commands.PortalCommand;
import qouteall.imm_ptl.core.portal.Portal;

public class PositionSocket implements Socket {

    @Override
    public boolean onFasten(PlayerEntity player, LivingEntity livingEntity) throws CancelFasteningException {
        return movePortal(player);
    }

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos blockPos, Vec3d vec3d, Direction direction) throws CancelFasteningException {
        return movePortal(player);
    }

    public boolean movePortal(PlayerEntity player) throws CancelFasteningException {
        if(!player.hasPermissionLevel(2) && !player.isCreative())
            throw new CancelFasteningException();

        if(!player.world.isClient) {
            double distance = (player.isSneaking()) ? -0.2 : 0.2;
            Vec3d facing = player.getRotationVector();
            Portal portal = PortalCommand.getPlayerPointingPortal((ServerPlayerEntity) player, false);

            double x = Math.abs(facing.x);
            double y = Math.abs(facing.y);
            double z = Math.abs(facing.z);
            int x2 = ((x >= y) && (x >= z)) ? (facing.x < 0 ? -1 : 1) : 0;
            int y2 = ((y > x) && (y > z)) ? (facing.y < 0 ? -1 : 1) : 0;
            int z2 = ((z > x) && (z >= y)) ? (facing.z < 0 ? -1 : 1) : 0;

            if(portal != null) {
                portal.setPosition(
                        portal.getX() + x2 * distance,
                        portal.getY() + y2 * distance,
                        portal.getZ() + z2 * distance
                );
                portal.rectifyClusterPortals();
                portal.reloadAndSyncToClient();
                return true;
            }
        }
        return false;
    }
}
