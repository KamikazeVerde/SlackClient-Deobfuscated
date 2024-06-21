/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class PlayerItemsLayer implements LayerRenderer {
/* 15 */   private RenderPlayer renderPlayer = null;
/*    */ 
/*    */   
/*    */   public PlayerItemsLayer(RenderPlayer renderPlayer) {
/* 19 */     this.renderPlayer = renderPlayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entityLiving, float limbSwing, float limbSwingAmount, float partialTicks, float ticksExisted, float headYaw, float rotationPitch, float scale) {
/* 24 */     renderEquippedItems(entityLiving, scale, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderEquippedItems(EntityLivingBase entityLiving, float scale, float partialTicks) {
/* 29 */     if (Config.isShowCapes())
/*    */     {
/* 31 */       if (entityLiving instanceof AbstractClientPlayer) {
/*    */         
/* 33 */         AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entityLiving;
/* 34 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 35 */         GlStateManager.disableRescaleNormal();
/* 36 */         GlStateManager.enableCull();
/* 37 */         ModelPlayer modelPlayer = this.renderPlayer.getMainModel();
/* 38 */         PlayerConfigurations.renderPlayerItems((ModelBiped)modelPlayer, abstractclientplayer, scale, partialTicks);
/* 39 */         GlStateManager.disableCull();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void register(Map renderPlayerMap) {
/* 51 */     Set set = renderPlayerMap.keySet();
/* 52 */     boolean flag = false;
/*    */     
/* 54 */     for (Object object : set) {
/*    */       
/* 56 */       Object object1 = renderPlayerMap.get(object);
/*    */       
/* 58 */       if (object1 instanceof RenderPlayer) {
/*    */         
/* 60 */         RenderPlayer renderplayer = (RenderPlayer)object1;
/* 61 */         renderplayer.addLayer(new PlayerItemsLayer(renderplayer));
/* 62 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 66 */     if (!flag)
/*    */     {
/* 68 */       Config.warn("PlayerItemsLayer not registered");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\player\PlayerItemsLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */