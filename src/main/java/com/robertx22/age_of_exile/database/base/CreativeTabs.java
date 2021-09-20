package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.player_skills.items.alchemy.PotionType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class CreativeTabs {

    public static ItemGroup MyModTab = new ItemGroup("mmorpg:main_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModRegistry.GEAR_ITEMS.NECKLACES.get(VanillaMaterial.DIAMOND));
        }
    };

    public static ItemGroup GearSouls = new ItemGroup("mmorpg:gear_soul_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.SOUL_LANTERN);
        }
    };

    public static ItemGroup GemRuneCurrency = new ItemGroup("mmorpg:gemrunecurrency_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModRegistry.GEMS.MAP.get(GemItem.GemType.GARNET)
                .get(GemItem.GemRank.PERFECT));
        }
    };

    public static ItemGroup Professions = new ItemGroup("mmorpg:proffs_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModRegistry.ALCHEMY.POTIONS_MAP.get(ImmutablePair.of(SkillItemTier.TIER4, PotionType.HEALTH)));
        }
    };

}
