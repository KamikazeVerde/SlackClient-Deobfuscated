/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class GuiCreateWorld
/*     */   extends GuiScreen {
/*     */   private GuiScreen parentScreen;
/*     */   private GuiTextField field_146333_g;
/*     */   private GuiTextField field_146335_h;
/*     */   private String field_146336_i;
/*  20 */   private String gameMode = "survival";
/*     */   
/*     */   private String field_175300_s;
/*     */   
/*     */   private boolean field_146341_s = true;
/*     */   private boolean allowCheats;
/*     */   private boolean field_146339_u;
/*     */   private boolean field_146338_v;
/*     */   private boolean field_146337_w;
/*     */   private boolean field_146345_x;
/*     */   private boolean field_146344_y;
/*     */   private GuiButton btnGameMode;
/*     */   private GuiButton btnMoreOptions;
/*     */   private GuiButton btnMapFeatures;
/*     */   private GuiButton btnBonusItems;
/*     */   private GuiButton btnMapType;
/*     */   private GuiButton btnAllowCommands;
/*     */   private GuiButton btnCustomizeType;
/*     */   private String field_146323_G;
/*     */   private String field_146328_H;
/*     */   private String field_146329_I;
/*     */   private String field_146330_J;
/*     */   private int selectedIndex;
/*  43 */   public String chunkProviderSettingsJson = "";
/*     */ 
/*     */   
/*  46 */   private static final String[] disallowedFilenames = new String[] { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
/*     */ 
/*     */   
/*     */   public GuiCreateWorld(GuiScreen p_i46320_1_) {
/*  50 */     this.parentScreen = p_i46320_1_;
/*  51 */     this.field_146329_I = "";
/*  52 */     this.field_146330_J = I18n.format("selectWorld.newWorld", new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  60 */     this.field_146333_g.updateCursorCounter();
/*  61 */     this.field_146335_h.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  70 */     Keyboard.enableRepeatEvents(true);
/*  71 */     this.buttonList.clear();
/*  72 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  73 */     this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  74 */     this.buttonList.add(this.btnGameMode = new GuiButton(2, this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  75 */     this.buttonList.add(this.btnMoreOptions = new GuiButton(3, this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
/*  76 */     this.buttonList.add(this.btnMapFeatures = new GuiButton(4, this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
/*  77 */     this.btnMapFeatures.visible = false;
/*  78 */     this.buttonList.add(this.btnBonusItems = new GuiButton(7, this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
/*  79 */     this.btnBonusItems.visible = false;
/*  80 */     this.buttonList.add(this.btnMapType = new GuiButton(5, this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
/*  81 */     this.btnMapType.visible = false;
/*  82 */     this.buttonList.add(this.btnAllowCommands = new GuiButton(6, this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  83 */     this.btnAllowCommands.visible = false;
/*  84 */     this.buttonList.add(this.btnCustomizeType = new GuiButton(8, this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
/*  85 */     this.btnCustomizeType.visible = false;
/*  86 */     this.field_146333_g = new GuiTextField(9, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  87 */     this.field_146333_g.setFocused(true);
/*  88 */     this.field_146333_g.setText(this.field_146330_J);
/*  89 */     this.field_146335_h = new GuiTextField(10, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
/*  90 */     this.field_146335_h.setText(this.field_146329_I);
/*  91 */     func_146316_a(this.field_146344_y);
/*  92 */     func_146314_g();
/*  93 */     func_146319_h();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146314_g() {
/*  98 */     this.field_146336_i = this.field_146333_g.getText().trim();
/*     */     
/* 100 */     for (char c0 : ChatAllowedCharacters.allowedCharactersArray)
/*     */     {
/* 102 */       this.field_146336_i = this.field_146336_i.replace(c0, '_');
/*     */     }
/*     */     
/* 105 */     if (StringUtils.isEmpty(this.field_146336_i))
/*     */     {
/* 107 */       this.field_146336_i = "World";
/*     */     }
/*     */     
/* 110 */     this.field_146336_i = func_146317_a(this.mc.getSaveLoader(), this.field_146336_i);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146319_h() {
/* 115 */     this.btnGameMode.displayString = I18n.format("selectWorld.gameMode", new Object[0]) + ": " + I18n.format("selectWorld.gameMode." + this.gameMode, new Object[0]);
/* 116 */     this.field_146323_G = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1", new Object[0]);
/* 117 */     this.field_146328_H = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2", new Object[0]);
/* 118 */     this.btnMapFeatures.displayString = I18n.format("selectWorld.mapFeatures", new Object[0]) + " ";
/*     */     
/* 120 */     if (this.field_146341_s) {
/*     */       
/* 122 */       this.btnMapFeatures.displayString += I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 126 */       this.btnMapFeatures.displayString += I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 129 */     this.btnBonusItems.displayString = I18n.format("selectWorld.bonusItems", new Object[0]) + " ";
/*     */     
/* 131 */     if (this.field_146338_v && !this.field_146337_w) {
/*     */       
/* 133 */       this.btnBonusItems.displayString += I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 137 */       this.btnBonusItems.displayString += I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */     
/* 140 */     this.btnMapType.displayString = I18n.format("selectWorld.mapType", new Object[0]) + " " + I18n.format(WorldType.worldTypes[this.selectedIndex].getTranslateName(), new Object[0]);
/* 141 */     this.btnAllowCommands.displayString = I18n.format("selectWorld.allowCommands", new Object[0]) + " ";
/*     */     
/* 143 */     if (this.allowCheats && !this.field_146337_w) {
/*     */       
/* 145 */       this.btnAllowCommands.displayString += I18n.format("options.on", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 149 */       this.btnAllowCommands.displayString += I18n.format("options.off", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String func_146317_a(ISaveFormat p_146317_0_, String p_146317_1_) {
/* 155 */     p_146317_1_ = p_146317_1_.replaceAll("[\\./\"]", "_");
/*     */     
/* 157 */     for (String s : disallowedFilenames) {
/*     */       
/* 159 */       if (p_146317_1_.equalsIgnoreCase(s))
/*     */       {
/* 161 */         p_146317_1_ = "_" + p_146317_1_ + "_";
/*     */       }
/*     */     } 
/*     */     
/* 165 */     while (p_146317_0_.getWorldInfo(p_146317_1_) != null)
/*     */     {
/* 167 */       p_146317_1_ = p_146317_1_ + "-";
/*     */     }
/*     */     
/* 170 */     return p_146317_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 178 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 186 */     if (button.enabled) {
/*     */       long i; String s; WorldSettings.GameType worldsettings$gametype; WorldSettings worldsettings;
/* 188 */       switch (button.id) {
/*     */         case 1:
/* 190 */           this.mc.displayGuiScreen(this.parentScreen);
/*     */           break;
/*     */         case 0:
/* 193 */           this.mc.displayGuiScreen(null);
/*     */           
/* 195 */           if (this.field_146345_x) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 200 */           this.field_146345_x = true;
/* 201 */           i = (new Random()).nextLong();
/* 202 */           s = this.field_146335_h.getText();
/*     */           
/* 204 */           if (!StringUtils.isEmpty(s)) {
/*     */             
/*     */             try {
/*     */               
/* 208 */               long j = Long.parseLong(s);
/*     */               
/* 210 */               if (j != 0L)
/*     */               {
/* 212 */                 i = j;
/*     */               }
/*     */             }
/* 215 */             catch (NumberFormatException var7) {
/*     */               
/* 217 */               i = s.hashCode();
/*     */             } 
/*     */           }
/*     */           
/* 221 */           worldsettings$gametype = WorldSettings.GameType.getByName(this.gameMode);
/* 222 */           worldsettings = new WorldSettings(i, worldsettings$gametype, this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.selectedIndex]);
/* 223 */           worldsettings.setWorldName(this.chunkProviderSettingsJson);
/*     */           
/* 225 */           if (this.field_146338_v && !this.field_146337_w)
/*     */           {
/* 227 */             worldsettings.enableBonusChest();
/*     */           }
/*     */           
/* 230 */           if (this.allowCheats && !this.field_146337_w)
/*     */           {
/* 232 */             worldsettings.enableCommands();
/*     */           }
/*     */           
/* 235 */           this.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), worldsettings);
/*     */           break;
/*     */         case 3:
/* 238 */           func_146315_i();
/*     */           break;
/*     */         case 2:
/* 241 */           if (this.gameMode.equals("survival")) {
/*     */             
/* 243 */             if (!this.field_146339_u)
/*     */             {
/* 245 */               this.allowCheats = false;
/*     */             }
/*     */             
/* 248 */             this.field_146337_w = false;
/* 249 */             this.gameMode = "hardcore";
/* 250 */             this.field_146337_w = true;
/* 251 */             this.btnAllowCommands.enabled = false;
/* 252 */             this.btnBonusItems.enabled = false;
/* 253 */             func_146319_h();
/*     */           }
/* 255 */           else if (this.gameMode.equals("hardcore")) {
/*     */             
/* 257 */             if (!this.field_146339_u)
/*     */             {
/* 259 */               this.allowCheats = true;
/*     */             }
/*     */             
/* 262 */             this.field_146337_w = false;
/* 263 */             this.gameMode = "creative";
/* 264 */             func_146319_h();
/* 265 */             this.field_146337_w = false;
/* 266 */             this.btnAllowCommands.enabled = true;
/* 267 */             this.btnBonusItems.enabled = true;
/*     */           }
/*     */           else {
/*     */             
/* 271 */             if (!this.field_146339_u)
/*     */             {
/* 273 */               this.allowCheats = false;
/*     */             }
/*     */             
/* 276 */             this.gameMode = "survival";
/* 277 */             func_146319_h();
/* 278 */             this.btnAllowCommands.enabled = true;
/* 279 */             this.btnBonusItems.enabled = true;
/* 280 */             this.field_146337_w = false;
/*     */           } 
/*     */           
/* 283 */           func_146319_h();
/*     */           break;
/*     */         case 4:
/* 286 */           this.field_146341_s = !this.field_146341_s;
/* 287 */           func_146319_h();
/*     */           break;
/*     */         case 7:
/* 290 */           this.field_146338_v = !this.field_146338_v;
/* 291 */           func_146319_h();
/*     */           break;
/*     */         case 5:
/* 294 */           this.selectedIndex++;
/*     */           
/* 296 */           if (this.selectedIndex >= WorldType.worldTypes.length)
/*     */           {
/* 298 */             this.selectedIndex = 0;
/*     */           }
/*     */           
/* 301 */           while (!func_175299_g()) {
/*     */             
/* 303 */             this.selectedIndex++;
/*     */             
/* 305 */             if (this.selectedIndex >= WorldType.worldTypes.length)
/*     */             {
/* 307 */               this.selectedIndex = 0;
/*     */             }
/*     */           } 
/*     */           
/* 311 */           this.chunkProviderSettingsJson = "";
/* 312 */           func_146319_h();
/* 313 */           func_146316_a(this.field_146344_y);
/*     */           break;
/*     */         case 6:
/* 316 */           this.field_146339_u = true;
/* 317 */           this.allowCheats = !this.allowCheats;
/* 318 */           func_146319_h();
/*     */           break;
/*     */         case 8:
/* 321 */           if (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT) {
/*     */             
/* 323 */             this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.chunkProviderSettingsJson));
/*     */             
/*     */             break;
/*     */           } 
/* 327 */           this.mc.displayGuiScreen(new GuiCustomizeWorldScreen(this, this.chunkProviderSettingsJson));
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean func_175299_g() {
/* 336 */     WorldType worldtype = WorldType.worldTypes[this.selectedIndex];
/* 337 */     return (worldtype != null && worldtype.getCanBeCreated()) ? ((worldtype == WorldType.DEBUG_WORLD) ? isShiftKeyDown() : true) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146315_i() {
/* 342 */     func_146316_a(!this.field_146344_y);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146316_a(boolean p_146316_1_) {
/* 347 */     this.field_146344_y = p_146316_1_;
/*     */     
/* 349 */     if (WorldType.worldTypes[this.selectedIndex] == WorldType.DEBUG_WORLD) {
/*     */       
/* 351 */       this.btnGameMode.visible = !this.field_146344_y;
/* 352 */       this.btnGameMode.enabled = false;
/*     */       
/* 354 */       if (this.field_175300_s == null)
/*     */       {
/* 356 */         this.field_175300_s = this.gameMode;
/*     */       }
/*     */       
/* 359 */       this.gameMode = "spectator";
/* 360 */       this.btnMapFeatures.visible = false;
/* 361 */       this.btnBonusItems.visible = false;
/* 362 */       this.btnMapType.visible = this.field_146344_y;
/* 363 */       this.btnAllowCommands.visible = false;
/* 364 */       this.btnCustomizeType.visible = false;
/*     */     }
/*     */     else {
/*     */       
/* 368 */       this.btnGameMode.visible = !this.field_146344_y;
/* 369 */       this.btnGameMode.enabled = true;
/*     */       
/* 371 */       if (this.field_175300_s != null) {
/*     */         
/* 373 */         this.gameMode = this.field_175300_s;
/* 374 */         this.field_175300_s = null;
/*     */       } 
/*     */       
/* 377 */       this.btnMapFeatures.visible = (this.field_146344_y && WorldType.worldTypes[this.selectedIndex] != WorldType.CUSTOMIZED);
/* 378 */       this.btnBonusItems.visible = this.field_146344_y;
/* 379 */       this.btnMapType.visible = this.field_146344_y;
/* 380 */       this.btnAllowCommands.visible = this.field_146344_y;
/* 381 */       this.btnCustomizeType.visible = (this.field_146344_y && (WorldType.worldTypes[this.selectedIndex] == WorldType.FLAT || WorldType.worldTypes[this.selectedIndex] == WorldType.CUSTOMIZED));
/*     */     } 
/*     */     
/* 384 */     func_146319_h();
/*     */     
/* 386 */     if (this.field_146344_y) {
/*     */       
/* 388 */       this.btnMoreOptions.displayString = I18n.format("gui.done", new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 392 */       this.btnMoreOptions.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 402 */     if (this.field_146333_g.isFocused() && !this.field_146344_y) {
/*     */       
/* 404 */       this.field_146333_g.textboxKeyTyped(typedChar, keyCode);
/* 405 */       this.field_146330_J = this.field_146333_g.getText();
/*     */     }
/* 407 */     else if (this.field_146335_h.isFocused() && this.field_146344_y) {
/*     */       
/* 409 */       this.field_146335_h.textboxKeyTyped(typedChar, keyCode);
/* 410 */       this.field_146329_I = this.field_146335_h.getText();
/*     */     } 
/*     */     
/* 413 */     if (keyCode == 28 || keyCode == 156)
/*     */     {
/* 415 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*     */     
/* 418 */     ((GuiButton)this.buttonList.get(0)).enabled = (this.field_146333_g.getText().length() > 0);
/* 419 */     func_146314_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 427 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 429 */     if (this.field_146344_y) {
/*     */       
/* 431 */       this.field_146335_h.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     }
/*     */     else {
/*     */       
/* 435 */       this.field_146333_g.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 444 */     drawDefaultBackground();
/* 445 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), this.width / 2, 20, -1);
/*     */     
/* 447 */     if (this.field_146344_y) {
/*     */       
/* 449 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 450 */       drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), this.width / 2 - 100, 85, -6250336);
/*     */       
/* 452 */       if (this.btnMapFeatures.visible)
/*     */       {
/* 454 */         drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), this.width / 2 - 150, 122, -6250336);
/*     */       }
/*     */       
/* 457 */       if (this.btnAllowCommands.visible)
/*     */       {
/* 459 */         drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), this.width / 2 - 150, 172, -6250336);
/*     */       }
/*     */       
/* 462 */       this.field_146335_h.drawTextBox();
/*     */       
/* 464 */       if (WorldType.worldTypes[this.selectedIndex].showWorldInfoNotice())
/*     */       {
/* 466 */         this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.selectedIndex].func_151359_c(), new Object[0]), this.btnMapType.xPosition + 2, this.btnMapType.yPosition + 22, this.btnMapType.getButtonWidth(), 10526880);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 471 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, -6250336);
/* 472 */       drawString(this.fontRendererObj, I18n.format("selectWorld.resultFolder", new Object[0]) + " " + this.field_146336_i, this.width / 2 - 100, 85, -6250336);
/* 473 */       this.field_146333_g.drawTextBox();
/* 474 */       drawString(this.fontRendererObj, this.field_146323_G, this.width / 2 - 100, 137, -6250336);
/* 475 */       drawString(this.fontRendererObj, this.field_146328_H, this.width / 2 - 100, 149, -6250336);
/*     */     } 
/*     */     
/* 478 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146318_a(WorldInfo p_146318_1_) {
/* 483 */     this.field_146330_J = I18n.format("selectWorld.newWorld.copyOf", new Object[] { p_146318_1_.getWorldName() });
/* 484 */     this.field_146329_I = p_146318_1_.getSeed() + "";
/* 485 */     this.selectedIndex = p_146318_1_.getTerrainType().getWorldTypeID();
/* 486 */     this.chunkProviderSettingsJson = p_146318_1_.getGeneratorOptions();
/* 487 */     this.field_146341_s = p_146318_1_.isMapFeaturesEnabled();
/* 488 */     this.allowCheats = p_146318_1_.areCommandsAllowed();
/*     */     
/* 490 */     if (p_146318_1_.isHardcoreModeEnabled()) {
/*     */       
/* 492 */       this.gameMode = "hardcore";
/*     */     }
/* 494 */     else if (p_146318_1_.getGameType().isSurvivalOrAdventure()) {
/*     */       
/* 496 */       this.gameMode = "survival";
/*     */     }
/* 498 */     else if (p_146318_1_.getGameType().isCreative()) {
/*     */       
/* 500 */       this.gameMode = "creative";
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiCreateWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */