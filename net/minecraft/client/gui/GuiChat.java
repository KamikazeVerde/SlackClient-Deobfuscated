/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiChat extends GuiScreen {
/*  20 */   private static final Logger logger = LogManager.getLogger();
/*  21 */   private String historyBuffer = "";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   private int sentHistoryCursor = -1;
/*     */   private boolean playerNamesFound;
/*     */   private boolean waitingOnAutocomplete;
/*     */   private int autocompleteIndex;
/*  31 */   private List<String> foundPlayerNames = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */   
/*     */   protected GuiTextField inputField;
/*     */ 
/*     */ 
/*     */   
/*  39 */   private String defaultInputFieldText = "";
/*     */ 
/*     */ 
/*     */   
/*     */   public GuiChat() {}
/*     */ 
/*     */   
/*     */   public GuiChat(String defaultText) {
/*  47 */     this.defaultInputFieldText = defaultText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  56 */     Keyboard.enableRepeatEvents(true);
/*  57 */     this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/*  58 */     this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
/*  59 */     this.inputField.setMaxStringLength(100);
/*  60 */     this.inputField.setEnableBackgroundDrawing(false);
/*  61 */     this.inputField.setFocused(true);
/*  62 */     this.inputField.setText(this.defaultInputFieldText);
/*  63 */     this.inputField.setCanLoseFocus(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  71 */     Keyboard.enableRepeatEvents(false);
/*  72 */     this.mc.ingameGUI.getChatGUI().resetScroll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  80 */     this.inputField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  89 */     this.waitingOnAutocomplete = false;
/*     */     
/*  91 */     if (keyCode == 15) {
/*     */       
/*  93 */       autocompletePlayerNames();
/*     */     }
/*     */     else {
/*     */       
/*  97 */       this.playerNamesFound = false;
/*     */     } 
/*     */     
/* 100 */     if (keyCode == 1) {
/*     */       
/* 102 */       this.mc.displayGuiScreen(null);
/*     */     }
/* 104 */     else if (keyCode != 28 && keyCode != 156) {
/*     */       
/* 106 */       if (keyCode == 200)
/*     */       {
/* 108 */         getSentHistory(-1);
/*     */       }
/* 110 */       else if (keyCode == 208)
/*     */       {
/* 112 */         getSentHistory(1);
/*     */       }
/* 114 */       else if (keyCode == 201)
/*     */       {
/* 116 */         this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
/*     */       }
/* 118 */       else if (keyCode == 209)
/*     */       {
/* 120 */         this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
/*     */       }
/*     */       else
/*     */       {
/* 124 */         this.inputField.textboxKeyTyped(typedChar, keyCode);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 129 */       String s = this.inputField.getText().trim();
/*     */       
/* 131 */       if (s.length() > 0)
/*     */       {
/* 133 */         sendChatMessage(s);
/*     */       }
/*     */       
/* 136 */       this.mc.displayGuiScreen(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 145 */     super.handleMouseInput();
/* 146 */     int i = Mouse.getEventDWheel();
/*     */     
/* 148 */     if (i != 0) {
/*     */       
/* 150 */       if (i > 1)
/*     */       {
/* 152 */         i = 1;
/*     */       }
/*     */       
/* 155 */       if (i < -1)
/*     */       {
/* 157 */         i = -1;
/*     */       }
/*     */       
/* 160 */       if (!isShiftKeyDown())
/*     */       {
/* 162 */         i *= 7;
/*     */       }
/*     */       
/* 165 */       this.mc.ingameGUI.getChatGUI().scroll(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 174 */     if (mouseButton == 0) {
/*     */       
/* 176 */       IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/*     */       
/* 178 */       if (handleComponentClick(ichatcomponent)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 184 */     this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
/* 185 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {
/* 196 */     if (shouldOverwrite) {
/*     */       
/* 198 */       this.inputField.setText(newChatText);
/*     */     }
/*     */     else {
/*     */       
/* 202 */       this.inputField.writeText(newChatText);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void autocompletePlayerNames() {
/* 208 */     if (this.playerNamesFound) {
/*     */       
/* 210 */       this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
/*     */       
/* 212 */       if (this.autocompleteIndex >= this.foundPlayerNames.size())
/*     */       {
/* 214 */         this.autocompleteIndex = 0;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 219 */       int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
/* 220 */       this.foundPlayerNames.clear();
/* 221 */       this.autocompleteIndex = 0;
/* 222 */       String s = this.inputField.getText().substring(i).toLowerCase();
/* 223 */       String s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
/* 224 */       sendAutocompleteRequest(s1, s);
/*     */       
/* 226 */       if (this.foundPlayerNames.isEmpty()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 231 */       this.playerNamesFound = true;
/* 232 */       this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
/*     */     } 
/*     */     
/* 235 */     if (this.foundPlayerNames.size() > 1) {
/*     */       
/* 237 */       StringBuilder stringbuilder = new StringBuilder();
/*     */       
/* 239 */       for (String s2 : this.foundPlayerNames) {
/*     */         
/* 241 */         if (stringbuilder.length() > 0)
/*     */         {
/* 243 */           stringbuilder.append(", ");
/*     */         }
/*     */         
/* 246 */         stringbuilder.append(s2);
/*     */       } 
/*     */       
/* 249 */       this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((IChatComponent)new ChatComponentText(stringbuilder.toString()), 1);
/*     */     } 
/*     */     
/* 252 */     this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_) {
/* 257 */     if (p_146405_1_.length() >= 1) {
/*     */       
/* 259 */       BlockPos blockpos = null;
/*     */       
/* 261 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK)
/*     */       {
/* 263 */         blockpos = this.mc.objectMouseOver.getBlockPos();
/*     */       }
/*     */       
/* 266 */       this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C14PacketTabComplete(p_146405_1_, blockpos));
/* 267 */       this.waitingOnAutocomplete = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSentHistory(int msgPos) {
/* 279 */     int i = this.sentHistoryCursor + msgPos;
/* 280 */     int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/* 281 */     i = MathHelper.clamp_int(i, 0, j);
/*     */     
/* 283 */     if (i != this.sentHistoryCursor)
/*     */     {
/* 285 */       if (i == j) {
/*     */         
/* 287 */         this.sentHistoryCursor = j;
/* 288 */         this.inputField.setText(this.historyBuffer);
/*     */       }
/*     */       else {
/*     */         
/* 292 */         if (this.sentHistoryCursor == j)
/*     */         {
/* 294 */           this.historyBuffer = this.inputField.getText();
/*     */         }
/*     */         
/* 297 */         this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
/* 298 */         this.sentHistoryCursor = i;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 308 */     drawRect(2, this.height - 14, this.width - 2, this.height - 2, -2147483648);
/* 309 */     this.inputField.drawTextBox();
/* 310 */     IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/*     */     
/* 312 */     if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null)
/*     */     {
/* 314 */       handleComponentHover(ichatcomponent, mouseX, mouseY);
/*     */     }
/*     */     
/* 317 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAutocompleteResponse(String[] p_146406_1_) {
/* 322 */     if (this.waitingOnAutocomplete) {
/*     */       
/* 324 */       this.playerNamesFound = false;
/* 325 */       this.foundPlayerNames.clear();
/*     */       
/* 327 */       for (String s : p_146406_1_) {
/*     */         
/* 329 */         if (s.length() > 0)
/*     */         {
/* 331 */           this.foundPlayerNames.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 335 */       String s1 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
/* 336 */       String s2 = StringUtils.getCommonPrefix(p_146406_1_);
/*     */       
/* 338 */       if (s2.length() > 0 && !s1.equalsIgnoreCase(s2)) {
/*     */         
/* 340 */         this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
/* 341 */         this.inputField.writeText(s2);
/*     */       }
/* 343 */       else if (this.foundPlayerNames.size() > 0) {
/*     */         
/* 345 */         this.playerNamesFound = true;
/* 346 */         autocompletePlayerNames();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 356 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */