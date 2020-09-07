package com.robertx22.age_of_exile.database.data.spells.activated_on;

import com.robertx22.age_of_exile.database.data.spells.contexts.SpellCtx;

import java.util.Arrays;
import java.util.HashMap;

public class ActivatedOnCast extends ActivatedOn {

    public ActivatedOnCast() {
        super(Arrays.asList());
    }

    @Override
    public boolean canActivate(SpellCtx ctx, HashMap<String, Object> map) {
        return true;
    }

    @Override
    public String GUID() {
        return "on_cast";
    }

}
