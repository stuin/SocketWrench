package com.stuintech.socketwrenchitem.immersiveportals;

import com.stuintech.socketwrench.item.ModeWrenchItem;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import com.stuintech.socketwrench.socket.SocketSetManager;
import com.stuintech.socketwrenchitem.SocketWrenchItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;

public class ImmersivePortals implements SocketSetLoader {
    public static final Identifier POSITION_MODE = new Identifier(SocketWrenchItem.MODID, "portal.position");
    public static final Identifier HORIZONTAL_MODE = new Identifier(SocketWrenchItem.MODID, "portal.horizontal");
    public static final Identifier VERTICAL_MODE = new Identifier(SocketWrenchItem.MODID, "portal.vertical");
    public static final ArrayList<Identifier> PORTAL_MODES = new ArrayList<>();

    public static final ModeWrenchItem portalToolItem = new PortalTool(SocketWrenchItem.CREATIVE_SETTINGS, PORTAL_MODES);

    @Override
    public void registerSockets() {
        SocketSetManager.addSocket(new PositionSocket(), POSITION_MODE);
        SocketSetManager.addSocket(new HorizontalRotationSocket(), HORIZONTAL_MODE);
        SocketSetManager.addSocket(new VerticalRotationSocket(), VERTICAL_MODE);

        Registry.register(Registry.ITEM, new Identifier(SocketWrenchItem.MODID, "portal_tool"), portalToolItem);
    }

    static {
        PORTAL_MODES.add(POSITION_MODE);
        PORTAL_MODES.add(HORIZONTAL_MODE);
        PORTAL_MODES.add(VERTICAL_MODE);
    }
}
