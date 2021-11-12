package com.stuintech.socketwrench.item;

import net.minecraft.item.ItemStack;

public interface CancelBlockInteraction {
    boolean shouldCancelInteraction(ItemStack item);
}
