package com.robertx22.age_of_exile.gui.screens.spell_hotbar_setup;

import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.mmorpg.Packets;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenSpellSetupContainerPacket;
import net.minecraft.util.Identifier;

public class HotbarNamedScreen implements IContainerNamedScreen {
    @Override
    public void openContainer() {
        Packets.sendToServer(new OpenSpellSetupContainerPacket());
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/spellbar.png");
    }

    @Override
    public Words screenName() {
        return Words.Spellbar;
    }

}
