/*    */ package cc.slack.ui.clickGUI.component.components.sub;
/*    */ 
/*    */ import cc.slack.features.modules.api.Module;
/*    */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*    */ import cc.slack.ui.clickGUI.component.Component;
/*    */ import cc.slack.ui.clickGUI.component.components.Button;
/*    */ import cc.slack.utils.client.mc;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class ModeButton
/*    */   extends Component
/*    */ {
/*    */   private boolean hovered;
/*    */   private final Button parent;
/*    */   private final ModeValue set;
/*    */   private int offset;
/*    */   private int x;
/*    */   private int y;
/*    */   private final Module mod;
/*    */   private int modeIndex;
/*    */   
/*    */   public ModeButton(ModeValue set, Button button, Module mod, int offset) {
/* 24 */     this.set = set;
/* 25 */     this.parent = button;
/* 26 */     this.mod = mod;
/* 27 */     this.x = button.parent.getX() + button.parent.getWidth();
/* 28 */     this.y = button.parent.getY() + button.offset;
/* 29 */     this.offset = offset;
/* 30 */     this.modeIndex = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setOff(int newOff) {
/* 35 */     this.offset = newOff;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderComponent() {
/* 40 */     Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
/* 41 */     Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
/* 42 */     GL11.glPushMatrix();
/* 43 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 44 */     String prefix = (this.set.getName() == null) ? "Mode: " : (this.set.getName() + " Mode: ");
/* 45 */     mc.getFontRenderer().drawStringWithShadow(prefix + this.set.getValue().toString(), ((this.parent.parent.getX() + 7) * 2), ((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
/* 46 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateComponent(int mouseX, int mouseY) {
/* 51 */     this.hovered = isMouseOnButton(mouseX, mouseY);
/* 52 */     this.y = this.parent.parent.getY() + this.offset;
/* 53 */     this.x = this.parent.parent.getX();
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 58 */     if (isMouseOnButton(mouseX, mouseY) && this.parent.open) {
/* 59 */       int maxIndex = (this.set.getModes()).length - 1;
/* 60 */       switch (button) {
/*    */         case 0:
/* 62 */           if (this.modeIndex + 1 > maxIndex) {
/* 63 */             this.modeIndex = 0; break;
/*    */           } 
/* 65 */           this.modeIndex++;
/*    */           break;
/*    */         case 1:
/* 68 */           if (this.modeIndex - 1 < 0) {
/* 69 */             this.modeIndex = maxIndex; break;
/*    */           } 
/* 71 */           this.modeIndex--;
/*    */           break;
/*    */       } 
/*    */       
/* 75 */       this.set.setValueFromString(this.set.getModes()[this.modeIndex].toString());
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isMouseOnButton(int x, int y) {
/* 80 */     if (x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
/* 81 */       return true;
/*    */     }
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\clickGUI\component\components\sub\ModeButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */