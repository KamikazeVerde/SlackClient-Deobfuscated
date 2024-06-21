/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.GuiAnimationSettingsOF;
/*     */ import net.optifine.gui.GuiDetailSettingsOF;
/*     */ import net.optifine.gui.GuiOptionButtonOF;
/*     */ import net.optifine.gui.GuiOptionSliderOF;
/*     */ import net.optifine.gui.GuiOtherSettingsOF;
/*     */ import net.optifine.gui.GuiPerformanceSettingsOF;
/*     */ import net.optifine.gui.GuiQualitySettingsOF;
/*     */ import net.optifine.gui.GuiScreenOF;
/*     */ import net.optifine.gui.TooltipManager;
/*     */ import net.optifine.gui.TooltipProvider;
/*     */ import net.optifine.gui.TooltipProviderOptions;
/*     */ import net.optifine.shaders.gui.GuiShaders;
/*     */ 
/*     */ public class GuiVideoSettings extends GuiScreenOF {
/*     */   private GuiScreen parentGuiScreen;
/*  23 */   protected String screenTitle = "Video Settings";
/*     */   
/*     */   private GameSettings guiGameSettings;
/*     */   
/*  27 */   private static GameSettings.Options[] videoOptions = new GameSettings.Options[] { GameSettings.Options.GRAPHICS, GameSettings.Options.RENDER_DISTANCE, GameSettings.Options.AMBIENT_OCCLUSION, GameSettings.Options.FRAMERATE_LIMIT, GameSettings.Options.AO_LEVEL, GameSettings.Options.VIEW_BOBBING, GameSettings.Options.GUI_SCALE, GameSettings.Options.USE_VBO, GameSettings.Options.GAMMA, GameSettings.Options.BLOCK_ALTERNATIVES, GameSettings.Options.DYNAMIC_LIGHTS, GameSettings.Options.DYNAMIC_FOV };
/*  28 */   private TooltipManager tooltipManager = new TooltipManager((GuiScreen)this, (TooltipProvider)new TooltipProviderOptions());
/*     */ 
/*     */   
/*     */   public GuiVideoSettings(GuiScreen parentScreenIn, GameSettings gameSettingsIn) {
/*  32 */     this.parentGuiScreen = parentScreenIn;
/*  33 */     this.guiGameSettings = gameSettingsIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  42 */     this.screenTitle = I18n.format("options.videoTitle", new Object[0]);
/*  43 */     this.buttonList.clear();
/*     */     
/*  45 */     for (int i = 0; i < videoOptions.length; i++) {
/*     */       
/*  47 */       GameSettings.Options gamesettings$options = videoOptions[i];
/*     */       
/*  49 */       if (gamesettings$options != null) {
/*     */         
/*  51 */         int j = this.width / 2 - 155 + i % 2 * 160;
/*  52 */         int k = this.height / 6 + 21 * i / 2 - 12;
/*     */         
/*  54 */         if (gamesettings$options.getEnumFloat()) {
/*     */           
/*  56 */           this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
/*     */         }
/*     */         else {
/*     */           
/*  60 */           this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.guiGameSettings.getKeyBinding(gamesettings$options)));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     int l = this.height / 6 + 21 * videoOptions.length / 2 - 12;
/*  66 */     int i1 = 0;
/*  67 */     i1 = this.width / 2 - 155 + 0;
/*  68 */     this.buttonList.add(new GuiOptionButton(231, i1, l, Lang.get("of.options.shaders")));
/*  69 */     i1 = this.width / 2 - 155 + 160;
/*  70 */     this.buttonList.add(new GuiOptionButton(202, i1, l, Lang.get("of.options.quality")));
/*  71 */     l += 21;
/*  72 */     i1 = this.width / 2 - 155 + 0;
/*  73 */     this.buttonList.add(new GuiOptionButton(201, i1, l, Lang.get("of.options.details")));
/*  74 */     i1 = this.width / 2 - 155 + 160;
/*  75 */     this.buttonList.add(new GuiOptionButton(212, i1, l, Lang.get("of.options.performance")));
/*  76 */     l += 21;
/*  77 */     i1 = this.width / 2 - 155 + 0;
/*  78 */     this.buttonList.add(new GuiOptionButton(211, i1, l, Lang.get("of.options.animations")));
/*  79 */     i1 = this.width / 2 - 155 + 160;
/*  80 */     this.buttonList.add(new GuiOptionButton(222, i1, l, Lang.get("of.options.other")));
/*  81 */     l += 21;
/*  82 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  90 */     actionPerformed(button, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton p_actionPerformedRightClick_1_) {
/*  95 */     if (p_actionPerformedRightClick_1_.id == GameSettings.Options.GUI_SCALE.ordinal())
/*     */     {
/*  97 */       actionPerformed(p_actionPerformedRightClick_1_, -1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void actionPerformed(GuiButton p_actionPerformed_1_, int p_actionPerformed_2_) {
/* 103 */     if (p_actionPerformed_1_.enabled) {
/*     */       
/* 105 */       int i = this.guiGameSettings.guiScale;
/*     */       
/* 107 */       if (p_actionPerformed_1_.id < 200 && p_actionPerformed_1_ instanceof GuiOptionButton) {
/*     */         
/* 109 */         this.guiGameSettings.setOptionValue(((GuiOptionButton)p_actionPerformed_1_).returnEnumOptions(), p_actionPerformed_2_);
/* 110 */         p_actionPerformed_1_.displayString = this.guiGameSettings.getKeyBinding(GameSettings.Options.getEnumOptions(p_actionPerformed_1_.id));
/*     */       } 
/*     */       
/* 113 */       if (p_actionPerformed_1_.id == 200) {
/*     */         
/* 115 */         this.mc.gameSettings.saveOptions();
/* 116 */         this.mc.displayGuiScreen(this.parentGuiScreen);
/*     */       } 
/*     */       
/* 119 */       if (this.guiGameSettings.guiScale != i) {
/*     */         
/* 121 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 122 */         int j = scaledresolution.getScaledWidth();
/* 123 */         int k = scaledresolution.getScaledHeight();
/* 124 */         setWorldAndResolution(this.mc, j, k);
/*     */       } 
/*     */       
/* 127 */       if (p_actionPerformed_1_.id == 201) {
/*     */         
/* 129 */         this.mc.gameSettings.saveOptions();
/* 130 */         GuiDetailSettingsOF guidetailsettingsof = new GuiDetailSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 131 */         this.mc.displayGuiScreen((GuiScreen)guidetailsettingsof);
/*     */       } 
/*     */       
/* 134 */       if (p_actionPerformed_1_.id == 202) {
/*     */         
/* 136 */         this.mc.gameSettings.saveOptions();
/* 137 */         GuiQualitySettingsOF guiqualitysettingsof = new GuiQualitySettingsOF((GuiScreen)this, this.guiGameSettings);
/* 138 */         this.mc.displayGuiScreen((GuiScreen)guiqualitysettingsof);
/*     */       } 
/*     */       
/* 141 */       if (p_actionPerformed_1_.id == 211) {
/*     */         
/* 143 */         this.mc.gameSettings.saveOptions();
/* 144 */         GuiAnimationSettingsOF guianimationsettingsof = new GuiAnimationSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 145 */         this.mc.displayGuiScreen((GuiScreen)guianimationsettingsof);
/*     */       } 
/*     */       
/* 148 */       if (p_actionPerformed_1_.id == 212) {
/*     */         
/* 150 */         this.mc.gameSettings.saveOptions();
/* 151 */         GuiPerformanceSettingsOF guiperformancesettingsof = new GuiPerformanceSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 152 */         this.mc.displayGuiScreen((GuiScreen)guiperformancesettingsof);
/*     */       } 
/*     */       
/* 155 */       if (p_actionPerformed_1_.id == 222) {
/*     */         
/* 157 */         this.mc.gameSettings.saveOptions();
/* 158 */         GuiOtherSettingsOF guiothersettingsof = new GuiOtherSettingsOF((GuiScreen)this, this.guiGameSettings);
/* 159 */         this.mc.displayGuiScreen((GuiScreen)guiothersettingsof);
/*     */       } 
/*     */       
/* 162 */       if (p_actionPerformed_1_.id == 231) {
/*     */         
/* 164 */         if (Config.isAntialiasing() || Config.isAntialiasingConfigured()) {
/*     */           
/* 166 */           Config.showGuiMessage(Lang.get("of.message.shaders.aa1"), Lang.get("of.message.shaders.aa2"));
/*     */           
/*     */           return;
/*     */         } 
/* 170 */         if (Config.isAnisotropicFiltering()) {
/*     */           
/* 172 */           Config.showGuiMessage(Lang.get("of.message.shaders.af1"), Lang.get("of.message.shaders.af2"));
/*     */           
/*     */           return;
/*     */         } 
/* 176 */         if (Config.isFastRender()) {
/*     */           
/* 178 */           Config.showGuiMessage(Lang.get("of.message.shaders.fr1"), Lang.get("of.message.shaders.fr2"));
/*     */           
/*     */           return;
/*     */         } 
/* 182 */         if ((Config.getGameSettings()).anaglyph) {
/*     */           
/* 184 */           Config.showGuiMessage(Lang.get("of.message.shaders.an1"), Lang.get("of.message.shaders.an2"));
/*     */           
/*     */           return;
/*     */         } 
/* 188 */         this.mc.gameSettings.saveOptions();
/* 189 */         GuiShaders guishaders = new GuiShaders((GuiScreen)this, this.guiGameSettings);
/* 190 */         this.mc.displayGuiScreen((GuiScreen)guishaders);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 200 */     drawDefaultBackground();
/* 201 */     drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 15, 16777215);
/* 202 */     String s = Config.getVersion();
/* 203 */     String s1 = "HD_U";
/*     */     
/* 205 */     if (s1.equals("HD"))
/*     */     {
/* 207 */       s = "OptiFine HD L5";
/*     */     }
/*     */     
/* 210 */     if (s1.equals("HD_U"))
/*     */     {
/* 212 */       s = "OptiFine HD L5 Ultra";
/*     */     }
/*     */     
/* 215 */     if (s1.equals("L"))
/*     */     {
/* 217 */       s = "OptiFine L5 Light";
/*     */     }
/*     */     
/* 220 */     drawString(this.fontRendererObj, s, 2, this.height - 10, 8421504);
/* 221 */     String s2 = "Minecraft 1.8.9";
/* 222 */     int i = this.fontRendererObj.getStringWidth(s2);
/* 223 */     drawString(this.fontRendererObj, s2, this.width - i - 2, this.height - 10, 8421504);
/* 224 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 225 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getButtonWidth(GuiButton p_getButtonWidth_0_) {
/* 230 */     return p_getButtonWidth_0_.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getButtonHeight(GuiButton p_getButtonHeight_0_) {
/* 235 */     return p_getButtonHeight_0_.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawGradientRect(GuiScreen p_drawGradientRect_0_, int p_drawGradientRect_1_, int p_drawGradientRect_2_, int p_drawGradientRect_3_, int p_drawGradientRect_4_, int p_drawGradientRect_5_, int p_drawGradientRect_6_) {
/* 240 */     p_drawGradientRect_0_.drawGradientRect(p_drawGradientRect_1_, p_drawGradientRect_2_, p_drawGradientRect_3_, p_drawGradientRect_4_, p_drawGradientRect_5_, p_drawGradientRect_6_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getGuiChatText(GuiChat p_getGuiChatText_0_) {
/* 245 */     return p_getGuiChatText_0_.inputField.getText();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiVideoSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */