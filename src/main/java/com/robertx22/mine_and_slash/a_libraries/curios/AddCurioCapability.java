package com.robertx22.mine_and_slash.a_libraries.curios;

import com.robertx22.mine_and_slash.a_libraries.curios.interfaces.ICuriosType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.capability.CuriosCapability;
import top.theillusivec4.curios.api.capability.ICurio;




@Mod.EventBusSubscriber
public class AddCurioCapability {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {

        if (evt.getObject().getItem() instanceof ICuriosType) {

            ICuriosType type = (ICuriosType) evt.getObject().getItem();

            ICurio curio = new ICurio() {

                @Override
                public boolean canRightClickEquip() {
                    return type.rightClickEquip();
                }
            };

            evt.addCapability(CuriosCapability.ID_ITEM, new ICapabilityProvider() {
                private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                                                         Direction side) {

                    return CuriosCapability.ITEM.orEmpty(cap, curioOpt);

                }
            });
        }
    }

}
