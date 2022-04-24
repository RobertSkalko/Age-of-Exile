package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.AggroAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.ExileEffectAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashSounds;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class HolySpells implements ExileRegistryInit {

    public static String HEALING_AURA_ID = "healing_aura";
    public static String UNDYING_WILL = "undying_will";
    public static String INSPIRATION = "awaken_mana";
    public static String SHOOTING_STAR = "shooting_star";

    public static String TAUNT = "taunt";
    public static String PULL = "pull";

    @Override
    public void registerAll() {

        SpellBuilder.of(TAUNT, SpellConfiguration.Builder.instant(0, 20 * 30)
                    .setSwingArm(), "Taunt",
                Arrays.asList(SpellTag.area))
            .manualDesc(
                "Shout, making enemies nearby want to attack you. " +
                    "Generates theat"
            )
            .attackStyle(PlayStyle.melee)
            .weaponReq(CastingWeapon.MELEE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.SHIELD_BLOCK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.CLOUD, 20D, 3D))

            .build();

        SpellBuilder.of(PULL, SpellConfiguration.Builder.instant(5, 60 * 20), "Pull",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Pull enemies in area to you, dealing damage and slowing them."
            )
            .attackStyle(PlayStyle.melee)
            .onCast(PartBuilder.playSound(SoundEvents.ANVIL_HIT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addActions(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 20D * 5))
                .addActions(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PULL))
                .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.STUN.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 2))
                .addTarget(TargetSelector.AOE.create(8D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 100D, 6D, 0.1D))
            .build();

        SpellBuilder.of(INSPIRATION, SpellConfiguration.Builder.instant(0, 300 * 20), "Inspiration",
                Arrays.asList(SpellTag.heal)
            )
            .manualDesc("Restores  mana.")
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1D, 1D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 40D, 1.5D))
            .onCast(PartBuilder.aoeParticles(ParticleTypes.HEART, 12D, 1.5D))
            .onCast(PartBuilder.restoreManaToCaster(SpellCalcs.AWAKEN_MANA))
            .build();

        SpellBuilder.of(SHOOTING_STAR, SpellConfiguration.Builder.instant(10, 20)
                    .setSwingArm(), "Shooting Star",
                Arrays.asList(SpellTag.projectile, SpellTag.heal))
            .manualDesc("Shoots a star that heals allies")

            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.playSound(SoundEvents.BEACON_ACTIVATE, 1D, 1.7D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.NETHER_STAR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)
                .put(MapField.HITS_ALLIES, true)))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.CRIT, 1D, 0.5D)
                .onTick(1D))
            .onTick(PartBuilder.aoeParticles(ParticleTypes.ENCHANT, 1D, 0.7D)
                .onTick(1D))
            .onExpire(PartBuilder.healInAoe(SpellCalcs.SHOOTING_STAR, 2D))
            .onExpire(PartBuilder.aoeParticles(ParticleTypes.SOUL_FIRE_FLAME, 10D, 1D))
            .build();

        SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.instant(15, 20 * 30), "Healing Aura",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.playSound(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.groundParticles(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.healInAoe(SpellCalcs.HEALING_AURA, 2D))
            .build();

    }

}
