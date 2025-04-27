package com.bot.javabot.event;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.time.Instant;
import java.util.List;

public class LoggingListener extends ListenerAdapter {

    private void sendLog(TextChannel channel, String title, String description, Color color) {
        if (channel == null || !channel.canTalk()) {
            System.out.println("[Logging Error] Cannot send message to " + (channel == null ? "null channel" : channel.getName()));
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setDescription(description);
        eb.setColor(color);
        eb.setTimestamp(Instant.now());
        channel.sendMessageEmbeds(eb.build()).queue();
    }

    private TextChannel getLogChannel(@NotNull net.dv8tion.jda.api.entities.Guild guild) {
        List<TextChannel> logChannels = guild.getTextChannelsByName("logs", true);
        if (!logChannels.isEmpty()) return logChannels.get(0);

        List<TextChannel> generalChannels = guild.getTextChannelsByName("general", true);
        if (!generalChannels.isEmpty()) return generalChannels.get(0);

        return guild.getTextChannels().isEmpty() ? null : guild.getTextChannels().get(0);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        TextChannel logChannel = getLogChannel(event.getGuild());
        sendLog(logChannel, "✅ Member Joined",
                "**" + event.getUser().getAsTag() + "** has joined the server!",
                Color.GREEN);
    }

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
        TextChannel logChannel = getLogChannel(event.getGuild());
        sendLog(logChannel, "❌ Member Left",
                "**" + event.getUser().getAsTag() + "** has left the server.",
                Color.ORANGE);
    }

    @Override
    public void onGuildBan(GuildBanEvent event) {
        TextChannel logChannel = getLogChannel(event.getGuild());
        sendLog(logChannel, "🚫 Member Banned",
                "**" + event.getUser().getAsTag() + "** was banned from the server!",
                Color.RED);
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        TextChannel logChannel = getLogChannel(event.getGuild());
        sendLog(logChannel, "🗑️ Message Deleted",
                "A message was deleted in **#" + event.getChannel().getName() + "**.",
                Color.GRAY);
    }
}
