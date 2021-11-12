package com.stuintech.socketwrench.socket;

import com.stuintech.socketwrench.SocketWrench;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketSetManager {
    private static final Map<Identifier, SocketSet> sets = new HashMap<>();

    //Master set of basic wrench actions
    public static final Identifier WRENCH_MASTER_KEY = new Identifier(SocketWrench.MODID, "wrench");

    //Subset for calling fastener interfaces
    public static final Identifier FASTENER_SET_KEY = new Identifier(SocketWrench.MODID, "fastener");

    //Subset for custom wrench addons
    public static final Identifier ADDON_SET_KEY = new Identifier(SocketWrench.MODID, "addon");

    //Subset for rotating blocks
    public static final Identifier ROTATE_SET_KEY = new Identifier(SocketWrench.MODID, "rotate");

    //Blank fallback subset
    public static final Identifier NULL_SET_KEY = new Identifier(SocketWrench.MODID, "null");

    public static SocketSet getSocketSet(Identifier key) {
        return sets.getOrDefault(key, NullSocket.INSTANCE);
    }

    //Simple entity fasten function for items to call
    public static boolean tryFasten(Identifier key, PlayerEntity player, LivingEntity entity) {
        try {
            if(getSocketSet(key).onFasten(player, entity))
                return true;
        } catch(CancelFasteningException e) {
            //No action should be taken
        }
        return false;
    }

    //Simple block fasten function for items to call
    public static boolean tryFasten(Identifier key, PlayerEntity player, World world, BlockPos pos, Vec3d hit, Direction dir) {
        try {
            if(getSocketSet(key).onFasten(player, world, pos, hit, dir))
                return true;
        } catch(CancelFasteningException e) {
            //No action should be taken
        }
        return false;
    }

    //Create new set
    public static SocketSet registerSet(Identifier key) {
        if(!sets.containsKey(key))
            sets.put(key, new SocketSet());
        return sets.get(key);
    }

    public static void registerSetList(List<Identifier> sets) {
        for(Identifier key : sets)
            registerSet(key);
    }

    //Create new set as child of another set
    public static SocketSet addSubSet(Identifier childKey, Identifier parentKey) {
        SocketSet childSet = registerSet(childKey);
        SocketSet parentSet = registerSet(parentKey);

        if(!parentSet.contains(childSet))
            addSocket(childSet, parentKey);
        return childSet;
    }

    //Add new socket to existing set
    public static void addSocket(Socket socket, Identifier key) {
        registerSet(key);
        sets.get(key).addUse(socket);
    }

    //Register default sets in correct order
    static {
        registerSet(NULL_SET_KEY);
        addSubSet(FASTENER_SET_KEY, WRENCH_MASTER_KEY);
        addSubSet(ADDON_SET_KEY, WRENCH_MASTER_KEY);
        addSubSet(ROTATE_SET_KEY, WRENCH_MASTER_KEY);
    }
}
