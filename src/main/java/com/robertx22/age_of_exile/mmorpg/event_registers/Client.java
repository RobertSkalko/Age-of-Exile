package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.event_hooks.ontick.OnClientEntityTick;
import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.gui.overlays.bar_overlays.types.VanillaOverlay;
import com.robertx22.age_of_exile.gui.overlays.mob_bar.MobBarScreen;
import com.robertx22.age_of_exile.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.age_of_exile.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class Client {

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(new OnClientTick());
        ClientTickEvents.END_CLIENT_TICK.register(new OnKeyPress());

        ExileEvents.LIVING_ENTITY_TICK.register(new OnClientEntityTick());

        HudRenderCallback.EVENT.register(new VanillaOverlay());
        HudRenderCallback.EVENT.register(new SpellHotbarOverlay());
        HudRenderCallback.EVENT.register(new MobBarScreen());
        HudRenderCallback.EVENT.register(new SpellCastBarOverlay());

    }
}
