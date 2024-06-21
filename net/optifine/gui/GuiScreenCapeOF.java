/*     */ package net.optifine.gui;
/*     */ 
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ 
/*     */ public class GuiScreenCapeOF
/*     */   extends GuiScreenOF
/*     */ {
/*     */   private final GuiScreen parentScreen;
/*     */   private String title;
/*     */   private String message;
/*     */   private long messageHideTimeMs;
/*     */   private String linkUrl;
/*     */   private GuiButtonOF buttonCopyLink;
/*     */   private FontRenderer fontRenderer;
/*     */   
/*     */   public GuiScreenCapeOF(GuiScreen parentScreenIn) {
/*  26 */     this.fontRenderer = (Config.getMinecraft()).MCfontRenderer;
/*  27 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  36 */     int i = 0;
/*  37 */     this.title = I18n.format("of.options.capeOF.title", new Object[0]);
/*  38 */     i += 2;
/*  39 */     this.buttonList.add(new GuiButtonOF(210, this.width / 2 - 155, this.height / 6 + 24 * (i >> 1), 150, 20, I18n.format("of.options.capeOF.openEditor", new Object[0])));
/*  40 */     this.buttonList.add(new GuiButtonOF(220, this.width / 2 - 155 + 160, this.height / 6 + 24 * (i >> 1), 150, 20, I18n.format("of.options.capeOF.reloadCape", new Object[0])));
/*  41 */     i += 6;
/*  42 */     this.buttonCopyLink = new GuiButtonOF(230, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), 200, 20, I18n.format("of.options.capeOF.copyEditorLink", new Object[0]));
/*  43 */     this.buttonCopyLink.visible = (this.linkUrl != null);
/*  44 */     this.buttonList.add(this.buttonCopyLink);
/*  45 */     i += 4;
/*  46 */     this.buttonList.add(new GuiButtonOF(200, this.width / 2 - 100, this.height / 6 + 24 * (i >> 1), I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  54 */     if (button.enabled) {
/*     */       
/*  56 */       if (button.id == 200)
/*     */       {
/*  58 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       }
/*     */       
/*  61 */       if (button.id == 210) {
/*     */         
/*     */         try {
/*     */           
/*  65 */           String s = this.mc.getSession().getProfile().getName();
/*  66 */           String s1 = this.mc.getSession().getProfile().getId().toString().replace("-", "");
/*  67 */           String s2 = this.mc.getSession().getToken();
/*  68 */           Random random = new Random();
/*  69 */           Random random1 = new Random(System.identityHashCode(new Object()));
/*  70 */           BigInteger biginteger = new BigInteger(128, random);
/*  71 */           BigInteger biginteger1 = new BigInteger(128, random1);
/*  72 */           BigInteger biginteger2 = biginteger.xor(biginteger1);
/*  73 */           String s3 = biginteger2.toString(16);
/*  74 */           this.mc.getSessionService().joinServer(this.mc.getSession().getProfile(), s2, s3);
/*  75 */           String s4 = "https://optifine.net/capeChange?u=" + s1 + "&n=" + s + "&s=" + s3;
/*  76 */           boolean flag = Config.openWebLink(new URI(s4));
/*     */           
/*  78 */           if (flag)
/*     */           {
/*  80 */             showMessage(Lang.get("of.message.capeOF.openEditor"), 10000L);
/*     */           }
/*     */           else
/*     */           {
/*  84 */             showMessage(Lang.get("of.message.capeOF.openEditorError"), 10000L);
/*  85 */             setLinkUrl(s4);
/*     */           }
/*     */         
/*  88 */         } catch (InvalidCredentialsException invalidcredentialsexception) {
/*     */           
/*  90 */           Config.showGuiMessage(I18n.format("of.message.capeOF.error1", new Object[0]), I18n.format("of.message.capeOF.error2", new Object[] { invalidcredentialsexception.getMessage() }));
/*  91 */           Config.warn("Mojang authentication failed");
/*  92 */           Config.warn(invalidcredentialsexception.getClass().getName() + ": " + invalidcredentialsexception.getMessage());
/*     */         }
/*  94 */         catch (Exception exception) {
/*     */           
/*  96 */           Config.warn("Error opening OptiFine cape link");
/*  97 */           Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */         } 
/*     */       }
/*     */       
/* 101 */       if (button.id == 220) {
/*     */         
/* 103 */         showMessage(Lang.get("of.message.capeOF.reloadCape"), 15000L);
/*     */         
/* 105 */         if (this.mc.thePlayer != null) {
/*     */           
/* 107 */           long i = 15000L;
/* 108 */           long j = System.currentTimeMillis() + i;
/* 109 */           this.mc.thePlayer.setReloadCapeTimeMs(j);
/*     */         } 
/*     */       } 
/*     */       
/* 113 */       if (button.id == 230 && this.linkUrl != null)
/*     */       {
/* 115 */         setClipboardString(this.linkUrl);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void showMessage(String msg, long timeMs) {
/* 122 */     this.message = msg;
/* 123 */     this.messageHideTimeMs = System.currentTimeMillis() + timeMs;
/* 124 */     setLinkUrl((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 132 */     drawDefaultBackground();
/* 133 */     drawCenteredString(this.fontRenderer, this.title, this.width / 2, 20, 16777215);
/*     */     
/* 135 */     if (this.message != null) {
/*     */       
/* 137 */       drawCenteredString(this.fontRenderer, this.message, this.width / 2, this.height / 6 + 60, 16777215);
/*     */       
/* 139 */       if (System.currentTimeMillis() > this.messageHideTimeMs) {
/*     */         
/* 141 */         this.message = null;
/* 142 */         setLinkUrl((String)null);
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLinkUrl(String linkUrl) {
/* 151 */     this.linkUrl = linkUrl;
/* 152 */     this.buttonCopyLink.visible = (linkUrl != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\gui\GuiScreenCapeOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */