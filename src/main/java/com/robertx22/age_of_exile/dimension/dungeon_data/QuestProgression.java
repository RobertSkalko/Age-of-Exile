package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.loot.LootUtils;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.age_of_exile.vanilla_mc.commands.TeamCommand;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;

import java.util.List;

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

    public void increaseProgressBy(PlayerEntity player, int num, SingleDungeonData single) {

        DungeonData dungeon = single.data;

        if (!finished) {

            List<PlayerEntity> members = TeamUtils.getOnlineMembers(player);

            if (members
                .size() > 1) {
                for (PlayerEntity p : members) {
                    if (p.getUuidAsString()
                        .equals(single.ownerUUID)) {
                        player = p;
                    }
                }
            }

            number += num;

            if (!single.pop.donePop) {
                return; // don't check for completition if dungeon didn't finish generating
            }

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

                    if (members.size() > 1) {
                        members.forEach(x -> {
                            TeamCommand.sendDpsCharts(x);
                        });
                    }
                    for (ItemStack x : dungeon.quest_rew.stacks) {
                        PlayerUtils.giveItem(x, player);
                    }
                }

                Load.playerMaps(player)
                    .onDungeonCompletedAdvanceProgress();

            }
        }
    }

}
