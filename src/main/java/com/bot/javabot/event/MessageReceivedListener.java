package com.bot.javabot.event;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        String messageSent = event.getMessage().getContentRaw();
        System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());

        if(event.getAuthor().isBot()){
            return;
        }

        String name = event.getAuthor().getName();

        if(messageSent.equalsIgnoreCase("hi") || messageSent.equalsIgnoreCase("hello")){
            event.getChannel().sendMessage("Hi! " + name).queue();
        }
    }
}
