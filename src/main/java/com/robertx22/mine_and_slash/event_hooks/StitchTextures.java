package com.robertx22.mine_and_slash.event_hooks;

import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.vanilla_mc.items.gearitems.offhands.NormalShield;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import net.minecraft.client.texture.SpriteAtlasTexture;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class StitchTextures {

    @SubscribeEvent
    public static void onStitch(TextureStitchEvent.Pre event) {
        if (event.getMap()
            .getId()
            .equals(SpriteAtlasTexture.BLOCK_ATLAS_TEX)) {

            SlashRegistry.GearTypes()
                .getList()
                .stream()
                .filter(x -> x.getTags()
                    .contains(BaseGearType.SlotTag.Shield))
                .forEach(x -> event.addSprite(NormalShield.getResource(x)));

        }
    }

}
