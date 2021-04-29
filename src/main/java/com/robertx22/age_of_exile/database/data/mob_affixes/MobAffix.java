package com.robertx22.age_of_exile.database.data.mob_affixes;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.aoe_data.datapacks.JsonUtils;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializable;
import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MobAffix implements ISerializedRegistryEntry<MobAffix>, ISerializable<MobAffix>, IAutoLocName, IApplyableStats {

    List<StatModifier> stats = new ArrayList<>();
    String id = "";
    int weight = 1000;
    public String icon = "";
    public Formatting format;
    transient String locName;

    public MobAffix(String id, String locName, Formatting format) {
        this.id = id;
        this.locName = locName;
        this.format = format;
    }

    public MobAffix setMods(StatModifier... mods) {
        this.stats = Arrays.asList(mods);
        return this;
    }

    public MobAffix icon(String icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = getDefaultJson();

        json.addProperty("format", format.name());

        json.addProperty("icon", icon);

        if (stats != null) {
            JsonUtils.addStats(stats, json, "stats");
        }

        return json;
    }

    @Override
    public MobAffix fromJson(JsonObject json) {

        MobAffix affix = new MobAffix(
            getGUIDFromJson(json),
            "",
            Formatting.valueOf(json.get("format")
                .getAsString()));

        try {
            affix.stats = JsonUtils.getStats(json, "stats");
            affix.icon = json.get("icon")
                .getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return affix;
    }

    public MobAffix setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.MOB_AFFIX;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".mob_affix." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locName;
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<ExactStatData> stats = new ArrayList<>();
        try {
            this.stats.forEach(x -> stats.add(x.ToExactStat(100, Load.Unit(en)
                .getLevel())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(new SimpleStatCtx(StatContext.StatCtxType.MOB_AFFIX, stats));
    }

}
