package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.generators.GemLootGen;
import com.robertx22.age_of_exile.loot.generators.RuneLootGen;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
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
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class LootCrateItem extends Item implements IAutoModel {
    LootCrateType type;

    public LootCrateItem(LootCrateType type) {
        super(new Settings().maxCount(3)
            .group(CreativeTabs.MyModTab));
        this.type = type;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new TranslatableText(this.getTranslationKey()).formatted(Formatting.LIGHT_PURPLE);
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    public enum LootCrateType {

        RUNE(new MinMax(2, 5)) {
            @Override
            public boolean canUse(PlayerEntity en) {
                return new RuneLootGen(LootInfo.ofPlayer(en)).condition();
            }

            @Override
            public ItemStack generate(PlayerEntity en) {
                return new RuneLootGen(LootInfo.ofPlayer(en)).generateOne();
            }
        },
        GEM(new MinMax(3, 6)) {
            @Override
            public ItemStack generate(PlayerEntity en) {
                return new GemLootGen(LootInfo.ofPlayer(en)).generateOne();
            }
        },
        COMMON(new MinMax(2, 6)) {
            @Override
            public ItemStack generate(PlayerEntity en) {
                int lvl = Load.Unit(en)
                    .getLevel();
                GearBlueprint blueprint = new GearBlueprint(lvl);
                blueprint.rarity.set(Database.GearRarities()
                    .get(IRarity.COMMON_ID));
                return blueprint.createStack();
            }
        },
        MAGIC(new MinMax(2, 5)) {
            @Override
            public ItemStack generate(PlayerEntity en) {
                int lvl = Load.Unit(en)
                    .getLevel();
                GearBlueprint blueprint = new GearBlueprint(lvl);
                blueprint.rarity.set(Database.GearRarities()
                    .get(IRarity.MAGICAL_ID));
                return blueprint.createStack();
            }
        },
        RARE(new MinMax(2, 4)) {
            @Override
            public ItemStack generate(PlayerEntity en) {
                int lvl = Load.Unit(en)
                    .getLevel();
                GearBlueprint blueprint = new GearBlueprint(lvl);
                blueprint.rarity.set(Database.GearRarities()
                    .get(IRarity.RARE_ID));
                return blueprint.createStack();
            }
        },
        UNIQUE(new MinMax(1, 2)) {
            @Override
            public ItemStack generate(PlayerEntity en) {
                int lvl = Load.Unit(en)
                    .getLevel();
                GearBlueprint blueprint = new GearBlueprint(lvl);
                // use only gear types that have uniques
                blueprint.gearItemSlot.set(Database.GearTypes()
                    .getFilterWrapped(x -> Database.UniqueGears()
                        .getList()
                        .stream()
                        .anyMatch(e -> e.getBaseGearType()
                            .GUID()
                            .equals(x.GUID())))
                    .random());
                blueprint.rarity.set(Database.GearRarities()
                    .get(IRarity.UNIQUE_ID));
                return blueprint.createStack();
            }
        },
        RELIC(new MinMax(2, 3)) {
            @Override
            public ItemStack generate(PlayerEntity en) {
                int lvl = Load.Unit(en)
                    .getLevel();
                GearBlueprint blueprint = new GearBlueprint(lvl);
                blueprint.rarity.set(Database.GearRarities()
                    .get(IRarity.RELIC_ID));
                return blueprint.createStack();
            }
        };

        public abstract ItemStack generate(PlayerEntity en);

        public boolean canUse(PlayerEntity en) {
            return true;
        }

        public MinMax amount;

        LootCrateType(MinMax amount) {
            this.amount = amount;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        if (!world.isClient) {
            try {
                if (this.type.canUse(player)) {
                    ItemStack stack = player.getStackInHand(hand);
                    stack.decrement(1);

                    int amount = type.amount.random();

                    spawnEffects(world, player);

                    for (int i = 0; i < amount; i++) {
                        player.dropItem(type.generate(player), false);
                    }
                } else {
                    player.sendMessage(new LiteralText("Level too low to use."), false);
                }
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
        tooltip.add(Words.ClickToOpen.locName()
            .formatted(Formatting.RED));
    }

}

