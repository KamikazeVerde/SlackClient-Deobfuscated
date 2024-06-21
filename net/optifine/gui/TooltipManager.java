/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class TooltipManager
/*    */ {
/*    */   private GuiScreen guiScreen;
/*    */   private TooltipProvider tooltipProvider;
/* 16 */   private int lastMouseX = 0;
/* 17 */   private int lastMouseY = 0;
/* 18 */   private long mouseStillTime = 0L;
/*    */ 
/*    */   
/*    */   public TooltipManager(GuiScreen guiScreen, TooltipProvider tooltipProvider) {
/* 22 */     this.guiScreen = guiScreen;
/* 23 */     this.tooltipProvider = tooltipProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawTooltips(int x, int y, List<GuiButton> buttonList) {
/* 28 */     if (Math.abs(x - this.lastMouseX) <= 5 && Math.abs(y - this.lastMouseY) <= 5) {
/*    */       
/* 30 */       int i = 700;
/*    */       
/* 32 */       if (System.currentTimeMillis() >= this.mouseStillTime + i) {
/*    */         
/* 34 */         GuiButton guibutton = GuiScreenOF.getSelectedButton(x, y, buttonList);
/*    */         
/* 36 */         if (guibutton != null) {
/*    */           
/* 38 */           Rectangle rectangle = this.tooltipProvider.getTooltipBounds(this.guiScreen, x, y);
/* 39 */           String[] astring = this.tooltipProvider.getTooltipLines(guibutton, rectangle.width);
/*    */           
/* 41 */           if (astring != null) {
/*    */             
/* 43 */             if (astring.length > 8) {
/*    */               
/* 45 */               astring = Arrays.<String>copyOf(astring, 8);
/* 46 */               astring[astring.length - 1] = astring[astring.length - 1] + " ...";
/*    */             } 
/*    */             
/* 49 */             if (this.tooltipProvider.isRenderBorder()) {
/*    */               
/* 51 */               int j = -528449408;
/* 52 */               drawRectBorder(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, j);
/*    */             } 
/*    */             
/* 55 */             Gui.drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, -536870912);
/*    */             
/* 57 */             for (int l = 0; l < astring.length; l++)
/*    */             {
/* 59 */               String s = astring[l];
/* 60 */               int k = 14540253;
/*    */               
/* 62 */               if (s.endsWith("!"))
/*    */               {
/* 64 */                 k = 16719904;
/*    */               }
/*    */               
/* 67 */               FontRenderer fontrenderer = (Minecraft.getMinecraft()).MCfontRenderer;
/* 68 */               fontrenderer.drawStringWithShadow(s, (rectangle.x + 5), (rectangle.y + 5 + l * 11), k);
/*    */             }
/*    */           
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } else {
/*    */       
/* 76 */       this.lastMouseX = x;
/* 77 */       this.lastMouseY = y;
/* 78 */       this.mouseStillTime = System.currentTimeMillis();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void drawRectBorder(int x1, int y1, int x2, int y2, int col) {
/* 84 */     Gui.drawRect(x1, y1 - 1, x2, y1, col);
/* 85 */     Gui.drawRect(x1, y2, x2, y2 + 1, col);
/* 86 */     Gui.drawRect(x1 - 1, y1, x1, y2, col);
/* 87 */     Gui.drawRect(x2, y1, x2 + 1, y2, col);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\gui\TooltipManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */