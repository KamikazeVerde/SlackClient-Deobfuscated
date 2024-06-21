/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.storage.ISaveFormat;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatComparator;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GuiSelectWorld
/*     */   extends GuiScreen implements GuiYesNoCallback {
/*  23 */   private static final Logger logger = LogManager.getLogger();
/*  24 */   private final DateFormat field_146633_h = new SimpleDateFormat();
/*     */   protected GuiScreen parentScreen;
/*  26 */   protected String field_146628_f = "Select world";
/*     */   private boolean field_146634_i;
/*     */   private int field_146640_r;
/*     */   private java.util.List<SaveFormatComparator> field_146639_s;
/*     */   private List field_146638_t;
/*     */   private String field_146637_u;
/*     */   private String field_146636_v;
/*  33 */   private String[] field_146635_w = new String[4];
/*     */   
/*     */   private boolean field_146643_x;
/*     */   private GuiButton deleteButton;
/*     */   private GuiButton selectButton;
/*     */   private GuiButton renameButton;
/*     */   private GuiButton recreateButton;
/*     */   
/*     */   public GuiSelectWorld(GuiScreen parentScreenIn) {
/*  42 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  51 */     this.field_146628_f = I18n.format("selectWorld.title", new Object[0]);
/*     */ 
/*     */     
/*     */     try {
/*  55 */       func_146627_h();
/*     */     }
/*  57 */     catch (AnvilConverterException anvilconverterexception) {
/*     */       
/*  59 */       logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*  60 */       this.mc.displayGuiScreen(new GuiErrorScreen("Unable to load worlds", anvilconverterexception.getMessage()));
/*     */       
/*     */       return;
/*     */     } 
/*  64 */     this.field_146637_u = I18n.format("selectWorld.world", new Object[0]);
/*  65 */     this.field_146636_v = I18n.format("selectWorld.conversion", new Object[0]);
/*  66 */     this.field_146635_w[WorldSettings.GameType.SURVIVAL.getID()] = I18n.format("gameMode.survival", new Object[0]);
/*  67 */     this.field_146635_w[WorldSettings.GameType.CREATIVE.getID()] = I18n.format("gameMode.creative", new Object[0]);
/*  68 */     this.field_146635_w[WorldSettings.GameType.ADVENTURE.getID()] = I18n.format("gameMode.adventure", new Object[0]);
/*  69 */     this.field_146635_w[WorldSettings.GameType.SPECTATOR.getID()] = I18n.format("gameMode.spectator", new Object[0]);
/*  70 */     this.field_146638_t = new List(this.mc);
/*  71 */     this.field_146638_t.registerScrollButtons(4, 5);
/*  72 */     func_146618_g();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  80 */     super.handleMouseInput();
/*  81 */     this.field_146638_t.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_146627_h() throws AnvilConverterException {
/*  86 */     ISaveFormat isaveformat = this.mc.getSaveLoader();
/*  87 */     this.field_146639_s = isaveformat.getSaveList();
/*  88 */     Collections.sort(this.field_146639_s);
/*  89 */     this.field_146640_r = -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_146621_a(int p_146621_1_) {
/*  94 */     return ((SaveFormatComparator)this.field_146639_s.get(p_146621_1_)).getFileName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String func_146614_d(int p_146614_1_) {
/*  99 */     String s = ((SaveFormatComparator)this.field_146639_s.get(p_146614_1_)).getDisplayName();
/*     */     
/* 101 */     if (StringUtils.isEmpty(s))
/*     */     {
/* 103 */       s = I18n.format("selectWorld.world", new Object[0]) + " " + (p_146614_1_ + 1);
/*     */     }
/*     */     
/* 106 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146618_g() {
/* 111 */     this.buttonList.add(this.selectButton = new GuiButton(1, this.width / 2 - 154, this.height - 52, 150, 20, I18n.format("selectWorld.select", new Object[0])));
/* 112 */     this.buttonList.add(new GuiButton(3, this.width / 2 + 4, this.height - 52, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/* 113 */     this.buttonList.add(this.renameButton = new GuiButton(6, this.width / 2 - 154, this.height - 28, 72, 20, I18n.format("selectWorld.rename", new Object[0])));
/* 114 */     this.buttonList.add(this.deleteButton = new GuiButton(2, this.width / 2 - 76, this.height - 28, 72, 20, I18n.format("selectWorld.delete", new Object[0])));
/* 115 */     this.buttonList.add(this.recreateButton = new GuiButton(7, this.width / 2 + 4, this.height - 28, 72, 20, I18n.format("selectWorld.recreate", new Object[0])));
/* 116 */     this.buttonList.add(new GuiButton(0, this.width / 2 + 82, this.height - 28, 72, 20, I18n.format("gui.cancel", new Object[0])));
/* 117 */     this.selectButton.enabled = false;
/* 118 */     this.deleteButton.enabled = false;
/* 119 */     this.renameButton.enabled = false;
/* 120 */     this.recreateButton.enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 128 */     if (button.enabled)
/*     */     {
/* 130 */       if (button.id == 2) {
/*     */         
/* 132 */         String s = func_146614_d(this.field_146640_r);
/*     */         
/* 134 */         if (s != null)
/*     */         {
/* 136 */           this.field_146643_x = true;
/* 137 */           GuiYesNo guiyesno = func_152129_a(this, s, this.field_146640_r);
/* 138 */           this.mc.displayGuiScreen(guiyesno);
/*     */         }
/*     */       
/* 141 */       } else if (button.id == 1) {
/*     */         
/* 143 */         func_146615_e(this.field_146640_r);
/*     */       }
/* 145 */       else if (button.id == 3) {
/*     */         
/* 147 */         this.mc.displayGuiScreen(new GuiCreateWorld(this));
/*     */       }
/* 149 */       else if (button.id == 6) {
/*     */         
/* 151 */         this.mc.displayGuiScreen(new GuiRenameWorld(this, func_146621_a(this.field_146640_r)));
/*     */       }
/* 153 */       else if (button.id == 0) {
/*     */         
/* 155 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/* 157 */       else if (button.id == 7) {
/*     */         
/* 159 */         GuiCreateWorld guicreateworld = new GuiCreateWorld(this);
/* 160 */         ISaveHandler isavehandler = this.mc.getSaveLoader().getSaveLoader(func_146621_a(this.field_146640_r), false);
/* 161 */         WorldInfo worldinfo = isavehandler.loadWorldInfo();
/* 162 */         isavehandler.flush();
/* 163 */         guicreateworld.func_146318_a(worldinfo);
/* 164 */         this.mc.displayGuiScreen(guicreateworld);
/*     */       }
/*     */       else {
/*     */         
/* 168 */         this.field_146638_t.actionPerformed(button);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_146615_e(int p_146615_1_) {
/* 175 */     this.mc.displayGuiScreen(null);
/*     */     
/* 177 */     if (!this.field_146634_i) {
/*     */       
/* 179 */       this.field_146634_i = true;
/* 180 */       String s = func_146621_a(p_146615_1_);
/*     */       
/* 182 */       if (s == null)
/*     */       {
/* 184 */         s = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 187 */       String s1 = func_146614_d(p_146615_1_);
/*     */       
/* 189 */       if (s1 == null)
/*     */       {
/* 191 */         s1 = "World" + p_146615_1_;
/*     */       }
/*     */       
/* 194 */       if (this.mc.getSaveLoader().canLoadWorld(s))
/*     */       {
/* 196 */         this.mc.launchIntegratedServer(s, s1, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 203 */     if (this.field_146643_x) {
/*     */       
/* 205 */       this.field_146643_x = false;
/*     */       
/* 207 */       if (result) {
/*     */         
/* 209 */         ISaveFormat isaveformat = this.mc.getSaveLoader();
/* 210 */         isaveformat.flushCache();
/* 211 */         isaveformat.deleteWorldDirectory(func_146621_a(id));
/*     */ 
/*     */         
/*     */         try {
/* 215 */           func_146627_h();
/*     */         }
/* 217 */         catch (AnvilConverterException anvilconverterexception) {
/*     */           
/* 219 */           logger.error("Couldn't load level list", (Throwable)anvilconverterexception);
/*     */         } 
/*     */       } 
/*     */       
/* 223 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 232 */     this.field_146638_t.drawScreen(mouseX, mouseY, partialTicks);
/* 233 */     drawCenteredString(this.fontRendererObj, this.field_146628_f, this.width / 2, 20, 16777215);
/* 234 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public static GuiYesNo func_152129_a(GuiYesNoCallback p_152129_0_, String p_152129_1_, int p_152129_2_) {
/* 239 */     String s = I18n.format("selectWorld.deleteQuestion", new Object[0]);
/* 240 */     String s1 = "'" + p_152129_1_ + "' " + I18n.format("selectWorld.deleteWarning", new Object[0]);
/* 241 */     String s2 = I18n.format("selectWorld.deleteButton", new Object[0]);
/* 242 */     String s3 = I18n.format("gui.cancel", new Object[0]);
/* 243 */     GuiYesNo guiyesno = new GuiYesNo(p_152129_0_, s, s1, s2, s3, p_152129_2_);
/* 244 */     return guiyesno;
/*     */   }
/*     */   
/*     */   class List
/*     */     extends GuiSlot
/*     */   {
/*     */     public List(Minecraft mcIn) {
/* 251 */       super(mcIn, GuiSelectWorld.this.width, GuiSelectWorld.this.height, 32, GuiSelectWorld.this.height - 64, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getSize() {
/* 256 */       return GuiSelectWorld.this.field_146639_s.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/* 261 */       GuiSelectWorld.this.field_146640_r = slotIndex;
/* 262 */       boolean flag = (GuiSelectWorld.this.field_146640_r >= 0 && GuiSelectWorld.this.field_146640_r < getSize());
/* 263 */       GuiSelectWorld.this.selectButton.enabled = flag;
/* 264 */       GuiSelectWorld.this.deleteButton.enabled = flag;
/* 265 */       GuiSelectWorld.this.renameButton.enabled = flag;
/* 266 */       GuiSelectWorld.this.recreateButton.enabled = flag;
/*     */       
/* 268 */       if (isDoubleClick && flag)
/*     */       {
/* 270 */         GuiSelectWorld.this.func_146615_e(slotIndex);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isSelected(int slotIndex) {
/* 276 */       return (slotIndex == GuiSelectWorld.this.field_146640_r);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getContentHeight() {
/* 281 */       return GuiSelectWorld.this.field_146639_s.size() * 36;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawBackground() {
/* 286 */       GuiSelectWorld.this.drawDefaultBackground();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/* 291 */       SaveFormatComparator saveformatcomparator = GuiSelectWorld.this.field_146639_s.get(entryID);
/* 292 */       String s = saveformatcomparator.getDisplayName();
/*     */       
/* 294 */       if (StringUtils.isEmpty(s))
/*     */       {
/* 296 */         s = GuiSelectWorld.this.field_146637_u + " " + (entryID + 1);
/*     */       }
/*     */       
/* 299 */       String s1 = saveformatcomparator.getFileName();
/* 300 */       s1 = s1 + " (" + GuiSelectWorld.this.field_146633_h.format(new Date(saveformatcomparator.getLastTimePlayed()));
/* 301 */       s1 = s1 + ")";
/* 302 */       String s2 = "";
/*     */       
/* 304 */       if (saveformatcomparator.requiresConversion()) {
/*     */         
/* 306 */         s2 = GuiSelectWorld.this.field_146636_v + " " + s2;
/*     */       }
/*     */       else {
/*     */         
/* 310 */         s2 = GuiSelectWorld.this.field_146635_w[saveformatcomparator.getEnumGameType().getID()];
/*     */         
/* 312 */         if (saveformatcomparator.isHardcoreModeEnabled())
/*     */         {
/* 314 */           s2 = ChatFormatting.DARK_RED + I18n.format("gameMode.hardcore", new Object[0]) + ChatFormatting.RESET;
/*     */         }
/*     */         
/* 317 */         if (saveformatcomparator.getCheatsEnabled())
/*     */         {
/* 319 */           s2 = s2 + ", " + I18n.format("selectWorld.cheats", new Object[0]);
/*     */         }
/*     */       } 
/*     */       
/* 323 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s, p_180791_2_ + 2, p_180791_3_ + 1, 16777215);
/* 324 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s1, p_180791_2_ + 2, p_180791_3_ + 12, 8421504);
/* 325 */       GuiSelectWorld.this.drawString(GuiSelectWorld.this.fontRendererObj, s2, p_180791_2_ + 2, p_180791_3_ + 12 + 10, 8421504);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiSelectWorld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */