/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class CustomLoadingScreen
/*     */ {
/*     */   private ResourceLocation locationTexture;
/*  14 */   private int scaleMode = 0;
/*  15 */   private int scale = 2;
/*     */   
/*     */   private boolean center;
/*     */   private static final int SCALE_DEFAULT = 2;
/*     */   private static final int SCALE_MODE_FIXED = 0;
/*     */   private static final int SCALE_MODE_FULL = 1;
/*     */   private static final int SCALE_MODE_STRETCH = 2;
/*     */   
/*     */   public CustomLoadingScreen(ResourceLocation locationTexture, int scaleMode, int scale, boolean center) {
/*  24 */     this.locationTexture = locationTexture;
/*  25 */     this.scaleMode = scaleMode;
/*  26 */     this.scale = scale;
/*  27 */     this.center = center;
/*     */   }
/*     */ 
/*     */   
/*     */   public static CustomLoadingScreen parseScreen(String path, int dimId, Properties props) {
/*  32 */     ResourceLocation resourcelocation = new ResourceLocation(path);
/*  33 */     int i = parseScaleMode(getProperty("scaleMode", dimId, props));
/*  34 */     int j = (i == 0) ? 2 : 1;
/*  35 */     int k = parseScale(getProperty("scale", dimId, props), j);
/*  36 */     boolean flag = Config.parseBoolean(getProperty("center", dimId, props), false);
/*  37 */     CustomLoadingScreen customloadingscreen = new CustomLoadingScreen(resourcelocation, i, k, flag);
/*  38 */     return customloadingscreen;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getProperty(String key, int dim, Properties props) {
/*  43 */     if (props == null)
/*     */     {
/*  45 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  49 */     String s = props.getProperty("dim" + dim + "." + key);
/*     */     
/*  51 */     if (s != null)
/*     */     {
/*  53 */       return s;
/*     */     }
/*     */ 
/*     */     
/*  57 */     s = props.getProperty(key);
/*  58 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseScaleMode(String str) {
/*  65 */     if (str == null)
/*     */     {
/*  67 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  71 */     str = str.toLowerCase().trim();
/*     */     
/*  73 */     if (str.equals("fixed"))
/*     */     {
/*  75 */       return 0;
/*     */     }
/*  77 */     if (str.equals("full"))
/*     */     {
/*  79 */       return 1;
/*     */     }
/*  81 */     if (str.equals("stretch"))
/*     */     {
/*  83 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*  87 */     CustomLoadingScreens.warn("Invalid scale mode: " + str);
/*  88 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int parseScale(String str, int def) {
/*  95 */     if (str == null)
/*     */     {
/*  97 */       return def;
/*     */     }
/*     */ 
/*     */     
/* 101 */     str = str.trim();
/* 102 */     int i = Config.parseInt(str, -1);
/*     */     
/* 104 */     if (i < 1) {
/*     */       
/* 106 */       CustomLoadingScreens.warn("Invalid scale: " + str);
/* 107 */       return def;
/*     */     } 
/*     */ 
/*     */     
/* 111 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int width, int height) {
/* 118 */     GlStateManager.disableLighting();
/* 119 */     GlStateManager.disableFog();
/* 120 */     Tessellator tessellator = Tessellator.getInstance();
/* 121 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 122 */     Config.getTextureManager().bindTexture(this.locationTexture);
/* 123 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 124 */     double d0 = (16 * this.scale);
/* 125 */     double d1 = width / d0;
/* 126 */     double d2 = height / d0;
/* 127 */     double d3 = 0.0D;
/* 128 */     double d4 = 0.0D;
/*     */     
/* 130 */     if (this.center) {
/*     */       
/* 132 */       d3 = (d0 - width) / d0 * 2.0D;
/* 133 */       d4 = (d0 - height) / d0 * 2.0D;
/*     */     } 
/*     */     
/* 136 */     switch (this.scaleMode) {
/*     */       
/*     */       case 1:
/* 139 */         d0 = Math.max(width, height);
/* 140 */         d1 = (this.scale * width) / d0;
/* 141 */         d2 = (this.scale * height) / d0;
/*     */         
/* 143 */         if (this.center) {
/*     */           
/* 145 */           d3 = this.scale * (d0 - width) / d0 * 2.0D;
/* 146 */           d4 = this.scale * (d0 - height) / d0 * 2.0D;
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 152 */         d1 = this.scale;
/* 153 */         d2 = this.scale;
/* 154 */         d3 = 0.0D;
/* 155 */         d4 = 0.0D;
/*     */         break;
/*     */     } 
/* 158 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 159 */     worldrenderer.pos(0.0D, height, 0.0D).tex(d3, d4 + d2).func_181669_b(255, 255, 255, 255).endVertex();
/* 160 */     worldrenderer.pos(width, height, 0.0D).tex(d3 + d1, d4 + d2).func_181669_b(255, 255, 255, 255).endVertex();
/* 161 */     worldrenderer.pos(width, 0.0D, 0.0D).tex(d3 + d1, d4).func_181669_b(255, 255, 255, 255).endVertex();
/* 162 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(d3, d4).func_181669_b(255, 255, 255, 255).endVertex();
/* 163 */     tessellator.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\CustomLoadingScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */