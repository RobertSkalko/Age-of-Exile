package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.event_hooks.item.OnTooltip;
import com.robertx22.mine_and_slash.event_hooks.ontick.OnClientTick;
import com.robertx22.mine_and_slash.event_hooks.player.OnKeyPress;
import com.robertx22.mine_and_slash.gui.overlays.bar_overlays.types.VanillaOverlay;
import com.robertx22.mine_and_slash.gui.overlays.mob_bar.MobBarScreen;
import com.robertx22.mine_and_slash.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.mine_and_slash.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class RegisterClientEvents {

    public static void register() {

        ItemTooltipCallback.EVENT.register(new OnTooltip());
        ClientTickEvents.END_CLIENT_TICK.register(new OnClientTick());
        ClientTickEvents.END_CLIENT_TICK.register(new OnKeyPress());

        HudRenderCallback.EVENT.register(new VanillaOverlay());
        HudRenderCallback.EVENT.register(new SpellHotbarOverlay());
        HudRenderCallback.EVENT.register(new MobBarScreen());
        HudRenderCallback.EVENT.register(new SpellCastBarOverlay());

    }
}
