package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;

public class DamageUnderPotion extends Stat {

    public static DamageUnderPotion HUNGER = new DamageUnderPotion(StatusEffects.HUNGER, "hunger");
    public static DamageUnderPotion POISON = new DamageUnderPotion(StatusEffects.POISON, "poison");
    public static DamageUnderPotion WITHER = new DamageUnderPotion(StatusEffects.WITHER, "wither");

    StatusEffect status;
    String id;

    private DamageUnderPotion(StatusEffect status, String id) {
        this.status = status;
        this.id = id;
        this.statEffect = new Effect(status);
        this.is_percent = true;

        this.isLongStat = true;

    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your damage if you are under this status effect.";
    }

    @Override
    public String locNameForLangFile() {
        return "You deal " + SpecialStats.VAL1 + "% increased " + "Damage while under the effects of " + CLOC.translate(status.getName());
    }

    @Override
    public String GUID() {
        return "dmg_under_" + id;
    }

    private static class Effect extends BaseDamageIncreaseEffect {
        StatusEffect status;

        public Effect(StatusEffect status) {
            this.status = status;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.source.hasStatusEffect(status);
        }

    }
}
