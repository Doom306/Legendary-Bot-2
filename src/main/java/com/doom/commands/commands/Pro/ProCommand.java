package com.doom.commands.commands.Pro;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Money.MoneyData;

public class ProCommand implements ICommand {
    public static double amount = 100_000.00;

    @Override
    public void handle(CommandContext ctx) {
        if (MoneyData.money.containsKey(ctx.getAuthor())) {
            if (MoneyData.money.get(ctx.getAuthor()).intValue() >= 100_000) {
                ProData.isPro.put(ctx.getAuthor(), true);
                final Double aDouble = MoneyData.money.get(ctx.getAuthor());
                MoneyData.money.put(ctx.getAuthor(), aDouble - 100_000);
                ctx.getChannel().sendMessage("Legendary Bot Pro has been purchased").queue();
                ctx.getChannel().sendMessage("\uD83E\uDE99 " + amount + " has been deducted from your account in Legendary bot").queue();
            }

            ctx.getChannel().sendMessage("You lack the funds to buy this cool product").queue();
        }
    }

    @Override
    public String getName() {
        return "pro";
    }

    @Override
    public String getHelp() {
        return "Buys Pro\n" +
                "Pro gives more rewards and commands";
    }
}
