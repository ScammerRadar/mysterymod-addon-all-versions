package de.kyleonaut.scammerradar_extension.command;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.scammerradar_extension.command.scammer.ScammerCommand;
import de.kyleonaut.scammerradar_extension.command.trust.TrustCommand;
import de.kyleonaut.scammerradar_extension.repository.MojangRepository;
import net.mysterymod.api.event.EventHandler;
import net.mysterymod.api.event.message.MessageSendEvent;
import net.mysterymod.mod.MysteryMod;
import net.mysterymod.mod.profile.internal.trust.ScammerRepository;
import net.mysterymod.mod.profile.internal.trust.TrustedRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 12.12.2021
 */
@Singleton
public class CommandHandler {
  private final Map<String, ICommand> commandMap;
  private final String prefix;

  @Inject
  public CommandHandler(TrustedRepository trustedRepository, ScammerRepository scammerRepository, MojangRepository mojangRepository) {
    this.prefix = "/";
    this.commandMap = new HashMap<>();
    this.commandMap.put("scammer", new ScammerCommand(mojangRepository, scammerRepository));
    this.commandMap.put("trust", new TrustCommand(mojangRepository, trustedRepository));
  }

  @EventHandler
  public void onSend(MessageSendEvent event) {
    if (event.getMessage().equalsIgnoreCase("#author")) {
      MysteryMod.getInstance().getMinecraft().addChatMessage("§8[§6ScammerRadar Extension§8] §aAuthor: kyleonaut");
      event.setCancelled(true);
    }
    if (!event.getMessage().startsWith(this.prefix)) {
      return;
    }
    final String command = event.getMessage().split("/")[1];
    final String key = command.split(" ")[0];
    if (this.commandMap.containsKey(key.toLowerCase())) {
      event.setCancelled(true);
      this.commandMap.get(key.toLowerCase()).execute(event.getMessage());
    }
  }
}
