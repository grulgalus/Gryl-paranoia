package com.grylmods.paranoia;

import com.gryl.paranoia.core.ModEntities;
import com.gryl.paranoia.core.ParanoiaManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrylParanoia implements ModInitializer {
    public static final String MOD_ID = "gryl-paranoia";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("[Gryl-Paranoia] Inicializace psychologického teroru. API připojeno.");

        // Zaregistrujeme naši entitu
        ModEntities.registerAll();

        // Spustíme nekonečnou smyčku Paranoia Managera
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            ParanoiaManager.tick(server);
        });
    }
}
