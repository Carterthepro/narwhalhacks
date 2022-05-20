package net.narwhal;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Hand;

public class AutoFishing extends Hack{
    public AutoFishing(boolean enabled){
        super(enabled,"Auto Fishing");
    }
    int recastRod = -1;
    public void tick(MinecraftClient client){
        if(recastRod>0){
            recastRod--;
        }
        if(recastRod == 0 && Enabled){
            client.interactionManager.interactItem(client.player,client.world, Hand.MAIN_HAND);
            NarwhalHacks.LOGGER.info("pulled in");
            recastRod =-1;
        }
    }
    public void setRecastRod(int countdown){
        recastRod = countdown;
    }
}
