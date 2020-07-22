package com.robertx22.mine_and_slash.capability.player;

import com.robertx22.mine_and_slash.capability.bases.ICommonPlayerCap;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.spells.IAbility;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.PlayerCaps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class PlayerSpellCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "spells");

    private static final String PLAYER_SPELL_DATA = "player_spells_data";

    public abstract static class ISpellsCap implements ICommonPlayerCap {

        public abstract BaseSpell getCurrentRightClickSpell();

        public abstract BaseSpell getSpellByNumber(int key);

        public abstract SpellCastingData getCastingData();

        public abstract int getEffectiveAbilityLevel(EntityCap.UnitData data, IAbility ability);

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
            return this.spellCastingData.getSelectedSpell(); // TODO
        }

        @Override
        public BaseSpell getSpellByNumber(int key) {
            return this.spellCastingData.getSpellByNumber(key);
        }

        @Override
        public SpellCastingData getCastingData() {
            return this.spellCastingData;
        }

        @Override
        public int getEffectiveAbilityLevel(EntityCap.UnitData data, IAbility ability) {
            if (ability.getAbilityType() == IAbility.Type.SPELL) {
                try {
                    return this.getCastingData()
                        .getSkillGem(ability.GUID()).level;
                } catch (Exception e) {
                    return 1;
                }
            }
            return data.getLevel(); // if its an effect or synergy, use player level.
        }

    }

}
