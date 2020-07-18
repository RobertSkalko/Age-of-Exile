package com.robertx22.mine_and_slash.a_libraries.curios;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import top.theillusivec4.curios.api.CuriosApi;

import top.theillusivec4.curios.api.type.component.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyCurioUtils {

    public static List<ItemStack> getAllSlots(PlayerEntity player) {


        List<ItemStack> list = new ArrayList<>();

        return null; // TODO
/*
        ICuriosItemHandler handler = CuriosApi.getCuriosHelper().getCuriosHandler(player).orElse(null);
        if (handler != null) {
            Map<String, ICurioStacksHandler> curioMap = handler.getCurios();
            for (ICurioStacksHandler stacksHandler : curioMap.values()) {
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                for (int i = 0; i < stackHandler.size(); i++) {
                    list.add(stackHandler.getStack(i));
                }}}


        return list;


 */
    }


}
