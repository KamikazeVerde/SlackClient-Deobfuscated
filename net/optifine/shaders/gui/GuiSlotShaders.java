/*     */ package net.optifine.shaders.gui;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiSlot;
/*     */ import net.minecraft.client.gui.GuiYesNo;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.shaders.IShaderPack;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.ResUtils;
/*     */ 
/*     */ class GuiSlotShaders extends GuiSlot {
/*     */   private ArrayList shaderslist;
/*  20 */   private long lastClickedCached = 0L;
/*     */   private int selectedIndex;
/*     */   final GuiShaders shadersGui;
/*     */   
/*     */   public GuiSlotShaders(GuiShaders par1GuiShaders, int width, int height, int top, int bottom, int slotHeight) {
/*  25 */     super(par1GuiShaders.getMc(), width, height, top, bottom, slotHeight);
/*  26 */     this.shadersGui = par1GuiShaders;
/*  27 */     updateList();
/*  28 */     this.amountScrolled = 0.0F;
/*  29 */     int i = this.selectedIndex * slotHeight;
/*  30 */     int j = (bottom - top) / 2;
/*     */     
/*  32 */     if (i > j)
/*     */     {
/*  34 */       scrollBy(i - j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/*  43 */     return this.width - 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateList() {
/*  48 */     this.shaderslist = Shaders.listOfShaders();
/*  49 */     this.selectedIndex = 0;
/*  50 */     int i = 0;
/*     */     
/*  52 */     for (int j = this.shaderslist.size(); i < j; i++) {
/*     */       
/*  54 */       if (this.shaderslist.get(i).equals(Shaders.currentShaderName)) {
/*     */         
/*  56 */         this.selectedIndex = i;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  64 */     return this.shaderslist.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int index, boolean doubleClicked, int mouseX, int mouseY) {
/*  72 */     if (index != this.selectedIndex || this.lastClicked != this.lastClickedCached) {
/*     */       
/*  74 */       String s = this.shaderslist.get(index);
/*  75 */       IShaderPack ishaderpack = Shaders.getShaderPack(s);
/*     */       
/*  77 */       if (checkCompatible(ishaderpack, index))
/*     */       {
/*  79 */         selectIndex(index);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectIndex(int index) {
/*  86 */     this.selectedIndex = index;
/*  87 */     this.lastClickedCached = this.lastClicked;
/*  88 */     Shaders.setShaderPack(this.shaderslist.get(index));
/*  89 */     Shaders.uninit();
/*  90 */     this.shadersGui.updateButtons();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkCompatible(IShaderPack sp, final int index) {
/*  95 */     if (sp == null)
/*     */     {
/*  97 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 101 */     InputStream inputstream = sp.getResourceAsStream("/shaders/shaders.properties");
/* 102 */     Properties properties = ResUtils.readProperties(inputstream, "Shaders");
/*     */     
/* 104 */     if (properties == null)
/*     */     {
/* 106 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 110 */     String s = "version.1.8.9";
/* 111 */     String s1 = properties.getProperty(s);
/*     */     
/* 113 */     if (s1 == null)
/*     */     {
/* 115 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 119 */     s1 = s1.trim();
/* 120 */     String s2 = "L5";
/* 121 */     int i = Config.compareRelease(s2, s1);
/*     */     
/* 123 */     if (i >= 0)
/*     */     {
/* 125 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 129 */     String s3 = ("HD_U_" + s1).replace('_', ' ');
/* 130 */     String s4 = I18n.format("of.message.shaders.nv1", new Object[] { s3 });
/* 131 */     String s5 = I18n.format("of.message.shaders.nv2", new Object[0]);
/* 132 */     GuiYesNoCallback guiyesnocallback = new GuiYesNoCallback()
/*     */       {
/*     */         public void confirmClicked(boolean result, int id)
/*     */         {
/* 136 */           if (result)
/*     */           {
/* 138 */             GuiSlotShaders.this.selectIndex(index);
/*     */           }
/*     */           
/* 141 */           GuiSlotShaders.this.mc.displayGuiScreen(GuiSlotShaders.this.shadersGui);
/*     */         }
/*     */       };
/* 144 */     GuiYesNo guiyesno = new GuiYesNo(guiyesnocallback, s4, s5, 0);
/* 145 */     this.mc.displayGuiScreen((GuiScreen)guiyesno);
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int index) {
/* 158 */     return (index == this.selectedIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 163 */     return this.width - 6;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 171 */     return getSize() * 18;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawBackground() {}
/*     */ 
/*     */   
/*     */   protected void drawSlot(int index, int posX, int posY, int contentY, int mouseX, int mouseY) {
/* 180 */     String s = this.shaderslist.get(index);
/*     */     
/* 182 */     if (s.equals("OFF")) {
/*     */       
/* 184 */       s = Lang.get("of.options.shaders.packNone");
/*     */     }
/* 186 */     else if (s.equals("(internal)")) {
/*     */       
/* 188 */       s = Lang.get("of.options.shaders.packDefault");
/*     */     } 
/*     */     
/* 191 */     this.shadersGui.drawCenteredString(s, this.width / 2, posY + 1, 14737632);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSelectedIndex() {
/* 196 */     return this.selectedIndex;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\gui\GuiSlotShaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */