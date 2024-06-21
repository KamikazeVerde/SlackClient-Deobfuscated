package com.viaversion.viaversion.api.legacy.bossbar;

import com.viaversion.viaversion.api.connection.UserConnection;
import java.util.Set;
import java.util.UUID;

public interface BossBar {
  String getTitle();
  
  BossBar setTitle(String paramString);
  
  float getHealth();
  
  BossBar setHealth(float paramFloat);
  
  BossColor getColor();
  
  BossBar setColor(BossColor paramBossColor);
  
  BossStyle getStyle();
  
  BossBar setStyle(BossStyle paramBossStyle);
  
  BossBar addPlayer(UUID paramUUID);
  
  BossBar addConnection(UserConnection paramUserConnection);
  
  BossBar removePlayer(UUID paramUUID);
  
  BossBar removeConnection(UserConnection paramUserConnection);
  
  BossBar addFlag(BossFlag paramBossFlag);
  
  BossBar removeFlag(BossFlag paramBossFlag);
  
  boolean hasFlag(BossFlag paramBossFlag);
  
  Set<UUID> getPlayers();
  
  Set<UserConnection> getConnections();
  
  BossBar show();
  
  BossBar hide();
  
  boolean isVisible();
  
  UUID getId();
}


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\legacy\bossbar\BossBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */