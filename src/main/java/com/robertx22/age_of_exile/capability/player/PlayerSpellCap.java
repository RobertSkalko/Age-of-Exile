package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.spell_related.GiveSpellStat;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlayerSpellCap {

    private static final String PLAYER_SPELL_DATA = "player_spells_data";

    public abstract static class ISpellsCap implements ICommonPlayerCap {

        public abstract Spell getCurrentRightClickSpell();

        public abstract Spell getSpellByNumber(int key);

        public abstract SpellCastingData getCastingData();

        public abstract List<Spell> getLearnedSpells(LivingEntity en);

        public abstract boolean isSpellLearned(LivingEntity en, Spell spell);
    }

    public static class DefaultImpl extends ISpellsCap {
        SpellCastingData spellCastingData = new SpellCastingData();

        @Override
        public CompoundTag toTag(CompoundTag nbt) {

            try {
                LoadSave.Save(spellCastingData, nbt, PLAYER_SPELL_DATA);
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
        public void fromTag(CompoundTag nbt) {

            try {
                this.spellCastingData = LoadSave.Load(SpellCastingData.class, new SpellCastingData(), nbt, PLAYER_SPELL_DATA);

                if (spellCastingData == null) {
                    spellCastingData = new SpellCastingData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Spell getCurrentRightClickSpell() {
            return this.spellCastingData.getSelectedSpell();
        }

        @Override
        public Spell getSpellByNumber(int key) {
            return this.spellCastingData.getSpellByNumber(key);
        }

        @Override
        public SpellCastingData getCastingData() {
            return this.spellCastingData;
        }

        @Override
        public List<Spell> getLearnedSpells(LivingEntity en) {

            if (en instanceof PlayerEntity) {
                PlayerEntity p = (PlayerEntity) en;
                if (p.isCreative()) { // allow easy testing of all spells
                    return SlashRegistry.Spells()
                        .getList();
                }
            }

            Set<Spell> list = new HashSet<>();

            Load.Unit(en)
                .getUnit()
                .getStats()
                .entrySet()
                .forEach(e -> {
                    if (e.getValue()
                        .GetStat() instanceof GiveSpellStat) {
                        GiveSpellStat stat = (GiveSpellStat) e.getValue()
                            .GetStat();
                        list.add(stat.getSpell());
                    }
                });

            SlashRegistry.Spells()
                .getList()
                .stream()
                .filter(x -> x.getConfig().is_starter)
                .forEach(x -> list.add(x));

            return new ArrayList<>(list);
        }

        @Override
        public boolean isSpellLearned(LivingEntity en, Spell spell) {
            return getLearnedSpells(en).contains(spell);
        }

    }

}
