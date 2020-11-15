package com.robertx22.age_of_exile.database.base;

import com.robertx22.age_of_exile.database.registrators.LevelRanges;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.player_skills.items.alchemy.PotionType;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodExileEffect;
import com.robertx22.age_of_exile.player_skills.items.foods.FoodType;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.GemItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Triple;

public class CreativeTabs {

    public static ItemGroup MyModTab = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "main_group")
        ,
        () -> new ItemStack(ModRegistry.GEAR_ITEMS.WANDS.get(LevelRanges.ENDGAME)));

    public static ItemGroup Gems = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "gem_group")
        ,
        () -> new ItemStack(ModRegistry.GEMS.MAP.get(GemItem.GemType.TOPAZ)
            .get(GemItem.GemRank.PERFECT)));

    public static ItemGroup Runes = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "runes_group")
        ,
        () -> new ItemStack(ModRegistry.RUNES.ALL.get(0)));

    public static ItemGroup Runewords = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "runeword_group")
        ,
        () -> new ItemStack(ModRegistry.MISC_ITEMS.RUNEWORD));

    public static ItemGroup Foods = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "food_group")
        ,
        () -> new ItemStack(ModRegistry.FOOD_ITEMS.MAP.get(Triple.of(FoodType.BEER, FoodExileEffect.EffectColor.RED, SkillItemTier.DIVINE))));

    public static ItemGroup Alchemy = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "alchemy_group")
        ,
        () -> new ItemStack(ModRegistry.ALCHEMY.POTIONS_MAP.get(ImmutablePair.of(SkillItemTier.DIVINE, PotionType.HEALTH))));

    public static ItemGroup Inscribing = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "inscribing_group")
        ,
        () -> new ItemStack(ModRegistry.INSCRIBING.ANTI_FIRE));

    public static ItemGroup Tinkering = FabricItemGroupBuilder.build(
        new Identifier(Ref.MODID, "tinkering_group")
        ,
        () -> new ItemStack(ModRegistry.TINKERING.MAT_BACKPACKS_TIER_MAP.get(SkillItemTier.DIVINE)));

}
