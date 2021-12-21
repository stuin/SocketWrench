package com.stuintech.socketwrench.rotate;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import com.stuintech.socketwrench.socket.Socket;
import net.minecraft.block.*;
import net.minecraft.block.enums.ChestType;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;

public class PropertyRotation extends Socket.BlockActionSocket {
    public static final ArrayList<Property<?>> facingProps = new ArrayList<>();
    public static final ArrayList<Property<?>> secondaryProps = new ArrayList<>();
    public static final HashMap<Direction, Direction> horizontalOrder = new HashMap<>();
    public static final HashMap<Direction, Direction> hopperOrder = new HashMap<>();
    public static final HashMap<SlabType, SlabType> slabOrder = new HashMap<>();

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) throws CancelFasteningException {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        //Cancel on op blocks
        if(block instanceof OperatorBlock && !player.isCreativeLevelTwoOp())
            throw new CancelFasteningException();

        //Skip portal parts
        if(block instanceof NetherPortalBlock || block instanceof EndPortalFrameBlock)
            return false;

        //Skip horizontal double blocks
        if(block instanceof BedBlock || (block instanceof ChestBlock && state.get(Properties.CHEST_TYPE) != ChestType.SINGLE))
            return false;

        for(Property<?> p : state.getProperties())
            if((!player.isSneaking() && facingProps.contains(p)) || (player.isSneaking() && secondaryProps.contains(p))) {
                //Find valid rotation
                BlockState state2 = cycle(state, p);
                while(!state2.canPlaceAt(world, pos) && state2 != state)
                    state2 = cycle(state2, p);

                if(state2.equals(state))
                    return false;

                //Handle door
                if(block instanceof DoorBlock) {
                    BlockPos other = pos.offset(state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER ? Direction.UP : Direction.DOWN);
                    world.setBlockState(other, world.getBlockState(other).with(Properties.HORIZONTAL_FACING, state2.get(Properties.HORIZONTAL_FACING)));
                }

                //Apply rotation
                world.setBlockState(pos, state2);
                world.updateNeighbor(pos, state2.getBlock(), pos);
                return true;
            }

        return false;
    }

    @Override
    public boolean checksSneaking() {
        return true;
    }

    public BlockState cycle(BlockState state, Property property) {
        if(property == Properties.HORIZONTAL_FACING)
            return state.with(property, horizontalOrder.get(state.get(property)));
        if(property == Properties.HOPPER_FACING)
            return state.with(property, hopperOrder.get(state.get(property)));
        if(property == Properties.SLAB_TYPE)
            return state.with(property, slabOrder.get(state.get(property)));
        return state.cycle(property);
    }

    static {
        facingProps.add(Properties.AXIS);
        facingProps.add(Properties.HORIZONTAL_AXIS);
        facingProps.add(Properties.HORIZONTAL_FACING);
        facingProps.add(Properties.HOPPER_FACING);
        facingProps.add(Properties.STRAIGHT_RAIL_SHAPE);
        facingProps.add(Properties.ORIENTATION);
        facingProps.add(Properties.SLAB_TYPE);

        //Should move to dedicated class
        facingProps.add(Properties.RAIL_SHAPE);
        facingProps.add(Properties.ROTATION);

        //When sneaking
        secondaryProps.add(Properties.WALL_MOUNT_LOCATION);
        secondaryProps.add(Properties.DOOR_HINGE);
        secondaryProps.add(Properties.ATTACHMENT);
        secondaryProps.add(Properties.BLOCK_HALF);

        //Horizontal direction order
        horizontalOrder.put(Direction.NORTH, Direction.EAST);
        horizontalOrder.put(Direction.EAST, Direction.SOUTH);
        horizontalOrder.put(Direction.SOUTH, Direction.WEST);
        horizontalOrder.put(Direction.WEST, Direction.NORTH);

        //Hopper Direction order
        hopperOrder.putAll(horizontalOrder);
        hopperOrder.replace(Direction.WEST, Direction.DOWN);
        hopperOrder.put(Direction.DOWN, Direction.NORTH);

        //Custom Slab order
        slabOrder.put(SlabType.DOUBLE, SlabType.DOUBLE);
        slabOrder.put(SlabType.BOTTOM, SlabType.TOP);
        slabOrder.put(SlabType.TOP, SlabType.BOTTOM);
    }
}
