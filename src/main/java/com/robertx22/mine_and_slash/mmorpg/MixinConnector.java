package com.robertx22.mine_and_slash.mmorpg;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;

public class MixinConnector implements IMixinConnector {

    @Override
    public void connect() {
        System.out.println("Connecting " + Ref.MOD_NAME + " Mixins");

        Mixins.addConfiguration(
            "assets/mmorpg/mmorpg-mixins.json"
        );

    }
}
