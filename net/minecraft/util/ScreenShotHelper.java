/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.nio.IntBuffer;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.src.Config;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class ScreenShotHelper
/*     */ {
/*  26 */   private static final Logger logger = LogManager.getLogger();
/*  27 */   private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static IntBuffer pixelBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int[] pixelValues;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IChatComponent saveScreenshot(File gameDirectory, int width, int height, Framebuffer buffer) {
/*  43 */     return saveScreenshot(gameDirectory, null, width, height, buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IChatComponent saveScreenshot(File gameDirectory, String screenshotName, int width, int height, Framebuffer buffer) {
/*     */     try {
/*  54 */       File file1 = new File(gameDirectory, "screenshots");
/*  55 */       file1.mkdir();
/*  56 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  57 */       int i = (Config.getGameSettings()).guiScale;
/*  58 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/*  59 */       int j = scaledresolution.getScaleFactor();
/*  60 */       int k = Config.getScreenshotSize();
/*  61 */       boolean flag = (OpenGlHelper.isFramebufferEnabled() && k > 1);
/*     */       
/*  63 */       if (flag) {
/*     */         
/*  65 */         (Config.getGameSettings()).guiScale = j * k;
/*  66 */         resize(width * k, height * k);
/*  67 */         GlStateManager.pushMatrix();
/*  68 */         GlStateManager.clear(16640);
/*  69 */         minecraft.getFramebuffer().bindFramebuffer(true);
/*  70 */         minecraft.entityRenderer.func_181560_a(Config.renderPartialTicks, System.nanoTime());
/*     */       } 
/*     */       
/*  73 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/*  75 */         width = buffer.framebufferTextureWidth;
/*  76 */         height = buffer.framebufferTextureHeight;
/*     */       } 
/*     */       
/*  79 */       int l = width * height;
/*     */       
/*  81 */       if (pixelBuffer == null || pixelBuffer.capacity() < l) {
/*     */         
/*  83 */         pixelBuffer = BufferUtils.createIntBuffer(l);
/*  84 */         pixelValues = new int[l];
/*     */       } 
/*     */       
/*  87 */       GL11.glPixelStorei(3333, 1);
/*  88 */       GL11.glPixelStorei(3317, 1);
/*  89 */       pixelBuffer.clear();
/*     */       
/*  91 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/*  93 */         GlStateManager.bindTexture(buffer.framebufferTexture);
/*  94 */         GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
/*     */       }
/*     */       else {
/*     */         
/*  98 */         GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
/*     */       } 
/*     */       
/* 101 */       pixelBuffer.get(pixelValues);
/* 102 */       TextureUtil.processPixelValues(pixelValues, width, height);
/* 103 */       BufferedImage bufferedimage = null;
/*     */       
/* 105 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/* 107 */         bufferedimage = new BufferedImage(buffer.framebufferWidth, buffer.framebufferHeight, 1);
/* 108 */         int i1 = buffer.framebufferTextureHeight - buffer.framebufferHeight;
/*     */         
/* 110 */         for (int j1 = i1; j1 < buffer.framebufferTextureHeight; j1++)
/*     */         {
/* 112 */           for (int k1 = 0; k1 < buffer.framebufferWidth; k1++)
/*     */           {
/* 114 */             bufferedimage.setRGB(k1, j1 - i1, pixelValues[j1 * buffer.framebufferTextureWidth + k1]);
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 120 */         bufferedimage = new BufferedImage(width, height, 1);
/* 121 */         bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
/*     */       } 
/*     */       
/* 124 */       if (flag) {
/*     */         
/* 126 */         minecraft.getFramebuffer().unbindFramebuffer();
/* 127 */         GlStateManager.popMatrix();
/* 128 */         (Config.getGameSettings()).guiScale = i;
/* 129 */         resize(width, height);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 134 */       if (screenshotName == null) {
/*     */         
/* 136 */         file2 = getTimestampedPNGFileForDirectory(file1);
/*     */       }
/*     */       else {
/*     */         
/* 140 */         file2 = new File(file1, screenshotName);
/*     */       } 
/*     */       
/* 143 */       File file2 = file2.getCanonicalFile();
/* 144 */       ImageIO.write(bufferedimage, "png", file2);
/* 145 */       IChatComponent ichatcomponent = new ChatComponentText(file2.getName());
/* 146 */       ichatcomponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file2.getAbsolutePath()));
/* 147 */       ichatcomponent.getChatStyle().setUnderlined(Boolean.TRUE);
/* 148 */       return new ChatComponentTranslation("screenshot.success", new Object[] { ichatcomponent });
/*     */     }
/* 150 */     catch (Exception exception) {
/*     */       
/* 152 */       logger.warn("Couldn't save screenshot", exception);
/* 153 */       return new ChatComponentTranslation("screenshot.failure", new Object[] { exception.getMessage() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File getTimestampedPNGFileForDirectory(File gameDirectory) {
/* 165 */     String s = dateFormat.format(new Date());
/* 166 */     int i = 1;
/*     */ 
/*     */     
/*     */     while (true) {
/* 170 */       File file1 = new File(gameDirectory, s + ((i == 1) ? "" : ("_" + i)) + ".png");
/*     */       
/* 172 */       if (!file1.exists())
/*     */       {
/* 174 */         return file1;
/*     */       }
/*     */       
/* 177 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void resize(int p_resize_0_, int p_resize_1_) {
/* 183 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 184 */     minecraft.displayWidth = Math.max(1, p_resize_0_);
/* 185 */     minecraft.displayHeight = Math.max(1, p_resize_1_);
/*     */     
/* 187 */     if (minecraft.currentScreen != null) {
/*     */       
/* 189 */       ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 190 */       minecraft.currentScreen.onResize(minecraft, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*     */     } 
/*     */     
/* 193 */     updateFramebufferSize();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void updateFramebufferSize() {
/* 198 */     Minecraft minecraft = Minecraft.getMinecraft();
/* 199 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*     */     
/* 201 */     if (minecraft.entityRenderer != null)
/*     */     {
/* 203 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\ScreenShotHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */