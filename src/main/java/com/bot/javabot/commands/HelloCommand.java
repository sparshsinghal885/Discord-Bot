package com.bot.javabot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class HelloCommand implements SlashCommand{
    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String name;
        if (event.isFromGuild()) {
            name = event.getMember().getEffectiveName();
        } else {
            name = event.getUser().getName();
        }
        event.reply("Hello " + name + "! 👋").queue();
    }
}
