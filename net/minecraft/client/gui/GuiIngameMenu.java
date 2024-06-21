/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.achievement.GuiAchievements;
/*     */ import net.minecraft.client.gui.achievement.GuiStats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiIngameMenu
/*     */   extends GuiScreen
/*     */ {
/*     */   private int field_146445_a;
/*     */   private int field_146444_f;
/*     */   
/*     */   public void initGui() {
/*  21 */     this.field_146445_a = 0;
/*  22 */     this.buttonList.clear();
/*  23 */     int i = -16;
/*  24 */     int j = 98;
/*  25 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));
/*     */     
/*  27 */     if (!this.mc.isIntegratedServerRunning())
/*     */     {
/*  29 */       ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
/*     */     }
/*     */     
/*  32 */     this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
/*  33 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
/*     */     GuiButton guibutton;
/*  35 */     this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
/*  36 */     this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
/*  37 */     this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
/*  38 */     guibutton.enabled = (this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*     */     boolean flag;
/*  46 */     switch (button.id) {
/*     */       
/*     */       case 0:
/*  49 */         this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */ 
/*     */       
/*     */       case 1:
/*  53 */         flag = this.mc.isIntegratedServerRunning();
/*  54 */         button.enabled = false;
/*  55 */         this.mc.theWorld.sendQuittingDisconnectingPacket();
/*  56 */         this.mc.loadWorld(null);
/*     */         
/*  58 */         if (flag) {
/*     */           
/*  60 */           this.mc.displayGuiScreen(new GuiMainMenu());
/*     */         }
/*     */         else {
/*     */           
/*  64 */           this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
/*     */         } 
/*     */ 
/*     */       
/*     */       default:
/*     */         return;
/*     */ 
/*     */       
/*     */       case 4:
/*  73 */         this.mc.displayGuiScreen(null);
/*  74 */         this.mc.setIngameFocus();
/*     */ 
/*     */       
/*     */       case 5:
/*  78 */         this.mc.displayGuiScreen((GuiScreen)new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
/*     */ 
/*     */       
/*     */       case 6:
/*  82 */         this.mc.displayGuiScreen((GuiScreen)new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
/*     */       case 7:
/*     */         break;
/*     */     } 
/*  86 */     this.mc.displayGuiScreen(new GuiShareToLan(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  95 */     super.updateScreen();
/*  96 */     this.field_146444_f++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 104 */     drawDefaultBackground();
/* 105 */     drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);
/* 106 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiIngameMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */