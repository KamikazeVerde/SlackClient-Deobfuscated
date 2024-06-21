/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundCategory;
/*     */ import net.minecraft.client.audio.SoundEventAccessorComposite;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ 
/*     */ public class GuiOptions extends GuiScreen implements GuiYesNoCallback {
/*  17 */   private static final GameSettings.Options[] field_146440_f = new GameSettings.Options[] { GameSettings.Options.FOV };
/*     */   
/*     */   private final GuiScreen field_146441_g;
/*     */   
/*     */   private final GameSettings game_settings_1;
/*     */   private GuiButton field_175357_i;
/*     */   private GuiLockIconButton field_175356_r;
/*  24 */   protected String field_146442_a = "Options";
/*     */ 
/*     */   
/*     */   public GuiOptions(GuiScreen p_i1046_1_, GameSettings p_i1046_2_) {
/*  28 */     this.field_146441_g = p_i1046_1_;
/*  29 */     this.game_settings_1 = p_i1046_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  38 */     int i = 0;
/*  39 */     this.field_146442_a = I18n.format("options.title", new Object[0]);
/*     */     
/*  41 */     for (GameSettings.Options gamesettings$options : field_146440_f) {
/*     */       
/*  43 */       if (gamesettings$options.getEnumFloat()) {
/*     */         
/*  45 */         this.buttonList.add(new GuiOptionSlider(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options));
/*     */       }
/*     */       else {
/*     */         
/*  49 */         GuiOptionButton guioptionbutton = new GuiOptionButton(gamesettings$options.returnEnumOrdinal(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), gamesettings$options, this.game_settings_1.getKeyBinding(gamesettings$options));
/*  50 */         this.buttonList.add(guioptionbutton);
/*     */       } 
/*     */       
/*  53 */       i++;
/*     */     } 
/*     */     
/*  56 */     if (this.mc.theWorld != null) {
/*     */       
/*  58 */       EnumDifficulty enumdifficulty = this.mc.theWorld.getDifficulty();
/*  59 */       this.field_175357_i = new GuiButton(108, this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), 150, 20, func_175355_a(enumdifficulty));
/*  60 */       this.buttonList.add(this.field_175357_i);
/*     */       
/*  62 */       if (this.mc.isSingleplayer() && !this.mc.theWorld.getWorldInfo().isHardcoreModeEnabled()) {
/*     */         
/*  64 */         this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - 20);
/*  65 */         this.field_175356_r = new GuiLockIconButton(109, this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), this.field_175357_i.yPosition);
/*  66 */         this.buttonList.add(this.field_175356_r);
/*  67 */         this.field_175356_r.func_175229_b(this.mc.theWorld.getWorldInfo().isDifficultyLocked());
/*  68 */         this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
/*  69 */         this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
/*     */       }
/*     */       else {
/*     */         
/*  73 */         this.field_175357_i.enabled = false;
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     this.buttonList.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
/*  78 */     this.buttonList.add(new GuiButton(8675309, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "Super Secret Settings...")
/*     */         {
/*     */           public void playPressSound(SoundHandler soundHandlerIn)
/*     */           {
/*  82 */             SoundEventAccessorComposite soundeventaccessorcomposite = soundHandlerIn.getRandomSoundFromCategories(new SoundCategory[] { SoundCategory.ANIMALS, SoundCategory.BLOCKS, SoundCategory.MOBS, SoundCategory.PLAYERS, SoundCategory.WEATHER });
/*     */             
/*  84 */             if (soundeventaccessorcomposite != null)
/*     */             {
/*  86 */               soundHandlerIn.playSound((ISound)PositionedSoundRecord.create(soundeventaccessorcomposite.getSoundEventLocation(), 0.5F));
/*     */             }
/*     */           }
/*     */         });
/*  90 */     this.buttonList.add(new GuiButton(106, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
/*  91 */     this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
/*  92 */     this.buttonList.add(new GuiButton(100, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
/*  93 */     this.buttonList.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
/*  94 */     this.buttonList.add(new GuiButton(103, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.chat.title", new Object[0])));
/*  95 */     this.buttonList.add(new GuiButton(105, this.width / 2 - 155, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
/*  96 */     this.buttonList.add(new GuiButton(104, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
/*  97 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */   
/*     */   public String func_175355_a(EnumDifficulty p_175355_1_) {
/* 102 */     ChatComponentText chatComponentText = new ChatComponentText("");
/* 103 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation("options.difficulty", new Object[0]));
/* 104 */     chatComponentText.appendText(": ");
/* 105 */     chatComponentText.appendSibling((IChatComponent)new ChatComponentTranslation(p_175355_1_.getDifficultyResourceKey(), new Object[0]));
/* 106 */     return chatComponentText.getFormattedText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 111 */     this.mc.displayGuiScreen(this);
/*     */     
/* 113 */     if (id == 109 && result && this.mc.theWorld != null) {
/*     */       
/* 115 */       this.mc.theWorld.getWorldInfo().setDifficultyLocked(true);
/* 116 */       this.field_175356_r.func_175229_b(true);
/* 117 */       this.field_175356_r.enabled = false;
/* 118 */       this.field_175357_i.enabled = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 127 */     if (button.enabled) {
/*     */       
/* 129 */       if (button.id < 100 && button instanceof GuiOptionButton) {
/*     */         
/* 131 */         GameSettings.Options gamesettings$options = ((GuiOptionButton)button).returnEnumOptions();
/* 132 */         this.game_settings_1.setOptionValue(gamesettings$options, 1);
/* 133 */         button.displayString = this.game_settings_1.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
/*     */       } 
/*     */       
/* 136 */       if (button.id == 108) {
/*     */         
/* 138 */         this.mc.theWorld.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.theWorld.getDifficulty().getDifficultyId() + 1));
/* 139 */         this.field_175357_i.displayString = func_175355_a(this.mc.theWorld.getDifficulty());
/*     */       } 
/*     */       
/* 142 */       if (button.id == 109)
/*     */       {
/* 144 */         this.mc.displayGuiScreen(new GuiYesNo(this, (new ChatComponentTranslation("difficulty.lock.title", new Object[0])).getFormattedText(), (new ChatComponentTranslation("difficulty.lock.question", new Object[] { new ChatComponentTranslation(this.mc.theWorld.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0]) })).getFormattedText(), 109));
/*     */       }
/*     */       
/* 147 */       if (button.id == 110) {
/*     */         
/* 149 */         this.mc.gameSettings.saveOptions();
/* 150 */         this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
/*     */       } 
/*     */       
/* 153 */       if (button.id == 8675309)
/*     */       {
/* 155 */         this.mc.entityRenderer.activateNextShader();
/*     */       }
/*     */       
/* 158 */       if (button.id == 101) {
/*     */         
/* 160 */         this.mc.gameSettings.saveOptions();
/* 161 */         this.mc.displayGuiScreen((GuiScreen)new GuiVideoSettings(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 164 */       if (button.id == 100) {
/*     */         
/* 166 */         this.mc.gameSettings.saveOptions();
/* 167 */         this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 170 */       if (button.id == 102) {
/*     */         
/* 172 */         this.mc.gameSettings.saveOptions();
/* 173 */         this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.getLanguageManager()));
/*     */       } 
/*     */       
/* 176 */       if (button.id == 103) {
/*     */         
/* 178 */         this.mc.gameSettings.saveOptions();
/* 179 */         this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 182 */       if (button.id == 104) {
/*     */         
/* 184 */         this.mc.gameSettings.saveOptions();
/* 185 */         this.mc.displayGuiScreen(new GuiSnooper(this, this.game_settings_1));
/*     */       } 
/*     */       
/* 188 */       if (button.id == 200) {
/*     */         
/* 190 */         this.mc.gameSettings.saveOptions();
/* 191 */         this.mc.displayGuiScreen(this.field_146441_g);
/*     */       } 
/*     */       
/* 194 */       if (button.id == 105) {
/*     */         
/* 196 */         this.mc.gameSettings.saveOptions();
/* 197 */         this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
/*     */       } 
/*     */       
/* 200 */       if (button.id == 106) {
/*     */         
/* 202 */         this.mc.gameSettings.saveOptions();
/* 203 */         this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 213 */     drawDefaultBackground();
/* 214 */     drawCenteredString(this.fontRendererObj, this.field_146442_a, this.width / 2, 15, 16777215);
/* 215 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */