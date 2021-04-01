package com.robertx22.age_of_exile.mmorpg.init;

import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.mmorpg.event_registers.Client;
import com.robertx22.age_of_exile.mmorpg.registers.client.ClientSetup;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.mmorpg.registers.client.ParticleFactoryRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ClientSetup.setup();

        KeybindsRegister.register();
        Client.register();

        ParticleFactoryRegister.register();

        ModRegistry.GEAR_ITEMS.BOWS.values()
            .forEach(x -> {
                FabricModelPredicateProviderRegistry.register(x, new Identifier("pulling"), (itemStack, clientWorld, livingEntity) -> {
                    return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
                });

                FabricModelPredicateProviderRegistry.register(x, new Identifier("pull"), (itemStack, clientWorld, livingEntity) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    } else {
                        return livingEntity.getActiveItem() != itemStack ? 0.0F : (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
                    }
                });
            });

    }
}
