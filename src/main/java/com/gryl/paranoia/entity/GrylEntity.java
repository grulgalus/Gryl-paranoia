package com.gryl.paranoia.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrylEntity extends HostileEntity {

    public GrylEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createGrylAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45D); // Velmi rychlý!
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) return;

        PlayerEntity player = this.getWorld().getClosestPlayer(this, 64.0D);
        if (player != null) {
            Vec3d playerLook = player.getRotationVec(1.0F).normalize();
            Vec3d diff = this.getPos().subtract(player.getPos());
            double dist = diff.length();
            diff = diff.normalize();
            
            // Matematika: Zjistí, jestli se hráč dívá směrem k entitě
            double dot = playerLook.dotProduct(diff);
            boolean isLooking = dot > 0.1 && player.canSee(this);

            if (isLooking) {
                // Hráč se kouká -> Entita ZAMRZNE
                this.getNavigation().stop();
                this.setVelocity(0, this.getVelocity().y, 0);
            } else {
                // Hráč se nekouká -> Entita běží k hráči!
                this.getNavigation().startMovingTo(player, 1.2D);
                
                // Pokud tě dožene
                if (dist < 2.0D) {
                    jumpscareAndVanish(player);
                }
            }
        } else {
            // Pokud jsi moc daleko, entita sama tiše zmizí
            this.discard();
        }
    }

    private void jumpscareAndVanish(PlayerEntity player) {
        // Obrovský řev, absolutní tma na obrazovce a vypaření
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.HOSTILE, 1.0f, 0.5f);
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 60, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 120, 1));
        this.discard();
    }
}
