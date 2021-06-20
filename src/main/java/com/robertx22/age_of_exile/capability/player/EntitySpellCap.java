package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGem;
import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.spells.skill_gems.SkillGemsData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.AuraStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.effectdatas.ReserveManaEvent;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EntitySpellCap {

    private static final String PLAYER_SPELL_DATA = "player_spells_data";
    private static final String GEMS = "gems";

    public abstract static class ISpellsCap implements ICommonPlayerCap, IApplyableStats {

        public abstract Spell getSpellByNumber(int key);

        public abstract SpellCastingData getCastingData();

        public abstract SkillGemsData getSkillGemData();

        public abstract void onSpellHitTarget(Entity spellEntity, LivingEntity target);

        public abstract boolean alreadyHit(Entity spellEntity, LivingEntity target);

        public abstract void triggerAura(Spell spell);

        public abstract float getManaReservedByAuras();

        public abstract float getReservedManaMulti();
    }

    public static class DefaultImpl extends ISpellsCap {

        SpellCastingData spellCastingData = new SpellCastingData();

        SkillGemsData skillGems = new SkillGemsData();

        LivingEntity entity;

        public DefaultImpl(LivingEntity entity) {
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
            SkillGemData data = this.skillGems.getSkillGemOf(key);

            if (data != null) {
                SkillGem gem = data.getSkillGem();
                if (gem != null) {
                    if (Database.Spells()
                        .isRegistered(gem.spell_id)) {
                        return Database.Spells()
                            .get(data.getSkillGem()
                                .spell_id);
                    }
                }
            }

            return null;
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
        public void triggerAura(Spell spell) {

            String key = spell.GUID();
            SpellCastingData.AuraData current = spellCastingData.auras.getOrDefault(key, new SpellCastingData.AuraData(false, 0, 0));
            current.active = !current.active;

            for (int i = 0; i < skillGems.stacks.size(); i++) {
                ItemStack x = skillGems.stacks.get(i);
                SkillGemData data = StackSaving.SKILL_GEMS.loadFrom(x);
                if (data != null && data.getSkillGem().spell_id.equals(spell.GUID())) {
                    current.place = i;
                    break;
                }
            }

            if (current.active) {
                ReserveManaEvent effect = new ReserveManaEvent(spell, entity);
                effect.Activate();
                current.mana_reserved = effect.data.getNumber();
            } else {
                current.mana_reserved = 0;
            }

            spellCastingData.auras.put(key, current);

            Load.Unit(entity)
                .setEquipsChanged(true);

        }

        @Override
        public float getManaReservedByAuras() {

            List<String> auras = getAuras();

            return (float) spellCastingData.auras.entrySet()
                .stream()
                .filter(x -> x.getValue().active && auras.contains(x.getKey()))
                .mapToDouble(x -> x.getValue().mana_reserved)
                .sum();
        }

        @Override
        public float getReservedManaMulti() {
            float multi = 1F - getManaReservedByAuras();

            return multi;
        }

        public List<String> getAuras() {
            return this.spellCastingData.auras.entrySet()
                .stream()
                .filter(x -> {
                    if (x.getValue().active) {
                        SkillGemData data = StackSaving.SKILL_GEMS.loadFrom(skillGems.stacks.get(x.getValue().place));
                        return data != null && data.getSkillGem() != null && data.getSkillGem().spell_id.equals(x.getKey());
                    }
                    return false;
                })
                .map(x -> x.getKey())
                .collect(Collectors.toList());
        }

        @Override
        public List<StatContext> getStatAndContext(LivingEntity en) {

            List<StatContext> ctxs = new ArrayList<>();

            List<String> auras = getAuras();
            auras.forEach(x -> {
                skillGems.stacks.stream()
                    .forEach(s -> {
                        SkillGemData data = StackSaving.SKILL_GEMS.loadFrom(s);
                        if (data != null && data.getSkillGem() != null && data.getSkillGem().spell_id.equals(x)) {
                            Spell spell = Database.Spells()
                                .get(data.getSkillGem().spell_id);

                            List<ExactStatData> list = spell.aura_data.getStats(data, en, data.lvl);
                            ctxs.add(new AuraStatCtx(spell, list));
                        }
                    });

            });

            return ctxs;
        }

    }

}
