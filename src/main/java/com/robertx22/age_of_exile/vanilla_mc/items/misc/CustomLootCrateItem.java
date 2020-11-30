package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.LootType;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CustomLootCrateItem extends Item implements IAutoModel {

    public CustomLootCrateItem() {
        super(new Settings().maxCount(3)
            .group(CreativeTabs.MyModTab));

    }

    @Override
    public Text getName(ItemStack stack) {

        try {
            CrateInfo info = new CrateInfo(stack, null);

            MutableText size = null;

            if (info.amount < 3) {
                size = Words.Small.locName();
            } else {
                size = Words.Big.locName();
            }

            return size.append(" ")
                .append(info.lootType.word.locName())
                .append(" ")
                .append(new TranslatableText(this.getTranslationKey()))
                .formatted(Formatting.LIGHT_PURPLE);

        } catch (Exception e) {
            return new TranslatableText(this.getTranslationKey()).formatted(Formatting.LIGHT_PURPLE);
        }
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    static class CrateInfo {

        public CrateInfo(ItemStack stack, PlayerEntity player) {

            if (stack.getTag()
                .contains("amount")) {
                this.amount = stack.getTag()
                    .getInt("amount");
            } else {
                this.amount = 1;
            }

            this.lootType = LootType.of(stack.getTag()
                .getString("loot_type"));

            String rar = stack.getTag()
                .getString("rarity");

            if (stack.getTag()
                .contains("level")) {
                this.level = stack.getTag()
                    .getInt("level");
            } else {
                if (player != null) {
                    this.level = Load.Unit(player)
                        .getLevel();
                }
            }
            if (rar != null) {
                if (Database.GearRarities()
                    .isRegistered(rar)) {
                    this.gearRarity = Database.GearRarities()
                        .get(rar);
                }
            }

        }

        public Integer level = 1;
        public LootType lootType;
        public int amount;

        public GearRarity gearRarity;

        public List<MutableText> getTooltip() {

            List<MutableText> list = new ArrayList<>();

            if (lootType == LootType.Gear) {
                list.add(new LiteralText("Drops " + amount + " ").append(gearRarity.locName())
                    .append(" gears."));
            }

            return list;

        }

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient) {
            try {

                List<ItemStack> drops = new ArrayList<>();

                ItemStack stack = player.getStackInHand(hand);
                stack.decrement(1);

                CrateInfo info = new CrateInfo(stack, player);

                for (int i = 0; i < info.amount; i++) {

                    if (info.lootType == LootType.Gear) {
                        GearBlueprint blueprint = new GearBlueprint(info.level);
                        blueprint.rarity.set(info.gearRarity);
                        drops.add(blueprint.createStack());
                    }
                }

                spawnEffects(world, player);

                drops.forEach(x -> player.dropItem(x, false));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new TypedActionResult<ItemStack>(ActionResult.PASS, player.getStackInHand(hand));
    }

    private void spawnEffects(World world, LivingEntity en) {
        FireworkRocketEntity firework = new FireworkRocketEntity(world, en.getX(), en.getY(), en.getZ(), ItemStack.EMPTY);
        firework.setPos(en.getX(), en.getY(), en.getZ());
        WorldUtils.spawnEntity(world, firework);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World worldIn, List<Text> tooltip, TooltipContext flagIn) {
        try {
            tooltip.add(Words.ClickToOpen.locName()
                .formatted(Formatting.RED));

            tooltip.addAll(new CrateInfo(stack, ClientOnly.getPlayer()).getTooltip());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

}


