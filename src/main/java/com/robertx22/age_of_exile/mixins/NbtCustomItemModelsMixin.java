package com.robertx22.age_of_exile.mixins;

import net.minecraft.client.renderer.model.ItemOverrideList;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemOverrideList.class)
public class NbtCustomItemModelsMixin {
/*
    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    private void getTooltip(CallbackInfo ci) {
        ItemOverrideList model = (ItemOverrideList) (Object) this;
        try {
            NbtItemModelsMethod.method(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 */

}
