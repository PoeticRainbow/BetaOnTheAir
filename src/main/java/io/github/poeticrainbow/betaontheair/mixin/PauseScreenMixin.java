package io.github.poeticrainbow.betaontheair.mixin;

import io.github.poeticrainbow.betaontheair.ConfigScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public class PauseScreenMixin extends Screen {
    @Inject(method = "init", at = @At("TAIL"))
    private void inject(CallbackInfo ci) {
        this.buttons.add(new Button(32, this.width/2 - 100, 0, "On The Air..."));
    }

    @Inject(method = "onButtonClick", at = @At("TAIL"))
    private void inject(Button button, CallbackInfo ci) {
        if (button.id == 32) {
            this.minecraft.setScreen(new ConfigScreen());
        }
    }
}
