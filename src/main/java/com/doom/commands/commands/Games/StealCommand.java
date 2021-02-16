package com.doom.commands.commands.Games;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Money.MoneyData;
import com.doom.commands.commands.Pro.ProData;
import net.dv8tion.jda.api.entities.User;

public class StealCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.money.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could steal a fellow").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getAuthor()).intValue() == 0) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could steal a fellow").queue();
            return;
        }

        if (ctx.getMessage().getMentionedMembers().isEmpty()) {
            ctx.getChannel().sendMessage("You did not mention who to steal at all!!!").queue();
            return;
        }

        if (!MoneyData.money.containsKey(ctx.getMessage().getMentionedUsers().get(0))) {
            ctx.getChannel().sendMessage("That person has no money at all!!!\n" +
                    "You think that you could steal that poor fellow").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getMessage().getMentionedUsers().get(0)).intValue() < 1000) {
            ctx.getChannel().sendMessage("That person has no money at all!!!\n" +
                    "You think that you could steal that poor fellow").queue();
            return;
        }

        final double random = Math.random();
        final User target = ctx.getMessage().getMentionedUsers().get(0);
        final Double targetMoney = MoneyData.money.get(target);
        final Double robber = MoneyData.money.get(ctx.getAuthor());
        final Double robProgress = MoneyData.robGoalProgress.get(ctx.getAuthor());

        if (random > 0.4) {
            int finalEarned;

            if (random * 10000 >= targetMoney) {
                finalEarned = targetMoney.intValue();
                MoneyData.money.put(target, targetMoney - finalEarned);
            } else {
                finalEarned = (int) (random * 10000);
                MoneyData.money.put(target, targetMoney - finalEarned);
            }

            if (ProData.isPro.get(ctx.getAuthor())) {
                MoneyData.money.put(ctx.getAuthor(), robber + finalEarned + 200);
                MoneyData.robGoalProgress.put(ctx.getAuthor(), robProgress + finalEarned);
            } else {
                MoneyData.money.put(ctx.getAuthor(), finalEarned + robber);
                MoneyData.robGoalProgress.put(ctx.getAuthor(), robProgress + finalEarned);
            }
            ctx.getChannel().sendMessage("You successfully robbed someone " + finalEarned).queue();

            final Double robGoal = MoneyData.robGoal.get(ctx.getAuthor());

            if (robGoal <= robProgress) {
                MoneyData.robGoal.put(ctx.getAuthor(), robGoal * 2);
            }
        } else {
            ctx.getChannel().sendMessage("You failed to robbed someone and lost 1000 coins").queue();
            final Double thecash = MoneyData.money.get(ctx.getAuthor());
            MoneyData.money.put(ctx.getAuthor(), thecash - 1000);
        }
    }

    @Override
    public String getName() {
        return "rob";
    }

    @Override
    public String getHelp() {
        return "Robs a user";
    }
}
