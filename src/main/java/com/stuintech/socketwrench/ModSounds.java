package com.stuintech.socketwrench;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final Identifier wrenchSoundID = new Identifier(SocketWrench.MODID, "default_wrench");
    public static final SoundEvent wrenchSound = new SoundEvent(wrenchSoundID);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, wrenchSoundID, wrenchSound);
    }
}
