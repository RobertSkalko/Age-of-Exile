package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.alchemy.PotionType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.ImmutablePair;

public class CreativeTabs {

    public static ItemGroup MyModTab = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "main_group")
        ,
        () -> new ItemStack(ModRegistry.GEAR_ITEMS.NECKLACES.get(VanillaMaterial.DIAMOND)));

    public static ItemGroup GearSouls = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "gear_soul_group")
        ,
        () -> new ItemStack(Items.SOUL_LANTERN));

    public static ItemGroup Gems = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "gem_group")
        ,
        () -> new ItemStack(ModRegistry.GEMS.MAP.get(GemItem.GemType.TOPAZ)
            .get(GemItem.GemRank.PERFECT)));

    public static ItemGroup Runes = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "runes_group")
        ,
        () -> new ItemStack(ModRegistry.RUNES.ALL.get(0)));

    public static ItemGroup Professions = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "proffs_group")
        ,
        () -> new ItemStack(ModRegistry.ALCHEMY.POTIONS_MAP.get(ImmutablePair.of(SkillItemTier.TIER4, PotionType.HEALTH))));

}
