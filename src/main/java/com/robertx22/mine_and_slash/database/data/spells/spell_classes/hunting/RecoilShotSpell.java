package com.robertx22.mine_and_slash.database.data.spells.spell_classes.hunting;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.spells.entities.proj.RangerArrowEntity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.cast_types.SpellCastType;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.ImmutableSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class RecoilShotSpell extends BaseSpell {

    private RecoilShotSpell() {
        super(
            new ImmutableSpellConfigs() {

                @Override
                public SpellCastType castType() {
                    return SpellCastType.PROJECTILE;
                }

                @Override
                public SoundEvent sound() {
                    return SoundEvents.ENTITY_ARROW_SHOOT;
                }

                @Override
                public Elements element() {
                    return Elements.Elemental;
                }
            }.cooldownIfCanceled(true)
                .summonsEntity(w -> new RangerArrowEntity(w))
                .castingWeapon(CastingWeapon.RANGED));
    }

    @Override
    public BaseGearType.PlayStyle getPlayStyle() {
        return BaseGearType.PlayStyle.DEX;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs c = new PreCalcSpellConfigs();

        c.set(SC.MANA_COST, 6, 15);
        c.set(SC.BASE_VALUE, 6, 15);
        c.set(SC.ATTACK_SCALE_VALUE, 0.1F, 1.2F);
        c.set(SC.SHOOT_SPEED, 1F, 1.5F);
        c.set(SC.PROJECTILE_COUNT, 1, 1);
        c.set(SC.CAST_TIME_TICKS, 10, 0);
        c.set(SC.COOLDOWN_SECONDS, 20, 10);
        c.set(SC.DURATION_TICKS, 100, 160);

        c.setMaxLevel(16);

        return c;
    }

    public static RecoilShotSpell getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String GUID() {
        return "recoil_shot";
    }

    public static void dashBackward(LivingEntity caster) {

        float distance = 0.017453292f;
        caster.setVelocity(new Vec3d(0, 0, 0));
        double x = (double) -MathHelper.sin(caster.yaw * distance);
        double z = (double) (MathHelper.cos(caster.yaw * distance));

        caster.takeKnockback(1.8f, x, z);

        if (caster instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) caster).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(caster));
            caster.velocityModified = false;
        }
    }

    @Override
    public void castExtra(SpellCastContext ctx) {
        dashBackward(ctx.caster);
    }

    @Override
    public List<Text> GetDescription(TooltipInfo info, SpellCastContext ctx) {

        List<Text> list = new ArrayList<>();

        list.add(new LiteralText("Shoot an arrow and dash back: "));

        list.addAll(getCalculation(ctx).GetTooltipString(info, ctx));

        return list;

    }

    @Override
    public Words getName() {
        return Words.RecoilShot;
    }

    private static class SingletonHolder {
        private static final RecoilShotSpell INSTANCE = new RecoilShotSpell();
    }
}
