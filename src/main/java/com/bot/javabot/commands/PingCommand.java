package com.bot.javabot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class PingCommand implements SlashCommand {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Pong! 🏓").flatMap(v ->
                event.getHook().editOriginalFormat("Pong! 🏓 `%d ms`", System.currentTimeMillis() - time)
        ).queue();
    }
}
