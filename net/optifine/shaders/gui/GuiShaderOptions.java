/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.GuiScreenOF;
/*     */ import net.optifine.gui.TooltipManager;
/*     */ import net.optifine.gui.TooltipProvider;
/*     */ import net.optifine.gui.TooltipProviderShaderOptions;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.config.ShaderOption;
/*     */ import net.optifine.shaders.config.ShaderOptionProfile;
/*     */ import net.optifine.shaders.config.ShaderOptionScreen;
/*     */ 
/*     */ public class GuiShaderOptions
/*     */   extends GuiScreenOF {
/*     */   private GuiScreen prevScreen;
/*     */   protected String title;
/*     */   private GameSettings settings;
/*     */   private TooltipManager tooltipManager;
/*     */   private String screenName;
/*     */   private String screenText;
/*     */   private boolean changed;
/*     */   public static final String OPTION_PROFILE = "<profile>";
/*     */   public static final String OPTION_EMPTY = "<empty>";
/*     */   public static final String OPTION_REST = "*";
/*     */   
/*     */   public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings) {
/*  34 */     this.tooltipManager = new TooltipManager((GuiScreen)this, (TooltipProvider)new TooltipProviderShaderOptions());
/*  35 */     this.screenName = null;
/*  36 */     this.screenText = null;
/*  37 */     this.changed = false;
/*  38 */     this.title = "Shader Options";
/*  39 */     this.prevScreen = guiscreen;
/*  40 */     this.settings = gamesettings;
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiShaderOptions(GuiScreen guiscreen, GameSettings gamesettings, String screenName) {
/*  45 */     this(guiscreen, gamesettings);
/*  46 */     this.screenName = screenName;
/*     */     
/*  48 */     if (screenName != null)
/*     */     {
/*  50 */       this.screenText = Shaders.translate("screen." + screenName, screenName);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  60 */     this.title = I18n.format("of.options.shaderOptionsTitle", new Object[0]);
/*  61 */     int i = 100;
/*  62 */     int j = 0;
/*  63 */     int k = 30;
/*  64 */     int l = 20;
/*  65 */     int i1 = 120;
/*  66 */     int j1 = 20;
/*  67 */     int k1 = Shaders.getShaderPackColumns(this.screenName, 2);
/*  68 */     ShaderOption[] ashaderoption = Shaders.getShaderPackOptions(this.screenName);
/*     */     
/*  70 */     if (ashaderoption != null) {
/*     */       
/*  72 */       int l1 = MathHelper.ceiling_double_int(ashaderoption.length / 9.0D);
/*     */       
/*  74 */       if (k1 < l1)
/*     */       {
/*  76 */         k1 = l1;
/*     */       }
/*     */       
/*  79 */       for (int i2 = 0; i2 < ashaderoption.length; i2++) {
/*     */         
/*  81 */         ShaderOption shaderoption = ashaderoption[i2];
/*     */         
/*  83 */         if (shaderoption != null && shaderoption.isVisible()) {
/*     */           GuiButtonShaderOption guibuttonshaderoption;
/*  85 */           int j2 = i2 % k1;
/*  86 */           int k2 = i2 / k1;
/*  87 */           int l2 = Math.min(this.width / k1, 200);
/*  88 */           j = (this.width - l2 * k1) / 2;
/*  89 */           int i3 = j2 * l2 + 5 + j;
/*  90 */           int j3 = k + k2 * l;
/*  91 */           int k3 = l2 - 10;
/*  92 */           String s = getButtonText(shaderoption, k3);
/*     */ 
/*     */           
/*  95 */           if (Shaders.isShaderPackOptionSlider(shaderoption.getName())) {
/*     */             
/*  97 */             guibuttonshaderoption = new GuiSliderShaderOption(i + i2, i3, j3, k3, j1, shaderoption, s);
/*     */           }
/*     */           else {
/*     */             
/* 101 */             guibuttonshaderoption = new GuiButtonShaderOption(i + i2, i3, j3, k3, j1, shaderoption, s);
/*     */           } 
/*     */           
/* 104 */           guibuttonshaderoption.enabled = shaderoption.isEnabled();
/* 105 */           this.buttonList.add(guibuttonshaderoption);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     this.buttonList.add(new GuiButton(201, this.width / 2 - i1 - 20, this.height / 6 + 168 + 11, i1, j1, I18n.format("controls.reset", new Object[0])));
/* 111 */     this.buttonList.add(new GuiButton(200, this.width / 2 + 20, this.height / 6 + 168 + 11, i1, j1, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getButtonText(ShaderOption so, int btnWidth) {
/* 116 */     String s = so.getNameText();
/*     */     
/* 118 */     if (so instanceof ShaderOptionScreen) {
/*     */       
/* 120 */       ShaderOptionScreen shaderoptionscreen = (ShaderOptionScreen)so;
/* 121 */       return s + "...";
/*     */     } 
/*     */ 
/*     */     
/* 125 */     FontRenderer fontrenderer = (Config.getMinecraft()).MCfontRenderer;
/*     */     
/* 127 */     for (int i = fontrenderer.getStringWidth(": " + Lang.getOff()) + 5; fontrenderer.getStringWidth(s) + i >= btnWidth && s.length() > 0; s = s.substring(0, s.length() - 1));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     String s1 = so.isChanged() ? so.getValueColor(so.getValue()) : "";
/* 133 */     String s2 = so.getValueText(so.getValue());
/* 134 */     return s + ": " + s1 + s2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton guibutton) {
/* 143 */     if (guibutton.enabled) {
/*     */       
/* 145 */       if (guibutton.id < 200 && guibutton instanceof GuiButtonShaderOption) {
/*     */         
/* 147 */         GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
/* 148 */         ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */         
/* 150 */         if (shaderoption instanceof ShaderOptionScreen) {
/*     */           
/* 152 */           String s = shaderoption.getName();
/* 153 */           GuiShaderOptions guishaderoptions = new GuiShaderOptions((GuiScreen)this, this.settings, s);
/* 154 */           this.mc.displayGuiScreen((GuiScreen)guishaderoptions);
/*     */           
/*     */           return;
/*     */         } 
/* 158 */         if (isShiftKeyDown()) {
/*     */           
/* 160 */           shaderoption.resetValue();
/*     */         }
/* 162 */         else if (guibuttonshaderoption.isSwitchable()) {
/*     */           
/* 164 */           shaderoption.nextValue();
/*     */         } 
/*     */         
/* 167 */         updateAllButtons();
/* 168 */         this.changed = true;
/*     */       } 
/*     */       
/* 171 */       if (guibutton.id == 201) {
/*     */         
/* 173 */         ShaderOption[] ashaderoption = Shaders.getChangedOptions(Shaders.getShaderPackOptions());
/*     */         
/* 175 */         for (int i = 0; i < ashaderoption.length; i++) {
/*     */           
/* 177 */           ShaderOption shaderoption1 = ashaderoption[i];
/* 178 */           shaderoption1.resetValue();
/* 179 */           this.changed = true;
/*     */         } 
/*     */         
/* 182 */         updateAllButtons();
/*     */       } 
/*     */       
/* 185 */       if (guibutton.id == 200) {
/*     */         
/* 187 */         if (this.changed) {
/*     */           
/* 189 */           Shaders.saveShaderPackOptions();
/* 190 */           this.changed = false;
/* 191 */           Shaders.uninit();
/*     */         } 
/*     */         
/* 194 */         this.mc.displayGuiScreen(this.prevScreen);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformedRightClick(GuiButton btn) {
/* 201 */     if (btn instanceof GuiButtonShaderOption) {
/*     */       
/* 203 */       GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)btn;
/* 204 */       ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */       
/* 206 */       if (isShiftKeyDown()) {
/*     */         
/* 208 */         shaderoption.resetValue();
/*     */       }
/* 210 */       else if (guibuttonshaderoption.isSwitchable()) {
/*     */         
/* 212 */         shaderoption.prevValue();
/*     */       } 
/*     */       
/* 215 */       updateAllButtons();
/* 216 */       this.changed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 225 */     super.onGuiClosed();
/*     */     
/* 227 */     if (this.changed) {
/*     */       
/* 229 */       Shaders.saveShaderPackOptions();
/* 230 */       this.changed = false;
/* 231 */       Shaders.uninit();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAllButtons() {
/* 237 */     for (GuiButton guibutton : this.buttonList) {
/*     */       
/* 239 */       if (guibutton instanceof GuiButtonShaderOption) {
/*     */         
/* 241 */         GuiButtonShaderOption guibuttonshaderoption = (GuiButtonShaderOption)guibutton;
/* 242 */         ShaderOption shaderoption = guibuttonshaderoption.getShaderOption();
/*     */         
/* 244 */         if (shaderoption instanceof ShaderOptionProfile) {
/*     */           
/* 246 */           ShaderOptionProfile shaderoptionprofile = (ShaderOptionProfile)shaderoption;
/* 247 */           shaderoptionprofile.updateProfile();
/*     */         } 
/*     */         
/* 250 */         guibuttonshaderoption.displayString = getButtonText(shaderoption, guibuttonshaderoption.getButtonWidth());
/* 251 */         guibuttonshaderoption.valueChanged();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int x, int y, float f) {
/* 261 */     drawDefaultBackground();
/*     */     
/* 263 */     if (this.screenText != null) {
/*     */       
/* 265 */       drawCenteredString(this.fontRendererObj, this.screenText, this.width / 2, 15, 16777215);
/*     */     }
/*     */     else {
/*     */       
/* 269 */       drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
/*     */     } 
/*     */     
/* 272 */     super.drawScreen(x, y, f);
/* 273 */     this.tooltipManager.drawTooltips(x, y, this.buttonList);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\gui\GuiShaderOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */