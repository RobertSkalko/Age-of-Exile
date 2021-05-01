package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.ModifyResourceEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellHealEffect;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityUnitPacket;
import com.robertx22.library_of_exile.main.Packets;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

@Storable
public class ResourcesData {

    public ResourcesData() {

    }

    public static class Context {

        public String spell = null;

        public UnitData sourceData;
        public LivingEntity source;

        public UnitData targetData;
        public LivingEntity target;

        public ResourceType type;
        public float amount;
        public Use use;

        public boolean statsCalculated = false;

        public Context setSpell(String id) {
            this.spell = spell;
            return this;
        }

        public Context(UnitData data, LivingEntity entity, ResourceType type, float amount, Use use) {
            this.targetData = data;
            this.target = entity;
            this.sourceData = data;
            this.source = entity;
            this.type = type;
            this.amount = amount;
            this.use = use;
            calculateStats();
        }

        public Context(LivingEntity caster, LivingEntity target, ResourceType type, float amount, Use use) {
            this.targetData = Load.Unit(target);
            this.target = target;
            this.sourceData = Load.Unit(caster);
            this.source = caster;
            this.type = type;
            this.amount = amount;
            this.use = use;

            calculateStats();
        }

        public Context(LivingEntity caster, LivingEntity target, UnitData casterData, UnitData targetData, ResourceType type,
                       float amount, Use use, Spell spell) {
            this.targetData = targetData;
            this.target = target;
            this.sourceData = casterData;
            this.source = caster;
            this.type = type;
            this.amount = amount;
            this.use = use;
            calculateStats();
        }

        private void calculateStats() {
            if (!statsCalculated) {
                new ModifyResourceEffect(this).Activate();
            }
        }

    }

    public enum Use {
        SPEND,
        RESTORE
    }

    @Store
    private float mana = 0;
    @Store
    private float blood = 0;

    @Store
    public AllShieldsData shields = new AllShieldsData();

    public float getMana() {
        return mana;
    }

    public float getBlood() {
        return blood;
    }

    public float getShield() {
        return shields.getTotalShields();
    }

    public float getModifiedValue(Context ctx) {
        if (ctx.use == Use.RESTORE) {
            return get(ctx) + ctx.amount;
        } else {
            return get(ctx) - ctx.amount;
        }

    }

    public float get(LivingEntity en, ResourceType type) {
        if (type == ResourceType.MANA) {
            return mana;
        }
        if (type == ResourceType.SHIELD) {
            return getShield();
        } else if (type == ResourceType.BLOOD) {
            return blood;
        } else if (type == ResourceType.HEALTH) {
            return HealthUtils.getCurrentHealth(en);
        }
        return 0;

    }

    public float getMax(LivingEntity en, ResourceType type) {
        UnitData data = Load.Unit(en);

        if (type == ResourceType.SHIELD) {
            return data.getUnit()
                .healthData()
                .getAverageValue();
        }
        if (type == ResourceType.MANA) {
            return data.getUnit()
                .manaData()
                .getAverageValue();

        } else if (type == ResourceType.BLOOD) {
            return data.getUnit()
                .bloodData()
                .getAverageValue();
        } else if (type == ResourceType.HEALTH) {
            return HealthUtils.getMaxHealth(en);
        }
        return 0;

    }

    private float get(Context ctx) {
        return get(ctx.target, ctx.type);
    }

    private void modifyBy(Context ctx) {

        if (ctx.amount == 0) {
            return;
        }

        if (ctx.type == ResourceType.MANA) {
            mana = MathHelper.clamp(getModifiedValue(ctx), 0, ctx.targetData.getUnit()
                .manaData()
                .getAverageValue() * Load.spells(ctx.source)
                .getReservedManaMulti());
            sync(ctx);

        } else if (ctx.type == ResourceType.BLOOD) {

            blood = MathHelper.clamp(getModifiedValue(ctx), 0, ctx.targetData.getUnit()
                .bloodData()
                .getAverageValue() * Load.spells(ctx.source)
                .getReservedManaMulti());
            sync(ctx);
        } else if (ctx.type == ResourceType.HEALTH) {
            if (ctx.use == Use.RESTORE) {
                heal(ctx);
            } else {
                ctx.target.setHealth(getModifiedValue(ctx));
            }
        }
    }

    private void sync(Context ctx) {
        if (ctx.target instanceof ServerPlayerEntity) {
            Packets.sendToClient((PlayerEntity) ctx.target, new EntityUnitPacket(ctx.target));
        }
    }

    private void heal(Context ctx) {
        if (ctx.target.isAlive()) {

            if (ctx.spell != null) {
                SpellHealEffect effect = new SpellHealEffect(ctx);
                effect.Activate();

            } else {
                HealEffect effect = new HealEffect(ctx);
                effect.Activate();
            }
        }
    }

    public boolean hasEnough(Context ctx) {
        if (ctx.amount <= 0) {
            return true;
        }

        return get(ctx) >= ctx.amount;
    }

    public void modify(Context ctx) {
        modifyBy(ctx);
    }

}
