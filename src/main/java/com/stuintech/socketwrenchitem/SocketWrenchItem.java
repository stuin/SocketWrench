package com.stuintech.socketwrenchitem;

import com.stuintech.socketwrench.item.BasicWrenchItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketWrenchItem implements ModInitializer {
    public static final String MODID = "socketwrenchitem";
    public static final Logger LOGGER = LogManager.getLogger("SonicDevices");

    public static final Item.Settings SETTINGS = new Item.Settings().maxCount(1).group(ItemGroup.TOOLS);
    public static final BasicWrenchItem wrenchItem = new BasicWrenchItem(SETTINGS);

    //Mod integration
    private static final String[][] loadExtensions = new String[][] {
            {
                    "Industrial Revolution",
                    "me.steven.indrev.blocks.machine.MachineBlock",
                    "com.stuintech.sonicdevices.compat.IndustrialRevolution"
            }
    };

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "wrench"), wrenchItem);

        //Try loading extensions
        for(String[] s : loadExtensions) {
            try {
                Class.forName(s[1]);
                ((ILoader) Class.forName(s[2]).newInstance()).onInitialize();
                LOGGER.info(s[0] + " extension successfully initialized");
            } catch (Exception e) {
                LOGGER.debug(s[0] + "extension not found");
            }
        }
    }
}
