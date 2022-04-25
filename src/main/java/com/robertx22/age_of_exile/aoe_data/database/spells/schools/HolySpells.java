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
            .onCast(PartBuilder.Sound.play(SoundEvents.SHIELD_BLOCK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.TAUNT, AggroAction.Type.AGGRO))
                .addTarget(TargetSelector.AOE.create(3D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.CLOUD, 20D, 3D))

            .build();

        SpellBuilder.of(PULL, SpellConfiguration.Builder.instant(5, 60 * 20), "Pull",
                Arrays.asList(SpellTag.technique, SpellTag.area, SpellTag.damage))
            .manualDesc(
                "Pull enemies in area to you, dealing damage and slowing them."
            )
            .attackStyle(PlayStyle.melee)
            .onCast(PartBuilder.Sound.play(SoundEvents.ANVIL_HIT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                .addActions(SpellAction.POTION.createGive(Effects.MOVEMENT_SLOWDOWN, 20D * 5))
                .addActions(SpellAction.DEAL_DAMAGE.create(SpellCalcs.PULL))
                .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.STUN.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 2))
                .addTarget(TargetSelector.AOE.create(8D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.Particles.groundEdge(ParticleTypes.CRIT, 100D, 6D, 0.1D))
            .build();

        SpellBuilder.of(SHOOTING_STAR, SpellConfiguration.Builder.instant(10, 20)
                    .setSwingArm(), "Shooting Star",
                Arrays.asList(SpellTag.projectile, SpellTag.heal))
            .manualDesc("Shoots a star that heals allies")

            .weaponReq(CastingWeapon.MAGE_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.BEACON_ACTIVATE, 1D, 1.7D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.create(Items.NETHER_STAR, 1D, 1D, SlashEntities.SIMPLE_PROJECTILE.get(), 20D, false)
                .put(MapField.HITS_ALLIES, true)))
            .onTick(PartBuilder.Particles.aoe(ParticleTypes.CRIT, 1D, 0.5D)
                .onTick(1D))
            .onTick(PartBuilder.Particles.aoe(ParticleTypes.ENCHANT, 1D, 0.7D)
                .onTick(1D))
            .onExpire(PartBuilder.Restore.Health.aoe(SpellCalcs.SHOOTING_STAR, 2D))
            .onExpire(PartBuilder.Particles.aoe(ParticleTypes.SOUL_FIRE_FLAME, 10D, 1D))
            .build();

        SpellBuilder.of(HEALING_AURA_ID, SpellConfiguration.Builder.instant(15, 20 * 30), "Healing Aura",
                Arrays.asList(SpellTag.heal))
            .manualDesc(
                "Heal allies around you")

            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.Sound.play(SlashSounds.BUFF.get(), 1D, 1D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.COMPOSTER, 50D, 2D, 0.2D))
            .onCast(PartBuilder.Particles.ground(ParticleTypes.HEART, 20D, 2D, 0.2D))
            .onCast(PartBuilder.Restore.Health.aoe(SpellCalcs.HEALING_AURA, 2D))
            .build();

    }

}
