package com.robertx22.age_of_exile.gui.screens.player_skills;

import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenGuiPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.util.Identifier;

public class NewPlayerSkillGemsOpener implements IContainerNamedScreen {

    @Override
    public void openContainer() {

        Packets.sendToServer(new OpenGuiPacket(OpenGuiPacket.GuiType.SKILL_GEMS));
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/skill_tree.png");
    }

    @Override
    public Words screenName() {
        return Words.Spells;
    }
}
