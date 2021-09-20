package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public enum BarGuiType {
    NONE {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {
            return 0;
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            return 0;
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            return SlashRef.id("empty");
        }
    },
    EXP {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {
            return data.getExp();
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            return data.getExpRequiredForLevelUp();
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            return SlashRef.id("textures/gui/overlay/exp.png");
        }

        @Override
        public String getText(EntityData data, PlayerEntity en) {
            return "Level " + data.getLevel() + " " + (int) (getMulti(data, en) * 100) + "%";
        }
    },
    ENERGY {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {

            return data.getResources()
                .getEnergy();
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            return data.getUnit()
                .energyData()
                .getValue();
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            return SlashRef.id("textures/gui/overlay/energy.png");
        }
    },

    MANA {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {
            if (data.getUnit()
                .isBloodMage()) {
                return data.getResources()
                    .getBlood();
            }
            return data.getResources()
                .getMana();
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            if (data.getUnit()
                .isBloodMage()) {
                return data.getUnit()
                    .bloodData()
                    .getValue();
            }
            return data.getUnit()
                .manaData()
                .getValue();
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            if (data.getUnit()
                .getCalculatedStat(BloodUser.getInstance())
                .getValue() > 0) {
                return SlashRef.id("textures/gui/overlay/blood.png");
            } else {
                return SlashRef.id("textures/gui/overlay/mana.png");
            }
        }
    },
    HEALTH {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {
            return HealthUtils.getCurrentHealth(en);
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            return HealthUtils.getMaxHealth(en);
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            return SlashRef.id("textures/gui/overlay/health.png");
        }

    },
    SHIELD {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {
            return data.getResources()
                .getShield();
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            return HealthUtils.getMaxHealth(en);
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            return SlashRef.id("textures/gui/overlay/shield.png");
        }

        @Override
        public boolean shouldRender(EntityData data, PlayerEntity en) {
            return getCurrent(data, en) > 0;
        }

    },

    HUNGER {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {
            return en.getFoodData()
                .getFoodLevel();
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            return 20; // ?
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            return SlashRef.id("textures/gui/overlay/hunger.png");
        }

        @Override
        public String getText(EntityData data, PlayerEntity en) {
            return "H: " + (int) getCurrent(data, en) + " S: " + (int) en.getFoodData()
                .getSaturationLevel();
        }

    },
    AIR {
        @Override
        public float getCurrent(EntityData data, PlayerEntity en) {
            return en.getAirSupply();
        }

        @Override
        public float getMax(EntityData data, PlayerEntity en) {
            return en.getMaxAirSupply();
        }

        @Override
        public ResourceLocation getTexture(EntityData data, PlayerEntity en) {
            return SlashRef.id("textures/gui/overlay/air.png");
        }

        @Override
        public boolean shouldRender(EntityData data, PlayerEntity en) {
            return getCurrent(data, en) < getMax(data, en);
        }
    };

    public float getMulti(EntityData data, PlayerEntity en) {
        return Math.min(getCurrent(data, en) / getMax(data, en), 1);
    }

    public String getText(EntityData data, PlayerEntity en) {
        return (int) getCurrent(data, en) + "/" + (int) getMax(data, en);
    }

    public ResourceLocation getIcon(EntityData data, PlayerEntity en) {
        return new ResourceLocation(getTexture(data, en).toString()
            .replaceAll(".png", "_icon.png"));
    }

    public boolean shouldRender(EntityData data, PlayerEntity en) {
        return true;
    }

    public abstract float getCurrent(EntityData data, PlayerEntity en);

    public abstract float getMax(EntityData data, PlayerEntity en);

    public abstract ResourceLocation getTexture(EntityData data, PlayerEntity en);

}
