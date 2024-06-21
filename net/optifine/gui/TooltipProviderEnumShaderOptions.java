/*    */ package net.optifine.gui;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.optifine.shaders.config.EnumShaderOption;
/*    */ import net.optifine.shaders.gui.GuiButtonEnumShaderOption;
/*    */ 
/*    */ 
/*    */ public class TooltipProviderEnumShaderOptions
/*    */   implements TooltipProvider
/*    */ {
/*    */   public Rectangle getTooltipBounds(GuiScreen guiScreen, int x, int y) {
/* 14 */     int i = guiScreen.width - 450;
/* 15 */     int j = 35;
/*    */     
/* 17 */     if (i < 10)
/*    */     {
/* 19 */       i = 10;
/*    */     }
/*    */     
/* 22 */     if (y <= j + 94)
/*    */     {
/* 24 */       j += 100;
/*    */     }
/*    */     
/* 27 */     int k = i + 150 + 150;
/* 28 */     int l = j + 84 + 10;
/* 29 */     return new Rectangle(i, j, k - i, l - j);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRenderBorder() {
/* 34 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getTooltipLines(GuiButton btn, int width) {
/* 39 */     if (btn instanceof net.optifine.shaders.gui.GuiButtonDownloadShaders)
/*    */     {
/* 41 */       return TooltipProviderOptions.getTooltipLines("of.options.shaders.DOWNLOAD");
/*    */     }
/* 43 */     if (!(btn instanceof GuiButtonEnumShaderOption))
/*    */     {
/* 45 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 49 */     GuiButtonEnumShaderOption guibuttonenumshaderoption = (GuiButtonEnumShaderOption)btn;
/* 50 */     EnumShaderOption enumshaderoption = guibuttonenumshaderoption.getEnumShaderOption();
/* 51 */     String[] astring = getTooltipLines(enumshaderoption);
/* 52 */     return astring;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private String[] getTooltipLines(EnumShaderOption option) {
/* 58 */     return TooltipProviderOptions.getTooltipLines(option.getResourceKey());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\gui\TooltipProviderEnumShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */