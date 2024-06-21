/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ import net.optifine.Lang;
/*    */ 
/*    */ public class TooltipProviderOptions
/*    */   implements TooltipProvider
/*    */ {
/*    */   public Rectangle getTooltipBounds(GuiScreen guiScreen, int x, int y) {
/* 15 */     int i = guiScreen.width / 2 - 150;
/* 16 */     int j = guiScreen.height / 6 - 7;
/*    */     
/* 18 */     if (y <= j + 98)
/*    */     {
/* 20 */       j += 105;
/*    */     }
/*    */     
/* 23 */     int k = i + 150 + 150;
/* 24 */     int l = j + 84 + 10;
/* 25 */     return new Rectangle(i, j, k - i, l - j);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRenderBorder() {
/* 30 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getTooltipLines(GuiButton btn, int width) {
/* 35 */     if (!(btn instanceof IOptionControl))
/*    */     {
/* 37 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 41 */     IOptionControl ioptioncontrol = (IOptionControl)btn;
/* 42 */     GameSettings.Options gamesettings$options = ioptioncontrol.getOption();
/* 43 */     String[] astring = getTooltipLines(gamesettings$options.getEnumString());
/* 44 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String[] getTooltipLines(String key) {
/* 50 */     List<String> list = new ArrayList<>();
/*    */     
/* 52 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 54 */       String s = key + ".tooltip." + (i + 1);
/* 55 */       String s1 = Lang.get(s, null);
/*    */       
/* 57 */       if (s1 == null) {
/*    */         break;
/*    */       }
/*    */ 
/*    */       
/* 62 */       list.add(s1);
/*    */     } 
/*    */     
/* 65 */     if (list.size() <= 0)
/*    */     {
/* 67 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 71 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 72 */     return astring;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\gui\TooltipProviderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */