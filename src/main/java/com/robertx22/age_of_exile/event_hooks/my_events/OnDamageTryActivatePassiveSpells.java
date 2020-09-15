package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellClientToCastSpellPacket;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class OnDamageTryActivatePassiveSpells extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {

        LivingEntity target = event.mob;
        if (target instanceof PlayerEntity) {

            if (event.source.getAttacker() instanceof LivingEntity) {
                if (event.source.getAttacker() instanceof PlayerEntity) {
                    return; // we dont want a chain thunder strike explosion in pvp
                }

                PlayerSpellCap.ISpellsCap spells = Load.spells(target);
                List<Spell> passives = spells
                    .getLearnedSpells(target)
                    .stream()
                    .filter(x -> x.getConfig().passive_config.is_passive)
                    .collect(Collectors.toList());

                passives.forEach(x -> {
                    if (spells.getCastingData()
                        .getDataBySpell(x)
                        .cooldownIsReady()) {
                        SpellCastContext ctx = new SpellCastContext(target, 0, x);
                        if (x.getConfig().passive_config.canCastNow(target)) {
                            Packets.sendToClient((PlayerEntity) target, new TellClientToCastSpellPacket(x));// because this is server only, so client wouldnt know how to cast particles on spell cast
                            x.cast(ctx);
                            spells.getCastingData()
                                .onSpellCast(ctx);
                        }
                    }
                });

            }
        }
    }
}
