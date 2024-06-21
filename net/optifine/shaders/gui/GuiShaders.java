/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.gui.TooltipManager;
/*     */ import net.optifine.gui.TooltipProvider;
/*     */ import net.optifine.gui.TooltipProviderEnumShaderOptions;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.ShadersTex;
/*     */ import net.optifine.shaders.config.EnumShaderOption;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiShaders extends GuiScreen {
/*     */   protected GuiScreen parentGui;
/*  23 */   protected String screenTitle = "Shaders";
/*  24 */   private TooltipManager tooltipManager = new TooltipManager(this, (TooltipProvider)new TooltipProviderEnumShaderOptions());
/*  25 */   private int updateTimer = -1;
/*     */   private GuiSlotShaders shaderList;
/*     */   private boolean saved = false;
/*  28 */   private static float[] QUALITY_MULTIPLIERS = new float[] { 0.5F, 0.70710677F, 1.0F, 1.4142135F, 2.0F };
/*  29 */   private static String[] QUALITY_MULTIPLIER_NAMES = new String[] { "0.5x", "0.7x", "1x", "1.5x", "2x" };
/*  30 */   private static float[] HAND_DEPTH_VALUES = new float[] { 0.0625F, 0.125F, 0.25F };
/*  31 */   private static String[] HAND_DEPTH_NAMES = new String[] { "0.5x", "1x", "2x" };
/*     */   
/*     */   public static final int EnumOS_UNKNOWN = 0;
/*     */   public static final int EnumOS_WINDOWS = 1;
/*     */   public static final int EnumOS_OSX = 2;
/*     */   public static final int EnumOS_SOLARIS = 3;
/*     */   public static final int EnumOS_LINUX = 4;
/*     */   
/*     */   public GuiShaders(GuiScreen par1GuiScreen, GameSettings par2GameSettings) {
/*  40 */     this.parentGui = par1GuiScreen;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  49 */     this.screenTitle = I18n.format("of.options.shadersTitle", new Object[0]);
/*     */     
/*  51 */     if (Shaders.shadersConfig == null)
/*     */     {
/*  53 */       Shaders.loadConfig();
/*     */     }
/*     */     
/*  56 */     int i = 120;
/*  57 */     int j = 20;
/*  58 */     int k = this.width - i - 10;
/*  59 */     int l = 30;
/*  60 */     int i1 = 20;
/*  61 */     int j1 = this.width - i - 20;
/*  62 */     this.shaderList = new GuiSlotShaders(this, j1, this.height, l, this.height - 50, 16);
/*  63 */     this.shaderList.registerScrollButtons(7, 8);
/*  64 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.ANTIALIASING, k, 0 * i1 + l, i, j));
/*  65 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.NORMAL_MAP, k, 1 * i1 + l, i, j));
/*  66 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SPECULAR_MAP, k, 2 * i1 + l, i, j));
/*  67 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.RENDER_RES_MUL, k, 3 * i1 + l, i, j));
/*  68 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.SHADOW_RES_MUL, k, 4 * i1 + l, i, j));
/*  69 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.HAND_DEPTH_MUL, k, 5 * i1 + l, i, j));
/*  70 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_HAND_LIGHT, k, 6 * i1 + l, i, j));
/*  71 */     this.buttonList.add(new GuiButtonEnumShaderOption(EnumShaderOption.OLD_LIGHTING, k, 7 * i1 + l, i, j));
/*  72 */     int k1 = Math.min(150, j1 / 2 - 10);
/*  73 */     int l1 = j1 / 4 - k1 / 2;
/*  74 */     int i2 = this.height - 25;
/*  75 */     this.buttonList.add(new GuiButton(201, l1, i2, k1 - 22 + 1, j, Lang.get("of.options.shaders.shadersFolder")));
/*  76 */     this.buttonList.add(new GuiButtonDownloadShaders(210, l1 + k1 - 22 - 1, i2));
/*  77 */     this.buttonList.add(new GuiButton(202, j1 / 4 * 3 - k1 / 2, this.height - 25, k1, j, I18n.format("gui.done", new Object[0])));
/*  78 */     this.buttonList.add(new GuiButton(203, k, this.height - 25, i, j, Lang.get("of.options.shaders.shaderOptions")));
/*  79 */     updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateButtons() {
/*  84 */     boolean flag = Config.isShaders();
/*     */     
/*  86 */     for (GuiButton guibutton : this.buttonList) {
/*     */       
/*  88 */       if (guibutton.id != 201 && guibutton.id != 202 && guibutton.id != 210 && guibutton.id != EnumShaderOption.ANTIALIASING.ordinal())
/*     */       {
/*  90 */         guibutton.enabled = flag;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 100 */     super.handleMouseInput();
/* 101 */     this.shaderList.handleMouseInput();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 109 */     if (button.enabled)
/*     */     {
/* 111 */       if (button instanceof GuiButtonEnumShaderOption) {
/*     */         float f2, afloat2[]; String[] astring2; int k; float f1, afloat1[]; String[] astring1; int j; float f, afloat[]; String[] astring; int i;
/* 113 */         GuiButtonEnumShaderOption guibuttonenumshaderoption = (GuiButtonEnumShaderOption)button;
/*     */         
/* 115 */         switch (guibuttonenumshaderoption.getEnumShaderOption()) {
/*     */           
/*     */           case ANTIALIASING:
/* 118 */             Shaders.nextAntialiasingLevel();
/* 119 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case NORMAL_MAP:
/* 123 */             Shaders.configNormalMap = !Shaders.configNormalMap;
/* 124 */             Shaders.uninit();
/* 125 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case SPECULAR_MAP:
/* 129 */             Shaders.configSpecularMap = !Shaders.configSpecularMap;
/* 130 */             Shaders.uninit();
/* 131 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case RENDER_RES_MUL:
/* 135 */             f2 = Shaders.configRenderResMul;
/* 136 */             afloat2 = QUALITY_MULTIPLIERS;
/* 137 */             astring2 = QUALITY_MULTIPLIER_NAMES;
/* 138 */             k = getValueIndex(f2, afloat2);
/*     */             
/* 140 */             if (isShiftKeyDown()) {
/*     */               
/* 142 */               k--;
/*     */               
/* 144 */               if (k < 0)
/*     */               {
/* 146 */                 k = afloat2.length - 1;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 151 */               k++;
/*     */               
/* 153 */               if (k >= afloat2.length)
/*     */               {
/* 155 */                 k = 0;
/*     */               }
/*     */             } 
/*     */             
/* 159 */             Shaders.configRenderResMul = afloat2[k];
/* 160 */             Shaders.uninit();
/* 161 */             Shaders.scheduleResize();
/*     */             break;
/*     */           
/*     */           case SHADOW_RES_MUL:
/* 165 */             f1 = Shaders.configShadowResMul;
/* 166 */             afloat1 = QUALITY_MULTIPLIERS;
/* 167 */             astring1 = QUALITY_MULTIPLIER_NAMES;
/* 168 */             j = getValueIndex(f1, afloat1);
/*     */             
/* 170 */             if (isShiftKeyDown()) {
/*     */               
/* 172 */               j--;
/*     */               
/* 174 */               if (j < 0)
/*     */               {
/* 176 */                 j = afloat1.length - 1;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 181 */               j++;
/*     */               
/* 183 */               if (j >= afloat1.length)
/*     */               {
/* 185 */                 j = 0;
/*     */               }
/*     */             } 
/*     */             
/* 189 */             Shaders.configShadowResMul = afloat1[j];
/* 190 */             Shaders.uninit();
/* 191 */             Shaders.scheduleResizeShadow();
/*     */             break;
/*     */           
/*     */           case HAND_DEPTH_MUL:
/* 195 */             f = Shaders.configHandDepthMul;
/* 196 */             afloat = HAND_DEPTH_VALUES;
/* 197 */             astring = HAND_DEPTH_NAMES;
/* 198 */             i = getValueIndex(f, afloat);
/*     */             
/* 200 */             if (isShiftKeyDown()) {
/*     */               
/* 202 */               i--;
/*     */               
/* 204 */               if (i < 0)
/*     */               {
/* 206 */                 i = afloat.length - 1;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/* 211 */               i++;
/*     */               
/* 213 */               if (i >= afloat.length)
/*     */               {
/* 215 */                 i = 0;
/*     */               }
/*     */             } 
/*     */             
/* 219 */             Shaders.configHandDepthMul = afloat[i];
/* 220 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_HAND_LIGHT:
/* 224 */             Shaders.configOldHandLight.nextValue();
/* 225 */             Shaders.uninit();
/*     */             break;
/*     */           
/*     */           case OLD_LIGHTING:
/* 229 */             Shaders.configOldLighting.nextValue();
/* 230 */             Shaders.updateBlockLightLevel();
/* 231 */             Shaders.uninit();
/* 232 */             this.mc.scheduleResourcesRefresh();
/*     */             break;
/*     */           
/*     */           case TWEAK_BLOCK_DAMAGE:
/* 236 */             Shaders.configTweakBlockDamage = !Shaders.configTweakBlockDamage;
/*     */             break;
/*     */           
/*     */           case CLOUD_SHADOW:
/* 240 */             Shaders.configCloudShadow = !Shaders.configCloudShadow;
/*     */             break;
/*     */           
/*     */           case TEX_MIN_FIL_B:
/* 244 */             Shaders.configTexMinFilB = (Shaders.configTexMinFilB + 1) % 3;
/* 245 */             Shaders.configTexMinFilN = Shaders.configTexMinFilS = Shaders.configTexMinFilB;
/* 246 */             button.displayString = "Tex Min: " + Shaders.texMinFilDesc[Shaders.configTexMinFilB];
/* 247 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_N:
/* 251 */             Shaders.configTexMagFilN = (Shaders.configTexMagFilN + 1) % 2;
/* 252 */             button.displayString = "Tex_n Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilN];
/* 253 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case TEX_MAG_FIL_S:
/* 257 */             Shaders.configTexMagFilS = (Shaders.configTexMagFilS + 1) % 2;
/* 258 */             button.displayString = "Tex_s Mag: " + Shaders.texMagFilDesc[Shaders.configTexMagFilS];
/* 259 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */           
/*     */           case SHADOW_CLIP_FRUSTRUM:
/* 263 */             Shaders.configShadowClipFrustrum = !Shaders.configShadowClipFrustrum;
/* 264 */             button.displayString = "ShadowClipFrustrum: " + toStringOnOff(Shaders.configShadowClipFrustrum);
/* 265 */             ShadersTex.updateTextureMinMagFilter();
/*     */             break;
/*     */         } 
/* 268 */         guibuttonenumshaderoption.updateButtonText();
/*     */       } else {
/*     */         String s; boolean flag;
/*     */         GuiShaderOptions guishaderoptions;
/* 272 */         switch (button.id) {
/*     */           
/*     */           case 201:
/* 275 */             switch (getOSType()) {
/*     */               
/*     */               case 1:
/* 278 */                 s = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { Shaders.shaderPacksDir.getAbsolutePath() });
/*     */ 
/*     */                 
/*     */                 try {
/* 282 */                   Runtime.getRuntime().exec(s);
/*     */                   
/*     */                   return;
/* 285 */                 } catch (IOException ioexception) {
/*     */                   
/* 287 */                   ioexception.printStackTrace();
/*     */                   break;
/*     */                 } 
/*     */ 
/*     */               
/*     */               case 2:
/*     */                 try {
/* 294 */                   Runtime.getRuntime().exec(new String[] { "/usr/bin/open", Shaders.shaderPacksDir.getAbsolutePath() });
/*     */                   
/*     */                   return;
/* 297 */                 } catch (IOException ioexception1) {
/*     */                   
/* 299 */                   ioexception1.printStackTrace();
/*     */                   break;
/*     */                 } 
/*     */             } 
/* 303 */             flag = false;
/*     */ 
/*     */             
/*     */             try {
/* 307 */               Class<?> oclass1 = Class.forName("java.awt.Desktop");
/* 308 */               Object object1 = oclass1.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 309 */               oclass1.getMethod("browse", new Class[] { URI.class }).invoke(object1, new Object[] { (new File(this.mc.mcDataDir, "shaderpacks")).toURI() });
/*     */             }
/* 311 */             catch (Throwable throwable1) {
/*     */               
/* 313 */               throwable1.printStackTrace();
/* 314 */               flag = true;
/*     */             } 
/*     */             
/* 317 */             if (flag) {
/*     */               
/* 319 */               Config.dbg("Opening via system class!");
/* 320 */               Sys.openURL("file://" + Shaders.shaderPacksDir.getAbsolutePath());
/*     */             } 
/*     */             return;
/*     */ 
/*     */           
/*     */           case 202:
/* 326 */             Shaders.storeConfig();
/* 327 */             this.saved = true;
/* 328 */             this.mc.displayGuiScreen(this.parentGui);
/*     */             return;
/*     */           
/*     */           case 203:
/* 332 */             guishaderoptions = new GuiShaderOptions(this, Config.getGameSettings());
/* 333 */             Config.getMinecraft().displayGuiScreen((GuiScreen)guishaderoptions);
/*     */             return;
/*     */ 
/*     */           
/*     */           case 210:
/*     */             try {
/* 339 */               Class<?> oclass = Class.forName("java.awt.Desktop");
/* 340 */               Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 341 */               oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI("http://optifine.net/shaderPacks") });
/*     */             }
/* 343 */             catch (Throwable throwable) {
/*     */               
/* 345 */               throwable.printStackTrace();
/*     */             } 
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 355 */         this.shaderList.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 366 */     super.onGuiClosed();
/*     */     
/* 368 */     if (!this.saved)
/*     */     {
/* 370 */       Shaders.storeConfig();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 379 */     drawDefaultBackground();
/* 380 */     this.shaderList.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 382 */     if (this.updateTimer <= 0) {
/*     */       
/* 384 */       this.shaderList.updateList();
/* 385 */       this.updateTimer += 20;
/*     */     } 
/*     */     
/* 388 */     drawCenteredString(this.fontRendererObj, this.screenTitle + " ", this.width / 2, 15, 16777215);
/* 389 */     String s = "OpenGL: " + Shaders.glVersionString + ", " + Shaders.glVendorString + ", " + Shaders.glRendererString;
/* 390 */     int i = this.fontRendererObj.getStringWidth(s);
/*     */     
/* 392 */     if (i < this.width - 5) {
/*     */       
/* 394 */       drawCenteredString(this.fontRendererObj, s, this.width / 2, this.height - 40, 8421504);
/*     */     }
/*     */     else {
/*     */       
/* 398 */       drawString(this.fontRendererObj, s, 5, this.height - 40, 8421504);
/*     */     } 
/*     */     
/* 401 */     super.drawScreen(mouseX, mouseY, partialTicks);
/* 402 */     this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 410 */     super.updateScreen();
/* 411 */     this.updateTimer--;
/*     */   }
/*     */ 
/*     */   
/*     */   public Minecraft getMc() {
/* 416 */     return this.mc;
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawCenteredString(String text, int x, int y, int color) {
/* 421 */     drawCenteredString(this.fontRendererObj, text, x, y, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringOnOff(boolean value) {
/* 426 */     String s = Lang.getOn();
/* 427 */     String s1 = Lang.getOff();
/* 428 */     return value ? s : s1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringAa(int value) {
/* 433 */     return (value == 2) ? "FXAA 2x" : ((value == 4) ? "FXAA 4x" : Lang.getOff());
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringValue(float val, float[] values, String[] names) {
/* 438 */     int i = getValueIndex(val, values);
/* 439 */     return names[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getValueIndex(float val, float[] values) {
/* 444 */     for (int i = 0; i < values.length; i++) {
/*     */       
/* 446 */       float f = values[i];
/*     */       
/* 448 */       if (f >= val)
/*     */       {
/* 450 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 454 */     return values.length - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringQuality(float val) {
/* 459 */     return toStringValue(val, QUALITY_MULTIPLIERS, QUALITY_MULTIPLIER_NAMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String toStringHandDepth(float val) {
/* 464 */     return toStringValue(val, HAND_DEPTH_VALUES, HAND_DEPTH_NAMES);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getOSType() {
/* 469 */     String s = System.getProperty("os.name").toLowerCase();
/* 470 */     return s.contains("win") ? 1 : (s.contains("mac") ? 2 : (s.contains("solaris") ? 3 : (s.contains("sunos") ? 3 : (s.contains("linux") ? 4 : (s.contains("unix") ? 4 : 0)))));
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\gui\GuiShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */