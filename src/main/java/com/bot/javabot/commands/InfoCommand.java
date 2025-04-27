package com.bot.javabot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class InfoCommand implements SlashCommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("🤖 Bot Information");
        embed.setDescription("This bot is built with Java + Spring Boot + JDA! 🚀");
        embed.addField("Developer", "Sparsh Singhal", false);
        embed.addField("GitHub", "[Visit My GitHub](https://github.com/sparshsinghal885)", false);
        embed.addField("Language", "Java", true);
        embed.addField("Framework", "Spring Boot", true);
        embed.setColor(Color.ORANGE);

        event.replyEmbeds(embed.build()).queue();
    }
}
