package com.robertx22.age_of_exile.vanilla_mc.potion_effects.ember_mage;

import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.saveclasses.spells.calc.SpellCalcData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.age_of_exile.vanilla_mc.packets.particles.ParticlePacketData;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.OnTickAction;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class BurnEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final BurnEffect INSTANCE = new BurnEffect();

    private BurnEffect() {
        super(StatusEffectType.HARMFUL, 4393423);

        this.tickActions.add(new OnTickAction(ctx -> {
            int num = getCalc(ctx.caster).getCalculatedValue(ctx.caster);

            DamageEffect dmg = new DamageEffect(null, ctx.caster, ctx.entity, num, ctx.casterData, ctx.entityData,
                EffectData.EffectTypes.SPELL, WeaponTypes.None
            );
            dmg.element = Elements.Fire;
            dmg.removeKnockback();
            dmg.Activate();

            SoundUtils.playSound(ctx.entity, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, 0.5F, 1F);

            ParticleEnum.sendToClients(
                ctx.entity, new ParticlePacketData(ctx.entity.getBlockPos(), ParticleEnum.AOE).type(
                    ParticleTypes.FLAME)
                    .motion(new Vec3d(0, 0, 0))
                    .amount(5));

            return ctx;
        }, info -> {
            List<Text> list = new ArrayList<>();
            list.add(new LiteralText("Does damage:"));
            list.addAll(getCalc(info.player).GetTooltipString(info));
            return list;
        }));

    }

    @Override
    public String GUID() {
        return "burn";
    }

    @Override
    public String locNameForLangFile() {
        return "Burn";
    }

    @Override
    public int getMaxStacks() {
        return 3;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(-5, new ElementalResist(Elements.Fire)));
        list.add(new PotionStat(-5, new ElementalResist(Elements.Water)));
        return list;
    }

    @Override
    public SpellCalcData getCalc(LivingEntity caster) {
        return SpellCalcData.base(4);
    }

    @Override
    public int getDurationInSeconds(LivingEntity en) {
        return 10;
    }

    @Override
    public int getTickRate(LivingEntity en) {
        return 25;
    }

}

