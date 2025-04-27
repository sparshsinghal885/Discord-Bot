# JavaBot

JavaBot is a simple yet powerful Discord bot built using Java and the [JDA library](https://github.com/DV8FromTheWorld/JDA). It includes several commands such as greeting users, chatting with an AI, providing user info, and more! This bot is designed to be easily customizable and deployable.

## Features

- **Slash Commands**: The bot supports multiple commands such as `/hello`, `/chat`, `/ping`, `/user`, `/help`, and more.
- **Abusive Language Detection**: The bot filters abusive language and can take action like warning or kicking users.
- **Customizable AI Integration**: Integrated with Grok API for intelligent responses.
- **User Info Retrieval**: Fetches user info like online status, name, and avatar.
- **Easy Deployment**: Simple deployment on platforms like Railway.

## Commands

- `/hello`: Say hello to the bot.
- `/chat [prompt]`: Chat with the bot using an AI model.
- `/user [username]`: Get user information in the server.
- `/ping`: Check the bot's latency.
- `/help`: Display help message with all available commands.
- `/info`: Display information about the bot.
- `/define [word]`: Get the definition of any word.

## Prerequisites

- Java 17 or later
- Maven or Gradle for dependency management
- Discord Developer Account to create a bot and get the token
- [Grok API Key](https://grokapi.com) (for AI-related features)

## Setup

1. Clone the repository:

    ```bash
    git clone https://github.com/sparshsinghal885/Discord-Bot.git
    cd Discord-Bot
    ```

2. Create a `.env` file in the root of the project and add the following values:

    ```bash
    DISCORD_TOKEN=your-discord-bot-token-here
    GROK_API_KEY=your-grok-api-key-here
    ```

3. Build the project using Maven or Gradle:

    For Maven:

    ```bash
    mvn clean install
    ```

    For Gradle:

    ```bash
    ./gradlew build
    ```

4. Run the bot:

    ```bash
    java -jar target/your-bot.jar
    ```

## Deployment on Railway

1. **Push your code to GitHub**.
2. **Create a new project on Railway**.
3. **Link your GitHub repository**.
4. Set up **Environment Variables** on Railway:
    - `DISCORD_TOKEN=your-discord-bot-token-here`
    - `GROK_API_KEY=your-grok-api-key-here`
5. Railway will automatically deploy your bot and it will be up and running.

## Contributing

Feel free to fork the repository, make changes, and submit pull requests. Contributions are always welcome!

## Acknowledgements

- [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA) for providing the framework to interact with Discord.
- [Spring Boot](https://spring.io/projects/spring-boot) for the easy setup of the bot.
- [Grok API](https://grokapi.com) for AI-related functionalities.

---

Made with ❤️ by sparshsinghal885
