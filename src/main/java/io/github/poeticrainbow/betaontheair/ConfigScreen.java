package io.github.poeticrainbow.betaontheair;

import com.github.twitch4j.chat.TwitchChat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    public EditBox username;

    @Override
    public void init() {
        username = new EditBox(this, this.font, this.width / 2 - 100, 95, 200, 20, "");
        this.buttons.add(new Button(0, this.width / 2 - 100 - 1, 120, 100, 20, "Disconnect"));
        this.buttons.add(new Button(1, this.width / 2 + 1, 120, 100, 20, "Connect"));
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
        this.drawCenteredString(font, "Twitch Username", this.width/2, 80, 0xFFFFFF);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        username.clicked(mouseX, mouseY, button);
    }

    @Override
    protected void onButtonClick(Button button) {
        TwitchChat chat = BetaOnTheAir.twitchClient.getChat();
        if (button.id == 0) {
            username.setValue("");
            BetaOnTheAir.disconnectAllChannels();
        }
        if (button.id == 1) {
            chat.joinChannel(username.getValue());
        }
    }

    @Override
    public void tick() {
        username.tick();
    }
}
