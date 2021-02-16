package com.doom.commands.commands.Others;

import com.doom.Config;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;

public class BugCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("No information placed!!!").queue();
            return;
        }

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Bug Form");
        embedBuilder.setFooter("We will contact you shortly");
        embedBuilder.addField("Kindly fill the form in the link below", "https://forms.gle/vXbCvRTt1vGYr1cu6", false);

        ctx.getChannel().sendMessage(embedBuilder.build()).queue();

        final String owner_id = Config.get("owner_id");
        User user = ctx.getJDA().getUserById(owner_id);
        user.openPrivateChannel().queue(PrivateChannel ->
                PrivateChannel.sendMessage("***New Bug Form by " + ctx.getAuthor().getAsMention() + "***\n"
                        + ctx.getArgs().get(0)
                        + "*** in ***" + ctx.getGuild().getName())
                        .queue());

        final String owner_id_partner = Config.get("owner_id");
        User user1 = ctx.getJDA().getUserById(owner_id_partner);
        user1.openPrivateChannel().queue(PrivateChannel ->
                PrivateChannel.sendMessage("***New Bug Form by " + ctx.getAuthor().getAsMention() + "***\n"
                        + ctx.getArgs().get(0)
                        + "*** in ***" + ctx.getGuild().getName())
                        .queue());
    }

    @Override
    public String getName() {
        return "bug";
    }

    @Override
    public String getHelp() {
        return "Fill out a form to report a bug.\n" +
                "Usage: `/bug [info]`";
    }
}
