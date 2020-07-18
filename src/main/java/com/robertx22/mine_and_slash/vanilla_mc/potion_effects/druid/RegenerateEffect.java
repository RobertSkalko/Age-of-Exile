package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.druid;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.PreCalcSpellConfigs;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.nature.NatureBalmSpell;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourcesData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ParticleUtils;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.BasePotionEffect;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.OnTickAction;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class RegenerateEffect extends BasePotionEffect {

    public static final RegenerateEffect INSTANCE = new RegenerateEffect();

    private RegenerateEffect() {
        super(StatusEffectType.BENEFICIAL, 4393423);

        this.tickActions.add(new OnTickAction(ctx -> {
            if (ctx.entity.world.isClient) {
                ParticleUtils.spawnParticles(ParticleTypes.HAPPY_VILLAGER, ctx.entity, 3);
            } else {

                int num = getCalc(ctx.caster)
                    .getCalculatedValue(ctx.casterData, ctx.spellsCap, getAbilityThatDeterminesLevel());

                ResourcesData.Context hp = new ResourcesData.Context(ctx.caster, ctx.entity, ctx.casterData,
                    ctx.entityData, ResourcesData.Type.HEALTH, num,
                    ResourcesData.Use.RESTORE,
                    NatureBalmSpell.getInstance()
                );

                ctx.entityData.modifyResource(hp);
            }
            return ctx;
        }, info -> {
            List<Text> list = new ArrayList<>();
            list.add(new LiteralText("Heals user."));
            list.addAll(getCalc(info.player).GetTooltipString(info, Load.spells(info.player), getAbilityThatDeterminesLevel()));
            return list;
        }));

    }

    @Override
    public String GUID() {
        return "self_regen";
    }

    @Override
    public String locNameForLangFile() {
        return "Regenerate";
    }

    @Override
    public PreCalcSpellConfigs getPreCalcConfig() {
        PreCalcSpellConfigs p = new PreCalcSpellConfigs();
        return p;
    }

    @Override
    public BaseSpell getSpell() {
        return NatureBalmSpell.getInstance();
    }

}
