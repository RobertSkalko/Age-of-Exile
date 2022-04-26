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
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;

import java.util.Arrays;

public class HunterSpells implements ExileRegistryInit {

    public static String ARROW_STORM = "arrow_storm";
    public static String DASH_ID = "dash";

    public static String SMOKE_BOMB = "smoke_bomb";

    @Override

    public void registerAll() {

        SpellBuilder.of(SMOKE_BOMB, SpellConfiguration.Builder.instant(7, 20 * 60), "Smoke Bomb",
                Arrays.asList())
            .manualDesc("Throw out a smoke bomb, blinding enemies and reducing threat.")
            .attackStyle(PlayStyle.ranged)
            .weaponReq(CastingWeapon.ANY_WEAPON)
            .onCast(PartBuilder.Sound.play(SoundEvents.SPLASH_POTION_BREAK, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.AGGRO.create(SpellCalcs.SMOKE_BOMB, AggroAction.Type.DE_AGGRO))
                .addActions(SpellAction.EXILE_EFFECT.create(NegativeEffects.BLIND.resourcePath, ExileEffectAction.GiveOrTake.GIVE_STACKS, 20D * 5))
                .addTarget(TargetSelector.AOE.create(10D, EntityFinder.SelectionType.RADIUS, AllyOrEnemy.enemies)))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.SMOKE, 200D, 3D))
            .onCast(PartBuilder.Particles.aoe(ParticleTypes.EFFECT, 50D, 3D))
            .build();

        SpellBuilder.of(DASH_ID, SpellConfiguration.Builder.instant(10, 15)
                , "Dash",
                Arrays.asList(SpellTag.movement, SpellTag.technique))
            .manualDesc(
                "Dash in your direction and gain slowfall.")
            .weaponReq(CastingWeapon.NON_MAGE_WEAPON)
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundEvents.CREEPER_PRIMED, 1D, 1.6D)
                .addActions(SpellAction.CASTER_USE_COMMAND.create("effect give @p minecraft:slow_falling 1 1 true")))
            .onCast(PartBuilder.Sound.play(SoundEvents.FIRE_EXTINGUISH, 1D, 1.6D))

            .teleportForward()

            .build();

        SpellBuilder.of(ARROW_STORM, SpellConfiguration.Builder.arrowImbue(20, 20 * 25), "Arrow Storm",
                Arrays.asList(SpellTag.projectile, SpellTag.damage))
            .weaponReq(CastingWeapon.RANGED)
            .manualDesc("Shoot out arrows in an arc, dealing ")
            .attackStyle(PlayStyle.ranged)
            .onCast(PartBuilder.Sound.play(SoundEvents.ARROW_SHOOT, 1D, 1D))
            .onCast(PartBuilder.justAction(SpellAction.SUMMON_PROJECTILE.createArrow(5D)))
            .onHit(PartBuilder.Particles.tickAoe(3D, ParticleTypes.CLOUD, 3D, 0.1D))
            .onHit(PartBuilder.Sound.play(SoundEvents.ARROW_HIT, 1D, 1D))
            .onHit(PartBuilder.Damage.of(SpellCalcs.ARROW_STORM))
            .onTick(PartBuilder.Particles.tickAoe(5D, ParticleTypes.CRIT, 5D, 0.1D))
            .build();

    }

}
