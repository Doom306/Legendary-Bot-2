package com.doom.commands.commands.Shop;

import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

public class InventoryCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        StringBuilder message = new StringBuilder();

        final Integer fish = ShopData.fish.get(ctx.getAuthor());
        final Integer gold = ShopData.gold.get(ctx.getAuthor());
        final Integer silver = ShopData.silver.get(ctx.getAuthor());
        final Integer egg = ShopData.goldenEgg.get(ctx.getAuthor());
        final Integer charm = ShopData.luckyCharm.get(ctx.getAuthor());
        final Integer dragon = ShopData.legendaryDragon.get(ctx.getAuthor());

        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (fish > 0) {
            message.append("Fish - ").append(fish).append("\n");
        } else {
            message.append("Fish - 0");
        }

        if (gold > 0) {
            message.append("Gold - ").append(gold).append("\n");
        } else {
            message.append("Gold - 0");
        }

        if (charm > 0) {
            message.append("Charm - ").append(silver).append("\n");
        } else {
            message.append("Charm - 0");
        }

        if (dragon > 0) {
            message.append("Legendary Dragon - ").append(dragon).append("\n");
        } else {
            message.append("Legendary Dragon - 0");
        }

        if (silver > 0) {
            message.append("Silver - ").append(silver).append("\n");
        } else {
            message.append("Silver - 0");
        }


        if (egg > 0) {
            message.append("Golden Egg - ").append(egg).append("\n");
        } else {
            message.append("Golden Egg - 0");
        }

        embedBuilder.setTitle("Inventory");
        embedBuilder.setAuthor(ctx.getAuthor().getName() + "'s inventory");
        embedBuilder.addField(message.toString(), "", false);

        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
    }

    @Override
    public String getName() {
        return "inv";
    }

    @Override
    public String getHelp() {
        return "Shows your items in your inventory";
    }
}
