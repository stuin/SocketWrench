package com.stuintech.socketwrenchitem.immersiveportals;

import com.stuintech.socketwrench.item.ModeWrenchItem;
import com.stuintech.socketwrenchitem.SocketWrenchItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.List;

public class PortalTool extends ModeWrenchItem {
    public PortalTool(Settings settings, List<Identifier> modes) {
        super(settings, modes);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext tooltipContext) {
        list.add(getModeName(stack));
        //list.add(new TranslatableText("mode." + SocketWrenchItem.MODID + "." + getSocketSet(stack).getPath() + ".hint"));
    }
}
