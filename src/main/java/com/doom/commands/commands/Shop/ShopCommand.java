package com.doom.commands.commands.Shop;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Money.MoneyData;

public class ShopCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        if (!MoneyData.money.containsKey(ctx.getAuthor())) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could buy something").queue();
            return;
        }

        if (MoneyData.money.get(ctx.getAuthor()).intValue() == 0) {
            ctx.getChannel().sendMessage("You have no money at all!!!\n" +
                    "You also think that you could buy something").queue();
            return;
        }

        if (ctx.getArgs().isEmpty()) {
            ctx.getChannel().sendMessage("***Shop***\n" +
                    "1.) Fish\n" +
                    "- `/shop fish`\n" +
                    "2.) Gold\n" +
                    "- `/shop gold`\n" +
                    "3.) Silver\n" +
                    "- `/shop silver`\n" +
                    "4.) Lucky Charm\n" +
                    "- `/shop charm\n\n" +
                    "RARE\n" +
                    "1.) Legendary Dragon\n" +
                    "- `/shop dragon`\n" +
                    "2.) Golden Egg\n" +
                    "- `/shop egg").queue();
            return;
        }

        int howManyCount = 1;

        if (!ctx.getArgs().get(1).isEmpty()) {
            howManyCount = Integer.parseInt(ctx.getArgs().get(1));
        }

        final Integer fish = ShopData.fish.get(ctx.getAuthor());
        final Integer gold = ShopData.gold.get(ctx.getAuthor());
        final Integer silver = ShopData.silver.get(ctx.getAuthor());
        final Integer egg = ShopData.goldenEgg.get(ctx.getAuthor());
        final Integer charm = ShopData.luckyCharm.get(ctx.getAuthor());
        final Integer dragon = ShopData.legendaryDragon.get(ctx.getAuthor());
        final Double money = MoneyData.money.get(ctx.getAuthor());
        final String item  = ctx.getArgs().get(0);

        if (item.equals("fish")) {
            if (money >= (400 * howManyCount)) {
                    ctx.getChannel().sendMessage("Fish has been successfully bought for \uD83E\uDE99 " + (400 * howManyCount) + ".").queue();
                    ShopData.fish.put(ctx.getAuthor(), fish + howManyCount);
                    MoneyData.money.put(ctx.getAuthor(), money - (400* howManyCount));
                    return;
            }

            ctx.getChannel().sendMessage("You don't have \uD83E\uDE99 " + 400 * howManyCount + ".\n" +
                    "Unable to purchase!!!").queue();
            return;
        }

        if (item.equals("gold")) {
            if (money >= (4000 * howManyCount)) {
                ctx.getChannel().sendMessage("Gold has been successfully bought for \uD83E\uDE99 " + (howManyCount * 4000) + ".").queue();
                ShopData.gold.put(ctx.getAuthor(), gold + howManyCount);
                MoneyData.money.put(ctx.getAuthor(), money - (4000 * howManyCount));
                return;
            }

            ctx.getChannel().sendMessage("You don't have \uD83E\uDE99 " + 4000 * howManyCount + ".\n" +
                    "Unable to purchase!!!").queue();
            return;
        }

        if (item.equals("silver")) {
            if (money >= (2000 * howManyCount)) {
                ctx.getChannel().sendMessage("Silver has been successfully bought for \uD83E\uDE99 " + 2000 * howManyCount + ".").queue();
                ShopData.silver.put(ctx.getAuthor(), silver + howManyCount);
                MoneyData.money.put(ctx.getAuthor(), money - (2000 * howManyCount));
                return;
            }

            ctx.getChannel().sendMessage("You don't have \uD83E\uDE99 " + 2000 * howManyCount + ".\n" +
                    "Unable to purchase!!!").queue();
            return;
        }

        if (item.equals("egg")) {
            if (money >= 400_000 * howManyCount) {
                ctx.getChannel().sendMessage("Golden Egg has been successfully bought for \uD83E\uDE99 " + 400000 * howManyCount + ".").queue();
                ShopData.goldenEgg.put(ctx.getAuthor(), egg + howManyCount);
                MoneyData.money.put(ctx.getAuthor(), money - (400000* howManyCount));
                return;
            }

            ctx.getChannel().sendMessage("You don't have \uD83E\uDE99 " + 400000 * howManyCount + ".\n" +
                    "Unable to purchase!!!").queue();
            return;
        }

        if (item.equals("charm")) {
            if (money >= 50_000 * howManyCount) {
                ctx.getChannel().sendMessage("Lucky Charm has been successfully bought for \uD83E\uDE99 " + howManyCount * 50000 + ".").queue();
                ShopData.luckyCharm.put(ctx.getAuthor(), charm + howManyCount);
                MoneyData.money.put(ctx.getAuthor(), money - (howManyCount * 50000));
                return;
            }

            ctx.getChannel().sendMessage("You don't have \uD83E\uDE99 " + howManyCount * 50000 + ".\n" +
                    "Unable to purchase!!!").queue();
            return;
        }

        if (item.equals("dragon")) {
            if (money >= 100_000 * howManyCount) {
                ctx.getChannel().sendMessage("Legendary Dragon has been successfully bought for \uD83E\uDE99 " + 100000 * howManyCount + ".").queue();
                ShopData.legendaryDragon.put(ctx.getAuthor(), (dragon + howManyCount));
                MoneyData.money.put(ctx.getAuthor(), money - (100000 * howManyCount));
                return;
            }

            ctx.getChannel().sendMessage("You don't have \uD83E\uDE99 100,000.\n" +
                    "Unable to purchase!!!").queue();
            return;
        }

        ctx.getChannel().sendMessage("Invalid input!!!").queue();
        ctx.getChannel().sendMessage("***Shop***\n" +
                "1.) Fish\n" +
                "- `/shop fish`\n" +
                "2.) Gold\n" +
                "- `/shop gold`\n" +
                "3.) Silver\n" +
                "- `/shop silver`\n" +
                "4.) Lucky Charm\n" +
                "- `/shop charm`\n\n" +
                "***RARE***\n" +
                "1.) Legendary Dragon\n" +
                "- `/shop dragon`\n" +
                "2.) Golden Egg\n" +
                "- `/shop egg`").queue();
    }

    @Override
    public String getName() {
        return "shop";
    }

    @Override
    public String getHelp() {
        return "Shop for items";
    }
}
