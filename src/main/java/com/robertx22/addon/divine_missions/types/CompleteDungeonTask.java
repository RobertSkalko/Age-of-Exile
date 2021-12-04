package com.robertx22.addon.divine_missions.types;

import com.robertx22.divine_missions.database.db_types.TaskType;
import com.robertx22.divine_missions.main.DivineMissions;
import com.robertx22.divine_missions.saving.TaskData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TextFormatting;

public class CompleteDungeonTask extends TaskType {
    public static String ID = "complete_dungeon";

    public CompleteDungeonTask() {
        super(ID);
    }

    @Override
    public IFormattableTextComponent getTranslatable(PlayerEntity playerEntity, TaskData taskData) {
        return DivineMissions.ofTranslation("complete_dungeon")
            .withStyle(TextFormatting.DARK_PURPLE);
    }
}
