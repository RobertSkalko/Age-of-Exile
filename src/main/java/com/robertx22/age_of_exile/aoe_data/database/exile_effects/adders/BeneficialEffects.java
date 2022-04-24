package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;

public class BeneficialEffects implements ExileRegistryInit {

    public static EffectCtx ANTIDOTE = new EffectCtx("antidote", "Antidote", 1, Elements.All, EffectType.beneficial);
    public static EffectCtx NOURISHMENT = new EffectCtx("nourishment", "Nourishment", 2, Elements.Water, EffectType.beneficial);
    public static EffectCtx VAMPIRIC_BLOOD = new EffectCtx("vamp_blood", "Vamp Blood", 3, Elements.Fire, EffectType.beneficial);
    public static EffectCtx DRACONIC_BLOOD = new EffectCtx("draconic_blood", "Dragon Blood", 4, Elements.Fire, EffectType.beneficial);
    public static EffectCtx REGENERATE = new EffectCtx("regenerate", "Nature Balm", 5, Elements.Earth, EffectType.beneficial);
    public static EffectCtx THORN_ARMOR = new EffectCtx("thorn_armor", "Thorn Armor", 6, Elements.Earth, EffectType.beneficial);
    public static EffectCtx FIRE_WEAPON = new EffectCtx("fire_weapon", "Flame Weapon", 7, Elements.Fire, EffectType.beneficial);
    public static EffectCtx DIVINE_SHIELD = new EffectCtx("divine_shield", "Divine Shield", 8, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx POISON_WEAPONS = new EffectCtx("poison_weapons", "Poison Wep", 9, Elements.Earth, EffectType.beneficial);
    public static EffectCtx ICY_WEAPON = new EffectCtx("ice_weapon", "Icy Weapon", 10, Elements.Water, EffectType.beneficial);
    public static EffectCtx FROST_ARMOR = new EffectCtx("frost_armor", "Frost Armor", 14, Elements.Water, EffectType.beneficial);
    public static EffectCtx VALOR = new EffectCtx("valor", "Valor", 18, Elements.Physical, EffectType.beneficial);
    public static EffectCtx PERSEVERANCE = new EffectCtx("perseverance", "Perseverance", 19, Elements.Physical, EffectType.beneficial);
    public static EffectCtx VIGOR = new EffectCtx("vigor", "Vigor", 20, Elements.Physical, EffectType.beneficial);
    public static EffectCtx TAUNT_STANCE = new EffectCtx("taunt_stance", "Taunt Stance", 21, Elements.Physical, EffectType.beneficial);
    public static EffectCtx UNDYING_WILL = new EffectCtx("undying_will", "Undying Will", 24, Elements.Physical, EffectType.beneficial);
    public static EffectCtx CLEANSE = new EffectCtx("cleanse", "Cleanse", 30, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx MURDER_INSTINCT = new EffectCtx("murder_instinct", "Murder Instinct", 31, Elements.Physical, EffectType.beneficial);
    public static EffectCtx FROST_STEPS = new EffectCtx("frost_steps", "Frost Steps", 33, Elements.Water, EffectType.beneficial);

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(ICY_WEAPON)
            .stat(1, 3, new AttackDamage(Elements.Water), ModType.FLAT)
            .maxStacks(1)
            .addTags(EffectTags.positive)
            .build();

        ExileEffectBuilder.of(FIRE_WEAPON)
            .stat(1, 3, new AttackDamage(Elements.Fire), ModType.FLAT)
            .maxStacks(1)
            .addTags(EffectTags.positive)
            .build();

        ExileEffectBuilder.of(DRACONIC_BLOOD)
            .stat(2, 4, Stats.SPELL_LIFESTEAL.get(), ModType.FLAT)
            .maxStacks(1)
            .addTags(EffectTags.positive)
            .build();

        ExileEffectBuilder.of(VAMPIRIC_BLOOD)
            .stat(2, 5, Stats.LIFESTEAL.get(), ModType.FLAT)
            .maxStacks(1)
            .addTags(EffectTags.positive)
            .build();

        ExileEffectBuilder.of(NOURISHMENT)
            .stat(0.5F, 1, HealthRegen.getInstance(), ModType.FLAT)
            .maxStacks(1)
            .addTags(EffectTags.positive)
            .build();

        ExileEffectBuilder.of(FROST_STEPS)

            .spell(SpellBuilder.forEffect()

                .onTick(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 20D * 8)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, true)
                        .put(MapField.IS_BLOCK_FALLING, false))
                    .onTick(10D))

