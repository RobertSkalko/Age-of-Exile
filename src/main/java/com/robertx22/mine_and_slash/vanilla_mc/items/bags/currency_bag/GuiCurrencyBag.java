package com.robertx22.mine_and_slash.vanilla_mc.items.bags.currency_bag;

import com.robertx22.mine_and_slash.vanilla_mc.items.bags.BaseBagGui;
import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GuiCurrencyBag extends BaseBagGui<ContainerCurrencyBag> {

    private static final Identifier texture = new Identifier(Ref.MODID, "textures/gui/loot_bag.png");

    public GuiCurrencyBag(ContainerCurrencyBag bag, PlayerInventory inv,
                          Text comp) {
        super(inv, bag);

    }

    @Override
    public Identifier texture() {
        return texture;
    }

    @Override
    public int rows() {
        return 2;
    }

    @Override
    public String name() {
        return "Currency Bag";
    }

}