package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.mmorpg.Ref;
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
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import java.util.ArrayList;
import java.util.List;

public class PoisonEffect extends BasePotionEffect implements IApplyStatPotion {

    public static final PoisonEffect INSTANCE = new PoisonEffect();

    private PoisonEffect() {
        super(StatusEffectType.HARMFUL, 4393423);
        this.setRegistryName(new Identifier(Ref.MODID, GUID()));

        this.tickActions.add(new OnTickAction(ctx -> {
            int num = getCalc(ctx.caster).getCalculatedValue(ctx.casterData, ctx.spellsCap, this);

            DamageEffect dmg = new DamageEffect(null, ctx.caster, ctx.entity, num, ctx.casterData, ctx.entityData,
                EffectData.EffectTypes.SPELL, WeaponTypes.None
            );
            dmg.element = Elements.Nature;
            dmg.removeKnockback();
            dmg.Activate();

            ParticleEnum.sendToClients(
                ctx.entity, new ParticlePacketData(ctx.entity.getBlockPos(), ParticleEnum.THORNS).amount(10));

            SoundUtils.playSound(ctx.entity, SoundEvents.BLOCK_GRASS_BREAK, 1, 1);
            return ctx;
        }, info -> {
            List<Text> list = new ArrayList<>();
            list.add(new LiteralText("Does damage:"));
            list.addAll(getCalc(info.player).GetTooltipString(info, Load.spells(info.player), this));

            return list;
        }));
    }

    @Override
    public String GUID() {
        return "poison";
    }

    @Override
    public String locNameForLangFile() {
        return "Poison";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<PotionStat> getPotionStats() {
        List<PotionStat> list = new ArrayList<>();
        list.add(new PotionStat(-5, new ElementalResist(Elements.Thunder)));
        list.add(new PotionStat(-5, new ElementalResist(Elements.Nature)));

        return list;
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.set(SC.BASE_VALUE, 2, 4);
        p.set(SC.TICK_RATE, 30, 15);
        p.set(SC.DURATION_TICKS, 60, 120);
        return p;
    }

    @Nullable
    @Override
    public BaseSpell getSpell() {
        return null;
    }

}

