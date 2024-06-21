/*     */ package cc.slack.ui.alt;
/*     */ 
/*     */ import cc.slack.utils.client.mc;
/*     */ import net.minecraft.client.gui.FontRenderer;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class PasswordField extends Gui {
/*     */   private final FontRenderer fontRenderer;
/*     */   private final int xPos;
/*     */   private final int yPos;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private String text;
/*     */   private int maxStringLength;
/*     */   private int cursorCounter;
/*     */   private boolean enableBackgroundDrawing;
/*     */   private boolean canLoseFocus;
/*     */   public boolean isFocused;
/*     */   private final boolean isEnabled;
/*     */   private int i;
/*     */   private int cursorPosition;
/*     */   private int selectionEnd;
/*     */   private int enabledColor;
/*     */   private final int disabledColor;
/*     */   private boolean b;
/*     */   
/*     */   public PasswordField(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5) {
/*  33 */     this.text = "";
/*  34 */     this.maxStringLength = 50;
/*  35 */     this.enableBackgroundDrawing = true;
/*  36 */     this.canLoseFocus = true;
/*  37 */     this.isFocused = false;
/*  38 */     this.isEnabled = true;
/*  39 */     this.i = 0;
/*  40 */     this.cursorPosition = 0;
/*  41 */     this.selectionEnd = 0;
/*  42 */     this.enabledColor = 14737632;
/*  43 */     this.disabledColor = 7368816;
/*  44 */     this.b = true;
/*  45 */     this.fontRenderer = par1FontRenderer;
/*  46 */     this.xPos = par2;
/*  47 */     this.yPos = par3;
/*  48 */     this.width = par4;
/*  49 */     this.height = par5;
/*     */   }
/*     */   
/*     */   public void updateCursorCounter() {
/*  53 */     this.cursorCounter++;
/*     */   }
/*     */   
/*     */   public void setText(String par1Str) {
/*  57 */     if (par1Str.length() > this.maxStringLength) {
/*  58 */       this.text = par1Str.substring(0, this.maxStringLength);
/*     */     } else {
/*  60 */       this.text = par1Str;
/*     */     } 
/*  62 */     setCursorPositionEnd();
/*     */   }
/*     */   
/*     */   public String getText() {
/*  66 */     return this.text.replaceAll(" ", "");
/*     */   }
/*     */   
/*     */   public String getSelectedtext() {
/*  70 */     int var1 = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/*  71 */     int var2 = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/*  72 */     return this.text.substring(var1, var2);
/*     */   }
/*     */   public void writeText(String par1Str) {
/*     */     int var8;
/*  76 */     String var2 = "";
/*  77 */     String var3 = ChatAllowedCharacters.filterAllowedCharacters(par1Str);
/*  78 */     int var4 = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/*  79 */     int var5 = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/*  80 */     int var6 = this.maxStringLength - this.text.length() - var4 - this.selectionEnd;
/*  81 */     boolean var7 = false;
/*  82 */     if (this.text.length() > 0) {
/*  83 */       var2 = String.valueOf(String.valueOf(var2)) + this.text.substring(0, var4);
/*     */     }
/*     */     
/*  86 */     if (var6 < var3.length()) {
/*  87 */       var2 = String.valueOf(String.valueOf(var2)) + var3.substring(0, var6);
/*  88 */       var8 = var6;
/*     */     } else {
/*  90 */       var2 = String.valueOf(String.valueOf(var2)) + var3;
/*  91 */       var8 = var3.length();
/*     */     } 
/*  93 */     if (this.text.length() > 0 && var5 < this.text.length()) {
/*  94 */       var2 = String.valueOf(String.valueOf(var2)) + this.text.substring(var5);
/*     */     }
/*  96 */     this.text = var2.replaceAll(" ", "");
/*  97 */     cursorPos(var4 - this.selectionEnd + var8);
/*     */   }
/*     */   
/*     */   public void func_73779_a(int par1) {
/* 101 */     if (this.text.length() != 0) {
/* 102 */       if (this.selectionEnd != this.cursorPosition) {
/* 103 */         writeText("");
/*     */       } else {
/* 105 */         deleteFromCursor(getNthWordFromCursor(par1) - this.cursorPosition);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void deleteFromCursor(int par1) {
/* 111 */     if (this.text.length() != 0) {
/* 112 */       if (this.selectionEnd != this.cursorPosition) {
/* 113 */         writeText("");
/*     */       } else {
/* 115 */         boolean var2 = (par1 < 0);
/* 116 */         int var3 = var2 ? (this.cursorPosition + par1) : this.cursorPosition;
/* 117 */         int var4 = var2 ? this.cursorPosition : (this.cursorPosition + par1);
/* 118 */         String var5 = "";
/* 119 */         if (var3 >= 0) {
/* 120 */           var5 = this.text.substring(0, var3);
/*     */         }
/* 122 */         if (var4 < this.text.length()) {
/* 123 */           var5 = String.valueOf(String.valueOf(var5)) + this.text.substring(var4);
/*     */         }
/* 125 */         this.text = var5;
/* 126 */         if (var2) {
/* 127 */           cursorPos(par1);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public int getNthWordFromCursor(int par1) {
/* 134 */     return getNthWordFromPos(par1, getCursorPosition());
/*     */   }
/*     */   
/*     */   public int getNthWordFromPos(int par1, int par2) {
/* 138 */     return type(par1, getCursorPosition(), true);
/*     */   }
/*     */   
/*     */   public int type(int par1, int par2, boolean par3) {
/* 142 */     int var4 = par2;
/* 143 */     boolean var5 = (par1 < 0);
/* 144 */     for (int var6 = Math.abs(par1), var7 = 0; var7 < var6; var7++) {
/* 145 */       if (!var5) {
/* 146 */         int var8 = this.text.length();
/* 147 */         var4 = this.text.indexOf(' ', var4);
/* 148 */         if (var4 == -1) {
/* 149 */           var4 = var8;
/*     */         } else {
/* 151 */           while (par3 && 
/* 152 */             var4 < var8) {
/*     */ 
/*     */             
/* 155 */             if (this.text.charAt(var4) != ' ') {
/*     */               break;
/*     */             }
/* 158 */             var4++;
/*     */           } 
/*     */         } 
/*     */       } else {
/* 162 */         while (par3 && 
/* 163 */           var4 > 0) {
/*     */ 
/*     */           
/* 166 */           if (this.text.charAt(var4 - 1) != ' ') {
/*     */             break;
/*     */           }
/* 169 */           var4--;
/*     */         } 
/* 171 */         while (var4 > 0 && this.text.charAt(var4 - 1) != ' ') {
/* 172 */           var4--;
/*     */         }
/*     */       } 
/*     */     } 
/* 176 */     return var4;
/*     */   }
/*     */   
/*     */   public void cursorPos(int par1) {
/* 180 */     setCursorPosition(this.selectionEnd + par1);
/*     */   }
/*     */   
/*     */   public void setCursorPosition(int par1) {
/* 184 */     this.cursorPosition = par1;
/* 185 */     int var2 = this.text.length();
/* 186 */     if (this.cursorPosition < 0) {
/* 187 */       this.cursorPosition = 0;
/*     */     }
/* 189 */     if (this.cursorPosition > var2) {
/* 190 */       this.cursorPosition = var2;
/*     */     }
/* 192 */     func_73800_i(this.cursorPosition);
/*     */   }
/*     */   
/*     */   public void setCursorPositionZero() {
/* 196 */     setCursorPosition(0);
/*     */   }
/*     */   
/*     */   public void setCursorPositionEnd() {
/* 200 */     setCursorPosition(this.text.length());
/*     */   }
/*     */   
/*     */   public boolean textboxKeyTyped(char par1, int par2) {
/* 204 */     if (!this.isEnabled || !this.isFocused) {
/* 205 */       return false;
/*     */     }
/* 207 */     switch (par1) {
/*     */       case '\001':
/* 209 */         setCursorPositionEnd();
/* 210 */         func_73800_i(0);
/* 211 */         return true;
/*     */       
/*     */       case '\003':
/* 214 */         GuiScreen.setClipboardString(getSelectedtext());
/* 215 */         return true;
/*     */       
/*     */       case '\026':
/* 218 */         writeText(GuiScreen.getClipboardString());
/* 219 */         return true;
/*     */       
/*     */       case '\030':
/* 222 */         GuiScreen.setClipboardString(getSelectedtext());
/* 223 */         writeText("");
/* 224 */         return true;
/*     */     } 
/*     */     
/* 227 */     switch (par2) {
/*     */       case 14:
/* 229 */         if (GuiScreen.isCtrlKeyDown()) {
/* 230 */           func_73779_a(-1);
/*     */         } else {
/* 232 */           deleteFromCursor(-1);
/*     */         } 
/* 234 */         return true;
/*     */       
/*     */       case 199:
/* 237 */         if (GuiScreen.isShiftKeyDown()) {
/* 238 */           func_73800_i(0);
/*     */         } else {
/* 240 */           setCursorPositionZero();
/*     */         } 
/* 242 */         return true;
/*     */       
/*     */       case 203:
/* 245 */         if (GuiScreen.isShiftKeyDown()) {
/* 246 */           if (GuiScreen.isCtrlKeyDown()) {
/* 247 */             func_73800_i(getNthWordFromPos(-1, getSelectionEnd()));
/*     */           } else {
/* 249 */             func_73800_i(getSelectionEnd() - 1);
/*     */           } 
/* 251 */         } else if (GuiScreen.isCtrlKeyDown()) {
/* 252 */           setCursorPosition(getNthWordFromCursor(-1));
/*     */         } else {
/* 254 */           cursorPos(-1);
/*     */         } 
/* 256 */         return true;
/*     */       
/*     */       case 205:
/* 259 */         if (GuiScreen.isShiftKeyDown()) {
/* 260 */           if (GuiScreen.isCtrlKeyDown()) {
/* 261 */             func_73800_i(getNthWordFromPos(1, getSelectionEnd()));
/*     */           } else {
/* 263 */             func_73800_i(getSelectionEnd() + 1);
/*     */           } 
/* 265 */         } else if (GuiScreen.isCtrlKeyDown()) {
/* 266 */           setCursorPosition(getNthWordFromCursor(1));
/*     */         } else {
/* 268 */           cursorPos(1);
/*     */         } 
/* 270 */         return true;
/*     */       
/*     */       case 207:
/* 273 */         if (GuiScreen.isShiftKeyDown()) {
/* 274 */           func_73800_i(this.text.length());
/*     */         } else {
/* 276 */           setCursorPositionEnd();
/*     */         } 
/* 278 */         return true;
/*     */       
/*     */       case 211:
/* 281 */         if (GuiScreen.isCtrlKeyDown()) {
/* 282 */           func_73779_a(1);
/*     */         } else {
/* 284 */           deleteFromCursor(1);
/*     */         } 
/* 286 */         return true;
/*     */     } 
/*     */     
/* 289 */     if (ChatAllowedCharacters.isAllowedCharacter(par1)) {
/* 290 */       writeText(Character.toString(par1));
/* 291 */       return true;
/*     */     } 
/* 293 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int par1, int par2, int par3) {
/* 301 */     boolean var4 = (par1 >= this.xPos && par1 < this.xPos + this.width && par2 >= this.yPos && par2 < this.yPos + this.height);
/* 302 */     if (this.canLoseFocus) {
/* 303 */       setFocused((this.isEnabled && var4));
/*     */     }
/* 305 */     if (this.isFocused && par3 == 0) {
/* 306 */       int var5 = par1 - this.xPos;
/* 307 */       if (this.enableBackgroundDrawing) {
/* 308 */         var5 -= 4;
/*     */       }
/* 310 */       String var6 = this.fontRenderer.trimStringToWidth(this.text.substring(this.i), getWidth());
/* 311 */       setCursorPosition(this.fontRenderer.trimStringToWidth(var6, var5).length() + this.i);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void drawTextBox() {
/* 316 */     if (func_73778_q()) {
/* 317 */       if (getEnableBackgroundDrawing()) {
/* 318 */         Gui.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
/* 319 */         Gui.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -16777216);
/*     */       } 
/* 321 */       int var1 = this.isEnabled ? this.enabledColor : this.disabledColor;
/* 322 */       int var2 = this.cursorPosition - this.i;
/* 323 */       int var3 = this.selectionEnd - this.i;
/* 324 */       String var4 = this.fontRenderer.trimStringToWidth(this.text.substring(this.i), getWidth());
/* 325 */       boolean var5 = (var2 >= 0 && var2 <= var4.length());
/* 326 */       boolean var6 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && var5);
/* 327 */       int var7 = this.enableBackgroundDrawing ? (this.xPos + 4) : this.xPos;
/* 328 */       int var8 = this.enableBackgroundDrawing ? (this.yPos + (this.height - 8) / 2) : this.yPos;
/* 329 */       int var9 = var7;
/* 330 */       if (var3 > var4.length()) {
/* 331 */         var3 = var4.length();
/*     */       }
/* 333 */       if (var4.length() > 0) {
/* 334 */         if (var5) {
/* 335 */           var4.substring(0, var2);
/*     */         }
/* 337 */         var9 = mc.getFontRenderer().drawStringWithShadow(this.text.replaceAll("(?s).", "*"), var7, var8, var1);
/*     */       } 
/* 339 */       boolean var10 = (this.cursorPosition < this.text.length() || this.text.length() >= getMaxStringLength());
/* 340 */       int var11 = var9;
/* 341 */       if (!var5) {
/* 342 */         var11 = (var2 > 0) ? (var7 + this.width) : var7;
/* 343 */       } else if (var10) {
/* 344 */         var11 = var9 - 1;
/* 345 */         var9--;
/*     */       } 
/* 347 */       if (var4.length() > 0 && var5 && var2 < var4.length()) {
/* 348 */         mc.getFontRenderer().drawStringWithShadow(var4.substring(var2), var9, var8, var1);
/*     */       }
/* 350 */       if (var6) {
/* 351 */         if (var10) {
/* 352 */           Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + this.fontRenderer.FONT_HEIGHT, -3092272);
/*     */         } else {
/* 354 */           mc.getFontRenderer().drawStringWithShadow("_", var11, var8, var1);
/*     */         } 
/*     */       }
/* 357 */       if (var3 != var2) {
/* 358 */         int var12 = var7 + this.fontRenderer.getStringWidth(var4.substring(0, var3));
/* 359 */         drawCursorVertical(var11, var8 - 1, var12 - 1, var8 + 1 + this.fontRenderer.FONT_HEIGHT);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawCursorVertical(int par1, int par2, int par3, int par4) {
/* 365 */     if (par1 < par3) {
/* 366 */       int var5 = par1;
/* 367 */       par1 = par3;
/* 368 */       par3 = var5;
/*     */     } 
/* 370 */     if (par2 < par4) {
/* 371 */       int var5 = par2;
/* 372 */       par2 = par4;
/* 373 */       par4 = var5;
/*     */     } 
/* 375 */     Tessellator tessellator = Tessellator.getInstance();
/* 376 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 377 */     GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
/* 378 */     GL11.glDisable(3553);
/* 379 */     GL11.glEnable(3058);
/* 380 */     GL11.glLogicOp(5387);
/* 381 */     worldrenderer.begin(7, worldrenderer.getVertexFormat());
/* 382 */     worldrenderer.pos(par1, par4, 0.0D);
/* 383 */     worldrenderer.pos(par3, par4, 0.0D);
/* 384 */     worldrenderer.pos(par3, par2, 0.0D);
/* 385 */     worldrenderer.pos(par1, par2, 0.0D);
/* 386 */     worldrenderer.finishDrawing();
/* 387 */     GL11.glDisable(3058);
/* 388 */     GL11.glEnable(3553);
/*     */   }
/*     */   
/*     */   public void setMaxStringLength(int par1) {
/* 392 */     this.maxStringLength = par1;
/* 393 */     if (this.text.length() > par1) {
/* 394 */       this.text = this.text.substring(0, par1);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getMaxStringLength() {
/* 399 */     return this.maxStringLength;
/*     */   }
/*     */   
/*     */   public int getCursorPosition() {
/* 403 */     return this.cursorPosition;
/*     */   }
/*     */   
/*     */   public boolean getEnableBackgroundDrawing() {
/* 407 */     return this.enableBackgroundDrawing;
/*     */   }
/*     */   
/*     */   public void setEnableBackgroundDrawing(boolean par1) {
/* 411 */     this.enableBackgroundDrawing = par1;
/*     */   }
/*     */   
/*     */   public void func_73794_g(int par1) {
/* 415 */     this.enabledColor = par1;
/*     */   }
/*     */   
/*     */   public void setFocused(boolean par1) {
/* 419 */     if (par1 && !this.isFocused) {
/* 420 */       this.cursorCounter = 0;
/*     */     }
/* 422 */     this.isFocused = par1;
/*     */   }
/*     */   
/*     */   public boolean isFocused() {
/* 426 */     return this.isFocused;
/*     */   }
/*     */   
/*     */   public int getSelectionEnd() {
/* 430 */     return this.selectionEnd;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/* 434 */     return getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
/*     */   }
/*     */   
/*     */   public void func_73800_i(int par1) {
/* 438 */     int var2 = this.text.length();
/* 439 */     if (par1 > var2) {
/* 440 */       par1 = var2;
/*     */     }
/* 442 */     if (par1 < 0) {
/* 443 */       par1 = 0;
/*     */     }
/* 445 */     this.selectionEnd = par1;
/* 446 */     if (this.fontRenderer != null) {
/* 447 */       if (this.i > var2) {
/* 448 */         this.i = var2;
/*     */       }
/* 450 */       int var3 = getWidth();
/* 451 */       String var4 = this.fontRenderer.trimStringToWidth(this.text.substring(this.i), var3);
/* 452 */       int var5 = var4.length() + this.i;
/* 453 */       if (par1 == this.i) {
/* 454 */         this.i -= this.fontRenderer.trimStringToWidth(this.text, var3, true).length();
/*     */       }
/* 456 */       if (par1 > var5) {
/* 457 */         this.i += par1 - var5;
/* 458 */       } else if (par1 <= this.i) {
/* 459 */         this.i -= this.i - par1;
/*     */       } 
/* 461 */       if (this.i < 0) {
/* 462 */         this.i = 0;
/*     */       }
/* 464 */       if (this.i > var2) {
/* 465 */         this.i = var2;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setCanLoseFocus(boolean par1) {
/* 471 */     this.canLoseFocus = par1;
/*     */   }
/*     */   
/*     */   public boolean func_73778_q() {
/* 475 */     return this.b;
/*     */   }
/*     */   
/*     */   public void func_73790_e(boolean par1) {
/* 479 */     this.b = par1;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\ui\alt\PasswordField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */