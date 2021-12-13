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
    if (event.getComponent().getUnformatted().equals("[GrieferGames] Deine Daten wurden vollständig heruntergeladen.")) {
      MysteryMod.getInstance().getMinecraft().addChatMessage("§8[§6ScammerRadar Extension§8] §aScammer- und Trusted-Liste wurden analysiert.");
    }
  }
}
