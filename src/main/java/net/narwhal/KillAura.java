package net.narwhal;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;


public class KillAura extends Hack{

    public KillAura(boolean enabled, double range, float cooldownPercent){
        super(enabled,"KillAura");
        AttackRange = range;
        CooldownPercent = cooldownPercent;
    }
    public double AttackRange;
    public float CooldownPercent;
    public void tick(MinecraftClient client){
        if(client.player!=null && Enabled) {
            PlayerEntity player = client.player;
            ClientWorld world = client.world;
            world.getPlayers().forEach((p)->{
                if(p!=player){
                    if(player.isInRange(p,AttackRange)) {
                        if(player.getAttackCooldownProgress(0F)>=CooldownPercent)
                            client.interactionManager.attackEntity(player,p);
                    }
                }
            });
        }
    }
}
