package com.stuintech.socketwrenchitem.compat;

import com.stuintech.socketwrench.socket.Socket;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import me.steven.indrev.blocks.machine.MachineBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class IndustrialRevolution extends Socket.BlockActionSocket implements SocketSetLoader {

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d vec3d, Direction direction) {
        BlockState state = world.getBlockState(pos);

        if(player.isSneaking() && state.getBlock() instanceof MachineBlock machineBlock) {
            machineBlock.writeNbtComponents(world, player, pos, state, world.getBlockEntity(pos), player.getActiveItem());
            world.breakBlock(pos, false, player);
            return true;
        }

        return false;
    }

    @Override
    public void registerSockets() {
        SocketSetManager.addSocket(this, SocketSetManager.ADDON_SET_KEY);
    }
}
