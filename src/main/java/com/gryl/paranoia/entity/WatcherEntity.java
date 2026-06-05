package com.grylmods.paranoia.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WatcherEntity extends HostileEntity {

    public WatcherEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
        this.setInvulnerable(true); // Nelze ho zabít
    }

    @Override
    public void tick() {
        super.tick();
        
        if (this.getWorld().isClient()) return;

        PlayerEntity player = this.getWorld().getClosestPlayer(this, 40.0); // Vidí na 40 bloků
        
        if (player != null) {
            // Matematika úhlu pohledu (Raycasting)
            Vec3d lookVector = player.getRotationVec(1.0F).normalize();
            Vec3d directionToWatcher = this.getPos().subtract(player.getEyePos()).normalize();
            
            double dotProduct = lookVector.dotProduct(directionToWatcher);
            
            // 0.85 znamená, že hráč se podíval "přibližně" směrem k entitě
            if (dotProduct > 0.85) {
                // Udělá strašidelný zvuk zmizení
                this.playSound(net.minecraft.sound.SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.5F);
                this.discard(); // Okamžitě se smaže ze světa!
            }
        }
    }
}
