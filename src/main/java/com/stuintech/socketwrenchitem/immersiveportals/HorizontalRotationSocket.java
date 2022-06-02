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
import qouteall.q_misc_util.my_util.DQuaternion;

public class HorizontalRotationSocket implements Socket {

    @Override
    public boolean onFasten(PlayerEntity player, LivingEntity livingEntity) throws CancelFasteningException {
        return rotatePortal(player);
    }

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos blockPos, Vec3d vec3d, Direction direction) throws CancelFasteningException {
        return rotatePortal(player);
    }

    public boolean rotatePortal(PlayerEntity player) throws CancelFasteningException {
        if(!player.hasPermissionLevel(2) && !player.isCreative())
            throw new CancelFasteningException();

        if(!player.world.isClient) {
            float angle = (player.isSneaking()) ? -15 : 15;
            Portal portal = PortalCommand.getPlayerPointingPortal((ServerPlayerEntity) player, false);
            DQuaternion quaternion = DQuaternion.fromMcQuaternion(Direction.UP.getUnitVector().getDegreesQuaternion(angle));

            if(portal != null) {
                portal.axisW = quaternion.rotate(portal.axisW);
                portal.axisH = quaternion.rotate(portal.axisH);
                portal.setRotationTransformationD(portal.getRotationD().hamiltonProduct(quaternion.getConjugated()));

                portal.rectifyClusterPortals();
                portal.reloadAndSyncToClient();
                return true;
            }
        }
        return false;
    }
}
