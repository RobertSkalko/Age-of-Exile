package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.uncommon.interfaces.IGenerated;
import com.robertx22.mine_and_slash.vanilla_mc.items.SimpleMatItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag.ItemCurrencyBag;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.ItemCapacitor;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Ref.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegister {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        putInLists();
        registerSingles(event);
        registerLists(event);

    }

    private static void putInLists() {

    }

    private static void registerLists(RegistryEvent.Register<Item> event) {

        IForgeRegistry<Item> r = event.getRegistry();

        ItemCapacitor.ITEMS.values()
            .forEach((x) -> r.register(x));

    }

    private static List<Item> list = new ArrayList<>();

    public static void shcheduleToRegister(Item item) {
        list.add(item);
    }

    private static void registerSingles(RegistryEvent.Register<Item> event) {

        IForgeRegistry<Item> r = event.getRegistry();

        list.add(new ItemCurrencyBag().setRegistryName(ItemCurrencyBag.ID));

        list.add(SimpleMatItem.CRYSTALLIZED_ESSENCE);
        list.add(SimpleMatItem.INFUSED_IRON);
        list.add(SimpleMatItem.MYTHIC_ESSENCE);
        list.add(SimpleMatItem.GOLDEN_ORB);

        for (Item item : list) {
            if (item instanceof IGenerated) {
                IGenerated gen = (IGenerated) item;
                gen.generateAllPossibleStatVariations()
                    .forEach(x -> r.register((Item) x));

            } else {
                r.register(item);
            }

        }

    }

}


