package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.tooltips.StatTooltipType;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.rework.StatModifierInfo;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.modify.IStatCtxModifier;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public abstract class Stat implements IGUID, IAutoLocName, IWeighted, IAutoLocDesc, JsonExileRegistry<BaseDatapackStat> {

    public static String VAL1 = "[VAL1]";
    static TextFormatting FORMAT = TextFormatting.GRAY;
    static TextFormatting NUMBER = TextFormatting.GREEN;

    public static String format(String str) {

        str = FORMAT + str;

        str = str.replace(VAL1, NUMBER + VAL1 + FORMAT);

        return str;
    }

    public Stat() {
    }

    public boolean show = true;

    // can't serialize
    public transient IStatEffect statEffect = null;
    public transient IStatCtxModifier statContextModifier;

    public float min = -1000;
    public float max = Integer.MAX_VALUE;
    public float base = 0;
    public boolean is_perc = false;
    public StatScaling scaling = StatScaling.SLOW;
    public boolean is_long = false;
    public String icon = "\u2741";
    public int order = 100;
    public String format = TextFormatting.AQUA.getName();
    public StatGroup group = StatGroup.Misc;

    public TextFormatting getFormat() {
        return TextFormatting.getByName(format);
    }

    public String getIconNameFormat() {
        return getIconNameFormat(locNameForLangFile());
    }

    public String getFormatAndIcon() {
        return getFormat() + icon;
    }

    public String getIconNameFormat(String str) {
        return this.getFormat() + this.icon + " " + str + TextFormatting.GRAY;
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

    public final StatScaling getScaling() {
        return scaling;
    }

    public final float scale(ModType mod, float stat, float lvl) {
        if (mod.isFlat()) {
            return getScaling().scale(stat, lvl);
        } else {
            return StatScaling.SLOW.scale(stat, lvl);
        }
    }

    public List<IFormattableTextComponent> getCutDescTooltip() {
        List<IFormattableTextComponent> list = new ArrayList<>();

        List<ITextComponent> cut = TooltipUtils.cutIfTooLong(locDesc());

        for (int i = 0; i < cut.size(); i++) {

            IFormattableTextComponent comp = new StringTextComponent("");
            if (i == 0) {
                comp.append(" [");
            }
            comp.append(cut.get(i));

            if (i == cut.size() - 1) {
                comp.append("]");
            }

            list.add(comp);

            comp.withStyle(TextFormatting.BLUE);

        }
        return list;
    }

    // this is used for alltraitmods, check if confused
    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT;
    }

    public static ResourceLocation DEFAULT_ICON = new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/default.png");

    public ResourceLocation getIconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/stat_icons/" + group.id + "/" + GUID() + ".png");
    }

    transient ResourceLocation cachedIcon = null;

    public ResourceLocation getIconForRendering() {
        if (cachedIcon == null) {
            ResourceLocation id = getIconLocation();
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
        return SlashRef.MODID + ".stat." + GUID();
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + GUID();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Stats;
    }

    public boolean IsPercent() {
        return is_perc;
    }

    public abstract Elements getElement();

    @OnlyIn(Dist.CLIENT)
    public List<ITextComponent> getTooltipList(StatModifierInfo info) {
        return StatTooltipType.NORMAL.impl.getTooltipList(null, info);
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
