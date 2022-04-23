package com.robertx22.age_of_exile.mmorpg.event_registers;

import com.robertx22.age_of_exile.a_libraries.neat.AnotherTry;
import com.robertx22.age_of_exile.event_hooks.ontick.OnClientTick;
import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.gui.overlays.bar_overlays.types.RPGGuiOverlay;
import com.robertx22.age_of_exile.gui.overlays.spell_cast_bar.SpellCastBarOverlay;
import com.robertx22.age_of_exile.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;

public class Client {

    public static void register() {

        SpellHotbarOverlay spellHotbarOverlay = new SpellHotbarOverlay();
        SpellCastBarOverlay castbar = new SpellCastBarOverlay();
        RPGGuiOverlay rpggui = new RPGGuiOverlay();

        AnotherTry.register();

        ForgeEvents.registerForgeEvent(TickEvent.ClientTickEvent.class, event -> {
            if (event.phase == TickEvent.Phase.END) {
                OnClientTick.onEndTick(Minecraft.getInstance());
                OnKeyPress.onEndTick(Minecraft.getInstance());
            }
        });

        // todo does this work?
        ForgeEvents.registerForgeEvent(RenderGameOverlayEvent.class, event -> {

            if (event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
                return;
            }

            spellHotbarOverlay.onHudRender(event.getMatrixStack(), event.getPartialTicks());
            castbar.onHudRender(event.getMatrixStack(), event.getPartialTicks());
            rpggui.onHudRender(event.getMatrixStack(), event.getPartialTicks());

        });
    }
}
