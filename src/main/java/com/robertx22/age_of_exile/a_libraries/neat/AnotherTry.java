package com.robertx22.age_of_exile.a_libraries.neat;

import com.robertx22.age_of_exile.mixin_methods.RenderMobInfo;
import com.robertx22.library_of_exile.main.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderLivingEvent;

public class AnotherTry {

    public static void register() {

        ForgeEvents.registerForgeEvent(RenderLivingEvent.class, event -> {

            Minecraft mc = Minecraft.getInstance();

            RenderMobInfo.renderLivingEntityLabelIfPresent(mc.font, event.getRenderer()
                .getDispatcher(), event.getEntity(), event.getMatrixStack(), event.getBuffers(), event.getLight());

        });

        /*
        ForgeEvents.registerForgeEvent(RenderWorldLastEvent.class, event -> {

            Minecraft mc = Minecraft.getInstance();

            if (!NeatConfig.renderInF1 || !NeatConfig.draw) {
                // return;
            }

            ActiveRenderInfo renderInfo = mc.gameRenderer.getMainCamera();
            MatrixStack matrixStack = event.getMatrixStack();
            float partialTicks = event.getPartialTicks();
            Entity entity = renderInfo.getEntity() != null ? renderInfo.getEntity() : mc.player;

            Quaternion rotation = renderInfo.rotation()
                .copy();
            rotation.mul(-1.0F);
            matrixStack.mulPose(rotation);
            float scale = 0.026666672F;
            matrixStack.scale(-scale, -scale, scale);

            Vector3d renderPos = renderInfo.getPosition();

            mc.level.entitiesForRendering()
                .forEach(e -> {
                    if (e instanceof LivingEntity) {
                        LivingEntity passedEntity = (LivingEntity) e;

                        double x = passedEntity.xOld + (passedEntity.getX() - passedEntity.xOld) * partialTicks;
                        double y = passedEntity.yOld + (passedEntity.getY() - passedEntity.yOld) * partialTicks;
                        double z = passedEntity.zOld + (passedEntity.getZ() - passedEntity.zOld) * partialTicks;

                        matrixStack.translate((float) (x - renderPos.x()), (float) (y - renderPos.y() + passedEntity.getBbHeight() + NeatConfig.heightAbove), (float) (z - renderPos.z()));
                        IRenderTypeBuffer.Impl buffer = mc.renderBuffers()
                            .bufferSource();

                        RenderMobInfo.renderLivingEntityLabelIfPresent(mc.font, mc.getEntityRenderDispatcher(),
                            passedEntity, event.getMatrixStack(), buffer, 0);
                    }

                });

        });

         */
    }

}
