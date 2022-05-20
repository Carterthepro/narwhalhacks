package net.narwhal;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;


public class Speed extends Hack{

    public Speed(boolean enabled,double maxSpeed,double walkSpeedMul,double shiftSpeedMul){
        super(enabled,"Speed");
        MaxSpeed = maxSpeed;
        WalkMul = walkSpeedMul;
        ShiftMul = shiftSpeedMul;
    }
    public double MaxSpeed;
    public double WalkMul;
    public double ShiftMul;

    double forwardAcceleration = 0;
    double sideAcceleration = 0;
    double maxSpeed = MaxSpeed;
    double sideMaxSpeed = MaxSpeed*WalkMul;
    public void tick(MinecraftClient client){
        if(client.player!=null && Enabled) {
            Entity object = client.player;
            Vec3d rotatedVelocity = new Vec3d(sideAcceleration, object.getVelocity().y,forwardAcceleration).rotateY((float) (-client.player.getHeadYaw()*Math.PI/180));
            if (client.player.hasVehicle()) {
                object = client.player.getVehicle();
            }
            boolean sneakPressed = client.options.sneakKey.isPressed() || client.options.sneakToggled;
            boolean sprintPressed = (client.options.sprintKey.isPressed() || client.options.sprintToggled);
            sideMaxSpeed = MaxSpeed*WalkMul;
            if(sprintPressed)maxSpeed = MaxSpeed;
            else if(sneakPressed){
                maxSpeed = MaxSpeed* ShiftMul;
                sideMaxSpeed = maxSpeed;
            }
            else maxSpeed = MaxSpeed*WalkMul;
            boolean forwardPressed = client.options.forwardKey.isPressed();
            boolean leftPressed = client.options.leftKey.isPressed();
            boolean rightPressed = client.options.rightKey.isPressed();
            boolean backPressed = client.options.backKey.isPressed();
                if(forwardPressed ^ backPressed) {
                    if (forwardPressed) {
                        if (forwardAcceleration < 0) forwardAcceleration = 0;
                        if (forwardAcceleration < sideMaxSpeed) {
                            forwardAcceleration += sideMaxSpeed/5;
                        } else forwardAcceleration = sideMaxSpeed;
                    }
                    else{
                        if (forwardAcceleration > 0) forwardAcceleration = 0;
                        if (forwardAcceleration > -sideMaxSpeed) {
                            forwardAcceleration -= sideMaxSpeed/5;
                        } else forwardAcceleration = -sideMaxSpeed;
                    }
                }else {
                    if (forwardAcceleration > 0) forwardAcceleration -= sideMaxSpeed/5;
                    if (forwardAcceleration < 0) forwardAcceleration += sideMaxSpeed/5;
                    if (forwardAcceleration < sideMaxSpeed/5 && forwardAcceleration > -sideMaxSpeed/5)
                        forwardAcceleration = 0;
                }
                if(leftPressed ^ rightPressed) {
                    if (rightPressed) {
                        if (sideAcceleration > 0) sideAcceleration = 0;
                        if (sideAcceleration > -sideMaxSpeed) {
                            sideAcceleration -= sideMaxSpeed/5;
                        } else sideAcceleration = -sideMaxSpeed;
                    }
                    else{
                        if (sideAcceleration < 0) sideAcceleration = 0;
                        if (sideAcceleration < sideMaxSpeed) {
                            sideAcceleration += sideMaxSpeed/5;
                        } else sideAcceleration = sideMaxSpeed;
                    }
                }else{
                    if (sideAcceleration > 0) sideAcceleration -= sideMaxSpeed/5;
                    if (sideAcceleration < 0) sideAcceleration += sideMaxSpeed/5;
                    if (sideAcceleration < sideMaxSpeed/5 && sideAcceleration > -sideMaxSpeed/5)
                        sideAcceleration = 0;
                }
            object.setVelocity(rotatedVelocity);

        }
    }
}
