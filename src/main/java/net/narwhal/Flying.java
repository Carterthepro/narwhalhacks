package net.narwhal;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;


public class Flying extends Hack{

    public Flying(boolean enabled, double flySpeed,double flyAcceleration,double flyUpSpeed,double fallSpeed){
        super(enabled,"Flying");
        Speed = flySpeed;
        Acceleration = flyAcceleration;
        FlyUpSpeed = flyUpSpeed;
        FallSpeed = fallSpeed;
    }
    public double Speed;
    public double Acceleration;
    public double FlyUpSpeed;
    public double FallSpeed;

    int toggle = 0;
    double forwardAcceleration = 0;
    double sideAcceleration = 0;
    boolean firstJump = false;
    boolean letGoOfJump = false;
    int jumpTimer = -1;
    int flyDelay = 0;
    boolean flying = false;

    public void tick(MinecraftClient client){
        if(client.player!=null && Enabled) {
            boolean jumpPressed = client.options.jumpKey.isPressed();
            boolean sneakPressed = client.options.sneakKey.isPressed();
            boolean forwardPressed = client.options.forwardKey.isPressed();
            boolean leftPressed = client.options.leftKey.isPressed();
            boolean rightPressed = client.options.rightKey.isPressed();
            boolean backPressed = client.options.backKey.isPressed();
            Entity object = client.player;
            if (client.player.hasVehicle()) {
                object = client.player.getVehicle();
                flying = true;
            }


            if (flying) {
                Vec3d rotatedVelocity = new Vec3d(sideAcceleration, 0,forwardAcceleration).rotateY((float) (-client.player.getHeadYaw()*Math.PI/180));
                if(forwardPressed ^ backPressed) {
                    if (forwardPressed) {
                        if (forwardAcceleration < 0) forwardAcceleration = 0;
                        if (forwardAcceleration < Speed) {
                            forwardAcceleration += Acceleration;
                        } else forwardAcceleration = Speed;
                    }
                    else{
                        if (forwardAcceleration > 0) forwardAcceleration = 0;
                        if (forwardAcceleration > -Speed) {
                            forwardAcceleration -= Acceleration;
                        } else forwardAcceleration = -Speed;
                    }
                }else {
                    if (forwardAcceleration > 0) forwardAcceleration -= Acceleration;
                    if (forwardAcceleration < 0) forwardAcceleration += Acceleration;
                    if (forwardAcceleration < Acceleration && forwardAcceleration > -Acceleration)
                        forwardAcceleration = 0;
                }
                if(leftPressed ^ rightPressed) {
                    if (rightPressed) {
                        if (sideAcceleration > 0) sideAcceleration = 0;
                        if (sideAcceleration > -Speed) {
                            sideAcceleration -= Acceleration;
                        } else sideAcceleration = -Speed;
                    }
                    else {
                        if (sideAcceleration < 0) sideAcceleration = 0;
                        if (sideAcceleration < Speed) {
                            sideAcceleration += Acceleration;
                        } else sideAcceleration = Speed;
                    }
                }else{
                    if (sideAcceleration > 0) sideAcceleration -= Acceleration;
                    if (sideAcceleration < 0) sideAcceleration += Acceleration;
                    if (sideAcceleration < Acceleration && sideAcceleration > -Acceleration)
                        sideAcceleration = 0;
                }
                if (jumpPressed) {
                    rotatedVelocity = new Vec3d(rotatedVelocity.x, FlyUpSpeed, rotatedVelocity.z);
                } else if (sneakPressed) {
                    rotatedVelocity = new Vec3d(rotatedVelocity.x, FallSpeed, rotatedVelocity.z);

                }
                if(rotatedVelocity.y<=-0.04) toggle = 40;
                if(toggle == 0) {
                    rotatedVelocity = new Vec3d(rotatedVelocity.x, -0.04, rotatedVelocity.z);
                    toggle = 40;
                }
                if(toggle == 20) {
                    rotatedVelocity = new Vec3d(rotatedVelocity.x, rotatedVelocity.y+0.04, rotatedVelocity.z);
                }
                object.setVelocity(rotatedVelocity);
                toggle--;
            }else{sideAcceleration = 0;forwardAcceleration = 0;toggle=40;}
            if(jumpTimer == 0){
                jumpTimer--;
                letGoOfJump = false;
                firstJump = false;
            }
            if(jumpTimer>0 && !jumpPressed){
                letGoOfJump = true;
            }
            if(letGoOfJump && jumpPressed && jumpTimer>0&&flyDelay==0){
                flying =!flying;
                firstJump = false;
                jumpTimer = -1;
                letGoOfJump = false;
                flyDelay = 5;
            }
            if(flyDelay>3){
                object.setVelocity(object.getVelocity().x,0,object.getVelocity().z);
            }
            if(jumpTimer>0)jumpTimer--;
            if(jumpPressed && !firstJump){
                firstJump = true;
                jumpTimer = 10;
            }
            if(object.isOnGround()){
                firstJump = false;
                jumpTimer = -1;
                letGoOfJump = false;
                flying = false;
                flyDelay = 2;
            }
            if(flyDelay>0)flyDelay--;
        }
    }
}
