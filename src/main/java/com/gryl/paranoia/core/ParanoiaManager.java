package com.grylmods.paranoia.core;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import java.util.Random;

public class ParanoiaManager {
    private static final Random random = new Random();
    private static int tickCounter = 0;

    public static void tick(MinecraftServer server) {
        tickCounter++;
        
        // Běží jen každé 3 vteřiny (60 ticků), aby to nezatěžovalo FPS
        if (tickCounter % 60 != 0) return;

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            // Extrémní šance (1:150), že se hráči stane halucinace
            if (random.nextInt(150) == 1) {
                triggerHorrorEvent(player);
            }
        }
    }

    private static void triggerHorrorEvent(ServerPlayerEntity player) {
        int eventType = random.nextInt(5); 

        switch (eventType) {
            case 0:
                // Zvuk dýchání/jeskyně hned u ucha
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.AMBIENT_CAVE.value(), SoundCategory.AMBIENT, 1.0f, 0.5f);
                break;
            case 1:
                // Falešný Creeper těsně za zády!
                player.getWorld().playSound(null, player.getBlockPos().backward(1), SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 1.0f, 0.8f);
                break;
            case 2:
                // Schizofrenie - zpráva od sebe sama do chatu
                player.sendMessage(Text.literal("§c<" + player.getName().getString() + "> Zastav se. Dívá se na nás."), false);
                break;
            case 3:
                // Náhlá tma (Záblesk slepoty na 2 vteřiny)
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40, 1, false, false, false));
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_STARE, SoundCategory.HOSTILE, 1.0f, 0.1f);
                break;
            case 4:
                // Zvuk kroků sprintujícího hráče za tebou
                BlockPos pos = player.getBlockPos().backward(3);
                player.getWorld().playSound(null, pos, SoundEvents.BLOCK_DEEPSLATE_STEP, SoundCategory.PLAYERS, 2.0f, 1.2f);
                break;
        }
    }
}
