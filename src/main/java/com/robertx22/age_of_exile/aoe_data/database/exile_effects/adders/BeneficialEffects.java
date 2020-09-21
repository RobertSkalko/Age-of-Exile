package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificElementalWeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class BeneficialEffects implements ISlashRegistryInit {

    public static String ELE_RESIST = "beneficial/" + 0;
    public static String CLEANSE = "beneficial/" + 1;
    public static String BRAVERY = "beneficial/" + 2;
    public static String WIZARDRY = "beneficial/" + 3;
    public static String TRICKERY = "beneficial/" + 4;
    public static String REGENERATE = "beneficial/" + 5;
    public static String THORN_ARMOR = "beneficial/" + 6;
    public static String ANGER = "beneficial/" + 7;
    public static String DIVINE_SHIELD = "beneficial/" + 8;
    public static String POISON_WEAPONS = "beneficial/" + 9;
    public static String IMBUE = "beneficial/" + 10;
    public static String HUNTER_INSTINCTS = "beneficial/" + 11;

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(ELE_RESIST, "Ele Resist", EffectType.BENEFICIAL)
            .stat(15, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(CLEANSE, "Cleanse", EffectType.BENEFICIAL)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.removeSelfEffect(StatusEffects.POISON)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(BRAVERY, "Bravery", EffectType.BENEFICIAL)
            .stat(2, HealthRegen.getInstance(), ModType.FLAT)
            .stat(50, Armor.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(WIZARDRY, "Wizardy", EffectType.BENEFICIAL)
            .stat(20, new ElementalSpellDamage(Elements.Elemental), ModType.FLAT)
            .stat(3, ManaRegen.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(TRICKERY, "Trickery", EffectType.BENEFICIAL)
            .stat(40, DodgeRating.getInstance(), ModType.FLAT)
            .stat(10, CriticalHit.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(REGENERATE, "Regenerate", EffectType.BENEFICIAL)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(ValueCalculationData.base(3)))
                    .setTarget(TargetSelector.TARGET.create())
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.HEART, 5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(THORN_ARMOR, "Thorn Armor", EffectType.BENEFICIAL)
            .stat(25, new ElementalResist(Elements.Nature), ModType.FLAT)
            .stat(25, Armor.getInstance(), ModType.FLAT)
            .stat(25, DodgeRating.getInstance(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.EXILE_EFFECT.create(NegativeEffects.THORNS, ExileEffectAction.GiveOrTake.GIVE_STACKS, 80D))
                    .setTarget(TargetSelector.AOE.create(2D, EntityFinder.SelectionType.RADIUS, EntityFinder.EntityPredicate.ENEMIES))
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(ANGER, "Anger", EffectType.BENEFICIAL)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, 0.15F, ModType.GLOBAL_INCREASE, UUID.fromString("7107DE5E-5CE8-4030-940E-514C1F160890")))
            .stat(15, CriticalHit.getInstance(), ModType.FLAT)
            .stat(20, CriticalDamage.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(DIVINE_SHIELD, "Divine Shield", EffectType.BENEFICIAL)
            .stat(25, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .stat(75, Armor.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(POISON_WEAPONS, "Poison Attack", EffectType.BENEFICIAL)
            .stat(3, new WeaponDamage(Elements.Nature), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(IMBUE, "Imbue", EffectType.BENEFICIAL)
            .stat(10, new SpecificElementalWeaponDamage(WeaponTypes.Bow), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(HUNTER_INSTINCTS, "Hunter Instinct", EffectType.BENEFICIAL)
            .stat(10, new SpecificElementalWeaponDamage(WeaponTypes.Bow), ModType.FLAT)
            .stat(5, CriticalHit.getInstance(), ModType.FLAT)
            .build();
    }
}
