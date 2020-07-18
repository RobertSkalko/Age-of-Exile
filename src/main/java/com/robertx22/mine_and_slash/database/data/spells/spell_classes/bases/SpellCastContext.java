package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.EntityCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.saveclasses.spells.IAbility;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;

public class SpellCastContext {

    public final LivingEntity caster;
    public final EntityCap.UnitData data;
    public final PlayerSpellCap.ISpellsCap spellsCap;
    public final int ticksInUse;
    public final BaseSpell spell;
    public final IAbility ability;
    public boolean isLastCastTick;
    public boolean castedThisTick = false;

    public EntityCalcSpellConfigs configForSummonedEntities;

    private HashMap<String, PreCalcSpellConfigs> cacheMap = new HashMap<>();

    public PreCalcSpellConfigs getConfigFor(IAbility ability) {

        if (!cacheMap.containsKey(ability.GUID())) {

            PreCalcSpellConfigs pre = ability.getPreCalcConfig();

            if (ability.getAbilityType() == IAbility.Type.SPELL) {
                pre.modifyByUserStats(this);
            }

            cacheMap.put(ability.GUID(), pre);
        }

        return cacheMap.get(ability.GUID());
    }

    public SpellCastContext(LivingEntity caster, int ticksInUse, IAbility ability) {
        this.caster = caster;
        this.ticksInUse = ticksInUse;

        this.ability = ability;

        this.data = Load.Unit(caster);

        if (caster instanceof PlayerEntity) {
            this.spellsCap = Load.spells((PlayerEntity) caster);
        } else {
            this.spellsCap = new PlayerSpellCap.DefaultImpl();
        }

        this.configForSummonedEntities = new EntityCalcSpellConfigs(data, spellsCap, ability);

        this.spell = ability.getSpell();

        if (spell != null) {
            int castTicks = (int) getConfigFor(spell).get(SC.CAST_TIME_TICKS)
                .get(spellsCap, spell);
            this.isLastCastTick = castTicks == ticksInUse;
        }
    }
}
