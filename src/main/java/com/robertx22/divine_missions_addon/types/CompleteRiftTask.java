package com.robertx22.divine_missions_addon.types;

import com.robertx22.divine_missions.database.db_types.TaskType;
import com.robertx22.divine_missions.main.DivineMissions;
import com.robertx22.divine_missions.saving.TaskData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;

public class CompleteRiftTask extends TaskType {
    public static String ID = "complete_rift";

    public CompleteRiftTask() {
        super(ID);
    }

    @Override
    public MutableText getTranslatable(PlayerEntity playerEntity, TaskData taskData) {
        return DivineMissions.ofTranslation("complete_rift")
            .formatted(Formatting.DARK_PURPLE);
    }
}
