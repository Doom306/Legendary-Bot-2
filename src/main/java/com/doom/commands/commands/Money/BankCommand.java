package com.doom.commands.commands.Money;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Pro.ProData;

public class BankCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.money.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money at all!!!").queue();
            return;
        }

        if (!MoneyData.moneyGoalProgress.containsKey(ctx.getAuthor())) {
            MoneyData.moneyGoalProgress.put(ctx.getAuthor(), 0d);
        }
        if (MoneyData.money.get(ctx.getAuthor()).intValue() <= 0) {
            ctx.getChannel().sendMessage("You have no money at all!!!").queue();
            return;
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("You did not mention how much at all!!!").queue();
            return;
        }

        final Double money = MoneyData.bank.get(ctx.getAuthor());
        MoneyData.bank.put(ctx.getAuthor(), (Double.parseDouble(ctx.getArgs().get(0))) + money);
        ctx.getChannel().sendMessage(ctx.getMember().getAsMention() + " \uD83E\uDE99 " + ctx.getArgs().get(0) + " has been deposited").queue();
        final Double progress = MoneyData.moneyGoalProgress.get(ctx.getAuthor());
        MoneyData.moneyGoalProgress.put(ctx.getAuthor(), progress + Double.parseDouble(ctx.getArgs().get(0)));


        if (MoneyData.moneyGoalProgress.get(ctx.getAuthor()) >= MoneyData.goal.get(ctx.getAuthor())) {
            if (ProData.isPro.get(ctx.getAuthor())) {
                final Double goal = MoneyData.goal.get(ctx.getAuthor());
                MoneyData.bank.put(ctx.getAuthor(), 2000 + money);
                ctx.getChannel().sendMessage(ctx.getMember().getAsMention() + " " + "\uD83E\uDE99 2000 coins has been deposited for reaching " + goal + " in your bank.").queue();
                MoneyData.goal.put(ctx.getAuthor(), goal * 2);
            } else {
                final Double goal = MoneyData.goal.get(ctx.getAuthor());
                MoneyData.bank.put(ctx.getAuthor(), 1000 + money);
                ctx.getChannel().sendMessage(ctx.getMember().getAsMention() + " "  + "\uD83E\uDE99 1000 coins has been deposited for reaching " + goal + " in your bank.").queue();
                MoneyData.goal.put(ctx.getAuthor(), goal * 2);
            }
        }
    }

    @Override
    public String getName() {
        return "dep";
    }

    @Override
    public String getHelp() {
        return "Deposits money at your bank";
    }
}
