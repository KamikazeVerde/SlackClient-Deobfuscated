/*    */ package cc.slack.utils.render;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public final class Render2DUtil
/*    */   extends mc
/*    */ {
/*    */   public static boolean mouseInArea(int mouseX, int mouseY, double x, double y, double width, double height) {
/* 18 */     return (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height);
/*    */   }
/*    */   
/*    */   public static void drawImage(ResourceLocation image, float x, float y, float width, float height, Color color) {
/* 22 */     mc.getMinecraft().getTextureManager().bindTexture(image);
/* 23 */     float f = 1.0F / width;
/* 24 */     float f2 = 1.0F / height;
/* 25 */     GL11.glEnable(3042);
/* 26 */     GL11.glBlendFunc(770, 771);
/* 27 */     GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/* 28 */     Tessellator tessellator = Tessellator.getInstance();
/* 29 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 30 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 31 */     worldrenderer.pos(x, (y + height), 0.0D).tex(0.0D, (height * f2)).endVertex();
/* 32 */     worldrenderer.pos((x + width), (y + height), 0.0D).tex((width * f), (height * f2)).endVertex();
/* 33 */     worldrenderer.pos((x + width), y, 0.0D).tex((width * f), 0.0D).endVertex();
/* 34 */     worldrenderer.pos(x, y, 0.0D).tex(0.0D, 0.0D).endVertex();
/* 35 */     tessellator.draw();
/* 36 */     GL11.glDisable(3042);
/*    */   }
/*    */   
/*    */   public void drawRect(int x, int y, int width, int height, int color) {
/* 40 */     Gui.drawRect(x, y, x + width, y + height, color);
/*    */   }
/*    */   
/*    */   public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius, Color color) {
/* 44 */     GL11.glEnable(3042);
/* 45 */     GL11.glBlendFunc(770, 771);
/* 46 */     GL11.glDisable(3553);
/* 47 */     boolean hasCull = GL11.glIsEnabled(2884);
/* 48 */     GL11.glDisable(2884);
/*    */     
/* 50 */     glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
/* 51 */     GL11.glBegin(9);
/*    */     
/* 53 */     float xRadius = (float)Math.min((x1 - x) * 0.5D, radius);
/* 54 */     float yRadius = (float)Math.min((y1 - y) * 0.5D, radius);
/* 55 */     polygonCircle(x + xRadius, y + yRadius, xRadius, yRadius, 180, 270);
/* 56 */     polygonCircle(x1 - xRadius, y + yRadius, xRadius, yRadius, 90, 180);
/* 57 */     polygonCircle(x1 - xRadius, y1 - yRadius, xRadius, yRadius, 0, 90);
/* 58 */     polygonCircle(x + xRadius, y1 - yRadius, xRadius, yRadius, 270, 360);
/*    */     
/* 60 */     GL11.glEnd();
/* 61 */     GL11.glEnable(3553);
/* 62 */     GL11.glDisable(3042);
/* 63 */     setGlState(2884, hasCull);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void polygonCircle(float x, float y, float xRadius, float yRadius, int start, int end) {
/* 68 */     for (int i = end; i >= start; i -= 4) {
/* 69 */       GL11.glVertex2d(x + Math.sin(i * Math.PI / 180.0D) * xRadius, y + Math.cos(i * Math.PI / 180.0D) * yRadius);
/*    */     }
/*    */   }
/*    */   
/*    */   public static void glColor(int red, int green, int blue, int alpha) {
/* 74 */     GlStateManager.color(red / 255.0F, green / 255.0F, blue / 255.0F, alpha / 255.0F);
/*    */   }
/*    */   
/*    */   public static void setGlState(int cap, boolean state) {
/* 78 */     if (state) {
/* 79 */       GL11.glEnable(cap);
/*    */     } else {
/* 81 */       GL11.glDisable(cap);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\render\Render2DUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */