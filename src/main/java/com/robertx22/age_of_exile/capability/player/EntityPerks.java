package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.perks.PlayerPerksData;
import com.robertx22.age_of_exile.saveclasses.perks.SchoolData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.TalentStatCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityPerks implements ICommonPlayerCap, IApplyableStats {
    PlayerEntity entity;

    public PlayerPerksData data = new PlayerPerksData();

    public EntityPerks(PlayerEntity entity) {
        this.entity = entity;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.ENTITY_PERKS;
    }

    public void clearAllTalents() {
        this.data.getPerks(SpellSchool.SchoolType.TALENTS)
                .clear();

    }

    public HashMap<PointData, Perk> getAllAllocatedPerks() {
        HashMap<PointData, Perk> perks = new HashMap<>();
        for (SpellSchool.SchoolType type : SpellSchool.SchoolType.values()) {
            for (Map.Entry<String, SchoolData> x : data.getPerks(type)
                    .entrySet()) {
                if (Database.SpellSchools()
                        .isRegistered(x.getKey())) {

                    SpellSchool school = Database.SpellSchools()
                            .get(x.getKey());
                    if (school != null) {
                        for (PointData p : x.getValue()
                                .getAllocatedPoints(school)) {
                            perks.put(p, school.calcData.getPerk(p));
                        }
                    }
                }
            }
        }
        return perks;
    }

    public PerkStatus getStatus(PlayerEntity player, SpellSchool school, PointData point) {

        if (isAllocated(school, point)) {
            return PerkStatus.CONNECTED;
        }
        Perk perk = school.calcData.getPerk(point);

        if (perk.isLockedToPlayer(player)) {
            return PerkStatus.LOCKED_UNDER_ACHIEV;
        }

        if (data.canAllocate(school, point, Load.Unit(player), player)) {
            return PerkStatus.POSSIBLE;
        } else {
            return PerkStatus.BLOCKED;
        }
    }

    public Perk.Connection getConnection(SpellSchool school, PointData one, PointData two) {

        if (isAllocated(school, one) && isAllocated(school, two)) {
            return Perk.Connection.LINKED;
        }

        if (isAllocated(school, one)) {
            return Perk.Connection.POSSIBLE;
        }
        if (isAllocated(school, two)) {
            return Perk.Connection.POSSIBLE;
        }

        return Perk.Connection.BLOCKED;
    }

    public boolean isAllocated(SpellSchool school, PointData point) {
        if (entity instanceof PlayerEntity) {
            return data.getSchool(school)
                    .isAllocated(point);
        } else {
            return true;
        }
    }

    @Override
    public void fromTag(CompoundTag tag) {
        try {
            this.data = LoadSave.Load(PlayerPerksData.class, new PlayerPerksData(), tag, "data");

            if (data == null) {
                data = new PlayerPerksData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        try {
            LoadSave.Save(data, tag, "data");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tag;
    }

    @Override
    public List<StatContext> getStatAndContext(LivingEntity en) {
        List<StatContext> ctx = new ArrayList<>();

        HashMap<PointData, Perk> map = getAllAllocatedPerks();

        int lvl = Load.Unit(en)
                .getLevel();

        map.forEach((key, value) -> {
            List<ExactStatData> stats = new ArrayList<>();
            value.stats.forEach(s -> stats.add(s.toExactStat(lvl)));
            ctx.add(new TalentStatCtx(key, value, stats));

        });

        return ctx;
    }
}
