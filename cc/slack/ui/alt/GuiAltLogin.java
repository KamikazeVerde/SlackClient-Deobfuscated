/*     */ package cc.slack.ui.alt;
/*     */ 
/*     */ import cc.slack.utils.client.mc;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.gui.GuiButton;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiTextField;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public final class GuiAltLogin
/*     */   extends GuiScreen {
/*     */   private PasswordField password;
/*     */   private final GuiScreen previousScreen;
/*     */   private AltLoginThread thread;
/*     */   private GuiTextField username;
/*     */   
/*     */   public GuiAltLogin(GuiScreen previousScreen) {
/*  19 */     this.previousScreen = previousScreen;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/*  24 */     switch (button.id) {
/*     */       case 1:
/*  26 */         this.mc.displayGuiScreen(this.previousScreen);
/*     */         break;
/*     */       
/*     */       case 0:
/*  30 */         this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
/*  31 */         this.thread.start();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int x2, int y2, float z2) {
/*  38 */     drawDefaultBackground();
/*  39 */     this.username.drawTextBox();
/*  40 */     this.password.drawTextBox();
/*  41 */     drawCenteredString(mc.getFontRenderer(), "Alt Login", this.width / 2, 20, -1);
/*  42 */     drawCenteredString(mc.getFontRenderer(), (this.thread == null) ? (ChatFormatting.GRAY + "Idle...") : this.thread.getStatus(), this.width / 2, 29, -1);
/*  43 */     if (this.username.getText().isEmpty()) {
/*  44 */       drawString(mc.getFontRenderer(), "Username / E-Mail", this.width / 2 - 96, 66, -7829368);
/*     */     }
/*  46 */     if (this.password.getText().isEmpty()) {
/*  47 */       drawString(mc.getFontRenderer(), "Password", this.width / 2 - 96, 106, -7829368);
/*     */     }
/*  49 */     super.drawScreen(x2, y2, z2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  54 */     int var3 = this.height / 4 + 24;
/*  55 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
/*  56 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
/*  57 */     this.username = new GuiTextField(var3, mc.getFontRenderer(), this.width / 2 - 100, 60, 200, 20);
/*  58 */     this.password = new PasswordField(mc.getFontRenderer(), this.width / 2 - 100, 100, 200, 20);
/*  59 */     this.username.setFocused(true);
/*  60 */     Keyboard.enableRepeatEvents(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void keyTyped(char character, int key) {
/*     */     try {
/*  66 */       super.keyTyped(character, key);
/*  67 */     } catch (IOException e) {
/*  68 */       e.printStackTrace();
/*     */     } 
/*  70 */     if (character == '\t') {
/*  71 */       if (!this.username.isFocused() && !this.password.isFocused()) {
/*  72 */         this.username.setFocused(true);
/*     */       } else {
/*  74 */         this.username.setFocused(this.password.isFocused());
/*  75 */         this.password.setFocused(!this.username.isFocused());
/*     */       } 
/*     */     }
/*  78 */     if (character == '\r') {
/*  79 */       actionPerformed(this.buttonList.get(0));
/*     */     }
/*  81 */     this.username.textboxKeyTyped(character, key);
/*  82 */     this.password.textboxKeyTyped(character, key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int x2, int y2, int button) {
/*     */     try {
/*  88 */       super.mouseClicked(x2, y2, button);
/*  89 */     } catch (IOException e) {
/*  90 */       e.printStackTrace();
/*     */     } 
/*  92 */     this.username.mouseClicked(x2, y2, button);
/*  93 */     this.password.mouseClicked(x2, y2, button);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  98 */     Keyboard.enableRepeatEvents(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 103 */     this.username.updateCursorCounter();
/* 104 */     this.password.updateCursorCounter();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\alt\GuiAltLogin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */