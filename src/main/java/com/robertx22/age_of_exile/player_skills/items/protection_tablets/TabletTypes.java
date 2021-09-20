package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.TabletItems;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.StringTextComponent;

import java.util.Arrays;
import java.util.List;

public enum TabletTypes {

    ANTI_FIRE("Anti Fire", "anti_fire") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.BLAZE_POWDER, TabletItems.BLANK_TABLET.get());
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {
            return healthIsBellowTresh(en) && source.isFire()
                && !en.hasEffect(Effects.FIRE_RESISTANCE);
        }

        @Override
        public void onActivate(PlayerEntity en) {
            en.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, DUR, 1));
        }
    },
    ANTI_POISON("Anti Poison", "anti_poison") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.SPIDER_EYE, TabletItems.BLANK_TABLET.get());
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {
            return healthIsBellowTresh(en) && en.hasEffect(Effects.POISON)
                && !en.hasEffect(SlashPotions.ANTI_POISON.get());
        }

        @Override
        public void onActivate(PlayerEntity en) {
            en.addEffect(new EffectInstance(SlashPotions.ANTI_POISON.get(), DUR, 1));
        }
    },
    ANTI_WITHER("Anti Wither", "anti_wither") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.BLACK_DYE, TabletItems.BLANK_TABLET.get());
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {
            return healthIsBellowTresh(en) && en.hasEffect(Effects.WITHER)
                && !en.hasEffect(SlashPotions.ANTI_WITHER.get());
        }

        @Override
        public void onActivate(PlayerEntity en) {
            en.addEffect(new EffectInstance(SlashPotions.ANTI_WITHER.get(), DUR, 1));
        }
    },
    ANTI_DEATH("Anti Death", "anti_death") {
        @Override
        public int cooldownTicks() {
            return 20 * 60 * 10;
        }

        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.ENCHANTED_GOLDEN_APPLE, TabletItems.RARE_BLANK_TABLET.get());
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {

            return HealthUtils.getCombinedHealthMulti(en) < 0.15F;
        }

        @Override
        public void onActivate(PlayerEntity en) {
            en.heal(Integer.MAX_VALUE);
        }
    },
    ANTI_GEAR_BREAK("Anti Gear Break", "anti_gear_break") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.IRON_INGOT, TabletItems.BLANK_TABLET.get());
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {

            ItemStack stack = PlayerUtils.lowestDurabilityWornGear(en);

            if (stack.isEmpty() || !stack.isDamageableItem()) {
                return false;
            }

            int left = stack.getMaxDamage() - stack.getDamageValue();
            return left < 20;
        }

        @Override
        public void onActivate(PlayerEntity en) {
            ItemStack stack = PlayerUtils.lowestDurabilityWornGear(en);
            if (stack.isEmpty()) {
                return;
            }
            stack.setDamageValue(stack.getDamageValue() - 100);
            en.displayClientMessage(new StringTextComponent("A piece of gear that almost broke was saved!"), false);
        }
    },
    GEAR_REPAIR("Gear Repair", "gear_repair") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.IRON_INGOT, TabletItems.RARE_BLANK_TABLET.get());
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {

            ItemStack stack = PlayerUtils.lowestDurabilityWornGear(en);

            if (stack.isEmpty() || !stack.isDamageableItem()) {
                return false;
            }

            float perc = (float) stack.getDamageValue() / (float) stack.getMaxDamage();
            return perc > 0.7F;
        }

        @Override
        public void onActivate(PlayerEntity en) {
            ItemStack stack = PlayerUtils.lowestDurabilityWornGear(en);
            if (stack.isEmpty()) {
                return;
            }
            stack.setDamageValue(0);
            en.displayClientMessage(new StringTextComponent("A piece of gear was fully repaired."), false);
        }
    },
    ANTI_HUNGER("Anti Hunger", "anti_hunger") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.BREAD, TabletItems.BLANK_TABLET.get());
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {
            return en.getFoodData()
                .getFoodLevel() < 10 && !en.hasEffect(Effects.SATURATION);
        }

        @Override
        public void onActivate(PlayerEntity en) {
            en.addEffect(new EffectInstance(Effects.SATURATION, DUR, 1));
        }
    };

    TabletTypes(String word, String id) {
        this.word = word;

        this.id = id;
    }

    static int DUR = 20 * 60;

    public abstract List<Item> getRecipeItems();

    public int cooldownTicks() {
        return 0;
    }

    boolean healthIsBellowTresh(PlayerEntity en) {
        return HealthUtils.getCombinedHealthMulti(en) < 0.5F;
    }

    public abstract boolean shouldActivate(PlayerEntity en, DamageSource source);

    public abstract void onActivate(PlayerEntity en);

    public String word;
    public String id;
}
