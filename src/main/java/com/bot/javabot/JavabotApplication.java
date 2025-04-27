package com.bot.javabot;

import com.bot.javabot.commands.*;
import com.bot.javabot.event.CommandDispatcher;
import com.bot.javabot.event.LoggingListener;
import com.bot.javabot.event.MessageFilter;
import com.bot.javabot.event.MessageReceivedListener;
import com.bot.javabot.service.GrokService;
import com.bot.javabot.service.GrokServiceImpl;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@SpringBootApplication
public class JavabotApplication {
	private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

	private static final String token = dotenv.get("DISCORD_TOKEN");

	public static void main(String[] args) {
		SpringApplication.run(JavabotApplication.class, args);
	}

	@Bean
	public GrokService grokService() {
		return new GrokServiceImpl();
	}

	@Bean
	public JDA jda(GrokService grokService) throws Exception {
		try {
			EnumSet<GatewayIntent> intents = EnumSet.of(
					GatewayIntent.GUILD_MESSAGES,
					GatewayIntent.GUILD_MEMBERS,
					GatewayIntent.GUILD_INVITES,
					GatewayIntent.MESSAGE_CONTENT,
					GatewayIntent.DIRECT_MESSAGES
			);

			JDA jda = JDABuilder.createDefault(token)
					.enableIntents(intents)
					.setActivity(Activity.customStatus("online"))
					.build();

			jda.awaitReady();
			System.out.println("🤖 Bot is ready!");

			List<CommandData> commandDataList = new ArrayList<>();
			commandDataList.add(Commands.slash("hello", "Say hello to the bot"));
			commandDataList.add(Commands.slash("chat", "Chat with the AI")
					.addOption(OptionType.STRING, "prompt", "Ask anything", true));
			commandDataList.add(Commands.slash("user", "Get the user information")
					.addOption(OptionType.STRING, "username", "Enter username", true));
			commandDataList.add(Commands.slash("help", "Displays this help message"));
			commandDataList.add(Commands.slash("ping", "Checks the bot's latency"));
			commandDataList.add(Commands.slash("info", "Displays information about the bot"));
			commandDataList.add(Commands.slash("define", "Get the definition of word")
					.addOption(OptionType.STRING, "word", "ask about any word", true));

			jda.updateCommands().addCommands(commandDataList).queue();

			List<SlashCommand> commandHandlers = List.of(
					new HelloCommand(),
					new ChatCommand(grokService),
					new HelpCommand(),
					new UserInfoCommand(),
					new InfoCommand(),
					new PingCommand(),
					new DefineCommand()
			);

			jda.addEventListener(new MessageReceivedListener());
			jda.addEventListener(new CommandDispatcher(commandHandlers));
			jda.addEventListener(new LoggingListener());
			jda.addEventListener(new MessageFilter());

			return jda;
		} catch (Exception e) {
			throw new IllegalStateException("Failed to start JDA bot", e);
		}
	}
}