                .buildForEffect())

            .maxStacks(1)
            .addTags(EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(MURDER_INSTINCT)
            .stat(3, 5, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
            .stat(5, 25, DodgeRating.getInstance(), ModType.PERCENT)
            .stat(3, 5, Stats.ATTACK_SPEED.get(), ModType.FLAT)
            .stat(3, 10, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
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
            .stat(10, 25, Stats.THREAT_GENERATED.get())
            .stat(25, 50, Stats.MORE_THREAT_WHEN_TAKING_DAMAGE.get())

            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                    .setTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies))
                    .onTick(60D))
                .buildForEffect())
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(UNDYING_WILL)
            .stat(-25, -75, Stats.DAMAGE_RECEIVED.get())
            .stat(1, 2, HealthRegen.getInstance())
            .maxStacks(1)
            .build();

        ExileEffectBuilder.of(VIGOR)
            .stat(0.25F, 0.5F, HealthRegen.getInstance())
            .stat(0.25F, 0.5F, ManaRegen.getInstance())
            .maxStacks(3)
            .addTags(EffectTags.song)
            .build();

        ExileEffectBuilder.of(PERSEVERANCE)
            .stat(-3, -6, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.melee))
            .stat(-3, -6, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.ranged))
            .stat(-3, -6, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.magic))
            .maxStacks(3)
            .addTags(EffectTags.song, EffectTags.defensive)
            .build();

        ExileEffectBuilder.of(VALOR)
            .stat(3, 6, Stats.TOTAL_DAMAGE.get(), ModType.FLAT)
            .stat(2, 5, Stats.ATTACK_SPEED.get(), ModType.FLAT)
            .maxStacks(3)
            .addTags(EffectTags.song, EffectTags.offensive)
            .build();

        ExileEffectBuilder.of(ANTIDOTE)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.removeSelfEffect(Effects.POISON)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(REGENERATE)
            .maxStacks(3)
            .addTags(EffectTags.heal_over_time)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(SpellCalcs.NATURE_BALM))
                    .setTarget(TargetSelector.TARGET.create())
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.HEART, 5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(THORN_ARMOR)
            .stat(10, 25, new ElementalResist(Elements.Earth), ModType.PERCENT)
            .stat(5, 10, Armor.getInstance(), ModType.FLAT)
            .stat(5, 10, DodgeRating.getInstance(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.EXILE_EFFECT.create(NegativeEffects.POISON.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 80D))
                    .setTarget(TargetSelector.AOE.create(2D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies))
                    .onTick(40D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 5D, 1D)
                    .onTick(40D))

                .buildForEffect())
            .addTags(EffectTags.defensive)

            .build();

        ExileEffectBuilder.of(FROST_ARMOR)
            .stat(10, 20, new ElementalResist(Elements.Water), ModType.PERCENT)
            .stat(15, 25, Armor.getInstance(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .buildForEffect())
            .addTags(EffectTags.defensive)

            .build();

        ExileEffectBuilder.of(DIVINE_SHIELD)
            .stat(10, 15, new ElementalResist(Elements.Elemental), ModType.PERCENT)
            .stat(5, 15, Armor.getInstance(), ModType.FLAT)
            .addTags(EffectTags.defensive)
            .build();

        ExileEffectBuilder.of(POISON_WEAPONS)
            .stat(1, 2, new AttackDamage(Elements.Earth), ModType.FLAT)
            .stat(5, 15, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON), ModType.FLAT)
            .addTags(EffectTags.offensive)
            .build();

    }
}
