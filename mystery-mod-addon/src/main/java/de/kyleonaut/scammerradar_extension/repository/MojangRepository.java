package de.kyleonaut.scammerradar_extension.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.mysterymod.gson.Gson;
import de.kyleonaut.scammerradar_extension.entity.MojangUser;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 12.12.2021
 */
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MojangRepository implements IMojangRepository {
  private final OkHttpClient okHttpClient;

  @Override
  public CompletableFuture<MojangUser> getMojangUser(String name) {
    return CompletableFuture.supplyAsync(() -> {
      final Request request = new Request.Builder().url("https://api.mojang.com/users/profiles/minecraft/" + name).build();
      final Response response;
      try {
        response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
          return null;
        }
        if (response.body() == null) {
          return null;
        }
        final Gson gson = new Gson();
        return gson.fromJson(Objects.requireNonNull(response.body()).string(), MojangUser.class);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    });
  }
}
