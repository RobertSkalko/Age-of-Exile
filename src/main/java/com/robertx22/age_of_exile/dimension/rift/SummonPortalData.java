package com.robertx22.age_of_exile.dimension.rift;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class SummonPortalData {

    @Store
    public int summons_left = 3;
}
