package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStatAdder;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.crit.GlobalCriticalHit;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class BeneficialEffects implements ISlashRegistryInit {

    public static EffectCtx ELE_RESIST = new EffectCtx("ele_res", "Ele Resist", 0, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx REGENERATE = new EffectCtx("regenerate", "Regenerate", 5, Elements.Nature, EffectType.beneficial);
    public static EffectCtx CLEANSE = new EffectCtx("cleanse", "Cleanse", 1, Elements.All, EffectType.beneficial);
    public static EffectCtx THORN_ARMOR = new EffectCtx("thorn_armor", "Thorn Armor", 6, Elements.Nature, EffectType.beneficial);
    public static EffectCtx ANGER = new EffectCtx("anger", "Anger", 7, Elements.Physical, EffectType.beneficial);
    public static EffectCtx DIVINE_SHIELD = new EffectCtx("divine_shield", "Divine Shield", 8, Elements.Elemental, EffectType.beneficial);
    public static EffectCtx POISON_WEAPONS = new EffectCtx("poison_weapons", "Poison Wep", 9, Elements.Nature, EffectType.beneficial);
    public static EffectCtx FROST_ARMOR = new EffectCtx("frost_armor", "Frost Armor", 14, Elements.Water, EffectType.beneficial);
    public static EffectCtx VOID_EYE = new EffectCtx("void_eye", "Void Eye", 15, Elements.Dark, EffectType.beneficial);
    public static EffectCtx BLOODLUST = new EffectCtx("bloodlust", "Bloodlust", 16, Elements.Physical, EffectType.beneficial);

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(BLOODLUST)
            .stat(2, Stats.LIFESTEAL.get(), ModType.FLAT)
            .stat(2, DatapackStatAdder.MOVE_SPEED, ModType.FLAT)
            .maxStacks(10)
            .build();

        ExileEffectBuilder.of(VOID_EYE)
            .stat(10, new ElementalPenetration(Elements.Elemental), ModType.FLAT)
            .stat(25, GlobalCriticalHit.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(ELE_RESIST)
            .stat(10, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(CLEANSE)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.removeSelfEffect(StatusEffects.POISON)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(REGENERATE)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(ValueCalculation.base("regenerate_tick", 3)))
                    .setTarget(TargetSelector.TARGET.create())
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.HEART, 5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(THORN_ARMOR)
            .stat(25, new ElementalResist(Elements.Nature), ModType.FLAT)
            .stat(3, Armor.getInstance(), ModType.FLAT)
            .stat(3, DodgeRating.getInstance(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.EXILE_EFFECT.create(NegativeEffects.POISON.effectId, ExileEffectAction.GiveOrTake.GIVE_STACKS, 80D))
                    .setTarget(TargetSelector.AOE.create(2D, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES))
                    .onTick(40D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 5D, 1D)
                    .onTick(40D))

                .buildForEffect())
            .build();

        ExileEffectBuilder.of(FROST_ARMOR)
            .stat(20, new ElementalResist(Elements.Water), ModType.FLAT)
            .stat(20, Armor.getInstance(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(ANGER)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, 0.15F, ModType.GLOBAL_INCREASE, UUID.fromString("7107DE5E-5CE8-4030-940E-514C1F160890")))
            .stat(15, Stats.CRIT_CHANCE.get(), ModType.FLAT)
            .stat(20, Stats.CRIT_DAMAGE.get(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(DIVINE_SHIELD)
            .stat(25, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .stat(7, Armor.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(POISON_WEAPONS)
            .stat(3, new AttackDamage(Elements.Nature), ModType.FLAT)
            .stat(20, Stats.CHANCE_OF_APPLYING_EFFECT.get(NegativeEffects.POISON), ModType.FLAT)
            .build();

    }
}
