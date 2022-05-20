package net.narwhal.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.Hand;
import net.narwhal.NarwhalHacks;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Shadow private boolean caughtFish;

    @Shadow @Nullable public abstract PlayerEntity getPlayerOwner();

    @Inject(at = @At("TAIL"), method = "onTrackedDataSet",cancellable = true)
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci){
        MinecraftClient client = MinecraftClient.getInstance();

        if(caughtFish && NarwhalHacks.GetInstance().autoFishing.Enabled && this.getPlayerOwner() == client.player){
            NarwhalHacks.GetInstance().autoFishing.setRecastRod(10);
            client.interactionManager.interactItem((client.player),client.world, Hand.MAIN_HAND);
        }
    }
}
