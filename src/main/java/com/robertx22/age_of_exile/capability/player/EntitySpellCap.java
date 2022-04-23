package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellsData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.components.forge.BaseProvider;
import com.robertx22.library_of_exile.components.forge.BaseStorage;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class EntitySpellCap {
    public static final ResourceLocation RESOURCE = new ResourceLocation(Ref.MODID, "spells");

    private static final String PLAYER_SPELL_DATA = "player_spells_data";
    private static final String GEMS = "gems";

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof LivingEntity) {
                event.addCapability(RESOURCE, new Provider((LivingEntity) event.getObject()));
            }
        }
    }

    public static class Storage implements BaseStorage<ISpellsCap> {

    }

    public static class Provider extends BaseProvider<ISpellsCap, LivingEntity> {
        public Provider(LivingEntity owner) {
            super(owner);
        }

        @Override
        public ISpellsCap newDefaultImpl(LivingEntity owner) {
            return new SpellCap(owner);
        }

        @Override
        public Capability<ISpellsCap> dataInstance() {
            return Data;
        }
    }

    @CapabilityInject(ISpellsCap.class)
    public static final Capability<ISpellsCap> Data = null;

    public abstract static class ISpellsCap implements ICommonPlayerCap, IApplyableStats {

        public abstract Spell getSpellByNumber(int key);

        public abstract void reset();

        public abstract int getFreeSpellPoints();

        public abstract boolean canLearn(SpellSchool school, Spell spell);

        public abstract List<Spell> getLearnedSpells();

        public abstract SpellCastingData getCastingData();

        public abstract SpellsData getSpellsData();

        public abstract void onSpellHitTarget(Entity spellEntity, LivingEntity target);

        public abstract boolean alreadyHit(Entity spellEntity, LivingEntity target);

        public abstract boolean hasSpell(Spell spell);

    }

    public static class SpellCap extends ISpellsCap {

        SpellCastingData spellCastingData = new SpellCastingData();

        SpellsData spellData = new SpellsData();

        LivingEntity entity;

        public SpellCap(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public CompoundNBT saveToNBT() {

            CompoundNBT nbt = new CompoundNBT();

            try {
                LoadSave.Save(spellCastingData, nbt, PLAYER_SPELL_DATA);
                LoadSave.Save(spellData, nbt, GEMS);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return nbt;

        }

        @Override
        public void loadFromNBT(CompoundNBT nbt) {

            try {
                this.spellCastingData = LoadSave.Load(SpellCastingData.class, new SpellCastingData(), nbt, PLAYER_SPELL_DATA);

                if (spellCastingData == null) {
                    spellCastingData = new SpellCastingData();
                }

                this.spellData = LoadSave.Load(SpellsData.class, new SpellsData(), nbt, GEMS);

                if (spellData == null) {
                    spellData = new SpellsData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Spell getSpellByNumber(int key) {
            String spellid = this.spellData.hotbars.get(key);
            if (spellid != null && !spellid.isEmpty()) {
                return ExileDB.Spells()
                    .get(spellid);

            }

            return null;
        }

        @Override
        public void reset() {
            this.spellData = new SpellsData();
        }

        public int getSpentSpellPoints() {
            int total = this.spellData.spells.size();
            return total;
        }

        @Override
        public int getFreeSpellPoints() {
            return (int) (GameBalanceConfig.get().SPELL_POINTS_PER_LEVEL * Load.Unit(entity)
                .getLevel()) - getSpentSpellPoints();
        }

        @Override
        public boolean canLearn(SpellSchool school, Spell spell) {
            if (getFreeSpellPoints() < 1) {
                return false;
            }
            if (!school.isLevelEnoughForSpell(entity, spell)) {
                return false;
            }
            if (spellData.schools.size() > 1) {
                if (!spellData.schools.contains(school.GUID())) {
                    return false;
                }
            }
            if (hasSpell(spell)) {
                return false;
            }

            return true;
        }

        @Override
        public List<Spell> getLearnedSpells() {
            return ExileDB.Spells()
                .getFilterWrapped(x -> hasSpell(x)).list;
        }

        @Override
        public SpellCastingData getCastingData() {
            return this.spellCastingData;
        }

        @Override
        public SpellsData getSpellsData() {
            return this.spellData;
        }

        public HashMap<UUID, List<UUID>> mobsHit = new HashMap<>();

        @Override
        public void onSpellHitTarget(Entity spellEntity, LivingEntity target) {

            UUID id = target.getUUID();

            UUID key = spellEntity.getUUID();

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

            UUID key = spellEntity.getUUID();

            if (!mobsHit.containsKey(key)) {
                return false;
            }
            return mobsHit.get(key)
                .contains(target.getUUID());
        }

        @Override
        public boolean hasSpell(Spell spell) {
            return this.spellData.spells.contains(spell.GUID());
        }

        @Override
        public List<StatContext> getStatAndContext(LivingEntity en) {

            List<StatContext> ctxs = new ArrayList<>();

            List<ExactStatData> stats = new ArrayList<>();

            ctxs.add(new MiscStatCtx(stats));

            return ctxs;
        }

        @Override
        public String getCapIdForSyncing() {
            return "spells";
        }

    }

}
