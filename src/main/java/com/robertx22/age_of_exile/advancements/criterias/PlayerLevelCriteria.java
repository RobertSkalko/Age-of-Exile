package com.robertx22.age_of_exile.advancements.criterias;

import com.google.gson.JsonObject;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

// todo add advancements and trigger it
public class PlayerLevelCriteria extends AbstractCriterion<PlayerLevelCriteria.LevelCriteria> {
    public static final Identifier ID = new Identifier(Ref.MODID, "player_level");

    @Override
    protected LevelCriteria conditionsFromJson(JsonObject obj, EntityPredicate.Extended pred, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new LevelCriteria(pred, obj.get("level")
            .getAsInt());
    }

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        this.test(player, (levelCriteria) -> levelCriteria.matches(player));
    }

    public static class LevelCriteria extends AbstractCriterionConditions {
        private final int level;

        public LevelCriteria(EntityPredicate.Extended playerPredicate, int lvl) {
            super(ID, playerPredicate);
            this.level = lvl;
        }

        public boolean matches(ServerPlayerEntity player) {
            return Load.Unit(player)
                .getLevel() >= level;
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer ser) {
            JsonObject jsonObject = super.toJson(ser);
            jsonObject.addProperty("level", level);
            return jsonObject;
        }
    }
}
