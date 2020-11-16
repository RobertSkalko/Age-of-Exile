package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.gui.overlays.bar_overlays.types.RPGGuiOverlay;
import com.robertx22.age_of_exile.gui.overlays.death_stats.DeathStatsOverlay;
import com.robertx22.age_of_exile.gui.overlays.mob_bar.MobBarScreen;
import com.robertx22.age_of_exile.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.age_of_exile.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class Client {

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(new OnClientTick());
        ClientTickEvents.END_CLIENT_TICK.register(new OnKeyPress());

        HudRenderCallback.EVENT.register(new SpellHotbarOverlay());
        HudRenderCallback.EVENT.register(new MobBarScreen());
        HudRenderCallback.EVENT.register(new SpellCastBarOverlay());
        HudRenderCallback.EVENT.register(new RPGGuiOverlay());
        HudRenderCallback.EVENT.register(new DeathStatsOverlay());

    }
}
