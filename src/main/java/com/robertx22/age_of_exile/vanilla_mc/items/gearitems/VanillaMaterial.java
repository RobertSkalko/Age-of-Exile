package com.robertx22.age_of_exile.vanilla_mc.items.gearitems;

import net.minecraft.item.*;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;

public enum VanillaMaterial {

    WOOD("wood", new ItemOrTag(ItemTags.PLANKS), ArmorMaterials.LEATHER, ToolMaterials.WOOD),
    IRON("iron", new ItemOrTag(Items.IRON_INGOT), ArmorMaterials.IRON, ToolMaterials.IRON),
    GOLD("gold", new ItemOrTag(Items.GOLD_INGOT), ArmorMaterials.GOLD, ToolMaterials.GOLD),
    DIAMOND("diamond", new ItemOrTag(Items.DIAMOND), ArmorMaterials.DIAMOND, ToolMaterials.DIAMOND);

    public String id;
    public ItemOrTag mat;
    public ArmorMaterial armormat;
    public ToolMaterial toolmat;

    VanillaMaterial(String id, ItemOrTag mat, ArmorMaterial armormat, ToolMaterial toolmat) {
        this.id = id;
        this.mat = mat;
        this.armormat = armormat;
        this.toolmat = toolmat;
    }

    public static class ItemOrTag {
        public Item item;
        public Tag.Identified<Item> tag;

        public ItemOrTag(Item item) {
            this.item = item;
        }

        public ItemOrTag(Tag.Identified<Item> tag) {
            this.tag = tag;
        }
    }
}
