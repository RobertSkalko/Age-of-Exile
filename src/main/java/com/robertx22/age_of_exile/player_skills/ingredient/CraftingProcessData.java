package com.robertx22.age_of_exile.player_skills.ingredient;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;

import java.util.ArrayList;
import java.util.List;

@Storable
public class CraftingProcessData {

    @Store
    public String prof = "";

    @Store
    public List<IngredientData> ingredients = new ArrayList<>();

}
