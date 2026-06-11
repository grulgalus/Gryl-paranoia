package com.gryl.paranoia.client.render;

import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.util.Identifier;
import com.gryl.paranoia.entity.GrylEntity;
import com.gryl.paranoia.GrylParanoia;

public class GrylEntityRenderer extends BipedEntityRenderer<GrylEntity, ZombieEntityModel<GrylEntity>> {
    
    // Tady hře říkáme, kde najde texturu
    private static final Identifier TEXTURE = Identifier.of(GrylParanoia.MOD_ID, "textures/entity/gryl.png");

    public GrylEntityRenderer(EntityRendererFactory.Context context) {
        // Použijeme model zombíka (má ruce před sebou, což je u stalkera mega creepy!)
        super(context, new ZombieEntityModel<>(context.getPart(EntityModelLayers.ZOMBIE)), 0.5f);
    }

    @Override
    public Identifier getTexture(GrylEntity entity) {
        return TEXTURE;
    }
}
