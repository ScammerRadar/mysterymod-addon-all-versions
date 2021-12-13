package de.kyleonaut.scammerradar_extension.command.scammer;

import com.google.inject.Inject;
import de.kyleonaut.scammerradar_extension.command.ICommand;
import de.kyleonaut.scammerradar_extension.entity.MojangUser;
import de.kyleonaut.scammerradar_extension.repository.MojangRepository;
import lombok.RequiredArgsConstructor;
import net.mysterymod.mod.MysteryMod;
import net.mysterymod.mod.profile.internal.trust.ScammerRepository;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 12.12.2021
 */
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ScammerCommand implements ICommand {
  private final MojangRepository mojangRepository;
  private final ScammerRepository scammerRepository;
  private final String PREFIX = "§8[§6ScammerRadar Extension§8]";

  @Override
  public void execute(String message) {
    final String[] args = message.split(" ");
    if (args.length != 3) {
      MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Bitte benutze §e/scammer <add | remove | check> <name>§7.");
      return;
    }
    final String targetName = args[2];
    if (args[1].equalsIgnoreCase("add")) {
      mojangRepository.getMojangUser(targetName).whenComplete((user, throwable) -> {
        if (user == null) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + targetName + "§7 konnte nicht gefunden werden.");
          return;
        }
        scammerRepository.add(MojangUser.format(user).getUUID(), user.getName(), " ", new byte[]{});
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §aDer Spieler §2" + user.getName() + "§a wurde zu deiner Scammer Liste hinzugefügt.");
      });
    } else if (args[1].equalsIgnoreCase("remove")) {
      mojangRepository.getMojangUser(targetName).whenComplete((user, throwable) -> {
        if (user == null) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + targetName + "§7 konnte nicht gefunden werden.");
          return;
        }
        scammerRepository.delete(user.getName());
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §aDer Spieler §2" + user.getName() + "§a wurde von deiner Scammer Liste entfernt.");
      });
    } else if (args[1].equalsIgnoreCase("check")) {
      mojangRepository.getMojangUser(targetName).whenComplete((mojangUser, throwable) -> {
        if (mojangUser == null) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + targetName + "§7 konnte nicht gefunden werden.");
          return;
        }
        final MojangUser user = MojangUser.format(mojangUser);
        if (scammerRepository.isScammer(user.getUUID())) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §e" + user.getName() + "§7 ist in deiner Scammer Liste eingetragen");
          return;
        }
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §e" + user.getName() + "§7 ist nicht in deiner Scammer Liste eingetragen.");
      });
    } else {
      MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Bitte benutze §e/scammer <add | remove | check> <name>§7.");
    }
  }
}
