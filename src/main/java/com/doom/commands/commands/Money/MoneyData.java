package com.doom.commands.commands.Money;

import net.dv8tion.jda.api.entities.User;

import java.util.HashMap;

public class MoneyData {
    public static HashMap<User, Double> money = new HashMap<>();
    public static HashMap<User, Double> bank = new HashMap<>();
    public static HashMap<User, Double> goal = new HashMap<>();
    public static HashMap<User, Double> robGoal = new HashMap<>();
    public static HashMap<User, Double> robGoalProgress = new HashMap<>();
    public static HashMap<User, Double> moneyGoalProgress = new HashMap<>();
}
