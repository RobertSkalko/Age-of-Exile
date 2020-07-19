package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.event_hooks.item.OnTooltip;
import com.robertx22.mine_and_slash.event_hooks.ontick.OnClientTick;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class RegisterClientEvents {

    public static void register() {

        ItemTooltipCallback.EVENT.register(new OnTooltip());
        ClientTickEvents.END_CLIENT_TICK.register(new OnClientTick());

    }
}
