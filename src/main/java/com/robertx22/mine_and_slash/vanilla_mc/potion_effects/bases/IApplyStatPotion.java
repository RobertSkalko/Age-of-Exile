package com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases;

import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.capability.player.PlayerSpellCap;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.ExtraPotionData;
import com.robertx22.mine_and_slash.vanilla_mc.potion_effects.bases.data.PotionStat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public interface IApplyStatPotion {

    default void applyStats(EntityCap.UnitData data, PlayerSpellCap.ISpellsCap cap, StatusEffectInstance instance) {
        ExtraPotionData extraData = PotionDataSaving.getData(instance);

        if (extraData != null) {
            getStatsAffected((BasePotionEffect) instance.getEffectType(), data, cap, extraData).forEach(x -> x.applyStats(data));
        }

    }

    default List<ExactStatData> getStatsAffected(BasePotionEffect effect, EntityCap.UnitData data, PlayerSpellCap.ISpellsCap cap, ExtraPotionData extraData) {
        return getPotionStats().stream()
            .map(x -> x.getExactStat(data, cap, extraData, effect))
            .collect(Collectors.toList());
    }

    List<PotionStat> getPotionStats();

    default List<MutableText> getStatTooltip(TooltipInfo info, BasePotionEffect effect) {

        List<MutableText> list = new ArrayList<>();

        ExtraPotionData minStacks = new ExtraPotionData();

        list.add(new LiteralText(Formatting.GREEN + "Affects stats: "));

        getStatsAffected(effect, info.unitdata, Load.spells(info.player), minStacks).forEach(x -> {
            list.addAll(x.GetTooltipString(info));
        });

        return list;

    }

}
