package com.robertx22.age_of_exile.player_skills.items.protection_tablets;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;

import java.util.Arrays;
import java.util.List;

public enum TabletTypes {

    ANTI_FIRE("Anti Fire", "anti_fire") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.BLAZE_POWDER, ModRegistry.TABLETS.BLANK_TABLET);
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {
            return healthIsBellowTresh(en) && source.isFire();
        }

        @Override
        public void onActivate(PlayerEntity en) {
            en.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, DUR, 1));
        }
    },
    ANTI_DEATH("Anti Death", "anti_death") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.ENCHANTED_GOLDEN_APPLE, ModRegistry.TABLETS.RARE_BLANK_TABLET);
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
            return Arrays.asList(Items.IRON_INGOT, ModRegistry.TABLETS.BLANK_TABLET);
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {

            ItemStack stack = PlayerUtils.lowestDurabilityWornGear(en);

            if (stack.isEmpty()) {
                return false;
            }

            int left = stack.getMaxDamage() - stack.getDamage();
            return left < 20;
        }

        @Override
        public void onActivate(PlayerEntity en) {
            ItemStack stack = PlayerUtils.lowestDurabilityWornGear(en);
            if (stack.isEmpty()) {
                return;
            }
            stack.setDamage(stack.getDamage() - 100);
            en.sendMessage(new LiteralText("A piece of gear that almost broke was saved!"), false);
        }
    },
    ANTI_HUNGER("Anti Hunger", "anti_hunger") {
        @Override
        public List<Item> getRecipeItems() {
            return Arrays.asList(Items.BREAD, ModRegistry.TABLETS.BLANK_TABLET);
        }

        @Override
        public boolean shouldActivate(PlayerEntity en, DamageSource source) {
            return healthIsBellowTresh(en);
        }

        @Override
        public void onActivate(PlayerEntity en) {
            en.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, DUR, 1));
        }
    };

    TabletTypes(String word, String id) {
        this.word = word;

        this.id = id;
    }

    static int DUR = 20 * 60;

    public abstract List<Item> getRecipeItems();

    boolean healthIsBellowTresh(PlayerEntity en) {
        return HealthUtils.getCombinedHealthMulti(en) < 0.5F;
    }

    public abstract boolean shouldActivate(PlayerEntity en, DamageSource source);

    public abstract void onActivate(PlayerEntity en);

    public String word;
    public String id;
}
