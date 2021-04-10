package com.robertx22.age_of_exile.database.data.set;

import com.google.common.base.Preconditions;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GearSet implements ISerializedRegistryEntry<GearSet>, IAutoGson<GearSet>, ITooltipList, IAutoLocName {

    public static GearSet SERIALIZER = new GearSet();

    public String id = "";

    public HashMap<Integer, List<OptScaleExactStat>> stats = new HashMap<>();

    transient String locname = "";

    public boolean hasSetBonus(Integer num, EntityCap.UnitData data) {
        return data.getUnit().sets.getOrDefault(id, 0) >= num;
    }

    public StatContext getStats(EntityCap.UnitData data) {

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
    public List<Text> GetTooltipString(TooltipInfo info) {

        List<Text> list = new ArrayList<>();

        int lvl = info.unitdata.getLevel();

        list.add(locName().append(" Set")
            .formatted(Formatting.GREEN));

        stats.entrySet()
            .forEach(x -> {
                Formatting format = Formatting.GRAY;

                int num = x.getKey();
                if (info.unitdata.getUnit().sets.getOrDefault(id, 0) >= x.getKey()) {
                    format = Formatting.GREEN;
                }

                list.add(new LiteralText(format + "[" + num + "/" + num + "]" + " Set Bonus:"));

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
        return Ref.MODID + ".sets." + id;
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
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.GEAR_SET;
    }

    @Override
    public String GUID() {
        return id;
    }
}
