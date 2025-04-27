package com.bot.javabot.commands;

import com.bot.javabot.service.GrokService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class ChatCommand implements SlashCommand {

    private final GrokService groqService;

    public ChatCommand(GrokService groqService) {
        this.groqService = groqService;
    }

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        if(event.getOption("prompt") == null){
            event.reply("Some error!\uD83D\uDE05").queue();
            return;
        }

        event.deferReply().queue();
        String prompt = event.getOption("prompt").getAsString();
        String finalPrompt = "You are a discord chat bot. Answer clearly, concisely, and only in a few sentences, generate output in markdown." + prompt;

        String response = groqService.sendRequestToGroq(finalPrompt);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("💬 Your Answer");
        embed.setDescription(response);
        embed.setColor(0xFFA500); // Orange color
        embed.setFooter("Asked by: " + event.getUser().getName(), event.getUser().getAvatarUrl());


        event.getHook().sendMessageEmbeds(embed.build()).queue();

    }
}

