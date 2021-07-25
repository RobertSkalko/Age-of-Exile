package com.robertx22.divine_missions_addon.data_gen;

import com.robertx22.divine_missions.database.WorthTypeIds;
import com.robertx22.divine_missions.database.db_types.TaskEntry;
import com.robertx22.divine_missions_addon.types.CompleteDungeonTask;
import com.robertx22.divine_missions_addon.types.CompleteRiftTask;

public class DMTasksAdder {

    public static TaskEntry COMPLETE_DUNGEON = completeDungeon();
    public static TaskEntry COMPLETE_RIFT = rift();

    public static void init() {

    }

    private static TaskEntry rift() {

        TaskEntry en = new TaskEntry();
        en.weight = 500;
        en.id = "complete_rift";
        en.worths.put(WorthTypeIds.DEFAULT, 200);
        en.worths.put(WorthTypeIds.AGE_OF_EXILE, 1000);
        en.seconds = 60 * 10;
        en.min = 1;
        en.max = 1;
        en.task_type = CompleteRiftTask.ID;
        en.addToSerializables();

        return en;
    }

    private static TaskEntry completeDungeon() {

        TaskEntry en = new TaskEntry();
        en.weight = 500;
        en.id = "complete_dungeon";
        en.worths.put(WorthTypeIds.DEFAULT, 1000);
        en.worths.put(WorthTypeIds.AGE_OF_EXILE, 2000);
        en.seconds = 60 * 20;
        en.min = 1;
        en.max = 1;
        en.task_type = CompleteDungeonTask.ID;
        en.addToSerializables();
        return en;
    }
}
