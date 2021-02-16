package com.doom.commands.commands.Money;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;

import java.text.DecimalFormat;

public class BalanceBankCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.bank.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money in the bank at all!!!").queue();
            return;
        }

        if (MoneyData.bank.get(ctx.getAuthor()).intValue() == 0) {
            ctx.getChannel().sendMessage("You have no money at all!!!").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {

            DecimalFormat formatter = new DecimalFormat("#,###.00");

            double amount = MoneyData.bank.get(ctx.getAuthor());

            ctx.getChannel().sendMessage(ctx.getMember().getAsMention() + " bank money is \uD83E\uDE99 " + formatter.format(amount)).queue();
        } else {
            DecimalFormat formatter = new DecimalFormat("#,###.00");

            double amount = MoneyData.bank.get(ctx.getMessage().getMentionedUsers().get(0));

            ctx.getChannel().sendMessage(ctx.getMessage().getMentionedMembers().get(0).getAsMention() + " bank money is \uD83E\uDE99 " + formatter.format(amount)).queue();
        }
    }

    @Override
    public String getName() {
        return "bank";
    }

    @Override
    public String getHelp() {
        return "Checks the bank balance";
    }
}
