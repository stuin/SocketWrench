package com.stuintech.socketwrenchitem;

import com.stuintech.socketwrench.item.BasicWrenchItem;
import com.stuintech.socketwrench.socket.SocketSetLoader;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketWrenchItem implements ModInitializer {
    public static final String MODID = "socketwrenchitem";
    public static final Logger LOGGER = LogManager.getLogger("SocketWrench");

    public static final Item.Settings SETTINGS = new Item.Settings().maxCount(1).group(ItemGroup.TOOLS);
    public static final Item.Settings CREATIVE_SETTINGS = new Item.Settings().maxCount(1).group(ItemGroup.TOOLS).rarity(Rarity.EPIC);
    public static final BasicWrenchItem wrenchItem = new BasicWrenchItem(SETTINGS);

    //Mod integration
    private static final String[][] loadExtensions = new String[][] {
            {
                    "Industrial Revolution",
                    "me.steven.indrev.blocks.machine.MachineBlock",
                    "com.stuintech.socketwrenchitem.compat.IndustrialRevolution"
            },
            {
                    "Reborn Core",
                    "reborncore.api.IToolDrop",
                    "com.stuintech.socketwrenchitem.compat.RebornCore"
            },
            {
                    "Applied Energistics 2",
                    "appeng.blockentity.AEBaseBlockEntity",
                    "com.stuintech.socketwrenchitem.compat.AppliedEnergistics"
            },
            {
                    "Modern Industrializaion",
                    "aztech.modern_industrialization.api.WrenchableBlockEntity",
                    "com.stuintech.socketwrenchitem.compat.ModernIndustrialization"
            },
            {
                    "Immersive Portals",
                    "qouteall.imm_ptl.core.commands.PortalCommand",
                    "com.stuintech.socketwrenchitem.immersiveportals.ImmersivePortals"
            }
    };

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "wrench"), wrenchItem);

        //Try loading extensions
        for(String[] s : loadExtensions) {
            try {
                Class.forName(s[1]);
                ((SocketSetLoader) Class.forName(s[2]).newInstance()).registerSockets();
                LOGGER.info(s[0] + " socket successfully initialized");
            } catch (Exception e) {
                LOGGER.debug(s[0] + " socket not found");
                LOGGER.debug(e);
            }
        }
    }
}
