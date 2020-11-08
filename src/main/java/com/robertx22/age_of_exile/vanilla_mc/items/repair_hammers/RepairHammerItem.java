package com.robertx22.age_of_exile.vanilla_mc.items.repair_hammers;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.event_hooks.my_events.CollectGearEvent;
import com.robertx22.age_of_exile.saveclasses.unit.GearData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.data.server.recipe.ShapedRecipeJsonFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public abstract class RepairHammerItem extends Item implements IAutoLocName, IAutoModel, IShapedRecipe {

    int totalRepair;

    public RepairHammerItem(int repairAmount) {
        super(new Settings().group(CreativeTabs.MyModTab)
            .maxCount(1));
        this.totalRepair = repairAmount;
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return Registry.ITEM.getId(this)
            .toString();
    }

    @Override
    public String GUID() {
        return "";
    }

    public abstract Tag.Identified<Item> getGemTag();

    @Override
    public ShapedRecipeJsonFactory getRecipe() {
        return shaped(this)
            .input('g', getGemTag())
            .input('s', Items.STICK)
            .pattern("ggg")
            .pattern(" s ")
            .pattern(" s ")
            .criterion("player_level", trigger());
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        tooltip.add(new LiteralText("Use to repair all your gear."));
        tooltip.add(new LiteralText("Total Repair Value: " + this.totalRepair));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient) {
            try {

                List<GearData> stacks = CollectGearEvent.getAllGear(null, player, Load.Unit(player));

                int totalRepairValueLeft = this.totalRepair;

                int toRepairEachTimeMax = totalRepair / (stacks.size() * 3);

                while (totalRepairValueLeft > 0 && stacks.stream()
                    .anyMatch(x -> x.stack.isDamaged())) {

                    for (GearData x : stacks) {
                        if (x.stack.isDamaged()) {

                            int damage = x.stack.getDamage();

                            int toRepStack = MathHelper.clamp(damage, 0, toRepairEachTimeMax);

                            x.stack.setDamage(x.stack.getDamage() - toRepStack);

                            totalRepairValueLeft -= toRepStack;
                        }
                    }

                }

                player.getStackInHand(hand)
                    .decrement(1);

                SoundUtils.playSound(world, player.getBlockPos(), SoundEvents.BLOCK_ANVIL_USE, 0.8f, 1f);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }

}
