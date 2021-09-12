package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.value_calc.LevelProvider;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class SpellStatsCalculationEvent extends EffectEvent {
    public static String ID = "on_spell_stat_calc";

    @Override
    public String GUID() {
        return ID;
    }

    int lvl;

    public EntitySavedSpellData savedData;

    public SpellStatsCalculationEvent(EntitySavedSpellData savedData, LivingEntity caster, String spellid) {
        super(caster, caster);

        this.savedData = savedData;
        this.lvl = Load.Unit(caster)
            .getLevel();

        Spell spell = ExileDB.Spells()
            .get(spellid);
        if (spell.config.scale_mana_cost_to_player_lvl) {
            lvl = this.sourceData.getLevel();
        }

        this.data.setString(EventData.STYLE, spell.config.style.name());

        this.data.setString(EventData.SPELL, spellid);

        float manamultilvl = GameBalanceConfig.get().MANA_COST_SCALING.getMultiFor(lvl);

        this.data.setupNumber(EventData.CAST_TICKS, spell.config.getCastTimeTicks());
        this.data.setupNumber(EventData.MANA_COST, manamultilvl * spell.config.mana_cost.getValue(new LevelProvider(caster, spell)));
        this.data.setupNumber(EventData.COOLDOWN_TICKS, spell.config.cooldown_ticks);
        this.data.setupNumber(EventData.PROJECTILE_SPEED_MULTI, 1F);
        this.data.setupNumber(EventData.AREA_MULTI, 1);

    }

    @Override
    protected void activate() {

        int cd = (int) MathHelper.clamp(data.getNumber(EventData.COOLDOWN_TICKS).number, getSpell().config.cooldown_ticks * 0.2D, 1000000);
        this.data.getNumber(EventData.COOLDOWN_TICKS).number = cd; // cap it to 80% cooldown

        savedData.proj_speed_multi = data.getNumber(EventData.PROJECTILE_SPEED_MULTI).number;
        savedData.pierce = data.getBoolean(EventData.PIERCE);
        savedData.area_multi = data.getNumber(EventData.AREA_MULTI).number;
        savedData.lvl = lvl;

    }

}
