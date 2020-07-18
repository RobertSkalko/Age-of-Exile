package com.robertx22.mine_and_slash.capability.player;

import com.robertx22.mine_and_slash.capability.bases.BaseProvider;
import com.robertx22.mine_and_slash.capability.bases.BaseStorage;
import com.robertx22.mine_and_slash.capability.bases.ICommonPlayerCap;
import com.robertx22.mine_and_slash.capability.entity.EntityCap;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.BaseSpell;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.spells.IAbility;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.uncommon.datasaving.base.LoadSave;
import com.robertx22.mine_and_slash.vanilla_mc.packets.sync_cap.PlayerCaps;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerSpellCap {

    public static final Identifier RESOURCE = new Identifier(Ref.MODID, "spells");

    private static final String SPELL_PERK_DATA = "spell_perk_data";
    private static final String PLAYER_SPELL_DATA = "player_spells_data";

    @CapabilityInject(ISpellsCap.class)
    public static final Capability<ISpellsCap> Data = null;

    public abstract static class ISpellsCap implements ICommonPlayerCap {

        public abstract BaseSpell getCurrentRightClickSpell();

        public abstract BaseSpell getSpellByKeybind(int key, SpellCastingData.Hotbar bar);

        public abstract SpellCastingData getCastingData();

        public abstract int getEffectiveAbilityLevel(EntityCap.UnitData data, IAbility ability);

    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(RESOURCE, new Provider());
            }
        }

    }

    public static class Provider extends BaseProvider<ISpellsCap> {

        @Override
        public ISpellsCap defaultImpl() {
            return new DefaultImpl();
        }

        @Override
        public Capability<ISpellsCap> dataInstance() {
            return Data;
        }
    }

    public static class DefaultImpl extends ISpellsCap {
        SpellCastingData spellCastingData = new SpellCastingData();

        @Override
        public CompoundTag saveToNBT() {
            CompoundTag nbt = new CompoundTag();

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
        public void loadFromNBT(CompoundTag nbt) {

            this.spellCastingData = LoadSave.Load(
                SpellCastingData.class, new SpellCastingData(), nbt, PLAYER_SPELL_DATA);

            if (spellCastingData == null) {
                spellCastingData = new SpellCastingData();
            }
        }

        @Override
        public BaseSpell getCurrentRightClickSpell() {
            return null; // TODO
        }

        @Override
        public BaseSpell getSpellByKeybind(int key, SpellCastingData.Hotbar hotbar) {
            return this.spellCastingData.getSpellByKeybind(key, hotbar);
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

    public static class Storage extends BaseStorage<ISpellsCap> {

    }

}
