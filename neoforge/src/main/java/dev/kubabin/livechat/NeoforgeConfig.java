package dev.kubabin.livechat;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = LivechatNeoforge.MODID)
public class NeoforgeConfig
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue PORT = BUILDER
            .comment("The port the HTTP server will listen on")
            .defineInRange("port", 8080, 1, 65535);
    private static final ModConfigSpec.ConfigValue<String> HOST = BUILDER
            .comment("The host the HTTP server will bind to")
            .define("host", "0.0.0.0");
    private static final ModConfigSpec.BooleanValue ENABLE_DISCORD_WEBHOOK = BUILDER
            .comment("Whether to enable sending chat messages to Discord via webhook")
            .define("enableDiscordWebhook", true);
    private static final ModConfigSpec.ConfigValue<String> DISCORD_WEBHOOK_URL = BUILDER
            .comment("The Discord webhook URL to send chat messages to")
            .define("discordWebhookUrl", "https://discord.com/api/webhooks/your_webhook_url_here");
    static final ModConfigSpec SPEC = BUILDER.build();
    public static int port;
    public static String host;
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        port = PORT.get();
        host = HOST.get();
        DiscordWebhook.webhook = DISCORD_WEBHOOK_URL.get();
        DiscordWebhook.enabled = ENABLE_DISCORD_WEBHOOK.get();
    }
}
