package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.saveclasses.perks.PlayerPerksData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

public class EntityPerks implements ICommonPlayerCap {
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
}
