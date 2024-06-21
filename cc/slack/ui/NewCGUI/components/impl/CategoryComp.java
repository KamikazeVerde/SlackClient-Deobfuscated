/*    */ package cc.slack.ui.NewCGUI.components.impl;
/*    */ 
/*    */ import cc.slack.Slack;
/*    */ import cc.slack.features.modules.api.Category;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.ui.NewCGUI.components.Components;
/*    */ import cc.slack.utils.render.Render2DUtil;
/*    */ import java.awt.Color;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class CategoryComp
/*    */   extends Components
/*    */ {
/*    */   public void setDragX(int dragX) {
/* 19 */     this.dragX = dragX; } public void setDragY(int dragY) { this.dragY = dragY; } public void setCollapsed(boolean collapsed) { this.collapsed = collapsed; } public void setDragging(boolean dragging) { this.dragging = dragging; } public void setCategory(Category category) { this.category = category; }
/*    */   
/*    */   private int dragX; private int dragY; private boolean collapsed;
/* 22 */   protected final List<ModuleComp> modules = new ArrayList<>(); private boolean dragging; private Category category; public List<ModuleComp> getModules() { return this.modules; }
/* 23 */   public int getDragX() { return this.dragX; } public int getDragY() { return this.dragY; }
/* 24 */   public boolean isCollapsed() { return this.collapsed; } public boolean isDragging() { return this.dragging; } public Category getCategory() {
/* 25 */     return this.category;
/*    */   }
/*    */   public CategoryComp(Category category, int posX, int posY, int width, int height) {
/* 28 */     this.category = category;
/* 29 */     setPosX(posX);
/* 30 */     setPosY(posY);
/* 31 */     setWidth(width);
/* 32 */     setHeight(height);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 37 */     int offsetY = 1;
/* 38 */     for (Module module : Slack.getInstance().getModuleManager().getModulesByCategoryABC(this.category)) {
/* 39 */       ModuleComp moduleComp = new ModuleComp(module, this, offsetY);
/* 40 */       this.modules.add(moduleComp);
/* 41 */       offsetY++;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(int x, int y) {
/* 47 */     if (this.dragging) {
/* 48 */       setPosX(MathHelper.clamp_int(x - this.dragX, 0, getSR().getScaledWidth() - getWidth()));
/* 49 */       setPosY(MathHelper.clamp_int(y - this.dragY, 0, getSR().getScaledHeight() - getHeight()));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(FontRenderer font, int mouseX, int mouseY, float partialTicks) {
/* 55 */     Gui.drawRect(getPosX(), getPosY(), getPosX() + getWidth(), getPosY() + getHeight(), getCategory().getColorRGB());
/*    */     
/* 57 */     font.drawStringWithShadow(getCategory().getName(), getPosX() + 5.0F, getPosY() + 4.0F, Color.WHITE.getRGB());
/* 58 */     font.drawStringWithShadow(isCollapsed() ? "+" : "-", (getPosX() + getWidth()) - 10.0F, getPosY() + 4.0F, Color.WHITE.getRGB());
/*    */     
/* 60 */     if (!isCollapsed()) {
/* 61 */       this.modules.forEach(moduleComp -> moduleComp.draw(font, mouseX, mouseY, partialTicks));
/*    */     }
/*    */     
/* 64 */     update(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 69 */     if (!isCollapsed()) {
/* 70 */       this.modules.forEach(moduleComp -> moduleComp.mouseClicked(mouseX, mouseY, button));
/*    */     }
/* 72 */     if (!Render2DUtil.mouseInArea(mouseX, mouseY, getPosX(), getPosY(), getWidth(), getHeight()))
/* 73 */       return;  switch (button) {
/*    */       case 0:
/* 75 */         this.dragging = true;
/* 76 */         this.dragX = mouseX - getPosX();
/* 77 */         this.dragY = mouseY - getPosY();
/*    */         break;
/*    */       case 1:
/* 80 */         this.collapsed = !this.collapsed;
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 87 */     this.dragging = false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void keyClicked(char typedChar, int key) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 97 */     this.dragging = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\NewCGUI\components\impl\CategoryComp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */