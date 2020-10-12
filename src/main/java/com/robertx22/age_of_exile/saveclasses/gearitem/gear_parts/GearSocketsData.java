package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

@Storable
public class GearSocketsData implements IStatsContainer, IGearPartTooltip {

    @Store
    public List<SocketData> sockets = new ArrayList<>();

    @Store
    public int max_sockets = 0;

    @Store
    public String activated_runeword = "";

    @Store
    public int runeword_percent = 0;

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();

        sockets.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear, info)));
        RuneWord word = getRuneWord();

        if (word != null) {
            word.stats.forEach(s -> {
                ExactStatData exact = s.ToExactStat(runeword_percent, gear.level);
                list.add(new TooltipStatWithContext(new TooltipStatInfo(exact, info), s, gear.level));
            });
        }
        return list;
    }

    public void create(GearItemData gear) {
        IGearRarity rarity = gear.getRarity();

        this.max_sockets = rarity.minSockets();

        for (int i = 0; i < rarity.maxSockets() - rarity.minSockets(); i++) {
            if (RandomUtils.roll(rarity.socketChance())) {
                max_sockets++;
            }
        }
    }

    public int getEmptySockets() {
        return max_sockets - sockets.size();
    }

    public RuneWord getRuneWord() {

        if (SlashRegistry.Runewords()
            .isRegistered(activated_runeword)) {
            return SlashRegistry.Runewords()
                .get(activated_runeword);
        }
        return null;

    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        List<ExactStatData> list = new ArrayList<>();
        sockets.forEach(x -> list.addAll(x.GetAllStats(gear)));
        RuneWord word = getRuneWord();

        if (word != null) {
            word.stats.forEach(s -> list.add(s.ToExactStat(runeword_percent, gear.level)));
        }

        return list;
    }

    @Override
    public List<Text> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Text> list = new ArrayList<Text>();
        getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    @Override
    public Part getPart() {
        return null;
    }
}
