package com.stuintech.socketwrench;

import com.stuintech.socketwrench.fasteners.FastenerSocket;
import com.stuintech.socketwrench.rotate.FacingRotation;
import com.stuintech.socketwrench.rotate.PropertyRotation;
import com.stuintech.socketwrench.rotate.RailRotation;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.GameRules;

import java.util.List;

public class SocketWrench implements ModInitializer {
    public static final String MODID = "socketwrench";

    public static GameRules.Key<GameRules.BooleanRule> SOCKET_WRENCH_DURABILITY;

    @Override
    public void onInitialize() {
        //Register builtin socket actions
        SocketSetManager.addSocket(new FastenerSocket(), SocketSetManager.FASTENER_SET_KEY);
        SocketSetManager.addSocket(new RailRotation(), SocketSetManager.ROTATE_SET_KEY);
        SocketSetManager.addSocket(new PropertyRotation(), SocketSetManager.ROTATE_SET_KEY);
        SocketSetManager.addSocket(new FacingRotation(), SocketSetManager.ROTATE_SET_KEY);

        //Register durability gamerule
        SOCKET_WRENCH_DURABILITY = GameRuleRegistry.register("socketWrenchDurability",
                GameRules.Category.PLAYER, GameRuleFactory.createBooleanRule(false));

        //Check for additional entrypoints
        List<SocketSetLoader> entries = FabricLoader.getInstance().getEntrypoints(MODID, SocketSetLoader.class);
        for(SocketSetLoader loader : entries)
            loader.registerSockets();
    }
}
