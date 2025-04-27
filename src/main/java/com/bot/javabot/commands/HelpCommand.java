package com.bot.javabot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class HelpCommand implements SlashCommand{
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        String helpMessage = """
                **Available Commands:**
                `/hello` - Says Hello
                `/chat` - Chat with the AI
                `/define` - Ask about any word
                `/user` - Displays the user information
                `/help` - Displays this help message
                `/ping` - Checks the bot's latency
                `/info` - Displays information about the bot
                """;

        event.reply(helpMessage).setEphemeral(true).queue();
    }
}
