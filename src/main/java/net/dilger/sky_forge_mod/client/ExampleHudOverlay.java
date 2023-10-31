package net.dilger.sky_forge_mod.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ExampleHudOverlay {
    private static final ResourceLocation EXAMPLE_RESOURCE = new ResourceLocation("");
    public static final IGuiOverlay HUD = (((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight / 2;

        RenderSystem.setShader(GameRenderer::getParticleShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 10.F);
        RenderSystem.setShaderTexture(0, EXAMPLE_RESOURCE);


    }));
}
