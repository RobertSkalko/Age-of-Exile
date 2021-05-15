package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public enum BarGuiType {
    NONE {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            return 0;
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            return 0;
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            return Ref.id("empty");
        }
    },
    EXP {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            return data.getExp();
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            return data.getExpRequiredForLevelUp();
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            return Ref.id("textures/gui/overlay/exp.png");
        }

        @Override
        public String getText(EntityCap.UnitData data, PlayerEntity en) {
            return "Level " + data.getLevel() + " " + (int) (getMulti(data, en) * 100) + "%";
        }
    },

    MANA {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            if (data.getUnit()
                .isBloodMage()) {
                return data.getResources()
                    .getBlood();
            }
            return data.getResources()
                .getMana();
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            if (data.getUnit()
                .isBloodMage()) {
                return data.getUnit()
                    .bloodData()
                    .getAverageValue();
            }
            return data.getUnit()
                .manaData()
                .getAverageValue();
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            if (data.getUnit()
                .getCalculatedStat(BloodUser.getInstance())
                .getAverageValue() > 0) {
                return Ref.id("textures/gui/overlay/blood.png");
            } else {
                return Ref.id("textures/gui/overlay/mana.png");
            }
        }
    },
    HEALTH {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            return HealthUtils.getCurrentHealth(en);
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            return HealthUtils.getMaxHealth(en);
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            return Ref.id("textures/gui/overlay/health.png");
        }

    },
    SHIELD {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            return data.getResources()
                .getShield();
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            return HealthUtils.getMaxHealth(en);
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            return Ref.id("textures/gui/overlay/shield.png");
        }

        @Override
        public boolean shouldRender(EntityCap.UnitData data, PlayerEntity en) {
            return getCurrent(data, en) > 0;
        }

    },

    BLOCK {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            return data.getResources()
                .getBlock();
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            return 100F;
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            return Ref.id("textures/gui/overlay/block.png");
        }

        @Override
        public boolean shouldRender(EntityCap.UnitData data, PlayerEntity en) {
            return getCurrent(data, en) < getMax(data, en);
        }

    },
    HUNGER {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            return en.getHungerManager()
                .getFoodLevel();
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            return 20; // ?
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            return Ref.id("textures/gui/overlay/hunger.png");
        }

        @Override
        public String getText(EntityCap.UnitData data, PlayerEntity en) {
            return "H: " + (int) getCurrent(data, en) + " S: " + (int) en.getHungerManager()
                .getSaturationLevel();
        }

    },
    AIR {
        @Override
        public float getCurrent(EntityCap.UnitData data, PlayerEntity en) {
            return en.getAir();
        }

        @Override
        public float getMax(EntityCap.UnitData data, PlayerEntity en) {
            return en.getMaxAir();
        }

        @Override
        public Identifier getTexture(EntityCap.UnitData data, PlayerEntity en) {
            return Ref.id("textures/gui/overlay/air.png");
        }

        @Override
        public boolean shouldRender(EntityCap.UnitData data, PlayerEntity en) {
            return getCurrent(data, en) < getMax(data, en);
        }
    };

    public float getMulti(EntityCap.UnitData data, PlayerEntity en) {
        return Math.min(getCurrent(data, en) / getMax(data, en), 1);
    }

    public String getText(EntityCap.UnitData data, PlayerEntity en) {
        return (int) getCurrent(data, en) + "/" + (int) getMax(data, en);
    }

    public Identifier getIcon(EntityCap.UnitData data, PlayerEntity en) {
        return new Identifier(getTexture(data, en).toString()
            .replaceAll(".png", "_icon.png"));
    }

    public boolean shouldRender(EntityCap.UnitData data, PlayerEntity en) {
        return true;
    }

    public abstract float getCurrent(EntityCap.UnitData data, PlayerEntity en);

    public abstract float getMax(EntityCap.UnitData data, PlayerEntity en);

    public abstract Identifier getTexture(EntityCap.UnitData data, PlayerEntity en);

}
