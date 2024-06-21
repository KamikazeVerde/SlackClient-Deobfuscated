/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.optifine.gui.GuiButtonOF;
/*     */ import net.optifine.gui.GuiScreenCapeOF;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiCustomizeSkin
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private String title;
/*     */   
/*     */   public GuiCustomizeSkin(GuiScreen parentScreenIn) {
/*  19 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  28 */     int i = 0;
/*  29 */     this.title = I18n.format("options.skinCustomisation.title", new Object[0]);
/*     */     
/*  31 */     for (EnumPlayerModelParts enumplayermodelparts : EnumPlayerModelParts.values()) {
/*     */       
/*  33 */       this.buttonList.add(new ButtonPart(enumplayermodelparts.getPartId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 + 24 * (i >> 1), 150, 20, enumplayermodelparts));
/*  34 */       i++;
/*     */     } 
/*     */     
/*  37 */     if (i % 2 == 1)
/*     */     {
/*  39 */       i++;
/*     */     }
/*     */     
/*  42 */     this.buttonList.add(new GuiButtonOF(210, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("of.options.skinCustomisation.ofCape", new Object[0])));
/*  43 */     i += 2;
/*  44 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  52 */     if (button.enabled) {
/*     */       
/*  54 */       if (button.id == 210)
/*     */       {
/*  56 */         this.mc.displayGuiScreen((GuiScreen)new GuiScreenCapeOF(this));
/*     */       }
/*     */       
/*  59 */       if (button.id == 200) {
/*     */         
/*  61 */         this.mc.gameSettings.saveOptions();
/*  62 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*  64 */       else if (button instanceof ButtonPart) {
/*     */         
/*  66 */         EnumPlayerModelParts enumplayermodelparts = ((ButtonPart)button).playerModelParts;
/*  67 */         this.mc.gameSettings.switchModelPartEnabled(enumplayermodelparts);
/*  68 */         button.displayString = func_175358_a(enumplayermodelparts);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  78 */     drawDefaultBackground();
/*  79 */     drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
/*  80 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private String func_175358_a(EnumPlayerModelParts playerModelParts) {
/*     */     String s;
/*  87 */     if (this.mc.gameSettings.getModelParts().contains(playerModelParts)) {
/*     */       
/*  89 */       s = I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/*  93 */       s = I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/*  96 */     return playerModelParts.func_179326_d().getFormattedText() + ": " + s;
/*     */   }
/*     */   
/*     */   class ButtonPart
/*     */     extends GuiButton
/*     */   {
/*     */     private final EnumPlayerModelParts playerModelParts;
/*     */     
/*     */     private ButtonPart(int p_i45514_2_, int p_i45514_3_, int p_i45514_4_, int p_i45514_5_, int p_i45514_6_, EnumPlayerModelParts playerModelParts) {
/* 105 */       super(p_i45514_2_, p_i45514_3_, p_i45514_4_, p_i45514_5_, p_i45514_6_, GuiCustomizeSkin.this.func_175358_a(playerModelParts));
/* 106 */       this.playerModelParts = playerModelParts;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiCustomizeSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */