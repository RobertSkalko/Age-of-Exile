package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.skill_gem.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.value_calc.ScalingCalc;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class BeneficialEffects implements ExileRegistryInit {

    public static EffectCtx ELE_RESIST = new EffectCtx("ele_res", "Ele Resist", 0, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx ANTIDOTE = new EffectCtx("antidote", "Antidote", 1, Elements.All, EffectType.beneficial);
    public static EffectCtx HP_REGEN = new EffectCtx("hp_reg_bard", "Health Reg", 2, Elements.Physical, EffectType.beneficial);
    public static EffectCtx MANA_REGEN = new EffectCtx("mana_reg_bard", "Mana Reg", 3, Elements.Physical, EffectType.beneficial);
    public static EffectCtx INFUSED_BLADE = new EffectCtx("infused_blade", "Infused Blade", 4, Elements.Physical, EffectType.beneficial);
    public static EffectCtx REGENERATE = new EffectCtx("regenerate", "Rejuvenate", 5, Elements.Earth, EffectType.beneficial);
    public static EffectCtx THORN_ARMOR = new EffectCtx("thorn_armor", "Thorn Armor", 6, Elements.Earth, EffectType.beneficial);
    public static EffectCtx ANGER = new EffectCtx("anger", "Anger", 7, Elements.Physical, EffectType.beneficial);
    public static EffectCtx DIVINE_SHIELD = new EffectCtx("divine_shield", "Divine Shield", 8, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx POISON_WEAPONS = new EffectCtx("poison_weapons", "Poison Wep", 9, Elements.Earth, EffectType.beneficial);
    public static EffectCtx BLESSING = new EffectCtx("blessing", "Azuna's Blessing", 10, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx GATHER_STORM = new EffectCtx("gather_storm", "Gather Storm", 11, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx MARK = new EffectCtx("mark", "Mark of Zadal", 12, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx BLADE_DANCE = new EffectCtx("blade_dance", "Blade Dance", 13, Elements.Physical, EffectType.beneficial);
    public static EffectCtx FROST_ARMOR = new EffectCtx("frost_armor", "Frost Armor", 14, Elements.Water, EffectType.beneficial);
    public static EffectCtx BLOODLUST = new EffectCtx("bloodlust", "Bloodlust", 16, Elements.Physical, EffectType.beneficial);
    public static EffectCtx OVERLOAD = new EffectCtx("overload", "Overload", 17, Elements.Physical, EffectType.beneficial);
    public static EffectCtx VALOR = new EffectCtx("valor", "Valor", 18, Elements.Physical, EffectType.beneficial);
    public static EffectCtx PERSEVERANCE = new EffectCtx("perseverance", "Perseverance", 19, Elements.Physical, EffectType.beneficial);
    public static EffectCtx VIGOR = new EffectCtx("vigor", "Vigor", 20, Elements.Physical, EffectType.beneficial);
    public static EffectCtx TAUNT_STANCE = new EffectCtx("taunt_stance", "Taunt Stance", 21, Elements.Physical, EffectType.beneficial);
    public static EffectCtx UNDYING_WILL = new EffectCtx("undying_will", "Undying Will", 24, Elements.Physical, EffectType.beneficial);
    public static EffectCtx FRENZY = new EffectCtx("frenzy", "Frenzy", 25, Elements.Physical, EffectType.beneficial);
    public static EffectCtx ALACRITY = new EffectCtx("alacrity", "Alacrity", 27, Elements.Physical, EffectType.beneficial);
    public static EffectCtx STEAM_POWER = new EffectCtx("steam_power", "Steam Power", 28, Elements.Physical, EffectType.beneficial);
    public static EffectCtx CONCENTRATION = new EffectCtx("concentration", "Concentration", 29, Elements.Physical, EffectType.beneficial);
    public static EffectCtx CLEANSE = new EffectCtx("cleanse", "Cleanse", 30, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx MURDER_INSTINCT = new EffectCtx("murder_instinct", "Murder Instinct", 31, Elements.Physical, EffectType.beneficial);
    public static EffectCtx DEMON_TRANSFORMATION = new EffectCtx("demon", "Demon", 32, Elements.Physical, EffectType.beneficial);
    public static EffectCtx MAGE_CIRCLE = new EffectCtx("mage_circle", "Mage Circle", 33, Elements.Elemental, EffectType.beneficial);

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(MAGE_CIRCLE)
            .stat(25, Stats.SPELL_CRIT_DAMAGE.get(), ModType.FLAT)
            .stat(20, SpellDamage.getInstance(), ModType.FLAT)
            .maxStacks(1)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(DEMON_TRANSFORMATION)
            .vanillaStat(VanillaStatData.create(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1, ModType.FLAT, UUID.fromString("116a0931-d576-4721-b286-8d11de1ee42b")))
            .stat(25, Stats.COOLDOWN_REDUCTION_PER_SPELL_TAG.get(SpellTag.technique), ModType.FLAT)
            .stat(25, Stats.DAMAGE_PER_SPELL_TAG.get(SpellTag.technique), ModType.FLAT)
            .stat(25, Stats.CRIT_DAMAGE.get(), ModType.FLAT)

            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 2D, 0.5D)
                    .onTick(1D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.LARGE_SMOKE, 1D, 0.1D)
                    .onTick(1D))
                .buildForEffect())

            .maxStacks(1)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(MURDER_INSTINCT)
            .stat(5, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
            .stat(25, DodgeRating.getInstance(), ModType.PERCENT)
            .stat(5, Stats.ATTACK_SPEED.get(), ModType.FLAT)
            .stat(10, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
            .maxStacks(1)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(CLEANSE)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.POTION.removeNegative(1D))
                    .addTarget(TargetSelector.CASTER.create())
                    .onTick(1D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(TAUNT_STANCE)
            .stat(25, Stats.THREAT_GENERATED.get())
            .stat(50, Stats.MORE_THREAT_WHEN_TAKING_DAMAGE.get())

            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.AGGRO.create(ValueCalculation.withStat("taunt_stance", new ScalingCalc(HealthRegen.getInstance(), 0.05F), 2), AggroAction.Type.AGGRO))
                    .setTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies))
                    .onTick(60D))
                .buildForEffect())
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(CONCENTRATION)
            .stat(10, Stats.ACCURACY.get(), ModType.FLAT)
            .maxStacks(3)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(STEAM_POWER)
            .stat(1, ManaRegen.getInstance(), ModType.FLAT)
            .maxStacks(3)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(STEAM_POWER)
            .stat(1, ManaRegen.getInstance(), ModType.FLAT)
            .maxStacks(3)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(ALACRITY)
            .stat(2, Stats.ATTACK_SPEED.get(), ModType.FLAT)
            .maxStacks(10)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(FRENZY)
            .stat(5, Stats.TOTAL_DAMAGE.get())
            .stat(5, Stats.DAMAGE_RECEIVED.get())
            .stat(1, Stats.LIFESTEAL.get())
            .maxStacks(3)
            .build();

        ExileEffectBuilder.of(UNDYING_WILL)
            .stat(-75, Stats.DAMAGE_RECEIVED.get())
            .stat(2, HealthRegen.getInstance())
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(TAUNT_STANCE)
            .stat(25, Stats.THREAT_GENERATED.get())
            .stat(50, Stats.MORE_THREAT_WHEN_TAKING_DAMAGE.get())

            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.AGGRO.create(ValueCalculation.withStat("taunt_stance", new ScalingCalc(HealthRegen.getInstance(), 0.05F), 2), AggroAction.Type.AGGRO))
                    .setTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies))
                    .onTick(60D))
                .buildForEffect())
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(VIGOR)
            .stat(0.5F, HealthRegen.getInstance())
            .stat(0.5F, ManaRegen.getInstance())
            .maxStacks(3)
            .addTags(EffectTags.song)
            .build();

        ExileEffectBuilder.of(PERSEVERANCE)
            .stat(-5, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.melee))
            .stat(-5, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.ranged))
            .stat(-5, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.magic))
            .maxStacks(3)
            .addTags(EffectTags.song, EffectTags.defensive)
            .build();

        ExileEffectBuilder.of(VALOR)
            .stat(10, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
            .stat(5, Stats.ATTACK_SPEED.get(), ModType.FLAT)
            .stat(5, Stats.CAST_SPEED.get(), ModType.FLAT)
            .maxStacks(3)
            .addTags(EffectTags.song, EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(BLADE_DANCE)
            .stat(2, Stats.ATTACK_SPEED.get(), ModType.FLAT)
            .maxStacks(5)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(GATHER_STORM)
            .stat(5, Stats.AREA_DAMAGE.get(), ModType.FLAT)
            .maxStacks(5)
            .addTags(EffectTags.offensive)

            .build();

        ExileEffectBuilder.of(BLESSING)
            .stat(-3, Stats.DAMAGE_RECEIVED.get(), ModType.FLAT)
            .stat(5, Stats.HEAL_STRENGTH.get(), ModType.FLAT)
            .maxStacks(5)
            .build();

        ExileEffectBuilder.of(MARK)
            .stat(5, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
            .maxStacks(5)
            .build();

        ExileEffectBuilder.of(INFUSED_BLADE)
            .stat(5, SpellDamage.getInstance(), ModType.FLAT)
            .addTags(EffectTags.offensive)
            .maxStacks(5)
            .build();

        ExileEffectBuilder.of(HP_REGEN)
            .stat(20, HealthRegen.getInstance(), ModType.PERCENT)
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(MANA_REGEN)
            .stat(20, ManaRegen.getInstance(), ModType.PERCENT)
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(OVERLOAD)
            .stat(-10, Stats.COOLDOWN_TICKS.get(), ModType.FLAT)
            .stat(25, Stats.MANA_COST.get(), ModType.FLAT)
            .stat(-25, DatapackStats.MOVE_SPEED, ModType.FLAT)
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(BLOODLUST)
            .stat(2, Stats.LIFESTEAL.get(), ModType.FLAT)
            .stat(2, DatapackStats.MOVE_SPEED, ModType.FLAT)
            .maxStacks(10)
            .build();

        ExileEffectBuilder.of(ELE_RESIST)
            .stat(10, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .addTags(EffectTags.defensive)
            .build();

        ExileEffectBuilder.of(ANTIDOTE)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.removeSelfEffect(StatusEffects.POISON)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(REGENERATE)
            .maxStacks(3)
            .addTags(EffectTags.heal_over_time)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(ValueCalculation.base("regenerate_tick", 1)))
                    .setTarget(TargetSelector.TARGET.create())
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.HEART, 5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(THORN_ARMOR)
            .stat(25, new ElementalResist(Elements.Earth), ModType.FLAT)
            .stat(3, Armor.getInstance(), ModType.FLAT)
            .stat(3, DodgeRating.getInstance(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.EXILE_EFFECT.create(NegativeEffects.POISON.effectId, ExileEffectAction.GiveOrTake.GIVE_STACKS, 80D))
                    .setTarget(TargetSelector.AOE.create(2D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies))
                    .onTick(40D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 5D, 1D)
                    .onTick(40D))

                .buildForEffect())
            .addTags(EffectTags.defensive)

            .build();

        ExileEffectBuilder.of(FROST_ARMOR)
            .stat(20, new ElementalResist(Elements.Water), ModType.FLAT)
            .stat(30, Armor.getInstance(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .buildForEffect())
            .addTags(EffectTags.defensive)

            .build();

        ExileEffectBuilder.of(ANGER)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, 0.15F, ModType.GLOBAL_INCREASE, UUID.fromString("7107DE5E-5CE8-4030-940E-514C1F160890")))
            .stat(15, Stats.CRIT_CHANCE.get(), ModType.FLAT)
            .stat(20, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
            .addTags(EffectTags.offensive)

            .build();

        ExileEffectBuilder.of(DIVINE_SHIELD)
            .stat(25, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .stat(7, Armor.getInstance(), ModType.FLAT)
            .addTags(EffectTags.defensive)
            .build();

        ExileEffectBuilder.of(POISON_WEAPONS)
            .stat(3, new AttackDamage(Elements.Earth), ModType.FLAT)
            .stat(20, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON), ModType.FLAT)
            .addTags(EffectTags.offensive)
            .build();

    }
}
