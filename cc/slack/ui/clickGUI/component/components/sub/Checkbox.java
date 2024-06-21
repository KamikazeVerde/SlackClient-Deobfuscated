/*    */ package cc.slack.ui.clickGUI.component.components.sub;
/*    */ 
/*    */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*    */ import cc.slack.ui.clickGUI.component.Component;
/*    */ import cc.slack.ui.clickGUI.component.components.Button;
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class Checkbox
/*    */   extends Component
/*    */ {
/*    */   private boolean hovered;
/*    */   private final BooleanValue op;
/*    */   private Button parent;
/*    */   private int offset;
/*    */   private int x;
/*    */   private int y;
/*    */   
/*    */   public Checkbox(BooleanValue option, Button button, int offset) {
/* 21 */     this.op = option;
/* 22 */     this.parent = button;
/* 23 */     this.x = button.parent.getX() + button.parent.getWidth();
/* 24 */     this.y = button.parent.getY() + button.offset;
/* 25 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderComponent() {
/* 30 */     Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
/* 31 */     Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
/* 32 */     GL11.glPushMatrix();
/* 33 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 34 */     mc.getFontRenderer().drawStringWithShadow(this.op.getName(), ((this.parent.parent.getX() + 10 + 4) * 2 + 5), ((this.parent.parent.getY() + this.offset + 2) * 2 + 4), -1);
/* 35 */     GL11.glPopMatrix();
/* 36 */     Gui.drawRect(this.parent.parent.getX() + 3 + 4, this.parent.parent.getY() + this.offset + 3, this.parent.parent.getX() + 9 + 4, this.parent.parent.getY() + this.offset + 9, -6710887);
/* 37 */     if (((Boolean)this.op.getValue()).booleanValue()) {
/* 38 */       Gui.drawRect(this.parent.parent.getX() + 4 + 4, this.parent.parent.getY() + this.offset + 4, this.parent.parent.getX() + 8 + 4, this.parent.parent.getY() + this.offset + 8, -10066330);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setOff(int newOff) {
/* 43 */     this.offset = newOff;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateComponent(int mouseX, int mouseY) {
/* 48 */     this.hovered = isMouseOnButton(mouseX, mouseY);
/* 49 */     this.y = this.parent.parent.getY() + this.offset;
/* 50 */     this.x = this.parent.parent.getX();
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 55 */     if (isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
/* 56 */       this.op.setValue(Boolean.valueOf(!((Boolean)this.op.getValue()).booleanValue()));
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isMouseOnButton(int x, int y) {
/* 61 */     if (x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
/* 62 */       return true;
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\clickGUI\component\components\sub\Checkbox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */