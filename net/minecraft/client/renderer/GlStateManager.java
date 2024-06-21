/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import net.minecraft.src.Config;
/*      */ import net.optifine.SmartAnimations;
/*      */ import net.optifine.render.GlAlphaState;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.util.LockCounter;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ 
/*      */ 
/*      */ public class GlStateManager
/*      */ {
/*   18 */   private static AlphaState alphaState = new AlphaState();
/*   19 */   private static BooleanState lightingState = new BooleanState(2896);
/*   20 */   private static BooleanState[] lightState = new BooleanState[8];
/*   21 */   private static ColorMaterialState colorMaterialState = new ColorMaterialState();
/*   22 */   private static BlendState blendState = new BlendState();
/*   23 */   private static DepthState depthState = new DepthState();
/*   24 */   private static FogState fogState = new FogState();
/*   25 */   private static CullState cullState = new CullState();
/*   26 */   private static PolygonOffsetState polygonOffsetState = new PolygonOffsetState();
/*   27 */   private static ColorLogicState colorLogicState = new ColorLogicState();
/*   28 */   private static TexGenState texGenState = new TexGenState();
/*   29 */   private static ClearState clearState = new ClearState();
/*   30 */   private static StencilState stencilState = new StencilState();
/*   31 */   private static BooleanState normalizeState = new BooleanState(2977);
/*   32 */   private static int activeTextureUnit = 0;
/*   33 */   private static TextureState[] textureState = new TextureState[32];
/*   34 */   private static int activeShadeModel = 7425;
/*   35 */   private static BooleanState rescaleNormalState = new BooleanState(32826);
/*   36 */   private static ColorMask colorMaskState = new ColorMask();
/*   37 */   private static Color colorState = new Color();
/*      */   public static boolean clearEnabled = true;
/*   39 */   private static LockCounter alphaLock = new LockCounter();
/*   40 */   private static GlAlphaState alphaLockState = new GlAlphaState();
/*   41 */   private static LockCounter blendLock = new LockCounter();
/*   42 */   private static GlBlendState blendLockState = new GlBlendState();
/*      */   
/*      */   private static boolean creatingDisplayList = false;
/*      */   
/*      */   public static void pushAttrib() {
/*   47 */     GL11.glPushAttrib(8256);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popAttrib() {
/*   52 */     GL11.glPopAttrib();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableAlpha() {
/*   57 */     if (alphaLock.isLocked()) {
/*      */       
/*   59 */       alphaLockState.setDisabled();
/*      */     }
/*      */     else {
/*      */       
/*   63 */       alphaState.field_179208_a.setDisabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableAlpha() {
/*   69 */     if (alphaLock.isLocked()) {
/*      */       
/*   71 */       alphaLockState.setEnabled();
/*      */     }
/*      */     else {
/*      */       
/*   75 */       alphaState.field_179208_a.setEnabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void alphaFunc(int func, float ref) {
/*   81 */     if (alphaLock.isLocked()) {
/*      */       
/*   83 */       alphaLockState.setFuncRef(func, ref);
/*      */ 
/*      */     
/*      */     }
/*   87 */     else if (func != alphaState.func || ref != alphaState.ref) {
/*      */       
/*   89 */       alphaState.func = func;
/*   90 */       alphaState.ref = ref;
/*   91 */       GL11.glAlphaFunc(func, ref);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void enableLighting() {
/*   98 */     lightingState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLighting() {
/*  103 */     lightingState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLight(int light) {
/*  108 */     lightState[light].setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLight(int light) {
/*  113 */     lightState[light].setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableColorMaterial() {
/*  118 */     colorMaterialState.field_179191_a.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableColorMaterial() {
/*  123 */     colorMaterialState.field_179191_a.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorMaterial(int face, int mode) {
/*  128 */     if (face != colorMaterialState.field_179189_b || mode != colorMaterialState.field_179190_c) {
/*      */       
/*  130 */       colorMaterialState.field_179189_b = face;
/*  131 */       colorMaterialState.field_179190_c = mode;
/*  132 */       GL11.glColorMaterial(face, mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableDepth() {
/*  138 */     depthState.depthTest.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableDepth() {
/*  143 */     depthState.depthTest.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthFunc(int depthFunc) {
/*  148 */     if (depthFunc != depthState.depthFunc) {
/*      */       
/*  150 */       depthState.depthFunc = depthFunc;
/*  151 */       GL11.glDepthFunc(depthFunc);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void depthMask(boolean flagIn) {
/*  157 */     if (flagIn != depthState.maskEnabled) {
/*      */       
/*  159 */       depthState.maskEnabled = flagIn;
/*  160 */       GL11.glDepthMask(flagIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableBlend() {
/*  166 */     if (blendLock.isLocked()) {
/*      */       
/*  168 */       blendLockState.setDisabled();
/*      */     }
/*      */     else {
/*      */       
/*  172 */       blendState.field_179213_a.setDisabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableBlend() {
/*  178 */     if (blendLock.isLocked()) {
/*      */       
/*  180 */       blendLockState.setEnabled();
/*      */     }
/*      */     else {
/*      */       
/*  184 */       blendState.field_179213_a.setEnabled();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void blendFunc(int srcFactor, int dstFactor) {
/*  190 */     if (blendLock.isLocked()) {
/*      */       
/*  192 */       blendLockState.setFactors(srcFactor, dstFactor);
/*      */ 
/*      */     
/*      */     }
/*  196 */     else if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactor != blendState.srcFactorAlpha || dstFactor != blendState.dstFactorAlpha) {
/*      */       
/*  198 */       blendState.srcFactor = srcFactor;
/*  199 */       blendState.dstFactor = dstFactor;
/*  200 */       blendState.srcFactorAlpha = srcFactor;
/*  201 */       blendState.dstFactorAlpha = dstFactor;
/*      */       
/*  203 */       if (Config.isShaders())
/*      */       {
/*  205 */         Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactor, dstFactor);
/*      */       }
/*      */       
/*  208 */       GL11.glBlendFunc(srcFactor, dstFactor);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
/*  215 */     if (blendLock.isLocked()) {
/*      */       
/*  217 */       blendLockState.setFactors(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */ 
/*      */     
/*      */     }
/*  221 */     else if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactorAlpha != blendState.srcFactorAlpha || dstFactorAlpha != blendState.dstFactorAlpha) {
/*      */       
/*  223 */       blendState.srcFactor = srcFactor;
/*  224 */       blendState.dstFactor = dstFactor;
/*  225 */       blendState.srcFactorAlpha = srcFactorAlpha;
/*  226 */       blendState.dstFactorAlpha = dstFactorAlpha;
/*      */       
/*  228 */       if (Config.isShaders())
/*      */       {
/*  230 */         Shaders.uniform_blendFunc.setValue(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */       }
/*      */       
/*  233 */       OpenGlHelper.glBlendFunc(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/*  240 */     fogState.field_179049_a.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableFog() {
/*  245 */     fogState.field_179049_a.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFog(int param) {
/*  250 */     if (param != fogState.field_179047_b) {
/*      */       
/*  252 */       fogState.field_179047_b = param;
/*  253 */       GL11.glFogi(2917, param);
/*      */       
/*  255 */       if (Config.isShaders())
/*      */       {
/*  257 */         Shaders.setFogMode(param);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogDensity(float param) {
/*  264 */     if (param < 0.0F)
/*      */     {
/*  266 */       param = 0.0F;
/*      */     }
/*      */     
/*  269 */     if (param != fogState.field_179048_c) {
/*      */       
/*  271 */       fogState.field_179048_c = param;
/*  272 */       GL11.glFogf(2914, param);
/*      */       
/*  274 */       if (Config.isShaders())
/*      */       {
/*  276 */         Shaders.setFogDensity(param);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogStart(float param) {
/*  283 */     if (param != fogState.field_179045_d) {
/*      */       
/*  285 */       fogState.field_179045_d = param;
/*  286 */       GL11.glFogf(2915, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogEnd(float param) {
/*  292 */     if (param != fogState.field_179046_e) {
/*      */       
/*  294 */       fogState.field_179046_e = param;
/*  295 */       GL11.glFogf(2916, param);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glFog(int p_glFog_0_, FloatBuffer p_glFog_1_) {
/*  301 */     GL11.glFog(p_glFog_0_, p_glFog_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glFogi(int p_glFogi_0_, int p_glFogi_1_) {
/*  306 */     GL11.glFogi(p_glFogi_0_, p_glFogi_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableCull() {
/*  311 */     cullState.field_179054_a.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableCull() {
/*  316 */     cullState.field_179054_a.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void cullFace(int mode) {
/*  321 */     if (mode != cullState.field_179053_b) {
/*      */       
/*  323 */       cullState.field_179053_b = mode;
/*  324 */       GL11.glCullFace(mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enablePolygonOffset() {
/*  330 */     polygonOffsetState.field_179044_a.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disablePolygonOffset() {
/*  335 */     polygonOffsetState.field_179044_a.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void doPolygonOffset(float factor, float units) {
/*  340 */     if (factor != polygonOffsetState.field_179043_c || units != polygonOffsetState.field_179041_d) {
/*      */       
/*  342 */       polygonOffsetState.field_179043_c = factor;
/*  343 */       polygonOffsetState.field_179041_d = units;
/*  344 */       GL11.glPolygonOffset(factor, units);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableColorLogic() {
/*  350 */     colorLogicState.field_179197_a.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableColorLogic() {
/*  355 */     colorLogicState.field_179197_a.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorLogicOp(int opcode) {
/*  360 */     if (opcode != colorLogicState.field_179196_b) {
/*      */       
/*  362 */       colorLogicState.field_179196_b = opcode;
/*  363 */       GL11.glLogicOp(opcode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexGenCoord(TexGen p_179087_0_) {
/*  369 */     (texGenCoord(p_179087_0_)).field_179067_a.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexGenCoord(TexGen p_179100_0_) {
/*  374 */     (texGenCoord(p_179100_0_)).field_179067_a.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void texGen(TexGen p_179149_0_, int p_179149_1_) {
/*  379 */     TexGenCoord glstatemanager$texgencoord = texGenCoord(p_179149_0_);
/*      */     
/*  381 */     if (p_179149_1_ != glstatemanager$texgencoord.field_179066_c) {
/*      */       
/*  383 */       glstatemanager$texgencoord.field_179066_c = p_179149_1_;
/*  384 */       GL11.glTexGeni(glstatemanager$texgencoord.field_179065_b, 9472, p_179149_1_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_179105_a(TexGen p_179105_0_, int pname, FloatBuffer params) {
/*  390 */     GL11.glTexGen((texGenCoord(p_179105_0_)).field_179065_b, pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   private static TexGenCoord texGenCoord(TexGen p_179125_0_) {
/*  395 */     switch (p_179125_0_) {
/*      */       
/*      */       case S:
/*  398 */         return texGenState.field_179064_a;
/*      */       
/*      */       case T:
/*  401 */         return texGenState.field_179062_b;
/*      */       
/*      */       case R:
/*  404 */         return texGenState.field_179063_c;
/*      */       
/*      */       case Q:
/*  407 */         return texGenState.field_179061_d;
/*      */     } 
/*      */     
/*  410 */     return texGenState.field_179064_a;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setActiveTexture(int texture) {
/*  416 */     if (activeTextureUnit != texture - OpenGlHelper.defaultTexUnit) {
/*      */       
/*  418 */       activeTextureUnit = texture - OpenGlHelper.defaultTexUnit;
/*  419 */       OpenGlHelper.setActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexture2D() {
/*  425 */     (textureState[activeTextureUnit]).texture2DState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexture2D() {
/*  430 */     (textureState[activeTextureUnit]).texture2DState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static int generateTexture() {
/*  435 */     return GL11.glGenTextures();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTexture(int texture) {
/*  440 */     if (texture != 0) {
/*      */       
/*  442 */       GL11.glDeleteTextures(texture);
/*      */       
/*  444 */       for (TextureState glstatemanager$texturestate : textureState) {
/*      */         
/*  446 */         if (glstatemanager$texturestate.textureName == texture)
/*      */         {
/*  448 */           glstatemanager$texturestate.textureName = 0;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void bindTexture(int texture) {
/*  456 */     if (texture != (textureState[activeTextureUnit]).textureName) {
/*      */       
/*  458 */       (textureState[activeTextureUnit]).textureName = texture;
/*  459 */       GL11.glBindTexture(3553, texture);
/*      */       
/*  461 */       if (SmartAnimations.isActive())
/*      */       {
/*  463 */         SmartAnimations.textureRendered(texture);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableNormalize() {
/*  470 */     normalizeState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableNormalize() {
/*  475 */     normalizeState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void shadeModel(int mode) {
/*  480 */     if (mode != activeShadeModel) {
/*      */       
/*  482 */       activeShadeModel = mode;
/*  483 */       GL11.glShadeModel(mode);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableRescaleNormal() {
/*  489 */     rescaleNormalState.setEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableRescaleNormal() {
/*  494 */     rescaleNormalState.setDisabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void viewport(int x, int y, int width, int height) {
/*  499 */     GL11.glViewport(x, y, width, height);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
/*  504 */     if (red != colorMaskState.red || green != colorMaskState.green || blue != colorMaskState.blue || alpha != colorMaskState.alpha) {
/*      */       
/*  506 */       colorMaskState.red = red;
/*  507 */       colorMaskState.green = green;
/*  508 */       colorMaskState.blue = blue;
/*  509 */       colorMaskState.alpha = alpha;
/*  510 */       GL11.glColorMask(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearDepth(double depth) {
/*  516 */     if (depth != clearState.field_179205_a) {
/*      */       
/*  518 */       clearState.field_179205_a = depth;
/*  519 */       GL11.glClearDepth(depth);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearColor(float red, float green, float blue, float alpha) {
/*  525 */     if (red != clearState.field_179203_b.red || green != clearState.field_179203_b.green || blue != clearState.field_179203_b.blue || alpha != clearState.field_179203_b.alpha) {
/*      */       
/*  527 */       clearState.field_179203_b.red = red;
/*  528 */       clearState.field_179203_b.green = green;
/*  529 */       clearState.field_179203_b.blue = blue;
/*  530 */       clearState.field_179203_b.alpha = alpha;
/*  531 */       GL11.glClearColor(red, green, blue, alpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clear(int mask) {
/*  537 */     if (clearEnabled)
/*      */     {
/*  539 */       GL11.glClear(mask);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void matrixMode(int mode) {
/*  545 */     GL11.glMatrixMode(mode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadIdentity() {
/*  550 */     GL11.glLoadIdentity();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushMatrix() {
/*  555 */     GL11.glPushMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popMatrix() {
/*  560 */     GL11.glPopMatrix();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getFloat(int pname, FloatBuffer params) {
/*  565 */     GL11.glGetFloat(pname, params);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
/*  570 */     GL11.glOrtho(left, right, bottom, top, zNear, zFar);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void rotate(float angle, float x, float y, float z) {
/*  575 */     GL11.glRotatef(angle, x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scale(float x, float y, float z) {
/*  580 */     GL11.glScalef(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scale(double x, double y, double z) {
/*  585 */     GL11.glScaled(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void translate(float x, float y, float z) {
/*  590 */     GL11.glTranslatef(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void translate(double x, double y, double z) {
/*  595 */     GL11.glTranslated(x, y, z);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void multMatrix(FloatBuffer matrix) {
/*  600 */     GL11.glMultMatrix(matrix);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
/*  605 */     if (colorRed != colorState.red || colorGreen != colorState.green || colorBlue != colorState.blue || colorAlpha != colorState.alpha) {
/*      */       
/*  607 */       colorState.red = colorRed;
/*  608 */       colorState.green = colorGreen;
/*  609 */       colorState.blue = colorBlue;
/*  610 */       colorState.alpha = colorAlpha;
/*  611 */       GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void color(float colorRed, float colorGreen, float colorBlue) {
/*  617 */     color(colorRed, colorGreen, colorBlue, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetColor() {
/*  622 */     colorState.red = colorState.green = colorState.blue = colorState.alpha = -1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glNormalPointer(int p_glNormalPointer_0_, int p_glNormalPointer_1_, ByteBuffer p_glNormalPointer_2_) {
/*  627 */     GL11.glNormalPointer(p_glNormalPointer_0_, p_glNormalPointer_1_, p_glNormalPointer_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, int p_glTexCoordPointer_3_) {
/*  632 */     GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, p_glTexCoordPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexCoordPointer(int p_glTexCoordPointer_0_, int p_glTexCoordPointer_1_, int p_glTexCoordPointer_2_, ByteBuffer p_glTexCoordPointer_3_) {
/*  637 */     GL11.glTexCoordPointer(p_glTexCoordPointer_0_, p_glTexCoordPointer_1_, p_glTexCoordPointer_2_, p_glTexCoordPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, int p_glVertexPointer_3_) {
/*  642 */     GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, p_glVertexPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glVertexPointer(int p_glVertexPointer_0_, int p_glVertexPointer_1_, int p_glVertexPointer_2_, ByteBuffer p_glVertexPointer_3_) {
/*  647 */     GL11.glVertexPointer(p_glVertexPointer_0_, p_glVertexPointer_1_, p_glVertexPointer_2_, p_glVertexPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, int p_glColorPointer_3_) {
/*  652 */     GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, p_glColorPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glColorPointer(int p_glColorPointer_0_, int p_glColorPointer_1_, int p_glColorPointer_2_, ByteBuffer p_glColorPointer_3_) {
/*  657 */     GL11.glColorPointer(p_glColorPointer_0_, p_glColorPointer_1_, p_glColorPointer_2_, p_glColorPointer_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDisableClientState(int p_glDisableClientState_0_) {
/*  662 */     GL11.glDisableClientState(p_glDisableClientState_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEnableClientState(int p_glEnableClientState_0_) {
/*  667 */     GL11.glEnableClientState(p_glEnableClientState_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBegin(int p_glBegin_0_) {
/*  672 */     GL11.glBegin(p_glBegin_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEnd() {
/*  677 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDrawArrays(int p_glDrawArrays_0_, int p_glDrawArrays_1_, int p_glDrawArrays_2_) {
/*  682 */     GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
/*      */     
/*  684 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  686 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  688 */       if (i > 1) {
/*      */         
/*  690 */         for (int j = 1; j < i; j++) {
/*      */           
/*  692 */           Shaders.uniform_instanceId.setValue(j);
/*  693 */           GL11.glDrawArrays(p_glDrawArrays_0_, p_glDrawArrays_1_, p_glDrawArrays_2_);
/*      */         } 
/*      */         
/*  696 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void callList(int list) {
/*  703 */     GL11.glCallList(list);
/*      */     
/*  705 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  707 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  709 */       if (i > 1) {
/*      */         
/*  711 */         for (int j = 1; j < i; j++) {
/*      */           
/*  713 */           Shaders.uniform_instanceId.setValue(j);
/*  714 */           GL11.glCallList(list);
/*      */         } 
/*      */         
/*  717 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void callLists(IntBuffer p_callLists_0_) {
/*  724 */     GL11.glCallLists(p_callLists_0_);
/*      */     
/*  726 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  728 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  730 */       if (i > 1) {
/*      */         
/*  732 */         for (int j = 1; j < i; j++) {
/*      */           
/*  734 */           Shaders.uniform_instanceId.setValue(j);
/*  735 */           GL11.glCallLists(p_callLists_0_);
/*      */         } 
/*      */         
/*  738 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteLists(int p_glDeleteLists_0_, int p_glDeleteLists_1_) {
/*  745 */     GL11.glDeleteLists(p_glDeleteLists_0_, p_glDeleteLists_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glNewList(int p_glNewList_0_, int p_glNewList_1_) {
/*  750 */     GL11.glNewList(p_glNewList_0_, p_glNewList_1_);
/*  751 */     creatingDisplayList = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glEndList() {
/*  756 */     GL11.glEndList();
/*  757 */     creatingDisplayList = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetError() {
/*  762 */     return GL11.glGetError();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexImage2D(int p_glTexImage2D_0_, int p_glTexImage2D_1_, int p_glTexImage2D_2_, int p_glTexImage2D_3_, int p_glTexImage2D_4_, int p_glTexImage2D_5_, int p_glTexImage2D_6_, int p_glTexImage2D_7_, IntBuffer p_glTexImage2D_8_) {
/*  767 */     GL11.glTexImage2D(p_glTexImage2D_0_, p_glTexImage2D_1_, p_glTexImage2D_2_, p_glTexImage2D_3_, p_glTexImage2D_4_, p_glTexImage2D_5_, p_glTexImage2D_6_, p_glTexImage2D_7_, p_glTexImage2D_8_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexSubImage2D(int p_glTexSubImage2D_0_, int p_glTexSubImage2D_1_, int p_glTexSubImage2D_2_, int p_glTexSubImage2D_3_, int p_glTexSubImage2D_4_, int p_glTexSubImage2D_5_, int p_glTexSubImage2D_6_, int p_glTexSubImage2D_7_, IntBuffer p_glTexSubImage2D_8_) {
/*  772 */     GL11.glTexSubImage2D(p_glTexSubImage2D_0_, p_glTexSubImage2D_1_, p_glTexSubImage2D_2_, p_glTexSubImage2D_3_, p_glTexSubImage2D_4_, p_glTexSubImage2D_5_, p_glTexSubImage2D_6_, p_glTexSubImage2D_7_, p_glTexSubImage2D_8_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glCopyTexSubImage2D(int p_glCopyTexSubImage2D_0_, int p_glCopyTexSubImage2D_1_, int p_glCopyTexSubImage2D_2_, int p_glCopyTexSubImage2D_3_, int p_glCopyTexSubImage2D_4_, int p_glCopyTexSubImage2D_5_, int p_glCopyTexSubImage2D_6_, int p_glCopyTexSubImage2D_7_) {
/*  777 */     GL11.glCopyTexSubImage2D(p_glCopyTexSubImage2D_0_, p_glCopyTexSubImage2D_1_, p_glCopyTexSubImage2D_2_, p_glCopyTexSubImage2D_3_, p_glCopyTexSubImage2D_4_, p_glCopyTexSubImage2D_5_, p_glCopyTexSubImage2D_6_, p_glCopyTexSubImage2D_7_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glGetTexImage(int p_glGetTexImage_0_, int p_glGetTexImage_1_, int p_glGetTexImage_2_, int p_glGetTexImage_3_, IntBuffer p_glGetTexImage_4_) {
/*  782 */     GL11.glGetTexImage(p_glGetTexImage_0_, p_glGetTexImage_1_, p_glGetTexImage_2_, p_glGetTexImage_3_, p_glGetTexImage_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexParameterf(int p_glTexParameterf_0_, int p_glTexParameterf_1_, float p_glTexParameterf_2_) {
/*  787 */     GL11.glTexParameterf(p_glTexParameterf_0_, p_glTexParameterf_1_, p_glTexParameterf_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glTexParameteri(int p_glTexParameteri_0_, int p_glTexParameteri_1_, int p_glTexParameteri_2_) {
/*  792 */     GL11.glTexParameteri(p_glTexParameteri_0_, p_glTexParameteri_1_, p_glTexParameteri_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetTexLevelParameteri(int p_glGetTexLevelParameteri_0_, int p_glGetTexLevelParameteri_1_, int p_glGetTexLevelParameteri_2_) {
/*  797 */     return GL11.glGetTexLevelParameteri(p_glGetTexLevelParameteri_0_, p_glGetTexLevelParameteri_1_, p_glGetTexLevelParameteri_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getActiveTextureUnit() {
/*  802 */     return OpenGlHelper.defaultTexUnit + activeTextureUnit;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void bindCurrentTexture() {
/*  807 */     GL11.glBindTexture(3553, (textureState[activeTextureUnit]).textureName);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBoundTexture() {
/*  812 */     return (textureState[activeTextureUnit]).textureName;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkBoundTexture() {
/*  817 */     if (Config.isMinecraftThread()) {
/*      */       
/*  819 */       int i = GL11.glGetInteger(34016);
/*  820 */       int j = GL11.glGetInteger(32873);
/*  821 */       int k = getActiveTextureUnit();
/*  822 */       int l = getBoundTexture();
/*      */       
/*  824 */       if (l > 0)
/*      */       {
/*  826 */         if (i != k || j != l)
/*      */         {
/*  828 */           Config.dbg("checkTexture: act: " + k + ", glAct: " + i + ", tex: " + l + ", glTex: " + j);
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void deleteTextures(IntBuffer p_deleteTextures_0_) {
/*  836 */     p_deleteTextures_0_.rewind();
/*      */     
/*  838 */     while (p_deleteTextures_0_.position() < p_deleteTextures_0_.limit()) {
/*      */       
/*  840 */       int i = p_deleteTextures_0_.get();
/*  841 */       deleteTexture(i);
/*      */     } 
/*      */     
/*  844 */     p_deleteTextures_0_.rewind();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogEnabled() {
/*  849 */     return fogState.field_179049_a.currentState;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogEnabled(boolean p_setFogEnabled_0_) {
/*  854 */     fogState.field_179049_a.setState(p_setFogEnabled_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void lockAlpha(GlAlphaState p_lockAlpha_0_) {
/*  859 */     if (!alphaLock.isLocked()) {
/*      */       
/*  861 */       getAlphaState(alphaLockState);
/*  862 */       setAlphaState(p_lockAlpha_0_);
/*  863 */       alphaLock.lock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void unlockAlpha() {
/*  869 */     if (alphaLock.unlock())
/*      */     {
/*  871 */       setAlphaState(alphaLockState);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getAlphaState(GlAlphaState p_getAlphaState_0_) {
/*  877 */     if (alphaLock.isLocked()) {
/*      */       
/*  879 */       p_getAlphaState_0_.setState(alphaLockState);
/*      */     }
/*      */     else {
/*      */       
/*  883 */       p_getAlphaState_0_.setState(alphaState.field_179208_a.currentState, alphaState.func, alphaState.ref);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setAlphaState(GlAlphaState p_setAlphaState_0_) {
/*  889 */     if (alphaLock.isLocked()) {
/*      */       
/*  891 */       alphaLockState.setState(p_setAlphaState_0_);
/*      */     }
/*      */     else {
/*      */       
/*  895 */       alphaState.field_179208_a.setState(p_setAlphaState_0_.isEnabled());
/*  896 */       alphaFunc(p_setAlphaState_0_.getFunc(), p_setAlphaState_0_.getRef());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void lockBlend(GlBlendState p_lockBlend_0_) {
/*  902 */     if (!blendLock.isLocked()) {
/*      */       
/*  904 */       getBlendState(blendLockState);
/*  905 */       setBlendState(p_lockBlend_0_);
/*  906 */       blendLock.lock();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void unlockBlend() {
/*  912 */     if (blendLock.unlock())
/*      */     {
/*  914 */       setBlendState(blendLockState);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void getBlendState(GlBlendState p_getBlendState_0_) {
/*  920 */     if (blendLock.isLocked()) {
/*      */       
/*  922 */       p_getBlendState_0_.setState(blendLockState);
/*      */     }
/*      */     else {
/*      */       
/*  926 */       p_getBlendState_0_.setState(blendState.field_179213_a.currentState, blendState.srcFactor, blendState.dstFactor, blendState.srcFactorAlpha, blendState.dstFactorAlpha);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setBlendState(GlBlendState p_setBlendState_0_) {
/*  932 */     if (blendLock.isLocked()) {
/*      */       
/*  934 */       blendLockState.setState(p_setBlendState_0_);
/*      */     }
/*      */     else {
/*      */       
/*  938 */       blendState.field_179213_a.setState(p_setBlendState_0_.isEnabled());
/*      */       
/*  940 */       if (!p_setBlendState_0_.isSeparate()) {
/*      */         
/*  942 */         blendFunc(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor());
/*      */       }
/*      */       else {
/*      */         
/*  946 */         tryBlendFuncSeparate(p_setBlendState_0_.getSrcFactor(), p_setBlendState_0_.getDstFactor(), p_setBlendState_0_.getSrcFactorAlpha(), p_setBlendState_0_.getDstFactorAlpha());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glMultiDrawArrays(int p_glMultiDrawArrays_0_, IntBuffer p_glMultiDrawArrays_1_, IntBuffer p_glMultiDrawArrays_2_) {
/*  953 */     GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
/*      */     
/*  955 */     if (Config.isShaders() && !creatingDisplayList) {
/*      */       
/*  957 */       int i = Shaders.activeProgram.getCountInstances();
/*      */       
/*  959 */       if (i > 1) {
/*      */         
/*  961 */         for (int j = 1; j < i; j++) {
/*      */           
/*  963 */           Shaders.uniform_instanceId.setValue(j);
/*  964 */           GL14.glMultiDrawArrays(p_glMultiDrawArrays_0_, p_glMultiDrawArrays_1_, p_glMultiDrawArrays_2_);
/*      */         } 
/*      */         
/*  967 */         Shaders.uniform_instanceId.setValue(0);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/*  974 */     for (int i = 0; i < 8; i++)
/*      */     {
/*  976 */       lightState[i] = new BooleanState(16384 + i);
/*      */     }
/*      */     
/*  979 */     for (int j = 0; j < textureState.length; j++)
/*      */     {
/*  981 */       textureState[j] = new TextureState();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class AlphaState
/*      */   {
/*      */     private AlphaState() {}
/*      */ 
/*      */ 
/*      */     
/*  993 */     public GlStateManager.BooleanState field_179208_a = new GlStateManager.BooleanState(3008);
/*  994 */     public int func = 519;
/*  995 */     public float ref = -1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class BlendState
/*      */   {
/*      */     private BlendState() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1009 */     public GlStateManager.BooleanState field_179213_a = new GlStateManager.BooleanState(3042);
/* 1010 */     public int srcFactor = 1;
/* 1011 */     public int dstFactor = 0;
/* 1012 */     public int srcFactorAlpha = 1;
/* 1013 */     public int dstFactorAlpha = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   static class BooleanState
/*      */   {
/*      */     private final int capability;
/*      */     
/*      */     private boolean currentState = false;
/*      */     
/*      */     public BooleanState(int capabilityIn) {
/* 1024 */       this.capability = capabilityIn;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setDisabled() {
/* 1029 */       setState(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setEnabled() {
/* 1034 */       setState(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setState(boolean state) {
/* 1039 */       if (state != this.currentState) {
/*      */         
/* 1041 */         this.currentState = state;
/*      */         
/* 1043 */         if (state) {
/*      */           
/* 1045 */           GL11.glEnable(this.capability);
/*      */         }
/*      */         else {
/*      */           
/* 1049 */           GL11.glDisable(this.capability);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class ClearState
/*      */   {
/*      */     private ClearState() {}
/*      */ 
/*      */ 
/*      */     
/* 1063 */     public double field_179205_a = 1.0D;
/* 1064 */     public GlStateManager.Color field_179203_b = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
/* 1065 */     public int field_179204_c = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   static class Color
/*      */   {
/* 1071 */     public float red = 1.0F;
/* 1072 */     public float green = 1.0F;
/* 1073 */     public float blue = 1.0F;
/* 1074 */     public float alpha = 1.0F;
/*      */ 
/*      */ 
/*      */     
/*      */     public Color() {}
/*      */ 
/*      */     
/*      */     public Color(float redIn, float greenIn, float blueIn, float alphaIn) {
/* 1082 */       this.red = redIn;
/* 1083 */       this.green = greenIn;
/* 1084 */       this.blue = blueIn;
/* 1085 */       this.alpha = alphaIn;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class ColorLogicState
/*      */   {
/*      */     private ColorLogicState() {}
/*      */ 
/*      */     
/* 1096 */     public GlStateManager.BooleanState field_179197_a = new GlStateManager.BooleanState(3058);
/* 1097 */     public int field_179196_b = 5379;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class ColorMask
/*      */   {
/*      */     private ColorMask() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean red = true;
/*      */ 
/*      */     
/*      */     public boolean green = true;
/*      */ 
/*      */     
/*      */     public boolean blue = true;
/*      */     
/*      */     public boolean alpha = true;
/*      */   }
/*      */ 
/*      */   
/*      */   static class ColorMaterialState
/*      */   {
/*      */     private ColorMaterialState() {}
/*      */ 
/*      */     
/* 1125 */     public GlStateManager.BooleanState field_179191_a = new GlStateManager.BooleanState(2903);
/* 1126 */     public int field_179189_b = 1032;
/* 1127 */     public int field_179190_c = 5634;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class CullState
/*      */   {
/*      */     private CullState() {}
/*      */ 
/*      */ 
/*      */     
/* 1138 */     public GlStateManager.BooleanState field_179054_a = new GlStateManager.BooleanState(2884);
/* 1139 */     public int field_179053_b = 1029;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DepthState
/*      */   {
/*      */     private DepthState() {}
/*      */ 
/*      */ 
/*      */     
/* 1151 */     public GlStateManager.BooleanState depthTest = new GlStateManager.BooleanState(2929);
/*      */     public boolean maskEnabled = true;
/* 1153 */     public int depthFunc = 513;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class FogState
/*      */   {
/*      */     private FogState() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1167 */     public GlStateManager.BooleanState field_179049_a = new GlStateManager.BooleanState(2912);
/* 1168 */     public int field_179047_b = 2048;
/* 1169 */     public float field_179048_c = 1.0F;
/* 1170 */     public float field_179045_d = 0.0F;
/* 1171 */     public float field_179046_e = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class PolygonOffsetState
/*      */   {
/*      */     private PolygonOffsetState() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1184 */     public GlStateManager.BooleanState field_179044_a = new GlStateManager.BooleanState(32823);
/* 1185 */     public GlStateManager.BooleanState field_179042_b = new GlStateManager.BooleanState(10754);
/* 1186 */     public float field_179043_c = 0.0F;
/* 1187 */     public float field_179041_d = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class StencilFunc
/*      */   {
/*      */     private StencilFunc() {}
/*      */ 
/*      */ 
/*      */     
/* 1199 */     public int field_179081_a = 519;
/* 1200 */     public int field_179079_b = 0;
/* 1201 */     public int field_179080_c = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class StencilState
/*      */   {
/*      */     private StencilState() {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1215 */     public GlStateManager.StencilFunc field_179078_a = new GlStateManager.StencilFunc();
/* 1216 */     public int field_179076_b = -1;
/* 1217 */     public int field_179077_c = 7680;
/* 1218 */     public int field_179074_d = 7680;
/* 1219 */     public int field_179075_e = 7680;
/*      */   }
/*      */ 
/*      */   
/*      */   public enum TexGen
/*      */   {
/* 1225 */     S,
/* 1226 */     T,
/* 1227 */     R,
/* 1228 */     Q;
/*      */   }
/*      */   
/*      */   static class TexGenCoord
/*      */   {
/*      */     public GlStateManager.BooleanState field_179067_a;
/*      */     public int field_179065_b;
/* 1235 */     public int field_179066_c = -1;
/*      */ 
/*      */     
/*      */     public TexGenCoord(int p_i46254_1_, int p_i46254_2_) {
/* 1239 */       this.field_179065_b = p_i46254_1_;
/* 1240 */       this.field_179067_a = new GlStateManager.BooleanState(p_i46254_2_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class TexGenState
/*      */   {
/*      */     private TexGenState() {}
/*      */ 
/*      */ 
/*      */     
/* 1253 */     public GlStateManager.TexGenCoord field_179064_a = new GlStateManager.TexGenCoord(8192, 3168);
/* 1254 */     public GlStateManager.TexGenCoord field_179062_b = new GlStateManager.TexGenCoord(8193, 3169);
/* 1255 */     public GlStateManager.TexGenCoord field_179063_c = new GlStateManager.TexGenCoord(8194, 3170);
/* 1256 */     public GlStateManager.TexGenCoord field_179061_d = new GlStateManager.TexGenCoord(8195, 3171);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static class TextureState
/*      */   {
/*      */     private TextureState() {}
/*      */ 
/*      */ 
/*      */     
/* 1267 */     public GlStateManager.BooleanState texture2DState = new GlStateManager.BooleanState(3553);
/* 1268 */     public int textureName = 0;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\GlStateManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */