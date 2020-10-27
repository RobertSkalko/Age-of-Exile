package com.robertx22.age_of_exile.saveclasses.perks;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.config.forge.ModConfig;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
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
    private HashMap<String, SchoolData> spells = new HashMap<>();

    @Store
    public int reset_points = 5;

    public HashMap<String, SchoolData> getPerks(SpellSchool.SchoolType type) {
        if (type == SpellSchool.SchoolType.SPELLS) {
            return spells;
        } else {
            return perks;
        }
    }

    public int getAllocatedPoints(SpellSchool.SchoolType type) {
        int points = 0;
        for (Map.Entry<String, SchoolData> x : getPerks(type).entrySet()) {
            points += x.getValue()
                .getAllocatedPointsInSchool();
        }
        return points;
    }

    public int getFreePoints(EntityCap.UnitData data, SpellSchool.SchoolType type) {

        int num = 0;
        if (type == SpellSchool.SchoolType.SPELLS) {
            num = (int) ModConfig.get().Server.STARTING_SPELL_POINTS;
            num += ModConfig.get().Server.SPELL_POINTS_AT_MAX_LEVEL * LevelUtils.getMaxLevelMultiplier(data.getLevel());

        } else {
            num = (int) ModConfig.get().Server.STARTING_TALENT_POINTS;
            num += ModConfig.get().Server.TALENT_POINTS_AT_MAX_LEVEL * LevelUtils.getMaxLevelMultiplier(data.getLevel());
        }
        num -= this.getAllocatedPoints(type);
        return num;
    }

    public SchoolData getSchool(SpellSchool school) {
        if (!getPerks(school.getSchool_type()).containsKey(school.GUID())) {
            getPerks(school.getSchool_type()).put(school.GUID(), new SchoolData());
        }
        return getPerks(school.getSchool_type()).get(school.GUID());
    }

    public void putOnFirstEmptyHotbarSlot(PlayerEntity player, Spell spell) {
        for (Map.Entry<Integer, String> entry : new HashMap<>(Load.spells(player)
            .getCastingData()
            .getBar())
            .entrySet()) {

            if (entry.getValue()
                .isEmpty()) {

                Load.spells(player)
                    .getCastingData()
                    .getBar()
                    .put(entry.getKey(), spell.GUID());

                break;
            }

        }

    }

    public void allocate(PlayerEntity player, SpellSchool school, PointData point) {
        Perk perk = school.calcData.getPerk(point);

        if (SlashRegistry.Spells()
            .isRegistered(perk.spell)) {
            if (perk.getSpell() != null && !perk.getSpell()
                .isPassive()) {
                if (!getSchool(school).map.getOrDefault(point, false)) {
                    putOnFirstEmptyHotbarSlot(player, perk.getSpell());
                }
            }

        }
        getSchool(school).map.put(point, true);

        Packets.sendToClient(player, new SyncCapabilityToClient(player, PlayerCaps.SPELLS));

    }

    public void remove(SpellSchool school, PointData point) {
        getSchool(school).map.put(point, false);
    }

    public boolean hasFreePoints(EntityCap.UnitData data, SpellSchool.SchoolType type) {
        return getFreePoints(data, type) > 0;
    }

    public boolean canAllocate(SpellSchool school, PointData point, EntityCap.UnitData data, PlayerEntity player) {

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

    public boolean canRemove(SpellSchool school, PointData point) {

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

    private boolean hasPathToStart(SpellSchool school, PointData check, PointData toRemove) {
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
