/*    */ package net.optifine.gui;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiChat;
/*    */ import net.minecraft.client.gui.GuiVideoSettings;
/*    */ import net.minecraft.src.Config;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class GuiChatOF
/*    */   extends GuiChat
/*    */ {
/*    */   private static final String CMD_RELOAD_SHADERS = "/reloadShaders";
/*    */   private static final String CMD_RELOAD_CHUNKS = "/reloadChunks";
/*    */   
/*    */   public GuiChatOF(GuiChat guiChat) {
/* 15 */     super(GuiVideoSettings.getGuiChatText(guiChat));
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendChatMessage(String msg) {
/* 20 */     if (checkCustomCommand(msg)) {
/*    */       
/* 22 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*    */     }
/*    */     else {
/*    */       
/* 26 */       super.sendChatMessage(msg);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean checkCustomCommand(String msg) {
/* 32 */     if (msg == null)
/*    */     {
/* 34 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 38 */     msg = msg.trim();
/*    */     
/* 40 */     if (msg.equals("/reloadShaders")) {
/*    */       
/* 42 */       if (Config.isShaders()) {
/*    */         
/* 44 */         Shaders.uninit();
/* 45 */         Shaders.loadShaderPack();
/*    */       } 
/*    */       
/* 48 */       return true;
/*    */     } 
/* 50 */     if (msg.equals("/reloadChunks")) {
/*    */       
/* 52 */       this.mc.renderGlobal.loadRenderers();
/* 53 */       return true;
/*    */     } 
/*    */ 
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\gui\GuiChatOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */