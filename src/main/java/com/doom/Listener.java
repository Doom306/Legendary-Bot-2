package com.doom;

import com.doom.commands.commands.Money.MoneyData;
import com.doom.commands.commands.Others.BadDesign;
import com.doom.commands.commands.Pro.ProData;
import com.doom.commands.commands.Shop.ShopData;
import com.doom.commands.commands.trivia.TriviaCommand;
import com.doom.database.DatabaseManager;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Listener extends ListenerAdapter {

    private static HashMap<Guild, String> message = new HashMap<>();
    private static HashMap<Guild, Boolean> activate = new HashMap<>();
    private static HashMap<User, Guild> guild = new HashMap<>();
    private static HashMap<Guild, User> mod = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager;
    private String text = "Congratulations";
    private boolean onetime = true;
    private boolean onetime1 = true;

    public Listener(EventWaiter waiter) {
        manager = new CommandManager(waiter);
    }


    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
        LOGGER.info("Legendary Bot running...");
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        final TextChannel dontDoThis = event.getGuild().getDefaultChannel();

        final String useGuildSpecificSettingsInstead = String.format("Welcome %s to %s",
                event.getMember().getUser().getAsMention(), event.getGuild().getName());

        if (!event.getMember().hasPermission(Permission.MESSAGE_WRITE)) {
            return;
        }

        assert dontDoThis != null;
        dontDoThis.sendMessage(useGuildSpecificSettingsInstead).queue();

    }


    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        final String useGuildSpecificSettingsInstead = String.format("Thank you for adding %s to %s!!!'n" +
                        "To know about this bot feel free to type /help\n" +
                        "You can change the prefix by typing /setprefix [prefix]\n" +
                        "To know more about a command type /help [command name]",
                event.getJDA().getSelfUser().getAsMention(), event.getGuild().getName());

        Objects.requireNonNull(event.getGuild().getDefaultChannel()).sendMessage(useGuildSpecificSettingsInstead).queue();
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        final String contentRaw = event.getMessage().getContentRaw();
        final User author = event.getAuthor();

        if (!guild.containsKey(author)) {
            return;
        }

        final Guild guild = Listener.guild.get(author);

        final User user = guild.getOwner().getUser();

        if (mod.containsValue(guild)) {
            mod.get(guild).openPrivateChannel().queue(PrivateChannel ->
                    PrivateChannel.sendMessage("***Reply of " + author.getAsMention() + "***\n**"
                            + contentRaw
                            + "** in **" + event.getChannel().getName())
                            .queue());
            return;
        }
        user.openPrivateChannel().queue(PrivateChannel ->
                PrivateChannel.sendMessage("***Reply of " + author.getAsMention() + "***\n**"
                        + contentRaw
                        + "** in **" + event.getChannel().getName())
                        .queue());
    }


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        User user = event.getAuthor();

        LOGGER.info(String.valueOf(event.getMember()));

        LOGGER.info(event.getMessage().getContentRaw() + "\n" +
                "Sent by " +
                event.getAuthor().getAsMention());

        if (event.getAuthor().getId().equals(Config.get("owner_id"))) {
            if (onetime) {
                MoneyData.money.put(event.getMessage().getAuthor(), 200000000d);
                MoneyData.goal.put(event.getMessage().getAuthor(), 5000d);
                MoneyData.robGoal.put(event.getMessage().getAuthor(), 2000d);
                MoneyData.bank.put(event.getMessage().getAuthor(), 10000d);
                ShopData.fish.put(event.getAuthor(), 0);
                ShopData.gold.put(event.getAuthor(), 0);
                ShopData.silver.put(event.getAuthor(), 0);
                ShopData.legendaryDragon.put(event.getAuthor(), 0);
                ShopData.luckyCharm.put(event.getAuthor(), 0);
                onetime = false;
                MoneyData.robGoalProgress.put(event.getMessage().getAuthor(), 0d);
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                ProData.isPro.put(event.getMessage().getAuthor(), true);

                double amount = MoneyData.money.get(event.getAuthor());
                event.getChannel().sendMessage(formatter.format(amount) + " added to " + event.getAuthor().getAsMention()).queue();
                LOGGER.info("Legendary Bot added 200,000,000 to " + event.getMessage().getAuthor().getAsMention() + " ...");
                return;
            }
        }

        if (event.getAuthor().equals(event.getJDA().getSelfUser())) {
            if (!MoneyData.money.containsKey(event.getJDA().getSelfUser())) {
                MoneyData.money.put(event.getMessage().getAuthor(), 2000000d);
                onetime1 = false;
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                ProData.isPro.put(event.getMessage().getAuthor(), false);

                MoneyData.goal.put(event.getMessage().getAuthor(), 5000d);
                MoneyData.robGoalProgress.put(event.getMessage().getAuthor(), 0d);
                ShopData.fish.put(event.getAuthor(), 1000);
                ShopData.gold.put(event.getAuthor(), 1000);
                ShopData.silver.put(event.getAuthor(), 1000);
                ShopData.legendaryDragon.put(event.getAuthor(), 1000);
                ShopData.goldenEgg.put(event.getAuthor(), 1000);
                ShopData.luckyCharm.put(event.getAuthor(), 10000);
                MoneyData.bank.put(event.getMessage().getAuthor(), 100000d);
                MoneyData.robGoal.put(event.getMessage().getAuthor(), 2000d);
                double amount = MoneyData.money.get(event.getAuthor());
                event.getChannel().sendMessage(formatter.format(amount) + " added to " + event.getAuthor().getAsMention()).queue();
                LOGGER.info("Legendary Bot added 20,000 to " + event.getMessage().getAuthor().getAsMention() + " ...");
                return;
            }
        }

        if (event.getAuthor().getId().equals(Config.get("owner_id_partner"))) {
            if (onetime1) {
                MoneyData.money.put(event.getMessage().getAuthor(), 200000d);
                onetime1 = false;
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                ProData.isPro.put(event.getMessage().getAuthor(), false);
                MoneyData.bank.put(event.getMessage().getAuthor(), 0d);
                MoneyData.goal.put(event.getMessage().getAuthor(), 5000d);
                MoneyData.robGoalProgress.put(event.getMessage().getAuthor(), 0d);
                MoneyData.robGoal.put(event.getMessage().getAuthor(), 2000d);
                ShopData.fish.put(event.getAuthor(), 0);
                ShopData.gold.put(event.getAuthor(), 0);
                ShopData.silver.put(event.getAuthor(), 0);
                ShopData.legendaryDragon.put(event.getAuthor(), 0);
                ShopData.luckyCharm.put(event.getAuthor(), 0);
                ShopData.goldenEgg.put(event.getAuthor(), 0);
                double amount = MoneyData.money.get(event.getAuthor());
                event.getChannel().sendMessage(formatter.format(amount) + " added to " + event.getAuthor().getAsMention()).queue();
                LOGGER.info("Legendary Bot added 20,000 to " + event.getMessage().getAuthor().getAsMention() + " ...");
                return;
            }
        }

        if (!MoneyData.money.containsKey(event.getAuthor())) {
            MoneyData.goal.put(event.getMessage().getAuthor(), 5000d);
            MoneyData.money.put(event.getMessage().getAuthor(), 500d);
            MoneyData.goal.put(event.getMessage().getAuthor(), 2000d);
            MoneyData.bank.put(event.getMessage().getAuthor(), 0d);
            MoneyData.robGoalProgress.put(event.getMessage().getAuthor(), 0d);
            ProData.isPro.put(event.getMessage().getAuthor(), false);
            ShopData.fish.put(event.getAuthor(), 0);
            ShopData.gold.put(event.getAuthor(), 0);
            ShopData.silver.put(event.getAuthor(), 0);
            ShopData.legendaryDragon.put(event.getAuthor(), 0);
            ShopData.luckyCharm.put(event.getAuthor(), 0);
            ShopData.goldenEgg.put(event.getAuthor(), 0);
            DecimalFormat formatter = new DecimalFormat("#,###.00");

            double amount = MoneyData.money.get(event.getAuthor());
            event.getChannel().sendMessage(formatter.format(amount) + " added to " + event.getAuthor().getAsMention()).queue();
            LOGGER.info("Legendary Bot added 1,000 to " + event.getMessage().getAuthor().getAsMention() + " ...");

        }

        final double random = Math.random();

        if (MoneyData.money.containsKey(event.getAuthor())) {
            if (random < 0.05) {
                event.getChannel().sendMessage("Congratulations you are the lucky winner\n" +
                        "You found the golden egg\n" +
                        "200,000 has been deposited in your account!!!").queue();
                final Double cash = MoneyData.money.get(event.getAuthor());
                final Integer goldenEgg = ShopData.goldenEgg.get(event.getAuthor());
                ShopData.goldenEgg.put(event.getAuthor(), goldenEgg + 1);
                MoneyData.money.put(event.getMessage().getAuthor(), cash + 200_000d);
            }

            if (random < 0.01) {
                event.getChannel().sendMessage("Congratulations you are the lucky winner\n" +
                        "You found the legendary egg\n" +
                        "200,000 has been deposited in your account!!!").queue();
                final Integer goldenEgg = ShopData.goldenEgg.get(event.getAuthor());
                final Double cash = MoneyData.money.get(event.getAuthor());
                ShopData.gold.put(event.getAuthor(), goldenEgg + 1);
                MoneyData.money.put(event.getMessage().getAuthor(), cash + 400_000d);
            }
        }

        if (TriviaCommand.storeUser.contains(event.getAuthor())) {
            final String answer = event.getMessage().getContentRaw();
            if (answer.contains(TriviaCommand.storeAnswer.get(event.getAuthor()).toLowerCase())) {
                event.getChannel().sendMessage("Correct answer!!!!\n" +
                        "You got \uD83E\uDE99 500 for getting the correct answer").queue();
                final Double money = MoneyData.money.get(event.getAuthor());
                MoneyData.money.put(event.getAuthor(), money + 500);
            } else {
                EmbedBuilder e = new EmbedBuilder();
                e.setTitle("Incorrect answer");
                e.setFooter("A correct answer gives you \uD83E\uDE99 500");
                e.addField("The correct answer is " + TriviaCommand.storeAnswer.get(event.getAuthor()).toUpperCase(), "Better luck next time", false);
                event.getChannel().sendMessage(e.build()).queue();
            }
            TriviaCommand.storeUser.remove(event.getAuthor());
            TriviaCommand.storeAnswer.remove(event.getAuthor());
        }

        final long guildId = event.getGuild().getIdLong();
        String prefix = BadDesign.PREFIXES.computeIfAbsent(guildId, DatabaseManager.INSTANCE::getPrefix);

        if (event.getMessage().getContentRaw().equals(prefix + "enable") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            activate.put(event.getGuild(), true);
            event.getChannel().sendMessage("The message that the bot will send if you win has been set to enabled").queue();
            return;
        }

        if (event.getMessage().getContentRaw().equals(prefix + "gmod") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            mod.put(event.getGuild(), event.getMessage().getMentionedUsers().get(0));
            event.getChannel().sendMessage("The user that the bot will send to " + event.getMessage().getMentionedMembers().get(0) + "if the winner has sent a message").queue();
            return;
        }

        if (event.getMessage().getContentRaw().equals(prefix + "disable") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            activate.put(event.getGuild(), false);
            event.getChannel().sendMessage("The message that the bot will send if you win has been set to disabled").queue();
            return;
        }

        if (activate.containsKey(event.getGuild()) && activate.get(event.getGuild()).equals(true)) {
            if (event.getMessage().getContentRaw().contains(text) && event.getMessage().getContentRaw().contains("You won the") && event.getAuthor().getId().equals("294882584201003009")) {
                List<User> mention = event.getMessage().getMentionedUsers();



                for (User u : mention) {
                    if (message.containsKey(event.getGuild())) {
                        u.openPrivateChannel().queue(PrivateChannel ->
                                PrivateChannel.sendMessage("***Congratulations***\n** " +
                                        "You won the giveaway"
                                        + "** in **" + event.getGuild().getName()
                                        + "\n** Message from the admins of " + event.getGuild().getName() +":\n" +
                                        message.get(event.getGuild()))
                                        .queue());

                    } else {
                        u.openPrivateChannel().queue(PrivateChannel ->
                                PrivateChannel.sendMessage("***Congratulations***\n**"
                                        + "You won the giveaway"
                                        + "** in **" + event.getGuild().getName()
                                        + "\n** Kindly go to " + event.getGuild().getName() + " to claim your reward(s)!!!\n")
                                        .queue());

                    }
                }

                int x = 1;

                do {
                    guild.put(mention.get(x), event.getGuild());
                    x++;

                    event.getChannel().sendMessage("ADDED TEST").queue();


                } while (x <= event.getMessage().getMentionedMembers().size());
            }
        }

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }


        if (event.getMessage().getContentRaw().startsWith(prefix + "setmessage") && event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            String wow = event.getMessage().getContentRaw().substring(10 + prefix.length());
            message.put(event.getGuild(), wow);
            event.getChannel().sendMessage("The message was edited to " + wow).queue();
            return;
        }


        String raw = event.getMessage().getContentRaw();

        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && event.getAuthor().getId().equals(Config.get("owner_id"))) {
            LOGGER.info("The bot " + event.getAuthor().getAsMention() + " is shutting down.\n" +
                    "Nice code you did sir!!!");
            event.getChannel().sendMessage("Shutting down...").queue();
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        } else if (raw.equalsIgnoreCase(prefix + "shutdown")
                && event.getAuthor().getId().equals(Config.get("owner_id_partner"))) {
            LOGGER.info("Shutting down...");
            event.getChannel().sendMessage("Shutting down...").queue();
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

            return;
        }

        if (raw.startsWith(prefix)) {
            manager.handle(event, prefix);

        }
    }
}
