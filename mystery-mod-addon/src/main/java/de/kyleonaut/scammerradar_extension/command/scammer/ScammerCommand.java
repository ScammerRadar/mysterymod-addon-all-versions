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
  private final String PREFIX = "§8[§9ScammerRadar §3Extension§8]";

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
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §cEs konnte kein Spieler unter diesem Namen gefunden werden.");
          return;
        }
        scammerRepository.add(MojangUser.format(user).getUUID(), user.getName(), " ", new byte[]{});
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + user.getName() + "§7 wurde zu deiner §eScammer-Liste §ahinzugefügt.");
      });
    } else if (args[1].equalsIgnoreCase("remove")) {
      mojangRepository.getMojangUser(targetName).whenComplete((user, throwable) -> {
        if (user == null) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §cEs konnte kein Spieler unter diesem Namen gefunden werden.");
          return;
        }
        scammerRepository.delete(user.getName());
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + user.getName() + "§7 wurde von deiner §eScammer-Liste §centfernt.");
      });
    } else if (args[1].equalsIgnoreCase("check")) {
      mojangRepository.getMojangUser(targetName).whenComplete((mojangUser, throwable) -> {
        if (mojangUser == null) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §cEs konnte kein Spieler unter diesem Namen gefunden werden.");
          return;
        }
        final MojangUser user = MojangUser.format(mojangUser);
        if (scammerRepository.isScammer(user.getUUID())) {
          MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + user.getName() + "§7 ist in deiner §eScammer-Liste §aeingetragen.");
          return;
        }
        MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Der Spieler §e" + user.getName() + "§7 ist §cnicht§7 in deiner §eScammer-Liste §ceingetragen.");
      });
    } else {
      MysteryMod.getInstance().getMinecraft().addChatMessage(PREFIX + " §7Bitte benutze §e/scammer <add | remove | check> <name>§7.");
    }
  }
}
