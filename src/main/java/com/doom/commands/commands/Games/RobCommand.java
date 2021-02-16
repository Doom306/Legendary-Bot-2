package com.doom.commands.commands.Games;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Money.MoneyData;
import com.doom.commands.commands.Pro.ProData;
import net.dv8tion.jda.api.entities.User;

public class RobCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.money.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could bank rob a fellow").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getAuthor()).intValue() == 0) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could bank rov a fellow").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getAuthor()).intValue() < 5000) {
            ctx.getChannel().sendMessage("You don't have 4000 in your wallet. SO GET SOME NOW!!!").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            ctx.getChannel().sendMessage("You did not mention who to bank rob at all!!!").queue();
            return;
        }

        if (!MoneyData.bank.containsKey(ctx.getMessage().getMentionedUsers().get(0))) {
            ctx.getChannel().sendMessage("That person has no money at all!!!\n" +
                    "You think that you could bank rob that poor fellow").queue();
            return;
        }

        if (MoneyData.bank.get(ctx.getMessage().getMentionedUsers().get(0)).intValue() < 4000) {
            ctx.getChannel().sendMessage("That person has no money at all!!!\n" +
                    "You think that you could bank rob that poor fellow").queue();
            return;
        }

        final double random = Math.random();
        final User target = ctx.getMessage().getMentionedUsers().get(0);
        final Double targetMoney = MoneyData.bank.get(target);
        final Double robber = MoneyData.money.get(ctx.getAuthor());

        MoneyData.money.put(ctx.getAuthor(), robber - 4000);

        if (random > 0.5) {
            int finalEarned;

            if (random * 100000 >= targetMoney) {
                finalEarned = targetMoney.intValue();
                MoneyData.money.put(target, targetMoney - finalEarned);
            } else {
                finalEarned = (int) (random * 10000);
                MoneyData.money.put(target, targetMoney - finalEarned);
            }

            if (ProData.isPro.get(ctx.getAuthor())) {
                MoneyData.money.put(ctx.getAuthor(), robber + finalEarned + 1000);
                ctx.getChannel().sendMessage("You earned a 1000 bonus for being a PRO user.").queue();
            } else {
                MoneyData.money.put(ctx.getAuthor(), finalEarned + robber);
            }
            ctx.getChannel().sendMessage("You successfully robbed someone " + finalEarned).queue();
        } else {
            ctx.getChannel().sendMessage("You failed to  bank rob someone and lost 4000 more coins").queue();
            final Double theCash = MoneyData.money.get(ctx.getAuthor());
            MoneyData.money.put(ctx.getAuthor(), theCash - 4000);
        }
    }

    @Override
    public String getName() {
        return "bankrob";
    }

    @Override
    public String getHelp() {
        return "Bank Robs a user\n" +
                "Entry Fee: 4000\n" +
                "Amount of money needed is 5000\n" +
                "1000 will be lost for a failed robbery\n" +
                "Usage: `/rob [mention]`";
    }
}
