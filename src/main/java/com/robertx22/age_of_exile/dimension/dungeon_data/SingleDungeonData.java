package com.robertx22.age_of_exile.dimension.dungeon_data;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class SingleDungeonData {
    public static SingleDungeonData EMPTY = new SingleDungeonData();

    @Store
    public DungeonData data = new DungeonData();
    @Store
    public QuestProgression quest = new QuestProgression();
    @Store
    public String ownerUUID = "";

    @Store
    public DungeonPopulateData pop = new DungeonPopulateData();

    public SingleDungeonData(DungeonData data, QuestProgression quest, String ownerUUID) {
        this.data = data;
        this.quest = quest;
        this.ownerUUID = ownerUUID;
    }

    public SingleDungeonData() {
    }
}
