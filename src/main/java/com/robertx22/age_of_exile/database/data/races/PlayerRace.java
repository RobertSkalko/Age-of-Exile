package com.robertx22.age_of_exile.database.data.races;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.IAutoGson;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class PlayerRace implements ISerializedRegistryEntry<PlayerRace>, IAutoGson<PlayerRace>, IAutoLocName, IAutoLocDesc {
    public static PlayerRace SERIALIZER = new PlayerRace();

    public transient String locname = "";
    public transient String locdesc = "";

    public String id = "";

    public List<ExtraStatPerStat> extra_stats = new ArrayList<>();

    public List<OptScaleExactStat> base_stats = new ArrayList<>();

    public List<RaceLevelingPerk> lvling_perks = new ArrayList<>();

    public final Identifier getIcon() {
        return Ref.id("textures/gui/races/" + id + ".png");
    }

    public List<Text> getTooltip() {

        List<Text> list = new ArrayList<>();

        TooltipInfo info = new TooltipInfo();

        list.add(this.locName()
            .formatted(Formatting.RED));

        list.add(new LiteralText(""));

        list.addAll(TooltipUtils.cutIfTooLong(locDesc().formatted(Formatting.YELLOW)));

        list.add(new LiteralText(""));

        extra_stats.forEach(x -> {
            list.add(new LiteralText("For each 1 ").formatted(Formatting.GREEN)
                .append(Database.Stats()
                    .get(x.for_stat)
                    .locName())
                .append(":"));

            list.addAll(x.stat_given.GetTooltipString(info));
        });

        list.add(new LiteralText(""));

        list.add(new LiteralText("Base Stats:").formatted(Formatting.GREEN));
        base_stats.forEach(x -> list.addAll(x.GetTooltipString(info)));

        return list;

    }

    public static PlayerRace of(String id, String name, String desc, List<ExtraStatPerStat> extraStats, List<OptScaleExactStat> baseStats, List<RaceLevelingPerk> perks) {

        PlayerRace r = new PlayerRace();
        r.locdesc = desc;
        r.locname = name;
        r.id = id;
        r.base_stats = baseStats;
        r.extra_stats = extraStats;
        r.lvling_perks = perks;

        return r;

    }

    public void addStats(PlayerEntity player) {

        EntityCap.UnitData data = Load.Unit(player);

        int lvl = Load.Unit(player)
            .getLevel();

        this.extra_stats.forEach(x -> {
            float toadd = Load.Unit(player)
                .getUnit()
                .getStatInCalculation(x.for_stat)
                .getCalculated()
                .getAverageValue();

            OptScaleExactStat stat = new OptScaleExactStat(x.stat_given.v1 * toadd, x.stat_given.v2 * toadd, x.stat_given.getStat(), x.stat_given.getModType());

            stat.toExactStat(1)
                .applyStats(data);

        });

        this.base_stats.forEach(x -> x.toExactStat(lvl)
            .applyStats(data));

    }

    @Override
    public Class<PlayerRace> getClassForSerialization() {
        return PlayerRace.class;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.RACES;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Races;
    }

    @Override
    public String locDescLangFileGUID() {
        return "races.desc." + id;
    }

    @Override
    public String locDescForLangFile() {
        return locdesc;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Races;
    }

    @Override
    public String locNameLangFileGUID() {
        return "races." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
