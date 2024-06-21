/*     */ package net.minecraft.client;
/*     */ 
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MinecraftError;
/*     */ import net.optifine.CustomLoadingScreen;
/*     */ import net.optifine.CustomLoadingScreens;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class LoadingScreenRenderer
/*     */   implements IProgressUpdate {
/*  19 */   private String message = "";
/*     */ 
/*     */ 
/*     */   
/*     */   private Minecraft mc;
/*     */ 
/*     */ 
/*     */   
/*  27 */   private String currentlyDisplayedText = "";
/*     */ 
/*     */   
/*  30 */   private long systemTime = Minecraft.getSystemTime();
/*     */   
/*     */   private boolean field_73724_e;
/*     */   private ScaledResolution scaledResolution;
/*     */   private Framebuffer framebuffer;
/*     */   
/*     */   public LoadingScreenRenderer(Minecraft mcIn) {
/*  37 */     this.mc = mcIn;
/*  38 */     this.scaledResolution = new ScaledResolution(mcIn);
/*  39 */     this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
/*  40 */     this.framebuffer.setFramebufferFilter(9728);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetProgressAndMessage(String message) {
/*  49 */     this.field_73724_e = false;
/*  50 */     displayString(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displaySavingString(String message) {
/*  58 */     this.field_73724_e = true;
/*  59 */     displayString(message);
/*     */   }
/*     */ 
/*     */   
/*     */   private void displayString(String message) {
/*  64 */     this.currentlyDisplayedText = message;
/*     */     
/*  66 */     if (!this.mc.running) {
/*     */       
/*  68 */       if (!this.field_73724_e)
/*     */       {
/*  70 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  75 */       GlStateManager.clear(256);
/*  76 */       GlStateManager.matrixMode(5889);
/*  77 */       GlStateManager.loadIdentity();
/*     */       
/*  79 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/*  81 */         int i = this.scaledResolution.getScaleFactor();
/*  82 */         GlStateManager.ortho(0.0D, (this.scaledResolution.getScaledWidth() * i), (this.scaledResolution.getScaledHeight() * i), 0.0D, 100.0D, 300.0D);
/*     */       }
/*     */       else {
/*     */         
/*  86 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  87 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/*     */       } 
/*     */       
/*  90 */       GlStateManager.matrixMode(5888);
/*  91 */       GlStateManager.loadIdentity();
/*  92 */       GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayLoadingString(String message) {
/* 101 */     if (!this.mc.running) {
/*     */       
/* 103 */       if (!this.field_73724_e)
/*     */       {
/* 105 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 110 */       this.systemTime = 0L;
/* 111 */       this.message = message;
/* 112 */       setLoadingProgress(-1);
/* 113 */       this.systemTime = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadingProgress(int progress) {
/* 122 */     if (!this.mc.running) {
/*     */       
/* 124 */       if (!this.field_73724_e)
/*     */       {
/* 126 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 131 */       long i = Minecraft.getSystemTime();
/*     */       
/* 133 */       if (i - this.systemTime >= 100L) {
/*     */         
/* 135 */         this.systemTime = i;
/* 136 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 137 */         int j = scaledresolution.getScaleFactor();
/* 138 */         int k = scaledresolution.getScaledWidth();
/* 139 */         int l = scaledresolution.getScaledHeight();
/*     */         
/* 141 */         if (OpenGlHelper.isFramebufferEnabled()) {
/*     */           
/* 143 */           this.framebuffer.framebufferClear();
/*     */         }
/*     */         else {
/*     */           
/* 147 */           GlStateManager.clear(256);
/*     */         } 
/*     */         
/* 150 */         this.framebuffer.bindFramebuffer(false);
/* 151 */         GlStateManager.matrixMode(5889);
/* 152 */         GlStateManager.loadIdentity();
/* 153 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/* 154 */         GlStateManager.matrixMode(5888);
/* 155 */         GlStateManager.loadIdentity();
/* 156 */         GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */         
/* 158 */         if (!OpenGlHelper.isFramebufferEnabled())
/*     */         {
/* 160 */           GlStateManager.clear(16640);
/*     */         }
/*     */         
/* 163 */         boolean flag = true;
/*     */         
/* 165 */         if (Reflector.FMLClientHandler_handleLoadingScreen.exists()) {
/*     */           
/* 167 */           Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*     */           
/* 169 */           if (object != null)
/*     */           {
/* 171 */             flag = !Reflector.callBoolean(object, Reflector.FMLClientHandler_handleLoadingScreen, new Object[] { scaledresolution });
/*     */           }
/*     */         } 
/*     */         
/* 175 */         if (flag) {
/*     */           
/* 177 */           Tessellator tessellator = Tessellator.getInstance();
/* 178 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 179 */           CustomLoadingScreen customloadingscreen = CustomLoadingScreens.getCustomLoadingScreen();
/*     */           
/* 181 */           if (customloadingscreen != null) {
/*     */             
/* 183 */             customloadingscreen.drawBackground(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*     */           }
/*     */           else {
/*     */             
/* 187 */             this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 188 */             float f = 32.0F;
/* 189 */             worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 190 */             worldrenderer.pos(0.0D, l, 0.0D).tex(0.0D, (l / f)).func_181669_b(64, 64, 64, 255).endVertex();
/* 191 */             worldrenderer.pos(k, l, 0.0D).tex((k / f), (l / f)).func_181669_b(64, 64, 64, 255).endVertex();
/* 192 */             worldrenderer.pos(k, 0.0D, 0.0D).tex((k / f), 0.0D).func_181669_b(64, 64, 64, 255).endVertex();
/* 193 */             worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).func_181669_b(64, 64, 64, 255).endVertex();
/* 194 */             tessellator.draw();
/*     */           } 
/*     */           
/* 197 */           if (progress >= 0) {
/*     */             
/* 199 */             int l1 = 100;
/* 200 */             int i1 = 2;
/* 201 */             int j1 = k / 2 - l1 / 2;
/* 202 */             int k1 = l / 2 + 16;
/* 203 */             GlStateManager.disableTexture2D();
/* 204 */             worldrenderer.begin(7, DefaultVertexFormats.field_181706_f);
/* 205 */             worldrenderer.pos(j1, k1, 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 206 */             worldrenderer.pos(j1, (k1 + i1), 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 207 */             worldrenderer.pos((j1 + l1), (k1 + i1), 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 208 */             worldrenderer.pos((j1 + l1), k1, 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 209 */             worldrenderer.pos(j1, k1, 0.0D).func_181669_b(128, 255, 128, 255).endVertex();
/* 210 */             worldrenderer.pos(j1, (k1 + i1), 0.0D).func_181669_b(128, 255, 128, 255).endVertex();
/* 211 */             worldrenderer.pos((j1 + progress), (k1 + i1), 0.0D).func_181669_b(128, 255, 128, 255).endVertex();
/* 212 */             worldrenderer.pos((j1 + progress), k1, 0.0D).func_181669_b(128, 255, 128, 255).endVertex();
/* 213 */             tessellator.draw();
/* 214 */             GlStateManager.enableTexture2D();
/*     */           } 
/*     */           
/* 217 */           GlStateManager.enableBlend();
/* 218 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 219 */           this.mc.MCfontRenderer.drawStringWithShadow(this.currentlyDisplayedText, ((k - this.mc.MCfontRenderer.getStringWidth(this.currentlyDisplayedText)) / 2), (l / 2 - 4 - 16), 16777215);
/* 220 */           this.mc.MCfontRenderer.drawStringWithShadow(this.message, ((k - this.mc.MCfontRenderer.getStringWidth(this.message)) / 2), (l / 2 - 4 + 8), 16777215);
/*     */         } 
/*     */         
/* 223 */         this.framebuffer.unbindFramebuffer();
/*     */         
/* 225 */         if (OpenGlHelper.isFramebufferEnabled())
/*     */         {
/* 227 */           this.framebuffer.framebufferRender(k * j, l * j);
/*     */         }
/*     */         
/* 230 */         this.mc.updateDisplay();
/*     */ 
/*     */         
/*     */         try {
/* 234 */           Thread.yield();
/*     */         }
/* 236 */         catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDoneWorking() {}
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\LoadingScreenRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */