package com.doom;

import com.doom.database.DatabaseManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.EnumSet;


public class Bot {
    private Bot() throws LoginException {
        DatabaseManager.INSTANCE.getPrefix(-1);
        WebUtils.setUserAgent("Legendary Bot");
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                .setColor(Color.cyan)
                .setFooter("/help to get some help")
        );

        EventWaiter waiter = new EventWaiter();

        JDABuilder.createDefault(Config.get("token"),
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.DIRECT_MESSAGE_TYPING
        )
                .disableCache(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOTE
                ))
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new Listener(waiter), waiter)
                .setActivity(Activity.watching("/help"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .build();

    }
    public static void main(String[] args) throws LoginException {
        new Bot();
    }
}