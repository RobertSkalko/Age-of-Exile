package com.robertx22.age_of_exile.aoe_data.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart.PartBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.Spell.Builder;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.HealPower;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.saveclasses.spells.calc.ValueCalculationData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.GENERIC_MOVEMENT_SPEED;

public class NegativeEffects implements ISlashRegistryInit {

    public static String ELE_WEAKNESS = "negative/" + 0;
    public static String PETRIFY = "negative/" + 1;
    public static String CHILL = "negative/" + 2;
    public static String THORNS = "negative/" + 3;
    public static String WOUNDS = "negative/" + 4;
    public static String BURN = "negative/" + 5;
    public static String JUDGEMENT = "negative/" + 6;

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(ELE_WEAKNESS, "+Ele Res", EffectType.HARMFUL)
            .stat(-15, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(CHILL, "Chill", EffectType.HARMFUL)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -0.05F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd8")))
            .stat(-15, new ElementalResist(Elements.Water), ModType.FLAT)
            .spell(Builder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 5D, 1D)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(THORNS, "Thorns", EffectType.HARMFUL)
            .spell(Builder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculationData.base(2), Elements.Nature)
                    .put(MapField.DMG_EFFECT_TYPE, EffectData.EffectTypes.DOT_DMG.name()))
                    .setTarget(TargetSelector.TARGET.create())
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 5D, 1D)
                    .onTick(20D))
                .onTick(PartBuilder.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 0.5D, 0.5D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(WOUNDS, "Wounds", EffectType.HARMFUL)
            .stat(-25, HealPower.getInstance(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(BURN, "Burn", EffectType.HARMFUL)
            .spell(Builder.forEffect()
                .onTick(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculationData.base(2), Elements.Fire)
                    .put(MapField.DMG_EFFECT_TYPE, EffectData.EffectTypes.DOT_DMG.name()))
                    .setTarget(TargetSelector.TARGET.create())
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.FLAME, 5D, 1D)
                    .onTick(20D))
                .onTick(PartBuilder.playSound(SoundEvents.BLOCK_CAMPFIRE_CRACKLE, 0.5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(JUDGEMENT, "Judgement", EffectType.HARMFUL)
            .stat(-10, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .spell(Builder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 10D, 1D)
                    .onTick(20D))
                .onExpire(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculationData.base(10), Elements.Thunder))
                    .setTarget(TargetSelector.TARGET.create()))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_LIGHTNING_STRIKE.create())
                    .setTarget(TargetSelector.TARGET.create()))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(PETRIFY, "Petrify", EffectType.HARMFUL)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -1F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd5")))
            .spell(Builder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D)
                    .onTick(20D))
                .onExpire(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculationData.base(5), Elements.Nature))
                    .setTarget(TargetSelector.TARGET.create()))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 15D, 1D))
                .onExpire(PartBuilder.justAction(SpellAction.PLAY_SOUND.create(SoundEvents.ENTITY_SHEEP_SHEAR, 1D, 1D)))
                .buildForEffect())
            .build();

    }
}
