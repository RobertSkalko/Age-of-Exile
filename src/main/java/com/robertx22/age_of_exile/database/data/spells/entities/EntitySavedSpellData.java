package com.robertx22.age_of_exile.database.data.spells.entities;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.UUID;

@Storable
public class EntitySavedSpellData {

    @Store
    String caster_uuid;
    @Store
    String spell_id;
    @Store
    String exile_effect_id;
    @Store
    public String item_id;
    @Store
    public int lvl = 1;

    public SpellStatsCalcEffect.CalculatedSpellConfiguration config = new SpellStatsCalcEffect.CalculatedSpellConfiguration();

    // this is buggy in dev because the player's UUID changes (random name each time game is started)
    // so after restart of game, the caster is null
    // but works fine outside of dev
    public LivingEntity getCaster(World world) {
        return Utilities.getLivingEntityByUUID(world, UUID.fromString(caster_uuid));
    }

    public static EntitySavedSpellData create(int lvl, LivingEntity caster, ExileEffect exEffect) {
        Objects.requireNonNull(caster);

        EntitySavedSpellData data = new EntitySavedSpellData();
        data.exile_effect_id = exEffect.GUID();
        data.lvl = lvl;

        data.caster_uuid = caster.getUuid()
            .toString();

        return data;
    }

    public static EntitySavedSpellData create(int lvl, LivingEntity caster, Spell spell, SpellStatsCalcEffect.CalculatedSpellConfiguration config) {
        Objects.requireNonNull(caster);

        EntitySavedSpellData data = new EntitySavedSpellData();
        data.spell_id = spell.GUID();
        data.config = config;
        data.lvl = lvl;
        data.caster_uuid = caster.getUuid()
            .toString();

        return data;
    }

    public Spell getSpell() {
        return Database.Spells()
            .get(spell_id);
    }

}
