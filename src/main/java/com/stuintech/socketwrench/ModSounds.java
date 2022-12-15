package com.stuintech.socketwrench;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final Identifier wrenchSoundID = new Identifier(SocketWrench.MODID, "default_wrench");
    public static final Identifier modeSoundID = new Identifier(SocketWrench.MODID, "default_mode_change");
    public static final SoundEvent wrenchSound = new SoundEvent(wrenchSoundID);
    public static final SoundEvent modeSound = new SoundEvent(modeSoundID);

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, wrenchSoundID, wrenchSound);
        Registry.register(Registry.SOUND_EVENT, modeSoundID, modeSound);
    }
}
