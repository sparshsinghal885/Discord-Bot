package com.bot.javabot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserInfoCommand implements SlashCommand {
    @Override
    public String getName() {
        return "user";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) {
            event.reply("❌ This command only works in servers!").setEphemeral(true).queue();
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        OptionMapping userOption = event.getOption("username");

        if (userOption == null) {
            event.reply("❌ You need to provide a username!").setEphemeral(true).queue();
            return;
        }

        String userName = userOption.getAsString();

        List<Member> members = event.getGuild().getMembersByName(userName, true);

        if (members.isEmpty() && userName.matches("\\d+")) {
            Member member = event.getGuild().getMemberById(userName);
            if (member != null) {
                members.add(member);
            }
        }

        if (members.isEmpty()) {
            members = event.getGuild().getMembersByNickname(userName, true);
        }

        if (members.isEmpty()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.addField("Error", "User `" + userName + "` not found in this server. 😅", false);
            event.replyEmbeds(eb.build()).setEphemeral(true).queue();
            return;
        }

        User user = members.get(0).getUser();
        Member member = members.get(0);
        EmbedBuilder eb = new EmbedBuilder();
        String avatar = user.getEffectiveAvatarUrl();

        eb.setTitle(user.getName() + "'s Info:");
        eb.setColor(Color.RED);
        eb.addField("Name: ", user.getName(), true);
        eb.addField("Online Status: ", member.getOnlineStatus().toString(), true);
        eb.addField("Avatar: ", "The Avatar is below", true);
        eb.setImage(avatar);
        eb.setFooter("Request was made @ " + formatter.format(date));
        event.replyEmbeds(eb.build()).setEphemeral(true).queue();
    }
}
