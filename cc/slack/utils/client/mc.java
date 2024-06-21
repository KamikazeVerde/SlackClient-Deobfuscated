/*    */ package cc.slack.utils.client;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*    */ import net.minecraft.client.multiplayer.WorldClient;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.renderer.EntityRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.Timer;
/*    */ 
/*    */ public class mc
/*    */ {
/*    */   public static Minecraft getMinecraft() {
/* 26 */     return Minecraft.getMinecraft();
/*    */   }
/*    */   
/*    */   public static Timer getTimer() {
/* 30 */     return (getMinecraft()).timer;
/*    */   }
/*    */   
/*    */   public static EntityRenderer getEntityRenderer() {
/* 34 */     return (getMinecraft()).entityRenderer;
/*    */   }
/*    */   
/*    */   public static EntityPlayerSP getPlayer() {
/* 38 */     return (getMinecraft()).thePlayer;
/*    */   }
/*    */   
/*    */   public static WorldClient getWorld() {
/* 42 */     return (getMinecraft()).theWorld;
/*    */   }
/*    */   
/*    */   public static FontRenderer getFontRenderer() {
/* 46 */     return (getMinecraft()).MCfontRenderer;
/*    */   }
/*    */   
/*    */   public static PlayerControllerMP getPlayerController() {
/* 50 */     return (getMinecraft()).playerController;
/*    */   }
/*    */   
/*    */   public static NetHandlerPlayClient getNetHandler() {
/* 54 */     return getMinecraft().getNetHandler();
/*    */   }
/*    */   
/*    */   public static GameSettings getGameSettings() {
/* 58 */     return (getMinecraft()).gameSettings;
/*    */   }
/*    */   
/*    */   public static GuiScreen getCurrentScreen() {
/* 62 */     return (getMinecraft()).currentScreen;
/*    */   }
/*    */   
/*    */   public static List<EntityPlayer> getLoadedPlayers() {
/* 66 */     return (getWorld()).playerEntities;
/*    */   }
/*    */   public static TextureManager getTextureManager() {
/* 69 */     return getMinecraft().getTextureManager();
/*    */   } public static RenderManager getRenderManager() {
/* 71 */     return getMinecraft().getRenderManager();
/*    */   } public static ScaledResolution getScaledResolution() {
/* 73 */     return new ScaledResolution(getMinecraft());
/*    */   }
/*    */   public static List<EntityLivingBase> getLivingEntities(Predicate<EntityLivingBase> validator) {
/* 76 */     List<EntityLivingBase> entities = new ArrayList<>();
/*    */     
/* 78 */     (getWorld()).loadedEntityList.forEach(entity -> {
/*    */           if (entity instanceof EntityLivingBase) {
/*    */             EntityLivingBase ent = (EntityLivingBase)entity;
/*    */             if (validator.test(ent)) {
/*    */               entities.add(ent);
/*    */             }
/*    */           } 
/*    */         });
/* 86 */     return entities;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\client\mc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */