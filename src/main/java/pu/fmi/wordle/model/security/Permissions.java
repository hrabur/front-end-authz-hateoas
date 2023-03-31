package pu.fmi.wordle.model.security;

public final class Permissions {

  private Permissions() {}

  public static final String GAME_PLAY = "game:play";
  public static final String GAME_QUERY_TOP10 = "game:query:top10";
  public static final String GAME_QUERY_MY_TOP10 = "game:query:myTop10";

  public static final String CONFIG_READ = "config:read";
  public static final String CONFIG_UPDATE = "config:update";
}
