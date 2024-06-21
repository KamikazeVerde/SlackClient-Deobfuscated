/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class ServerListEntryLanScan
/*    */   implements GuiListExtended.IGuiListEntry {
/*  8 */   private final Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*    */     String s;
/* 12 */     int i = y + slotHeight / 2 - this.mc.MCfontRenderer.FONT_HEIGHT / 2;
/* 13 */     this.mc.MCfontRenderer.drawString(I18n.format("lanServer.scanning", new Object[0]), this.mc.currentScreen.width / 2 - this.mc.MCfontRenderer.getStringWidth(I18n.format("lanServer.scanning", new Object[0])) / 2, i, 16777215);
/*    */ 
/*    */     
/* 16 */     switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
/*    */ 
/*    */       
/*    */       default:
/* 20 */         s = "O o o";
/*    */         break;
/*    */       
/*    */       case 1:
/*    */       case 3:
/* 25 */         s = "o O o";
/*    */         break;
/*    */       
/*    */       case 2:
/* 29 */         s = "o o O";
/*    */         break;
/*    */     } 
/* 32 */     this.mc.MCfontRenderer.drawString(s, this.mc.currentScreen.width / 2 - this.mc.MCfontRenderer.getStringWidth(s) / 2, i + this.mc.MCfontRenderer.FONT_HEIGHT, 8421504);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\ServerListEntryLanScan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */