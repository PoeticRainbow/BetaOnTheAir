package io.github.poeticrainbow.betaontheair;

import com.github.kusaanko.youtubelivechat.YouTubeLiveChat;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class YoutubeClient extends TimerTask {
    public static YouTubeLiveChat chat;
    public final Timer timer;

    public YoutubeClient() {
        timer = new Timer();
        timer.schedule(this, 0, 1000);
    }

    @Override
    public void run() {
        if (chat != null) {
            try {
                chat.update();
            } catch (IOException e) {
            }
            chat.getChatItems().forEach(BetaOnTheAir::onYoutubeMessage);
        }
    }

    public void joinChannel(String videoId) {
        try {
            chat = new YouTubeLiveChat(videoId);
        } catch (Exception e) {
            System.out.println("Couldn't connect");
        }
    }

    public void disconnect() {
        chat = null;
    }
}
