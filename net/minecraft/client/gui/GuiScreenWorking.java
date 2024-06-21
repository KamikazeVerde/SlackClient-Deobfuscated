/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.util.IProgressUpdate;
/*    */ import net.optifine.CustomLoadingScreen;
/*    */ import net.optifine.CustomLoadingScreens;
/*    */ 
/*    */ public class GuiScreenWorking
/*    */   extends GuiScreen implements IProgressUpdate {
/*  9 */   private String field_146591_a = "";
/* 10 */   private String field_146589_f = "";
/*    */   private int progress;
/*    */   private boolean doneWorking;
/* 13 */   private CustomLoadingScreen customLoadingScreen = CustomLoadingScreens.getCustomLoadingScreen();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void displaySavingString(String message) {
/* 20 */     resetProgressAndMessage(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetProgressAndMessage(String message) {
/* 29 */     this.field_146591_a = message;
/* 30 */     displayLoadingString("Working...");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void displayLoadingString(String message) {
/* 38 */     this.field_146589_f = message;
/* 39 */     setLoadingProgress(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLoadingProgress(int progress) {
/* 47 */     this.progress = progress;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDoneWorking() {
/* 52 */     this.doneWorking = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 60 */     if (this.doneWorking) {
/*    */       
/* 62 */       if (!this.mc.func_181540_al())
/*    */       {
/* 64 */         this.mc.displayGuiScreen(null);
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 69 */       if (this.customLoadingScreen != null && this.mc.theWorld == null) {
/*    */         
/* 71 */         this.customLoadingScreen.drawBackground(this.width, this.height);
/*    */       }
/*    */       else {
/*    */         
/* 75 */         drawDefaultBackground();
/*    */       } 
/*    */       
/* 78 */       if (this.progress > 0) {
/*    */         
/* 80 */         drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
/* 81 */         drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.progress + "%", this.width / 2, 90, 16777215);
/*    */       } 
/*    */       
/* 84 */       super.drawScreen(mouseX, mouseY, partialTicks);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiScreenWorking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */