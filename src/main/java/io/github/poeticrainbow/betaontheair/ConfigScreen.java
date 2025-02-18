package io.github.poeticrainbow.betaontheair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    public int service = 0; // 0 Twitch, 1 YouTube
    public EditBox username;

    @Override
    public void init() {
        username = new EditBox(this, this.font, this.width / 2 - 100, 95, 200, 20, "");
        this.buttons.add(new Button(0, this.width / 2 - 100 - 1, 120, 100, 20, "Disconnect"));
        this.buttons.add(new Button(1, this.width / 2 + 1, 120, 100, 20, "Connect"));
        this.buttons.add(new Button(2, this.width/2 - 50, 140, 100, 20, "Service: Twitch"));
    }

    public boolean isTwitch() {
        return service == 0;
    }

    public boolean isYoutube() {
        return service == 1;
    }

    @Override
    protected void charTyped(char codePoint, int modifiers) {
        if (username.active) {
            username.charTyped(codePoint, modifiers);
            return;
        }
        super.charTyped(codePoint, modifiers);
    }

    @Override
    public void render(int mouseX, int mouseY, float tickDelta) {
        renderBackground();
        super.render(mouseX, mouseY, tickDelta);
        username.render();
        this.drawCenteredString(font, "Twitch Username or YouTube Video Id", this.width/2, 80, 0xFFFFFF);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        username.clicked(mouseX, mouseY, button);
    }

    @Override
    protected void onButtonClick(Button button) {
        if (button.id == 0) {
            BetaOnTheAir.disconnectAllChannels();
        }
        if (button.id == 1) {
            if (isTwitch()) {
                BetaOnTheAir.twitchClient.getChat().joinChannel(username.getValue());
            }
            if (isYoutube()) {
                BetaOnTheAir.youtubeClient.joinChannel(username.getValue());
            }
        }
        if (button.id == 2) {
            if (service == 0) {
                service = 1;
                button.message = "Service: YouTube";
            } else if (service == 1) {
                service = 0;
                button.message = "Service: Twitch";
            }
        }
    }

    @Override
    public void tick() {
        username.tick();
    }
}
