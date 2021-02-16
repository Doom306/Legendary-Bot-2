package com.doom.commands.commands.Money;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;

public class WithdrawCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.bank.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money at all in your bank!!!\n" +
                    "You also think that you could withdraw something from nothing!!!").queue();
            return;
        }

        if (MoneyData.bank.get(ctx.getAuthor()) == 0) {
            ctx.getChannel().sendMessage("You have no money at all in your bank!!!\n" +
                    "You also think that you could withdraw something from nothing!!!").queue();
            return;
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("You did not mention how much to withdraw at all!!!").queue();
            return;
        }

        final Double moneyBank = MoneyData.bank.get(ctx.getAuthor());
        final Double moneyWallet = MoneyData.money.get(ctx.getAuthor());

        MoneyData.money.put(ctx.getAuthor(), (moneyWallet + (Integer.parseInt(ctx.getArgs().get(0)))));
        MoneyData.bank.put(ctx.getAuthor(), moneyBank - Integer.parseInt(ctx.getArgs().get(0)));

        ctx.getChannel().sendMessage("Successfully withdrawn \uD83E\uDE99 " + ctx.getArgs().get(0) + " to your wallet!!!").queue();
    }

    @Override
    public String getName() {
        return "with";
    }

    @Override
    public String getHelp() {
        return "Withdraws money to your wallet";
    }
}
