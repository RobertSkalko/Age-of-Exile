package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RightClickSpell {

    @SubscribeEvent
    public static void onClick(PlayerInteractEvent.RightClickItem event) {

        try {

            PlayerEntity player = event.getPlayer();

            if (player.world.isClient) {
                return;
            }

            BaseSpell spell = Load.spells(player)
                .getCurrentRightClickSpell();

            if (spell != null) {

                PlayerSpellCap.ISpellsCap spells = Load.spells(player);

                if (spells.getCastingData()
                    .isCasting()) {
                    return;
                }

                spells
                    .getCastingData()
                    .setToCast(spell, player);

                SpellCastContext ctx = new SpellCastContext(player, 0, spell);

                spell.spendResources(ctx);

            }

        } catch (
            Exception e) {
            e.printStackTrace();
        }

    }
}
