package com.stuintech.socketwrenchitem;

import com.stuintech.socketwrench.item.BasicWrenchItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SocketWrenchItem implements ModInitializer {
    public static final String MODID = "socketwrenchitem";
    public static final Item.Settings SETTINGS = new Item.Settings().maxCount(1).group(ItemGroup.TOOLS);
    public static final BasicWrenchItem wrenchItem = new BasicWrenchItem(SETTINGS);

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MODID, "wrench"), wrenchItem);
    }
}
