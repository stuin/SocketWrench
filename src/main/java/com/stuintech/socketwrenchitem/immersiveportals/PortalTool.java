package com.stuintech.socketwrenchitem.immersiveportals;

import com.stuintech.socketwrench.item.ModeWrenchItem;
import com.stuintech.socketwrench.socket.SocketSetManager;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class PortalTool extends ModeWrenchItem {
    public PortalTool(Settings settings, List<Identifier> modes) {
        super(settings, modes);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (SocketSetManager.tryFasten(this.getSocketSet(itemStack), player, player)) {
            this.postFasten(itemStack, player);
            return TypedActionResult.success(itemStack);
        } else if (player.isSneaking()) {
            this.cycleModes(itemStack, player, 1);
            return TypedActionResult.success(itemStack);
        } else {
            return TypedActionResult.fail(itemStack);
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext tooltipContext) {
        list.add(Text.translatable(getModeName(stack).getKey()).formatted(Formatting.GRAY));
        MutableText text = Text.translatable(getModeName(stack).getKey() + ".hint");
        list.add(text.formatted(Formatting.GRAY));
    }
}
