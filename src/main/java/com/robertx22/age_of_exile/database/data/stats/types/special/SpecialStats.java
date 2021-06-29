package com.robertx22.age_of_exile.database.data.stats.types.special;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.BeneficialEffects;
import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mixins.StatusEffectAccessor;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.*;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Formatting;

import java.util.ArrayList;

import static com.robertx22.age_of_exile.database.data.stats.Stat.VAL1;
import static com.robertx22.age_of_exile.database.data.stats.Stat.format;

public class SpecialStats {

    public static void init() {

    }

    static int VOID_EYE_COOLDOWN_MINUTES = 5;

    public static SpecialStat VOID_EYE = new SpecialStat("void_eye",
        format("Dealing " + Elements.Dark.getIconNameFormat() + " Damage inside darkness has " + VAL1 + "% chance to give you Eye of the Void buff. " + VOID_EYE_COOLDOWN_MINUTES + " min Cooldown"),
        new BaseDamageEffect() {
            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return 0;
            }

            @Override
            public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {

                int duration = (int) (60 * 20 * 1);

                ExilePotionEvent potionEvent = EventBuilder.ofEffect(effect.source, effect.source, Load.Unit(effect.source)
                        .getLevel(), ExileDB.ExileEffects()
                        .get(BeneficialEffects.VOID_EYE.effectId), GiveOrTake.give, duration)
                    .build();
                potionEvent.Activate();

                effect.sourceData.getCooldowns()
                    .setOnCooldown("void_eye", VOID_EYE_COOLDOWN_MINUTES * 60 * 20);
                return effect;
            }

            @Override
            public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {

                if (!effect.getElement()
                    .isDark()) {
                    return false;
                }
                if (effect.source.world.getLightLevel(effect.source.getBlockPos()) > 7) {
                    return false;
                }
                if (!RandomUtils.roll(data.getAverageValue())) {
                    return false;
                }
                if (effect.sourceData.getCooldowns()
                    .isOnCooldown("void_eye")) {
                    return false;
                }

                return true;
            }
        }
    );

    public static SpecialStat MUMMY_CURSE = new SpecialStat("mummy_curse",
        format("Immobilizing effects have " + VAL1 + "% " + "chance to apply Curse of the Mummy"),

        new BasePotionEffect() {
            @Override
            public ExilePotionEvent activate(ExilePotionEvent effect, StatData data, Stat stat) {

                ExilePotionEvent potionEvent = EventBuilder.ofEffect(effect.source, effect.target, Load.Unit(effect.source)
                        .getLevel(), ExileDB.ExileEffects()
                        .get(NegativeEffects.MUMMY_CURSE.effectId), GiveOrTake.give, 20 * 10 * 20)
                    .build();
                potionEvent.Activate();

                return effect;
            }

            @Override
            public boolean canActivate(ExilePotionEvent effect, StatData data, Stat stat) {
                return ExileDB.ExileEffects()
                    .get(effect.data.getString(EventData.EXILE_EFFECT))
                    .hasTag(EffectTags.immobilizing) && RandomUtils.roll(data.getAverageValue());
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

    public static SpecialStat BONUS_REGEN_IN_WATER = new SpecialStat("bonus_regen_in_water",
        format("Your Regeneration effects are " + VAL1 + "% stronger inside water."),

        new BaseRegenEffect() {

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return 0;
            }

            @Override
            public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
                effect.increaseByPercent(data.getAverageValue());
                return effect;
            }

            @Override
            public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
                return effect.source.isTouchingWater();
            }
        }
    );
    public static SpecialStat PERC_SELF_HP_DMG_WHEN_IMMOBILZING = new SpecialStat("perc_self_hp_dmg_when_immo",
        format("Deal " + VAL1 + "% of your health as " + Elements.Nature.getIconNameDmg() + " when you cast immobilizing effects"),
        new BasePotionEffect() {
            @Override
            public ExilePotionEvent activate(ExilePotionEvent effect, StatData data, Stat stat) {

                float maxhp = effect.sourceData.getUnit()
                    .healthData()
                    .getAverageValue();

                float val = maxhp * data.getAverageValue() / 100F;
                DamageEvent dmg = EventBuilder.ofDamage(effect.source, effect.target, val)
                    .setupDamage(AttackType.spell, WeaponTypes.none, PlayStyle.magic)
                    .set(x -> x.setElement(Elements.Nature))
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

    public static SpecialStat RANGED_CRIT_DMG_AGAINST_LIVING = new SpecialStat("ranged_crit_dmg_to_undead",
        format("You have " + VAL1 + "% increased ranged " + Stats.CRIT_DAMAGE.get()
            .getIconNameFormat() + " against Undead enemies."),
        new BaseSpecialStatDamageEffect() {
            @Override
            public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
                effect.increaseByPercent(data.getAverageValue());
                return effect;
            }

            @Override
            public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
                return effect.data.getWeaponType().isProjectile && effect.target.isUndead() && effect.data.getBoolean(EventData.CRIT);
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }
        }
    );
    public static SpecialStat DARK_DMG_IGNORE_RES = new SpecialStat("dark_dmg_ignore_res",
        format("Your " + Elements.Dark.getIconNameFormat() + " Damage has " + VAL1 + "% chance to ignore all resistances."),
        new BaseDamageEffect() {
            @Override
            public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
                effect.data.setBoolean(EventData.IGNORE_RESIST, true);
                return effect;
            }

            @Override
            public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
                return effect.getElement()
                    .isDark() && RandomUtils.roll(data.getAverageValue());
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public int GetPriority() {
                return Priority.beforeThis(new ElementalResist(Elements.Nature).statEffect.GetPriority());
            }
        }
    );

    public static SpecialStat DAY_NIGHT_DMG = new SpecialStat("day_night_dmg",
        format("You deal " + VAL1 + "% increased  " + Elements.Light.getIconNameFormat() + " or "
            + Elements.Dark.getIconNameFormat() + " Damage depending on time of day"),
        new BaseSpecialStatDamageEffect() {
            @Override
            public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
                effect.increaseByPercent(data.getAverageValue());
                return effect;
            }

            @Override
            public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
                if (effect.getElement()
                    .isDark()) {
                    return effect.source.world.isNight();
                } else if (effect.getElement()
                    .isLight()) {
                    return effect.source.world.isDay();
                }
                return false;
            }

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }
        }
    );

    public static SpecialStat HEAL_CLEANSE = new SpecialStat("heal_cleanse",
        format("Your " + Stats.HEAL_STRENGTH.get()
            .getFormat() + Stats.HEAL_STRENGTH.get().icon + " Heal Spells " + Formatting.GRAY + "have a " + VAL1 + "%" + " chance to cleanse a negative effect."),

        new BaseHealEffect() {
            @Override
            public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
                for (StatusEffectInstance x : new ArrayList<>(effect.target.getStatusEffects())) {
                    StatusEffectAccessor acc = (StatusEffectAccessor) x.getEffectType();
                    if (acc.getType() == StatusEffectType.HARMFUL) {
                        effect.target.removeStatusEffect(x.getEffectType());
                    }
                }
                return effect;
            }

            @Override
            public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
                return effect.isSpell() && RandomUtils.roll(data.getAverageValue());
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
                    .isElemental() && RandomUtils.roll(data.getAverageValue());
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

                float val = data.getAverageValue();

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
