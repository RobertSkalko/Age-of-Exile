package com.robertx22.mine_and_slash.database.data.stats;

import com.robertx22.exiled_lib.registry.ISlashRegistryEntry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.mine_and_slash.uncommon.interfaces.IWeighted;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Styles;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Stat implements IGUID, IAutoLocName, IWeighted, IRarity, IAutoLocDesc, ISlashRegistryEntry {

    public Stat() {
    }

    public boolean isInt = false;

    public boolean add$To$toTooltip = true;
    public boolean add$plusminus$toTooltip = true;

    @Override
    public boolean isRegistryEntryValid() {
        return true;
    }

    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    public boolean UsesSecondValue() {
        return false;
    }

    public Formatting getIconFormat() {
        if (this.getElement() != null) {
            return this.getElement().format;
        } else {
            return Elements.Physical.format;
        }
    }

    public StatScaling getScaling() {
        return StatScaling.SLOW_SCALING;
    }

    public final float scale(float stat, int lvl) {
        return getScaling().scale(stat, lvl);
    }

    public String getIcon() {
        if (this.getElement() != null) {
            return this.getElement().icon;
        } else {
            return Elements.Physical.icon;
        }
    }

    public String getFormattedIcon() {
        return getIconFormat() + getIcon();
    }

    public List<MutableText> getCutDescTooltip() {
        List<MutableText> list = new ArrayList<>();

        List<MutableText> cut = TooltipUtils.cutIfTooLong(locDesc());

        for (int i = 0; i < cut.size(); i++) {

            MutableText comp = Styles.BLUECOMP();
            if (i == 0) {
                comp.append(" [");
            }
            comp.append(cut.get(i));

            if (i == cut.size() - 1) {
                comp.append("]");
            }

            list.add(comp);

        }
        return list;
    }

    public enum StatClassType {
        NORMAL, CORE, TRAIT
    }

    public StatClassType getStatType() {
        return StatClassType.NORMAL;
    }

    public boolean isTrait() {
        return getStatType().equals(StatClassType.TRAIT);
    }

    @Override
    public int getRarityRank() {
        return IRarity.Magical;
    }

    // this is used for alltraitmods, check if confused
    @Override
    public int Weight() {
        return this.getRarity()
            .Weight();
    }

    @Override
    public Rarity getRarity() {
        return Rarities.Gears.get(getRarityRank());
    }

    @Override
    public SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.STAT;
    }

    public String getIconPath() {
        return "";
    }

    public Identifier getIconLocation() {
        if (getIconPath().isEmpty()) {
            return new Identifier(Ref.MODID, "textures/gui/stat_icons/default.png");
        } else {
            return new Identifier(Ref.MODID, "textures/gui/stat_icons/" + getIconPath() + ".png");
        }

    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Stats;

    }

    @Override
    public String locNameLangFileGUID() {
        return Ref.MODID + ".stat." + formattedGUID();
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat_desc." + formattedGUID();
    }

    public boolean isLocal() {
        return this instanceof ILocalStat;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Stats;
    }

    public int maximumValue = Integer.MAX_VALUE;

    public float minimumValue = -1000;

    public abstract boolean IsPercent();

    public abstract Elements getElement();

    public int BaseFlat = 0;

    public String printValue(float val) {

        DecimalFormat format = new DecimalFormat();

        if (val < 5) {
            format.setMaximumFractionDigits(1);

            return format.format(val);

        } else {

            int intval = (int) val;
            return intval + "";

        }

    }

    @Environment(EnvType.CLIENT)
    public List<Text> getTooltipList(TooltipStatInfo info) {
        return info.tooltipInfo.statTooltipType.impl.getTooltipList(info);
    }

    public boolean IsShownOnStatGui() {
        return true;
    }

    public StatGroup statGroup() {
        return StatGroup.Misc;
    }

    public enum StatGroup {
        Main(Words.Main, 0),
        Misc(Words.Misc, 8),
        CoreStat(Words.Core_Stat, 5),
        SpellDamage(Words.Spell_Damage, 3),
        EleAttackDamage(Words.Elemental_Attack_Damage, 2),
        Defenses(Words.Defenses, 4),
        Penetration(Words.Penetration, 6),
        Damage(Words.Damage, 1),
        Regeneration(Words.Regeneration, 7);

        StatGroup(Words word, int place) {
            this.place = place;
            this.word = word;
        }

        public Words word;

        public final int width = 18;
        public final int height = 18;

        public int place = 0;

        public final int Y = 8;

        public int X() {
            return 25 + width * place;
        }
    }

}
