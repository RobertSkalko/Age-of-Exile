package com.robertx22.age_of_exile.player_skills.items.backpacks.upgrades;

import com.robertx22.library_of_exile.utils.LoadSave;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

@Storable
public class BagUpgradesData {

    @Store
    public List<String> upgrades = new ArrayList<>();

    public boolean canUpgrade(BackpackUpgradeItem item) {

        List<BackpackUpgradeItem> list = getUpgrades();

        if (list.size() > 9) {
            return false;
        }

        if (item.upgrade.canOverrideSelf()) {
            if (item.tier.lowerTier() != null) {
                if (list.stream()
                    .noneMatch(x -> x.tier == item.tier.lowerTier() && item.upgrade == x.upgrade)) {
                    return false;
                }
            }
        }

        for (BackpackUpgradeItem x : list) {
            if (!x.upgrade.canOverrideSelf()) {
                if (x == item) {
                    return false;
                }
            } else {
                if (x.upgrade == item.upgrade) {
                    if (item.tier.tier < x.tier.tier) {
                        return false;
                    }
                }
            }
        }
        return true;

    }

    public void upgrade(BackpackUpgradeItem item) {

        this.getUpgrades()
            .forEach(x -> {
                if (x.upgrade == item.upgrade) {
                    this.upgrades.remove(Registry.ITEM.getId(x)
                        .toString());
                }
            });

        upgrades.add(Registry.ITEM.getId(item)
            .toString());
    }

    public static BagUpgradesData load(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        BagUpgradesData data = LoadSave.Load(BagUpgradesData.class, new BagUpgradesData(), stack.getTag(), "upgrades");

        if (data != null) {
            return data;
        }

        return new BagUpgradesData();

    }

    public void saveToStack(ItemStack stack) {

        if (!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }

        LoadSave.Save(this, stack.getTag(), "upgrades");
    }

    public List<BackpackUpgradeItem> getUpgrades() {

        List<BackpackUpgradeItem> list = new ArrayList<>();

        upgrades.forEach(x -> {
            Item item = Registry.ITEM.get(new Identifier(x));
            if (item instanceof BackpackUpgradeItem) {
                list.add((BackpackUpgradeItem) item);
            }
        });

        return list;
    }
}
