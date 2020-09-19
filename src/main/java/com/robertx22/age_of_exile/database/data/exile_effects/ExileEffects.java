package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart.PartBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell.Builder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalSpellDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.CriticalHit;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class ExileEffects implements ISlashRegistryInit {

    public static Integer ELE_WEAKNESS = 0;
    public static Integer ELE_RESIST = 1;
    public static Integer CLEANSE = 2;
    public static Integer CHILL = 3;
    public static Integer BRAVERY = 4;
    public static Integer WIZARDRY = 5;
    public static Integer TRICKERY = 6;
    public static Integer REGENERATE = 7;
    public static Integer THORNS = 8;

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(ELE_WEAKNESS, "+Ele Res")
                .stat(-15, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(ELE_RESIST, "-Ele Res")
                .stat(15, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(CLEANSE, "Cleanse")
                .spell(Builder.forEffect()
                        .onTick(PartBuilder.removeSelfEffect(StatusEffects.POISON).onTick(10D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(CHILL, "Chill")
                .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -0.05F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd8")))
                .stat(-15, new ElementalResist(Elements.Water), ModType.FLAT)
                .spell(Builder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 5D, 1D).onTick(10D))
                        .buildForEffect())
                .build();


        ExileEffectBuilder.of(BRAVERY, "Bravery")
                .stat(2, HealthRegen.getInstance(), ModType.FLAT)
                .stat(50, Armor.getInstance(), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(WIZARDRY, "Wizardy")
                .stat(20, new ElementalSpellDamage(Elements.Elemental), ModType.FLAT)
                .stat(3, ManaRegen.getInstance(), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(TRICKERY, "Trickery")
                .stat(40, DodgeRating.getInstance(), ModType.FLAT)
                .stat(10, CriticalHit.getInstance(), ModType.FLAT)
                .build();


        ExileEffectBuilder.of(REGENERATE, "Regenerate")
                .spell(Builder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.RESTORE_HEALTH.create(ValueCalculationData.base(3))).setTarget(TargetSelector.TARGET.create()).onTick(20D))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.HEART, 5D, 1D).onTick(20D))
                        .buildForEffect())
                .build();


        ExileEffectBuilder.of(THORNS, "Thorns")
                .spell(Builder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculationData.base(2), Elements.Nature).put(MapField.DMG_EFFECT_TYPE, EffectData.EffectTypes.DOT_DMG.name())).setTarget(TargetSelector.TARGET.create()).onTick(20D))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 5D, 1D).onTick(20D))
                        .onTick(PartBuilder.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 0.5D, 0.5D).onTick(20D))
                        .buildForEffect())
                .build();

    }
}
