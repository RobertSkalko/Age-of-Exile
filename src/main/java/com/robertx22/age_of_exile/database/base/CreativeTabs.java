package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.AlchemyPotions;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GemItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.IngredientItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.player_skills.items.alchemy.PotionType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class CreativeTabs {

    public static ItemGroup MyModTab = new ItemGroup("mmorpg_main_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SlashItems.GearItems.NECKLACES.get(VanillaMaterial.DIAMOND)
                .get());
        }
    };

    public static ItemGroup GearSouls = new ItemGroup("mmorpg_gear_soul_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.SOUL_LANTERN);
        }
    };
    public static ItemGroup Materials = new ItemGroup("mmorpg_mats") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(IngredientItems.BOLETE_MUSHROOM.get());
        }
    };
    public static ItemGroup GemRuneCurrency = new ItemGroup("mmorpg_gemrunes") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(GemItems.MAP.get(GemItem.GemType.GARNET)
                .get(GemItem.GemRank.PERFECT)
                .get());
        }
    };

    public static ItemGroup Professions = new ItemGroup("mmorpg_proffs_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(AlchemyPotions.POTIONS_MAP.get(ImmutablePair.of(SkillItemTier.TIER4, PotionType.HEALTH))
                .get());
        }
    };

}
