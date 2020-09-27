package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.repair_kits.FillRepairKitRecipe;
import com.robertx22.age_of_exile.repair_kits.RepairWithKitRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipeSerializers {

    public SpecialRecipeSerializer<FillRepairKitRecipe> FILL_REPAIR_KIT = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Ref.MODID, "repair_gear"), new SpecialRecipeSerializer<FillRepairKitRecipe>(t -> new FillRepairKitRecipe(t)));
    public SpecialRecipeSerializer<RepairWithKitRecipe> REPAIR_WITH_KIT = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(Ref.MODID, "repair_with_kit"), new SpecialRecipeSerializer<RepairWithKitRecipe>(t -> new RepairWithKitRecipe(t)));

}
