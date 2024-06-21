/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.entity.RenderItem;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.event.HoverEvent;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.JsonToNBT;
/*     */ import net.minecraft.nbt.NBTException;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.stats.Achievement;
/*     */ import net.minecraft.stats.StatBase;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public abstract class GuiScreen
/*     */   extends Gui
/*     */   implements GuiYesNoCallback {
/*  48 */   private static final Logger LOGGER = LogManager.getLogger();
/*  49 */   private static final Set<String> PROTOCOLS = Sets.newHashSet((Object[])new String[] { "http", "https" });
/*  50 */   private static final Splitter NEWLINE_SPLITTER = Splitter.on('\n');
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Minecraft mc;
/*     */ 
/*     */ 
/*     */   
/*     */   protected RenderItem itemRender;
/*     */ 
/*     */ 
/*     */   
/*     */   public int width;
/*     */ 
/*     */ 
/*     */   
/*     */   public int height;
/*     */ 
/*     */ 
/*     */   
/*  71 */   protected CopyOnWriteArrayList<GuiButton> buttonList = new CopyOnWriteArrayList<>();
/*  72 */   protected CopyOnWriteArrayList<GuiLabel> labelList = new CopyOnWriteArrayList<>();
/*     */ 
/*     */   
/*     */   public boolean allowUserInput;
/*     */ 
/*     */   
/*     */   protected FontRenderer fontRendererObj;
/*     */ 
/*     */   
/*     */   private GuiButton selectedButton;
/*     */ 
/*     */   
/*     */   private int eventButton;
/*     */ 
/*     */   
/*     */   private long lastMouseEvent;
/*     */ 
/*     */   
/*     */   private int touchValue;
/*     */ 
/*     */   
/*     */   private URI clickedLinkURI;
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  98 */     this.buttonList.forEach(guiButton -> guiButton.drawButton(this.mc, mouseX, mouseY));
/*  99 */     this.labelList.forEach(guiLabel -> guiLabel.drawLabel(this.mc, mouseX, mouseY));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/* 107 */     if (keyCode == 1) {
/* 108 */       this.mc.displayGuiScreen(null);
/*     */       
/* 110 */       if (this.mc.currentScreen == null) {
/* 111 */         this.mc.setIngameFocus();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getClipboardString() {
/*     */     try {
/* 121 */       Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
/*     */       
/* 123 */       if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
/* 124 */         return (String)transferable.getTransferData(DataFlavor.stringFlavor);
/*     */       }
/* 126 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 130 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setClipboardString(String copyText) {
/* 137 */     if (!StringUtils.isEmpty(copyText)) {
/*     */       try {
/* 139 */         StringSelection stringselection = new StringSelection(copyText);
/* 140 */         Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringselection, null);
/* 141 */       } catch (Exception exception) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderToolTip(ItemStack stack, int x, int y) {
/* 148 */     List<String> list = stack.getTooltip((EntityPlayer)this.mc.thePlayer, this.mc.gameSettings.advancedItemTooltips);
/*     */     
/* 150 */     for (int i = 0; i < list.size(); i++) {
/* 151 */       if (i == 0) {
/* 152 */         list.set(i, (stack.getRarity()).rarityColor + (String)list.get(i));
/*     */       } else {
/* 154 */         list.set(i, ChatFormatting.GRAY + (String)list.get(i));
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     drawHoveringText(list, x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawCreativeTabHoveringText(String tabName, int mouseX, int mouseY) {
/* 166 */     drawHoveringText(Arrays.asList(new String[] { tabName }, ), mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawHoveringText(List<String> textLines, int x, int y) {
/* 173 */     if (!textLines.isEmpty()) {
/* 174 */       GlStateManager.disableRescaleNormal();
/* 175 */       RenderHelper.disableStandardItemLighting();
/* 176 */       GlStateManager.disableLighting();
/* 177 */       GlStateManager.disableDepth();
/* 178 */       int i = 0;
/*     */       
/* 180 */       for (String s : textLines) {
/* 181 */         int j = this.fontRendererObj.getStringWidth(s);
/*     */         
/* 183 */         if (j > i) {
/* 184 */           i = j;
/*     */         }
/*     */       } 
/*     */       
/* 188 */       int l1 = x + 12;
/* 189 */       int i2 = y - 12;
/* 190 */       int k = 8;
/*     */       
/* 192 */       if (textLines.size() > 1) {
/* 193 */         k += 2 + (textLines.size() - 1) * 10;
/*     */       }
/*     */       
/* 196 */       if (l1 + i > this.width) {
/* 197 */         l1 -= 28 + i;
/*     */       }
/*     */       
/* 200 */       if (i2 + k + 6 > this.height) {
/* 201 */         i2 = this.height - k - 6;
/*     */       }
/*     */       
/* 204 */       this.zLevel = 300.0F;
/* 205 */       this.itemRender.zLevel = 300.0F;
/* 206 */       int l = -267386864;
/* 207 */       drawGradientRect(l1 - 3, i2 - 4, l1 + i + 3, i2 - 3, l, l);
/* 208 */       drawGradientRect(l1 - 3, i2 + k + 3, l1 + i + 3, i2 + k + 4, l, l);
/* 209 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 + k + 3, l, l);
/* 210 */       drawGradientRect(l1 - 4, i2 - 3, l1 - 3, i2 + k + 3, l, l);
/* 211 */       drawGradientRect(l1 + i + 3, i2 - 3, l1 + i + 4, i2 + k + 3, l, l);
/* 212 */       int i1 = 1347420415;
/* 213 */       int j1 = (i1 & 0xFEFEFE) >> 1 | i1 & 0xFF000000;
/* 214 */       drawGradientRect(l1 - 3, i2 - 3 + 1, l1 - 3 + 1, i2 + k + 3 - 1, i1, j1);
/* 215 */       drawGradientRect(l1 + i + 2, i2 - 3 + 1, l1 + i + 3, i2 + k + 3 - 1, i1, j1);
/* 216 */       drawGradientRect(l1 - 3, i2 - 3, l1 + i + 3, i2 - 3 + 1, i1, i1);
/* 217 */       drawGradientRect(l1 - 3, i2 + k + 2, l1 + i + 3, i2 + k + 3, j1, j1);
/*     */       
/* 219 */       for (int k1 = 0; k1 < textLines.size(); k1++) {
/* 220 */         String s1 = textLines.get(k1);
/* 221 */         this.fontRendererObj.drawStringWithShadow(s1, l1, i2, -1);
/*     */         
/* 223 */         if (k1 == 0) {
/* 224 */           i2 += 2;
/*     */         }
/*     */         
/* 227 */         i2 += 10;
/*     */       } 
/*     */       
/* 230 */       this.zLevel = 0.0F;
/* 231 */       this.itemRender.zLevel = 0.0F;
/* 232 */       GlStateManager.enableLighting();
/* 233 */       GlStateManager.enableDepth();
/* 234 */       RenderHelper.enableStandardItemLighting();
/* 235 */       GlStateManager.enableRescaleNormal();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void handleComponentHover(IChatComponent chatComponent, int p_175272_2_, int p_175272_3_) {
/* 243 */     if (chatComponent != null && chatComponent.getChatStyle().getChatHoverEvent() != null) {
/* 244 */       HoverEvent hoverevent = chatComponent.getChatStyle().getChatHoverEvent();
/*     */       
/* 246 */       if (hoverevent.getAction() == HoverEvent.Action.SHOW_ITEM) {
/* 247 */         ItemStack itemstack = null;
/*     */         
/*     */         try {
/* 250 */           NBTTagCompound nBTTagCompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */           
/* 252 */           if (nBTTagCompound instanceof NBTTagCompound) {
/* 253 */             itemstack = ItemStack.loadItemStackFromNBT(nBTTagCompound);
/*     */           }
/* 255 */         } catch (NBTException nBTException) {}
/*     */ 
/*     */ 
/*     */         
/* 259 */         if (itemstack != null) {
/* 260 */           renderToolTip(itemstack, p_175272_2_, p_175272_3_);
/*     */         } else {
/* 262 */           drawCreativeTabHoveringText(ChatFormatting.RED + "Invalid Item!", p_175272_2_, p_175272_3_);
/*     */         } 
/* 264 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ENTITY) {
/* 265 */         if (this.mc.gameSettings.advancedItemTooltips) {
/*     */           try {
/* 267 */             NBTTagCompound nBTTagCompound = JsonToNBT.getTagFromJson(hoverevent.getValue().getUnformattedText());
/*     */             
/* 269 */             if (nBTTagCompound instanceof NBTTagCompound) {
/* 270 */               List<String> list1 = Lists.newArrayList();
/* 271 */               NBTTagCompound nbttagcompound = nBTTagCompound;
/* 272 */               list1.add(nbttagcompound.getString("name"));
/*     */               
/* 274 */               if (nbttagcompound.hasKey("type", 8)) {
/* 275 */                 String s = nbttagcompound.getString("type");
/* 276 */                 list1.add("Type: " + s + " (" + EntityList.getIDFromString(s) + ")");
/*     */               } 
/*     */               
/* 279 */               list1.add(nbttagcompound.getString("id"));
/* 280 */               drawHoveringText(list1, p_175272_2_, p_175272_3_);
/*     */             } else {
/* 282 */               drawCreativeTabHoveringText(ChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
/*     */             } 
/* 284 */           } catch (NBTException var10) {
/* 285 */             drawCreativeTabHoveringText(ChatFormatting.RED + "Invalid Entity!", p_175272_2_, p_175272_3_);
/*     */           } 
/*     */         }
/* 288 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_TEXT) {
/* 289 */         drawHoveringText(NEWLINE_SPLITTER.splitToList(hoverevent.getValue().getFormattedText()), p_175272_2_, p_175272_3_);
/* 290 */       } else if (hoverevent.getAction() == HoverEvent.Action.SHOW_ACHIEVEMENT) {
/* 291 */         StatBase statbase = StatList.getOneShotStat(hoverevent.getValue().getUnformattedText());
/*     */         
/* 293 */         if (statbase != null) {
/* 294 */           IChatComponent ichatcomponent = statbase.getStatName();
/* 295 */           ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("stats.tooltip.type." + (statbase.isAchievement() ? "achievement" : "statistic"), new Object[0]);
/* 296 */           chatComponentTranslation.getChatStyle().setItalic(Boolean.TRUE);
/* 297 */           String s1 = (statbase instanceof Achievement) ? ((Achievement)statbase).getDescription() : null;
/* 298 */           List<String> list = Lists.newArrayList((Object[])new String[] { ichatcomponent.getFormattedText(), chatComponentTranslation.getFormattedText() });
/*     */           
/* 300 */           if (s1 != null) {
/* 301 */             list.addAll(this.fontRendererObj.listFormattedStringToWidth(s1, 150));
/*     */           }
/*     */           
/* 304 */           drawHoveringText(list, p_175272_2_, p_175272_3_);
/*     */         } else {
/* 306 */           drawCreativeTabHoveringText(ChatFormatting.RED + "Invalid statistic/achievement!", p_175272_2_, p_175272_3_);
/*     */         } 
/*     */       } 
/*     */       
/* 310 */       GlStateManager.disableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean handleComponentClick(IChatComponent p_175276_1_) {
/* 327 */     if (p_175276_1_ != null) {
/* 328 */       ClickEvent clickevent = p_175276_1_.getChatStyle().getChatClickEvent();
/*     */       
/* 330 */       if (isShiftKeyDown()) {
/* 331 */         if (p_175276_1_.getChatStyle().getInsertion() != null) {
/* 332 */           setText(p_175276_1_.getChatStyle().getInsertion(), false);
/*     */         }
/* 334 */       } else if (clickevent != null) {
/* 335 */         if (clickevent.getAction() == ClickEvent.Action.OPEN_URL) {
/* 336 */           if (!this.mc.gameSettings.chatLinks) {
/* 337 */             return false;
/*     */           }
/*     */           
/*     */           try {
/* 341 */             URI uri = new URI(clickevent.getValue());
/* 342 */             String s = uri.getScheme();
/*     */             
/* 344 */             if (s == null) {
/* 345 */               throw new URISyntaxException(clickevent.getValue(), "Missing protocol");
/*     */             }
/*     */             
/* 348 */             if (!PROTOCOLS.contains(s.toLowerCase())) {
/* 349 */               throw new URISyntaxException(clickevent.getValue(), "Unsupported protocol: " + s.toLowerCase());
/*     */             }
/*     */             
/* 352 */             if (this.mc.gameSettings.chatLinksPrompt) {
/* 353 */               this.clickedLinkURI = uri;
/* 354 */               this.mc.displayGuiScreen(new GuiConfirmOpenLink(this, clickevent.getValue(), 31102009, false));
/*     */             } else {
/* 356 */               openWebLink(uri);
/*     */             } 
/* 358 */           } catch (URISyntaxException urisyntaxexception) {
/* 359 */             LOGGER.error("Can't open url for " + clickevent, urisyntaxexception);
/*     */           } 
/* 361 */         } else if (clickevent.getAction() == ClickEvent.Action.OPEN_FILE) {
/* 362 */           URI uri1 = (new File(clickevent.getValue())).toURI();
/* 363 */           openWebLink(uri1);
/* 364 */         } else if (clickevent.getAction() == ClickEvent.Action.SUGGEST_COMMAND) {
/* 365 */           setText(clickevent.getValue(), true);
/* 366 */         } else if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
/* 367 */           sendChatMessage(clickevent.getValue(), false);
/*     */         } else {
/* 369 */           LOGGER.error("Don't know how to handle " + clickevent);
/*     */         } 
/*     */         
/* 372 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 376 */     return false;
/*     */   }
/*     */   
/*     */   public void sendChatMessage(String msg) {
/* 380 */     sendChatMessage(msg, true);
/*     */   }
/*     */   
/*     */   public void sendChatMessage(String msg, boolean addToChat) {
/* 384 */     if (addToChat) {
/* 385 */       this.mc.ingameGUI.getChatGUI().addToSentMessages(msg);
/*     */     }
/*     */     
/* 388 */     this.mc.thePlayer.sendChatMessage(msg);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 395 */     if (mouseButton == 0) {
/* 396 */       for (GuiButton guibutton : this.buttonList) {
/* 397 */         if (guibutton.mousePressed(this.mc, mouseX, mouseY)) {
/* 398 */           this.selectedButton = guibutton;
/* 399 */           guibutton.playPressSound(this.mc.getSoundHandler());
/* 400 */           actionPerformed(guibutton);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 410 */     if (this.selectedButton != null && state == 0) {
/* 411 */       this.selectedButton.mouseReleased(mouseX, mouseY);
/* 412 */       this.selectedButton = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldAndResolution(Minecraft mc, int width, int height) {
/* 434 */     this.mc = mc;
/* 435 */     this.itemRender = mc.getRenderItem();
/* 436 */     this.fontRendererObj = mc.MCfontRenderer;
/* 437 */     this.width = width;
/* 438 */     this.height = height;
/* 439 */     this.buttonList.clear();
/* 440 */     initGui();
/*     */   }
/*     */   
/*     */   public void func_183500_a(int p_183500_1_, int p_183500_2_) {
/* 444 */     this.width = p_183500_1_;
/* 445 */     this.height = p_183500_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleInput() throws IOException {
/* 459 */     if (Mouse.isCreated()) {
/* 460 */       while (Mouse.next()) {
/* 461 */         handleMouseInput();
/*     */       }
/*     */     }
/*     */     
/* 465 */     if (Keyboard.isCreated()) {
/* 466 */       while (Keyboard.next()) {
/* 467 */         handleKeyboardInput();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 476 */     int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
/* 477 */     int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
/* 478 */     int k = Mouse.getEventButton();
/*     */     
/* 480 */     if (Mouse.getEventButtonState()) {
/* 481 */       if (this.mc.gameSettings.touchscreen && this.touchValue++ > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 485 */       this.eventButton = k;
/* 486 */       this.lastMouseEvent = Minecraft.getSystemTime();
/* 487 */       mouseClicked(i, j, this.eventButton);
/* 488 */     } else if (k != -1) {
/* 489 */       if (this.mc.gameSettings.touchscreen && --this.touchValue > 0) {
/*     */         return;
/*     */       }
/*     */       
/* 493 */       this.eventButton = -1;
/* 494 */       mouseReleased(i, j, k);
/* 495 */     } else if (this.eventButton != -1 && this.lastMouseEvent > 0L) {
/* 496 */       long l = Minecraft.getSystemTime() - this.lastMouseEvent;
/* 497 */       mouseClickMove(i, j, this.eventButton, l);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleKeyboardInput() throws IOException {
/* 505 */     if (Keyboard.getEventKeyState()) {
/* 506 */       keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
/*     */     }
/*     */     
/* 509 */     this.mc.dispatchKeypresses();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawDefaultBackground() {
/* 528 */     drawWorldBackground(0);
/*     */   }
/*     */   
/*     */   public void drawWorldBackground(int tint) {
/* 532 */     if (this.mc.theWorld != null) {
/* 533 */       drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
/*     */     } else {
/* 535 */       drawBackground(tint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawBackground(int tint) {
/* 543 */     GlStateManager.disableLighting();
/* 544 */     GlStateManager.disableFog();
/* 545 */     Tessellator tessellator = Tessellator.getInstance();
/* 546 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 547 */     this.mc.getTextureManager().bindTexture(optionsBackground);
/* 548 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 549 */     float f = 32.0F;
/* 550 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 551 */     worldrenderer.pos(0.0D, this.height, 0.0D).tex(0.0D, (this.height / 32.0F + tint)).func_181669_b(64, 64, 64, 255).endVertex();
/* 552 */     worldrenderer.pos(this.width, this.height, 0.0D).tex((this.width / 32.0F), (this.height / 32.0F + tint)).func_181669_b(64, 64, 64, 255).endVertex();
/* 553 */     worldrenderer.pos(this.width, 0.0D, 0.0D).tex((this.width / 32.0F), tint).func_181669_b(64, 64, 64, 255).endVertex();
/* 554 */     worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, tint).func_181669_b(64, 64, 64, 255).endVertex();
/* 555 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 562 */     return true;
/*     */   }
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 566 */     if (id == 31102009) {
/* 567 */       if (result) {
/* 568 */         openWebLink(this.clickedLinkURI);
/*     */       }
/*     */       
/* 571 */       this.clickedLinkURI = null;
/* 572 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void openWebLink(URI p_175282_1_) {
/*     */     try {
/* 578 */       Class<?> oclass = Class.forName("java.awt.Desktop");
/* 579 */       Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 580 */       oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { p_175282_1_ });
/* 581 */     } catch (Throwable throwable) {
/* 582 */       LOGGER.error("Couldn't open link", throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isCtrlKeyDown() {
/* 590 */     return Minecraft.isRunningOnMac ? ((Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220))) : ((Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isShiftKeyDown() {
/* 597 */     return (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAltKeyDown() {
/* 604 */     return (Keyboard.isKeyDown(56) || Keyboard.isKeyDown(184));
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlX(int p_175277_0_) {
/* 608 */     return (p_175277_0_ == 45 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlV(int p_175279_0_) {
/* 612 */     return (p_175279_0_ == 47 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlC(int p_175280_0_) {
/* 616 */     return (p_175280_0_ == 46 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */   
/*     */   public static boolean isKeyComboCtrlA(int p_175278_0_) {
/* 620 */     return (p_175278_0_ == 30 && isCtrlKeyDown() && !isShiftKeyDown() && !isAltKeyDown());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onResize(Minecraft mcIn, int p_175273_2_, int p_175273_3_) {
/* 627 */     setWorldAndResolution(mcIn, p_175273_2_, p_175273_3_);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiScreen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */