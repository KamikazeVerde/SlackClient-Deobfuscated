/*     */ package de.florianmichael.viamcp.gui;
/*     */ 
/*     */ import cc.slack.utils.client.mc;
/*     */ import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
/*     */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
/*     */ import de.florianmichael.viamcp.protocolinfo.ProtocolInfo;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ChatFormatting;
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
/*     */ public class GuiProtocolSelector
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parent;
/*     */   public SlotList list;
/*     */   
/*     */   public GuiProtocolSelector(GuiScreen parent) {
/*  35 */     this.parent = parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  40 */     super.initGui();
/*  41 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 25, 200, 20, "Back"));
/*  42 */     this.list = new SlotList(this.mc, this.width, this.height, 32, this.height - 32);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton guiButton) throws IOException {
/*  47 */     this.list.actionPerformed(guiButton);
/*     */     
/*  49 */     if (guiButton.id == 1) {
/*  50 */       this.mc.displayGuiScreen(this.parent);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  56 */     this.list.handleMouseInput();
/*  57 */     super.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  62 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*  63 */     GlStateManager.pushMatrix();
/*  64 */     GlStateManager.scale(2.0D, 2.0D, 2.0D);
/*  65 */     String title = ChatFormatting.BOLD + "ViaMCP";
/*  66 */     drawString(this.fontRendererObj, title, (this.width - this.fontRendererObj.getStringWidth(title) * 2) / 4, 5, -1);
/*  67 */     GlStateManager.popMatrix();
/*     */     
/*  69 */     drawString(this.fontRendererObj, "by EnZaXD/Flori2007", 1, 1, -1);
/*  70 */     drawString(this.fontRendererObj, "Discord: EnZaXD#6257", 1, 11, -1);
/*     */     
/*  72 */     ProtocolInfo protocolInfo = ProtocolInfo.fromProtocolVersion((ProtocolVersion)ViaLoadingBase.getInstance().getTargetVersion());
/*     */     
/*  74 */     String versionTitle = "Version: " + ViaLoadingBase.getInstance().getTargetVersion().getName() + " - " + protocolInfo.getName();
/*  75 */     String versionReleased = "Released: " + protocolInfo.getReleaseDate();
/*     */     
/*  77 */     int fixedHeight = (5 + this.fontRendererObj.FONT_HEIGHT) * 2 + 2;
/*     */     
/*  79 */     drawString(this.fontRendererObj, ChatFormatting.GRAY + ChatFormatting.BOLD + "Version Information", (this.width - this.fontRendererObj.getStringWidth("Version Information")) / 2, fixedHeight, -1);
/*  80 */     drawString(this.fontRendererObj, versionTitle, (this.width - this.fontRendererObj.getStringWidth(versionTitle)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT, -1);
/*  81 */     drawString(this.fontRendererObj, versionReleased, (this.width - this.fontRendererObj.getStringWidth(versionReleased)) / 2, fixedHeight + this.fontRendererObj.FONT_HEIGHT * 2, -1);
/*     */     
/*  83 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   class SlotList
/*     */     extends GuiSlot {
/*     */     public SlotList(Minecraft mc, int width, int height, int top, int bottom) {
/*  89 */       super(mc, width, height, top + 30, bottom, 18);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/*  94 */       return ViaLoadingBase.getProtocols().size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int i, boolean b, int i1, int i2) {
/*  99 */       ProtocolVersion protocolVersion = ViaLoadingBase.getProtocols().get(i);
/* 100 */       ViaLoadingBase.getInstance().reload(protocolVersion);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int i) {
/* 105 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 110 */       GuiProtocolSelector.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
/* 115 */       GuiProtocolSelector.this.drawCenteredString(mc.getFontRenderer(), String.valueOf((ViaLoadingBase.getInstance().getTargetVersion().getIndex() == i) ? (String.valueOf(ChatFormatting.GREEN.toString()) + ChatFormatting.BOLD) : ChatFormatting.GRAY.toString()) + ((ProtocolVersion)ViaLoadingBase.getProtocols().get(i)).getName(), this.width / 2, i2 + 2, -1);
/* 116 */       GlStateManager.pushMatrix();
/* 117 */       GlStateManager.scale(0.5D, 0.5D, 0.5D);
/* 118 */       GuiProtocolSelector.this.drawCenteredString(mc.getFontRenderer(), "PVN: " + ((ProtocolVersion)ViaLoadingBase.getProtocols().get(i)).getVersion(), this.width, (i2 + 2) * 2 + 20, -1);
/* 119 */       GlStateManager.popMatrix();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\viamcp\gui\GuiProtocolSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */