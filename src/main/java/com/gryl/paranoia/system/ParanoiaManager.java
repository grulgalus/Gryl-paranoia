package com.gryl.paranoia.system;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import com.gryl.paranoia.registry.ModEntities;
import com.gryl.paranoia.entity.GrylEntity;
import java.util.Random;

public class ParanoiaManager {
    private static final Random RANDOM = new Random();
    private static int tickCounter = 0;

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (tickCounter >= 200) { // Každých 10 vteřin šance
                tickCounter = 0;
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    triggerRandomParanoiaEvent(player);
                }
            }
        });
    }

    private static void triggerRandomParanoiaEvent(ServerPlayerEntity player) {
        if (RANDOM.nextInt(100) < 6) { // 6% šance na událost
            int eventType = RANDOM.nextInt(4); // Nyní máme 4 různé události (0-3)

            switch (eventType) {
                case 0:
                    player.getWorld().playSound(null, player.getBlockPos().add(RANDOM.nextInt(3) - 1, 0, -2), SoundEvents.BLOCK_STONE_STEP, SoundCategory.HOSTILE, 1.0f, 1.0f);
                    break;
                case 1:
                    player.sendMessage(Text.literal("§cTvoje postel je zničena nebo zablokována."), false);
                    break;
                case 2:
                    player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.AMBIENT_CAVE.value(), SoundCategory.AMBIENT, 0.8f, 0.5f);
                    break;
                case 3:
                    spawnTheGrylBehindPlayer(player);
                    break;
            }
        }
    }

    private static void spawnTheGrylBehindPlayer(ServerPlayerEntity player) {
        // Spočítá pozici přesně za hráčem
        Vec3d look = player.getRotationVec(1.0F).normalize();
        Vec3d spawnPos = player.getPos().subtract(look.multiply(15.0D)); 
        BlockPos pos = BlockPos.ofFloored(spawnPos.x, player.getY(), spawnPos.z);
        
        // Vytvoří a spawne stalkera
        GrylEntity stalker = ModEntities.GRYL.create(player.getServerWorld());
        if(stalker != null) {
            stalker.refreshPositionAndAngles(pos, 0, 0);
            player.getWorld().spawnEntity(stalker);
        }
    }
}
