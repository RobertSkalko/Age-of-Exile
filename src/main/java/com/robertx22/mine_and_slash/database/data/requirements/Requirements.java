package com.robertx22.mine_and_slash.database.data.requirements;

import com.google.gson.JsonObject;
import com.robertx22.mine_and_slash.data_generation.JsonUtils;
import com.robertx22.mine_and_slash.database.data.requirements.bases.BaseRequirement;
import com.robertx22.mine_and_slash.database.data.requirements.bases.GearRequestedFor;
import com.robertx22.mine_and_slash.event_hooks.data_gen.ISerializablePart;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.mine_and_slash.uncommon.wrappers.SText;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.text.MutableText;

public class Requirements implements ISerializablePart<Requirements>, ITooltipList {

    public static Requirements EMPTY = new Requirements((BaseRequirement) null);

    public List<BaseRequirement> requirements = new ArrayList<>();

    public Requirements(BaseRequirement req) {
        this.requirements.add(req);
    }

    public Requirements(BaseRequirement... req1) {
        for (BaseRequirement req : req1) {
            this.requirements.add(req);
        }
    }

    public Requirements(List<BaseRequirement> reqs) {
        this.requirements.addAll(reqs);
    }

    public boolean satisfiesAllRequirements(GearRequestedFor requested) {

        for (BaseRequirement req : requirements) {
            if (req.meetsRequierment(requested) == false) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String getJsonID() {
        return "requirements";
    }

    @Override
    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.add("list", JsonUtils.partListToJsonArray(requirements));

        return json;
    }

    public static List<ISerializablePart> possible = Arrays.asList(
        new SlotRequirement());

    @Override
    public Requirements fromJson(JsonObject json) {
        try {
            Requirements newobj = new Requirements(JsonUtils.jsonArrayToPartList(json.getAsJsonArray("list"), possible)
                .stream()
                .map(x -> (BaseRequirement) x)
                .collect(Collectors.toList()));
            return newobj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<MutableText> GetTooltipString(TooltipInfo info) {
        List<MutableText> list = new ArrayList<>();
        this.requirements.forEach(x -> {
            list.add(new SText(""));
            list.addAll(x.GetTooltipString(info));
        });
        return list;
    }
}
