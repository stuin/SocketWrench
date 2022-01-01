package com.stuintech.socketwrench;

import com.stuintech.socketwrench.fasteners.FastenerSocket;
import com.stuintech.socketwrench.rotate.FacingRotation;
import com.stuintech.socketwrench.rotate.PropertyRotation;
import com.stuintech.socketwrench.rotate.RailRotation;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import java.util.List;

public class SocketWrench implements ModInitializer {
    public static final String MODID = "socketwrench";

    @Override
    public void onInitialize() {
        //Register builtin socket actions
        SocketSetManager.addSocket(new FastenerSocket(), SocketSetManager.FASTENER_SET_KEY);
        SocketSetManager.addSocket(new RailRotation(), SocketSetManager.ROTATE_SET_KEY);
        SocketSetManager.addSocket(new PropertyRotation(), SocketSetManager.ROTATE_SET_KEY);
        SocketSetManager.addSocket(new FacingRotation(), SocketSetManager.ROTATE_SET_KEY);

        //Check for additional entrypoints
        List<SocketSetLoader> entries = FabricLoader.getInstance().getEntrypoints(MODID, SocketSetLoader.class);
        for(SocketSetLoader loader : entries)
            loader.registerSockets();
    }
}
