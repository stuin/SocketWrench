package com.stuintech.socketwrenchitem.immersiveportals;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import com.stuintech.socketwrench.socket.Socket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import qouteall.imm_ptl.core.commands.PortalCommand;
import qouteall.imm_ptl.core.portal.Portal;
import qouteall.q_misc_util.my_util.DQuaternion;

public class VerticalRotationSocket implements Socket {

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
            Vec3d facing = player.getRotationVector();
            Portal portal = PortalCommand.getPlayerPointingPortal((ServerPlayerEntity) player, false);

            double x = Math.abs(facing.x);
            double z = Math.abs(facing.z);
            int x2 = (x >= z) ? 0 : (facing.x < 0 ? -1 : 1);
            int z2 = (z > x) ? 0 : (facing.z < 0 ? -1 : 1);
            Vec3f dir = new Vec3f(x2, 0, z2);

            DQuaternion quaternion = DQuaternion.fromMcQuaternion(dir.getDegreesQuaternion(angle));

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
