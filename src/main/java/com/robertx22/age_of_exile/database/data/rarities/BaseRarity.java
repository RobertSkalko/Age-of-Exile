package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.util.text.TextFormatting;

public abstract class BaseRarity implements Rarity {

    public BaseRarity(RarityType rarity_type) {
        this.rarity_type = rarity_type;
    }

    public enum RarityType {
        GEAR("gear"), MOB("mob"), SKILL_GEM("skill_gem");

        public String id;

        RarityType(String id) {
            this.id = id;
        }
    }

    public RarityType rarity_type;
    public int weight;
    public String text_format;
    public String higher_rar = "";

    public transient String loc_name;
    public String guid;

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".rarity." + rarity_type.id + "." + GUID();
    }

    @Override
    public String GUID() {
        return guid;
    }

    @Override
    public int Weight() {
        return weight;
    }

    @Override
    public TextFormatting textFormatting() {
        try {
            return TextFormatting.valueOf(text_format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TextFormatting.GRAY;
    }

    @Override
    public String locNameForLangFile() {
        return loc_name;
    }

    public void setCommonFields() {
        this.guid = IRarity.COMMON_ID;
        this.loc_name = "Common";
        this.text_format = TextFormatting.GRAY.name();
        onSetFields();
    }

    public void setUncommonFields() {
        this.guid = IRarity.UNCOMMON;
        this.loc_name = "Uncommon";
        this.text_format = TextFormatting.GREEN.name();

        onSetFields();
    }

    public void setRareFields() {
        this.guid = IRarity.RARE_ID;
        this.loc_name = "Rare";
        this.text_format = TextFormatting.AQUA.name();

        onSetFields();
    }

    public void setEpicFields() {
        this.guid = IRarity.EPIC_ID;
        this.loc_name = "Epic";
        this.text_format = TextFormatting.LIGHT_PURPLE.name();

        onSetFields();
    }

    public void setBossFields() {
        this.guid = IRarity.BOSS_ID;
        this.loc_name = "Boss";
        this.text_format = TextFormatting.RED.name();

        onSetFields();
    }

    public void setUniqueFields() {
        this.guid = IRarity.UNIQUE_ID;
        this.loc_name = "Unique";
        this.text_format = TextFormatting.RED.name();

        onSetFields();
    }

    public void setRunewordFields() {
        this.guid = IRarity.RUNEWORD_ID;
        this.loc_name = "Rune Word";
        this.text_format = TextFormatting.YELLOW.name();
        onSetFields();
    }

    private void onSetFields() {

    }

}

