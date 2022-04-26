package com.robertx22.age_of_exile.mixin_methods;

public class NbtItemModelsMethod {
    /*
    static ResourceLocation REFORGE_KEY = SlashRef.id("reforge");

    public static void method(ItemOverrideList overrides) {

        if (!overrides.getOverrides()
            .isEmpty()) {

            ItemOverrideListAccessor listacc = (ItemOverrideListAccessor) overrides.getOverrides();
            ItemOverrideAccessor oacc = (ItemOverrideAccessor) overrides.getOverrides();

            if (oacc.getModel()
                .equals(REFORGE_KEY)) {
                for (Reforge forge : ExileDB.Reforges()
                    .getList()) {
                    listacc.getOverrides()
                        .add(addModelOverride(SlashRef.id("item/reforge/" + forge.GUID()), forge.model_num));
                }

            }
        }
    }

    private static ItemOverride addModelOverride(ResourceLocation loc, int num) {
        return new ItemOverride(loc, ImmutableMap.of(new ResourceLocation("custom_model_data"), (float) num));
    }

     */
}
