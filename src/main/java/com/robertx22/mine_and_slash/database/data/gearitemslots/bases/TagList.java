package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType.SlotTag;

public class TagList {

    public List<String> tags;

    public boolean contains(String tag) {
        return tags.contains(tag);
    }

    public boolean contains(SlotTag tag) {
        return tags.contains(tag.name());
    }

    public BaseGearType.SlotFamily getFamily() {
        Optional<SlotTag> opt = tags.stream()
            .filter(f -> Arrays.stream(SlotTag.values())
                .anyMatch(m -> m.name()
                    .equals(f)))
            .map(x -> SlotTag.valueOf(x))
            .filter(t -> t.family != BaseGearType.SlotFamily.NONE)
            .findFirst();

        if (!opt.isPresent()) {
            System.out.println("gear type doesn't have a slot family tag!!!");
            return null;
        } else {
            return opt.get().family;
        }

    }

    public TagList(List<SlotTag> tags) {
        this.tags = tags.stream()
            .map(x -> x.name())
            .collect(Collectors.toList());
    }

    public TagList(SlotTag... tags) {
        this.tags = new ArrayList<>();
        for (SlotTag tag : tags) {
            this.tags.add(tag.name());
        }
    }

}
