package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;

public class ModifyResourceEffect extends EffectEvent {

    public static String ID = "on_modify_resource";

    @Override
    public String GUID() {
        return ID;
    }

    public ResourcesData.Context ctx;

    public ModifyResourceEffect(ResourcesData.Context ctx) {
        super(ctx.amount, ctx.source, ctx.target);
        this.ctx = ctx;
    }

    @Override
    protected void activate() {
        // already done elsewhere
    }

}
