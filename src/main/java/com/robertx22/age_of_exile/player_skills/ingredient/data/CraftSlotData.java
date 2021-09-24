package com.robertx22.age_of_exile.player_skills.ingredient.data;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

@Storable
public class CraftSlotData {

    @Store
    public boolean calc = false;
    @Store
    public int place = 0;
    @Store
    public float multi = 1;
    @Store
    public IngredientData ing;

}
