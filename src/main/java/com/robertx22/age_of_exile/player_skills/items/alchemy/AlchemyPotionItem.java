package com.robertx22.age_of_exile.player_skills.items.alchemy;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.StatusEffectData;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.registers.common.PotionRegister;
import com.robertx22.age_of_exile.player_skills.items.TieredItem;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.player_skills.recipe_types.StationShapelessFactory;
import com.robertx22.age_of_exile.player_skills.recipe_types.base.IStationRecipe;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class AlchemyPotionItem extends TieredItem implements IStationRecipe {

    PotionType type;

    public AlchemyPotionItem(PotionType type, SkillItemTier tier) {
        super(tier, new Settings().group(CreativeTabs.Professions)
            .maxCount(16));
        this.type = type;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity player) {

        stack.decrement(1);
        if (player instanceof PlayerEntity) {
            PlayerEntity p = (PlayerEntity) player;

            for (AlchemyPotionItem x : ModRegistry.ALCHEMY.POTIONS_MAP.values()) {
                p.getItemCooldownManager()
                    .set(x, 20 * 20);
            }

            PlayerUtils.giveItem(new ItemStack(Items.GLASS_BOTTLE), (PlayerEntity) player);
        }
        if (!world.isClient) {

            EntityCap.UnitData unitdata = Load.Unit(player);

            int restore = (int) (tier.percent_healed / 100F * unitdata.getResources()
                .getMax(player, this.type.resource));

            RestoreResourceEvent event = EventBuilder.ofRestore(player, player, type.resource, RestoreType.potion, restore)
                .build();
            event.Activate();

            getFoodEffect().apply(player); // because it's only applied when eating food normally

            SoundUtils.playSound(player, SoundEvents.ENTITY_GENERIC_DRINK, 1, 1);
        }

        return stack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand handIn) {
        ItemStack itemStack = player.getStackInHand(handIn);
        player.setCurrentHand(handIn);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new LiteralText("Restores " + (int) tier.percent_healed + "% " + type.word).formatted(Formatting.LIGHT_PURPLE));
    }

    @Override
    public String locNameForLangFile() {
        return tier.word + " " + type.word + " Potion";
    }

    @Override
    public String GUID() {
        return "alchemy/potion/" + type.id + "/" + tier.tier;
    }

    public FoodEffect getFoodEffect() {

        FoodEffect eff = new FoodEffect();
        try {
            Identifier effect = null;

            if (this.type == PotionType.HEALTH) {
                effect = PotionRegister.FOOD_HP;
            }

            if (this.type == PotionType.MANA) {
                effect = PotionRegister.FOOD_MANA;
            }

            if (effect == null) {
                return null;
            }

            eff.effects_given.add(new StatusEffectData(effect, 6, (int) (this.tier.statMulti * 20)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return eff;

    }

    @Override
    public StationShapelessFactory getStationRecipe() {
        StationShapelessFactory fac = StationShapelessFactory.create(ModRegistry.RECIPE_SER.ALCHEMY, this, 3);
        fac.input(type.craftItem.get());
        fac.input(Items.GLASS_BOTTLE);
        fac.input(ModRegistry.TIERED.FARMING_PRODUCE.get(tier));
        return fac.criterion("player_level", trigger());
    }
}
