/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiButton extends Gui {
/*  11 */   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
/*     */ 
/*     */   
/*     */   protected int width;
/*     */ 
/*     */   
/*     */   protected int height;
/*     */ 
/*     */   
/*     */   public int xPosition;
/*     */ 
/*     */   
/*     */   public int yPosition;
/*     */ 
/*     */   
/*     */   public String displayString;
/*     */   
/*     */   public int id;
/*     */   
/*     */   public boolean enabled;
/*     */   
/*     */   public boolean visible;
/*     */   
/*     */   protected boolean hovered;
/*     */ 
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, String buttonText) {
/*  38 */     this(buttonId, x, y, 200, 20, buttonText);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/*  43 */     this.width = 200;
/*  44 */     this.height = 20;
/*  45 */     this.enabled = true;
/*  46 */     this.visible = true;
/*  47 */     this.id = buttonId;
/*  48 */     this.xPosition = x;
/*  49 */     this.yPosition = y;
/*  50 */     this.width = widthIn;
/*  51 */     this.height = heightIn;
/*  52 */     this.displayString = buttonText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHoverState(boolean mouseOver) {
/*  61 */     int i = 1;
/*     */     
/*  63 */     if (!this.enabled) {
/*     */       
/*  65 */       i = 0;
/*     */     }
/*  67 */     else if (mouseOver) {
/*     */       
/*  69 */       i = 2;
/*     */     } 
/*     */     
/*  72 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/*  80 */     if (this.visible) {
/*     */       
/*  82 */       FontRenderer fontrenderer = mc.MCfontRenderer;
/*  83 */       mc.getTextureManager().bindTexture(buttonTextures);
/*  84 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  85 */       this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*  86 */       int i = getHoverState(this.hovered);
/*  87 */       GlStateManager.enableBlend();
/*  88 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  89 */       GlStateManager.blendFunc(770, 771);
/*  90 */       drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
/*  91 */       drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
/*  92 */       mouseDragged(mc, mouseX, mouseY);
/*  93 */       int j = 14737632;
/*     */       
/*  95 */       if (!this.enabled) {
/*     */         
/*  97 */         j = 10526880;
/*     */       }
/*  99 */       else if (this.hovered) {
/*     */         
/* 101 */         j = 16777120;
/*     */       } 
/*     */       
/* 104 */       drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 128 */     return (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMouseOver() {
/* 136 */     return this.hovered;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public void playPressSound(SoundHandler soundHandlerIn) {
/* 145 */     soundHandlerIn.playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getButtonWidth() {
/* 150 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 155 */     this.width = width;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */