package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.database.base.Rarities;
import com.robertx22.mine_and_slash.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.bases.GearItemSlotPart;
import com.robertx22.mine_and_slash.loot.blueprints.bases.IsUniquePart;
import com.robertx22.mine_and_slash.loot.blueprints.bases.UnidentifiedPart;
import com.robertx22.mine_and_slash.loot.blueprints.bases.UniqueGearPart;
import com.robertx22.mine_and_slash.loot.generators.stack_changers.DamagedGear;
import com.robertx22.mine_and_slash.loot.generators.util.GearCreationUtils;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import net.minecraft.item.ItemStack;

public class GearBlueprint extends ItemBlueprint {

    public GearBlueprint(int lvl) {
        super(lvl);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);
    }

    public GearBlueprint(LootInfo info) {
        super(info);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);

        this.rarity.setupChances(info);
        this.isUniquePart.setupChances(info);
    }

    public GearBlueprint(int lvl, int tier) {
        super(tier);
        actionsAfterGeneration.add(DamagedGear.INSTANCE);
    }

    public GearItemSlotPart gearItemSlot = new GearItemSlotPart(this);
    public UnidentifiedPart unidentifiedPart = new UnidentifiedPart(this);
    public UniqueGearPart uniquePart = new UniqueGearPart(this);
    public IsUniquePart isUniquePart = new IsUniquePart(this);

    @Override
    public BaseRaritiesContainer<? extends Rarity> getRarityContainer() {
        return Rarities.Gears;
    }

    public GearItemData createData() {
        return GearCreationUtils.CreateData(this);
    }

    @Override
    ItemStack generate() {
        return GearCreationUtils.CreateStack(createData());
    }

}
