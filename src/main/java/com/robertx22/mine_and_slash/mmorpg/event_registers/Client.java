package com.robertx22.mine_and_slash.mmorpg.event_registers;

import com.robertx22.mine_and_slash.event_hooks.ontick.OnClientTick;
import com.robertx22.mine_and_slash.event_hooks.player.OnKeyPress;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class Client {

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(new OnClientTick());
        ClientTickEvents.END_CLIENT_TICK.register(new OnKeyPress());

        //HudRenderCallback.EVENT.register(new VanillaOverlay());
        //HudRenderCallback.EVENT.register(new SpellHotbarOverlay());
        //HudRenderCallback.EVENT.register(new MobBarScreen());
        //HudRenderCallback.EVENT.register(new SpellCastBarOverlay());

    }
}
