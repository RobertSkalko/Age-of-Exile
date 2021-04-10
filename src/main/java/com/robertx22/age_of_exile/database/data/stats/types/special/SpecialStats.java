package com.robertx22.age_of_exile.database.data.stats.types.special;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectsManager;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpecialStatDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.mixins.StatusEffectAccessor;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

public class SpecialStats {

    public static void init() {

    }

    public static String VAL1 = "[VAL1]";
    public static String VAL2 = "[VAL2]";

    static Formatting FORMAT = Formatting.GRAY;
    static Formatting NUMBER = Formatting.GREEN;

    static String format(String str) {

        str = FORMAT + str;

        str = str.replace(VAL1, NUMBER + VAL1 + FORMAT);
        str = str.replace(VAL2, NUMBER + VAL2 + FORMAT);

        return str;
    }

    public static SpecialStat CRIT_BURN = new SpecialStat("crit_burn",
        format("Your " + Elements.Fire.getIconNameFormat() + " Critical Hits have " + VAL1 + "% " + "chance to cause enemies to burn."),

        new BaseSpecialStatDamageEffect() {
            @Override
            public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
                ExileEffectsManager.apply(effect.sourceData.getLevel(), Database.ExileEffects()
                    .get(NegativeEffects.BURN), effect.source, effect.target, 20 * 10);
                return effect;
            }

            @Override
            public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
                return effect.element.isFire() && effect.isCriticalHit() && RandomUtils.roll(data.getAverageValue());
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }
        }
    );

    public static SpecialStat RANGED_CRIT_DMG_AGAINST_LIVING = new SpecialStat("ranged_crit_dmg_to_undead",
        format("You have " + VAL1 + "% increased ranged " + CriticalDamage.getInstance()
            .getIconNameFormat() + " against Undead enemies."),
        new BaseSpecialStatDamageEffect() {
            @Override
            public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
                effect.increaseByPercent(data.getAverageValue());
                return effect;
            }

            @Override
            public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
                return effect.weaponType.isProjectile && effect.target.isUndead() && effect.isCriticalHit();
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }
        }
    );

    public static SpecialStat HEAL_CLEANSE = new SpecialStat("heal_cleanse",
        format("Your " + HealPower.getInstance().textFormat + HealPower.getInstance().textIcon + " Heal Spells " + Formatting.GRAY + "have a " + VAL1 + "%" + " chance to cleanse you of a negative effect."),

        new BaseHealEffect() {
            @Override
            public HealEffect activate(HealEffect effect, StatData data, Stat stat) {
                for (StatusEffectInstance x : new ArrayList<>(effect.target.getStatusEffects())) {
                    StatusEffectAccessor acc = (StatusEffectAccessor) x.getEffectType();
                    if (acc.getType() == StatusEffectType.HARMFUL) {
                        effect.target.removeStatusEffect(x.getEffectType());
                    }
                }
                return effect;
            }

            @Override
            public boolean canActivate(HealEffect effect, StatData data, Stat stat) {
                return effect.attackType.isSpell() && RandomUtils.roll(data.getAverageValue());
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return 0;
            }
        }
    );

}
