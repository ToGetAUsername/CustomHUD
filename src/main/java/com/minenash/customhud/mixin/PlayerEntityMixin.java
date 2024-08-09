package com.minenash.customhud.mixin;


import com.minenash.customhud.complex.ComplexData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V"))
    private void logAttack(Entity target, CallbackInfo ci) {
        if (((Object)this) == MinecraftClient.getInstance().player && ComplexData.targetEntityHitPos != null) {
            ComplexData.lastHitEntity = target;
            ComplexData.lastHitEntityDist = ComplexData.targetEntityHitPos.distanceTo(MinecraftClient.getInstance().getCameraEntity().getPos());
            ComplexData.lastHitEntityTime = System.currentTimeMillis();
        }
    }

}
