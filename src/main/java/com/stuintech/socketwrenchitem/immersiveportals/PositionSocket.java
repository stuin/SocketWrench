package com.stuintech.socketwrenchitem.immersiveportals;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import com.stuintech.socketwrench.socket.Socket;
import com.stuintech.socketwrenchitem.SocketWrenchItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.IPGlobal;
import qouteall.imm_ptl.core.commands.PortalCommand;
import qouteall.imm_ptl.core.portal.Portal;

public class PositionSocket implements Socket {

    @Override
    public boolean onFasten(PlayerEntity player, LivingEntity livingEntity) throws CancelFasteningException {
        if(!player.hasPermissionLevel(2) && !player.isCreative())
            throw new CancelFasteningException();

        if(!player.world.isClient) {
            double distance = (player.isSneaking()) ? 1 : -1;
            Vec3d dir = player.getRotationVector();

            Portal portal = PortalCommand.getPlayerPointingPortal((ServerPlayerEntity) player, false);
            SocketWrenchItem.LOGGER.warn("Moving portal");
            SocketWrenchItem.LOGGER.warn(portal);
            if(portal != null) {
                portal.setPosition(
                        portal.getX() + dir.x * distance,
                        portal.getY() + dir.y * distance,
                        portal.getZ() + dir.z * distance
                );
                portal.rectifyClusterPortals();
                portal.reloadAndSyncToClient();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos blockPos, Vec3d vec3d, Direction direction) throws CancelFasteningException {
        if(!IPGlobal.easeCommandStickPermission && !player.hasPermissionLevel(2) && !player.isCreative())
            throw new CancelFasteningException();

        if(!player.world.isClient) {
            double distance = (player.isSneaking()) ? 1 : -1;
            Vec3d dir = player.getRotationVector();

            Portal portal = PortalCommand.getPlayerPointingPortal((ServerPlayerEntity) player, false);
            if(portal != null) {
                portal.setPosition(
                        portal.getX() + dir.x * distance,
                        portal.getY() + dir.y * distance,
                        portal.getZ() + dir.z * distance
                );
                portal.rectifyClusterPortals();
                portal.reloadAndSyncToClient();
                return true;
            }
        }
        return false;
    }
}
