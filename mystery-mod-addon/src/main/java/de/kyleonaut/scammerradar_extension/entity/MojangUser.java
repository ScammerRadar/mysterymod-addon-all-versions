package de.kyleonaut.scammerradar_extension.entity;

import lombok.Data;

import java.util.UUID;

/**
 * @author kyleonaut
 * @version 1.0.0
 * created at 12.12.2021
 */
@Data
public class MojangUser {
  private String id;
  private String name;

  public static MojangUser format(MojangUser mojangUser) {
    final MojangUser user = new MojangUser();
    user.setName(mojangUser.getName());
    user.setId(mojangUser.getId().replaceAll(
      "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
      "$1-$2-$3-$4-$5"));
    return user;
  }

  public UUID getUUID() {
    return UUID.fromString(this.id);
  }
}
