package com.robertx22.addon.divine_missions.types;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.divine_missions.database.condition_types.ConditionData;
import com.robertx22.divine_missions.database.db_types.ConditionType;
import net.minecraft.entity.player.PlayerEntity;

public class AoeLevelCondition extends ConditionType {
    public static String ID = "aoe_level";

    public AoeLevelCondition() {
        super(ID);
    }

    public static ConditionData of(int min, int max) {
        ConditionData data = new ConditionData();
        data.id = ID;

        data.map.put("min", min + "");
        data.map.put("max", max + "");

        return data;
    }

    @Override
    public boolean isAllowed(PlayerEntity player, ConditionData data) {

        int min = Integer.parseInt(data.map.get("min"));
        int max = Integer.parseInt(data.map.get("max"));

        int lvl = Load.Unit(player)
            .getLevel();

        if (lvl >= min) {
            if (max >= lvl) {
                return true;
            }
        }

        return false;
    }
}
