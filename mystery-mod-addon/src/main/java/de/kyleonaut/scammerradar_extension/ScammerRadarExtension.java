package de.kyleonaut.scammerradar_extension;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.kyleonaut.scammerradar_extension.command.CommandHandler;
import de.kyleonaut.scammerradar_extension.listener.ServerJoinListener;
import lombok.RequiredArgsConstructor;
import net.mysterymod.api.listener.ListenerChannel;
import net.mysterymod.mod.addon.Addon;

import java.util.logging.Level;
import java.util.logging.Logger;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ScammerRadarExtension extends Addon {
  private final Logger logger;
  private final ListenerChannel listenerChannel;
  private final Injector injector;

  @Override
  public void onEnable() {
    logger.log(Level.INFO, "Starting ScammerRadarExtension Addon");
    listenerChannel.registerListener(injector.getInstance(CommandHandler.class));
    listenerChannel.registerListener(injector.getInstance(ServerJoinListener.class));
  }
}
