/*    */ package cc.slack.ui.NewCGUI;
/*    */ 
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.ui.NewCGUI.components.Components;
/*    */ import cc.slack.ui.NewCGUI.components.impl.CategoryComp;
/*    */ import cc.slack.utils.client.mc;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ 
/*    */ public class TransparentClickGUI extends GuiScreen {
/* 13 */   protected static final List<CategoryComp> frames = new ArrayList<>();
/*    */   
/*    */   public TransparentClickGUI() {
/* 16 */     int posX = 114;
/* 17 */     int gap = 1;
/* 18 */     for (Category category : Category.values()) {
/* 19 */       CategoryComp catComp = new CategoryComp(category, posX, gap, 110, 15);
/* 20 */       catComp.init();
/* 21 */       frames.add(catComp);
/* 22 */       posX += catComp.getWidth() + gap;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 28 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 29 */     frames.forEach(categoryComp -> categoryComp.draw(mc.getFontRenderer(), mouseX, mouseY, partialTicks));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 34 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 35 */     frames.forEach(categoryComp -> categoryComp.mouseClicked(mouseX, mouseY, mouseButton));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 40 */     super.keyTyped(typedChar, keyCode);
/* 41 */     frames.forEach(categoryComp -> categoryComp.keyClicked(typedChar, keyCode));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 46 */     super.mouseReleased(mouseX, mouseY, state);
/* 47 */     frames.forEach(categoryComp -> categoryComp.mouseReleased(mouseX, mouseY, state));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onGuiClosed() {
/* 52 */     super.onGuiClosed();
/* 53 */     frames.forEach(Components::close);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\NewCGUI\TransparentClickGUI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */