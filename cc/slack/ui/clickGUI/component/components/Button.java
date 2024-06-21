/*     */ package cc.slack.ui.clickGUI.component.components;
/*     */ 
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.BooleanValue;
/*     */ import cc.slack.features.modules.api.settings.impl.ModeValue;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.ui.clickGUI.component.Component;
/*     */ import cc.slack.ui.clickGUI.component.Frame;
/*     */ import cc.slack.ui.clickGUI.component.components.sub.Checkbox;
/*     */ import cc.slack.ui.clickGUI.component.components.sub.Keybind;
/*     */ import cc.slack.ui.clickGUI.component.components.sub.ModeButton;
/*     */ import cc.slack.ui.clickGUI.component.components.sub.Slider;
/*     */ import cc.slack.utils.client.mc;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Button
/*     */   extends Component
/*     */ {
/*     */   public Module mod;
/*     */   public Frame parent;
/*     */   public int offset;
/*     */   private boolean isHovered;
/*     */   private final ArrayList<Component> subcomponents;
/*     */   public boolean open;
/*     */   
/*     */   public Button(Module mod, Frame parent, int offset) {
/*  33 */     this.mod = mod;
/*  34 */     this.parent = parent;
/*  35 */     this.offset = offset;
/*  36 */     this.subcomponents = new ArrayList<>();
/*  37 */     this.open = false;
/*  38 */     int opY = offset + 12;
/*  39 */     if (mod.getSetting() != null) {
/*  40 */       for (Value value : mod.getSetting()) {
/*  41 */         if (value instanceof ModeValue) {
/*  42 */           this.subcomponents.add(new ModeButton((ModeValue)value, this, mod, opY));
/*  43 */           opY += 12;
/*     */         } 
/*  45 */         if (value instanceof NumberValue) {
/*  46 */           this.subcomponents.add(new Slider((NumberValue)value, this, opY));
/*  47 */           opY += 12;
/*     */         } 
/*  49 */         if (value instanceof BooleanValue) {
/*  50 */           this.subcomponents.add(new Checkbox((BooleanValue)value, this, opY));
/*  51 */           opY += 12;
/*     */         } 
/*     */       } 
/*     */     }
/*  55 */     this.subcomponents.add(new Keybind(this, opY));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOff(int newOff) {
/*  60 */     this.offset = newOff;
/*  61 */     int opY = this.offset + 12;
/*  62 */     for (Component comp : this.subcomponents) {
/*  63 */       comp.setOff(opY);
/*  64 */       opY += 12;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderComponent() {
/*  70 */     Gui.drawRect(this.parent.getX(), this.parent.getY() + this.offset, this.parent.getX() + this.parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggle() ? (new Color(-14540254)).darker().getRGB() : -14540254) : (this.mod.isToggle() ? (new Color(14, 14, 14)).getRGB() : -15658735));
/*  71 */     GL11.glPushMatrix();
/*  72 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  73 */     mc.getFontRenderer().drawStringWithShadow(this.mod.getName(), ((this.parent.getX() + 2) * 2), ((this.parent.getY() + this.offset + 2) * 2 + 4), this.mod.isToggle() ? 10066329 : -1);
/*  74 */     if (this.subcomponents.size() > 2)
/*  75 */       mc.getFontRenderer().drawStringWithShadow(this.open ? "-" : "+", ((this.parent.getX() + this.parent.getWidth() - 10) * 2), ((this.parent.getY() + this.offset + 2) * 2 + 4), -1); 
/*  76 */     GL11.glPopMatrix();
/*  77 */     if (this.open && 
/*  78 */       !this.subcomponents.isEmpty()) {
/*  79 */       this.subcomponents.forEach(Component::renderComponent);
/*  80 */       Gui.drawRect(this.parent.getX() + 2, this.parent.getY() + this.offset + 12, this.parent.getX() + 3, this.parent.getY() + this.offset + (this.subcomponents.size() + 1) * 12, -379081);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  87 */     if (this.open) {
/*  88 */       return 12 * (this.subcomponents.size() + 1);
/*     */     }
/*  90 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateComponent(int mouseX, int mouseY) {
/*  95 */     this.isHovered = isMouseOnButton(mouseX, mouseY);
/*  96 */     if (!this.subcomponents.isEmpty()) {
/*  97 */       this.subcomponents.forEach(comp -> comp.updateComponent(mouseX, mouseY));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseClicked(int mouseX, int mouseY, int button) {
/* 103 */     if (isMouseOnButton(mouseX, mouseY)) {
/* 104 */       switch (button) {
/*     */         case 0:
/* 106 */           this.mod.toggle();
/*     */           break;
/*     */         case 1:
/* 109 */           this.open = !this.open;
/* 110 */           this.parent.refresh();
/*     */           break;
/*     */       } 
/*     */     }
/* 114 */     this.subcomponents.forEach(comp -> comp.mouseClicked(mouseX, mouseY, button));
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
/* 119 */     this.subcomponents.forEach(comp -> comp.mouseReleased(mouseX, mouseY, mouseButton));
/*     */   }
/*     */ 
/*     */   
/*     */   public void keyTyped(char typedChar, int key) {
/* 124 */     this.subcomponents.forEach(comp -> comp.keyTyped(typedChar, key));
/*     */   }
/*     */   
/*     */   public boolean isMouseOnButton(int x, int y) {
/* 128 */     if (x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
/* 129 */       return true;
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\clickGUI\component\components\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */