/*    */ package cc.slack.ui.NewCGUI.components.impl;
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.ui.NewCGUI.components.Components;
/*    */ import cc.slack.utils.other.PrintUtil;
/*    */ import cc.slack.utils.render.Render2DUtil;
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ public class ModuleComp extends Components {
/*    */   private final Module module;
/*    */   private boolean expanded;
/*    */   
/*    */   public void setExpanded(boolean expanded) {
/* 15 */     this.expanded = expanded;
/*    */   }
/*    */   
/* 18 */   public Module getModule() { return this.module; } public boolean isExpanded() {
/* 19 */     return this.expanded;
/*    */   }
/*    */   public ModuleComp(Module module, Components parent, int offsetY) {
/* 22 */     this.module = module;
/* 23 */     setParent(parent);
/* 24 */     setOffsetY(offsetY);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(int x, int y) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(FontRenderer font, int mouseX, int mouseY, float partialTicks) {
/* 39 */     int boxPosY = getParent().getPosY() + getParent().getHeight() * getOffsetY();
/*    */     
/* 41 */     Gui.drawRect(getParent().getPosX(), boxPosY, getParent().getPosX() + getParent().getWidth(), boxPosY + getParent().getHeight(), (new Color(0, 0, 0, 100)).getRGB());
/* 42 */     font.drawStringWithShadow(this.module.getDisplayName(), getParent().getPosX() + 5.0F, boxPosY + 4.0F, Color.WHITE.getRGB());
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 47 */     if (!Render2DUtil.mouseInArea(mouseX, mouseY, getParent().getPosX(), (getParent().getPosY() + getParent().getHeight() * getOffsetY()), getParent().getWidth(), getParent().getHeight()))
/* 48 */       return;  switch (button) {
/*    */       case 0:
/* 50 */         this.module.toggle();
/*    */         break;
/*    */       case 1:
/* 53 */         this.expanded = !this.expanded;
/* 54 */         PrintUtil.message(this.module.getDisplayName() + " " + this.expanded);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY, int state) {}
/*    */   
/*    */   public void keyClicked(char typedChar, int key) {}
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\NewCGUI\components\impl\ModuleComp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */