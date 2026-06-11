package com.gryl.paranoia.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import com.gryl.paranoia.registry.ModEntities;
import com.gryl.paranoia.client.render.GrylEntityRenderer;

public class GrylParanoiaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Zaregistrujeme náš bílý model k entitě The Gryl
        EntityRendererRegistry.register(ModEntities.GRYL, GrylEntityRenderer::new);
    }
}
