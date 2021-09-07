package com.robertx22.age_of_exile.dimension.dungeon_data;

import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public enum DungeonType {

    DUNGEON(DimensionIds.DUNGEON_DIMENSION) {
        @Override
        public void onDeath(DungeonData data, PlayerEntity player) {

            data.deaths++;

            loseFavor(data, player);

            int deathsAllowed = (int) (data.getDifficulty().deaths_allowed * data.team.deathsAllowedMulti);

            if (data.deaths > deathsAllowed) {
                data.fail = true;
                TeamUtils.getOnlineMembers(player)
                    .forEach(x -> x.sendMessage(new LiteralText("Expedition failed due to too many deaths.").formatted(Formatting.RED), false));
                // todo fail dungoen
            }

        }

    };

    DungeonType(Identifier DIMENSION_ID) {
        this.DIMENSION_ID = DIMENSION_ID;
    }

    public abstract void onDeath(DungeonData data, PlayerEntity player);

    protected void loseFavor(DungeonData data, PlayerEntity player) {
        int favorloss = data.getDifficulty().death_favor_penalty;

        Load.favor(player)
            .addFavor(favorloss);

        player.sendMessage(new LiteralText("You lost " + Math.abs(favorloss) + " favor.").formatted(Formatting.RED), false);

    }

    public boolean isDungeon() {
        return this == DUNGEON;
    }

    public Identifier DIMENSION_ID;

}