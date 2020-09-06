package com.robertx22.age_of_exile.saveclasses.perks;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.saveclasses.PointData;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;

@Storable
public class PlayerPerksData {

    @Store
    public HashMap<String, SchoolData> perks = new HashMap<>();

    @Store
    public int reset_points = 5;

    public int getAllocatedPoints() {
        int points = 0;
        for (Map.Entry<String, SchoolData> x : perks.entrySet()) {
            points += x.getValue()
                .getAllocatedPointsInSchool();
        }
        return points;
    }

    public int getFreePoints(EntityCap.UnitData data) {
        return (int) ((ModConfig.get().Server.TALENT_POINTS_PER_LVL * data.getLevel()) - getAllocatedPoints());
    }

    public SchoolData getSchool(SpellSchool school) {
        if (!perks.containsKey(school.GUID())) {
            perks.put(school.GUID(), new SchoolData());
        }
        return perks.get(school.GUID());
    }

    public void allocate(SpellSchool school, PointData point) {
        getSchool(school).map.put(point, true);
    }

    public void remove(SpellSchool school, PointData point) {
        getSchool(school).map.put(point, false);
    }

    public boolean hasFreePoints(EntityCap.UnitData data) {
        return getFreePoints(data) > 0;
    }

    public boolean canAllocate(SpellSchool school, PointData point, EntityCap.UnitData data, PlayerEntity player) {

        if (!hasFreePoints(data)) {
            return false;
        }

        Perk perk = school.calcData.perks.get(point);

        if (perk.lvl_req > data.getLevel()) {
            return false;
        }

        if (!perk.is_entry) {
            Set<PointData> con = school.calcData.connections.get(point);
            if (!con.stream()
                .anyMatch(x -> getSchool(school)
                    .isAllocated(x))) {
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

    public boolean canRemove(SpellSchool school, PointData point, PlayerEntity player) {

        if (!getSchool(school).isAllocated(point)) {
            return false;
        }

        if (reset_points < 1) {
            return false;
        }

        for (PointData con : school.calcData.connections.get(point)) {
            if (getSchool(school).isAllocated(con)) {
                Perk perk = school.calcData.perks.get(con);
                if (perk.is_entry) {
                    continue;
                }
                if (!hasPathToStart(school, con, point)) {
                    return false;
                }
            }
        }

        return true;

    }

    private boolean hasPathToStart(SpellSchool school, PointData check, PointData toRemove) {
        Queue<PointData> openSet = new ArrayDeque<>();

        openSet.addAll(school.calcData.connections.get(check));

        Set<PointData> closedSet = new HashSet<>();

        while (!openSet.isEmpty()) {
            PointData current = openSet.poll();

            Perk perk = school.calcData.perks.get(current);

            if (current.equals(toRemove) || !getSchool(school).isAllocated(current)) {
                continue; // skip exploring this path
            }

            if (perk.is_entry) {
                return true;
            }

            if (!closedSet.add(current)) {
                continue; // we already visited it
            }

            openSet.addAll(school.calcData.connections.get(current));

        }

        return false;
    }

}
