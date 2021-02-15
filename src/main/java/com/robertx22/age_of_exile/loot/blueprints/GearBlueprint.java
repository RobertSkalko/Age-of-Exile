package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearItemSlotPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.GearRarityPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.UnidentifiedPart;
import com.robertx22.age_of_exile.loot.blueprints.bases.UniqueGearPart;
import com.robertx22.age_of_exile.loot.generators.stack_changers.DamagedGear;
import com.robertx22.age_of_exile.loot.generators.util.GearCreationUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import net.minecraft.item.ItemStack;

public class GearBlueprint extends ItemBlueprint {

    public GearBlueprint(int lvl) {
        super(lvl);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);
    }

    public GearBlueprint(LootInfo info) {
        super(info);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);

        this.info = info;
        this.rarity.setupChances(info);
    }

    public GearBlueprint(int lvl, int tier) {
        super(lvl, tier);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);
    }

    public GearRarityPart rarity = new GearRarityPart(this);

    public GearItemSlotPart gearItemSlot = new GearItemSlotPart(this);
    public UnidentifiedPart unidentifiedPart = new UnidentifiedPart(this);
    public UniqueGearPart uniquePart = new UniqueGearPart(this);

    @Override
    public RarityRegistryContainer<GearRarity> getRarityContainer() {
        return Database.GearRarities();
    }

    public GearItemData createData() {
        return GearCreationUtils.CreateData(this);
    }

    @Override
    ItemStack generate() {
        return GearCreationUtils.CreateStack(createData());
    }

}
