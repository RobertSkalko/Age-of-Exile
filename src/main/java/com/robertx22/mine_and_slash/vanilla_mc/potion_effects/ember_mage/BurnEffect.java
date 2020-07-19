package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.ember_mage;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacketData;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IApplyStatPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.OnTickAction;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class BurnEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final BurnEffect INSTANCE = new BurnEffect();

    private BurnEffect() {
        super(StatusEffectType.HARMFUL, 4393423);

        this.tickActions.add(new OnTickAction(ctx -> {
            int num = getCalc(ctx.caster).getCalculatedValue(ctx.casterData, ctx.spellsCap, this);

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
            List<MutableText> list = new ArrayList<>();
            list.add(new LiteralText("Does damage:"));
            list.addAll(getCalc(info.player).GetTooltipString(info, Load.spells(info.player), getAbilityThatDeterminesLevel()));
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
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.set(SC.BASE_VALUE, 4, 5);
        p.set(SC.DURATION_TICKS, 6 * 60, 10 * 60);
        p.set(SC.TICK_RATE, 30, 20);
        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return null;
    }

}

