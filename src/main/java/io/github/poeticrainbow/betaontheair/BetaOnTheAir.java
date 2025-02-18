package io.github.poeticrainbow.betaontheair;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import io.github.poeticrainbow.betaontheair.mixin.MinecraftAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.player.LocalPlayer;

public class BetaOnTheAir implements ModInitializer, ClientModInitializer {
    public static TwitchClient twitchClient;

    public static void onChannelMessage(ChannelMessageEvent event) {
        LocalPlayer player = MinecraftAccessor.getInstance().player;
        if (player != null) {
            if (event.isHighlightedMessage()) {
                player.displayClientMessage(String.format("§d%s§7: §f§l%s", event.getUser().getName(), event.getMessage()));
            } else {
                player.displayClientMessage(String.format("§d%s§7: §f%s", event.getUser().getName(), event.getMessage()));
            }
        } else {
            disconnectAllChannels();
        }
    }

    public static void disconnectAllChannels() {
        TwitchChat chat = twitchClient.getChat();
        chat.getChannels().forEach(chat::leaveChannel);
    }

    @Override
    public void onInitialize() {
    }

    @Override
    public void onInitializeClient() {
        BetaOnTheAir.twitchClient = TwitchClientBuilder.builder()
                .withEnableChat(true)
                .build();
        BetaOnTheAir.twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, BetaOnTheAir::onChannelMessage);
    }
}
