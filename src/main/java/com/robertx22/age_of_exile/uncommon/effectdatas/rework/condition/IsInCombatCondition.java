package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import net.minecraft.entity.LivingEntity;

public class IsInCombatCondition extends StatCondition {

    public IsInCombatCondition() {
        super("is_in_combat", "is_in_combat");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        LivingEntity en = event.getSide(statSource);
        return Load.Unit(en)
            .getCooldowns()
            .isOnCooldown(CooldownsData.IN_COMBAT);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsInCombatCondition.class;
    }

}
