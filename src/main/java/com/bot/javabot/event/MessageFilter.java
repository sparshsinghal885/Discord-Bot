package com.bot.javabot.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class MessageFilter extends ListenerAdapter {
    private static final List<String> ABUSIVE_WORDS = Arrays.asList(
            "shit", "bitch", "asshole", "f***", "bastard", "slut", "motherf***er", "n****r"
    );

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (!event.isFromGuild()) return;

        String messageContent = event.getMessage().getContentRaw().toLowerCase();

        for (String word : ABUSIVE_WORDS) {
            if (messageContent.contains(word.toLowerCase())) {
                Member member = event.getMember();
                User user = event.getAuthor();

                if (member == null) {
                    return;
                }

                EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("🚨 Abusive Language Detected");
                embed.setDescription("User **" + user.getName() + "** used abusive language!");
                embed.setColor(Color.RED);
                embed.setFooter("Action taken: Kicked");

                try {
                    Member selfMember = event.getGuild().getSelfMember();

                    if (!selfMember.hasPermission(net.dv8tion.jda.api.Permission.KICK_MEMBERS)) {
                        event.getChannel().sendMessage("I don't have permission to kick members in this server!").queue();
                        return;
                    }

                    if (member.canInteract(selfMember)) {
                        event.getChannel().sendMessage("I can't kick this user as they have higher roles than me!").queue();
                        return;
                    }

                    if (!selfMember.canInteract(member)) {
                        event.getChannel().sendMessage("I can't kick this user due to role hierarchy!").queue();
                        return;
                    }

                    event.getGuild().kick(member).queue(
                            success -> event.getChannel().sendMessageEmbeds(embed.build()).queue(),
                            error -> {
                                System.err.println("Error while trying to kick the member: " + error.getMessage());
                                event.getChannel().sendMessage("I couldn't kick the user: " + error.getMessage()).queue();
                            }
                    );
                } catch (Exception e) {
                    System.err.println("Error while trying to kick the member: " + e.getMessage());
                    event.getChannel().sendMessage("I couldn't take action against the user: " + e.getMessage()).queue();
                }

                break;
            }
        }
    }
}