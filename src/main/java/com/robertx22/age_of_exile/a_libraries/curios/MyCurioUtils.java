package com.robertx22.age_of_exile.a_libraries.curios;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.component.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MyCurioUtils {

    static List<String> slots = Arrays.asList("ring", "necklace");

    public static List<ICurioStacksHandler> getHandlers(PlayerEntity player) {

        List<ICurioStacksHandler> list = new ArrayList<>();

        ICuriosItemHandler handler = CuriosApi.getCuriosHelper()

            .getCuriosHandler(player)
            .get();

        for (String slot : slots) {

            Optional<ICurioStacksHandler> sh = handler.getStacksHandler(slot);

            if (sh.isPresent()) {
                list.add(sh.get());
            }

        }
        return list;
    }

    public static List<ItemStack> getAllSlots(PlayerEntity player) {

        List<ItemStack> list = new ArrayList<>();

        getHandlers(player).forEach(x -> {
            for (int i = 0; i < x
                .getSlots(); i++) {

                ItemStack stack = x
                    .getStacks()
                    .getStack(i);
                if (!stack.isEmpty()) {
                    list.add(stack);
                }
            }
        });

        return list;

    }

}
