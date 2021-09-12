package com.robertx22.age_of_exile.gui.screens.spell;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.PlayerCaps;
import com.robertx22.age_of_exile.vanilla_mc.packets.sync_cap.RequestSyncCapToClient;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SpellScreen extends BaseScreen implements INamedScreen {
    private static final Identifier BACKGROUND = new Identifier(Ref.MODID, "textures/gui/spells/spell_school_background.png");

    static int sizeX = 250;
    static int sizeY = 233;
    public static boolean IS_PICKING_HOTBAR_SPELL = false;
    public static int NUMBER_OF_HOTBAR_FOR_PICKING = 0;

    MinecraftClient mc = MinecraftClient.getInstance();

    public SpellSchool currentSchool = ExileDB.SpellSchools()
        .random(); // TODO

    public SpellScreen() {
        super(sizeX, sizeY);
        IS_PICKING_HOTBAR_SPELL = false;
        Packets.sendToServer(new RequestSyncCapToClient(PlayerCaps.SPELLS));
    }

    @Override
    public Identifier iconLocation() {
        return new Identifier(Ref.MODID, "textures/gui/main_hub/icons/spells.png");
    }

    @Override
    public Words screenName() {
        return Words.Skills;
    }

    static int SLOT_SPACING = 21;

    @Override
    public void init() {
        super.init();

        this.buttons.clear();

        currentSchool.spells.entrySet()
            .forEach(e -> {

                PointData point = e.getValue();
                Spell spell = ExileDB.Spells()
                    .get(e.getKey());

                int x = this.guiLeft + 12 + (point.x * SLOT_SPACING);
                int y = this.guiTop + 177 - (point.y * SLOT_SPACING);

                this.addButton(new LearnSpellButton(this, spell, x, y));
            });

        for (int i = 0; i < 8; i++) {

            int x = guiLeft + 10 + (PickHotbarButton.BUTTON_SIZE_X * i);

            if (i > 3) {
                x += 70;
            }
            int y = guiTop + 204;

            this.addButton(new PickHotbarButton(this, i, x, y));
        }

    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(MatrixStack matrix, int x, int y, float ticks) {

        mc.getTextureManager()
            .bindTexture(BACKGROUND);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, mc.getWindow()
                .getScaledWidth() / 2 - sizeX / 2,
            mc.getWindow()
                .getScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );

        super.render(matrix, x, y, ticks);

        mc.getTextureManager()
            .bindTexture(currentSchool.getIconLoc());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawTexture(matrix, guiLeft + 108, guiTop + 8, 34, 34, 34, 34, 34, 34);

        // background
        mc.getTextureManager()
            .bindTexture(currentSchool.getBackgroundLoc());
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        drawTexture(matrix, guiLeft + 7, guiTop + 7, 93, 36, 93, 36, 93, 36);
        drawTexture(matrix, guiLeft + 150, guiTop + 7, 93, 36, 93, 36, 93, 36);

        buttons.forEach(b -> b.renderToolTip(matrix, x, y));

    }

}