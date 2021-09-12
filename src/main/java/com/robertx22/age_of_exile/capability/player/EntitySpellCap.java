package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.spells.skill_gems.SkillGemsData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EntitySpellCap {

    private static final String PLAYER_SPELL_DATA = "player_spells_data";
    private static final String GEMS = "gems";

    public abstract static class ISpellsCap implements ICommonPlayerCap, IApplyableStats {

        public abstract Spell getSpellByNumber(int key);

        public abstract int getFreeSpellPoints();

        public abstract List<Spell> getLearnedSpells();

        public abstract SpellCastingData getCastingData();

        public abstract SkillGemsData getSkillGemData();

        public abstract void onSpellHitTarget(Entity spellEntity, LivingEntity target);

        public abstract boolean alreadyHit(Entity spellEntity, LivingEntity target);

        public abstract int getLevelOf(String id);

    }

    public static class SpellCap extends ISpellsCap {

        SpellCastingData spellCastingData = new SpellCastingData();

        SkillGemsData skillGems = new SkillGemsData();

        LivingEntity entity;

        public SpellCap(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public NbtCompound toTag(NbtCompound nbt) {

            try {
                LoadSave.Save(spellCastingData, nbt, PLAYER_SPELL_DATA);
                LoadSave.Save(skillGems, nbt, GEMS);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return nbt;

        }

        @Override
        public PlayerCaps getCapType() {
            return PlayerCaps.SPELLS;
        }

        @Override
        public void fromTag(NbtCompound nbt) {

            try {
                this.spellCastingData = LoadSave.Load(SpellCastingData.class, new SpellCastingData(), nbt, PLAYER_SPELL_DATA);

                if (spellCastingData == null) {
                    spellCastingData = new SpellCastingData();
                }

                this.skillGems = LoadSave.Load(SkillGemsData.class, new SkillGemsData(), nbt, GEMS);

                if (skillGems == null) {
                    skillGems = new SkillGemsData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Spell getSpellByNumber(int key) {
            String spellid = this.skillGems.hotbars.get(key);
            if (spellid != null && !spellid.isEmpty()) {
                return ExileDB.Spells()
                    .get(spellid);

            }

            return null;
        }

        public int getSpentSpellPoints() {
            int total = 0;
            for (Integer x : this.skillGems.allocated_lvls.values()) {
                total += x;
            }
            return total;
        }

        @Override
        public int getFreeSpellPoints() {
            return (int) (GameBalanceConfig.get().SPELL_POINTS_PER_LEVEL * Load.Unit(entity)
                .getLevel()) - getSpentSpellPoints();
        }

        @Override
        public List<Spell> getLearnedSpells() {
            return ExileDB.Spells()
                .getFilterWrapped(x -> getLevelOf(x.GUID()) > 0).list;
        }

        @Override
        public SpellCastingData getCastingData() {
            return this.spellCastingData;
        }

        @Override
        public SkillGemsData getSkillGemData() {
            return this.skillGems;
        }

        public HashMap<UUID, List<UUID>> mobsHit = new HashMap<>();

        @Override
        public void onSpellHitTarget(Entity spellEntity, LivingEntity target) {

            UUID id = target.getUuid();

            UUID key = spellEntity.getUuid();

            if (!mobsHit.containsKey(key)) {
                mobsHit.put(key, new ArrayList<>());
            }
            mobsHit.get(key)
                .add(id);

            if (mobsHit.size() > 1000) {
                mobsHit.clear();
            }

        }

        @Override
        public boolean alreadyHit(Entity spellEntity, LivingEntity target) {
            // this makes sure piercing projectiles hit target only once and then pass through
            // i can replace this with an effect that tags them too

            UUID key = spellEntity.getUuid();

            if (!mobsHit.containsKey(key)) {
                return false;
            }
            return mobsHit.get(key)
                .contains(target.getUuid());
        }

        @Override
        public int getLevelOf(String id) {
            return skillGems.getLevelOf(id);
        }

        @Override
        public List<StatContext> getStatAndContext(LivingEntity en) {

            List<StatContext> ctxs = new ArrayList<>();

            return ctxs;
        }

    }

}
