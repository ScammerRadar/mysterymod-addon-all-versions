package de.kyleonaut.scammerradar_extension.command.trust;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.kyleonaut.scammerradar_extension.command.ICommand;
import de.kyleonaut.scammerradar_extension.entity.MojangUser;
import de.kyleonaut.scammerradar_extension.repository.MojangRepository;
import lombok.RequiredArgsConstructor;
import net.mysterymod.mod.MysteryMod;
import net.mysterymod.mod.profile.internal.trust.TrustedRepository;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 12.12.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TrustCommand implements ICommand {
  private final MojangRepository mojangRepository;
  private final TrustedRepository trustedRepository;
  private final String PREFIX = "§8[§6ScammerRadar Extension§8]";

  @Override
  public void execute(String message) {
    final String[] args = message.split(" ");
    if (args.length != 3) {
      MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Bitte benutze §e/trust <add | remove> <name>§7.");
      return;
    }
    final String targetName = args[2];
    if (args[1].equalsIgnoreCase("add")) {
      mojangRepository.getMojangUser(targetName).whenComplete((user, throwable) -> {
        if (user == null) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + targetName + "§7 konnte nicht gefunden werden.");
          return;
        }
        trustedRepository.add(MojangUser.format(user).getUUID(), user.getName()," ", new byte[]{});
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §aDer Spieler §2" + user.getName() + "§a wurde zu deiner Trusted Liste hinzugefügt.");
      });
      return;
    }
    if (args[1].equalsIgnoreCase("remove")) {
      mojangRepository.getMojangUser(targetName).whenComplete((user, throwable) -> {
        if (user == null) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + targetName + "§7 konnte nicht gefunden werden.");
          return;
        }
        trustedRepository.delete(user.getName());
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §aDer Spieler §2" + user.getName() + "§a wurde von deiner Trusted Liste entfernt.");
      });
      return;
    }
    MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Bitte benutze §e/trust <add | remove> <name>§7.");
  }
}
