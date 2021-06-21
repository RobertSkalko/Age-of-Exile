package com.robertx22.age_of_exile.loot.blueprints;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.RarityRegistryContainer;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.bases.LevelPart;
import com.robertx22.age_of_exile.loot.generators.stack_changers.IStackAction;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

// use once and discard!
public abstract class ItemBlueprint {

    public LootInfo info = null;

    public int extraLevelModifier = 0;

    public ItemBlueprint(int lvl) {
        this.level.number = lvl;
        this.onConstruct();
    }

    public ItemBlueprint(LootInfo info) {
        this.level.number = info.level;

        if (info.mobData != null) {
            extraLevelModifier = ExileDB.MobRarities()
                .get(info.mobData.getRarity()).loot_lvl_modifier;
        }

        this.onConstruct();
    }

    public ItemBlueprint(int lvl, int tier) {
        this.level.number = lvl;
        this.onConstruct();
    }

    public List<IStackAction> actionsAfterGeneration = new ArrayList<>();

    boolean itemWasGenerated = false;

    void onConstruct() {

    }

    public LevelPart level = new LevelPart(this);

    abstract ItemStack generate();

    public abstract RarityRegistryContainer<? extends Rarity> getRarityContainer();

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
