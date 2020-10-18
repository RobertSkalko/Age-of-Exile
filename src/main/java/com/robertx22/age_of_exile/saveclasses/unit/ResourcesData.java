package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.ModifyResourceEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellHealEffect;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityUnitPacket;
import com.robertx22.library_of_exile.main.Packets;
import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

@Storable
public class ResourcesData {

    public ResourcesData() {

    }

    public static class Context {

        public Spell spell;

        public UnitData sourceData;
        public LivingEntity source;

        public UnitData targetData;
        public LivingEntity target;

        public Type type;
        public float amount;
        public Use use;

        public boolean statsCalculated = false;

        public Context(UnitData data, LivingEntity entity, Type type, float amount, Use use, Spell spell) {
            this.targetData = data;
            this.target = entity;
            this.sourceData = data;
            this.source = entity;
            this.type = type;
            this.amount = amount;
            this.use = use;
            this.spell = spell;
            calculateStats();
        }

        public Context(UnitData data, LivingEntity entity, Type type, float amount, Use use) {
            this.targetData = data;
            this.target = entity;
            this.sourceData = data;
            this.source = entity;
            this.type = type;
            this.amount = amount;
            this.use = use;
            calculateStats();
        }

        public Context(LivingEntity caster, LivingEntity target, Type type, float amount, Use use, Spell spell) {
            this.targetData = Load.Unit(target);
            this.target = target;
            this.sourceData = Load.Unit(caster);
            this.source = caster;
            this.type = type;
            this.amount = amount;
            this.use = use;
            this.spell = spell;

            calculateStats();
        }

        public Context(LivingEntity caster, LivingEntity target, UnitData casterData, UnitData targetData, Type type,
                       float amount, Use use, Spell spell) {
            this.targetData = targetData;
            this.target = target;
            this.sourceData = casterData;
            this.source = caster;
            this.type = type;
            this.amount = amount;
            this.use = use;
            this.spell = spell;
            calculateStats();
        }

        private void calculateStats() {
            if (!statsCalculated) {
                new ModifyResourceEffect(this).Activate();
            }
        }

    }

    public enum Type {
        HEALTH,
        MANA,
        MAGIC_SHIELD

    }

    public enum Use {
        SPEND,
        RESTORE
    }

    @Store
    private float mana = 0;
    @Store
    private float magicShield = 0;

    public float getMana() {
        return mana;
    }

    public float getMagicShield() {
        return magicShield;
    }

    public float getHealth(UnitData data, LivingEntity entity) {
        return data.getUnit()
            .health()
            .CurrentValue(entity, data.getUnit());
    }

    public float getModifiedValue(Context ctx) {
        if (ctx.use == Use.RESTORE) {
            return get(ctx) + ctx.amount;
        } else {
            return get(ctx) - ctx.amount;
        }

    }

    private float get(Context ctx) {
        if (ctx.type == Type.MANA) {
            return mana;
        } else if (ctx.type == Type.MAGIC_SHIELD) {
            return magicShield;
        } else if (ctx.type == Type.HEALTH) {
            return ctx.targetData.getUnit()
                .health()
                .CurrentValue(ctx.target, ctx.targetData.getUnit());
        }
        return 0;

    }

    private void modifyBy(Context ctx) {

        if (ctx.type == Type.MANA) {
            mana = MathHelper.clamp(getModifiedValue(ctx), 0, ctx.targetData.getUnit()
                .manaData()
                .getAverageValue());
            sync(ctx);
        } else if (ctx.type == Type.MAGIC_SHIELD) {
            magicShield = MathHelper.clamp(getModifiedValue(ctx), 0, ctx.targetData.getUnit()
                .magicShieldData()
                .getAverageValue());
            sync(ctx);
        } else if (ctx.type == Type.HEALTH) {
            if (ctx.use == Use.RESTORE) {
                heal(ctx);
            } else {
                ctx.target.setHealth(getModifiedValue(ctx));
            }
        }
    }

    private void sync(Context ctx) {
        if (ctx.target instanceof PlayerEntity) {
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
