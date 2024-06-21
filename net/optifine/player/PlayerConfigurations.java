/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.optifine.http.FileDownloadThread;
/*    */ import net.optifine.http.HttpUtils;
/*    */ 
/*    */ public class PlayerConfigurations {
/* 13 */   private static Map mapConfigurations = null;
/* 14 */   private static boolean reloadPlayerItems = Boolean.getBoolean("player.models.reload");
/* 15 */   private static long timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public static void renderPlayerItems(ModelBiped modelBiped, AbstractClientPlayer player, float scale, float partialTicks) {
/* 19 */     PlayerConfiguration playerconfiguration = getPlayerConfiguration(player);
/*    */     
/* 21 */     if (playerconfiguration != null)
/*    */     {
/* 23 */       playerconfiguration.renderPlayerItems(modelBiped, player, scale, partialTicks);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static synchronized PlayerConfiguration getPlayerConfiguration(AbstractClientPlayer player) {
/* 29 */     if (reloadPlayerItems && System.currentTimeMillis() > timeReloadPlayerItemsMs + 5000L) {
/*    */       
/* 31 */       EntityPlayerSP entityPlayerSP = (Minecraft.getMinecraft()).thePlayer;
/*    */       
/* 33 */       if (entityPlayerSP != null) {
/*    */         
/* 35 */         setPlayerConfiguration(entityPlayerSP.getNameClear(), null);
/* 36 */         timeReloadPlayerItemsMs = System.currentTimeMillis();
/*    */       } 
/*    */     } 
/*    */     
/* 40 */     String s1 = player.getNameClear();
/*    */     
/* 42 */     if (s1 == null)
/*    */     {
/* 44 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 48 */     PlayerConfiguration playerconfiguration = (PlayerConfiguration)getMapConfigurations().get(s1);
/*    */     
/* 50 */     if (playerconfiguration == null) {
/*    */       
/* 52 */       playerconfiguration = new PlayerConfiguration();
/* 53 */       getMapConfigurations().put(s1, playerconfiguration);
/* 54 */       PlayerConfigurationReceiver playerconfigurationreceiver = new PlayerConfigurationReceiver(s1);
/* 55 */       String s = HttpUtils.getPlayerItemsUrl() + "/users/" + s1 + ".cfg";
/* 56 */       FileDownloadThread filedownloadthread = new FileDownloadThread(s, playerconfigurationreceiver);
/* 57 */       filedownloadthread.start();
/*    */     } 
/*    */     
/* 60 */     return playerconfiguration;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void setPlayerConfiguration(String player, PlayerConfiguration pc) {
/* 66 */     getMapConfigurations().put(player, pc);
/*    */   }
/*    */ 
/*    */   
/*    */   private static Map getMapConfigurations() {
/* 71 */     if (mapConfigurations == null)
/*    */     {
/* 73 */       mapConfigurations = new HashMap<>();
/*    */     }
/*    */     
/* 76 */     return mapConfigurations;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\player\PlayerConfigurations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */