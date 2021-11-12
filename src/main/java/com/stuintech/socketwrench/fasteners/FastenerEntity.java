package com.stuintech.socketwrench.fasteners;

import com.stuintech.socketwrench.socket.CancelFasteningException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public interface FastenerEntity {
    //Called by the fastener subset when wrench used on entity
    boolean onFasten(PlayerEntity player, LivingEntity entity) throws CancelFasteningException;
}
