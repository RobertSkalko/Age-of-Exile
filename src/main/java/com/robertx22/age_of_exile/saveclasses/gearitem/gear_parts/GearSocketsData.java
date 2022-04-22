package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IStatsContainer;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

@Storable
public class GearSocketsData implements IStatsContainer, IGearPartTooltip {

    @Store
    public List<SocketData> sockets = new ArrayList<>();

    @Store
    private int slots = 1;

    public int getSocketedGemsCount() {
        return sockets.size();
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {
        List<ExactStatData> list = new ArrayList<>();
        for (int i = 0; i < gear.getTotalSockets(); i++) {
            if (sockets.size() > i) {
                list.addAll(sockets.get(i)
                    .GetAllStats(gear));
            }
        }
        return list;
    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<ITextComponent> list = new ArrayList<ITextComponent>();

        for (int i = 0; i < gear.getTotalSockets(); i++) {
            if (sockets.size() > i) {
                SocketData data = sockets.get(i);
                Gem gem = data.getGem();
                list.add(new SText(gem.getFormat() + "[" + TooltipUtils.STAR_2 + "] ").append(data.GetTooltipString(info, gear)
                    .get(0)));
            }
        }

        for (int i = 0; i < gear.getEmptySockets(); i++) {
            list.add(new SText(TextFormatting.YELLOW + "[Socket]"));
        }

        return list;
    }

    @Override
    public Part getPart() {
        return null;
    }
}
