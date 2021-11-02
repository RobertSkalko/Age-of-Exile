package com.robertx22.age_of_exile.database.data.stats.types.special;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BasePotionEffect;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpecialStatDamageEffect;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.ExilePotionEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

import static com.robertx22.age_of_exile.database.data.stats.Stat.VAL1;
import static com.robertx22.age_of_exile.database.data.stats.Stat.format;

public class SpecialStats {

    public static void init() {

    }

    public static SpecialStat PERC_SELF_HP_DMG_WHEN_IMMOBILZING = new SpecialStat("perc_self_hp_dmg_when_immo",
        format("Deal " + VAL1 + "% of your health as " + Elements.Earth.getIconNameDmg() + " when you cast immobilizing effects"),
        new BasePotionEffect() {
            @Override
            public ExilePotionEvent activate(ExilePotionEvent effect, StatData data, Stat stat) {

                float maxhp = effect.sourceData.getUnit()
                    .healthData()
                    .getValue();

                float val = maxhp * data.getValue() / 100F;
                DamageEvent dmg = EventBuilder.ofDamage(effect.source, effect.target, val)
                    .setupDamage(AttackType.spell, WeaponTypes.none, PlayStyle.magic)
                    .set(x -> x.setElement(Elements.Earth))
                    .build();

                dmg.Activate();

                return effect;
            }

            @Override
            public boolean canActivate(ExilePotionEvent effect, StatData data, Stat stat) {
                ExileEffect ex = effect.data.getExileEffect();
                return ex != null && ex.tags.contains(EffectTags.immobilizing.name());
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

    public static SpecialStat HEAL_CLEANSE = new SpecialStat("heal_cleanse",
        format("Your " + Stats.HEAL_STRENGTH.get()
            .getFormat() + Stats.HEAL_STRENGTH.get().icon + " Heal Spells " + TextFormatting.GRAY + "have a " + VAL1 + "%" + " chance to cleanse a negative effect."),

        new BaseHealEffect() {
            @Override
            public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
                for (EffectInstance x : new ArrayList<>(effect.target.getActiveEffects())) {
                    if (x.getEffect()
                        .getCategory() == EffectType.HARMFUL) {
                        effect.target.removeEffect(x.getEffect());
                    }
                }
                return effect;
            }

            @Override
            public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
                return effect.isSpell() && RandomUtils.roll(data.getValue());
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

    public static SpecialStat ABSORB_ELE_DMG_INTO_HP = new SpecialStat("absorb_ele_dmg_to_hp",
        format("You have " + VAL1 + "% chance to absorb " + Elements.Elemental
            .getIconNameFormat() + " damage and heal you instead."),
        new BaseSpecialStatDamageEffect() {
            @Override
            public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

                float val = effect.data.getNumber();

                RestoreResourceEvent restore = EventBuilder.ofRestore(effect.source, effect.target, ResourceType.health, RestoreType.heal, val)
                    .build();
                restore.Activate();

                effect.cancelDamage();
                return effect;
            }

            @Override
            public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
                return effect.getElement()
                    .isElemental() && RandomUtils.roll(data.getValue());
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Target;
            }
        }
    );

    public static SpecialStat BETTER_FOOD_BUFFS = new SpecialStat("more_food_stats",
        format("You gain " + VAL1 + "% more stats through Food buffs."),
        new IStatCtxModifier() {
            @Override
            public void modify(ExactStatData thisStat, StatContext target) {
                float multi = 1F + thisStat.getAverageValue() / 100F;
                target.stats.forEach(x -> {
                    x.multiplyBy(multi);
                });
            }

            @Override
            public StatContext.StatCtxType getCtxTypeNeeded() {
                return StatContext.StatCtxType.FOOD_BUFF;
            }
        }
    );

    public static SpecialStat CRIT_WATER_DMG_MANA = new SpecialStat("crit_water_dmg_mana",
        format("Your critical " + Elements.Water.getIconNameFormat() + " Damage restores " + VAL1 + " mana to you."),
        new BaseSpecialStatDamageEffect() {
            @Override
            public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

                float val = data.getValue();

                RestoreResourceEvent restore = EventBuilder.ofRestore(effect.source, effect.target, ResourceType.mana, RestoreType.heal, val)
                    .build();
                restore.Activate();

                return effect;
            }

            @Override
            public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
                return effect.getElement()
                    .isWater() && effect.data.getBoolean(EventData.CRIT);
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }
        }
    );

}
