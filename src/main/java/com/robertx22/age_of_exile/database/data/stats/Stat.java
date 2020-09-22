package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryEntry;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocDesc;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IWeighted;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public abstract class Stat implements IGUID, IAutoLocName, IWeighted, IRarity, IAutoLocDesc, ISlashRegistryEntry {

    public Stat() {
    }

    public int maximumValue = Integer.MAX_VALUE;
    public float minimumValue = -1000;
    public int BaseFlat = 0;

    public boolean add$To$toTooltip = true;
    public boolean add$plusminus$toTooltip = true;
    public boolean uses_second_val = true;

    @Override
    public boolean isRegistryEntryValid() {
        return true;
    }

    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    public final boolean UsesSecondValue() {
        return uses_second_val;
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
        return SlashRegistry.GearRarities()
            .get(getRarityRank());
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

    public abstract boolean IsPercent();

    public abstract Elements getElement();

    @Environment(EnvType.CLIENT)
    public List<Text> getTooltipList(TooltipStatInfo info) {
        return info.tooltipInfo.statTooltipType.impl.getTooltipList(info);
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
            this.word = word;
        }

        public Words word;

    }

}
