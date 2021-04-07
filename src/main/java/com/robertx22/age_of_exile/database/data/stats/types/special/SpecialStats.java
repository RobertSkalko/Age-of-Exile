package com.robertx22.age_of_exile.database.data.stats.types.special;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpecialStatDamageEffect;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.util.Formatting;

public class SpecialStats {

    public static void init() {

    }

    public static String VAL1 = "[VAL1]";
    public static String VAL2 = "[VAL2]";

    static Formatting FORMAT = Formatting.GRAY;
    static Formatting NUMBER = Formatting.GREEN;

    public static SpecialStat CRIT_BURN = new SpecialStat("crit_burn",
        FORMAT + "Your " + Elements.Fire.format + Elements.Fire.icon + " Fire" + FORMAT + " Spell Critical Hits have " + NUMBER + VAL1 + "% " + FORMAT + "chance to cause enemies to burn.",

        new BaseSpecialStatDamageEffect() {
            @Override
            public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
                ExileEffectsManager.apply(effect.sourceData.getLevel(), Database.ExileEffects()
                    .get(NegativeEffects.BURN), effect.source, effect.target, 20 * 10);
                return effect;
            }

            @Override
            public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
                return effect.attackType.isSpell() && effect.element.isFire() && RandomUtils.roll(data.getAverageValue());
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }
        }
    );
}
