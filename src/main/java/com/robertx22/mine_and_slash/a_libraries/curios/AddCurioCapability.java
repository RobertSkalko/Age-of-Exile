package com.robertx22.mine_and_slash.a_libraries.curios;

import com.robertx22.mine_and_slash.a_libraries.curios.interfaces.ICuriosType;
import com.sun.org.apache.xpath.internal.operations.Mod;
import nerdhub.cardinal.components.api.event.ItemComponentCallbackV2;
import net.minecraft.item.Item;

import net.minecraft.util.registry.Registry;
import top.theillusivec4.curios.api.CuriosComponent;

import top.theillusivec4.curios.api.type.component.ICurio;

import java.util.List;
import java.util.stream.Collectors;


public class AddCurioCapability {


    public static void addComponents() {

        List<Item> items = Registry.ITEM.stream().filter(x-> x instanceof  ICuriosType).collect(Collectors.toList());

        items.forEach(x-> {
            ICuriosType type = (ICuriosType)x;

            ItemComponentCallbackV2.event(x).register(
                ((item, itemStack, componentContainer) -> componentContainer
                    .put(CuriosComponent.ITEM, new ICurio() {

                    })));

        });
        // idk if this is needed



    }

}
