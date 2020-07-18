package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.nature.GorgonsGazeSpell;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.SoundUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticleEnum;
import com.robertx22.mine_and_slash.vanilla_mc.packets.particles.ParticlePacketData;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.IOnBasicAttackedPotion;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.OnTickAction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class PetrifyEffect extends BasePotionEffect implements IOnBasicAttackedPotion {

    public static final PetrifyEffect INSTANCE = new PetrifyEffect();

    private PetrifyEffect() {
        super(StatusEffectType.HARMFUL, 4393423);
        this.setRegistryName(new Identifier(Ref.MODID, GUID()));

        this.addAttributeModifier(EntityAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160892",
            (double) -0.95F, EntityAttributeModifier.Operation.MULTIPLY_TOTAL
        );

        this.tickActions.add(new OnTickAction(ctx -> {
            ParticleEnum.sendToClients(
                ctx.entity, new ParticlePacketData(ctx.entity.getBlockPos(), ParticleEnum.PETRIFY).radius(1)
                    .type(ParticleTypes.CLOUD)
                    .amount(15));

            SoundUtils.playSound(ctx.entity, SoundEvents.BLOCK_STONE_BREAK, 0.5F, 0.5F);
            return ctx;
        }, null));

    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        p.set(SC.BASE_VALUE, 1, 5);
        return p;
    }

    @Nullable
    @Override
    public BaseSpell getSpell() {
        return GorgonsGazeSpell.getInstance();
    }

    @Override
    public String GUID() {
        return "petrify";
    }

    @Override
    public String locNameForLangFile() {
        return "Petrify";
    }

    @Override
    public int getMaxStacks() {
        return 1;
    }

    @Override
    public List<Text> getEffectTooltip(TooltipInfo info) {

        List<Text> list = new ArrayList<>();
        list.add(new LiteralText("Petrifies Enemy."));
        list.add(new LiteralText("If Attacked, does extra damage, but stops effect."));
        list.addAll(getCalc(info.player).GetTooltipString(info, Load.spells(info.player), this));

        return list;
    }

    @Override
    public void onBasicAttacked(StatusEffectInstance instance, LivingEntity source, LivingEntity target) {

        int num = getCalc(source).getCalculatedValue(Load.Unit(source), Load.spells(source), this);

        DamageEffect dmg = new DamageEffect(null, source, target, num, EffectData.EffectTypes.SPELL, WeaponTypes.None);
        dmg.element = Elements.Nature;
        dmg.Activate();

        ParticleEnum.sendToClients(
            target, new ParticlePacketData(target.getBlockPos(), ParticleEnum.PETRIFY).radius(1)
                .type(ParticleTypes.CLOUD)
                .amount(20));

        target.playSound(SoundEvents.BLOCK_STONE_BREAK, 1, 1);

        target.removeStatusEffect(this);

    }
}