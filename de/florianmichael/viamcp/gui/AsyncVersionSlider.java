/*     */ package de.florianmichael.viamcp.gui;
/*     */ 
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncVersionSlider
/*     */   extends GuiButton
/*     */ {
/*  31 */   private float dragValue = (ViaLoadingBase.getProtocols().size() - ViaLoadingBase.getInstance().getTargetVersion().getIndex()) / ViaLoadingBase.getProtocols().size();
/*     */   
/*     */   private final List<ProtocolVersion> values;
/*     */   
/*     */   private float sliderValue;
/*     */   public boolean dragging;
/*     */   
/*     */   public AsyncVersionSlider(int buttonId, int x, int y, int widthIn, int heightIn) {
/*  39 */     super(buttonId, x, y, Math.max(widthIn, 110), heightIn, "");
/*  40 */     this.values = ViaLoadingBase.getProtocols();
/*  41 */     Collections.reverse(this.values);
/*  42 */     this.sliderValue = this.dragValue;
/*  43 */     this.displayString = ((ProtocolVersion)this.values.get((int)(this.sliderValue * (this.values.size() - 1)))).getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/*  48 */     super.drawButton(mc, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHoverState(boolean mouseOver) {
/*  57 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/*  65 */     if (this.visible) {
/*     */       
/*  67 */       if (this.dragging) {
/*     */         
/*  69 */         this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/*  70 */         this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
/*  71 */         this.dragValue = this.sliderValue;
/*  72 */         this.displayString = ((ProtocolVersion)this.values.get((int)(this.sliderValue * (this.values.size() - 1)))).getName();
/*  73 */         ViaLoadingBase.getInstance().reload(this.values.get((int)(this.sliderValue * (this.values.size() - 1))));
/*     */       } 
/*     */       
/*  76 */       mc.getTextureManager().bindTexture(buttonTextures);
/*  77 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  78 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/*  79 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/*  89 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/*     */       
/*  91 */       this.sliderValue = (mouseX - this.xPosition + 4) / (this.width - 8);
/*  92 */       this.sliderValue = MathHelper.clamp_float(this.sliderValue, 0.0F, 1.0F);
/*  93 */       this.dragValue = this.sliderValue;
/*  94 */       this.displayString = ((ProtocolVersion)this.values.get((int)(this.sliderValue * (this.values.size() - 1)))).getName();
/*  95 */       ViaLoadingBase.getInstance().reload(this.values.get((int)(this.sliderValue * (this.values.size() - 1))));
/*  96 */       this.dragging = true;
/*  97 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 101 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {
/* 110 */     this.dragging = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVersion(int protocol) {
/* 115 */     this.dragValue = (ViaLoadingBase.getProtocols().size() - ViaLoadingBase.fromProtocolId(protocol).getIndex()) / ViaLoadingBase.getProtocols().size();
/* 116 */     this.sliderValue = this.dragValue;
/* 117 */     this.displayString = ((ProtocolVersion)this.values.get((int)(this.sliderValue * (this.values.size() - 1)))).getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\viamcp\gui\AsyncVersionSlider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */