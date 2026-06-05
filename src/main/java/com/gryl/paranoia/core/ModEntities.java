package com.grylmods.paranoia.core;

import com.gryl.paranoia.entity.WatcherEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    // Vytvoření entity - vysoký 2 bloky, široký necelý blok (jako hráč)
    public static final EntityType<WatcherEntity> WATCHER = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("gryl-paranoia", "watcher"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WatcherEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .build()
    );

    public static void registerAll() {
        // Zde se entita inicializuje do hry
    }
}
