package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.event_hooks.player.StopCastingIfInteract;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;

public class RegisterEvents {

    public static void register() {

        AttackEntityCallback.EVENT.register(new StopCastingIfInteract());
    }

}
