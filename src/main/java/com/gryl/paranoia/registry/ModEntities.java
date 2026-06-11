package com.gryl.paranoia.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import com.gryl.paranoia.entity.GrylEntity;
import com.gryl.paranoia.GrylParanoia;

public class ModEntities {
    public static final EntityType<GrylEntity> GRYL = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(GrylParanoia.MOD_ID, "gryl"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GrylEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.9f))
                    .build()
    );

    public static void register() {
        // Načte třídu do paměti
    }
}
