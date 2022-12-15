package com.stuintech.socketwrench;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final Identifier wrenchSoundID = new Identifier(SocketWrench.MODID, "default_wrench");
    public static final Identifier modeSoundID = new Identifier(SocketWrench.MODID, "default_mode_change");
    public static final SoundEvent wrenchSound = SoundEvent.of(wrenchSoundID);
    public static final SoundEvent modeSound = SoundEvent.of(modeSoundID);

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, wrenchSoundID, wrenchSound);
        Registry.register(Registries.SOUND_EVENT, modeSoundID, modeSound);
    }
}
