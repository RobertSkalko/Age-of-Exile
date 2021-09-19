package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

public class OnSmeltMining {

    public static void hookOnDropExp(Object2IntOpenHashMap<Identifier> recipesUsed, AbstractFurnaceBlockEntity furnace, PlayerEntity player, CallbackInfo ci) {

        RPGPlayerData data = Load.playerRPGData(player);

        PlayerSkill mining = ExileDB.PlayerSkills()
            .get(PlayerSkillEnum.MINING.id);

        for (Object2IntMap.Entry<Identifier> entry : recipesUsed.object2IntEntrySet()) {
            Optional<? extends Recipe<?>> recipe = player.world.getRecipeManager()
                .get(entry.getKey());

            if (!recipe.isPresent()) {
                continue;
            }

            int timesUsed = entry.getIntValue();

            for (int i = 0; i < timesUsed; i++) {
                ItemStack output = recipe.get()
                    .getOutput();

                mining.item_smelt_exp.forEach(s -> {
                    if ((output
                        .getItem() == s.getItem())) {
                        int exp = s.exp;
                        data.professions.addExp(player, mining.type_enum, exp);

                        SkillDropEvent effect = new SkillDropEvent(
                            player,
                            PlayerSkillEnum.MINING,
                            mining.getExtraDropsFor(player, exp, LevelUtils.levelToSkillTier(data.professions.getProfessionLevel(mining.type_enum))));
                        effect.Activate();

                        effect.extraDrops.forEach(x -> PlayerUtils.giveItem(x, player));
                    }
                });
            }
        }
    }
}
