/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import cc.slack.Slack;
/*     */ import cc.slack.ui.alt.GuiAltLogin;
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URI;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomPanorama;
/*     */ import net.optifine.CustomPanoramaProperties;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import org.lwjgl.util.glu.Project;
/*     */ 
/*     */ public class GuiMainMenu
/*     */   extends GuiScreen implements GuiYesNoCallback {
/*  39 */   private static final AtomicInteger field_175373_f = new AtomicInteger(0);
/*  40 */   private static final Logger logger = LogManager.getLogger();
/*  41 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */ 
/*     */   
/*     */   private float updateCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   private String splashText;
/*     */ 
/*     */   
/*     */   private int panoramaTimer;
/*     */ 
/*     */   
/*     */   private DynamicTexture viewportTexture;
/*     */ 
/*     */   
/*     */   private boolean field_175375_v = true;
/*     */ 
/*     */   
/*  61 */   private final Object threadLock = new Object();
/*     */ 
/*     */   
/*     */   private String openGLWarning1;
/*     */ 
/*     */   
/*     */   private String openGLWarning2;
/*     */   
/*     */   private String openGLWarningLink;
/*     */   
/*  71 */   private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
/*  72 */   private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");
/*     */ 
/*     */   
/*  75 */   private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] { new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  82 */   public static final String field_96138_a = "Please click " + ChatFormatting.UNDERLINE + "here" + ChatFormatting.RESET + " for more information.";
/*     */   
/*     */   private int field_92024_r;
/*     */   
/*     */   private int field_92023_s;
/*     */   
/*     */   private int field_92022_t;
/*     */   private int field_92021_u;
/*     */   private int field_92020_v;
/*     */   private int field_92019_w;
/*     */   private ResourceLocation backgroundTexture;
/*     */   private GuiButton realmsButton;
/*     */   private boolean field_183502_L;
/*     */   private GuiScreen field_183503_M;
/*     */   private GuiButton modButton;
/*     */   private GuiScreen modUpdateNotification;
/*     */   
/*     */   public GuiMainMenu() {
/* 100 */     this.openGLWarning2 = field_96138_a;
/* 101 */     this.field_183502_L = false;
/* 102 */     this.splashText = "missingno";
/* 103 */     BufferedReader bufferedreader = null;
/*     */ 
/*     */     
/*     */     try {
/* 107 */       List<String> list = Lists.newArrayList();
/* 108 */       bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
/*     */       
/*     */       String s;
/* 111 */       while ((s = bufferedreader.readLine()) != null) {
/*     */         
/* 113 */         s = s.trim();
/*     */         
/* 115 */         if (!s.isEmpty())
/*     */         {
/* 117 */           list.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 121 */       if (!list.isEmpty()) {
/*     */         do
/*     */         {
/*     */           
/* 125 */           this.splashText = list.get(RANDOM.nextInt(list.size()));
/*     */         }
/* 127 */         while (this.splashText.hashCode() == 125780783);
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 134 */     catch (IOException iOException) {
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 140 */       if (bufferedreader != null) {
/*     */         
/*     */         try {
/*     */           
/* 144 */           bufferedreader.close();
/*     */         }
/* 146 */         catch (IOException iOException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     this.updateCounter = RANDOM.nextFloat();
/* 154 */     this.openGLWarning1 = "";
/*     */     
/* 156 */     if (!(GLContext.getCapabilities()).OpenGL20 && !OpenGlHelper.areShadersSupported()) {
/*     */       
/* 158 */       this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
/* 159 */       this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
/* 160 */       this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/* 169 */     this.panoramaTimer++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 177 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/* 194 */     this.viewportTexture = new DynamicTexture(256, 256);
/* 195 */     this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
/* 196 */     Calendar calendar = Calendar.getInstance();
/* 197 */     calendar.setTime(new Date());
/*     */     
/* 199 */     if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
/*     */       
/* 201 */       this.splashText = "Merry X-mas!";
/*     */     }
/* 203 */     else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
/*     */       
/* 205 */       this.splashText = "Happy new year!";
/*     */     }
/* 207 */     else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
/*     */       
/* 209 */       this.splashText = "OOoooOOOoooo! Spooky!";
/*     */     } 
/*     */     
/* 212 */     int i = 24;
/* 213 */     int j = this.height / 4 + 48;
/*     */     
/* 215 */     addSingleplayerMultiplayerButtons(j, 24);
/*     */     
/* 217 */     this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options", new Object[0])));
/* 218 */     this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit", new Object[0])));
/* 219 */     this.buttonList.add(new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12));
/*     */     
/* 221 */     synchronized (this.threadLock) {
/*     */       
/* 223 */       this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
/* 224 */       this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
/* 225 */       int k = Math.max(this.field_92023_s, this.field_92024_r);
/* 226 */       this.field_92022_t = (this.width - k) / 2;
/* 227 */       this.field_92021_u = ((GuiButton)this.buttonList.get(0)).yPosition - 24;
/* 228 */       this.field_92020_v = this.field_92022_t + k;
/* 229 */       this.field_92019_w = this.field_92021_u + 24;
/*     */     } 
/*     */     
/* 232 */     this.mc.func_181537_a(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
/* 240 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, p_73969_1_, I18n.format("menu.singleplayer", new Object[0])));
/* 241 */     this.buttonList.add(new GuiButton(2, this.width / 2 - 100, p_73969_1_ + p_73969_2_, I18n.format("menu.multiplayer", new Object[0])));
/* 242 */     this.buttonList.add(new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, "Alt Manager"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/* 250 */     if (button.id == 0)
/*     */     {
/* 252 */       this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
/*     */     }
/*     */     
/* 255 */     if (button.id == 5)
/*     */     {
/* 257 */       this.mc.displayGuiScreen(new GuiLanguage(this, this.mc.gameSettings, this.mc.getLanguageManager()));
/*     */     }
/*     */     
/* 260 */     if (button.id == 1)
/*     */     {
/* 262 */       this.mc.displayGuiScreen(new GuiSelectWorld(this));
/*     */     }
/*     */     
/* 265 */     if (button.id == 2)
/*     */     {
/* 267 */       this.mc.displayGuiScreen(new GuiMultiplayer(this));
/*     */     }
/*     */     
/* 270 */     if (button.id == 4)
/*     */     {
/* 272 */       this.mc.shutdown();
/*     */     }
/*     */     
/* 275 */     if (button.id == 6)
/*     */     {
/* 277 */       this.mc.displayGuiScreen((GuiScreen)new GuiAltLogin(this));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void confirmClicked(boolean result, int id) {
/* 283 */     if (id == 13) {
/*     */       
/* 285 */       if (result) {
/*     */         
/*     */         try {
/*     */           
/* 289 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 290 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 291 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { new URI(this.openGLWarningLink) });
/*     */         }
/* 293 */         catch (Throwable throwable) {
/*     */           
/* 295 */           logger.error("Couldn't open link", throwable);
/*     */         } 
/*     */       }
/*     */       
/* 299 */       this.mc.displayGuiScreen(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_) {
/* 308 */     Tessellator tessellator = Tessellator.getInstance();
/* 309 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 310 */     GlStateManager.matrixMode(5889);
/* 311 */     GlStateManager.pushMatrix();
/* 312 */     GlStateManager.loadIdentity();
/* 313 */     Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
/* 314 */     GlStateManager.matrixMode(5888);
/* 315 */     GlStateManager.pushMatrix();
/* 316 */     GlStateManager.loadIdentity();
/* 317 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 318 */     GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/* 319 */     GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 320 */     GlStateManager.enableBlend();
/* 321 */     GlStateManager.disableAlpha();
/* 322 */     GlStateManager.disableCull();
/* 323 */     GlStateManager.depthMask(false);
/* 324 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 325 */     int i = 8;
/* 326 */     int j = 64;
/* 327 */     CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
/*     */     
/* 329 */     if (custompanoramaproperties != null)
/*     */     {
/* 331 */       j = custompanoramaproperties.getBlur1();
/*     */     }
/*     */     
/* 334 */     for (int k = 0; k < j; k++) {
/*     */       
/* 336 */       GlStateManager.pushMatrix();
/* 337 */       float f = ((k % i) / i - 0.5F) / 64.0F;
/* 338 */       float f1 = ((k / i) / i - 0.5F) / 64.0F;
/* 339 */       float f2 = 0.0F;
/* 340 */       GlStateManager.translate(f, f1, f2);
/* 341 */       GlStateManager.rotate(MathHelper.sin((this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
/* 342 */       GlStateManager.rotate(-(this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);
/*     */       
/* 344 */       for (int l = 0; l < 6; l++) {
/*     */         
/* 346 */         GlStateManager.pushMatrix();
/*     */         
/* 348 */         if (l == 1)
/*     */         {
/* 350 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 353 */         if (l == 2)
/*     */         {
/* 355 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 358 */         if (l == 3)
/*     */         {
/* 360 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 363 */         if (l == 4)
/*     */         {
/* 365 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 368 */         if (l == 5)
/*     */         {
/* 370 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 373 */         ResourceLocation[] aresourcelocation = titlePanoramaPaths;
/*     */         
/* 375 */         if (custompanoramaproperties != null)
/*     */         {
/* 377 */           aresourcelocation = custompanoramaproperties.getPanoramaLocations();
/*     */         }
/*     */         
/* 380 */         this.mc.getTextureManager().bindTexture(aresourcelocation[l]);
/* 381 */         worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 382 */         int i1 = 255 / (k + 1);
/* 383 */         float f3 = 0.0F;
/* 384 */         worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).func_181669_b(255, 255, 255, i1).endVertex();
/* 385 */         worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).func_181669_b(255, 255, 255, i1).endVertex();
/* 386 */         worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).func_181669_b(255, 255, 255, i1).endVertex();
/* 387 */         worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).func_181669_b(255, 255, 255, i1).endVertex();
/* 388 */         tessellator.draw();
/* 389 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 392 */       GlStateManager.popMatrix();
/* 393 */       GlStateManager.colorMask(true, true, true, false);
/*     */     } 
/*     */     
/* 396 */     worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
/* 397 */     GlStateManager.colorMask(true, true, true, true);
/* 398 */     GlStateManager.matrixMode(5889);
/* 399 */     GlStateManager.popMatrix();
/* 400 */     GlStateManager.matrixMode(5888);
/* 401 */     GlStateManager.popMatrix();
/* 402 */     GlStateManager.depthMask(true);
/* 403 */     GlStateManager.enableCull();
/* 404 */     GlStateManager.enableDepth();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateAndBlurSkybox(float p_73968_1_) {
/* 412 */     this.mc.getTextureManager().bindTexture(this.backgroundTexture);
/* 413 */     GL11.glTexParameteri(3553, 10241, 9729);
/* 414 */     GL11.glTexParameteri(3553, 10240, 9729);
/* 415 */     GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
/* 416 */     GlStateManager.enableBlend();
/* 417 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 418 */     GlStateManager.colorMask(true, true, true, false);
/* 419 */     Tessellator tessellator = Tessellator.getInstance();
/* 420 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 421 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 422 */     GlStateManager.disableAlpha();
/* 423 */     int i = 3;
/* 424 */     int j = 3;
/* 425 */     CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
/*     */     
/* 427 */     if (custompanoramaproperties != null)
/*     */     {
/* 429 */       j = custompanoramaproperties.getBlur2();
/*     */     }
/*     */     
/* 432 */     for (int k = 0; k < j; k++) {
/*     */       
/* 434 */       float f = 1.0F / (k + 1);
/* 435 */       int l = this.width;
/* 436 */       int i1 = this.height;
/* 437 */       float f1 = (k - i / 2) / 256.0F;
/* 438 */       worldrenderer.pos(l, i1, this.zLevel).tex((0.0F + f1), 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).endVertex();
/* 439 */       worldrenderer.pos(l, 0.0D, this.zLevel).tex((1.0F + f1), 1.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).endVertex();
/* 440 */       worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex((1.0F + f1), 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).endVertex();
/* 441 */       worldrenderer.pos(0.0D, i1, this.zLevel).tex((0.0F + f1), 0.0D).func_181666_a(1.0F, 1.0F, 1.0F, f).endVertex();
/*     */     } 
/*     */     
/* 444 */     tessellator.draw();
/* 445 */     GlStateManager.enableAlpha();
/* 446 */     GlStateManager.colorMask(true, true, true, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_) {
/* 454 */     this.mc.getFramebuffer().unbindFramebuffer();
/* 455 */     GlStateManager.viewport(0, 0, 256, 256);
/* 456 */     drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
/* 457 */     rotateAndBlurSkybox(p_73971_3_);
/* 458 */     int i = 3;
/* 459 */     CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
/*     */     
/* 461 */     if (custompanoramaproperties != null)
/*     */     {
/* 463 */       i = custompanoramaproperties.getBlur3();
/*     */     }
/*     */     
/* 466 */     for (int j = 0; j < i; j++) {
/*     */       
/* 468 */       rotateAndBlurSkybox(p_73971_3_);
/* 469 */       rotateAndBlurSkybox(p_73971_3_);
/*     */     } 
/*     */     
/* 472 */     this.mc.getFramebuffer().bindFramebuffer(true);
/* 473 */     GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 474 */     float f2 = (this.width > this.height) ? (120.0F / this.width) : (120.0F / this.height);
/* 475 */     float f = this.height * f2 / 256.0F;
/* 476 */     float f1 = this.width * f2 / 256.0F;
/* 477 */     int k = this.width;
/* 478 */     int l = this.height;
/* 479 */     Tessellator tessellator = Tessellator.getInstance();
/* 480 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 481 */     worldrenderer.begin(7, DefaultVertexFormats.field_181709_i);
/* 482 */     worldrenderer.pos(0.0D, l, this.zLevel).tex((0.5F - f), (0.5F + f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 483 */     worldrenderer.pos(k, l, this.zLevel).tex((0.5F - f), (0.5F - f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 484 */     worldrenderer.pos(k, 0.0D, this.zLevel).tex((0.5F + f), (0.5F - f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 485 */     worldrenderer.pos(0.0D, 0.0D, this.zLevel).tex((0.5F + f), (0.5F + f1)).func_181666_a(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
/* 486 */     tessellator.draw();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 494 */     GlStateManager.disableAlpha();
/* 495 */     renderSkybox(mouseX, mouseY, partialTicks);
/* 496 */     GlStateManager.enableAlpha();
/* 497 */     Tessellator tessellator = Tessellator.getInstance();
/* 498 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 499 */     int i = 274;
/* 500 */     int j = this.width / 2 - i / 2;
/* 501 */     int k = 30;
/* 502 */     int l = -2130706433;
/* 503 */     int i1 = 16777215;
/* 504 */     int j1 = 0;
/* 505 */     int k1 = Integer.MIN_VALUE;
/* 506 */     CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();
/*     */     
/* 508 */     if (custompanoramaproperties != null) {
/*     */       
/* 510 */       l = custompanoramaproperties.getOverlay1Top();
/* 511 */       i1 = custompanoramaproperties.getOverlay1Bottom();
/* 512 */       j1 = custompanoramaproperties.getOverlay2Top();
/* 513 */       k1 = custompanoramaproperties.getOverlay2Bottom();
/*     */     } 
/*     */     
/* 516 */     if (l != 0 || i1 != 0)
/*     */     {
/* 518 */       drawGradientRect(0, 0, this.width, this.height, l, i1);
/*     */     }
/*     */     
/* 521 */     if (j1 != 0 || k1 != 0)
/*     */     {
/* 523 */       drawGradientRect(0, 0, this.width, this.height, j1, k1);
/*     */     }
/*     */     
/* 526 */     this.mc.getTextureManager().bindTexture(minecraftTitleTextures);
/* 527 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 529 */     if (this.updateCounter < 1.0E-4D) {
/*     */       
/* 531 */       drawTexturedModalRect(j + 0, k + 0, 0, 0, 99, 44);
/* 532 */       drawTexturedModalRect(j + 99, k + 0, 129, 0, 27, 44);
/* 533 */       drawTexturedModalRect(j + 99 + 26, k + 0, 126, 0, 3, 44);
/* 534 */       drawTexturedModalRect(j + 99 + 26 + 3, k + 0, 99, 0, 26, 44);
/* 535 */       drawTexturedModalRect(j + 155, k + 0, 0, 45, 155, 44);
/*     */     }
/*     */     else {
/*     */       
/* 539 */       drawTexturedModalRect(j + 0, k + 0, 0, 0, 155, 44);
/* 540 */       drawTexturedModalRect(j + 155, k + 0, 0, 45, 155, 44);
/*     */     } 
/*     */ 
/*     */     
/* 544 */     String s = (Slack.getInstance()).info.getName() + " " + (Slack.getInstance()).info.getVersion() + " | " + (Slack.getInstance()).info.getType() + " Build";
/*     */     
/* 546 */     if (Reflector.FMLCommonHandler_getBrandings.exists()) {
/*     */       
/* 548 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/* 549 */       List<String> list = Lists.reverse((List)Reflector.call(object, Reflector.FMLCommonHandler_getBrandings, new Object[] { Boolean.TRUE }));
/*     */       
/* 551 */       for (int l1 = 0; l1 < list.size(); l1++) {
/*     */         
/* 553 */         String s1 = list.get(l1);
/*     */         
/* 555 */         if (!Strings.isNullOrEmpty(s1))
/*     */         {
/* 557 */           drawString(this.fontRendererObj, s1, 2, this.height - 10 + l1 * (this.fontRendererObj.FONT_HEIGHT + 1), 16777215);
/*     */         }
/*     */       } 
/*     */       
/* 561 */       if (Reflector.ForgeHooksClient_renderMainMenu.exists())
/*     */       {
/* 563 */         Reflector.call(Reflector.ForgeHooksClient_renderMainMenu, new Object[] { this, this.fontRendererObj, Integer.valueOf(this.width), Integer.valueOf(this.height) });
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 568 */       drawString(this.fontRendererObj, s, 2, this.height - 10, -1);
/*     */     } 
/*     */     
/* 571 */     Slack.getInstance().getClass(); String s2 = "Release v0.01:\r\n-Added all modules (56)\r\n-Added SexModule";
/* 572 */     drawString(this.fontRendererObj, s2, 2, 20, -1);
/*     */     
/* 574 */     if (this.openGLWarning1 != null && this.openGLWarning1.length() > 0) {
/*     */       
/* 576 */       drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 1428160512);
/* 577 */       drawString(this.fontRendererObj, this.openGLWarning1, this.field_92022_t, this.field_92021_u, -1);
/* 578 */       drawString(this.fontRendererObj, this.openGLWarning2, (this.width - this.field_92024_r) / 2, ((GuiButton)this.buttonList.get(0)).yPosition - 12, -1);
/*     */     } 
/*     */     
/* 581 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */     
/* 583 */     if (this.modUpdateNotification != null)
/*     */     {
/* 585 */       this.modUpdateNotification.drawScreen(mouseX, mouseY, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 594 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */     
/* 596 */     synchronized (this.threadLock) {
/*     */       
/* 598 */       if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
/*     */         
/* 600 */         GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
/* 601 */         guiconfirmopenlink.disableSecurityWarning();
/* 602 */         this.mc.displayGuiScreen(guiconfirmopenlink);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/* 612 */     if (this.field_183503_M != null)
/*     */     {
/* 614 */       this.field_183503_M.onGuiClosed();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\gui\GuiMainMenu.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */