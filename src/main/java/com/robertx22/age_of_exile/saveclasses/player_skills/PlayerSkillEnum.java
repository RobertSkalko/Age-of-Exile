package com.robertx22.age_of_exile.saveclasses.player_skills;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.gui.screens.wiki.WikiType;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.text.TextFormatting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum PlayerSkillEnum implements IGUID {

    ALCHEMY("alchemy", null, Words.Alchemy, Words.AlchemyDesc, TextFormatting.LIGHT_PURPLE, 3) {
        @Override
        public Item getToolCraftItem() {
            return Items.WHEAT_SEEDS;
        }

    },
    INSCRIBING("inscribing", null, Words.Inscribing, Words.InscribingDesc, TextFormatting.AQUA, 2) {
        @Override
        public Item getToolCraftItem() {
            return Items.PAPER;
        }
    },
    COOKING("cooking", null, Words.Cooking, Words.CookingDesc, TextFormatting.RED, 1.5F) {
        @Override
        public Item getToolCraftItem() {
            return Items.BREAD;
        }
    },

    JEWEL_CRAFTING("jewel_craft", null, Words.JewelCrafting, Words.JewelCraftingDesc, TextFormatting.GREEN, 1) {
        @Override
        public boolean isGearCraftingProf() {
            return true;
        }

        @Override
        public boolean gearMatchesProfession(Item item) {
            GearSlot slot = GearSlot.getSlotOf(item);
            if (slot == null) {
                return false;
            }
            return slot.fam == SlotFamily.Jewelry;
        }

        @Override
        public Item getToolCraftItem() {
            return SlashItems.GearItems.RINGS.get(VanillaMaterial.IRON)
                .get();
        }
    },
    ARMOR_CRAFTING("armor_craft", null, Words.ArmorCrafting, Words.ArmorCraftingDesc, TextFormatting.BLUE, 1) {
        @Override
        public boolean isGearCraftingProf() {
            return true;
        }

        @Override
        public boolean gearMatchesProfession(Item item) {
            GearSlot slot = GearSlot.getSlotOf(item);
            if (slot == null) {
                return false;
            }
            return slot.fam == SlotFamily.Armor;
        }

        @Override
        public Item getToolCraftItem() {
            return Items.IRON_CHESTPLATE;
        }
    },
    WEAPON_CRAFTING("wep_craft", null, Words.WeaponCrafting, Words.WeaponCraftingDesc, TextFormatting.RED, 1) {
        @Override
        public boolean isGearCraftingProf() {
            return true;
        }

        @Override
        public boolean gearMatchesProfession(Item item) {

            GearSlot slot = GearSlot.getSlotOf(item);
            if (slot == null) {
                return false;
            }
            return slot.fam == SlotFamily.Weapon;
        }

        @Override
        public Item getToolCraftItem() {
            return Items.IRON_SWORD;
        }
    },

    ALL("all", null, Words.Skill, Words.Skill, TextFormatting.GREEN, 1) {
        @Override
        public Item getToolCraftItem() {
            return Items.AIR;
        }
    },

    NONE("none", null, Words.Skill, Words.Skill, TextFormatting.BLACK, 1) {
        @Override
        public Item getToolCraftItem() {
            return Items.AIR;
        }
    };

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

    public abstract Item getToolCraftItem();

    PlayerSkillEnum(String id, WikiType wiki, Words word, Words desc, TextFormatting format, float craftedStatMulti) {
        this.id = id;
        this.word = word;
        this.craftedStatMulti = craftedStatMulti;
        this.format = format;
        this.desc = desc;
        this.wiki = wiki;
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
