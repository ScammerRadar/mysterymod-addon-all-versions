package de.kyleonaut.scammerradar_extension.listener;

import com.google.inject.Singleton;
import net.mysterymod.api.event.EventHandler;
import net.mysterymod.api.event.message.MessageReceiveEvent;
import net.mysterymod.mod.MysteryMod;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 13.12.2021
 */
@Singleton
public class ServerJoinListener {
  @EventHandler
  public void onChatReceive(MessageReceiveEvent event) {
    if (event.getComponent().getFormatted().equals("§8[§6GrieferGames§8] §7§aDeine Daten wurden vollständig heruntergeladen.")) {
      MysteryMod.getInstance().getMinecraft().addChatMessage("§8[§6ScammerRadar Extension§8] §aScammer Liste wird analysiert.");
      MysteryMod.getInstance().getMinecraft().addChatMessage("§8[§6ScammerRadar Extension§8] §aTrusted Liste wird analysiert.");
      MysteryMod.getInstance().getMinecraft().addChatMessage("§8[§6ScammerRadar Extension§8] §aScammer- und Trusted Liste wurde analysiert.");
    }
  }
}
