package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.*;

public class NegativeEffects implements ISlashRegistryInit {

    public static EffectCtx ELE_WEAKNESS = new EffectCtx("ele_weakness", "Ele Weakness", 0, Elements.Elemental, EffectType.negative);
    public static EffectCtx PETRIFY = new EffectCtx("petrify", "Petrify", 1, Elements.Nature, EffectType.negative);
    public static EffectCtx FROSTBURN = new EffectCtx("frostburn", "Frost Burn", 2, Elements.Water, EffectType.negative);
    public static EffectCtx POISON = new EffectCtx("poison", "Poison", 3, Elements.Nature, EffectType.negative);
    public static EffectCtx WOUNDS = new EffectCtx("wounds", "Wounds", 4, Elements.Physical, EffectType.negative);
    public static EffectCtx BURN = new EffectCtx("burn", "Burn", 5, Elements.Fire, EffectType.negative);
    public static EffectCtx JUDGEMENT = new EffectCtx("judgement", "Judgement", 6, Elements.Light, EffectType.negative);
    public static EffectCtx TORMENT = new EffectCtx("torment", "Torment", 7, Elements.Dark, EffectType.negative);
    public static EffectCtx BLEED = new EffectCtx("bleed", "Bleed", 8, Elements.Physical, EffectType.negative);
    public static EffectCtx MUMMY_CURSE = new EffectCtx("mummy_curse", "Mummy Curse", 9, Elements.Light, EffectType.negative);
    public static EffectCtx BLIND = new EffectCtx("blind", "Blind", 10, Elements.Light, EffectType.negative);
    public static EffectCtx STUN = new EffectCtx("stun", "Stun", 11, Elements.Physical, EffectType.negative);

    @Override
    public void registerAll() {

        ExileEffectBuilder.of(STUN)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -100, ModType.GLOBAL_INCREASE, UUID.fromString("3fb10485-f309-468f-afc6-a23b0d6cf4c1")))
            .vanillaStat(VanillaStatData.create(GENERIC_ATTACK_SPEED, -100, ModType.GLOBAL_INCREASE, UUID.fromString("00fb60a7-904b-462f-a7cb-a557f02e362e")))
            .vanillaStat(VanillaStatData.create(GENERIC_ATTACK_DAMAGE, -100, ModType.GLOBAL_INCREASE, UUID.fromString("10fb60a7-904b-462f-a7cb-a557f02e362e")))
            .build();

        ExileEffectBuilder.of(MUMMY_CURSE)
            .maxStacks(1)
            .stat(20, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.magic), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.SOUL, 3D, 1D)
                    .onTick(10D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.WHITE_ASH, 10D, 1D)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(TORMENT)
            .maxStacks(1)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, 0.2F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9f32fa-c8c1-455c-92aa-4a94c2a70cd8")))
            .stat(-10, new ElementalResist(Elements.Dark), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.dotDamageOnTick(TORMENT.effectId, ValueCalculation.base("torment", 2F), Elements.Dark)
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.SOUL, 10D, 1D)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(FROSTBURN)
            .maxStacks(5)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -0.05F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd8")))
            .stat(-4, new ElementalResist(Elements.Water), ModType.FLAT)

            .spell(SpellBuilder.forEffect()

                .onTick(PartBuilder.dotDamageOnTick(FROSTBURN.effectId, ValueCalculation.base("frostburn", 1.5F), Elements.Water)
                    .onTick(20D))

                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 10D, 1D)
                    .onTick(10D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(POISON)
            .maxStacks(5)
            .spell(SpellBuilder.forEffect()

                .onTick(PartBuilder.dotDamageOnTick(POISON.effectId, ValueCalculation.base("poison", 2), Elements.Nature)
                    .onTick(20D))
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 15D, 1D)
                    .onTick(20D))
                .onTick(PartBuilder.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 0.5D, 0.5D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(BURN)
            .maxStacks(5)
            .spell(SpellBuilder.forEffect()

                .onTick(PartBuilder.dotDamageOnTick(BURN.effectId, ValueCalculation.base("burn", 2), Elements.Fire)
                    .onTick(20D))

                .onTick(PartBuilder.aoeParticles(ParticleTypes.FLAME, 10D, 1D)
                    .onTick(20D))
                .onTick(PartBuilder.playSound(SoundEvents.BLOCK_CAMPFIRE_CRACKLE, 0.5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(BLEED)
            .maxStacks(5)
            .spell(SpellBuilder.forEffect()

                .onTick(PartBuilder.dotDamageOnTick(BLEED.effectId, ValueCalculation.base("bleed", 2.25F), Elements.Physical)
                    .onTick(20D))

                .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 10D, 1D)
                    .onTick(20D))
                .onTick(PartBuilder.playSound(SoundEvents.ENTITY_GENERIC_HURT, 0.5D, 1D)
                    .onTick(20D))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(ELE_WEAKNESS)
            .stat(-15, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(BLIND)
            .stat(-10, new AttackDamage(Elements.Physical), ModType.FLAT)
            .stat(-25, Stats.CRIT_CHANCE.get(), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 3D, 1D)
                    .onTick(20D))
                .buildForEffect()
            )
            .build();

        ExileEffectBuilder.of(WOUNDS)
            .stat(-25, Stats.HEAL_STRENGTH.get(), ModType.FLAT)
            .build();

        ExileEffectBuilder.of(JUDGEMENT)
            .stat(-10, new ElementalResist(Elements.Elemental), ModType.FLAT)
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 10D, 1D)
                    .onTick(20D))
                .onExpire(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculation.base("judgement", 10), Elements.Light))
                    .setTarget(TargetSelector.TARGET.create()))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_LIGHTNING_STRIKE.create())
                    .setTarget(TargetSelector.TARGET.create()))
                .buildForEffect())
            .build();

        ExileEffectBuilder.of(PETRIFY)
            .addTags(EffectTags.immobilizing)
            .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -1F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd5")))
            .spell(SpellBuilder.forEffect()
                .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D)
                    .onTick(20D))
                .onExpire(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(ValueCalculation.base("petrify", 5), Elements.Nature))
                    .setTarget(TargetSelector.TARGET.create()))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 15D, 1D))
                .onExpire(PartBuilder.justAction(SpellAction.PLAY_SOUND.create(SoundEvents.ENTITY_SHEEP_SHEAR, 1D, 1D)))
                .buildForEffect())
            .build();

    }
}
