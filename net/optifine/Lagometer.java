/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiIngame;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.profiler.Profiler;
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Lagometer
/*     */ {
/*     */   private static Minecraft mc;
/*     */   private static GameSettings gameSettings;
/*     */   private static Profiler profiler;
/*     */   public static boolean active = false;
/*  21 */   public static TimerNano timerTick = new TimerNano();
/*  22 */   public static TimerNano timerScheduledExecutables = new TimerNano();
/*  23 */   public static TimerNano timerChunkUpload = new TimerNano();
/*  24 */   public static TimerNano timerChunkUpdate = new TimerNano();
/*  25 */   public static TimerNano timerVisibility = new TimerNano();
/*  26 */   public static TimerNano timerTerrain = new TimerNano();
/*  27 */   public static TimerNano timerServer = new TimerNano();
/*  28 */   private static long[] timesFrame = new long[512];
/*  29 */   private static long[] timesTick = new long[512];
/*  30 */   private static long[] timesScheduledExecutables = new long[512];
/*  31 */   private static long[] timesChunkUpload = new long[512];
/*  32 */   private static long[] timesChunkUpdate = new long[512];
/*  33 */   private static long[] timesVisibility = new long[512];
/*  34 */   private static long[] timesTerrain = new long[512];
/*  35 */   private static long[] timesServer = new long[512];
/*  36 */   private static boolean[] gcs = new boolean[512];
/*  37 */   private static int numRecordedFrameTimes = 0;
/*  38 */   private static long prevFrameTimeNano = -1L;
/*  39 */   private static long renderTimeNano = 0L;
/*  40 */   private static long memTimeStartMs = System.currentTimeMillis();
/*  41 */   private static long memStart = getMemoryUsed();
/*  42 */   private static long memTimeLast = memTimeStartMs;
/*  43 */   private static long memLast = memStart;
/*  44 */   private static long memTimeDiffMs = 1L;
/*  45 */   private static long memDiff = 0L;
/*  46 */   private static int memMbSec = 0;
/*     */ 
/*     */   
/*     */   public static boolean updateMemoryAllocation() {
/*  50 */     long i = System.currentTimeMillis();
/*  51 */     long j = getMemoryUsed();
/*  52 */     boolean flag = false;
/*     */     
/*  54 */     if (j < memLast) {
/*     */       
/*  56 */       double d0 = memDiff / 1000000.0D;
/*  57 */       double d1 = memTimeDiffMs / 1000.0D;
/*  58 */       int k = (int)(d0 / d1);
/*     */       
/*  60 */       if (k > 0)
/*     */       {
/*  62 */         memMbSec = k;
/*     */       }
/*     */       
/*  65 */       memTimeStartMs = i;
/*  66 */       memStart = j;
/*  67 */       memTimeDiffMs = 0L;
/*  68 */       memDiff = 0L;
/*  69 */       flag = true;
/*     */     }
/*     */     else {
/*     */       
/*  73 */       memTimeDiffMs = i - memTimeStartMs;
/*  74 */       memDiff = j - memStart;
/*     */     } 
/*     */     
/*  77 */     memTimeLast = i;
/*  78 */     memLast = j;
/*  79 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long getMemoryUsed() {
/*  84 */     Runtime runtime = Runtime.getRuntime();
/*  85 */     return runtime.totalMemory() - runtime.freeMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateLagometer() {
/*  90 */     if (mc == null) {
/*     */       
/*  92 */       mc = Minecraft.getMinecraft();
/*  93 */       gameSettings = mc.gameSettings;
/*  94 */       profiler = mc.mcProfiler;
/*     */     } 
/*     */     
/*  97 */     if (gameSettings.showDebugInfo && (gameSettings.ofLagometer || gameSettings.field_181657_aC)) {
/*     */       
/*  99 */       active = true;
/* 100 */       long timeNowNano = System.nanoTime();
/*     */       
/* 102 */       if (prevFrameTimeNano == -1L)
/*     */       {
/* 104 */         prevFrameTimeNano = timeNowNano;
/*     */       }
/*     */       else
/*     */       {
/* 108 */         int j = numRecordedFrameTimes & timesFrame.length - 1;
/* 109 */         numRecordedFrameTimes++;
/* 110 */         boolean flag = updateMemoryAllocation();
/* 111 */         timesFrame[j] = timeNowNano - prevFrameTimeNano - renderTimeNano;
/* 112 */         timesTick[j] = timerTick.timeNano;
/* 113 */         timesScheduledExecutables[j] = timerScheduledExecutables.timeNano;
/* 114 */         timesChunkUpload[j] = timerChunkUpload.timeNano;
/* 115 */         timesChunkUpdate[j] = timerChunkUpdate.timeNano;
/* 116 */         timesVisibility[j] = timerVisibility.timeNano;
/* 117 */         timesTerrain[j] = timerTerrain.timeNano;
/* 118 */         timesServer[j] = timerServer.timeNano;
/* 119 */         gcs[j] = flag;
/* 120 */         timerTick.reset();
/* 121 */         timerScheduledExecutables.reset();
/* 122 */         timerVisibility.reset();
/* 123 */         timerChunkUpdate.reset();
/* 124 */         timerChunkUpload.reset();
/* 125 */         timerTerrain.reset();
/* 126 */         timerServer.reset();
/* 127 */         prevFrameTimeNano = System.nanoTime();
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 132 */       active = false;
/* 133 */       prevFrameTimeNano = -1L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void showLagometer(ScaledResolution scaledResolution) {
/* 139 */     if (gameSettings != null)
/*     */     {
/* 141 */       if (gameSettings.ofLagometer || gameSettings.field_181657_aC) {
/*     */         
/* 143 */         long i = System.nanoTime();
/* 144 */         GlStateManager.clear(256);
/* 145 */         GlStateManager.matrixMode(5889);
/* 146 */         GlStateManager.pushMatrix();
/* 147 */         GlStateManager.enableColorMaterial();
/* 148 */         GlStateManager.loadIdentity();
/* 149 */         GlStateManager.ortho(0.0D, mc.displayWidth, mc.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 150 */         GlStateManager.matrixMode(5888);
/* 151 */         GlStateManager.pushMatrix();
/* 152 */         GlStateManager.loadIdentity();
/* 153 */         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 154 */         GL11.glLineWidth(1.0F);
/* 155 */         GlStateManager.disableTexture2D();
/* 156 */         Tessellator tessellator = Tessellator.getInstance();
/* 157 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 158 */         worldrenderer.begin(1, DefaultVertexFormats.field_181706_f);
/*     */         
/* 160 */         for (int j = 0; j < timesFrame.length; j++) {
/*     */           
/* 162 */           int k = (j - numRecordedFrameTimes & timesFrame.length - 1) * 100 / timesFrame.length;
/* 163 */           k += 155;
/* 164 */           float f = mc.displayHeight;
/* 165 */           long l = 0L;
/*     */           
/* 167 */           if (gcs[j]) {
/*     */             
/* 169 */             renderTime(j, timesFrame[j], k, k / 2, 0, f, worldrenderer);
/*     */           }
/*     */           else {
/*     */             
/* 173 */             renderTime(j, timesFrame[j], k, k, k, f, worldrenderer);
/* 174 */             f -= (float)renderTime(j, timesServer[j], k / 2, k / 2, k / 2, f, worldrenderer);
/* 175 */             f -= (float)renderTime(j, timesTerrain[j], 0, k, 0, f, worldrenderer);
/* 176 */             f -= (float)renderTime(j, timesVisibility[j], k, k, 0, f, worldrenderer);
/* 177 */             f -= (float)renderTime(j, timesChunkUpdate[j], k, 0, 0, f, worldrenderer);
/* 178 */             f -= (float)renderTime(j, timesChunkUpload[j], k, 0, k, f, worldrenderer);
/* 179 */             f -= (float)renderTime(j, timesScheduledExecutables[j], 0, 0, k, f, worldrenderer);
/* 180 */             float f2 = f - (float)renderTime(j, timesTick[j], 0, k, k, f, worldrenderer);
/*     */           } 
/*     */         } 
/*     */         
/* 184 */         renderTimeDivider(0, timesFrame.length, 33333333L, 196, 196, 196, mc.displayHeight, worldrenderer);
/* 185 */         renderTimeDivider(0, timesFrame.length, 16666666L, 196, 196, 196, mc.displayHeight, worldrenderer);
/* 186 */         tessellator.draw();
/* 187 */         GlStateManager.enableTexture2D();
/* 188 */         int j2 = mc.displayHeight - 80;
/* 189 */         int k2 = mc.displayHeight - 160;
/* 190 */         mc.MCfontRenderer.drawString("30", 2, k2 + 1, -8947849);
/* 191 */         mc.MCfontRenderer.drawString("30", 1, k2, -3881788);
/* 192 */         mc.MCfontRenderer.drawString("60", 2, j2 + 1, -8947849);
/* 193 */         mc.MCfontRenderer.drawString("60", 1, j2, -3881788);
/* 194 */         GlStateManager.matrixMode(5889);
/* 195 */         GlStateManager.popMatrix();
/* 196 */         GlStateManager.matrixMode(5888);
/* 197 */         GlStateManager.popMatrix();
/* 198 */         GlStateManager.enableTexture2D();
/* 199 */         float f1 = 1.0F - (float)((System.currentTimeMillis() - memTimeStartMs) / 1000.0D);
/* 200 */         f1 = Config.limit(f1, 0.0F, 1.0F);
/* 201 */         int l2 = (int)(170.0F + f1 * 85.0F);
/* 202 */         int i1 = (int)(100.0F + f1 * 55.0F);
/* 203 */         int j1 = (int)(10.0F + f1 * 10.0F);
/* 204 */         int k1 = l2 << 16 | i1 << 8 | j1;
/* 205 */         int l1 = 512 / scaledResolution.getScaleFactor() + 2;
/* 206 */         int i2 = mc.displayHeight / scaledResolution.getScaleFactor() - 8;
/* 207 */         GuiIngame guiingame = mc.ingameGUI;
/* 208 */         GuiIngame.drawRect(l1 - 1, i2 - 1, l1 + 50, i2 + 10, -1605349296);
/* 209 */         mc.MCfontRenderer.drawString(" " + memMbSec + " MB/s", l1, i2, k1);
/* 210 */         renderTimeNano = System.nanoTime() - i;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static long renderTime(int frameNum, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator) {
/* 217 */     long i = time / 200000L;
/*     */     
/* 219 */     if (i < 3L)
/*     */     {
/* 221 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/* 225 */     tessellator.pos((frameNum + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).func_181669_b(r, g, b, 255).endVertex();
/* 226 */     tessellator.pos((frameNum + 0.5F), (baseHeight + 0.5F), 0.0D).func_181669_b(r, g, b, 255).endVertex();
/* 227 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static long renderTimeDivider(int frameStart, int frameEnd, long time, int r, int g, int b, float baseHeight, WorldRenderer tessellator) {
/* 233 */     long i = time / 200000L;
/*     */     
/* 235 */     if (i < 3L)
/*     */     {
/* 237 */       return 0L;
/*     */     }
/*     */ 
/*     */     
/* 241 */     tessellator.pos((frameStart + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).func_181669_b(r, g, b, 255).endVertex();
/* 242 */     tessellator.pos((frameEnd + 0.5F), (baseHeight - (float)i + 0.5F), 0.0D).func_181669_b(r, g, b, 255).endVertex();
/* 243 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 249 */     return active;
/*     */   }
/*     */   
/*     */   public static class TimerNano
/*     */   {
/* 254 */     public long timeStartNano = 0L;
/* 255 */     public long timeNano = 0L;
/*     */ 
/*     */     
/*     */     public void start() {
/* 259 */       if (Lagometer.active)
/*     */       {
/* 261 */         if (this.timeStartNano == 0L)
/*     */         {
/* 263 */           this.timeStartNano = System.nanoTime();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void end() {
/* 270 */       if (Lagometer.active)
/*     */       {
/* 272 */         if (this.timeStartNano != 0L) {
/*     */           
/* 274 */           this.timeNano += System.nanoTime() - this.timeStartNano;
/* 275 */           this.timeStartNano = 0L;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     private void reset() {
/* 282 */       this.timeNano = 0L;
/* 283 */       this.timeStartNano = 0L;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\Lagometer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */