package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.perks.PlayerPerksData;
import com.robertx22.age_of_exile.saveclasses.perks.SchoolData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntityPerks implements ICommonPlayerCap, IApplyableStats {
    LivingEntity entity;

    public PlayerPerksData data = new PlayerPerksData();

    public EntityPerks(LivingEntity entity) {
        this.entity = entity;
    }

    @Override
    public PlayerCaps getCapType() {
        return PlayerCaps.ENTITY_PERKS;
    }

    public void clearAllPerks() {
        this.data.perks.clear();
    }

    public List<SpellModifier> getAllSpellModifiersFor(Spell spell) {
        List<SpellModifier> list = new ArrayList<>();
        getAllAllocatedPerks().stream()
            .filter(x -> x.type == Perk.PerkType.SPELL_MOD)
            .forEach(x -> list.addAll(x.getSpellMods()
                .stream()
                .filter(e -> e.affectsSpell(spell))
                .collect(Collectors.toList())));
        return list;
    }

    public List<Perk> getAllAllocatedPerks() {
        List<Perk> perks = new ArrayList<>();
        for (Map.Entry<String, SchoolData> x : data.perks.entrySet()) {
            SpellSchool school = SlashRegistry.SpellSchools()
                .get(x.getKey());
            for (PointData p : x.getValue()
                .getAllocatedPoints(school)) {
                perks.add(school.calcData.perks.get(p));
            }
        }

        return perks;
    }

    public Boolean hasSpellModifier(SpellModifier mod) {
        return getAllAllocatedPerks().stream()
            .filter(x -> x.type == Perk.PerkType.SPELL_MOD)
            .anyMatch(e -> e.getSpellMods()
                .stream()
                .anyMatch(m -> m.identifier.equals(mod.identifier)));
    }

    public PerkStatus getStatus(PlayerEntity player, SpellSchool school, PointData point) {

        if (isAllocated(school, point)) {
            return PerkStatus.CONNECTED;
        }
        Perk perk = school.calcData.perks.get(point);

        if (perk.isLockedUnderAdvancement()) {
            if (!perk.didPlayerUnlockAdvancement(player)) {
                return PerkStatus.LOCKED_UNDER_ACHIEV;
            }
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
    public void applyStats(EntityCap.UnitData data) {
        for (Perk x : getAllAllocatedPerks()) {
            for (OptScaleExactStat s : x.stats) {
                s.applyStats(data);
            }
        }
    }
}
