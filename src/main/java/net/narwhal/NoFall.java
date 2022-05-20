package net.narwhal;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;


public class NoFall extends Hack{


    public NoFall(boolean enabled) {
        super(enabled,"No Fall");
    }
    boolean canceled = false;
    public void tick(MinecraftClient client){
        if(client.player!=null && Enabled) {
            PlayerEntity player = client.player;
            if(player.fallDistance>client.player.getSafeFallDistance() && !canceled){
                if(!client.world.getBlockState(player.getBlockPos().add(0,-1,0)).isAir()){
                    if(player.getY()-(player.getBlockY()) < 1){
                        player.setVelocity(player.getVelocity().x,0.05,player.getVelocity().z);
                        canceled = true;
                    }
                    NarwhalHacks.LOGGER.info("diff: "+(player.getY()-(player.getBlockY())));
                }
            }
            if(player.isOnGround())canceled = false;
        }
    }
}
