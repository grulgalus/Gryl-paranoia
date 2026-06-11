package com.gryl.paranoia;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;

// Tvoje GrylAPI
import com.gryl.api.util.PlayerUtils;
import com.gryl.api.util.ColorUtils;
import com.gryl.api.math.VoxelMath;

import java.util.List;
import java.util.Random;

public class GrylParanoia implements ModInitializer {
    public static final String MOD_ID = "grylparanoia";
    private static final Random RANDOM = new Random();

    @Override
    public void onInitialize() {
        System.out.println("[Gryl: Paranoia] Mod probuzen. Vítejte v noční můře...");

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                
                // Získáme, jak dlouho hráč nespal (v tickech). 72000 ticků = 3 herní dny.
                int timeSinceRest = player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));

                // Pokud hráč nespal aspoň 1 herní den (24000 ticků), začne schíza
                if (timeSinceRest > 24000) {
                    
                    // Čím déle nespí, tím je menší číslo v RANDOM, takže tím VĚTŠÍ šance na halucinaci!
                    int baseChance = Math.max(1000, 10000 - (timeSinceRest / 10)); 

                    if (RANDOM.nextInt(baseChance) == 0) {
                        triggerParanoiaEvent(player);
                    }
                }
            }
        });
    }

    private void triggerParanoiaEvent(ServerPlayerEntity player) {
        int eventType = RANDOM.nextInt(3);

        if (eventType == 0) {
            PlayerUtils.sendActionBar(player, ColorUtils.errorText("Světlo tě nezachrání...").getString());
            BlockPos center = player.getBlockPos();
            List<BlockPos> nearbyBlocks = VoxelMath.getBlocksInBox(center.add(-5, -5, -5), center.add(5, 5, 5));
            for (BlockPos pos : nearbyBlocks) {
                if (player.getWorld().getBlockState(pos).isOf(Blocks.TORCH) || 
                    player.getWorld().getBlockState(pos).isOf(Blocks.WALL_TORCH)) {
                    player.getWorld().breakBlock(pos, false);
                }
            }
        } 
        else if (eventType == 1) {
            PlayerUtils.sendActionBar(player, ColorUtils.errorText("Ssssss...").getString());
            // Zvuk Creepera
            player.playSoundToPlayer(SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 1.0f, 1.0f);
        } 
        else {
            PlayerUtils.sendActionBar(player, ColorUtils.errorText("Někdo tam je.").getString());
            player.playSoundToPlayer(SoundEvents.ENTITY_ENDERMAN_STARE, SoundCategory.AMBIENT, 1.0f, 0.5f);
            player.playSoundToPlayer(SoundEvents.BLOCK_STONE_STEP, SoundCategory.HOSTILE, 1.0f, 0.5f);
        }
    }
}
