package com.robertx22.age_of_exile.database.data.spells.spell_classes.divine;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellDamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.library_of_exile.packets.particles.ParticleEnum;
import com.robertx22.library_of_exile.packets.particles.ParticlePacketData;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class PurifyingFiresSpell extends BaseSpell {

    private PurifyingFiresSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.SPECIAL;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.BLOCK_FIRE_EXTINGUISH;
                }

                @Override
                public Elements element() {
                    return Elements.Fire;
                }
            }.cooldownIfCanceled(true)
                .setSwingArmOnCast()
                .castingWeapon(CastingWeapon.MELEE_WEAPON));
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.STR;
    }

    @Override
    public void castExtra(SpellCastContext ctx) {

        if (ctx.caster instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) ctx.caster;
            player.spawnSweepAttackParticles();
        }

        ctx.caster.world.playSound((PlayerEntity) null, ctx.caster.getX(), ctx.caster.getY(), ctx.caster.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1.0F, 1.0F);

        Vec3d look = ctx.caster.getRotationVector()
            .multiply(3);

        List<LivingEntity> list = EntityFinder.start(ctx.caster, LivingEntity.class, ctx.caster.getPos()
            .add(look)
            .add(0, ctx.caster.getHeight() / 2, 0))
            .finder(EntityFinder.Finder.RADIUS)
            .radius(2)
            .height(2)
            .build();
        /*

        for (int i = 0; i < 10; i++) {
            // TODO THIS IS JUST FOR TESTING
            LivingEntity c = ctx.caster;
            FallingBlockEntity b = new FallingBlockEntity(c.world, c.getX() + i, c.getY(), c.getZ() + i, Blocks.ICE.getDefaultState());
            b.dropItem = false;
            b.timeFalling = 1;
            b.setVelocity(0, 0.5F, 0);
            b.setHurtEntities(false);

            destroyedOnLandingDuck m = (destroyedOnLandingDuck) b;
            m.setDestroyedOnLanding(true);

            ctx.caster.world.spawnEntity(b);
        }

         */

        SoundUtils.playSound(ctx.caster, SoundEvents.BLOCK_FIRE_EXTINGUISH, 1, 1);

        for (LivingEntity en : list) {

            int num = ctx.getConfigFor(this)
                .getCalc(ctx.skillGem)
                .getCalculatedValue(ctx.data, ctx.skillGem);

            SpellDamageEffect dmg = new SpellDamageEffect(ctx.caster, en, num, ctx.data, Load.Unit(en),
                this
            );
            dmg.removeKnockback();
            dmg.Activate();

            ParticleEnum.sendToClients(
                en.getBlockPos(), en.world,
                new ParticlePacketData(en.getPos(), ParticleEnum.AOE).radius(1)
                    .motion(new Vec3d(0, 0, 0))
                    .type(ParticleTypes.FLAME)
                    .amount((int) (45)));

        }
    }

    public static PurifyingFiresSpell getInstance() {
        return PurifyingFiresSpell.SingletonHolder.INSTANCE;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 6, 9);
        c.set(SC.BASE_VALUE, 3, 6);
        c.set(SC.ATTACK_SCALE_VALUE, 0.5F, 1F);
        c.set(SC.CAST_TIME_TICKS, 0, 0);
        c.set(SC.COOLDOWN_SECONDS, 1, 1);
        c.set(SC.TIMES_TO_CAST, 1, 1);

        c.setMaxLevel(16);

        return c;
    }

    @Override
    public String GUID() {
        return "purifying_fires";
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Attack enemies in melee: "));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.PurifyingFires;
    }

    private static class SingletonHolder {
        private static final PurifyingFiresSpell INSTANCE = new PurifyingFiresSpell();
    }
}