/*    */ package us.myles.ViaVersion.api;
/*    */ 
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public interface ViaAPI<T>
/*    */ {
/*    */   int getPlayerVersion(T paramT);
/*    */   
/*    */   int getPlayerVersion(UUID paramUUID);
/*    */   
/*    */   default boolean isPorted(UUID playerUUID) {
/* 44 */     return isInjected(playerUUID);
/*    */   }
/*    */   
/*    */   boolean isInjected(UUID paramUUID);
/*    */   
/*    */   String getVersion();
/*    */   
/*    */   void sendRawPacket(T paramT, ByteBuf paramByteBuf);
/*    */   
/*    */   void sendRawPacket(UUID paramUUID, ByteBuf paramByteBuf);
/*    */   
/*    */   BossBar createBossBar(String paramString, BossColor paramBossColor, BossStyle paramBossStyle);
/*    */   
/*    */   BossBar createBossBar(String paramString, float paramFloat, BossColor paramBossColor, BossStyle paramBossStyle);
/*    */   
/*    */   SortedSet<Integer> getSupportedVersions();
/*    */   
/*    */   SortedSet<Integer> getFullSupportedVersions();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar\\us\myles\ViaVersion\api\ViaAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */