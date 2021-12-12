package de.kyleonaut.scammerradar_extension.repository;

import de.kyleonaut.scammerradar_extension.entity.MojangUser;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 12.12.2021
 */
public interface IMojangRepository {
  CompletableFuture<MojangUser> getMojangUser(String name) throws IOException;
}
