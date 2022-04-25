package com.robertx22.age_of_exile.mixins;

import net.minecraft.client.renderer.model.BlockModel;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockModel.class)
public class NbtCustomBlockModelsMixin {

    // todo

    /*

    @Inject(method = {"<init>"}, at = {@At("RETURN")})
    private void getTooltip(ResourceLocation key, List<BlockPart> list, Map<String, Either<RenderMaterial, String>> map, boolean p_i230056_4_, BlockModel.GuiLight p_i230056_5_, ItemCameraTransforms p_i230056_6_, List<ItemOverride> overrides, CallbackInfo ci) {

        if (key.equals(ForgeRegistries.ITEMS.getKey(SlashItems.RUNEWORD.get()))) {
            for (RuneWord word : ExileDB.RuneWords()
                .getList()) {
                overrides.add(addModelOverride(SlashRef.id("runewords/" + word.GUID()), word.model_num));
            }

        }
    }

    private static ItemOverride addModelOverride(ResourceLocation loc, int num) {

    }

     */
}
