/*    */ package net.optifine.player;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.IImageBuffer;
/*    */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*    */ import net.minecraft.client.renderer.texture.ITextureObject;
/*    */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class CapeUtils
/*    */ {
/* 19 */   private static final Pattern PATTERN_USERNAME = Pattern.compile("[a-zA-Z0-9_]+");
/*    */ 
/*    */   
/*    */   public static void downloadCape(AbstractClientPlayer player) {
/* 23 */     String s = player.getNameClear();
/*    */     
/* 25 */     if (s != null && !s.isEmpty() && !s.contains("\000") && PATTERN_USERNAME.matcher(s).matches()) {
/*    */       
/* 27 */       String s1 = "http://s.optifine.net/capes/" + s + ".png";
/* 28 */       ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
/* 29 */       TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 30 */       ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*    */       
/* 32 */       if (itextureobject != null && itextureobject instanceof ThreadDownloadImageData) {
/*    */         
/* 34 */         ThreadDownloadImageData threaddownloadimagedata = (ThreadDownloadImageData)itextureobject;
/*    */         
/* 36 */         if (threaddownloadimagedata.imageFound != null) {
/*    */           
/* 38 */           if (threaddownloadimagedata.imageFound.booleanValue()) {
/*    */             
/* 40 */             player.setLocationOfCape(resourcelocation);
/*    */             
/* 42 */             if (threaddownloadimagedata.getImageBuffer() instanceof CapeImageBuffer) {
/*    */               
/* 44 */               CapeImageBuffer capeimagebuffer1 = (CapeImageBuffer)threaddownloadimagedata.getImageBuffer();
/* 45 */               player.setElytraOfCape(capeimagebuffer1.isElytraOfCape());
/*    */             } 
/*    */           } 
/*    */           
/*    */           return;
/*    */         } 
/*    */       } 
/*    */       
/* 53 */       CapeImageBuffer capeimagebuffer = new CapeImageBuffer(player, resourcelocation);
/* 54 */       ThreadDownloadImageData threaddownloadimagedata1 = new ThreadDownloadImageData(null, s1, null, (IImageBuffer)capeimagebuffer);
/* 55 */       threaddownloadimagedata1.pipeline = true;
/* 56 */       texturemanager.loadTexture(resourcelocation, (ITextureObject)threaddownloadimagedata1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public static BufferedImage parseCape(BufferedImage img) {
/* 62 */     int i = 64;
/* 63 */     int j = 32;
/* 64 */     int k = img.getWidth();
/*    */     
/* 66 */     for (int l = img.getHeight(); i < k || j < l; j *= 2)
/*    */     {
/* 68 */       i *= 2;
/*    */     }
/*    */     
/* 71 */     BufferedImage bufferedimage = new BufferedImage(i, j, 2);
/* 72 */     Graphics graphics = bufferedimage.getGraphics();
/* 73 */     graphics.drawImage(img, 0, 0, null);
/* 74 */     graphics.dispose();
/* 75 */     return bufferedimage;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isElytraCape(BufferedImage imageRaw, BufferedImage imageFixed) {
/* 80 */     return (imageRaw.getWidth() > imageFixed.getHeight());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void reloadCape(AbstractClientPlayer player) {
/* 85 */     String s = player.getNameClear();
/* 86 */     ResourceLocation resourcelocation = new ResourceLocation("capeof/" + s);
/* 87 */     TextureManager texturemanager = Config.getTextureManager();
/* 88 */     ITextureObject itextureobject = texturemanager.getTexture(resourcelocation);
/*    */     
/* 90 */     if (itextureobject instanceof SimpleTexture) {
/*    */       
/* 92 */       SimpleTexture simpletexture = (SimpleTexture)itextureobject;
/* 93 */       simpletexture.deleteGlTexture();
/* 94 */       texturemanager.deleteTexture(resourcelocation);
/*    */     } 
/*    */     
/* 97 */     player.setLocationOfCape(null);
/* 98 */     player.setElytraOfCape(false);
/* 99 */     downloadCape(player);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\player\CapeUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */