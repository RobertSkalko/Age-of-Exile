package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
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

    private enum Use {
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

    public float getModifiedValue(LivingEntity en, ResourceType type, Use use, float amount) {
        if (use == Use.RESTORE) {
            return get(en, type) + amount;
        } else {
            return get(en, type) - amount;
        }

    }

    public float get(LivingEntity en, ResourceType type) {
        if (type == ResourceType.mana) {
            return mana;
        }
        if (type == ResourceType.shield) {
            return getShield();
        } else if (type == ResourceType.blood) {
            return blood;
        } else if (type == ResourceType.health) {
            return HealthUtils.getCurrentHealth(en);
        }
        return 0;

    }

    public float getMax(LivingEntity en, ResourceType type) {
        UnitData data = Load.Unit(en);

        if (type == ResourceType.shield) {
            return data.getUnit()
                .healthData()
                .getAverageValue();
        }
        if (type == ResourceType.mana) {
            return data.getUnit()
                .manaData()
                .getAverageValue();

        } else if (type == ResourceType.blood) {
            return data.getUnit()
                .bloodData()
                .getAverageValue();
        } else if (type == ResourceType.health) {
            return HealthUtils.getMaxHealth(en);
        }
        return 0;

    }

    public void spend(LivingEntity en, ResourceType type, float amount) {
        modify(en, Use.SPEND, type, amount);
    }

    public void restore(LivingEntity en, ResourceType type, float amount) {
        modify(en, Use.RESTORE, type, amount);
    }

    public void modify(LivingEntity en, Use use, ResourceType type, float amount) {
        if (amount == 0) {
            return;
        }
        if (type == ResourceType.mana) {
            mana = getModifiedValue(en, type, use, amount);
        } else if (type == ResourceType.blood) {
            blood = getModifiedValue(en, type, use, amount);
        } else if (type == ResourceType.health) {
            if (use == Use.RESTORE) {
                HealthUtils.heal(en, amount);
            }
        }
        cap(en, type);
        sync(en);
    }

    private void cap(LivingEntity en, ResourceType type) {
        if (type == ResourceType.mana) {
            mana = MathHelper.clamp(mana, 0, Load.Unit(en)
                .getMaximumResource(type));
        } else if (type == ResourceType.blood) {
            blood = MathHelper.clamp(blood, 0, Load.Unit(en)
                .getMaximumResource(type));
        }
    }

    private void sync(LivingEntity en) {
        if (en instanceof ServerPlayerEntity) {
            Packets.sendToClient((PlayerEntity) en, new EntityUnitPacket(en));
        }
    }

    public boolean hasEnough(SpendResourceEvent ctx) {
        if (ctx.data.getNumber() <= 0) {
            return true;
        }
        return get(ctx.target, ctx.data.getResourceType()) >= ctx.data.getNumber();
    }

}
