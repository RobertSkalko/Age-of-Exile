package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiType;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CraftedConsumableItems;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.registry.IGUID;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PlayerSkillEnum implements IGUID {

    ALCHEMY("alchemy", WikiType.ALCHEMY_EXP, Words.Alchemy, Words.AlchemyDesc, TextFormatting.LIGHT_PURPLE, 3) {
        @Override
        public Item getCraftResultItem() {
            return RandomUtils.randomFromList(CraftedConsumableItems.POTIONS)
                .get();
        }
    },
    INSCRIBING("inscribing", WikiType.SCRIBE_EXP, Words.Inscribing, Words.InscribingDesc, TextFormatting.AQUA, 2) {
        @Override
        public Item getCraftResultItem() {
            return RandomUtils.randomFromList(CraftedConsumableItems.SCROLLS)
                .get();
        }
    },
    COOKING("cooking", WikiType.COOKING_EXP, Words.Cooking, Words.CookingDesc, TextFormatting.RED, 1.5F) {
        @Override
        public Item getCraftResultItem() {
            return RandomUtils.randomFromList(CraftedConsumableItems.FOODS)
                .get();
        }
    },

    JEWEL_CRAFTING("jewel_craft", WikiType.COOKING_EXP, Words.JewelCrafting, Words.CookingDesc, TextFormatting.GREEN, 1) {
        @Override
        public boolean isGearCraftingProf() {
            return true;
        }

        @Override
        public boolean gearMatchesProfession(Item item) {
            GearSlot slot = GearSlot.getSlotOf(item);
            return slot.fam == SlotFamily.Jewelry;
        }
    },
    ARMOR_CRAFTING("armor_craft", WikiType.COOKING_EXP, Words.ArmorCrafting, Words.CookingDesc, TextFormatting.BLUE, 1) {
        @Override
        public boolean isGearCraftingProf() {
            return true;
        }

        @Override
        public boolean gearMatchesProfession(Item item) {
            GearSlot slot = GearSlot.getSlotOf(item);
            return slot.fam == SlotFamily.Armor;
        }
    },
    WEAPON_CRAFTING("wep_craft", WikiType.COOKING_EXP, Words.WeaponCrafting, Words.CookingDesc, TextFormatting.RED, 1) {
        @Override
        public boolean isGearCraftingProf() {
            return true;
        }

        @Override
        public boolean gearMatchesProfession(Item item) {
            GearSlot slot = GearSlot.getSlotOf(item);
            return slot.fam == SlotFamily.Weapon;
        }
    },

    ALL("all", null, Words.Skill, Words.Skill, TextFormatting.GREEN, 1),
    NONE("none", null, Words.Skill, Words.Skill, TextFormatting.BLACK, 1);

    public String id;
    public Words word;
    public Words desc;
    public TextFormatting format;

    public float craftedStatMulti = 1;

    public WikiType wiki;

    public boolean isGearCraftingProf() {
        return false;
    }

    public boolean gearMatchesProfession(Item item) {
        return false;
    }

    PlayerSkillEnum(String id, WikiType wiki, Words word, Words desc, TextFormatting format, float craftedStatMulti) {
        this.id = id;
        this.word = word;
        this.craftedStatMulti = craftedStatMulti;
        this.format = format;
        this.desc = desc;
        this.wiki = wiki;
    }

    public Item getCraftResultItem() {
        return null;
    }

    public static List<PlayerSkillEnum> getAll() {
        return Arrays.stream(PlayerSkillEnum.values())
            .filter(x -> x != NONE && x != ALL)
            .collect(Collectors.toList());

    }

    @Override
    public String GUID() {
        return id;
    }
}
