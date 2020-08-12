package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.capability.player.PlayerSpellCap;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases.data.PotionStat;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface IApplyStatPotion {

    default void applyStats(World world, StatusEffectInstance instance, LivingEntity target) {
        ExtraPotionData extraData = PotionDataSaving.getData(target, instance);

        LivingEntity caster = extraData.getCaster(world);

        if (caster != null && extraData != null) {
            getStatsAffected((BasePotionEffect) instance.getEffectType(), Load.Unit(caster), Load.spells(caster), extraData).forEach(x -> x.applyStats(Load.Unit(target)));
        }

    }

    default List<ExactStatData> getStatsAffected(BasePotionEffect effect, EntityCap.UnitData data, PlayerSpellCap.ISpellsCap cap, ExtraPotionData extraData) {
        return getPotionStats().stream()
            .map(x -> x.getExactStat(data, cap, extraData, effect))
            .collect(Collectors.toList());
    }

    List<PotionStat> getPotionStats();

    default List<Text> getStatTooltip(TooltipInfo info, BasePotionEffect effect) {

        List<Text> list = new ArrayList<>();

        ExtraPotionData minStacks = new ExtraPotionData();

        list.add(new LiteralText(Formatting.GREEN + "Affects stats: "));

        getStatsAffected(effect, info.unitdata, Load.spells(info.player), minStacks).forEach(x -> {
            list.addAll(x.GetTooltipString(info));
        });

        return list;

    }

}
