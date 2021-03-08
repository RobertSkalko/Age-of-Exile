package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AuraSpellData {

    public float mana_reserved = 0.25F;

    private List<StatModifier> stats = new ArrayList<>();

    public AuraSpellData() {
    }

    public AuraSpellData(float mana_reserved, List<StatModifier> stats) {
        this.mana_reserved = mana_reserved;
        this.stats = stats;
    }

    public List<ExactStatData> getStats(SkillGemData gem, LivingEntity caster, int lvl) {
        return stats.stream()
            .map(x -> x.ToExactStat(gem.stat_perc, lvl))
            .collect(Collectors.toList());

    }

    public List<Text> GetTooltipString(Spell spell, SkillGemData data, TooltipInfo info) {
        List<Text> list = new ArrayList<>();
        list.add(new LiteralText("When Aura is Activated:"));
        getStats(data, info.player, data.lvl).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }
}
