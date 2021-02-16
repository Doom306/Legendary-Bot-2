package com.doom.commands.commands.Money;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;

import java.text.DecimalFormat;

public class BalanceCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.money.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money in your wallet at all!!!").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getAuthor()).intValue() == 0) {
            ctx.getChannel().sendMessage("You have no money in your wallet at all!!!").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {

            DecimalFormat formatter = new DecimalFormat("#,###.00");

            double amount = MoneyData.money.get(ctx.getAuthor());

            ctx.getChannel().sendMessage(ctx.getMember().getAsMention() + " money is \uD83E\uDE99 " + formatter.format(amount)).queue();
        } else {
            DecimalFormat formatter = new DecimalFormat("#,###.00");

            double amount = MoneyData.money.get(ctx.getMessage().getMentionedUsers().get(0));

            ctx.getChannel().sendMessage(ctx.getMessage().getMentionedMembers().get(0).getAsMention() + " money is \uD83E\uDE99 " + formatter.format(amount)).queue();
        }
    }

    @Override
    public String getName() {
        return "bal";
    }

    @Override
    public String getHelp() {
        return "Checks the wallet balance";
    }
}
