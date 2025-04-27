package com.bot.javabot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;

import java.awt.*;

public class DefineCommand implements SlashCommand{

    private final OkHttpClient client = new OkHttpClient();

    @Override
    public String getName() {
        return "define";
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        if(event.getOption("word") == null){
            event.reply("Please provide a word! 📚").queue();
            return;
        }

        String word = event.getOption("word").getAsString();

        try{
            Request request = new Request.Builder()
                    .url("https://api.dictionaryapi.dev/api/v2/entries/en/" + word)
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();

            JSONArray json = new JSONArray(responseBody);

            String definition = json.getJSONObject(0)
                    .getJSONArray("meanings")
                    .getJSONObject(0)
                    .getJSONArray("definitions")
                    .getJSONObject(0)
                    .getString("definition");

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("📖 Definition: " + word);
            embed.setDescription(definition);
            embed.setColor(Color.ORANGE);
            embed.setFooter("Powered by Free Dictionary API");

            event.replyEmbeds(embed.build()).queue();
        } catch (Exception e) {
            event.reply("Couldn't find the definition! ❌").queue();
        }
    }
}
