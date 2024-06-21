/*    */ package cc.slack.ui.clickGUI;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.ui.clickGUI.component.Component;
/*    */ import cc.slack.ui.clickGUI.component.Frame;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class ClickGui
/*    */   extends GuiScreen {
/*    */   public static ArrayList<Frame> frames;
/*    */   
/*    */   public ClickGui() {
/* 15 */     this; frames = new ArrayList<>();
/* 16 */     int frameX = 5;
/* 17 */     for (Category category : Category.values()) {
/* 18 */       Frame frame = new Frame(category);
/* 19 */       frame.setX(frameX);
/* 20 */       frames.add(frame);
/* 21 */       frameX += frame.getWidth() + 1;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 27 */     drawDefaultBackground();
/* 28 */     frames.forEach(frame -> {
/*    */           frame.renderFrame(this.fontRendererObj);
/*    */           frame.updatePosition(mouseX, mouseY);
/*    */           frame.getComponents().forEach(());
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 37 */     frames.forEach(frame -> {
/*    */           if (frame.isWithinHeader(mouseX, mouseY)) {
/*    */             switch (mouseButton) {
/*    */               case 0:
/*    */                 frame.setDrag(true);
/*    */                 frame.dragX = mouseX - frame.getX();
/*    */                 frame.dragY = mouseY - frame.getY();
/*    */                 break;
/*    */               case 1:
/*    */                 frame.setOpen(!frame.isOpen());
/*    */                 break;
/*    */             } 
/*    */           }
/*    */           if (frame.isOpen() && !frame.getComponents().isEmpty()) {
/*    */             frame.getComponents().forEach(());
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) {
/* 60 */     frames.forEach(frame -> {
/*    */           if (frame.isOpen() && keyCode != 1 && !frame.getComponents().isEmpty()) {
/*    */             frame.getComponents().forEach(());
/*    */           }
/*    */         });
/*    */ 
/*    */     
/* 67 */     if (keyCode == 1) {
/* 68 */       this.mc.displayGuiScreen(null);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 75 */     frames.forEach(frame -> {
/*    */           frame.setDrag(false);
/*    */           if (frame.isOpen() && !frame.getComponents().isEmpty())
/*    */             frame.getComponents().forEach(()); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\clickGUI\ClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */