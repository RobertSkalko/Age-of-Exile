package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearItemSlotPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearRarityPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.UniqueGearPart;
import com.robertx22.age_of_exile.loot.generators.GearSoulLootGen;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class GearBlueprint extends ItemBlueprint {

    public Item item = Items.AIR;

    public GearBlueprint(int lvl) {
        super(lvl);
        this.item = item;
    }

    public GearBlueprint(LootInfo info) {
        super(info);

        this.info = info;
        this.rarity.setupChances(info);
    }

    public GearBlueprint(int lvl, int tier) {
        super(lvl, tier);

    }

    public GearItemSlotPart gearItemSlot = new GearItemSlotPart(this);
    public GearRarityPart rarity = new GearRarityPart(this);
    public UniqueGearPart uniquePart = new UniqueGearPart(this);

    @Override
    public RarityRegistryContainer<GearRarity> getRarityContainer() {
        return ExileDB.GearRarities();
    }

    public GearItemData createData() {
        return GearCreationUtils.CreateData(this);
    }

    @Override
    ItemStack generate() {
        if (item == Items.AIR) {
            return GearSoulLootGen.createSoulBasedOnGear(this);
        } else {
            return GearCreationUtils.CreateStack(createData(), item);
        }
    }

}
