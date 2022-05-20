package net.narwhal;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NarwhalHacks implements ModInitializer {
	private static NarwhalHacks instance;
	public static final Logger LOGGER = LoggerFactory.getLogger("narwhalhacks");
	public AutoFishing autoFishing;
	public Flying flying;
	public Speed speed;
	public KillAura killAura;
	public NoFall noFall;
	@Override
	public void onInitialize() {
		if (instance == null) instance = this;
		ClientTickEvents.END_CLIENT_TICK.register(this::tick);
		autoFishing = new AutoFishing(false);
		flying = new Flying(false,3D,0.1D,1.5D,-0.4D);
		speed = new Speed(false,1.5D,0.7D,0.3D);
		killAura = new KillAura(false,5D,0.95F);
		noFall = new NoFall(false);

	}
	public static NarwhalHacks GetInstance(){
		return instance;
	}
	public void tick(MinecraftClient client) {
		autoFishing.tick(client);
		speed.tick(client);
		flying.tick(client);
		killAura.tick(client);
		noFall.tick(client);

	}
}
