package com.robertx22.age_of_exile.dimension.rift;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class RiftData {

    @Store
    public int wave_num = 0;
    @Store
    public int max_waves = 5;
    @Store
    public int ticks_till_next_wave = 5;

}
