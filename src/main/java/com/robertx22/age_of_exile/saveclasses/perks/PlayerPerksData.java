package com.robertx22.age_of_exile.saveclasses.perks;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.TalentTree;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.SyncCapabilityToClient;
import com.robertx22.library_of_exile.main.Packets;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.player.PlayerEntity;

import java.util.*;

@Storable
public class PlayerPerksData {

    @Store
    private HashMap<String, SchoolData> perks = new HashMap<>();

    @Store
    public int reset_points = 5;

    public HashMap<String, SchoolData> getPerks(TalentTree.SchoolType type) {
        return perks;
    }

    public int getAllocatedPoints(TalentTree.SchoolType type) {
        int points = 0;
        for (Map.Entry<String, SchoolData> x : getPerks(type).entrySet()) {
            points += x.getValue()
                .getAllocatedPointsInSchool();
        }
        return points;
    }

    public int getFreePoints(EntityCap.UnitData data, TalentTree.SchoolType type) {

        int num = 0;

        num = (int) GameBalanceConfig.get().STARTING_TALENT_POINTS;
        num += GameBalanceConfig.get().TALENT_POINTS_AT_MAX_LEVEL * LevelUtils.getMaxLevelMultiplier(data.getLevel());

        num -= this.getAllocatedPoints(type);
        return num;
    }

    public SchoolData getSchool(TalentTree school) {
        if (!getPerks(school.getSchool_type()).containsKey(school.GUID())) {
            getPerks(school.getSchool_type()).put(school.GUID(), new SchoolData());
        }
        return getPerks(school.getSchool_type()).get(school.GUID());
    }

    public void allocate(PlayerEntity player, TalentTree school, PointData point) {

        getSchool(school).map.put(point, true);

        Packets.sendToClient(player, new SyncCapabilityToClient(player, PlayerCaps.SPELLS));

    }

    public void remove(TalentTree school, PointData point) {
        getSchool(school).map.put(point, false);
    }

    public boolean hasFreePoints(EntityCap.UnitData data, TalentTree.SchoolType type) {
        return getFreePoints(data, type) > 0;
    }

    public boolean canAllocate(TalentTree school, PointData point, EntityCap.UnitData data, PlayerEntity player) {

        if (!hasFreePoints(data, school.getSchool_type())) {
            return false;
        }

        Perk perk = school.calcData.getPerk(point);

        if (perk.lvl_req > data.getLevel()) {
            return false;
        }

        if (perk.isLockedToPlayer(player)) {
            return false;
        }

        if (!perk.is_entry) {
            Set<PointData> con = school.calcData.connections.get(point);

            if (con == null || !con.stream()
                .anyMatch(x -> getSchool(school)
                    .isAllocated(x))) {
                return false;
            }
        }

        return true;

    }

    public boolean canRemove(TalentTree school, PointData point) {

        if (!getSchool(school).isAllocated(point)) {
            return false;
        }

        if (reset_points < 1) {
            return false;
        }

        for (PointData con : school.calcData.connections.get(point)) {
            if (getSchool(school).isAllocated(con)) {
                Perk perk = school.calcData.getPerk(con);
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

    private boolean hasPathToStart(TalentTree school, PointData check, PointData toRemove) {
        Queue<PointData> openSet = new ArrayDeque<>();

        openSet.addAll(school.calcData.connections.get(check));

        Set<PointData> closedSet = new HashSet<>();

        while (!openSet.isEmpty()) {
            PointData current = openSet.poll();

            Perk perk = school.calcData.getPerk(current);

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
