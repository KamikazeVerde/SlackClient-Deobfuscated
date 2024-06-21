/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*    */ import net.optifine.CustomLoadingScreen;
/*    */ import net.optifine.CustomLoadingScreens;
/*    */ 
/*    */ public class GuiDownloadTerrain extends GuiScreen {
/*    */   private NetHandlerPlayClient netHandlerPlayClient;
/*    */   private int progress;
/* 14 */   private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();
/*    */ 
/*    */   
/*    */   public GuiDownloadTerrain(NetHandlerPlayClient netHandler) {
/* 18 */     this.netHandlerPlayClient = netHandler;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 35 */     this.buttonList.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateScreen() {
/* 43 */     this.progress++;
/*    */     
/* 45 */     if (this.progress % 20 == 0)
/*    */     {
/* 47 */       this.netHandlerPlayClient.addToSendQueue((Packet)new C00PacketKeepAlive());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 56 */     if (this.customLoadingScreen != null) {
/*    */       
/* 58 */       this.customLoadingScreen.drawBackground(this.width, this.height);
/*    */     }
/*    */     else {
/*    */       
/* 62 */       drawBackground(0);
/*    */     } 
/*    */     
/* 65 */     drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingTerrain", new Object[0]), this.width / 2, this.height / 2 - 50, 16777215);
/* 66 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean doesGuiPauseGame() {
/* 74 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiDownloadTerrain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */