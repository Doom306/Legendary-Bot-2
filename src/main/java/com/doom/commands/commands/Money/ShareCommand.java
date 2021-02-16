package com.doom.commands.commands.Money;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.entities.User;

public class ShareCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.money.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could share nothing to a fellow").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getAuthor()) == 0) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could share something to a fellow").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            ctx.getChannel().sendMessage("You did not mention who to give at all!!!").queue();
            return;
        }

        if (!MoneyData.money.containsKey(ctx.getMessage().getMentionedUsers().get(0))) {
            ctx.getChannel().sendMessage("That person has not created an account at all!!!\n" +
                    "Create an account by messaging anywhere with me to read it").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getMessage().getMentionedUsers().get(0)) < 1000) {
            ctx.getChannel().sendMessage("That person has no money at all!!!\n" +
                    "You think that you could help that fellow\n" +
                    "He needs to rise by himself.\n" +
                    "You can give that fellow cash when he got \uD83E\uDE99 1000").queue();
            return;
        }

        if (ctx.getArgs().get(0).isEmpty()) {
            ctx.getChannel().sendMessage("You didn't state how much am I to give at all!!!\n" +
                    "What am I supposed to give nothing?!?!").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getAuthor()) < Integer.parseInt(ctx.getArgs().get(0))) {
            ctx.getChannel().sendMessage("That person has no money at all!!!\n" +
                    "You think that you could help that fellow\n" +
                    "He needs to rise by himself.\n" +
                    "You can give that fellow cash when he got \uD83E\uDE99 1000").queue();
            return;
        }


        final Double moneyWallet = MoneyData.money.get(ctx.getAuthor());
        final User toBeGivenTo = ctx.getMessage().getMentionedUsers().get(0);
        final Double moneyOfReciever = MoneyData.bank.get(toBeGivenTo);

        MoneyData.money.put(ctx.getAuthor(), moneyWallet - Integer.parseInt(ctx.getArgs().get(0)));
        MoneyData.money.put(toBeGivenTo, moneyOfReciever + Integer.parseInt(ctx.getArgs().get(0)));
        ctx.getChannel().sendMessage("Successfully sent \uD83E\uDE99 " + ctx.getArgs().get(0) + " to " + toBeGivenTo.getAsMention()).queue();
    }

    @Override
    public String getName() {
        return "share";
    }

    @Override
    public String getHelp() {
        return "Share an amount of money to your friend\n" +
                "Usage: `/share [mention]`";
    }
}
