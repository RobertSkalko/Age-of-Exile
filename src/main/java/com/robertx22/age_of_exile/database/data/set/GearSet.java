package com.robertx22.age_of_exile.database.data.set;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GearSet implements JsonExileRegistry<GearSet>, IAutoGson<GearSet>, ITooltipList, IAutoLocName {

    public static GearSet SERIALIZER = new GearSet();

    public String id = "";

    public HashMap<Integer, List<OptScaleExactStat>> stats = new HashMap<>();

    transient String locname = "";

    @Override
    public int Weight() {
        return 1000;
    }

    public StatContext getStats(EntityData data) {

        List<ExactStatData> list = new ArrayList<>();

        int lvl = data.getLevel();

        stats.entrySet()
            .forEach(x -> {
                if (data.getUnit().sets.getOrDefault(id, 0) >= x.getKey()) {
                    x.getValue()
                        .forEach(s -> {
                            list.add(s.toExactStat(lvl));
                        });
                }
            });

        return new MiscStatCtx(list);

    }

    @Override
    public List<ITextComponent> GetTooltipString(TooltipInfo info) {

        List<ITextComponent> list = new ArrayList<>();

        int lvl = info.unitdata.getLevel();

        list.add(locName().append(" Set")
            .withStyle(TextFormatting.GREEN));

        stats.entrySet()
            .forEach(x -> {
                TextFormatting format = TextFormatting.GRAY;

                int num = x.getKey();
                if (info.unitdata.getUnit().sets.getOrDefault(id, 0) >= x.getKey()) {
                    format = TextFormatting.GREEN;
                }

                list.add(new StringTextComponent(format + "[" + num + "/" + num + "]" + " Set Bonus:"));

                x.getValue()
                    .forEach(s -> {
                        list.addAll(s.toExactStat(lvl)
                            .GetTooltipString(info));
                    });

            });

        return list;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Item_Sets;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".sets." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    public static class Builder {

        GearSet set = new GearSet();

        public static Builder of(String id, String name) {
            Builder b = new Builder();
            b.set.id = id;
            b.set.locname = name;
            return b;

        }

        public Builder stat(Integer piecesNeeded, OptScaleExactStat... stats) {
            for (OptScaleExactStat stat : stats) {
                stat.scale_to_lvl = true;
            }
            set.stats.put(piecesNeeded, Arrays.asList(stats));
            return this;

        }

        public GearSet build() {
            Preconditions.checkArgument(!set.stats.isEmpty());
            set.addToSerializables();
            return set;
        }

    }

    @Override
    public Class<GearSet> getClassForSerialization() {
        return GearSet.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_SET;
    }

    @Override
    public String GUID() {
        return id;
    }
}
