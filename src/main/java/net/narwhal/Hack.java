package net.narwhal;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;


public abstract class Hack {
    public Hack(boolean enabled,String name) {
        Enabled = enabled;
        Name = name;
    }
    public boolean Enabled;
    public String Name;

}
