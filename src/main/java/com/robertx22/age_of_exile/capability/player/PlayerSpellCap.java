package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.age_of_exile.saveclasses.item_classes.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.CompoundTag;

public class PlayerSpellCap {

    private static final String PLAYER_SPELL_DATA = "player_spells_data";

    public abstract static class ISpellsCap implements ICommonPlayerCap {

        public abstract BaseSpell getCurrentRightClickSpell();

        public abstract BaseSpell getSpellByNumber(int key);

        public abstract SpellCastingData getCastingData();

        public abstract SkillGemData getCurrentSkillGem();

        public abstract Inventory getInventory();
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

            this.spellCastingData = LoadSave.Load(
                SpellCastingData.class, new SpellCastingData(), nbt, PLAYER_SPELL_DATA);

            if (spellCastingData == null) {
                spellCastingData = new SpellCastingData();
            }
        }

        @Override
        public BaseSpell getCurrentRightClickSpell() {
            return this.spellCastingData.getSelectedSpell();
        }

        @Override
        public BaseSpell getSpellByNumber(int key) {
            return this.spellCastingData.getSpellByNumber(key);
        }

        @Override
        public SpellCastingData getCastingData() {
            return this.spellCastingData;
        }

        // client only
        @Override
        public SkillGemData getCurrentSkillGem() {
            return
                getCastingData()
                    .getSkillGemByNumber(SpellCastingData.selectedSpell);

        }

        @Override
        public Inventory getInventory() {
            return getCastingData();
        }

    }

}
