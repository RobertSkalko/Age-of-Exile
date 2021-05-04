package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;

@Storable
public class QuestProgression {

    @Store
    public String uuid = "";

    @Store
    public int number = 0;

    @Store
    public int target = 0;

    @Store
    public boolean finished = false;

    public QuestProgression(String uuid, int target) {
        this.uuid = uuid;
        this.target = target;
    }

    public QuestProgression() {
    }

    public void increaseProgressBy(PlayerEntity player, int num, DungeonData dungeon) {

        if (!finished) {

            number += num;

            player.sendMessage(new LiteralText("Dungeon Progress: " + number + "/" + target), false);

            if (number >= target) {
                finished = true;

                player.sendMessage(
                    new LiteralText("Quest completed!, You can now progress to the next dungeon.")
                    , false);

                float multi = LootUtils.getLevelDistancePunishmentMulti(dungeon.lvl, Load.Unit(player)
                    .getLevel());

                if (multi == 0 && Load.Unit(player)
                    .getLevel() > dungeon.lvl) {
                    player.sendMessage(new LiteralText("You can't receive quest rewards as you are too high level for this dungeon."), false);
                } else {
                    dungeon.quest_rew.stacks.forEach(x -> {
                        PlayerUtils.giveItem(x, player);
                    });
                }

                Load.playerMaps(player)
                    .onDungeonCompletedAdvanceProgress();

            }
        }
    }

}
