package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiUtils {

    public static void renderScaledText(MatrixStack matrix, int x, int y, double scale, String text, Formatting format) {

        double antiScale = 1 / scale;

        RenderSystem.scaled(scale, scale, scale);

        double textWidthMinus = MinecraftClient.getInstance().textRenderer.getWidth(text) / 2F * scale;
        double textHeightMinus = MinecraftClient.getInstance().textRenderer.fontHeight * scale / 2;

        float xp = (float) (x - textWidthMinus);
        float yp = (float) (y - textHeightMinus);

        float xf = (float) (xp * antiScale);
        float yf = (float) (yp * antiScale);

        MinecraftClient.getInstance().textRenderer.drawWithShadow(matrix, text, xf, yf, format.getColorValue());
        RenderSystem.scaled(antiScale, antiScale, antiScale);

    }

    public static void renderTooltip(MatrixStack matrix, List<Text> tooltip, int mouseX, int mouseY) {

        Screen screen = MinecraftClient.getInstance().currentScreen;

        if (screen != null) {

            TooltipInfo info = new TooltipInfo(MinecraftClient.getInstance().player);
            screen.renderTooltip(matrix, tooltip, mouseX, mouseY
            );
        }

    }

    public static void renderTooltip(MatrixStack matrix, ItemStack stack, int mouseX, int mouseY) {

        Screen screen = MinecraftClient.getInstance().currentScreen;

        List<Text> tooltip = stack.getTooltip(MinecraftClient.getInstance().player, TooltipContext.Default.NORMAL);

        if (screen != null) {
            screen.renderTooltip(matrix, tooltip, mouseX, mouseY);
        }

    }

    public static boolean isInRectPoints(Point guiPos, Point size, Point mousePos) {
        return isInRect(guiPos.x, guiPos.y, size.x, size.y, mousePos.x, mousePos.y);
    }

    public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
    }

    public static class PointF {

        public float x, y;

        public PointF(float x, float y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static List<PointF> generateCurve(PointF pFrom, PointF pTo, float pRadius, float pMinDistance,
                                             boolean shortest) {

        List<PointF> pOutPut = new ArrayList<PointF>();

        // Calculate the middle of the two given points.
        PointF mPoint = new PointF(pFrom.x + pTo.x, pFrom.y + pTo.y);
        mPoint.x /= 2.0f;
        mPoint.y /= 2.0f;

        // Calculate the distance between the two points
        float xDiff = pTo.x - pFrom.x;
        float yDiff = pTo.y - pFrom.y;
        float distance = (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);

        while (pRadius * 2.0f < distance) {
            pRadius++;
        }

        // Calculate the middle of the expected curve.
        float factor = (float) Math.sqrt(
            (pRadius * pRadius) / ((pTo.x - pFrom.x) * (pTo.x - pFrom.x) + (pTo.y - pFrom.y) * (pTo.y - pFrom.y)) - 0.25f);
        PointF circleMiddlePoint = new PointF(0, 0);

        float middleDistance1 =
            0.5f * (pFrom.x + pTo.x) + factor * (pTo.y - pFrom.y) + 0.5f * (pFrom.y + pTo.y) + factor * (pFrom.x - pTo.x);
        float middleDistance2 =
            0.5f * (pFrom.x + pTo.x) - factor * (pTo.y - pFrom.y) + 0.5f * (pFrom.y + pTo.y) - factor * (pFrom.x - pTo.x);

        boolean side2 = middleDistance1 < middleDistance2;

        if (side2) {
            circleMiddlePoint.x = 0.5f * (pFrom.x + pTo.x) + factor * (pTo.y - pFrom.y);
            circleMiddlePoint.y = 0.5f * (pFrom.y + pTo.y) + factor * (pFrom.x - pTo.x);
        } else {
            circleMiddlePoint.x = 0.5f * (pFrom.x + pTo.x) - factor * (pTo.y - pFrom.y);
            circleMiddlePoint.y = 0.5f * (pFrom.y + pTo.y) - factor * (pFrom.x - pTo.x);
        }

        // Calculate the two reference angles
        float angle1 = (float) Math.atan2(pFrom.y - circleMiddlePoint.y, pFrom.x - circleMiddlePoint.x);
        float angle2 = (float) Math.atan2(pTo.y - circleMiddlePoint.y, pTo.x - circleMiddlePoint.x);

        // Calculate the step.
        float step = pMinDistance / pRadius;

        // Swap them if needed
        if (angle1 > angle2) {
            float temp = angle1;
            angle1 = angle2;
            angle2 = temp;

        }
        boolean flipped = false;
        if (!shortest) {
            if (angle2 - angle1 < Math.PI) {
                float temp = angle1;
                angle1 = angle2;
                angle2 = temp;
                angle2 += Math.PI * 2.0f;
                flipped = true;
            }
        }
        for (float f = angle1; f < angle2; f += step) {
            PointF p = new PointF((float) Math.cos(f) * pRadius + circleMiddlePoint.x,
                (float) Math.sin(f) * pRadius + circleMiddlePoint.y
            );
            pOutPut.add(p);
        }
        if (flipped ^ side2) {
            pOutPut.add(pFrom);
        } else {
            pOutPut.add(pTo);
        }

        return pOutPut;
    }

}

