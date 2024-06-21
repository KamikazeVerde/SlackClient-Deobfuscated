/*     */ package cc.slack.utils.render;
/*     */ 
/*     */ import cc.slack.utils.client.mc;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public final class Render3DUtil
/*     */   extends mc
/*     */ {
/*  17 */   private static final Map<String, Map<Integer, Boolean>> glCapMap = new HashMap<>();
/*     */   
/*     */   public static void drawAABB(AxisAlignedBB boundingBox) {
/*  20 */     GL11.glBlendFunc(770, 771);
/*  21 */     enableGlCap(3042);
/*  22 */     disableGlCap(new int[] { 3553, 2929 });
/*  23 */     GL11.glDepthMask(false);
/*  24 */     GL11.glLineWidth(1.5F);
/*  25 */     enableGlCap(2848);
/*  26 */     drawSelectionBoundingBox(boundingBox);
/*  27 */     GlStateManager.resetColor();
/*  28 */     GL11.glDepthMask(true);
/*  29 */     resetCaps();
/*     */   }
/*     */   
/*     */   public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
/*  33 */     Tessellator tessellator = Tessellator.getInstance();
/*  34 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/*  36 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION_TEX);
/*     */ 
/*     */     
/*  39 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
/*  40 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
/*  41 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
/*  42 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
/*  43 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
/*     */ 
/*     */     
/*  46 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
/*  47 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/*  48 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/*  49 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
/*  50 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
/*     */ 
/*     */     
/*  53 */     worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/*  54 */     worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
/*     */     
/*  56 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
/*  57 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
/*     */     
/*  59 */     worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
/*  60 */     worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
/*     */     
/*  62 */     tessellator.draw();
/*     */   }
/*     */   
/*     */   public static void enableGlCap(int cap, String scale) {
/*  66 */     setGlCap(cap, true, scale);
/*     */   }
/*     */   
/*     */   public static void enableGlCap(int cap) {
/*  70 */     enableGlCap(cap, "COMMON");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void disableGlCap(int... caps) {
/*  75 */     for (int cap : caps) {
/*  76 */       setGlCap(cap, false, "COMMON");
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setGlCap(int cap, boolean state, String scale) {
/*  81 */     if (!glCapMap.containsKey(scale)) {
/*  82 */       glCapMap.put(scale, new HashMap<>());
/*     */     }
/*  84 */     ((Map<Integer, Boolean>)glCapMap.get(scale)).put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
/*  85 */     setGlState(cap, state);
/*     */   }
/*     */   
/*     */   public static void setGlState(int cap, boolean state) {
/*  89 */     if (state) {
/*  90 */       GL11.glEnable(cap);
/*     */     } else {
/*  92 */       GL11.glDisable(cap);
/*     */     } 
/*     */   }
/*     */   public static void glColor(int red, int green, int blue, int alpha) {
/*  96 */     GlStateManager.color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/*     */   }
/*     */   
/*     */   public static void resetCaps(String scale) {
/* 100 */     if (!glCapMap.containsKey(scale)) {
/*     */       return;
/*     */     }
/* 103 */     Map<Integer, Boolean> map = glCapMap.get(scale);
/* 104 */     map.forEach(Render3DUtil::setGlState);
/* 105 */     map.clear();
/*     */   }
/*     */   
/*     */   public static void resetCaps() {
/* 109 */     resetCaps("COMMON");
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\render\Render3DUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */