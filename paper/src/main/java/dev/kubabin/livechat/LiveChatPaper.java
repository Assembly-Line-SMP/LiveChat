package dev.kubabin.livechat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class LiveChatPaper extends JavaPlugin {

    private final LivechatBase livechat = new LivechatBase();
    public class PaperLogger implements UniversalLogger {
        @Override
        public void info(String message) {
            getLogger().info(message);
        }

        @Override
        public void warn(String message) {
            getLogger().warning(message);
        }

        @Override
        public void error(String message) {
            getLogger().severe(message);
        }
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        livechat.setGameDir(getServer().getWorldContainer().toPath());
        FileConfiguration config = getConfig();
        livechat.startup(
                config.getString("http.host", "0.0.0.0"),
                config.getInt("http.port", 8080),
                new PaperLogger()
        );
        DiscordWebhook.enabled = config.getBoolean("discord.enabled", false);
        DiscordWebhook.webhook = config.getString("discord.url", "");
        getServer().getPluginManager().registerEvents(new LivechatEventsListener(livechat), this);
    }

    @Override
    public void onDisable() {
        livechat.shutdown();
    }
}
