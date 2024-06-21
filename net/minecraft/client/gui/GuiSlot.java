/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiSlot
/*     */ {
/*     */   protected final Minecraft mc;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int top;
/*     */   protected int bottom;
/*     */   protected int right;
/*     */   protected int left;
/*     */   protected final int slotHeight;
/*     */   private int scrollUpButtonID;
/*     */   private int scrollDownButtonID;
/*     */   protected int mouseX;
/*     */   protected int mouseY;
/*     */   protected boolean field_148163_i = true;
/*  38 */   protected int initialClickY = -2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float scrollMultiplier;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float amountScrolled;
/*     */ 
/*     */ 
/*     */   
/*  50 */   protected int selectedElement = -1;
/*     */   
/*     */   protected long lastClicked;
/*     */   
/*     */   protected boolean field_178041_q = true;
/*     */   
/*     */   protected boolean showSelectionBox = true;
/*     */   
/*     */   protected boolean hasListHeader;
/*     */   
/*     */   protected int headerPadding;
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */   
/*     */   public GuiSlot(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
/*  66 */     this.mc = mcIn;
/*  67 */     this.width = width;
/*  68 */     this.height = height;
/*  69 */     this.top = topIn;
/*  70 */     this.bottom = bottomIn;
/*  71 */     this.slotHeight = slotHeightIn;
/*  72 */     this.left = 0;
/*  73 */     this.right = width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn) {
/*  78 */     this.width = widthIn;
/*  79 */     this.height = heightIn;
/*  80 */     this.top = topIn;
/*  81 */     this.bottom = bottomIn;
/*  82 */     this.left = 0;
/*  83 */     this.right = widthIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowSelectionBox(boolean showSelectionBoxIn) {
/*  88 */     this.showSelectionBox = showSelectionBoxIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setHasListHeader(boolean hasListHeaderIn, int headerPaddingIn) {
/*  97 */     this.hasListHeader = hasListHeaderIn;
/*  98 */     this.headerPadding = headerPaddingIn;
/*     */     
/* 100 */     if (!hasListHeaderIn)
/*     */     {
/* 102 */       this.headerPadding = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getSize();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void elementClicked(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isSelected(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 123 */     return getSize() * this.slotHeight + this.headerPadding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawBackground();
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_148132_a(int p_148132_1_, int p_148132_2_) {}
/*     */ 
/*     */   
/*     */   protected void func_148142_b(int p_148142_1_, int p_148142_2_) {}
/*     */ 
/*     */   
/*     */   public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_) {
/* 151 */     int i = this.left + this.width / 2 - getListWidth() / 2;
/* 152 */     int j = this.left + this.width / 2 + getListWidth() / 2;
/* 153 */     int k = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 154 */     int l = k / this.slotHeight;
/* 155 */     return (p_148124_1_ < getScrollBarX() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < getSize()) ? l : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScrollButtons(int scrollUpButtonIDIn, int scrollDownButtonIDIn) {
/* 163 */     this.scrollUpButtonID = scrollUpButtonIDIn;
/* 164 */     this.scrollDownButtonID = scrollDownButtonIDIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bindAmountScrolled() {
/* 172 */     this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0F, func_148135_f());
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_148135_f() {
/* 177 */     return Math.max(0, getContentHeight() - this.bottom - this.top - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmountScrolled() {
/* 185 */     return (int)this.amountScrolled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseYWithinSlotBounds(int p_148141_1_) {
/* 190 */     return (p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrollBy(int amount) {
/* 198 */     this.amountScrolled += amount;
/* 199 */     bindAmountScrolled();
/* 200 */     this.initialClickY = -2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(GuiButton button) {
/* 205 */     if (button.enabled)
/*     */     {
/* 207 */       if (button.id == this.scrollUpButtonID) {
/*     */         
/* 209 */         this.amountScrolled -= (this.slotHeight * 2 / 3);
/* 210 */         this.initialClickY = -2;
/* 211 */         bindAmountScrolled();
/*     */       }
/* 213 */       else if (button.id == this.scrollDownButtonID) {
/*     */         
/* 215 */         this.amountScrolled += (this.slotHeight * 2 / 3);
/* 216 */         this.initialClickY = -2;
/* 217 */         bindAmountScrolled();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
/* 224 */     if (this.field_178041_q) {
/*     */       
/* 226 */       this.mouseX = mouseXIn;
/* 227 */       this.mouseY = mouseYIn;
/* 228 */       drawBackground();
/* 229 */       int i = getScrollBarX();
/* 230 */       int j = i + 6;
/* 231 */       bindAmountScrolled();
/* 232 */       GlStateManager.disableLighting();
/* 233 */       GlStateManager.disableFog();
/* 234 */       Tessellator tessellator = Tessellator.getInstance();
/* 235 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 236 */       drawContainerBackground(tessellator);
/* 237 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 238 */       int l = this.top + 4 - (int)this.amountScrolled;
/*     */       
/* 240 */       if (this.hasListHeader)
/*     */       {
/* 242 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/* 245 */       drawSelectionBox(k, l, mouseXIn, mouseYIn);
/* 246 */       GlStateManager.disableDepth();
/* 247 */       int i1 = 4;
/* 248 */       overlayBackground(0, this.top, 255, 255);
/* 249 */       overlayBackground(this.bottom, this.height, 255, 255);
/* 250 */       GlStateManager.enableBlend();
/* 251 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/* 252 */       GlStateManager.disableAlpha();
/* 253 */       GlStateManager.shadeModel(7425);
/* 254 */       GlStateManager.disableTexture2D();
/* 255 */       worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 256 */       worldrenderer.pos(this.left, (this.top + i1), 0.0D).tex(0.0D, 1.0D).func_181669_b(0, 0, 0, 0).endVertex();
/* 257 */       worldrenderer.pos(this.right, (this.top + i1), 0.0D).tex(1.0D, 1.0D).func_181669_b(0, 0, 0, 0).endVertex();
/* 258 */       worldrenderer.pos(this.right, this.top, 0.0D).tex(1.0D, 0.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 259 */       worldrenderer.pos(this.left, this.top, 0.0D).tex(0.0D, 0.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 260 */       tessellator.draw();
/* 261 */       worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 262 */       worldrenderer.pos(this.left, this.bottom, 0.0D).tex(0.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 263 */       worldrenderer.pos(this.right, this.bottom, 0.0D).tex(1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 264 */       worldrenderer.pos(this.right, (this.bottom - i1), 0.0D).tex(1.0D, 0.0D).func_181669_b(0, 0, 0, 0).endVertex();
/* 265 */       worldrenderer.pos(this.left, (this.bottom - i1), 0.0D).tex(0.0D, 0.0D).func_181669_b(0, 0, 0, 0).endVertex();
/* 266 */       tessellator.draw();
/* 267 */       int j1 = func_148135_f();
/*     */       
/* 269 */       if (j1 > 0) {
/*     */         
/* 271 */         int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 272 */         k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
/* 273 */         int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
/*     */         
/* 275 */         if (l1 < this.top)
/*     */         {
/* 277 */           l1 = this.top;
/*     */         }
/*     */         
/* 280 */         worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 281 */         worldrenderer.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 282 */         worldrenderer.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 283 */         worldrenderer.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 284 */         worldrenderer.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 285 */         tessellator.draw();
/* 286 */         worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 287 */         worldrenderer.pos(i, (l1 + k1), 0.0D).tex(0.0D, 1.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 288 */         worldrenderer.pos(j, (l1 + k1), 0.0D).tex(1.0D, 1.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 289 */         worldrenderer.pos(j, l1, 0.0D).tex(1.0D, 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 290 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 291 */         tessellator.draw();
/* 292 */         worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 293 */         worldrenderer.pos(i, (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).func_181669_b(192, 192, 192, 255).endVertex();
/* 294 */         worldrenderer.pos((j - 1), (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).func_181669_b(192, 192, 192, 255).endVertex();
/* 295 */         worldrenderer.pos((j - 1), l1, 0.0D).tex(1.0D, 0.0D).func_181669_b(192, 192, 192, 255).endVertex();
/* 296 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).func_181669_b(192, 192, 192, 255).endVertex();
/* 297 */         tessellator.draw();
/*     */       } 
/*     */       
/* 300 */       func_148142_b(mouseXIn, mouseYIn);
/* 301 */       GlStateManager.enableTexture2D();
/* 302 */       GlStateManager.shadeModel(7424);
/* 303 */       GlStateManager.enableAlpha();
/* 304 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() {
/* 310 */     if (isMouseYWithinSlotBounds(this.mouseY)) {
/*     */       
/* 312 */       if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */         
/* 314 */         int i = (this.width - getListWidth()) / 2;
/* 315 */         int j = (this.width + getListWidth()) / 2;
/* 316 */         int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 317 */         int l = k / this.slotHeight;
/*     */         
/* 319 */         if (l < getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
/*     */           
/* 321 */           elementClicked(l, false, this.mouseX, this.mouseY);
/* 322 */           this.selectedElement = l;
/*     */         }
/* 324 */         else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
/*     */           
/* 326 */           func_148132_a(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
/*     */         } 
/*     */       } 
/*     */       
/* 330 */       if (Mouse.isButtonDown(0) && getEnabled()) {
/*     */         
/* 332 */         if (this.initialClickY != -1) {
/*     */           
/* 334 */           if (this.initialClickY >= 0)
/*     */           {
/* 336 */             this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
/* 337 */             this.initialClickY = this.mouseY;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 342 */           boolean flag1 = true;
/*     */           
/* 344 */           if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */             
/* 346 */             int j2 = (this.width - getListWidth()) / 2;
/* 347 */             int k2 = (this.width + getListWidth()) / 2;
/* 348 */             int l2 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 349 */             int i1 = l2 / this.slotHeight;
/*     */             
/* 351 */             if (i1 < getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0) {
/*     */               
/* 353 */               boolean flag = (i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L);
/* 354 */               elementClicked(i1, flag, this.mouseX, this.mouseY);
/* 355 */               this.selectedElement = i1;
/* 356 */               this.lastClicked = Minecraft.getSystemTime();
/*     */             }
/* 358 */             else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0) {
/*     */               
/* 360 */               func_148132_a(this.mouseX - j2, this.mouseY - this.top + (int)this.amountScrolled - 4);
/* 361 */               flag1 = false;
/*     */             } 
/*     */             
/* 364 */             int i3 = getScrollBarX();
/* 365 */             int j1 = i3 + 6;
/*     */             
/* 367 */             if (this.mouseX >= i3 && this.mouseX <= j1) {
/*     */               
/* 369 */               this.scrollMultiplier = -1.0F;
/* 370 */               int k1 = func_148135_f();
/*     */               
/* 372 */               if (k1 < 1)
/*     */               {
/* 374 */                 k1 = 1;
/*     */               }
/*     */               
/* 377 */               int l1 = (int)(((this.bottom - this.top) * (this.bottom - this.top)) / getContentHeight());
/* 378 */               l1 = MathHelper.clamp_int(l1, 32, this.bottom - this.top - 8);
/* 379 */               this.scrollMultiplier /= (this.bottom - this.top - l1) / k1;
/*     */             }
/*     */             else {
/*     */               
/* 383 */               this.scrollMultiplier = 1.0F;
/*     */             } 
/*     */             
/* 386 */             if (flag1)
/*     */             {
/* 388 */               this.initialClickY = this.mouseY;
/*     */             }
/*     */             else
/*     */             {
/* 392 */               this.initialClickY = -2;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 397 */             this.initialClickY = -2;
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 403 */         this.initialClickY = -1;
/*     */       } 
/*     */       
/* 406 */       int i2 = Mouse.getEventDWheel();
/*     */       
/* 408 */       if (i2 != 0) {
/*     */         
/* 410 */         if (i2 > 0) {
/*     */           
/* 412 */           i2 = -1;
/*     */         }
/* 414 */         else if (i2 < 0) {
/*     */           
/* 416 */           i2 = 1;
/*     */         } 
/*     */         
/* 419 */         this.amountScrolled += (i2 * this.slotHeight / 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabledIn) {
/* 426 */     this.enabled = enabledIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnabled() {
/* 431 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 439 */     return 220;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn) {
/* 447 */     int i = getSize();
/* 448 */     Tessellator tessellator = Tessellator.getInstance();
/* 449 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 451 */     for (int j = 0; j < i; j++) {
/*     */       
/* 453 */       int k = p_148120_2_ + j * this.slotHeight + this.headerPadding;
/* 454 */       int l = this.slotHeight - 4;
/*     */       
/* 456 */       if (k > this.bottom || k + l < this.top)
/*     */       {
/* 458 */         func_178040_a(j, p_148120_1_, k);
/*     */       }
/*     */       
/* 461 */       if (this.showSelectionBox && isSelected(j)) {
/*     */         
/* 463 */         int i1 = this.left + this.width / 2 - getListWidth() / 2;
/* 464 */         int j1 = this.left + this.width / 2 + getListWidth() / 2;
/* 465 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 466 */         GlStateManager.disableTexture2D();
/* 467 */         worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 468 */         worldrenderer.pos(i1, (k + l + 2), 0.0D).tex(0.0D, 1.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 469 */         worldrenderer.pos(j1, (k + l + 2), 0.0D).tex(1.0D, 1.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 470 */         worldrenderer.pos(j1, (k - 2), 0.0D).tex(1.0D, 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 471 */         worldrenderer.pos(i1, (k - 2), 0.0D).tex(0.0D, 0.0D).func_181669_b(128, 128, 128, 255).endVertex();
/* 472 */         worldrenderer.pos((i1 + 1), (k + l + 1), 0.0D).tex(0.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 473 */         worldrenderer.pos((j1 - 1), (k + l + 1), 0.0D).tex(1.0D, 1.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 474 */         worldrenderer.pos((j1 - 1), (k - 1), 0.0D).tex(1.0D, 0.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 475 */         worldrenderer.pos((i1 + 1), (k - 1), 0.0D).tex(0.0D, 0.0D).func_181669_b(0, 0, 0, 255).endVertex();
/* 476 */         tessellator.draw();
/* 477 */         GlStateManager.enableTexture2D();
/*     */       } 
/*     */       
/* 480 */       if (!(this instanceof GuiResourcePackList) || (k >= this.top - this.slotHeight && k <= this.bottom))
/*     */       {
/* 482 */         drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 489 */     return this.width / 2 + 124;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
/* 497 */     Tessellator tessellator = Tessellator.getInstance();
/* 498 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 499 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 500 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 501 */     float f = 32.0F;
/* 502 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 503 */     worldrenderer.pos(this.left, endY, 0.0D).tex(0.0D, (endY / 32.0F)).func_181669_b(64, 64, 64, endAlpha).endVertex();
/* 504 */     worldrenderer.pos((this.left + this.width), endY, 0.0D).tex((this.width / 32.0F), (endY / 32.0F)).func_181669_b(64, 64, 64, endAlpha).endVertex();
/* 505 */     worldrenderer.pos((this.left + this.width), startY, 0.0D).tex((this.width / 32.0F), (startY / 32.0F)).func_181669_b(64, 64, 64, startAlpha).endVertex();
/* 506 */     worldrenderer.pos(this.left, startY, 0.0D).tex(0.0D, (startY / 32.0F)).func_181669_b(64, 64, 64, startAlpha).endVertex();
/* 507 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlotXBoundsFromLeft(int leftIn) {
/* 515 */     this.left = leftIn;
/* 516 */     this.right = leftIn + this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotHeight() {
/* 521 */     return this.slotHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawContainerBackground(Tessellator p_drawContainerBackground_1_) {
/* 526 */     WorldRenderer worldrenderer = p_drawContainerBackground_1_.getWorldRenderer();
/* 527 */     this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 528 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 529 */     float f = 32.0F;
/* 530 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 531 */     worldrenderer.pos(this.left, this.bottom, 0.0D).tex((this.left / f), ((this.bottom + (int)this.amountScrolled) / f)).func_181669_b(32, 32, 32, 255).endVertex();
/* 532 */     worldrenderer.pos(this.right, this.bottom, 0.0D).tex((this.right / f), ((this.bottom + (int)this.amountScrolled) / f)).func_181669_b(32, 32, 32, 255).endVertex();
/* 533 */     worldrenderer.pos(this.right, this.top, 0.0D).tex((this.right / f), ((this.top + (int)this.amountScrolled) / f)).func_181669_b(32, 32, 32, 255).endVertex();
/* 534 */     worldrenderer.pos(this.left, this.top, 0.0D).tex((this.left / f), ((this.top + (int)this.amountScrolled) / f)).func_181669_b(32, 32, 32, 255).endVertex();
/* 535 */     p_drawContainerBackground_1_.draw();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */