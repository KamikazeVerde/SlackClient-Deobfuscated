/*     */ package cc.slack.ui.clickGUI.component.components.sub;
/*     */ 
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.ui.clickGUI.component.Component;
/*     */ import cc.slack.ui.clickGUI.component.components.Button;
/*     */ import cc.slack.utils.client.mc;
/*     */ import cc.slack.utils.other.MathUtil;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ public class Slider
/*     */   extends Component
/*     */ {
/*     */   private boolean hovered;
/*     */   private final NumberValue set;
/*     */   private final Button parent;
/*     */   private int offset;
/*     */   private int x;
/*     */   private int y;
/*     */   private boolean dragging = false;
/*     */   private double renderWidth;
/*     */   
/*     */   public Slider(NumberValue value, Button button, int offset) {
/*  25 */     this.set = value;
/*  26 */     this.parent = button;
/*  27 */     this.x = button.parent.getX() + button.parent.getWidth();
/*  28 */     this.y = button.parent.getY() + button.offset;
/*  29 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderComponent() {
/*  34 */     Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth(), this.parent.parent.getY() + this.offset + 12, this.hovered ? -14540254 : -15658735);
/*  35 */     Gui.drawRect(this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + (int)this.renderWidth, this.parent.parent.getY() + this.offset + 12, this.hovered ? -11184811 : -12303292);
/*  36 */     Gui.drawRect(this.parent.parent.getX(), this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 2, this.parent.parent.getY() + this.offset + 12, -15658735);
/*  37 */     GL11.glPushMatrix();
/*  38 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  39 */     mc.getFontRenderer().drawStringWithShadow(this.set.getName() + ": " + this.set.getValue(), (this.parent.parent.getX() * 2 + 15), ((this.parent.parent.getY() + this.offset + 2) * 2 + 5), -1);
/*     */     
/*  41 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOff(int newOff) {
/*  46 */     this.offset = newOff;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateComponent(int mouseX, int mouseY) {
/*  51 */     this.hovered = (isMouseOnButtonD(mouseX, mouseY) || isMouseOnButtonI(mouseX, mouseY));
/*  52 */     this.y = this.parent.parent.getY() + this.offset;
/*  53 */     this.x = this.parent.parent.getX();
/*     */     
/*  55 */     double diff = Math.min(88, Math.max(0, mouseX - this.x));
/*     */     
/*  57 */     double min = this.set.getMinimum().doubleValue();
/*  58 */     double max = this.set.getMaximum().doubleValue();
/*     */     
/*  60 */     Number value = (Number)this.set.getValue();
/*  61 */     this.renderWidth = 88.0D * (value.doubleValue() - min) / (max - min);
/*     */     
/*  63 */     if (this.dragging) {
/*  64 */       if (diff == 0.0D) {
/*  65 */         this.set.setValue(this.set.getMinimum());
/*     */       } else {
/*  67 */         double roundMath = diff / 88.0D * (max - min) + min;
/*  68 */         if (this.set.getMinimum() instanceof Integer) {
/*  69 */           this.set.setValue(Integer.valueOf((int)MathUtil.round(roundMath, 0)));
/*  70 */         } else if (this.set.getMinimum() instanceof Double) {
/*  71 */           this.set.setValue(Double.valueOf(MathUtil.round(MathUtil.roundToDecimalPlace(roundMath, this.set.getIncrement().doubleValue()), 2)));
/*  72 */         } else if (this.set.getMinimum() instanceof Float) {
/*  73 */           this.set.setValue(Float.valueOf((float)MathUtil.round(MathUtil.roundToDecimalPlace(roundMath, this.set.getIncrement().doubleValue()), 2)));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/*  81 */     if (isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
/*  82 */       this.dragging = true;
/*     */     }
/*  84 */     if (isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
/*  85 */       this.dragging = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
/*  91 */     this.dragging = false;
/*     */   }
/*     */   
/*     */   public boolean isMouseOnButtonD(int x, int y) {
/*  95 */     if (x > this.x && x < this.x + this.parent.parent.getWidth() / 2 + 1 && y > this.y && y < this.y + 12) {
/*  96 */       return true;
/*     */     }
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isMouseOnButtonI(int x, int y) {
/* 102 */     if (x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12) {
/* 103 */       return true;
/*     */     }
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\clickGUI\component\components\sub\Slider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */