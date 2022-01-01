package com.stuintech.socketwrench.rotate;

import com.stuintech.socketwrench.socket.Socket;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;

public class RailRotation extends Socket.BlockActionSocket {
    public static final HashMap<RailShape, RailShape> rotateMap = new HashMap();
    public static final HashMap<RailShape, RailShape> flipMap = new HashMap();

    @Override
    public boolean onFasten(PlayerEntity player, World world, BlockPos pos, Vec3d vec3d, Direction direction) {
        BlockState state = world.getBlockState(pos);

        //Rotate straight rail
        if(state.getProperties().contains(Properties.STRAIGHT_RAIL_SHAPE)) {
            RailShape shape1 = state.get(Properties.STRAIGHT_RAIL_SHAPE);
            RailShape shape2 = shape1;

            //Choose new shape
            if(player.isSneaking() && shape1.isAscending())
                shape2 = flipMap.get(flipMap.get(shape1));
            else if(player.isSneaking())
                shape2 = findAscending(world, pos, shape1, flipMap.get(shape1));
            else if(shape2.isAscending())
                shape2 = findAscending(world, pos, shape1, rotateMap.get(shape1));
            else
                shape2 = rotateMap.get(shape1);

            //Apply rotation
            if(shape2 == shape1)
                return false;
            else {
                world.setBlockState(pos, state.with(Properties.STRAIGHT_RAIL_SHAPE, shape2));
                return true;
            }
        } else if(state.getProperties().contains(Properties.RAIL_SHAPE)) {
            RailShape shape1 = state.get(Properties.RAIL_SHAPE);
            RailShape shape2 = shape1;

            //Choose new shape
            if(player.isSneaking() && (isCurved(shape1) || shape1.isAscending()))
                shape2 = flipMap.get(shape1);
            else if(player.isSneaking()) {
                shape2 = findAscending(world, pos, shape1, flipMap.get(shape1));
                if(shape2 == shape1)
                    shape2 = flipMap.get(flipMap.get(shape1));
            } else if(shape2.isAscending())
                shape2 = findAscending(world, pos, shape1, rotateMap.get(shape1));
            else
                shape2 = rotateMap.get(shape1);

            //Apply rotation
            if(shape2 == shape1)
                return false;
            else {
                world.setBlockState(pos, state.with(Properties.RAIL_SHAPE, shape2));
                return true;
            }
        }

        return false;
    }

    private static RailShape findAscending(World world, BlockPos pos, RailShape shape1, RailShape shape2) {
        //Find valid angle
        if(shouldDropRail(pos, world, shape2)) {
            RailShape shape3 = shape2;
            do {
                shape3 = rotateMap.get(shape3);
            } while(shouldDropRail(pos, world, shape3) && shape3 != shape2);
            if(shape3 == shape2)
                return shape1;
            else
                return shape3;
        }
        return shape2;
    }

    private static boolean shouldDropRail(BlockPos pos, World world, RailShape shape) {
        if (!AbstractRailBlock.hasTopRim(world, pos.down())) {
            return true;
        } else {
            return switch(shape) {
                case ASCENDING_EAST -> !AbstractRailBlock.hasTopRim(world, pos.east());
                case ASCENDING_WEST -> !AbstractRailBlock.hasTopRim(world, pos.west());
                case ASCENDING_NORTH -> !AbstractRailBlock.hasTopRim(world, pos.north());
                case ASCENDING_SOUTH -> !AbstractRailBlock.hasTopRim(world, pos.south());
                default -> false;
            };
        }
    }

    private static boolean isCurved(RailShape shape) {
        return shape == RailShape.NORTH_WEST || shape == RailShape.NORTH_EAST || shape == RailShape.SOUTH_EAST || shape == RailShape.SOUTH_WEST;
    }

    static {
        //Rotate within one mode
        rotateMap.put(RailShape.NORTH_SOUTH, RailShape.EAST_WEST);
        rotateMap.put(RailShape.EAST_WEST, RailShape.NORTH_SOUTH);
        rotateMap.put(RailShape.ASCENDING_NORTH, RailShape.ASCENDING_EAST);
        rotateMap.put(RailShape.ASCENDING_EAST, RailShape.ASCENDING_SOUTH);
        rotateMap.put(RailShape.ASCENDING_SOUTH, RailShape.ASCENDING_WEST);
        rotateMap.put(RailShape.ASCENDING_WEST, RailShape.ASCENDING_NORTH);
        rotateMap.put(RailShape.NORTH_WEST, RailShape.NORTH_EAST);
        rotateMap.put(RailShape.NORTH_EAST, RailShape.SOUTH_EAST);
        rotateMap.put(RailShape.SOUTH_EAST, RailShape.SOUTH_WEST);
        rotateMap.put(RailShape.SOUTH_WEST, RailShape.NORTH_WEST);

        //Flip between angled, turned, and straight
        flipMap.put(RailShape.NORTH_SOUTH, RailShape.ASCENDING_NORTH);
        flipMap.put(RailShape.EAST_WEST, RailShape.ASCENDING_WEST);
        flipMap.put(RailShape.ASCENDING_NORTH, RailShape.NORTH_WEST);
        flipMap.put(RailShape.ASCENDING_WEST, RailShape.SOUTH_WEST);
        flipMap.put(RailShape.ASCENDING_SOUTH, RailShape.SOUTH_EAST);
        flipMap.put(RailShape.ASCENDING_EAST, RailShape.NORTH_EAST);
        flipMap.put(RailShape.NORTH_WEST, RailShape.NORTH_SOUTH);
        flipMap.put(RailShape.SOUTH_WEST, RailShape.EAST_WEST);
        flipMap.put(RailShape.SOUTH_EAST, RailShape.NORTH_SOUTH);
        flipMap.put(RailShape.NORTH_EAST, RailShape.EAST_WEST);
    }
}
