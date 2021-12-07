package com.robertx22.age_of_exile.vanilla_mc.items.crates.rarity_gear;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class RarityGearCrateItem extends Item implements IAutoModel {

    public RarityGearCrateItem() {
        super(new Properties().stacksTo(16)
            .tab(CreativeTabs.MyModTab));

    }

    public static ItemStack ofRarity(String rar) {

        RarityGearCrateData data = new RarityGearCrateData();
        data.rar = rar;

        ItemStack stack = new ItemStack(SlashItems.RARITY_GEAR_CRATE.get());

        StackSaving.RARITY_GEAR_CRATE.saveTo(stack, data);

        return stack;

    }

    public static RarityGearCrateData getData(ItemStack stack) {
        return StackSaving.RARITY_GEAR_CRATE.loadFrom(stack);
    }

    @Override
    public ITextComponent getName(ItemStack stack) {

        try {
            RarityGearCrateData data = getData(stack);

            IFormattableTextComponent comp = data.getRarity()
                .locName()
                .append(" ")
                .append(new TranslationTextComponent(this.getDescriptionId()))
                .withStyle(data.getRarity()
                    .textFormatting(), TextFormatting.BOLD);

            return comp;

        } catch (Exception e) {
        }
        return new TranslationTextComponent(this.getDescriptionId()).withStyle(TextFormatting.LIGHT_PURPLE);
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide) {
            ClientOnly.totemAnimWithItem(stack);
        }

        if (!world.isClientSide) {
            try {

                List<ItemStack> drops = new ArrayList<>();

                RarityGearCrateData data = getData(stack);

                int tier = data.tier;

                if (data.set_to_player_lvl) {
                    tier = LevelUtils.levelToTier(Load.Unit(player)
                        .getLevel());
                }

                GearBlueprint blueprint = new GearBlueprint((LevelUtils.tierToLevel(tier)));
                blueprint.rarity.set(data.getRarity());
                blueprint.level.set(LevelUtils.tierToLevel(tier));
                blueprint.uniquePart.get(); // generate it first
                drops.add(blueprint.createStack());

                SoundUtils.playSound(player, SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);

                drops.forEach(x -> PlayerUtils.giveItem(x, player));

                stack.shrink(1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    private void spawnEffects(World world, LivingEntity en) {
        FireworkRocketEntity firework = new FireworkRocketEntity(world, en.getX(), en.getY(), en.getZ(), ItemStack.EMPTY);
        firework.setPosRaw(en.getX(), en.getY(), en.getZ());
        WorldUtils.spawnEntity(world, firework);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        try {
            tooltip.add(Words.ClickToOpen.locName()
                .withStyle(TextFormatting.RED));

        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

}


