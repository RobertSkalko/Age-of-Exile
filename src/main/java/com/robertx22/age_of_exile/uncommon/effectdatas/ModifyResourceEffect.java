package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.saveclasses.unit.ModifyResourceContext;

public class ModifyResourceEffect extends EffectEvent {

    public static String ID = "on_modify_resource";

    @Override
    public String GUID() {
        return ID;
    }

    public ModifyResourceContext ctx;

    public ModifyResourceEffect(ModifyResourceContext ctx) {
        super(ctx.amount, ctx.source, ctx.target);
        this.ctx = ctx;
    }

    @Override
    protected void activate() {
        // already done elsewhere
    }

}
