package com.robertx22.age_of_exile.vanilla_mc.items.gearitems;

import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;

public enum VanillaMaterial {

    WOOD("wood", new ItemOrTag(ItemTags.PLANKS), ArmorMaterial.LEATHER, ItemTier.WOOD),
    IRON("iron", new ItemOrTag(Items.IRON_INGOT), ArmorMaterial.IRON, ItemTier.IRON),
    GOLD("gold", new ItemOrTag(Items.GOLD_INGOT), ArmorMaterial.GOLD, ItemTier.GOLD),
    DIAMOND("diamond", new ItemOrTag(Items.DIAMOND), ArmorMaterial.DIAMOND, ItemTier.DIAMOND);

    public String id;
    public ItemOrTag mat;
    public ArmorMaterial armormat;
    public ItemTier toolmat;

    VanillaMaterial(String id, ItemOrTag mat, ArmorMaterial armormat, ItemTier toolmat) {
        this.id = id;
        this.mat = mat;
        this.armormat = armormat;
        this.toolmat = toolmat;
    }

    public static class ItemOrTag {
        public Item item;
        public ITag.INamedTag<Item> tag;

        public ItemOrTag(Item item) {
            this.item = item;
        }

        public ItemOrTag(Tag.INamedTag<Item> tag) {
            this.tag = tag;
        }
    }
}
