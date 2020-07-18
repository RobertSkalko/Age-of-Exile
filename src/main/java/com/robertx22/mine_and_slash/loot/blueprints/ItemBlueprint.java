package com.robertx22.mine_and_slash.loot.blueprints;

import com.robertx22.mine_and_slash.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.bases.LevelPart;
import com.robertx22.mine_and_slash.loot.blueprints.bases.RarityPart;
import com.robertx22.mine_and_slash.loot.blueprints.bases.TierPart;
import com.robertx22.mine_and_slash.loot.generators.stack_changers.IStackAction;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

// use once and discard!
public abstract class ItemBlueprint {

    public ItemBlueprint(int lvl) {
        this.level.number = lvl;
        this.onConstruct();
    }

    public ItemBlueprint(LootInfo info) {
        this.level.number = info.level;
        this.tier.number = info.tier;

        this.onConstruct();
    }

    public ItemBlueprint(int lvl, int tier) {
        this.tier.number = tier;
        this.level.number = lvl;
        this.onConstruct();
    }

    protected List<IStackAction> actionsAfterGeneration = new ArrayList<>();

    boolean itemWasGenerated = false;

    void onConstruct() {

    }

    public RarityPart rarity = new RarityPart(this);
    public TierPart tier = new TierPart(this);
    public LevelPart level = new LevelPart(this);

    abstract ItemStack generate();

    public abstract BaseRaritiesContainer<? extends Rarity> getRarityContainer();

    final public ItemStack createStack() {
        checkAndSetGeneratedBoolean();
        ItemStack stack = generate();
        actionsAfterGeneration.forEach(x -> x.changeStack(stack));
        return stack;
    }

    private void checkAndSetGeneratedBoolean() {

        if (itemWasGenerated) {
            try {
                throw new Exception("Do not use a blueprint instance to make more than 1 item!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            itemWasGenerated = true;
        }

    }

}
