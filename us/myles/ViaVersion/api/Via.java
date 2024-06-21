/*    */ package us.myles.ViaVersion.api;
/*    */ 
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
/*    */ import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.SortedSet;
/*    */ import java.util.UUID;
/*    */ import us.myles.ViaVersion.api.boss.BossBar;
/*    */ import us.myles.ViaVersion.api.boss.BossColor;
/*    */ import us.myles.ViaVersion.api.boss.BossStyle;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class Via<T>
/*    */   implements ViaAPI<T>
/*    */ {
/* 38 */   private static final ViaAPI INSTANCE = new Via();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static ViaAPI getAPI() {
/* 45 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPlayerVersion(T player) {
/* 50 */     return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(player);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPlayerVersion(UUID uuid) {
/* 55 */     return com.viaversion.viaversion.api.Via.getAPI().getPlayerVersion(uuid);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInjected(UUID playerUUID) {
/* 60 */     return com.viaversion.viaversion.api.Via.getAPI().isInjected(playerUUID);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getVersion() {
/* 65 */     return com.viaversion.viaversion.api.Via.getAPI().getVersion();
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(T player, ByteBuf packet) {
/* 70 */     com.viaversion.viaversion.api.Via.getAPI().sendRawPacket(player, packet);
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendRawPacket(UUID uuid, ByteBuf packet) {
/* 75 */     com.viaversion.viaversion.api.Via.getAPI().sendRawPacket(uuid, packet);
/*    */   }
/*    */ 
/*    */   
/*    */   public BossBar createBossBar(String title, BossColor color, BossStyle style) {
/* 80 */     return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(title, 
/* 81 */           BossColor.values()[color.ordinal()], 
/* 82 */           BossStyle.values()[style.ordinal()]));
/*    */   }
/*    */ 
/*    */   
/*    */   public BossBar createBossBar(String title, float health, BossColor color, BossStyle style) {
/* 87 */     return new BossBar(com.viaversion.viaversion.api.Via.getAPI().legacyAPI().createLegacyBossBar(title, health, 
/* 88 */           BossColor.values()[color.ordinal()], 
/* 89 */           BossStyle.values()[style.ordinal()]));
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<Integer> getSupportedVersions() {
/* 94 */     return com.viaversion.viaversion.api.Via.getAPI().getSupportedVersions();
/*    */   }
/*    */ 
/*    */   
/*    */   public SortedSet<Integer> getFullSupportedVersions() {
/* 99 */     return com.viaversion.viaversion.api.Via.getAPI().getFullSupportedVersions();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar\\us\myles\ViaVersion\api\Via.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */