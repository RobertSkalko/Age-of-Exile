package com.robertx22.age_of_exile.saveclasses;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Storable
public class PlayerPerksData {

    @Store
    public HashMap<String, SchoolData> perks = new HashMap<>();

    public int getAllocatedPoints() {
        int points = 0;

        for (Map.Entry<String, SchoolData> x : perks.entrySet()) {
            points += x.getValue().list
                .size();
        }
        return points;
    }

    public int getFreePoints(EntityCap.UnitData data) {
        return (int) ((ModConfig.get().Server.TALENT_POINTS_PER_LVL * data.getLevel()) - getAllocatedPoints());
    }

    public boolean hasFreePoints(EntityCap.UnitData data) {
        return getFreePoints(data) > 0;
    }

    public boolean canAllocate(SpellSchool school, PointData point, EntityCap.UnitData data, PlayerEntity player) {

        if (!hasFreePoints(data)) {
            return false;
        }

        Perk perk = school.calcData.perks.get(point);

        if (!perk.is_entry) {
            Set<PointData> con = school.calcData.connections.get(point);
            if (con.stream()
                .allMatch(x -> !this.perks.get(school.identifier)
                    .isAllocated(point))) {
                return false;
            }
        }

        if (perk.isLockedUnderAdvancement()) {
            if (!perk.didPlayerUnlockAdvancement(player)) {
                return false;
            }

        }

        return true;

    }

}
