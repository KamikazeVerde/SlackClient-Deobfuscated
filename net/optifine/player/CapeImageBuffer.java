/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class CapeImageBuffer
/*    */   extends ImageBufferDownload
/*    */ {
/*    */   private AbstractClientPlayer player;
/*    */   private ResourceLocation resourceLocation;
/*    */   private boolean elytraOfCape;
/*    */   
/*    */   public CapeImageBuffer(AbstractClientPlayer player, ResourceLocation resourceLocation) {
/* 16 */     this.player = player;
/* 17 */     this.resourceLocation = resourceLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public BufferedImage parseUserSkin(BufferedImage imageRaw) {
/* 22 */     BufferedImage bufferedimage = CapeUtils.parseCape(imageRaw);
/* 23 */     this.elytraOfCape = CapeUtils.isElytraCape(imageRaw, bufferedimage);
/* 24 */     return bufferedimage;
/*    */   }
/*    */ 
/*    */   
/*    */   public void skinAvailable() {
/* 29 */     if (this.player != null) {
/*    */       
/* 31 */       this.player.setLocationOfCape(this.resourceLocation);
/* 32 */       this.player.setElytraOfCape(this.elytraOfCape);
/*    */     } 
/*    */     
/* 35 */     cleanup();
/*    */   }
/*    */ 
/*    */   
/*    */   public void cleanup() {
/* 40 */     this.player = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isElytraOfCape() {
/* 45 */     return this.elytraOfCape;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\player\CapeImageBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */