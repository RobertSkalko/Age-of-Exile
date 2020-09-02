package com.robertx22.age_of_exile.database.data.rarities.containers;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.BaseRaritiesContainer;
import com.robertx22.age_of_exile.database.data.rarities.IGearRarity;
import com.robertx22.age_of_exile.database.data.rarities.RarityTypeEnum;
import com.robertx22.age_of_exile.database.data.rarities.serialization.GearRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

public class GearRarities extends BaseRaritiesContainer<IGearRarity> {

    public GearRarities() {
        super();
        add(new CommonGear());
        add(new MagicalGear());
        add(new RareGear());
        add(new RelicGear());
        add(new UniqueGear());

        this.onInit();
    }

    public static class CommonGear extends GearRarity {
        public CommonGear() {
            this.rank = IRarity.Common;
            this.unidentified_chance = 0;
            this.stat_req_multi = 0.5F;
            this.spawn_durability_hit = new MinMax(60, 80);
            this.affixes = new GearRarity.Part(0, 0, 0);
            this.sockets = new Part(0, 3, 15);
            this.weight = 5000;
            this.item_tier_power = 1;
            this.item_value_multi = 1;
            this.setCommonFields();
        }
    }

    public class MagicalGear extends GearRarity {
        private MagicalGear() {
            this.unidentified_chance = 0;
            this.stat_req_multi = 0.75F;
            this.spawn_durability_hit = new MinMax(60, 80);
            this.affixes = new GearRarity.Part(1, 2, 15);
            this.sockets = new Part(0, 2, 15);
            this.weight = 2500;
            this.item_tier_power = 1.25F;
            this.item_value_multi = 1.25F;
            this.setMagicalFields();
        }
    }

    public class RareGear extends GearRarity {
        private RareGear() {
            this.unidentified_chance = 20;
            this.stat_req_multi = 1;
            this.spawn_durability_hit = new MinMax(60, 80);
            this.affixes = new GearRarity.Part(3, 6, 15);
            this.sockets = new Part(0, 0, 0);
            this.weight = 200;
            this.item_tier_power = 1.5F;
            this.item_value_multi = 1.5F;
            this.setRareFields();
        }
    }

    public class RelicGear extends GearRarity {
        private RelicGear() {
            this.unidentified_chance = 20;
            this.stat_req_multi = 1;
            this.spawn_durability_hit = new MinMax(60, 80);
            this.affixes = new GearRarity.Part(0, 0, 0);
            this.sockets = new Part(3, 5, 20);
            this.weight = 200;
            this.item_tier_power = 1.5F;
            this.item_value_multi = 1.5F;
            this.setRelicFields();
        }
    }

    public class UniqueGear extends GearRarity {
        private UniqueGear() {
            this.unidentified_chance = 25;
            this.stat_req_multi = 1;
            this.spawn_durability_hit = new MinMax(60, 80);
            this.affixes = new GearRarity.Part(0, 0, 0);
            this.sockets = new Part(0, 0, 0);
            this.weight = 0;
            this.item_tier_power = 2;
            this.item_value_multi = 2;
            this.setUniqueFields();
        }
    }

    @Override
    public RarityTypeEnum getType() {
        return RarityTypeEnum.GEAR;
    }

}
