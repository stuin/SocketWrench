package com.stuintech.socketwrench.rotate;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import com.stuintech.socketwrench.socket.Socket;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FacingRotation extends Socket.BlockActionSocket {
    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        //Cancel on op blocks
        if(block instanceof OperatorBlock && !player.isCreativeLevelTwoOp())
            throw new CancelFasteningException();

        //Skip on extended piston parts
        if(block instanceof PistonHeadBlock || block instanceof PistonExtensionBlock ||
                (block instanceof PistonBlock && state.get(PistonBlock.EXTENDED)))
            return false;

        //Rotate
        if(state.getProperties().contains(Properties.FACING)) {
            //Find valid rotation
            BlockState state2 = state;
            if(state.get(Properties.FACING).getAxis() == dir.getAxis()) {
                state2 = state2.with(Properties.FACING, state2.get(Properties.FACING).getOpposite());
            } else {
                do {
                    state2 = state2.with(Properties.FACING, state2.get(Properties.FACING).rotateClockwise(dir.getAxis()));
                } while(!state2.canPlaceAt(world, pos) && state2 != state);
            }

            if(state2.equals(state))
                return false;

            //Apply rotation
            world.setBlockState(pos, state2);
            world.updateNeighbor(pos, state.getBlock(), pos);
            return true;
        }

        return false;
    }
}
