/*    */ package net.optifine.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiOptionButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class GuiMessage
/*    */   extends GuiScreen {
/*    */   private GuiScreen parentScreen;
/*    */   private String messageLine1;
/*    */   private String messageLine2;
/* 17 */   private final List listLines2 = Lists.newArrayList();
/*    */   
/*    */   protected String confirmButtonText;
/*    */   private int ticksUntilEnable;
/*    */   
/*    */   public GuiMessage(GuiScreen parentScreen, String line1, String line2) {
/* 23 */     this.parentScreen = parentScreen;
/* 24 */     this.messageLine1 = line1;
/* 25 */     this.messageLine2 = line2;
/* 26 */     this.confirmButtonText = I18n.format("gui.done", new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 35 */     this.buttonList.add(new GuiOptionButton(0, this.width / 2 - 74, this.height / 6 + 96, this.confirmButtonText));
/* 36 */     this.listLines2.clear();
/* 37 */     this.listLines2.addAll(this.fontRendererObj.listFormattedStringToWidth(this.messageLine2, this.width - 50));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) throws IOException {
/* 45 */     Config.getMinecraft().displayGuiScreen(this.parentScreen);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 53 */     drawDefaultBackground();
/* 54 */     drawCenteredString(this.fontRendererObj, this.messageLine1, this.width / 2, 70, 16777215);
/* 55 */     int i = 90;
/*    */     
/* 57 */     for (Object e : this.listLines2) {
/*    */       
/* 59 */       String s = (String)e;
/* 60 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
/* 61 */       i += this.fontRendererObj.FONT_HEIGHT;
/*    */     } 
/*    */     
/* 64 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setButtonDelay(int ticksUntilEnable) {
/* 69 */     this.ticksUntilEnable = ticksUntilEnable;
/*    */     
/* 71 */     for (GuiButton guibutton : this.buttonList)
/*    */     {
/* 73 */       guibutton.enabled = false;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 82 */     super.updateScreen();
/*    */     
/* 84 */     if (--this.ticksUntilEnable == 0)
/*    */     {
/* 86 */       for (GuiButton guibutton : this.buttonList)
/*    */       {
/* 88 */         guibutton.enabled = true;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\gui\GuiMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */