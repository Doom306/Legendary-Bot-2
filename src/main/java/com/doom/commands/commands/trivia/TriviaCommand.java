package com.doom.commands.commands.trivia;

import com.doom.Listener;
import com.doom.commands.CommandContext;
import com.doom.commands.ICommand;
import com.doom.commands.commands.Utils.UtilNum;
import com.doom.database.FilePath;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TriviaCommand implements ICommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    public static ArrayList<User> storeUser = new ArrayList();
    public static HashMap<User, String> storeAnswer = new HashMap<>();

    @Override
    public void handle(CommandContext ctx) {
        String respond = "", output = "";
        int totalline = 0;

        try {
            totalline = UtilNum.getLineCount(FilePath.EightBall);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        int magic = UtilNum.randomNum(0, totalline), line = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FilePath.EightBall));

            while((respond = reader.readLine()) != null) {
                line++;
                if(line >= magic)
                    break;
            }
            reader.close();

        } catch (IOException io) {
            LOGGER.info(this.getClass().getName(), io, "BufferedReader at getting response.");
        }

        String[] splitString = respond.split("-");
        String msg = ctx.getAuthor().getAsMention() + splitString[0];
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Trivia!!!");
        embedBuilder.addField("Question:", msg, false);
        embedBuilder.setColor(Color.cyan);
        embedBuilder.setFooter("A correct answer will give you \uD83E\uDE99 500");
        ctx.getChannel().sendMessage(embedBuilder.build()).queue();
        storeAnswer.put(ctx.getAuthor(), splitString[1]);
        storeUser.add(ctx.getAuthor());
    }


    @Override
    public String getName() {
        return "trivia";
    }

    @Override
    public String getHelp() {
        return "Sends a trivia message!!!";
    }
}
