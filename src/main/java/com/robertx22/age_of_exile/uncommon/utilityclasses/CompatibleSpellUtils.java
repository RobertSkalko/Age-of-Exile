package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.SkillGem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SkillGemItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class CompatibleSpellUtils {

    public static void checkAndGenerate(PlayerEntity player) {

        try {

            if (player.world.isClient) {
                return;
            }

            for (ItemStack stack : player.inventory.main) {

                if (stack.isEmpty() || !(stack.getItem() instanceof SkillGemItem) || SkillGem.has(stack)) {
                    continue;
                }

                SkillGemItem gem = (SkillGemItem) stack.getItem();

                EntityCap.UnitData data = Load.Unit(player);

                SkillGemBlueprint blueprint = new SkillGemBlueprint(data.getLevel());
                blueprint.level.maxLevel = data.getLevel();
                blueprint.spellPart.set(gem.spell);
                SkillGemData sdata = blueprint.createData();

                SkillGem.Save(stack, sdata);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
