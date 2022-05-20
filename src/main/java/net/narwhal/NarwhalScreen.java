package net.narwhal;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.LiteralText;


public class NarwhalScreen extends Screen {
    private final Screen parent;
    private final GameOptions settings;
    private final Hack[] hacks;
    public NarwhalScreen(Screen parent, GameOptions gameOptions, Hack[] hacks) {
        super(new LiteralText("Narwhal Hacks Mod"));
        this.parent = parent;
        this.settings = gameOptions;
        this.hacks = hacks;
    }
    LiteralText buttonText(boolean enabled,String name){
        if(enabled)
            return new LiteralText(name+" is enabled");
        else
            return new LiteralText(name+" is disabled");
    }

    protected void init(){
        int i = this.hacks.length-1;
        for (Hack hack:this.hacks) {
            int xpos;
            int ypos;
            if(i%2==0) {xpos = this.width / 2+5;ypos = this.height / 6 + 108 + -13 * i;}
            else{xpos = this.width / 2 - 150-5;ypos = this.height / 6 + 108 + -13 * (i+1);}
                this.addDrawableChild(new ButtonWidget(xpos, ypos, 150, 20, buttonText(hack.Enabled, hack.Name), button -> {
                    hack.Enabled = !hack.Enabled;
                    button.setMessage(buttonText(hack.Enabled, hack.Name));
                }));

            i++;
        }


        this.addDrawableChild(new ButtonWidget(this.width/2-100,this.height/6+138,200,20, ScreenTexts.BACK, button -> this.client.setScreen(this.parent)));
    }
}
