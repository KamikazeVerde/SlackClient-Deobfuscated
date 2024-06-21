/*     */ package cc.slack.ui.clickGUI.component;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.ui.clickGUI.component.components.Button;
/*     */ import cc.slack.utils.client.mc;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Frame
/*     */ {
/*     */   public ArrayList<Component> components;
/*     */   public Category category;
/*     */   private boolean open;
/*     */   private final int width;
/*     */   private int y;
/*     */   private int x;
/*     */   private final int height;
/*     */   private boolean isDragging;
/*     */   public int dragX;
/*     */   public int dragY;
/*     */   
/*     */   public Frame(Category cat) {
/*  33 */     this.components = new ArrayList<>();
/*  34 */     this.category = cat;
/*  35 */     this.width = 88;
/*  36 */     this.x = 5;
/*  37 */     this.y = 5;
/*  38 */     this.height = 13;
/*  39 */     this.dragX = 0;
/*  40 */     this.open = false;
/*  41 */     this.isDragging = false;
/*  42 */     int tY = this.height;
/*     */     
/*  44 */     for (Module mod : Slack.getInstance().getModuleManager().getModulesByCategory(this.category)) {
/*  45 */       Button modButton = new Button(mod, this, tY);
/*  46 */       this.components.add(modButton);
/*  47 */       tY += 12;
/*     */     } 
/*     */   }
/*     */   
/*     */   public ArrayList<Component> getComponents() {
/*  52 */     return this.components;
/*     */   }
/*     */   
/*     */   public void setX(int newX) {
/*  56 */     this.x = newX;
/*     */   }
/*     */   
/*     */   public void setY(int newY) {
/*  60 */     this.y = newY;
/*     */   }
/*     */   
/*     */   public void setDrag(boolean drag) {
/*  64 */     this.isDragging = drag;
/*     */   }
/*     */   
/*     */   public boolean isOpen() {
/*  68 */     return this.open;
/*     */   }
/*     */   
/*     */   public void setOpen(boolean open) {
/*  72 */     this.open = open;
/*     */   }
/*     */   
/*     */   public void renderFrame(FontRenderer fontRenderer) {
/*  76 */     Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, this.category.getColorRGB());
/*  77 */     GL11.glPushMatrix();
/*  78 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  79 */     fontRenderer.drawStringWithShadow(this.category.name(), ((this.x + 2) * 2 + 5), (this.y + 2.5F) * 2.0F + 5.0F, -1);
/*  80 */     fontRenderer.drawStringWithShadow(this.open ? "-" : "+", ((this.x + this.width - 10) * 2 + 5), (this.y + 2.5F) * 2.0F + 5.0F, -1);
/*  81 */     GL11.glPopMatrix();
/*  82 */     if (this.open && 
/*  83 */       !this.components.isEmpty())
/*     */     {
/*     */ 
/*     */       
/*  87 */       this.components.forEach(Component::renderComponent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void refresh() {
/*  93 */     int off = this.height;
/*  94 */     for (Component comp : this.components) {
/*  95 */       comp.setOff(off);
/*  96 */       off += comp.getHeight();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getX() {
/* 101 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/* 105 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 109 */     return this.width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 113 */     return this.height;
/*     */   }
/*     */   
/*     */   public void updatePosition(int mouseX, int mouseY) {
/* 117 */     ScaledResolution sr = new ScaledResolution(mc.getMinecraft());
/*     */     
/* 119 */     if (this.isDragging) {
/* 120 */       setX(MathHelper.clamp_int(mouseX - this.dragX, 0, sr.getScaledWidth() - getWidth()));
/* 121 */       setY(MathHelper.clamp_int(mouseY - this.dragY, 0, sr.getScaledHeight() - getHeight()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isWithinHeader(int x, int y) {
/* 126 */     if (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height) {
/* 127 */       return true;
/*     */     }
/* 129 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\clickGUI\component\Frame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */