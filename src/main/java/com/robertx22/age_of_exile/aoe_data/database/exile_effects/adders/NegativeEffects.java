package com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.ExileEffectBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.VanillaStatData;
import com.robertx22.age_of_exile.database.data.spells.SetAdd;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleMotion;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;

import java.util.UUID;

import static net.minecraft.entity.attribute.EntityAttributes.*;

public class NegativeEffects implements ExileRegistryInit {

    public static EffectCtx ELE_WEAKNESS = new EffectCtx("ele_weakness", "Ele Weakness", 0, Elements.Elemental, EffectType.negative);
    public static EffectCtx PETRIFY = new EffectCtx("petrify", "Petrify", 1, Elements.Earth, EffectType.negative);
    public static EffectCtx CHILL = new EffectCtx("chill", "Chill", 2, Elements.Water, EffectType.negative);
    public static EffectCtx POISON = new EffectCtx("poison", "Poison", 3, Elements.Earth, EffectType.negative);
    public static EffectCtx WOUNDS = new EffectCtx("wounds", "Wounds", 4, Elements.Physical, EffectType.negative);
    public static EffectCtx BURN = new EffectCtx("burn", "Burn", 5, Elements.Fire, EffectType.negative);
    public static EffectCtx TORMENT = new EffectCtx("torment", "Torment", 7, Elements.Elemental, EffectType.negative);
    public static EffectCtx BLEED = new EffectCtx("bleed", "Bleed", 8, Elements.Physical, EffectType.negative);
    public static EffectCtx BLIND = new EffectCtx("blind", "Blind", 10, Elements.Earth, EffectType.negative);
    public static EffectCtx STUN = new EffectCtx("stun", "Stun", 11, Elements.Physical, EffectType.negative);
    public static EffectCtx SLOW = new EffectCtx("slow", "Slow", 12, Elements.Physical, EffectType.negative);
    public static EffectCtx AGONY = new EffectCtx("agony", "Curse of Agony", 13, Elements.Elemental, EffectType.negative);
    public static EffectCtx WEAKNESS = new EffectCtx("weak", "Curse of Weakness", 14, Elements.Elemental, EffectType.negative);
    public static EffectCtx DESPAIR = new EffectCtx("despair", "Curse of Despair", 15, Elements.Elemental, EffectType.negative);
    public static EffectCtx CHARM = new EffectCtx("charm", "Charm", 16, Elements.Elemental, EffectType.negative);
    public static EffectCtx GROUNDING = new EffectCtx("ground", "Grounding", 17, Elements.Physical, EffectType.negative);
    public static EffectCtx MARK_OF_DEATH = new EffectCtx("mark_of_death", "Mark of Death", 18, Elements.Physical, EffectType.negative);
    public static EffectCtx SHRED = new EffectCtx("shred", "Shred", 19, Elements.Physical, EffectType.negative);


    @Override
    public void registerAll() {

        ExileEffectBuilder.of(SHRED)
                .maxStacks(3)
                .stat(-25, Armor.getInstance(), ModType.FLAT)
                .stat(-3, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .stat(-5, Armor.getInstance(), ModType.PERCENT)
                .build();

        ExileEffectBuilder.of(MARK_OF_DEATH)
                .maxStacks(1)
                .stat(-50, Armor.getInstance(), ModType.FLAT)
                .stat(-25, DodgeRating.getInstance(), ModType.PERCENT)
                .stat(-10, new ElementalResist(Elements.Elemental))
                .build();

        ExileEffectBuilder.of(GROUNDING)
                .maxStacks(1)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.justAction(SpellAction.SET_ADD_MOTION.create(SetAdd.ADD, 1D, ParticleMotion.Downwards))
                                .addTarget(TargetSelector.CASTER.create()))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 2D, 0.5D)
                                .onTick(20D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(CHARM)
                .maxStacks(5)
                .stat(-3, Armor.getInstance(), ModType.PERCENT)
                .stat(-3, DodgeRating.getInstance(), ModType.PERCENT)
                .stat(-3, new ElementalResist(Elements.Elemental))
                .build();

        ExileEffectBuilder.of(AGONY)
                .maxStacks(1)
                .stat(20, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.melee))
                .stat(20, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.magic))
                .stat(20, Stats.STYLE_DAMAGE_RECEIVED.get(PlayStyle.ranged))
                .stat(-10, DatapackStats.MOVE_SPEED)

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.WITCH, 2D, 0.5D)
                                .onTick(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(WEAKNESS)
                .maxStacks(1)
                .stat(-30, Armor.getInstance())
                .stat(-30, DodgeRating.getInstance())
                .stat(-15, new ElementalResist(Elements.Elemental))

                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.WITCH, 2D, 0.5D)
                                .onTick(20D))
                        .buildForEffect())

                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(DESPAIR)
                .maxStacks(1)
                .stat(-20, Stats.TOTAL_DAMAGE.get())
                .spell(SpellBuilder.forEffect()
                        .onExpire(PartBuilder.damage(SpellCalcs.DESPAIR, Elements.Elemental))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.WITCH, 2D, 0.5D)
                                .onTick(20D))
                        .buildForEffect())
                .addTags(EffectTags.curse, EffectTags.negative)
                .build();

        ExileEffectBuilder.of(SLOW)
                .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -25, ModType.GLOBAL_INCREASE, UUID.fromString("3fb10485-f309-468f-afc6-a23b0d6cf4c1")))
                .vanillaStat(VanillaStatData.create(GENERIC_ATTACK_SPEED, -10, ModType.GLOBAL_INCREASE, UUID.fromString("00fb60a7-904b-462f-a7cb-a557f02e362e")))
                .addTags(EffectTags.negative)
                .build();

        ExileEffectBuilder.of(STUN)
                .addTags(EffectTags.immobilizing, EffectTags.negative)
                .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -100, ModType.GLOBAL_INCREASE, UUID.fromString("3fb10485-f309-468f-afc6-a23b0d6cf4c1")))
                .vanillaStat(VanillaStatData.create(GENERIC_ATTACK_SPEED, -100, ModType.GLOBAL_INCREASE, UUID.fromString("00fb60a7-904b-462f-a7cb-a557f02e362e")))
                .vanillaStat(VanillaStatData.create(GENERIC_ATTACK_DAMAGE, -100, ModType.GLOBAL_INCREASE, UUID.fromString("10fb60a7-904b-462f-a7cb-a557f02e362e")))
                .build();

        ExileEffectBuilder.of(TORMENT)
                .maxStacks(1)
                .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, 0.2F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9f32fa-c8c1-455c-92aa-4a94c2a70cd8")))
                .stat(-10, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.dotDamageOnTick(TORMENT.effectId, SpellCalcs.TORMENT, Elements.Elemental)
                                .onTick(20D))
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.SOUL, 10D, 1D)
                                .onTick(10D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(CHILL)

                .maxStacks(5)
                .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -0.1F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd8")))
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SNOWBALL, 10D, 1D)
                                .onTick(10D))
                        .buildForEffect())
                .build();


        ExileEffectBuilder.of(POISON)
                .maxStacks(5)
                .stat(-5, new ElementalResist(Elements.Elemental), ModType.PERCENT)
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.dotDamageOnTick(POISON.effectId, SpellCalcs.POISON, Elements.Earth)
                                .onTick(20D))
                        .onTick(PartBuilder.aoeParticles(ModRegistry.PARTICLES.POISON, 1D, 1D)
                                .onTick(2D))
                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(BURN)
                .maxStacks(5)
                .spell(SpellBuilder.forEffect()

                        .onTick(PartBuilder.dotDamageOnTick(BURN.effectId, SpellCalcs.BURN, Elements.Fire)
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

                        .onTick(PartBuilder.dotDamageOnTick(BLEED.effectId, SpellCalcs.BLEED, Elements.Physical)
                                .onTick(20D))

                        .onTick(PartBuilder.aoeParticles(ModRegistry.PARTICLES.BLOOD_DRIP, 2D, 0.5D)
                                .onTick(5D))

                        .buildForEffect())
                .build();

        ExileEffectBuilder.of(ELE_WEAKNESS)
                .stat(-15, new ElementalResist(Elements.Elemental), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(BLIND)
                .vanillaStat(VanillaStatData.create(GENERIC_ATTACK_DAMAGE, -1000F, ModType.GLOBAL_INCREASE, UUID.fromString("5eccf34c-29f7-4eea-bbad-82a905594064")))
                .vanillaStat(VanillaStatData.create(GENERIC_ATTACK_SPEED, -1000F, ModType.GLOBAL_INCREASE, UUID.fromString("57eb6210-2a42-4ad3-a604-6f679d440a9b")))
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 3D, 1D)
                                .onTick(20D))
                        .buildForEffect()
                )
                .addTags(EffectTags.immobilizing)
                .build();

        ExileEffectBuilder.of(WOUNDS)
                .stat(-25, Stats.HEAL_STRENGTH.get(), ModType.FLAT)
                .build();

        ExileEffectBuilder.of(PETRIFY)
                .addTags(EffectTags.immobilizing)
                .vanillaStat(VanillaStatData.create(GENERIC_MOVEMENT_SPEED, -1F, ModType.GLOBAL_INCREASE, UUID.fromString("bd9d32fa-c8c2-455c-92aa-4a94c2a70cd5")))
                .spell(SpellBuilder.forEffect()
                        .onTick(PartBuilder.aoeParticles(ParticleTypes.ITEM_SLIME, 10D, 1D)
                                .onTick(20D))
                        .onExpire(PartBuilder.justAction(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PETRIFY, Elements.Earth))
                                .setTarget(TargetSelector.TARGET.create()))
                        .onExpire(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 15D, 1D))
                        .onExpire(PartBuilder.justAction(SpellAction.PLAY_SOUND.create(SoundEvents.ENTITY_SHEEP_SHEAR, 1D, 1D)))
                        .buildForEffect())
                .build();

    }
}
