/*    */ package de.gerrygames.viarewind.protocol.protocol1_8to1_9.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import de.gerrygames.viarewind.protocol.protocol1_8to1_9.bossbar.WitherBossBar;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class BossBarStorage
/*    */   extends StoredObject {
/* 12 */   private final Map<UUID, WitherBossBar> bossBars = new HashMap<>();
/*    */   
/*    */   public BossBarStorage(UserConnection user) {
/* 15 */     super(user);
/*    */   }
/*    */   
/*    */   public void add(UUID uuid, String title, float health) {
/* 19 */     WitherBossBar bossBar = new WitherBossBar(getUser(), uuid, title, health);
/* 20 */     PlayerPosition playerPosition = (PlayerPosition)getUser().get(PlayerPosition.class);
/* 21 */     bossBar.setPlayerLocation(playerPosition.getPosX(), playerPosition.getPosY(), playerPosition.getPosZ(), playerPosition.getYaw(), playerPosition.getPitch());
/* 22 */     bossBar.show();
/* 23 */     this.bossBars.put(uuid, bossBar);
/*    */   }
/*    */   
/*    */   public void remove(UUID uuid) {
/* 27 */     WitherBossBar bossBar = this.bossBars.remove(uuid);
/* 28 */     if (bossBar == null)
/* 29 */       return;  bossBar.hide();
/*    */   }
/*    */   
/*    */   public void updateLocation() {
/* 33 */     PlayerPosition playerPosition = (PlayerPosition)getUser().get(PlayerPosition.class);
/* 34 */     this.bossBars.values().forEach(bossBar -> bossBar.setPlayerLocation(playerPosition.getPosX(), playerPosition.getPosY(), playerPosition.getPosZ(), playerPosition.getYaw(), playerPosition.getPitch()));
/*    */   }
/*    */   
/*    */   public void changeWorld() {
/* 38 */     this.bossBars.values().forEach(bossBar -> {
/*    */           bossBar.hide();
/*    */           bossBar.show();
/*    */         });
/*    */   }
/*    */   
/*    */   public void updateHealth(UUID uuid, float health) {
/* 45 */     WitherBossBar bossBar = this.bossBars.get(uuid);
/* 46 */     if (bossBar == null)
/* 47 */       return;  bossBar.setHealth(health);
/*    */   }
/*    */   
/*    */   public void updateTitle(UUID uuid, String title) {
/* 51 */     WitherBossBar bossBar = this.bossBars.get(uuid);
/* 52 */     if (bossBar == null)
/* 53 */       return;  bossBar.setTitle(title);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_8to1_9\storage\BossBarStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */