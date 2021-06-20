package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.aoe_data.datapacks.bases.ISerializedRegistryEntry;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.IGUID;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class Stat implements IGUID, IAutoLocName, IWeighted, IAutoLocDesc, ISerializedRegistryEntry<BaseDatapackStat> {

    public static String VAL1 = "[VAL1]";
    public static String VAL2 = "[VAL2]";
    static Formatting FORMAT = Formatting.GRAY;
    static Formatting NUMBER = Formatting.GREEN;

    public static String format(String str) {

        str = FORMAT + str;

        str = str.replace(VAL1, NUMBER + VAL1 + FORMAT);
        str = str.replace(VAL2, NUMBER + VAL2 + FORMAT);

        return str;
    }

    public Stat() {
    }

    public boolean show = true;

    // can't serialize
    public transient IStatEffect statEffect = null;
    public transient IStatCtxModifier statContextModifier;
    public transient Function<BaseGearType, Boolean> isLocalTo = x -> false;

    public float min = -1000;
    public float max = Integer.MAX_VALUE;
    public float base = 0;
    public boolean is_perc = false;
    public boolean use_sec_val = false;
    public StatScaling scaling = StatScaling.SLOW;
    public boolean is_long = false;
    public String icon = "\u2741";
    public int order = 100;
    public String format = Formatting.AQUA.getName();
    public StatGroup group = StatGroup.Misc;

    public Formatting getFormat() {
        return Formatting.byName(format);
    }

    public String getIconNameFormat() {
        return getIconNameFormat(locNameForLangFile());
    }

    public String getFormatAndIcon() {
        return getFormat() + icon;
    }

    public String getIconNameFormat(String str) {
        return this.getFormat() + this.icon + " " + str + Formatting.GRAY;
    }

    @Override
    public boolean isFromDatapack() {
        return false;
    }

    @Override
    public boolean isRegistryEntryValid() {
        return true;
    }

    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    public final boolean UsesSecondValue() {
        return use_sec_val;
    }

    public final StatScaling getScaling() {
        return scaling;
    }

    public final float scale(float stat, int lvl) {
        return getScaling().scale(stat, lvl);
    }

    public List<MutableText> getCutDescTooltip() {
        List<MutableText> list = new ArrayList<>();

        List<MutableText> cut = TooltipUtils.cutIfTooLong(locDesc());

        for (int i = 0; i < cut.size(); i++) {

            MutableText comp = new LiteralText("");
            if (i == 0) {
                comp.append(" [");
            }
            comp.append(cut.get(i));

            if (i == cut.size() - 1) {
                comp.append("]");
            }

            list.add(comp);

            comp.formatted(Formatting.BLUE);

        }
        return list;
    }

    // this is used for alltraitmods, check if confused
    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.STAT;
    }

    public static Identifier DEFAULT_ICON = new Identifier(Ref.MODID, "textures/gui/stat_icons/default.png");

    public Identifier getIconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/stat_icons/" + group.id + "/" + GUID() + ".png");
    }

    transient Identifier cachedIcon = null;

    public Identifier getIconForRendering() {
        if (cachedIcon == null) {
            Identifier id = getIconLocation();
            if (ClientTextureUtils.textureExists(id)) {
                cachedIcon = id;
            } else {
                cachedIcon = Stat.DEFAULT_ICON;
            }
        }
        return cachedIcon;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Stats;
    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".stat." + GUID();
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + GUID();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Stats;
    }

    public boolean IsPercent() {
        return is_perc;
    }

    public abstract Elements getElement();

    @Environment(EnvType.CLIENT)
    public List<Text> getTooltipList(TooltipStatWithContext info) {
        return info.statinfo.tooltipInfo.statTooltipType.impl.getTooltipList(info);
    }

    public enum StatGroup {
        MAIN("main"),
        WEAPON("weapon"),
        CORE("core"),
        ELEMENTAL("elemental"),
        RESTORATION("restoration"),
        Misc("misc");

        public String id;

        StatGroup(String id) {
            this.id = id;
        }
    }

}
