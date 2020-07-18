package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.database.base.CreativeTabs;
import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.mmorpg.ModRegistry;
import com.robertx22.mine_and_slash.uncommon.localization.CLOC;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.vanilla_mc.items.SimpleMatItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemCapacitor extends Item implements IShapedRecipe {

    public static HashMap<Integer, Item> ITEMS = new HashMap<Integer, Item>();

    public ItemCapacitor(int rarity) {

        super(new Settings().group(CreativeTabs.MyModTab));

        this.rarity = rarity;

    }

    int rarity;

    public List<Float> RepairValues = Arrays.asList(0.95F, 0.9F, 0.8F, 0.7F, 0.6F, 0.5F);

    public List<Float> bonusSalvageChance = Arrays.asList(1F, 2.5F, 5F, 10F, 15F, 25F);

    public List<Float> stationSpeedMulti = Arrays.asList(1F, 0.9F, 0.8F, 0.7F, 0.6F, 0.5F);

    public Float getSalvageBonusChance() {

        return bonusSalvageChance.get(rarity);

    }

    public Float GetFuelMultiplier() {

        return RepairValues.get(rarity);

    }

    public Float GetSpeedMultiplier() {

        return stationSpeedMulti.get(rarity);

    }

    @Override
    public ShapedRecipeJsonFactory getRecipe() {

        if (rarity == 0) {
            return shaped(ITEMS.get(rarity))
                .input('#', Items.IRON_INGOT)
                .input('t', ModRegistry.CURRENCIES.ORB_OF_TRANSMUTATION)
                .input('c', ModRegistry.MISC_ITEMS.MAGIC_ESSENCE)
                .pattern("#c#")
                .pattern("ctc")
                .pattern("#c#")
                .criterion("player_level", trigger());
        }
        if (rarity == 1) {
            return shaped(ITEMS.get(rarity))
                .input('#', SimpleMatItem.INFUSED_IRON)
                .input('t', ITEMS.get(rarity - 1))
                .input('c', ModRegistry.MISC_ITEMS.MAGIC_ESSENCE)
                .pattern("#c#")
                .pattern("ctc")
                .pattern("#c#")
                .criterion("player_level", trigger());
        }
        if (rarity == 2) {
            return shaped(ITEMS.get(rarity))
                .input('#', SimpleMatItem.GOLDEN_ORB)
                .input('t', ITEMS.get(rarity - 1))
                .input('c', ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE)
                .pattern("#c#")
                .pattern("ctc")
                .pattern("#c#")
                .criterion("player_level", trigger());
        }
        if (rarity == 3) {
            return shaped(ITEMS.get(rarity))
                .input('#', SimpleMatItem.CRYSTALLIZED_ESSENCE)
                .input('t', ITEMS.get(rarity - 1))
                .input('c', ModRegistry.MISC_ITEMS.RARE_MAGIC_ESSENCE)
                .pattern("#c#")
                .pattern("ctc")
                .pattern("#c#")
                .criterion("player_level", trigger());
        }
        if (rarity == 4) {
            return shaped(ITEMS.get(rarity))
                .input('#', SimpleMatItem.MYTHIC_ESSENCE)
                .input('t', ITEMS.get(rarity - 1))
                .input('c', ModRegistry.CURRENCIES.STONE_OF_HOPE)
                .pattern("#c#")
                .pattern("ctc")
                .pattern("#c#")
                .criterion("player_level", trigger());
        }

        return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip,
                              TooltipContext flagIn) {

        tooltip.add(CLOC.tooltip("capacitor"));

        tooltip.add(CLOC.tooltip("capacitor2")
            .append(": " + this.GetFuelMultiplier() + "x"));

        tooltip.add(Words.Bonus_Salvage_Chance.locName()
            .append(": " + this.getSalvageBonusChance() + "%"));

    }

}
