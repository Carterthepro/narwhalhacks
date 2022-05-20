package net.narwhal.mixin;

import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.narwhal.Hack;
import net.narwhal.NarwhalHacks;
import net.narwhal.NarwhalScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "initWidgets",cancellable = true)
    public void initWidgets(CallbackInfo ci){
        NarwhalHacks narwhalHacks = NarwhalHacks.GetInstance();
        this.addDrawableChild(new ButtonWidget(10,10,90,20,new LiteralText("NarwhalHacks"),button ->
                this.client.setScreen(new NarwhalScreen(this,this.client.options,new Hack[]{narwhalHacks.killAura,narwhalHacks.flying,narwhalHacks.noFall,narwhalHacks.speed,narwhalHacks.autoFishing}))));
    }
}
