package com.robertx22.age_of_exile.mmorpg;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        Mixins.addConfiguration(
            "mmorpg.mixins.json"
        );
    }
}