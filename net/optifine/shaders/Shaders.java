/*      */ package net.optifine.shaders;
/*      */ 
/*      */ import com.google.common.base.Charsets;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.Reader;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Deque;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.ActiveRenderInfo;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.texture.ITextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.src.Config;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ import net.optifine.CustomBlockLayers;
/*      */ import net.optifine.CustomColors;
/*      */ import net.optifine.GlErrors;
/*      */ import net.optifine.Lang;
/*      */ import net.optifine.config.ConnectedParser;
/*      */ import net.optifine.expr.IExpressionBool;
/*      */ import net.optifine.reflect.Reflector;
/*      */ import net.optifine.render.GlAlphaState;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.shaders.config.EnumShaderOption;
/*      */ import net.optifine.shaders.config.MacroProcessor;
/*      */ import net.optifine.shaders.config.MacroState;
/*      */ import net.optifine.shaders.config.PropertyDefaultFastFancyOff;
/*      */ import net.optifine.shaders.config.PropertyDefaultTrueFalse;
/*      */ import net.optifine.shaders.config.RenderScale;
/*      */ import net.optifine.shaders.config.ScreenShaderOptions;
/*      */ import net.optifine.shaders.config.ShaderLine;
/*      */ import net.optifine.shaders.config.ShaderOption;
/*      */ import net.optifine.shaders.config.ShaderOptionProfile;
/*      */ import net.optifine.shaders.config.ShaderPackParser;
/*      */ import net.optifine.shaders.config.ShaderParser;
/*      */ import net.optifine.shaders.config.ShaderProfile;
/*      */ import net.optifine.shaders.uniform.CustomUniforms;
/*      */ import net.optifine.shaders.uniform.ShaderUniform1f;
/*      */ import net.optifine.shaders.uniform.ShaderUniform1i;
/*      */ import net.optifine.shaders.uniform.ShaderUniform2i;
/*      */ import net.optifine.shaders.uniform.ShaderUniform3f;
/*      */ import net.optifine.shaders.uniform.ShaderUniform4f;
/*      */ import net.optifine.shaders.uniform.ShaderUniform4i;
/*      */ import net.optifine.shaders.uniform.ShaderUniformM4;
/*      */ import net.optifine.shaders.uniform.ShaderUniforms;
/*      */ import net.optifine.shaders.uniform.Smoother;
/*      */ import net.optifine.texture.InternalFormat;
/*      */ import net.optifine.texture.PixelFormat;
/*      */ import net.optifine.texture.PixelType;
/*      */ import net.optifine.texture.TextureType;
/*      */ import net.optifine.util.EntityUtils;
/*      */ import net.optifine.util.PropertiesOrdered;
/*      */ import net.optifine.util.StrUtils;
/*      */ import net.optifine.util.TimedEvent;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.BufferUtils;
/*      */ import org.lwjgl.opengl.ARBGeometryShader4;
/*      */ import org.lwjgl.opengl.ARBShaderObjects;
/*      */ import org.lwjgl.opengl.ARBVertexShader;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.EXTFramebufferObject;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import org.lwjgl.util.vector.Vector4f;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Shaders
/*      */ {
/*      */   static Minecraft mc;
/*      */   static EntityRenderer entityRenderer;
/*      */   public static boolean isInitializedOnce = false;
/*      */   public static boolean isShaderPackInitialized = false;
/*      */   public static ContextCapabilities capabilities;
/*      */   public static String glVersionString;
/*      */   public static String glVendorString;
/*      */   public static String glRendererString;
/*      */   public static boolean hasGlGenMipmap = false;
/*  130 */   public static int countResetDisplayLists = 0;
/*  131 */   private static int renderDisplayWidth = 0;
/*  132 */   private static int renderDisplayHeight = 0;
/*  133 */   public static int renderWidth = 0;
/*  134 */   public static int renderHeight = 0;
/*      */   public static boolean isRenderingWorld = false;
/*      */   public static boolean isRenderingSky = false;
/*      */   public static boolean isCompositeRendered = false;
/*      */   public static boolean isRenderingDfb = false;
/*      */   public static boolean isShadowPass = false;
/*      */   public static boolean isEntitiesGlowing = false;
/*      */   public static boolean isSleeping;
/*      */   private static boolean isRenderingFirstPersonHand;
/*      */   private static boolean isHandRenderedMain;
/*      */   private static boolean isHandRenderedOff;
/*      */   private static boolean skipRenderHandMain;
/*      */   private static boolean skipRenderHandOff;
/*      */   public static boolean renderItemKeepDepthMask = false;
/*      */   public static boolean itemToRenderMainTranslucent = false;
/*      */   public static boolean itemToRenderOffTranslucent = false;
/*  150 */   static float[] sunPosition = new float[4];
/*  151 */   static float[] moonPosition = new float[4];
/*  152 */   static float[] shadowLightPosition = new float[4];
/*  153 */   static float[] upPosition = new float[4];
/*  154 */   static float[] shadowLightPositionVector = new float[4];
/*  155 */   static float[] upPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  156 */   static float[] sunPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  157 */   static float[] moonPosModelView = new float[] { 0.0F, -100.0F, 0.0F, 0.0F };
/*  158 */   private static float[] tempMat = new float[16];
/*      */   static float clearColorR;
/*      */   static float clearColorG;
/*      */   static float clearColorB;
/*      */   static float skyColorR;
/*      */   static float skyColorG;
/*      */   static float skyColorB;
/*  165 */   static long worldTime = 0L;
/*  166 */   static long lastWorldTime = 0L;
/*  167 */   static long diffWorldTime = 0L;
/*  168 */   static float celestialAngle = 0.0F;
/*  169 */   static float sunAngle = 0.0F;
/*  170 */   static float shadowAngle = 0.0F;
/*  171 */   static int moonPhase = 0;
/*  172 */   static long systemTime = 0L;
/*  173 */   static long lastSystemTime = 0L;
/*  174 */   static long diffSystemTime = 0L;
/*  175 */   static int frameCounter = 0;
/*  176 */   static float frameTime = 0.0F;
/*  177 */   static float frameTimeCounter = 0.0F;
/*  178 */   static int systemTimeInt32 = 0;
/*  179 */   static float rainStrength = 0.0F;
/*  180 */   static float wetness = 0.0F;
/*  181 */   public static float wetnessHalfLife = 600.0F;
/*  182 */   public static float drynessHalfLife = 200.0F;
/*  183 */   public static float eyeBrightnessHalflife = 10.0F;
/*      */   static boolean usewetness = false;
/*  185 */   static int isEyeInWater = 0;
/*  186 */   static int eyeBrightness = 0;
/*  187 */   static float eyeBrightnessFadeX = 0.0F;
/*  188 */   static float eyeBrightnessFadeY = 0.0F;
/*  189 */   static float eyePosY = 0.0F;
/*  190 */   static float centerDepth = 0.0F;
/*  191 */   static float centerDepthSmooth = 0.0F;
/*  192 */   static float centerDepthSmoothHalflife = 1.0F;
/*      */   static boolean centerDepthSmoothEnabled = false;
/*  194 */   static int superSamplingLevel = 1;
/*  195 */   static float nightVision = 0.0F;
/*  196 */   static float blindness = 0.0F;
/*      */   static boolean lightmapEnabled = false;
/*      */   static boolean fogEnabled = true;
/*  199 */   public static int entityAttrib = 10;
/*  200 */   public static int midTexCoordAttrib = 11;
/*  201 */   public static int tangentAttrib = 12;
/*      */   public static boolean useEntityAttrib = false;
/*      */   public static boolean useMidTexCoordAttrib = false;
/*      */   public static boolean useTangentAttrib = false;
/*      */   public static boolean progUseEntityAttrib = false;
/*      */   public static boolean progUseMidTexCoordAttrib = false;
/*      */   public static boolean progUseTangentAttrib = false;
/*      */   private static boolean progArbGeometryShader4 = false;
/*  209 */   private static int progMaxVerticesOut = 3;
/*      */   private static boolean hasGeometryShaders = false;
/*  211 */   public static int atlasSizeX = 0;
/*  212 */   public static int atlasSizeY = 0;
/*  213 */   private static ShaderUniforms shaderUniforms = new ShaderUniforms();
/*  214 */   public static ShaderUniform4f uniform_entityColor = shaderUniforms.make4f("entityColor");
/*  215 */   public static ShaderUniform1i uniform_entityId = shaderUniforms.make1i("entityId");
/*  216 */   public static ShaderUniform1i uniform_blockEntityId = shaderUniforms.make1i("blockEntityId");
/*  217 */   public static ShaderUniform1i uniform_texture = shaderUniforms.make1i("texture");
/*  218 */   public static ShaderUniform1i uniform_lightmap = shaderUniforms.make1i("lightmap");
/*  219 */   public static ShaderUniform1i uniform_normals = shaderUniforms.make1i("normals");
/*  220 */   public static ShaderUniform1i uniform_specular = shaderUniforms.make1i("specular");
/*  221 */   public static ShaderUniform1i uniform_shadow = shaderUniforms.make1i("shadow");
/*  222 */   public static ShaderUniform1i uniform_watershadow = shaderUniforms.make1i("watershadow");
/*  223 */   public static ShaderUniform1i uniform_shadowtex0 = shaderUniforms.make1i("shadowtex0");
/*  224 */   public static ShaderUniform1i uniform_shadowtex1 = shaderUniforms.make1i("shadowtex1");
/*  225 */   public static ShaderUniform1i uniform_depthtex0 = shaderUniforms.make1i("depthtex0");
/*  226 */   public static ShaderUniform1i uniform_depthtex1 = shaderUniforms.make1i("depthtex1");
/*  227 */   public static ShaderUniform1i uniform_shadowcolor = shaderUniforms.make1i("shadowcolor");
/*  228 */   public static ShaderUniform1i uniform_shadowcolor0 = shaderUniforms.make1i("shadowcolor0");
/*  229 */   public static ShaderUniform1i uniform_shadowcolor1 = shaderUniforms.make1i("shadowcolor1");
/*  230 */   public static ShaderUniform1i uniform_noisetex = shaderUniforms.make1i("noisetex");
/*  231 */   public static ShaderUniform1i uniform_gcolor = shaderUniforms.make1i("gcolor");
/*  232 */   public static ShaderUniform1i uniform_gdepth = shaderUniforms.make1i("gdepth");
/*  233 */   public static ShaderUniform1i uniform_gnormal = shaderUniforms.make1i("gnormal");
/*  234 */   public static ShaderUniform1i uniform_composite = shaderUniforms.make1i("composite");
/*  235 */   public static ShaderUniform1i uniform_gaux1 = shaderUniforms.make1i("gaux1");
/*  236 */   public static ShaderUniform1i uniform_gaux2 = shaderUniforms.make1i("gaux2");
/*  237 */   public static ShaderUniform1i uniform_gaux3 = shaderUniforms.make1i("gaux3");
/*  238 */   public static ShaderUniform1i uniform_gaux4 = shaderUniforms.make1i("gaux4");
/*  239 */   public static ShaderUniform1i uniform_colortex0 = shaderUniforms.make1i("colortex0");
/*  240 */   public static ShaderUniform1i uniform_colortex1 = shaderUniforms.make1i("colortex1");
/*  241 */   public static ShaderUniform1i uniform_colortex2 = shaderUniforms.make1i("colortex2");
/*  242 */   public static ShaderUniform1i uniform_colortex3 = shaderUniforms.make1i("colortex3");
/*  243 */   public static ShaderUniform1i uniform_colortex4 = shaderUniforms.make1i("colortex4");
/*  244 */   public static ShaderUniform1i uniform_colortex5 = shaderUniforms.make1i("colortex5");
/*  245 */   public static ShaderUniform1i uniform_colortex6 = shaderUniforms.make1i("colortex6");
/*  246 */   public static ShaderUniform1i uniform_colortex7 = shaderUniforms.make1i("colortex7");
/*  247 */   public static ShaderUniform1i uniform_gdepthtex = shaderUniforms.make1i("gdepthtex");
/*  248 */   public static ShaderUniform1i uniform_depthtex2 = shaderUniforms.make1i("depthtex2");
/*  249 */   public static ShaderUniform1i uniform_tex = shaderUniforms.make1i("tex");
/*  250 */   public static ShaderUniform1i uniform_heldItemId = shaderUniforms.make1i("heldItemId");
/*  251 */   public static ShaderUniform1i uniform_heldBlockLightValue = shaderUniforms.make1i("heldBlockLightValue");
/*  252 */   public static ShaderUniform1i uniform_heldItemId2 = shaderUniforms.make1i("heldItemId2");
/*  253 */   public static ShaderUniform1i uniform_heldBlockLightValue2 = shaderUniforms.make1i("heldBlockLightValue2");
/*  254 */   public static ShaderUniform1i uniform_fogMode = shaderUniforms.make1i("fogMode");
/*  255 */   public static ShaderUniform1f uniform_fogDensity = shaderUniforms.make1f("fogDensity");
/*  256 */   public static ShaderUniform3f uniform_fogColor = shaderUniforms.make3f("fogColor");
/*  257 */   public static ShaderUniform3f uniform_skyColor = shaderUniforms.make3f("skyColor");
/*  258 */   public static ShaderUniform1i uniform_worldTime = shaderUniforms.make1i("worldTime");
/*  259 */   public static ShaderUniform1i uniform_worldDay = shaderUniforms.make1i("worldDay");
/*  260 */   public static ShaderUniform1i uniform_moonPhase = shaderUniforms.make1i("moonPhase");
/*  261 */   public static ShaderUniform1i uniform_frameCounter = shaderUniforms.make1i("frameCounter");
/*  262 */   public static ShaderUniform1f uniform_frameTime = shaderUniforms.make1f("frameTime");
/*  263 */   public static ShaderUniform1f uniform_frameTimeCounter = shaderUniforms.make1f("frameTimeCounter");
/*  264 */   public static ShaderUniform1f uniform_sunAngle = shaderUniforms.make1f("sunAngle");
/*  265 */   public static ShaderUniform1f uniform_shadowAngle = shaderUniforms.make1f("shadowAngle");
/*  266 */   public static ShaderUniform1f uniform_rainStrength = shaderUniforms.make1f("rainStrength");
/*  267 */   public static ShaderUniform1f uniform_aspectRatio = shaderUniforms.make1f("aspectRatio");
/*  268 */   public static ShaderUniform1f uniform_viewWidth = shaderUniforms.make1f("viewWidth");
/*  269 */   public static ShaderUniform1f uniform_viewHeight = shaderUniforms.make1f("viewHeight");
/*  270 */   public static ShaderUniform1f uniform_near = shaderUniforms.make1f("near");
/*  271 */   public static ShaderUniform1f uniform_far = shaderUniforms.make1f("far");
/*  272 */   public static ShaderUniform3f uniform_sunPosition = shaderUniforms.make3f("sunPosition");
/*  273 */   public static ShaderUniform3f uniform_moonPosition = shaderUniforms.make3f("moonPosition");
/*  274 */   public static ShaderUniform3f uniform_shadowLightPosition = shaderUniforms.make3f("shadowLightPosition");
/*  275 */   public static ShaderUniform3f uniform_upPosition = shaderUniforms.make3f("upPosition");
/*  276 */   public static ShaderUniform3f uniform_previousCameraPosition = shaderUniforms.make3f("previousCameraPosition");
/*  277 */   public static ShaderUniform3f uniform_cameraPosition = shaderUniforms.make3f("cameraPosition");
/*  278 */   public static ShaderUniformM4 uniform_gbufferModelView = shaderUniforms.makeM4("gbufferModelView");
/*  279 */   public static ShaderUniformM4 uniform_gbufferModelViewInverse = shaderUniforms.makeM4("gbufferModelViewInverse");
/*  280 */   public static ShaderUniformM4 uniform_gbufferPreviousProjection = shaderUniforms.makeM4("gbufferPreviousProjection");
/*  281 */   public static ShaderUniformM4 uniform_gbufferProjection = shaderUniforms.makeM4("gbufferProjection");
/*  282 */   public static ShaderUniformM4 uniform_gbufferProjectionInverse = shaderUniforms.makeM4("gbufferProjectionInverse");
/*  283 */   public static ShaderUniformM4 uniform_gbufferPreviousModelView = shaderUniforms.makeM4("gbufferPreviousModelView");
/*  284 */   public static ShaderUniformM4 uniform_shadowProjection = shaderUniforms.makeM4("shadowProjection");
/*  285 */   public static ShaderUniformM4 uniform_shadowProjectionInverse = shaderUniforms.makeM4("shadowProjectionInverse");
/*  286 */   public static ShaderUniformM4 uniform_shadowModelView = shaderUniforms.makeM4("shadowModelView");
/*  287 */   public static ShaderUniformM4 uniform_shadowModelViewInverse = shaderUniforms.makeM4("shadowModelViewInverse");
/*  288 */   public static ShaderUniform1f uniform_wetness = shaderUniforms.make1f("wetness");
/*  289 */   public static ShaderUniform1f uniform_eyeAltitude = shaderUniforms.make1f("eyeAltitude");
/*  290 */   public static ShaderUniform2i uniform_eyeBrightness = shaderUniforms.make2i("eyeBrightness");
/*  291 */   public static ShaderUniform2i uniform_eyeBrightnessSmooth = shaderUniforms.make2i("eyeBrightnessSmooth");
/*  292 */   public static ShaderUniform2i uniform_terrainTextureSize = shaderUniforms.make2i("terrainTextureSize");
/*  293 */   public static ShaderUniform1i uniform_terrainIconSize = shaderUniforms.make1i("terrainIconSize");
/*  294 */   public static ShaderUniform1i uniform_isEyeInWater = shaderUniforms.make1i("isEyeInWater");
/*  295 */   public static ShaderUniform1f uniform_nightVision = shaderUniforms.make1f("nightVision");
/*  296 */   public static ShaderUniform1f uniform_blindness = shaderUniforms.make1f("blindness");
/*  297 */   public static ShaderUniform1f uniform_screenBrightness = shaderUniforms.make1f("screenBrightness");
/*  298 */   public static ShaderUniform1i uniform_hideGUI = shaderUniforms.make1i("hideGUI");
/*  299 */   public static ShaderUniform1f uniform_centerDepthSmooth = shaderUniforms.make1f("centerDepthSmooth");
/*  300 */   public static ShaderUniform2i uniform_atlasSize = shaderUniforms.make2i("atlasSize");
/*  301 */   public static ShaderUniform4i uniform_blendFunc = shaderUniforms.make4i("blendFunc");
/*  302 */   public static ShaderUniform1i uniform_instanceId = shaderUniforms.make1i("instanceId");
/*      */   static double previousCameraPositionX;
/*      */   static double previousCameraPositionY;
/*      */   static double previousCameraPositionZ;
/*      */   static double cameraPositionX;
/*      */   static double cameraPositionY;
/*      */   static double cameraPositionZ;
/*      */   static int cameraOffsetX;
/*      */   static int cameraOffsetZ;
/*  311 */   static int shadowPassInterval = 0;
/*      */   public static boolean needResizeShadow = false;
/*  313 */   static int shadowMapWidth = 1024;
/*  314 */   static int shadowMapHeight = 1024;
/*  315 */   static int spShadowMapWidth = 1024;
/*  316 */   static int spShadowMapHeight = 1024;
/*  317 */   static float shadowMapFOV = 90.0F;
/*  318 */   static float shadowMapHalfPlane = 160.0F;
/*      */   static boolean shadowMapIsOrtho = true;
/*  320 */   static float shadowDistanceRenderMul = -1.0F;
/*  321 */   static int shadowPassCounter = 0;
/*      */   static int preShadowPassThirdPersonView;
/*      */   public static boolean shouldSkipDefaultShadow = false;
/*      */   static boolean waterShadowEnabled = false;
/*      */   static final int MaxDrawBuffers = 8;
/*      */   static final int MaxColorBuffers = 8;
/*      */   static final int MaxDepthBuffers = 3;
/*      */   static final int MaxShadowColorBuffers = 8;
/*      */   static final int MaxShadowDepthBuffers = 2;
/*  330 */   static int usedColorBuffers = 0;
/*  331 */   static int usedDepthBuffers = 0;
/*  332 */   static int usedShadowColorBuffers = 0;
/*  333 */   static int usedShadowDepthBuffers = 0;
/*  334 */   static int usedColorAttachs = 0;
/*  335 */   static int usedDrawBuffers = 0;
/*  336 */   static int dfb = 0;
/*  337 */   static int sfb = 0;
/*  338 */   private static int[] gbuffersFormat = new int[8];
/*  339 */   public static boolean[] gbuffersClear = new boolean[8];
/*  340 */   public static Vector4f[] gbuffersClearColor = new Vector4f[8];
/*  341 */   private static Programs programs = new Programs();
/*  342 */   public static final Program ProgramNone = programs.getProgramNone();
/*  343 */   public static final Program ProgramShadow = programs.makeShadow("shadow", ProgramNone);
/*  344 */   public static final Program ProgramShadowSolid = programs.makeShadow("shadow_solid", ProgramShadow);
/*  345 */   public static final Program ProgramShadowCutout = programs.makeShadow("shadow_cutout", ProgramShadow);
/*  346 */   public static final Program ProgramBasic = programs.makeGbuffers("gbuffers_basic", ProgramNone);
/*  347 */   public static final Program ProgramTextured = programs.makeGbuffers("gbuffers_textured", ProgramBasic);
/*  348 */   public static final Program ProgramTexturedLit = programs.makeGbuffers("gbuffers_textured_lit", ProgramTextured);
/*  349 */   public static final Program ProgramSkyBasic = programs.makeGbuffers("gbuffers_skybasic", ProgramBasic);
/*  350 */   public static final Program ProgramSkyTextured = programs.makeGbuffers("gbuffers_skytextured", ProgramTextured);
/*  351 */   public static final Program ProgramClouds = programs.makeGbuffers("gbuffers_clouds", ProgramTextured);
/*  352 */   public static final Program ProgramTerrain = programs.makeGbuffers("gbuffers_terrain", ProgramTexturedLit);
/*  353 */   public static final Program ProgramTerrainSolid = programs.makeGbuffers("gbuffers_terrain_solid", ProgramTerrain);
/*  354 */   public static final Program ProgramTerrainCutoutMip = programs.makeGbuffers("gbuffers_terrain_cutout_mip", ProgramTerrain);
/*  355 */   public static final Program ProgramTerrainCutout = programs.makeGbuffers("gbuffers_terrain_cutout", ProgramTerrain);
/*  356 */   public static final Program ProgramDamagedBlock = programs.makeGbuffers("gbuffers_damagedblock", ProgramTerrain);
/*  357 */   public static final Program ProgramBlock = programs.makeGbuffers("gbuffers_block", ProgramTerrain);
/*  358 */   public static final Program ProgramBeaconBeam = programs.makeGbuffers("gbuffers_beaconbeam", ProgramTextured);
/*  359 */   public static final Program ProgramItem = programs.makeGbuffers("gbuffers_item", ProgramTexturedLit);
/*  360 */   public static final Program ProgramEntities = programs.makeGbuffers("gbuffers_entities", ProgramTexturedLit);
/*  361 */   public static final Program ProgramEntitiesGlowing = programs.makeGbuffers("gbuffers_entities_glowing", ProgramEntities);
/*  362 */   public static final Program ProgramArmorGlint = programs.makeGbuffers("gbuffers_armor_glint", ProgramTextured);
/*  363 */   public static final Program ProgramSpiderEyes = programs.makeGbuffers("gbuffers_spidereyes", ProgramTextured);
/*  364 */   public static final Program ProgramHand = programs.makeGbuffers("gbuffers_hand", ProgramTexturedLit);
/*  365 */   public static final Program ProgramWeather = programs.makeGbuffers("gbuffers_weather", ProgramTexturedLit);
/*  366 */   public static final Program ProgramDeferredPre = programs.makeVirtual("deferred_pre");
/*  367 */   public static final Program[] ProgramsDeferred = programs.makeDeferreds("deferred", 16);
/*  368 */   public static final Program ProgramDeferred = ProgramsDeferred[0];
/*  369 */   public static final Program ProgramWater = programs.makeGbuffers("gbuffers_water", ProgramTerrain);
/*  370 */   public static final Program ProgramHandWater = programs.makeGbuffers("gbuffers_hand_water", ProgramHand);
/*  371 */   public static final Program ProgramCompositePre = programs.makeVirtual("composite_pre");
/*  372 */   public static final Program[] ProgramsComposite = programs.makeComposites("composite", 16);
/*  373 */   public static final Program ProgramComposite = ProgramsComposite[0];
/*  374 */   public static final Program ProgramFinal = programs.makeComposite("final");
/*  375 */   public static final int ProgramCount = programs.getCount();
/*  376 */   public static final Program[] ProgramsAll = programs.getPrograms();
/*  377 */   public static Program activeProgram = ProgramNone;
/*  378 */   public static int activeProgramID = 0;
/*  379 */   private static ProgramStack programStackLeash = new ProgramStack();
/*      */   private static boolean hasDeferredPrograms = false;
/*  381 */   static IntBuffer activeDrawBuffers = null;
/*  382 */   private static int activeCompositeMipmapSetting = 0;
/*  383 */   public static Properties loadedShaders = null;
/*  384 */   public static Properties shadersConfig = null;
/*  385 */   public static ITextureObject defaultTexture = null;
/*  386 */   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
/*  387 */   public static boolean[] shadowMipmapEnabled = new boolean[2];
/*  388 */   public static boolean[] shadowFilterNearest = new boolean[2];
/*  389 */   public static boolean[] shadowColorMipmapEnabled = new boolean[8];
/*  390 */   public static boolean[] shadowColorFilterNearest = new boolean[8];
/*      */   public static boolean configTweakBlockDamage = false;
/*      */   public static boolean configCloudShadow = false;
/*  393 */   public static float configHandDepthMul = 0.125F;
/*  394 */   public static float configRenderResMul = 1.0F;
/*  395 */   public static float configShadowResMul = 1.0F;
/*  396 */   public static int configTexMinFilB = 0;
/*  397 */   public static int configTexMinFilN = 0;
/*  398 */   public static int configTexMinFilS = 0;
/*  399 */   public static int configTexMagFilB = 0;
/*  400 */   public static int configTexMagFilN = 0;
/*  401 */   public static int configTexMagFilS = 0;
/*      */   public static boolean configShadowClipFrustrum = true;
/*      */   public static boolean configNormalMap = true;
/*      */   public static boolean configSpecularMap = true;
/*  405 */   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  406 */   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  407 */   public static int configAntialiasingLevel = 0;
/*      */   public static final int texMinFilRange = 3;
/*      */   public static final int texMagFilRange = 2;
/*  410 */   public static final String[] texMinFilDesc = new String[] { "Nearest", "Nearest-Nearest", "Nearest-Linear" };
/*  411 */   public static final String[] texMagFilDesc = new String[] { "Nearest", "Linear" };
/*  412 */   public static final int[] texMinFilValue = new int[] { 9728, 9984, 9986 };
/*  413 */   public static final int[] texMagFilValue = new int[] { 9728, 9729 };
/*  414 */   private static IShaderPack shaderPack = null;
/*      */   public static boolean shaderPackLoaded = false;
/*      */   public static String currentShaderName;
/*      */   public static final String SHADER_PACK_NAME_NONE = "OFF";
/*      */   public static final String SHADER_PACK_NAME_DEFAULT = "(internal)";
/*      */   public static final String SHADER_PACKS_DIR_NAME = "shaderpacks";
/*      */   public static final String OPTIONS_FILE_NAME = "optionsshaders.txt";
/*      */   public static final File shaderPacksDir;
/*      */   static File configFile;
/*  423 */   private static ShaderOption[] shaderPackOptions = null;
/*  424 */   private static Set<String> shaderPackOptionSliders = null;
/*  425 */   static ShaderProfile[] shaderPackProfiles = null;
/*  426 */   static Map<String, ScreenShaderOptions> shaderPackGuiScreens = null;
/*  427 */   static Map<String, IExpressionBool> shaderPackProgramConditions = new HashMap<>();
/*      */   public static final String PATH_SHADERS_PROPERTIES = "/shaders/shaders.properties";
/*  429 */   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
/*  430 */   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  431 */   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  432 */   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
/*  433 */   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
/*  434 */   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
/*  435 */   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
/*  436 */   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
/*  437 */   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
/*  438 */   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
/*  439 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
/*  440 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
/*  441 */   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
/*  442 */   public static PropertyDefaultTrueFalse shaderPackRainDepth = new PropertyDefaultTrueFalse("rain.depth", "Rain Depth", 0);
/*  443 */   public static PropertyDefaultTrueFalse shaderPackBeaconBeamDepth = new PropertyDefaultTrueFalse("beacon.beam.depth", "Rain Depth", 0);
/*  444 */   public static PropertyDefaultTrueFalse shaderPackSeparateAo = new PropertyDefaultTrueFalse("separateAo", "Separate AO", 0);
/*  445 */   public static PropertyDefaultTrueFalse shaderPackFrustumCulling = new PropertyDefaultTrueFalse("frustum.culling", "Frustum Culling", 0);
/*  446 */   private static Map<String, String> shaderPackResources = new HashMap<>();
/*  447 */   private static World currentWorld = null;
/*  448 */   private static List<Integer> shaderPackDimensions = new ArrayList<>();
/*  449 */   private static ICustomTexture[] customTexturesGbuffers = null;
/*  450 */   private static ICustomTexture[] customTexturesComposite = null;
/*  451 */   private static ICustomTexture[] customTexturesDeferred = null;
/*  452 */   private static String noiseTexturePath = null;
/*  453 */   private static CustomUniforms customUniforms = null;
/*      */   private static final int STAGE_GBUFFERS = 0;
/*      */   private static final int STAGE_COMPOSITE = 1;
/*      */   private static final int STAGE_DEFERRED = 2;
/*  457 */   private static final String[] STAGE_NAMES = new String[] { "gbuffers", "composite", "deferred" };
/*      */   public static final boolean enableShadersOption = true;
/*      */   private static final boolean enableShadersDebug = true;
/*  460 */   public static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
/*  461 */   public static float blockLightLevel05 = 0.5F;
/*  462 */   public static float blockLightLevel06 = 0.6F;
/*  463 */   public static float blockLightLevel08 = 0.8F;
/*  464 */   public static float aoLevel = -1.0F;
/*  465 */   public static float sunPathRotation = 0.0F;
/*  466 */   public static float shadowAngleInterval = 0.0F;
/*  467 */   public static int fogMode = 0;
/*  468 */   public static float fogDensity = 0.0F;
/*      */   public static float fogColorR;
/*      */   public static float fogColorG;
/*      */   public static float fogColorB;
/*  472 */   public static float shadowIntervalSize = 2.0F;
/*  473 */   public static int terrainIconSize = 16;
/*  474 */   public static int[] terrainTextureSize = new int[2];
/*      */   private static ICustomTexture noiseTexture;
/*      */   private static boolean noiseTextureEnabled = false;
/*  477 */   private static int noiseTextureResolution = 256;
/*  478 */   static final int[] colorTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10 };
/*  479 */   private static final int bigBufferSize = (285 + 8 * ProgramCount) * 4;
/*  480 */   private static final ByteBuffer bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(bigBufferSize).limit(0);
/*  481 */   static final float[] faProjection = new float[16];
/*  482 */   static final float[] faProjectionInverse = new float[16];
/*  483 */   static final float[] faModelView = new float[16];
/*  484 */   static final float[] faModelViewInverse = new float[16];
/*  485 */   static final float[] faShadowProjection = new float[16];
/*  486 */   static final float[] faShadowProjectionInverse = new float[16];
/*  487 */   static final float[] faShadowModelView = new float[16];
/*  488 */   static final float[] faShadowModelViewInverse = new float[16];
/*  489 */   static final FloatBuffer projection = nextFloatBuffer(16);
/*  490 */   static final FloatBuffer projectionInverse = nextFloatBuffer(16);
/*  491 */   static final FloatBuffer modelView = nextFloatBuffer(16);
/*  492 */   static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
/*  493 */   static final FloatBuffer shadowProjection = nextFloatBuffer(16);
/*  494 */   static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
/*  495 */   static final FloatBuffer shadowModelView = nextFloatBuffer(16);
/*  496 */   static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
/*  497 */   static final FloatBuffer previousProjection = nextFloatBuffer(16);
/*  498 */   static final FloatBuffer previousModelView = nextFloatBuffer(16);
/*  499 */   static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
/*  500 */   static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
/*  501 */   static final IntBuffer dfbColorTextures = nextIntBuffer(16);
/*  502 */   static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
/*  503 */   static final IntBuffer sfbColorTextures = nextIntBuffer(8);
/*  504 */   static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
/*  505 */   static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
/*  506 */   static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
/*  507 */   static final IntBuffer drawBuffersNone = (IntBuffer)nextIntBuffer(8).limit(0);
/*  508 */   static final IntBuffer drawBuffersColorAtt0 = (IntBuffer)nextIntBuffer(8).put(36064).position(0).limit(1);
/*  509 */   static final FlipTextures dfbColorTexturesFlip = new FlipTextures(dfbColorTextures, 8);
/*      */   static Map<Block, Integer> mapBlockToEntityData;
/*  511 */   private static final String[] formatNames = new String[] { "R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F", "RGB9_E5" };
/*  512 */   private static final int[] formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901 };
/*  513 */   private static final Pattern patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
/*  514 */   public static int[] entityData = new int[32];
/*  515 */   public static int entityDataIndex = 0;
/*      */ 
/*      */   
/*      */   private static ByteBuffer nextByteBuffer(int size) {
/*  519 */     ByteBuffer bytebuffer = bigBuffer;
/*  520 */     int i = bytebuffer.limit();
/*  521 */     bytebuffer.position(i).limit(i + size);
/*  522 */     return bytebuffer.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   public static IntBuffer nextIntBuffer(int size) {
/*  527 */     ByteBuffer bytebuffer = bigBuffer;
/*  528 */     int i = bytebuffer.limit();
/*  529 */     bytebuffer.position(i).limit(i + size * 4);
/*  530 */     return bytebuffer.asIntBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static FloatBuffer nextFloatBuffer(int size) {
/*  535 */     ByteBuffer bytebuffer = bigBuffer;
/*  536 */     int i = bytebuffer.limit();
/*  537 */     bytebuffer.position(i).limit(i + size * 4);
/*  538 */     return bytebuffer.asFloatBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static IntBuffer[] nextIntBufferArray(int count, int size) {
/*  543 */     IntBuffer[] aintbuffer = new IntBuffer[count];
/*      */     
/*  545 */     for (int i = 0; i < count; i++)
/*      */     {
/*  547 */       aintbuffer[i] = nextIntBuffer(size);
/*      */     }
/*      */     
/*  550 */     return aintbuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadConfig() {
/*  555 */     SMCLog.info("Load shaders configuration.");
/*      */ 
/*      */     
/*      */     try {
/*  559 */       if (!shaderPacksDir.exists())
/*      */       {
/*  561 */         shaderPacksDir.mkdir();
/*      */       }
/*      */     }
/*  564 */     catch (Exception var8) {
/*      */       
/*  566 */       SMCLog.severe("Failed to open the shaderpacks directory: " + shaderPacksDir);
/*      */     } 
/*      */     
/*  569 */     shadersConfig = (Properties)new PropertiesOrdered();
/*  570 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
/*      */     
/*  572 */     if (configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  576 */         FileReader filereader = new FileReader(configFile);
/*  577 */         shadersConfig.load(filereader);
/*  578 */         filereader.close();
/*      */       }
/*  580 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  586 */     if (!configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  590 */         storeConfig();
/*      */       }
/*  592 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  598 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  600 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*      */       
/*  602 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  603 */       String s = enumshaderoption.getPropertyKey();
/*  604 */       String s1 = enumshaderoption.getValueDefault();
/*  605 */       String s2 = shadersConfig.getProperty(s, s1);
/*  606 */       setEnumShaderOption(enumshaderoption, s2);
/*      */     } 
/*      */     
/*  609 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setEnumShaderOption(EnumShaderOption eso, String str) {
/*  614 */     if (str == null)
/*      */     {
/*  616 */       str = eso.getValueDefault();
/*      */     }
/*      */     
/*  619 */     switch (eso) {
/*      */       
/*      */       case GBUFFERS:
/*  622 */         configAntialiasingLevel = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case DEFERRED:
/*  626 */         configNormalMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case COMPOSITE:
/*  630 */         configSpecularMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case SHADOW:
/*  634 */         configRenderResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case null:
/*  638 */         configShadowResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case null:
/*  642 */         configHandDepthMul = Config.parseFloat(str, 0.125F);
/*      */         return;
/*      */       
/*      */       case null:
/*  646 */         configCloudShadow = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case null:
/*  650 */         configOldHandLight.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case null:
/*  654 */         configOldLighting.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case null:
/*  658 */         currentShaderName = str;
/*      */         return;
/*      */       
/*      */       case null:
/*  662 */         configTweakBlockDamage = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case null:
/*  666 */         configShadowClipFrustrum = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case null:
/*  670 */         configTexMinFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  674 */         configTexMinFilN = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  678 */         configTexMinFilS = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  682 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  686 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case null:
/*  690 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */     } 
/*      */     
/*  694 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeConfig() {
/*  700 */     SMCLog.info("Save shaders configuration.");
/*      */     
/*  702 */     if (shadersConfig == null)
/*      */     {
/*  704 */       shadersConfig = (Properties)new PropertiesOrdered();
/*      */     }
/*      */     
/*  707 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  709 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*      */       
/*  711 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  712 */       String s = enumshaderoption.getPropertyKey();
/*  713 */       String s1 = getEnumShaderOption(enumshaderoption);
/*  714 */       shadersConfig.setProperty(s, s1);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  719 */       FileWriter filewriter = new FileWriter(configFile);
/*  720 */       shadersConfig.store(filewriter, (String)null);
/*  721 */       filewriter.close();
/*      */     }
/*  723 */     catch (Exception exception) {
/*      */       
/*  725 */       SMCLog.severe("Error saving configuration: " + exception.getClass().getName() + ": " + exception.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getEnumShaderOption(EnumShaderOption eso) {
/*  731 */     switch (eso) {
/*      */       
/*      */       case GBUFFERS:
/*  734 */         return Integer.toString(configAntialiasingLevel);
/*      */       
/*      */       case DEFERRED:
/*  737 */         return Boolean.toString(configNormalMap);
/*      */       
/*      */       case COMPOSITE:
/*  740 */         return Boolean.toString(configSpecularMap);
/*      */       
/*      */       case SHADOW:
/*  743 */         return Float.toString(configRenderResMul);
/*      */       
/*      */       case null:
/*  746 */         return Float.toString(configShadowResMul);
/*      */       
/*      */       case null:
/*  749 */         return Float.toString(configHandDepthMul);
/*      */       
/*      */       case null:
/*  752 */         return Boolean.toString(configCloudShadow);
/*      */       
/*      */       case null:
/*  755 */         return configOldHandLight.getPropertyValue();
/*      */       
/*      */       case null:
/*  758 */         return configOldLighting.getPropertyValue();
/*      */       
/*      */       case null:
/*  761 */         return currentShaderName;
/*      */       
/*      */       case null:
/*  764 */         return Boolean.toString(configTweakBlockDamage);
/*      */       
/*      */       case null:
/*  767 */         return Boolean.toString(configShadowClipFrustrum);
/*      */       
/*      */       case null:
/*  770 */         return Integer.toString(configTexMinFilB);
/*      */       
/*      */       case null:
/*  773 */         return Integer.toString(configTexMinFilN);
/*      */       
/*      */       case null:
/*  776 */         return Integer.toString(configTexMinFilS);
/*      */       
/*      */       case null:
/*  779 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case null:
/*  782 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case null:
/*  785 */         return Integer.toString(configTexMagFilB);
/*      */     } 
/*      */     
/*  788 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setShaderPack(String par1name) {
/*  794 */     currentShaderName = par1name;
/*  795 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
/*  796 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadShaderPack() {
/*  801 */     boolean flag = shaderPackLoaded;
/*  802 */     boolean flag1 = isOldLighting();
/*      */     
/*  804 */     if (mc.renderGlobal != null)
/*      */     {
/*  806 */       mc.renderGlobal.pauseChunkUpdates();
/*      */     }
/*      */     
/*  809 */     shaderPackLoaded = false;
/*      */     
/*  811 */     if (shaderPack != null) {
/*      */       
/*  813 */       shaderPack.close();
/*  814 */       shaderPack = null;
/*  815 */       shaderPackResources.clear();
/*  816 */       shaderPackDimensions.clear();
/*  817 */       shaderPackOptions = null;
/*  818 */       shaderPackOptionSliders = null;
/*  819 */       shaderPackProfiles = null;
/*  820 */       shaderPackGuiScreens = null;
/*  821 */       shaderPackProgramConditions.clear();
/*  822 */       shaderPackClouds.resetValue();
/*  823 */       shaderPackOldHandLight.resetValue();
/*  824 */       shaderPackDynamicHandLight.resetValue();
/*  825 */       shaderPackOldLighting.resetValue();
/*  826 */       resetCustomTextures();
/*  827 */       noiseTexturePath = null;
/*      */     } 
/*      */     
/*  830 */     boolean flag2 = false;
/*      */     
/*  832 */     if (Config.isAntialiasing()) {
/*      */       
/*  834 */       SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
/*  835 */       flag2 = true;
/*      */     } 
/*      */     
/*  838 */     if (Config.isAnisotropicFiltering()) {
/*      */       
/*  840 */       SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: " + Config.getAnisotropicFilterLevel() + "x");
/*  841 */       flag2 = true;
/*      */     } 
/*      */     
/*  844 */     if (Config.isFastRender()) {
/*      */       
/*  846 */       SMCLog.info("Shaders can not be loaded, Fast Render is enabled.");
/*  847 */       flag2 = true;
/*      */     } 
/*      */     
/*  850 */     String s = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "(internal)");
/*      */     
/*  852 */     if (!flag2) {
/*      */       
/*  854 */       shaderPack = getShaderPack(s);
/*  855 */       shaderPackLoaded = (shaderPack != null);
/*      */     } 
/*      */     
/*  858 */     if (shaderPackLoaded) {
/*      */       
/*  860 */       SMCLog.info("Loaded shaderpack: " + getShaderPackName());
/*      */     }
/*      */     else {
/*      */       
/*  864 */       SMCLog.info("No shaderpack loaded.");
/*  865 */       shaderPack = new ShaderPackNone();
/*      */     } 
/*      */     
/*  868 */     if (saveFinalShaders)
/*      */     {
/*  870 */       clearDirectory(new File(shaderPacksDir, "debug"));
/*      */     }
/*      */     
/*  873 */     loadShaderPackResources();
/*  874 */     loadShaderPackDimensions();
/*  875 */     shaderPackOptions = loadShaderPackOptions();
/*  876 */     loadShaderPackProperties();
/*  877 */     boolean flag3 = (shaderPackLoaded != flag);
/*  878 */     boolean flag4 = (isOldLighting() != flag1);
/*      */     
/*  880 */     if (flag3 || flag4) {
/*      */       
/*  882 */       DefaultVertexFormats.updateVertexFormats();
/*      */       
/*  884 */       if (Reflector.LightUtil.exists()) {
/*      */         
/*  886 */         Reflector.LightUtil_itemConsumer.setValue(null);
/*  887 */         Reflector.LightUtil_tessellator.setValue(null);
/*      */       } 
/*      */       
/*  890 */       updateBlockLightLevel();
/*      */     } 
/*      */     
/*  893 */     if (mc.getResourcePackRepository() != null)
/*      */     {
/*  895 */       CustomBlockLayers.update();
/*      */     }
/*      */     
/*  898 */     if (mc.renderGlobal != null)
/*      */     {
/*  900 */       mc.renderGlobal.resumeChunkUpdates();
/*      */     }
/*      */     
/*  903 */     if ((flag3 || flag4) && mc.getResourceManager() != null)
/*      */     {
/*  905 */       mc.scheduleResourcesRefresh();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static IShaderPack getShaderPack(String name) {
/*  911 */     if (name == null)
/*      */     {
/*  913 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  917 */     name = name.trim();
/*      */     
/*  919 */     if (!name.isEmpty() && !name.equals("OFF")) {
/*      */       
/*  921 */       if (name.equals("(internal)"))
/*      */       {
/*  923 */         return new ShaderPackDefault();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  929 */         File file1 = new File(shaderPacksDir, name);
/*  930 */         return file1.isDirectory() ? new ShaderPackFolder(name, file1) : ((file1.isFile() && name.toLowerCase().endsWith(".zip")) ? new ShaderPackZip(name, file1) : null);
/*      */       }
/*  932 */       catch (Exception exception) {
/*      */         
/*  934 */         exception.printStackTrace();
/*  935 */         return null;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  941 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IShaderPack getShaderPack() {
/*  948 */     return shaderPack;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackDimensions() {
/*  953 */     shaderPackDimensions.clear();
/*      */     
/*  955 */     for (int i = -128; i <= 128; i++) {
/*      */       
/*  957 */       String s = "/shaders/world" + i;
/*      */       
/*  959 */       if (shaderPack.hasDirectory(s))
/*      */       {
/*  961 */         shaderPackDimensions.add(Integer.valueOf(i));
/*      */       }
/*      */     } 
/*      */     
/*  965 */     if (shaderPackDimensions.size() > 0) {
/*      */       
/*  967 */       Integer[] ainteger = shaderPackDimensions.<Integer>toArray(new Integer[shaderPackDimensions.size()]);
/*  968 */       Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[])ainteger));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackProperties() {
/*  974 */     shaderPackClouds.resetValue();
/*  975 */     shaderPackOldHandLight.resetValue();
/*  976 */     shaderPackDynamicHandLight.resetValue();
/*  977 */     shaderPackOldLighting.resetValue();
/*  978 */     shaderPackShadowTranslucent.resetValue();
/*  979 */     shaderPackUnderwaterOverlay.resetValue();
/*  980 */     shaderPackSun.resetValue();
/*  981 */     shaderPackMoon.resetValue();
/*  982 */     shaderPackVignette.resetValue();
/*  983 */     shaderPackBackFaceSolid.resetValue();
/*  984 */     shaderPackBackFaceCutout.resetValue();
/*  985 */     shaderPackBackFaceCutoutMipped.resetValue();
/*  986 */     shaderPackBackFaceTranslucent.resetValue();
/*  987 */     shaderPackRainDepth.resetValue();
/*  988 */     shaderPackBeaconBeamDepth.resetValue();
/*  989 */     shaderPackSeparateAo.resetValue();
/*  990 */     shaderPackFrustumCulling.resetValue();
/*  991 */     BlockAliases.reset();
/*  992 */     ItemAliases.reset();
/*  993 */     EntityAliases.reset();
/*  994 */     customUniforms = null;
/*      */     
/*  996 */     for (int i = 0; i < ProgramsAll.length; i++) {
/*      */       
/*  998 */       Program program = ProgramsAll[i];
/*  999 */       program.resetProperties();
/*      */     } 
/*      */     
/* 1002 */     if (shaderPack != null) {
/*      */       
/* 1004 */       BlockAliases.update(shaderPack);
/* 1005 */       ItemAliases.update(shaderPack);
/* 1006 */       EntityAliases.update(shaderPack);
/* 1007 */       String s = "/shaders/shaders.properties";
/*      */ 
/*      */       
/*      */       try {
/* 1011 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */         
/* 1013 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1018 */         inputstream = MacroProcessor.process(inputstream, s);
/* 1019 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 1020 */         propertiesOrdered.load(inputstream);
/* 1021 */         inputstream.close();
/* 1022 */         shaderPackClouds.loadFrom((Properties)propertiesOrdered);
/* 1023 */         shaderPackOldHandLight.loadFrom((Properties)propertiesOrdered);
/* 1024 */         shaderPackDynamicHandLight.loadFrom((Properties)propertiesOrdered);
/* 1025 */         shaderPackOldLighting.loadFrom((Properties)propertiesOrdered);
/* 1026 */         shaderPackShadowTranslucent.loadFrom((Properties)propertiesOrdered);
/* 1027 */         shaderPackUnderwaterOverlay.loadFrom((Properties)propertiesOrdered);
/* 1028 */         shaderPackSun.loadFrom((Properties)propertiesOrdered);
/* 1029 */         shaderPackVignette.loadFrom((Properties)propertiesOrdered);
/* 1030 */         shaderPackMoon.loadFrom((Properties)propertiesOrdered);
/* 1031 */         shaderPackBackFaceSolid.loadFrom((Properties)propertiesOrdered);
/* 1032 */         shaderPackBackFaceCutout.loadFrom((Properties)propertiesOrdered);
/* 1033 */         shaderPackBackFaceCutoutMipped.loadFrom((Properties)propertiesOrdered);
/* 1034 */         shaderPackBackFaceTranslucent.loadFrom((Properties)propertiesOrdered);
/* 1035 */         shaderPackRainDepth.loadFrom((Properties)propertiesOrdered);
/* 1036 */         shaderPackBeaconBeamDepth.loadFrom((Properties)propertiesOrdered);
/* 1037 */         shaderPackSeparateAo.loadFrom((Properties)propertiesOrdered);
/* 1038 */         shaderPackFrustumCulling.loadFrom((Properties)propertiesOrdered);
/* 1039 */         shaderPackOptionSliders = ShaderPackParser.parseOptionSliders((Properties)propertiesOrdered, shaderPackOptions);
/* 1040 */         shaderPackProfiles = ShaderPackParser.parseProfiles((Properties)propertiesOrdered, shaderPackOptions);
/* 1041 */         shaderPackGuiScreens = ShaderPackParser.parseGuiScreens((Properties)propertiesOrdered, shaderPackProfiles, shaderPackOptions);
/* 1042 */         shaderPackProgramConditions = ShaderPackParser.parseProgramConditions((Properties)propertiesOrdered, shaderPackOptions);
/* 1043 */         customTexturesGbuffers = loadCustomTextures((Properties)propertiesOrdered, 0);
/* 1044 */         customTexturesComposite = loadCustomTextures((Properties)propertiesOrdered, 1);
/* 1045 */         customTexturesDeferred = loadCustomTextures((Properties)propertiesOrdered, 2);
/* 1046 */         noiseTexturePath = propertiesOrdered.getProperty("texture.noise");
/*      */         
/* 1048 */         if (noiseTexturePath != null)
/*      */         {
/* 1050 */           noiseTextureEnabled = true;
/*      */         }
/*      */         
/* 1053 */         customUniforms = ShaderPackParser.parseCustomUniforms((Properties)propertiesOrdered);
/* 1054 */         ShaderPackParser.parseAlphaStates((Properties)propertiesOrdered);
/* 1055 */         ShaderPackParser.parseBlendStates((Properties)propertiesOrdered);
/* 1056 */         ShaderPackParser.parseRenderScales((Properties)propertiesOrdered);
/* 1057 */         ShaderPackParser.parseBuffersFlip((Properties)propertiesOrdered);
/*      */       }
/* 1059 */       catch (IOException var3) {
/*      */         
/* 1061 */         Config.warn("[Shaders] Error reading: " + s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture[] loadCustomTextures(Properties props, int stage) {
/* 1068 */     String s = "texture." + STAGE_NAMES[stage] + ".";
/* 1069 */     Set<Object> set = props.keySet();
/* 1070 */     List<ICustomTexture> list = new ArrayList<>();
/*      */     
/* 1072 */     for (Object e : set) {
/*      */       
/* 1074 */       String s1 = (String)e;
/* 1075 */       if (s1.startsWith(s)) {
/*      */         
/* 1077 */         String s2 = StrUtils.removePrefix(s1, s);
/* 1078 */         s2 = StrUtils.removeSuffix(s2, new String[] { ".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9" });
/* 1079 */         String s3 = props.getProperty(s1).trim();
/* 1080 */         int i = getTextureIndex(stage, s2);
/*      */         
/* 1082 */         if (i < 0) {
/*      */           
/* 1084 */           SMCLog.warning("Invalid texture name: " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/* 1088 */         ICustomTexture icustomtexture = loadCustomTexture(i, s3);
/*      */         
/* 1090 */         if (icustomtexture != null) {
/*      */           
/* 1092 */           SMCLog.info("Custom texture: " + s1 + " = " + s3);
/* 1093 */           list.add(icustomtexture);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1099 */     if (list.size() <= 0)
/*      */     {
/* 1101 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1105 */     ICustomTexture[] aicustomtexture = list.<ICustomTexture>toArray(new ICustomTexture[list.size()]);
/* 1106 */     return aicustomtexture;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTexture(int textureUnit, String path) {
/* 1112 */     if (path == null)
/*      */     {
/* 1114 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1118 */     path = path.trim();
/* 1119 */     return (path.indexOf(':') >= 0) ? loadCustomTextureLocation(textureUnit, path) : ((path.indexOf(' ') >= 0) ? loadCustomTextureRaw(textureUnit, path) : loadCustomTextureShaders(textureUnit, path));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureLocation(int textureUnit, String path) {
/* 1125 */     String s = path.trim();
/* 1126 */     int i = 0;
/*      */     
/* 1128 */     if (s.startsWith("minecraft:textures/")) {
/*      */       
/* 1130 */       s = StrUtils.addSuffixCheck(s, ".png");
/*      */       
/* 1132 */       if (s.endsWith("_n.png")) {
/*      */         
/* 1134 */         s = StrUtils.replaceSuffix(s, "_n.png", ".png");
/* 1135 */         i = 1;
/*      */       }
/* 1137 */       else if (s.endsWith("_s.png")) {
/*      */         
/* 1139 */         s = StrUtils.replaceSuffix(s, "_s.png", ".png");
/* 1140 */         i = 2;
/*      */       } 
/*      */     } 
/*      */     
/* 1144 */     ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1145 */     CustomTextureLocation customtexturelocation = new CustomTextureLocation(textureUnit, resourcelocation, i);
/* 1146 */     return customtexturelocation;
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureRaw(int textureUnit, String line) {
/* 1151 */     ConnectedParser connectedparser = new ConnectedParser("Shaders");
/* 1152 */     String[] astring = Config.tokenize(line, " ");
/* 1153 */     Deque<String> deque = new ArrayDeque<>(Arrays.asList(astring));
/* 1154 */     String s = deque.poll();
/* 1155 */     TextureType texturetype = (TextureType)connectedparser.parseEnum(deque.poll(), (Enum[])TextureType.values(), "texture type");
/*      */     
/* 1157 */     if (texturetype == null) {
/*      */       
/* 1159 */       SMCLog.warning("Invalid raw texture type: " + line);
/* 1160 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1164 */     InternalFormat internalformat = (InternalFormat)connectedparser.parseEnum(deque.poll(), (Enum[])InternalFormat.values(), "internal format");
/*      */     
/* 1166 */     if (internalformat == null) {
/*      */       
/* 1168 */       SMCLog.warning("Invalid raw texture internal format: " + line);
/* 1169 */       return null;
/*      */     } 
/*      */ 
/*      */     
/* 1173 */     int i = 0;
/* 1174 */     int j = 0;
/* 1175 */     int k = 0;
/*      */     
/* 1177 */     switch (texturetype) {
/*      */       
/*      */       case GBUFFERS:
/* 1180 */         i = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case DEFERRED:
/* 1184 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1185 */         j = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case COMPOSITE:
/* 1189 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1190 */         j = connectedparser.parseInt(deque.poll(), -1);
/* 1191 */         k = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       case SHADOW:
/* 1195 */         i = connectedparser.parseInt(deque.poll(), -1);
/* 1196 */         j = connectedparser.parseInt(deque.poll(), -1);
/*      */         break;
/*      */       
/*      */       default:
/* 1200 */         SMCLog.warning("Invalid raw texture type: " + texturetype);
/* 1201 */         return null;
/*      */     } 
/*      */     
/* 1204 */     if (i >= 0 && j >= 0 && k >= 0) {
/*      */       
/* 1206 */       PixelFormat pixelformat = (PixelFormat)connectedparser.parseEnum(deque.poll(), (Enum[])PixelFormat.values(), "pixel format");
/*      */       
/* 1208 */       if (pixelformat == null) {
/*      */         
/* 1210 */         SMCLog.warning("Invalid raw texture pixel format: " + line);
/* 1211 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1215 */       PixelType pixeltype = (PixelType)connectedparser.parseEnum(deque.poll(), (Enum[])PixelType.values(), "pixel type");
/*      */       
/* 1217 */       if (pixeltype == null) {
/*      */         
/* 1219 */         SMCLog.warning("Invalid raw texture pixel type: " + line);
/* 1220 */         return null;
/*      */       } 
/* 1222 */       if (!deque.isEmpty()) {
/*      */         
/* 1224 */         SMCLog.warning("Invalid raw texture, too many parameters: " + line);
/* 1225 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1229 */       return loadCustomTextureRaw(textureUnit, line, s, texturetype, internalformat, i, j, k, pixelformat, pixeltype);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1235 */     SMCLog.warning("Invalid raw texture size: " + line);
/* 1236 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureRaw(int textureUnit, String line, String path, TextureType type, InternalFormat internalFormat, int width, int height, int depth, PixelFormat pixelFormat, PixelType pixelType) {
/*      */     try {
/* 1246 */       String s = "shaders/" + StrUtils.removePrefix(path, "/");
/* 1247 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */       
/* 1249 */       if (inputstream == null) {
/*      */         
/* 1251 */         SMCLog.warning("Raw texture not found: " + path);
/* 1252 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1256 */       byte[] abyte = Config.readAll(inputstream);
/* 1257 */       IOUtils.closeQuietly(inputstream);
/* 1258 */       ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(abyte.length);
/* 1259 */       bytebuffer.put(abyte);
/* 1260 */       bytebuffer.flip();
/* 1261 */       CustomTextureRaw customtextureraw = new CustomTextureRaw(type, internalFormat, width, height, depth, pixelFormat, pixelType, bytebuffer, textureUnit);
/* 1262 */       return customtextureraw;
/*      */     
/*      */     }
/* 1265 */     catch (IOException ioexception) {
/*      */       
/* 1267 */       SMCLog.warning("Error loading raw texture: " + path);
/* 1268 */       SMCLog.warning("" + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 1269 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ICustomTexture loadCustomTextureShaders(int textureUnit, String path) {
/* 1275 */     path = path.trim();
/*      */     
/* 1277 */     if (path.indexOf('.') < 0)
/*      */     {
/* 1279 */       path = path + ".png";
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1284 */       String s = "shaders/" + StrUtils.removePrefix(path, "/");
/* 1285 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */       
/* 1287 */       if (inputstream == null) {
/*      */         
/* 1289 */         SMCLog.warning("Texture not found: " + path);
/* 1290 */         return null;
/*      */       } 
/*      */ 
/*      */       
/* 1294 */       IOUtils.closeQuietly(inputstream);
/* 1295 */       SimpleShaderTexture simpleshadertexture = new SimpleShaderTexture(s);
/* 1296 */       simpleshadertexture.loadTexture(mc.getResourceManager());
/* 1297 */       CustomTexture customtexture = new CustomTexture(textureUnit, s, (ITextureObject)simpleshadertexture);
/* 1298 */       return customtexture;
/*      */     
/*      */     }
/* 1301 */     catch (IOException ioexception) {
/*      */       
/* 1303 */       SMCLog.warning("Error loading texture: " + path);
/* 1304 */       SMCLog.warning("" + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 1305 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getTextureIndex(int stage, String name) {
/* 1311 */     if (stage == 0) {
/*      */       
/* 1313 */       if (name.equals("texture"))
/*      */       {
/* 1315 */         return 0;
/*      */       }
/*      */       
/* 1318 */       if (name.equals("lightmap"))
/*      */       {
/* 1320 */         return 1;
/*      */       }
/*      */       
/* 1323 */       if (name.equals("normals"))
/*      */       {
/* 1325 */         return 2;
/*      */       }
/*      */       
/* 1328 */       if (name.equals("specular"))
/*      */       {
/* 1330 */         return 3;
/*      */       }
/*      */       
/* 1333 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/*      */       {
/* 1335 */         return 4;
/*      */       }
/*      */       
/* 1338 */       if (name.equals("shadow"))
/*      */       {
/* 1340 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/* 1343 */       if (name.equals("shadowtex1"))
/*      */       {
/* 1345 */         return 5;
/*      */       }
/*      */       
/* 1348 */       if (name.equals("depthtex0"))
/*      */       {
/* 1350 */         return 6;
/*      */       }
/*      */       
/* 1353 */       if (name.equals("gaux1"))
/*      */       {
/* 1355 */         return 7;
/*      */       }
/*      */       
/* 1358 */       if (name.equals("gaux2"))
/*      */       {
/* 1360 */         return 8;
/*      */       }
/*      */       
/* 1363 */       if (name.equals("gaux3"))
/*      */       {
/* 1365 */         return 9;
/*      */       }
/*      */       
/* 1368 */       if (name.equals("gaux4"))
/*      */       {
/* 1370 */         return 10;
/*      */       }
/*      */       
/* 1373 */       if (name.equals("depthtex1"))
/*      */       {
/* 1375 */         return 12;
/*      */       }
/*      */       
/* 1378 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/*      */       {
/* 1380 */         return 13;
/*      */       }
/*      */       
/* 1383 */       if (name.equals("shadowcolor1"))
/*      */       {
/* 1385 */         return 14;
/*      */       }
/*      */       
/* 1388 */       if (name.equals("noisetex"))
/*      */       {
/* 1390 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1394 */     if (stage == 1 || stage == 2) {
/*      */       
/* 1396 */       if (name.equals("colortex0") || name.equals("colortex0"))
/*      */       {
/* 1398 */         return 0;
/*      */       }
/*      */       
/* 1401 */       if (name.equals("colortex1") || name.equals("gdepth"))
/*      */       {
/* 1403 */         return 1;
/*      */       }
/*      */       
/* 1406 */       if (name.equals("colortex2") || name.equals("gnormal"))
/*      */       {
/* 1408 */         return 2;
/*      */       }
/*      */       
/* 1411 */       if (name.equals("colortex3") || name.equals("composite"))
/*      */       {
/* 1413 */         return 3;
/*      */       }
/*      */       
/* 1416 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/*      */       {
/* 1418 */         return 4;
/*      */       }
/*      */       
/* 1421 */       if (name.equals("shadow"))
/*      */       {
/* 1423 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/* 1426 */       if (name.equals("shadowtex1"))
/*      */       {
/* 1428 */         return 5;
/*      */       }
/*      */       
/* 1431 */       if (name.equals("depthtex0") || name.equals("gdepthtex"))
/*      */       {
/* 1433 */         return 6;
/*      */       }
/*      */       
/* 1436 */       if (name.equals("colortex4") || name.equals("gaux1"))
/*      */       {
/* 1438 */         return 7;
/*      */       }
/*      */       
/* 1441 */       if (name.equals("colortex5") || name.equals("gaux2"))
/*      */       {
/* 1443 */         return 8;
/*      */       }
/*      */       
/* 1446 */       if (name.equals("colortex6") || name.equals("gaux3"))
/*      */       {
/* 1448 */         return 9;
/*      */       }
/*      */       
/* 1451 */       if (name.equals("colortex7") || name.equals("gaux4"))
/*      */       {
/* 1453 */         return 10;
/*      */       }
/*      */       
/* 1456 */       if (name.equals("depthtex1"))
/*      */       {
/* 1458 */         return 11;
/*      */       }
/*      */       
/* 1461 */       if (name.equals("depthtex2"))
/*      */       {
/* 1463 */         return 12;
/*      */       }
/*      */       
/* 1466 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/*      */       {
/* 1468 */         return 13;
/*      */       }
/*      */       
/* 1471 */       if (name.equals("shadowcolor1"))
/*      */       {
/* 1473 */         return 14;
/*      */       }
/*      */       
/* 1476 */       if (name.equals("noisetex"))
/*      */       {
/* 1478 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1482 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void bindCustomTextures(ICustomTexture[] cts) {
/* 1487 */     if (cts != null)
/*      */     {
/* 1489 */       for (int i = 0; i < cts.length; i++) {
/*      */         
/* 1491 */         ICustomTexture icustomtexture = cts[i];
/* 1492 */         GlStateManager.setActiveTexture(33984 + icustomtexture.getTextureUnit());
/* 1493 */         int j = icustomtexture.getTextureId();
/* 1494 */         int k = icustomtexture.getTarget();
/*      */         
/* 1496 */         if (k == 3553) {
/*      */           
/* 1498 */           GlStateManager.bindTexture(j);
/*      */         }
/*      */         else {
/*      */           
/* 1502 */           GL11.glBindTexture(k, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resetCustomTextures() {
/* 1510 */     deleteCustomTextures(customTexturesGbuffers);
/* 1511 */     deleteCustomTextures(customTexturesComposite);
/* 1512 */     deleteCustomTextures(customTexturesDeferred);
/* 1513 */     customTexturesGbuffers = null;
/* 1514 */     customTexturesComposite = null;
/* 1515 */     customTexturesDeferred = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void deleteCustomTextures(ICustomTexture[] cts) {
/* 1520 */     if (cts != null)
/*      */     {
/* 1522 */       for (int i = 0; i < cts.length; i++) {
/*      */         
/* 1524 */         ICustomTexture icustomtexture = cts[i];
/* 1525 */         icustomtexture.deleteTexture();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions(String screenName) {
/* 1532 */     ShaderOption[] ashaderoption = (ShaderOption[])shaderPackOptions.clone();
/*      */     
/* 1534 */     if (shaderPackGuiScreens == null) {
/*      */       
/* 1536 */       if (shaderPackProfiles != null) {
/*      */         
/* 1538 */         ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderPackProfiles, ashaderoption);
/* 1539 */         ashaderoption = (ShaderOption[])Config.addObjectToArray((Object[])ashaderoption, shaderoptionprofile, 0);
/*      */       } 
/*      */       
/* 1542 */       ashaderoption = getVisibleOptions(ashaderoption);
/* 1543 */       return ashaderoption;
/*      */     } 
/*      */ 
/*      */     
/* 1547 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/* 1548 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/*      */     
/* 1550 */     if (screenshaderoptions == null)
/*      */     {
/* 1552 */       return new ShaderOption[0];
/*      */     }
/*      */ 
/*      */     
/* 1556 */     ShaderOption[] ashaderoption1 = screenshaderoptions.getShaderOptions();
/* 1557 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1559 */     for (int i = 0; i < ashaderoption1.length; i++) {
/*      */       
/* 1561 */       ShaderOption shaderoption = ashaderoption1[i];
/*      */       
/* 1563 */       if (shaderoption == null) {
/*      */         
/* 1565 */         list.add(null);
/*      */       }
/* 1567 */       else if (shaderoption instanceof net.optifine.shaders.config.ShaderOptionRest) {
/*      */         
/* 1569 */         ShaderOption[] ashaderoption2 = getShaderOptionsRest(shaderPackGuiScreens, ashaderoption);
/* 1570 */         list.addAll(Arrays.asList(ashaderoption2));
/*      */       }
/*      */       else {
/*      */         
/* 1574 */         list.add(shaderoption);
/*      */       } 
/*      */     } 
/*      */     
/* 1578 */     ShaderOption[] ashaderoption3 = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1579 */     return ashaderoption3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getShaderPackColumns(String screenName, int def) {
/* 1586 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/*      */     
/* 1588 */     if (shaderPackGuiScreens == null)
/*      */     {
/* 1590 */       return def;
/*      */     }
/*      */ 
/*      */     
/* 1594 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/* 1595 */     return (screenshaderoptions == null) ? def : screenshaderoptions.getColumns();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> mapScreens, ShaderOption[] ops) {
/* 1601 */     Set<String> set = new HashSet<>();
/*      */     
/* 1603 */     for (String s : mapScreens.keySet()) {
/*      */       
/* 1605 */       ScreenShaderOptions screenshaderoptions = mapScreens.get(s);
/* 1606 */       ShaderOption[] ashaderoption = screenshaderoptions.getShaderOptions();
/*      */       
/* 1608 */       for (int i = 0; i < ashaderoption.length; i++) {
/*      */         
/* 1610 */         ShaderOption shaderoption = ashaderoption[i];
/*      */         
/* 1612 */         if (shaderoption != null)
/*      */         {
/* 1614 */           set.add(shaderoption.getName());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1619 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1621 */     for (int j = 0; j < ops.length; j++) {
/*      */       
/* 1623 */       ShaderOption shaderoption1 = ops[j];
/*      */       
/* 1625 */       if (shaderoption1.isVisible()) {
/*      */         
/* 1627 */         String s1 = shaderoption1.getName();
/*      */         
/* 1629 */         if (!set.contains(s1))
/*      */         {
/* 1631 */           list.add(shaderoption1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1636 */     ShaderOption[] ashaderoption1 = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1637 */     return ashaderoption1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption getShaderOption(String name) {
/* 1642 */     return ShaderUtils.getShaderOption(name, shaderPackOptions);
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions() {
/* 1647 */     return shaderPackOptions;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShaderPackOptionSlider(String name) {
/* 1652 */     return (shaderPackOptionSliders == null) ? false : shaderPackOptionSliders.contains(name);
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getVisibleOptions(ShaderOption[] ops) {
/* 1657 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1659 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1661 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1663 */       if (shaderoption.isVisible())
/*      */       {
/* 1665 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1669 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1670 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void saveShaderPackOptions() {
/* 1675 */     saveShaderPackOptions(shaderPackOptions, shaderPack);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void saveShaderPackOptions(ShaderOption[] sos, IShaderPack sp) {
/* 1680 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*      */     
/* 1682 */     if (shaderPackOptions != null)
/*      */     {
/* 1684 */       for (int i = 0; i < sos.length; i++) {
/*      */         
/* 1686 */         ShaderOption shaderoption = sos[i];
/*      */         
/* 1688 */         if (shaderoption.isChanged() && shaderoption.isEnabled())
/*      */         {
/* 1690 */           propertiesOrdered.setProperty(shaderoption.getName(), shaderoption.getValue());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1697 */       saveOptionProperties(sp, (Properties)propertiesOrdered);
/*      */     }
/* 1699 */     catch (IOException ioexception) {
/*      */       
/* 1701 */       Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
/* 1702 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void saveOptionProperties(IShaderPack sp, Properties props) throws IOException {
/* 1708 */     String s = "shaderpacks/" + sp.getName() + ".txt";
/* 1709 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1711 */     if (props.isEmpty()) {
/*      */       
/* 1713 */       file1.delete();
/*      */     }
/*      */     else {
/*      */       
/* 1717 */       FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 1718 */       props.store(fileoutputstream, (String)null);
/* 1719 */       fileoutputstream.flush();
/* 1720 */       fileoutputstream.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] loadShaderPackOptions() {
/*      */     try {
/* 1728 */       String[] astring = programs.getProgramNames();
/* 1729 */       ShaderOption[] ashaderoption = ShaderPackParser.parseShaderPackOptions(shaderPack, astring, shaderPackDimensions);
/* 1730 */       Properties properties = loadOptionProperties(shaderPack);
/*      */       
/* 1732 */       for (int i = 0; i < ashaderoption.length; i++) {
/*      */         
/* 1734 */         ShaderOption shaderoption = ashaderoption[i];
/* 1735 */         String s = properties.getProperty(shaderoption.getName());
/*      */         
/* 1737 */         if (s != null) {
/*      */           
/* 1739 */           shaderoption.resetValue();
/*      */           
/* 1741 */           if (!shaderoption.setValue(s))
/*      */           {
/* 1743 */             Config.warn("[Shaders] Invalid value, option: " + shaderoption.getName() + ", value: " + s);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1748 */       return ashaderoption;
/*      */     }
/* 1750 */     catch (IOException ioexception) {
/*      */       
/* 1752 */       Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
/* 1753 */       ioexception.printStackTrace();
/* 1754 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Properties loadOptionProperties(IShaderPack sp) throws IOException {
/* 1760 */     PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 1761 */     String s = "shaderpacks/" + sp.getName() + ".txt";
/* 1762 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1764 */     if (file1.exists() && file1.isFile() && file1.canRead()) {
/*      */       
/* 1766 */       FileInputStream fileinputstream = new FileInputStream(file1);
/* 1767 */       propertiesOrdered.load(fileinputstream);
/* 1768 */       fileinputstream.close();
/* 1769 */       return (Properties)propertiesOrdered;
/*      */     } 
/*      */ 
/*      */     
/* 1773 */     return (Properties)propertiesOrdered;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getChangedOptions(ShaderOption[] ops) {
/* 1779 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1781 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1783 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1785 */       if (shaderoption.isEnabled() && shaderoption.isChanged())
/*      */       {
/* 1787 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1791 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1792 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String applyOptions(String line, ShaderOption[] ops) {
/* 1797 */     if (ops != null && ops.length > 0) {
/*      */       
/* 1799 */       for (int i = 0; i < ops.length; i++) {
/*      */         
/* 1801 */         ShaderOption shaderoption = ops[i];
/*      */         
/* 1803 */         if (shaderoption.matchesLine(line)) {
/*      */           
/* 1805 */           line = shaderoption.getSourceLine();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1810 */       return line;
/*      */     } 
/*      */ 
/*      */     
/* 1814 */     return line;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ArrayList listOfShaders() {
/* 1820 */     ArrayList<String> arraylist = new ArrayList<>();
/* 1821 */     arraylist.add("OFF");
/* 1822 */     arraylist.add("(internal)");
/* 1823 */     int i = arraylist.size();
/*      */ 
/*      */     
/*      */     try {
/* 1827 */       if (!shaderPacksDir.exists())
/*      */       {
/* 1829 */         shaderPacksDir.mkdir();
/*      */       }
/*      */       
/* 1832 */       File[] afile = shaderPacksDir.listFiles();
/*      */       
/* 1834 */       for (int j = 0; j < afile.length; j++) {
/*      */         
/* 1836 */         File file1 = afile[j];
/* 1837 */         String s = file1.getName();
/*      */         
/* 1839 */         if (file1.isDirectory()) {
/*      */           
/* 1841 */           if (!s.equals("debug"))
/*      */           {
/* 1843 */             File file2 = new File(file1, "shaders");
/*      */             
/* 1845 */             if (file2.exists() && file2.isDirectory())
/*      */             {
/* 1847 */               arraylist.add(s);
/*      */             }
/*      */           }
/*      */         
/* 1851 */         } else if (file1.isFile() && s.toLowerCase().endsWith(".zip")) {
/*      */           
/* 1853 */           arraylist.add(s);
/*      */         }
/*      */       
/*      */       } 
/* 1857 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1862 */     List<String> list = arraylist.subList(i, arraylist.size());
/* 1863 */     Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
/* 1864 */     return arraylist;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkFramebufferStatus(String location) {
/* 1869 */     int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 1871 */     if (i != 36053)
/*      */     {
/* 1873 */       System.err.format("FramebufferStatus 0x%04X at %s\n", new Object[] { Integer.valueOf(i), location });
/*      */     }
/*      */     
/* 1876 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location) {
/* 1881 */     int i = GlStateManager.glGetError();
/*      */     
/* 1883 */     if (i != 0 && GlErrors.isEnabled(i)) {
/*      */       
/* 1885 */       String s = Config.getGlErrorString(i);
/* 1886 */       String s1 = getErrorInfo(i, location);
/* 1887 */       String s2 = String.format("OpenGL error: %s (%s)%s, at: %s", new Object[] { Integer.valueOf(i), s, s1, location });
/* 1888 */       SMCLog.severe(s2);
/*      */       
/* 1890 */       if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorShaders", 10000L)) {
/*      */         
/* 1892 */         String s3 = I18n.format("of.message.openglError", new Object[] { Integer.valueOf(i), s });
/* 1893 */         printChat(s3);
/*      */       } 
/*      */     } 
/*      */     
/* 1897 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getErrorInfo(int errorCode, String location) {
/* 1902 */     StringBuilder stringbuilder = new StringBuilder();
/*      */     
/* 1904 */     if (errorCode == 1286) {
/*      */       
/* 1906 */       int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 1907 */       String s = getFramebufferStatusText(i);
/* 1908 */       String s1 = ", fbStatus: " + i + " (" + s + ")";
/* 1909 */       stringbuilder.append(s1);
/*      */     } 
/*      */     
/* 1912 */     String s2 = activeProgram.getName();
/*      */     
/* 1914 */     if (s2.isEmpty())
/*      */     {
/* 1916 */       s2 = "none";
/*      */     }
/*      */     
/* 1919 */     stringbuilder.append(", program: " + s2);
/* 1920 */     Program program = getProgramById(activeProgramID);
/*      */     
/* 1922 */     if (program != activeProgram) {
/*      */       
/* 1924 */       String s3 = program.getName();
/*      */       
/* 1926 */       if (s3.isEmpty())
/*      */       {
/* 1928 */         s3 = "none";
/*      */       }
/*      */       
/* 1931 */       stringbuilder.append(" (" + s3 + ")");
/*      */     } 
/*      */     
/* 1934 */     if (location.equals("setDrawBuffers"))
/*      */     {
/* 1936 */       stringbuilder.append(", drawBuffers: " + activeProgram.getDrawBufSettings());
/*      */     }
/*      */     
/* 1939 */     return stringbuilder.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static Program getProgramById(int programID) {
/* 1944 */     for (int i = 0; i < ProgramsAll.length; i++) {
/*      */       
/* 1946 */       Program program = ProgramsAll[i];
/*      */       
/* 1948 */       if (program.getId() == programID)
/*      */       {
/* 1950 */         return program;
/*      */       }
/*      */     } 
/*      */     
/* 1954 */     return ProgramNone;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getFramebufferStatusText(int fbStatusCode) {
/* 1959 */     switch (fbStatusCode) {
/*      */       
/*      */       case 33305:
/* 1962 */         return "Undefined";
/*      */       
/*      */       case 36053:
/* 1965 */         return "Complete";
/*      */       
/*      */       case 36054:
/* 1968 */         return "Incomplete attachment";
/*      */       
/*      */       case 36055:
/* 1971 */         return "Incomplete missing attachment";
/*      */       
/*      */       case 36059:
/* 1974 */         return "Incomplete draw buffer";
/*      */       
/*      */       case 36060:
/* 1977 */         return "Incomplete read buffer";
/*      */       
/*      */       case 36061:
/* 1980 */         return "Unsupported";
/*      */       
/*      */       case 36182:
/* 1983 */         return "Incomplete multisample";
/*      */       
/*      */       case 36264:
/* 1986 */         return "Incomplete layer targets";
/*      */     } 
/*      */     
/* 1989 */     return "Unknown";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void printChat(String str) {
/* 1995 */     mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(str));
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printChatAndLogError(String str) {
/* 2000 */     SMCLog.severe(str);
/* 2001 */     mc.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText(str));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void printIntBuffer(String title, IntBuffer buf) {
/* 2006 */     StringBuilder stringbuilder = new StringBuilder(128);
/* 2007 */     stringbuilder.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
/* 2008 */     int i = buf.limit();
/*      */     
/* 2010 */     for (int j = 0; j < i; j++)
/*      */     {
/* 2012 */       stringbuilder.append(" ").append(buf.get(j));
/*      */     }
/*      */     
/* 2015 */     stringbuilder.append("]");
/* 2016 */     SMCLog.info(stringbuilder.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void startup(Minecraft mcs) {
/* 2021 */     checkShadersModInstalled();
/* 2022 */     mc = mcs;
/* 2023 */     capabilities = GLContext.getCapabilities();
/* 2024 */     glVersionString = GL11.glGetString(7938);
/* 2025 */     glVendorString = GL11.glGetString(7936);
/* 2026 */     glRendererString = GL11.glGetString(7937);
/* 2027 */     SMCLog.info("OpenGL Version: " + glVersionString);
/* 2028 */     SMCLog.info("Vendor:  " + glVendorString);
/* 2029 */     SMCLog.info("Renderer: " + glRendererString);
/* 2030 */     SMCLog.info("Capabilities: " + (capabilities.OpenGL20 ? " 2.0 " : " - ") + (capabilities.OpenGL21 ? " 2.1 " : " - ") + (capabilities.OpenGL30 ? " 3.0 " : " - ") + (capabilities.OpenGL32 ? " 3.2 " : " - ") + (capabilities.OpenGL40 ? " 4.0 " : " - "));
/* 2031 */     SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(34852));
/* 2032 */     SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(36063));
/* 2033 */     SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(34930));
/* 2034 */     hasGlGenMipmap = capabilities.OpenGL30;
/* 2035 */     loadConfig();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateBlockLightLevel() {
/* 2040 */     if (isOldLighting()) {
/*      */       
/* 2042 */       blockLightLevel05 = 0.5F;
/* 2043 */       blockLightLevel06 = 0.6F;
/* 2044 */       blockLightLevel08 = 0.8F;
/*      */     }
/*      */     else {
/*      */       
/* 2048 */       blockLightLevel05 = 1.0F;
/* 2049 */       blockLightLevel06 = 1.0F;
/* 2050 */       blockLightLevel08 = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOldHandLight() {
/* 2056 */     return !configOldHandLight.isDefault() ? configOldHandLight.isTrue() : (!shaderPackOldHandLight.isDefault() ? shaderPackOldHandLight.isTrue() : true);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 2061 */     return !shaderPackDynamicHandLight.isDefault() ? shaderPackDynamicHandLight.isTrue() : true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOldLighting() {
/* 2066 */     return !configOldLighting.isDefault() ? configOldLighting.isTrue() : (!shaderPackOldLighting.isDefault() ? shaderPackOldLighting.isTrue() : true);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderShadowTranslucent() {
/* 2071 */     return !shaderPackShadowTranslucent.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isUnderwaterOverlay() {
/* 2076 */     return !shaderPackUnderwaterOverlay.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSun() {
/* 2081 */     return !shaderPackSun.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMoon() {
/* 2086 */     return !shaderPackMoon.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isVignette() {
/* 2091 */     return !shaderPackVignette.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderBackFace(EnumWorldBlockLayer blockLayerIn) {
/* 2096 */     switch (blockLayerIn) {
/*      */       
/*      */       case GBUFFERS:
/* 2099 */         return shaderPackBackFaceSolid.isTrue();
/*      */       
/*      */       case DEFERRED:
/* 2102 */         return shaderPackBackFaceCutout.isTrue();
/*      */       
/*      */       case COMPOSITE:
/* 2105 */         return shaderPackBackFaceCutoutMipped.isTrue();
/*      */       
/*      */       case SHADOW:
/* 2108 */         return shaderPackBackFaceTranslucent.isTrue();
/*      */     } 
/*      */     
/* 2111 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRainDepth() {
/* 2117 */     return shaderPackRainDepth.isTrue();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBeaconBeamDepth() {
/* 2122 */     return shaderPackBeaconBeamDepth.isTrue();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSeparateAo() {
/* 2127 */     return shaderPackSeparateAo.isTrue();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFrustumCulling() {
/* 2132 */     return !shaderPackFrustumCulling.isFalse();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init() {
/*      */     boolean flag;
/* 2139 */     if (!isInitializedOnce) {
/*      */       
/* 2141 */       isInitializedOnce = true;
/* 2142 */       flag = true;
/*      */     }
/*      */     else {
/*      */       
/* 2146 */       flag = false;
/*      */     } 
/*      */     
/* 2149 */     if (!isShaderPackInitialized) {
/*      */       
/* 2151 */       checkGLError("Shaders.init pre");
/*      */       
/* 2153 */       if (getShaderPackName() != null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2158 */       if (!capabilities.OpenGL20)
/*      */       {
/* 2160 */         printChatAndLogError("No OpenGL 2.0");
/*      */       }
/*      */       
/* 2163 */       if (!capabilities.GL_EXT_framebuffer_object)
/*      */       {
/* 2165 */         printChatAndLogError("No EXT_framebuffer_object");
/*      */       }
/*      */       
/* 2168 */       dfbDrawBuffers.position(0).limit(8);
/* 2169 */       dfbColorTextures.position(0).limit(16);
/* 2170 */       dfbDepthTextures.position(0).limit(3);
/* 2171 */       sfbDrawBuffers.position(0).limit(8);
/* 2172 */       sfbDepthTextures.position(0).limit(2);
/* 2173 */       sfbColorTextures.position(0).limit(8);
/* 2174 */       usedColorBuffers = 4;
/* 2175 */       usedDepthBuffers = 1;
/* 2176 */       usedShadowColorBuffers = 0;
/* 2177 */       usedShadowDepthBuffers = 0;
/* 2178 */       usedColorAttachs = 1;
/* 2179 */       usedDrawBuffers = 1;
/* 2180 */       Arrays.fill(gbuffersFormat, 6408);
/* 2181 */       Arrays.fill(gbuffersClear, true);
/* 2182 */       Arrays.fill((Object[])gbuffersClearColor, (Object)null);
/* 2183 */       Arrays.fill(shadowHardwareFilteringEnabled, false);
/* 2184 */       Arrays.fill(shadowMipmapEnabled, false);
/* 2185 */       Arrays.fill(shadowFilterNearest, false);
/* 2186 */       Arrays.fill(shadowColorMipmapEnabled, false);
/* 2187 */       Arrays.fill(shadowColorFilterNearest, false);
/* 2188 */       centerDepthSmoothEnabled = false;
/* 2189 */       noiseTextureEnabled = false;
/* 2190 */       sunPathRotation = 0.0F;
/* 2191 */       shadowIntervalSize = 2.0F;
/* 2192 */       shadowMapWidth = 1024;
/* 2193 */       shadowMapHeight = 1024;
/* 2194 */       spShadowMapWidth = 1024;
/* 2195 */       spShadowMapHeight = 1024;
/* 2196 */       shadowMapFOV = 90.0F;
/* 2197 */       shadowMapHalfPlane = 160.0F;
/* 2198 */       shadowMapIsOrtho = true;
/* 2199 */       shadowDistanceRenderMul = -1.0F;
/* 2200 */       aoLevel = -1.0F;
/* 2201 */       useEntityAttrib = false;
/* 2202 */       useMidTexCoordAttrib = false;
/* 2203 */       useTangentAttrib = false;
/* 2204 */       waterShadowEnabled = false;
/* 2205 */       hasGeometryShaders = false;
/* 2206 */       updateBlockLightLevel();
/* 2207 */       Smoother.resetValues();
/* 2208 */       shaderUniforms.reset();
/*      */       
/* 2210 */       if (customUniforms != null)
/*      */       {
/* 2212 */         customUniforms.reset();
/*      */       }
/*      */       
/* 2215 */       ShaderProfile shaderprofile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
/* 2216 */       String s = "";
/*      */       
/* 2218 */       if (currentWorld != null) {
/*      */         
/* 2220 */         int i = currentWorld.provider.getDimensionId();
/*      */         
/* 2222 */         if (shaderPackDimensions.contains(Integer.valueOf(i)))
/*      */         {
/* 2224 */           s = "world" + i + "/";
/*      */         }
/*      */       } 
/*      */       
/* 2228 */       for (int k = 0; k < ProgramsAll.length; k++) {
/*      */         
/* 2230 */         Program program = ProgramsAll[k];
/* 2231 */         program.resetId();
/* 2232 */         program.resetConfiguration();
/*      */         
/* 2234 */         if (program.getProgramStage() != ProgramStage.NONE) {
/*      */           
/* 2236 */           String s1 = program.getName();
/* 2237 */           String s2 = s + s1;
/* 2238 */           boolean flag1 = true;
/*      */           
/* 2240 */           if (shaderPackProgramConditions.containsKey(s2))
/*      */           {
/* 2242 */             flag1 = (flag1 && ((IExpressionBool)shaderPackProgramConditions.get(s2)).eval());
/*      */           }
/*      */           
/* 2245 */           if (shaderprofile != null)
/*      */           {
/* 2247 */             flag1 = (flag1 && !shaderprofile.isProgramDisabled(s2));
/*      */           }
/*      */           
/* 2250 */           if (!flag1) {
/*      */             
/* 2252 */             SMCLog.info("Program disabled: " + s2);
/* 2253 */             s1 = "<disabled>";
/* 2254 */             s2 = s + s1;
/*      */           } 
/*      */           
/* 2257 */           String s3 = "/shaders/" + s2;
/* 2258 */           String s4 = s3 + ".vsh";
/* 2259 */           String s5 = s3 + ".gsh";
/* 2260 */           String s6 = s3 + ".fsh";
/* 2261 */           setupProgram(program, s4, s5, s6);
/* 2262 */           int j = program.getId();
/*      */           
/* 2264 */           if (j > 0)
/*      */           {
/* 2266 */             SMCLog.info("Program loaded: " + s2);
/*      */           }
/*      */           
/* 2269 */           initDrawBuffers(program);
/* 2270 */           updateToggleBuffers(program);
/*      */         } 
/*      */       } 
/*      */       
/* 2274 */       hasDeferredPrograms = false;
/*      */       
/* 2276 */       for (int l = 0; l < ProgramsDeferred.length; l++) {
/*      */         
/* 2278 */         if (ProgramsDeferred[l].getId() != 0) {
/*      */           
/* 2280 */           hasDeferredPrograms = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 2285 */       usedColorAttachs = usedColorBuffers;
/* 2286 */       shadowPassInterval = (usedShadowDepthBuffers > 0) ? 1 : 0;
/* 2287 */       shouldSkipDefaultShadow = (usedShadowDepthBuffers > 0);
/* 2288 */       SMCLog.info("usedColorBuffers: " + usedColorBuffers);
/* 2289 */       SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
/* 2290 */       SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
/* 2291 */       SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
/* 2292 */       SMCLog.info("usedColorAttachs: " + usedColorAttachs);
/* 2293 */       SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
/* 2294 */       dfbDrawBuffers.position(0).limit(usedDrawBuffers);
/* 2295 */       dfbColorTextures.position(0).limit(usedColorBuffers * 2);
/* 2296 */       dfbColorTexturesFlip.reset();
/*      */       
/* 2298 */       for (int i1 = 0; i1 < usedDrawBuffers; i1++)
/*      */       {
/* 2300 */         dfbDrawBuffers.put(i1, 36064 + i1);
/*      */       }
/*      */       
/* 2303 */       int j1 = GL11.glGetInteger(34852);
/*      */       
/* 2305 */       if (usedDrawBuffers > j1)
/*      */       {
/* 2307 */         printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + j1);
/*      */       }
/*      */       
/* 2310 */       sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
/*      */       
/* 2312 */       for (int k1 = 0; k1 < usedShadowColorBuffers; k1++)
/*      */       {
/* 2314 */         sfbDrawBuffers.put(k1, 36064 + k1);
/*      */       }
/*      */       
/* 2317 */       for (int l1 = 0; l1 < ProgramsAll.length; l1++) {
/*      */         
/* 2319 */         Program program1 = ProgramsAll[l1];
/*      */         
/*      */         Program program2;
/* 2322 */         for (program2 = program1; program2.getId() == 0 && program2.getProgramBackup() != program2; program2 = program2.getProgramBackup());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2327 */         if (program2 != program1 && program1 != ProgramShadow)
/*      */         {
/* 2329 */           program1.copyFrom(program2);
/*      */         }
/*      */       } 
/*      */       
/* 2333 */       resize();
/* 2334 */       resizeShadow();
/*      */       
/* 2336 */       if (noiseTextureEnabled)
/*      */       {
/* 2338 */         setupNoiseTexture();
/*      */       }
/*      */       
/* 2341 */       if (defaultTexture == null)
/*      */       {
/* 2343 */         defaultTexture = ShadersTex.createDefaultTexture();
/*      */       }
/*      */       
/* 2346 */       GlStateManager.pushMatrix();
/* 2347 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 2348 */       preCelestialRotate();
/* 2349 */       postCelestialRotate();
/* 2350 */       GlStateManager.popMatrix();
/* 2351 */       isShaderPackInitialized = true;
/* 2352 */       loadEntityDataMap();
/* 2353 */       resetDisplayLists();
/*      */       
/* 2355 */       if (!flag);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2360 */       checkGLError("Shaders.init");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void initDrawBuffers(Program p) {
/* 2366 */     int i = GL11.glGetInteger(34852);
/* 2367 */     Arrays.fill(p.getToggleColorTextures(), false);
/*      */     
/* 2369 */     if (p == ProgramFinal) {
/*      */       
/* 2371 */       p.setDrawBuffers(null);
/*      */     }
/* 2373 */     else if (p.getId() == 0) {
/*      */       
/* 2375 */       if (p == ProgramShadow)
/*      */       {
/* 2377 */         p.setDrawBuffers(drawBuffersNone);
/*      */       }
/*      */       else
/*      */       {
/* 2381 */         p.setDrawBuffers(drawBuffersColorAtt0);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2386 */       String s = p.getDrawBufSettings();
/*      */       
/* 2388 */       if (s == null) {
/*      */         
/* 2390 */         if (p != ProgramShadow && p != ProgramShadowSolid && p != ProgramShadowCutout)
/*      */         {
/* 2392 */           p.setDrawBuffers(dfbDrawBuffers);
/* 2393 */           usedDrawBuffers = usedColorBuffers;
/* 2394 */           Arrays.fill(p.getToggleColorTextures(), 0, usedColorBuffers, true);
/*      */         }
/*      */         else
/*      */         {
/* 2398 */           p.setDrawBuffers(sfbDrawBuffers);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2403 */         IntBuffer intbuffer = p.getDrawBuffersBuffer();
/* 2404 */         int j = s.length();
/* 2405 */         usedDrawBuffers = Math.max(usedDrawBuffers, j);
/* 2406 */         j = Math.min(j, i);
/* 2407 */         p.setDrawBuffers(intbuffer);
/* 2408 */         intbuffer.limit(j);
/*      */         
/* 2410 */         for (int k = 0; k < j; k++) {
/*      */           
/* 2412 */           int l = getDrawBuffer(p, s, k);
/* 2413 */           intbuffer.put(k, l);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getDrawBuffer(Program p, String str, int ic) {
/* 2421 */     int i = 0;
/*      */     
/* 2423 */     if (ic >= str.length())
/*      */     {
/* 2425 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 2429 */     int j = str.charAt(ic) - 48;
/*      */     
/* 2431 */     if (p == ProgramShadow) {
/*      */       
/* 2433 */       if (j >= 0 && j <= 1) {
/*      */         
/* 2435 */         i = j + 36064;
/* 2436 */         usedShadowColorBuffers = Math.max(usedShadowColorBuffers, j);
/*      */       } 
/*      */       
/* 2439 */       return i;
/*      */     } 
/*      */ 
/*      */     
/* 2443 */     if (j >= 0 && j <= 7) {
/*      */       
/* 2445 */       p.getToggleColorTextures()[j] = true;
/* 2446 */       i = j + 36064;
/* 2447 */       usedColorAttachs = Math.max(usedColorAttachs, j);
/* 2448 */       usedColorBuffers = Math.max(usedColorBuffers, j);
/*      */     } 
/*      */     
/* 2451 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateToggleBuffers(Program p) {
/* 2458 */     boolean[] aboolean = p.getToggleColorTextures();
/* 2459 */     Boolean[] aboolean1 = p.getBuffersFlip();
/*      */     
/* 2461 */     for (int i = 0; i < aboolean1.length; i++) {
/*      */       
/* 2463 */       Boolean obool = aboolean1[i];
/*      */       
/* 2465 */       if (obool != null)
/*      */       {
/* 2467 */         aboolean[i] = obool.booleanValue();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayLists() {
/* 2474 */     SMCLog.info("Reset model renderers");
/* 2475 */     countResetDisplayLists++;
/* 2476 */     SMCLog.info("Reset world renderers");
/* 2477 */     mc.renderGlobal.loadRenderers();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupProgram(Program program, String vShaderPath, String gShaderPath, String fShaderPath) {
/* 2482 */     checkGLError("pre setupProgram");
/* 2483 */     int i = ARBShaderObjects.glCreateProgramObjectARB();
/* 2484 */     checkGLError("create");
/*      */     
/* 2486 */     if (i != 0) {
/*      */       
/* 2488 */       progUseEntityAttrib = false;
/* 2489 */       progUseMidTexCoordAttrib = false;
/* 2490 */       progUseTangentAttrib = false;
/* 2491 */       int j = createVertShader(program, vShaderPath);
/* 2492 */       int k = createGeomShader(program, gShaderPath);
/* 2493 */       int l = createFragShader(program, fShaderPath);
/* 2494 */       checkGLError("create");
/*      */       
/* 2496 */       if (j == 0 && k == 0 && l == 0) {
/*      */         
/* 2498 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2499 */         i = 0;
/* 2500 */         program.resetId();
/*      */       }
/*      */       else {
/*      */         
/* 2504 */         if (j != 0) {
/*      */           
/* 2506 */           ARBShaderObjects.glAttachObjectARB(i, j);
/* 2507 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2510 */         if (k != 0) {
/*      */           
/* 2512 */           ARBShaderObjects.glAttachObjectARB(i, k);
/* 2513 */           checkGLError("attach");
/*      */           
/* 2515 */           if (progArbGeometryShader4) {
/*      */             
/* 2517 */             ARBGeometryShader4.glProgramParameteriARB(i, 36315, 4);
/* 2518 */             ARBGeometryShader4.glProgramParameteriARB(i, 36316, 5);
/* 2519 */             ARBGeometryShader4.glProgramParameteriARB(i, 36314, progMaxVerticesOut);
/* 2520 */             checkGLError("arbGeometryShader4");
/*      */           } 
/*      */           
/* 2523 */           hasGeometryShaders = true;
/*      */         } 
/*      */         
/* 2526 */         if (l != 0) {
/*      */           
/* 2528 */           ARBShaderObjects.glAttachObjectARB(i, l);
/* 2529 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2532 */         if (progUseEntityAttrib) {
/*      */           
/* 2534 */           ARBVertexShader.glBindAttribLocationARB(i, entityAttrib, "mc_Entity");
/* 2535 */           checkGLError("mc_Entity");
/*      */         } 
/*      */         
/* 2538 */         if (progUseMidTexCoordAttrib) {
/*      */           
/* 2540 */           ARBVertexShader.glBindAttribLocationARB(i, midTexCoordAttrib, "mc_midTexCoord");
/* 2541 */           checkGLError("mc_midTexCoord");
/*      */         } 
/*      */         
/* 2544 */         if (progUseTangentAttrib) {
/*      */           
/* 2546 */           ARBVertexShader.glBindAttribLocationARB(i, tangentAttrib, "at_tangent");
/* 2547 */           checkGLError("at_tangent");
/*      */         } 
/*      */         
/* 2550 */         ARBShaderObjects.glLinkProgramARB(i);
/*      */         
/* 2552 */         if (GL20.glGetProgrami(i, 35714) != 1)
/*      */         {
/* 2554 */           SMCLog.severe("Error linking program: " + i + " (" + program.getName() + ")");
/*      */         }
/*      */         
/* 2557 */         printLogInfo(i, program.getName());
/*      */         
/* 2559 */         if (j != 0) {
/*      */           
/* 2561 */           ARBShaderObjects.glDetachObjectARB(i, j);
/* 2562 */           ARBShaderObjects.glDeleteObjectARB(j);
/*      */         } 
/*      */         
/* 2565 */         if (k != 0) {
/*      */           
/* 2567 */           ARBShaderObjects.glDetachObjectARB(i, k);
/* 2568 */           ARBShaderObjects.glDeleteObjectARB(k);
/*      */         } 
/*      */         
/* 2571 */         if (l != 0) {
/*      */           
/* 2573 */           ARBShaderObjects.glDetachObjectARB(i, l);
/* 2574 */           ARBShaderObjects.glDeleteObjectARB(l);
/*      */         } 
/*      */         
/* 2577 */         program.setId(i);
/* 2578 */         program.setRef(i);
/* 2579 */         useProgram(program);
/* 2580 */         ARBShaderObjects.glValidateProgramARB(i);
/* 2581 */         useProgram(ProgramNone);
/* 2582 */         printLogInfo(i, program.getName());
/* 2583 */         int i1 = GL20.glGetProgrami(i, 35715);
/*      */         
/* 2585 */         if (i1 != 1) {
/*      */           
/* 2587 */           String s = "\"";
/* 2588 */           printChatAndLogError("[Shaders] Error: Invalid program " + s + program.getName() + s);
/* 2589 */           ARBShaderObjects.glDeleteObjectARB(i);
/* 2590 */           i = 0;
/* 2591 */           program.resetId();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int createVertShader(Program program, String filename) {
/* 2599 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35633);
/*      */     
/* 2601 */     if (i == 0)
/*      */     {
/* 2603 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2607 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2608 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2612 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2614 */     catch (Exception var10) {
/*      */       
/* 2616 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2617 */       return 0;
/*      */     } 
/*      */     
/* 2620 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2621 */     List<String> list = new ArrayList<>();
/*      */     
/* 2623 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2627 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2628 */         MacroState macrostate = new MacroState();
/*      */ 
/*      */         
/*      */         while (true) {
/* 2632 */           String s = bufferedreader.readLine();
/*      */           
/* 2634 */           if (s == null) {
/*      */             
/* 2636 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2640 */           s = applyOptions(s, ashaderoption);
/* 2641 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2643 */           if (macrostate.processLine(s)) {
/*      */             
/* 2645 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2647 */             if (shaderline != null) {
/*      */               
/* 2649 */               if (shaderline.isAttribute("mc_Entity")) {
/*      */                 
/* 2651 */                 useEntityAttrib = true;
/* 2652 */                 progUseEntityAttrib = true;
/*      */               }
/* 2654 */               else if (shaderline.isAttribute("mc_midTexCoord")) {
/*      */                 
/* 2656 */                 useMidTexCoordAttrib = true;
/* 2657 */                 progUseMidTexCoordAttrib = true;
/*      */               }
/* 2659 */               else if (shaderline.isAttribute("at_tangent")) {
/*      */                 
/* 2661 */                 useTangentAttrib = true;
/* 2662 */                 progUseTangentAttrib = true;
/*      */               } 
/*      */               
/* 2665 */               if (shaderline.isConstInt("countInstances"))
/*      */               {
/* 2667 */                 program.setCountInstances(shaderline.getValueInt());
/* 2668 */                 SMCLog.info("countInstances: " + program.getCountInstances());
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/* 2674 */       } catch (Exception exception) {
/*      */         
/* 2676 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2677 */         exception.printStackTrace();
/* 2678 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2679 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2683 */     if (saveFinalShaders)
/*      */     {
/* 2685 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2688 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2689 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2691 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 2693 */       SMCLog.severe("Error compiling vertex shader: " + filename);
/*      */     }
/*      */     
/* 2696 */     printShaderLogInfo(i, filename, list);
/* 2697 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int createGeomShader(Program program, String filename) {
/* 2703 */     int i = ARBShaderObjects.glCreateShaderObjectARB(36313);
/*      */     
/* 2705 */     if (i == 0)
/*      */     {
/* 2707 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2711 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2712 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2716 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2718 */     catch (Exception var11) {
/*      */       
/* 2720 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2721 */       return 0;
/*      */     } 
/*      */     
/* 2724 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2725 */     List<String> list = new ArrayList<>();
/* 2726 */     progArbGeometryShader4 = false;
/* 2727 */     progMaxVerticesOut = 3;
/*      */     
/* 2729 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2733 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2734 */         MacroState macrostate = new MacroState();
/*      */ 
/*      */         
/*      */         while (true) {
/* 2738 */           String s = bufferedreader.readLine();
/*      */           
/* 2740 */           if (s == null) {
/*      */             
/* 2742 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2746 */           s = applyOptions(s, ashaderoption);
/* 2747 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2749 */           if (macrostate.processLine(s)) {
/*      */             
/* 2751 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2753 */             if (shaderline != null)
/*      */             {
/* 2755 */               if (shaderline.isExtension("GL_ARB_geometry_shader4")) {
/*      */                 
/* 2757 */                 String s1 = Config.normalize(shaderline.getValue());
/*      */                 
/* 2759 */                 if (s1.equals("enable") || s1.equals("require") || s1.equals("warn"))
/*      */                 {
/* 2761 */                   progArbGeometryShader4 = true;
/*      */                 }
/*      */               } 
/*      */               
/* 2765 */               if (shaderline.isConstInt("maxVerticesOut"))
/*      */               {
/* 2767 */                 progMaxVerticesOut = shaderline.getValueInt();
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/* 2773 */       } catch (Exception exception) {
/*      */         
/* 2775 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2776 */         exception.printStackTrace();
/* 2777 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2778 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2782 */     if (saveFinalShaders)
/*      */     {
/* 2784 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2787 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2788 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2790 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 2792 */       SMCLog.severe("Error compiling geometry shader: " + filename);
/*      */     }
/*      */     
/* 2795 */     printShaderLogInfo(i, filename, list);
/* 2796 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int createFragShader(Program program, String filename) {
/* 2802 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35632);
/*      */     
/* 2804 */     if (i == 0)
/*      */     {
/* 2806 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2810 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2811 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2815 */       bufferedreader = new BufferedReader(getShaderReader(filename));
/*      */     }
/* 2817 */     catch (Exception var14) {
/*      */       
/* 2819 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2820 */       return 0;
/*      */     } 
/*      */     
/* 2823 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2824 */     List<String> list = new ArrayList<>();
/*      */     
/* 2826 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2830 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/* 2831 */         MacroState macrostate = new MacroState();
/*      */ 
/*      */         
/*      */         while (true) {
/* 2835 */           String s = bufferedreader.readLine();
/*      */           
/* 2837 */           if (s == null) {
/*      */             
/* 2839 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2843 */           s = applyOptions(s, ashaderoption);
/* 2844 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2846 */           if (macrostate.processLine(s))
/*      */           {
/* 2848 */             ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */             
/* 2850 */             if (shaderline != null)
/*      */             {
/* 2852 */               if (shaderline.isUniform()) {
/*      */                 
/* 2854 */                 String s6 = shaderline.getName();
/*      */                 
/*      */                 int l1;
/* 2857 */                 if ((l1 = ShaderParser.getShadowDepthIndex(s6)) >= 0) {
/*      */                   
/* 2859 */                   usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, l1 + 1); continue;
/*      */                 } 
/* 2861 */                 if ((l1 = ShaderParser.getShadowColorIndex(s6)) >= 0) {
/*      */                   
/* 2863 */                   usedShadowColorBuffers = Math.max(usedShadowColorBuffers, l1 + 1); continue;
/*      */                 } 
/* 2865 */                 if ((l1 = ShaderParser.getDepthIndex(s6)) >= 0) {
/*      */                   
/* 2867 */                   usedDepthBuffers = Math.max(usedDepthBuffers, l1 + 1); continue;
/*      */                 } 
/* 2869 */                 if (s6.equals("gdepth") && gbuffersFormat[1] == 6408) {
/*      */                   
/* 2871 */                   gbuffersFormat[1] = 34836; continue;
/*      */                 } 
/* 2873 */                 if ((l1 = ShaderParser.getColorIndex(s6)) >= 0) {
/*      */                   
/* 2875 */                   usedColorBuffers = Math.max(usedColorBuffers, l1 + 1); continue;
/*      */                 } 
/* 2877 */                 if (s6.equals("centerDepthSmooth"))
/*      */                 {
/* 2879 */                   centerDepthSmoothEnabled = true; } 
/*      */                 continue;
/*      */               } 
/* 2882 */               if (!shaderline.isConstInt("shadowMapResolution") && !shaderline.isProperty("SHADOWRES")) {
/*      */                 
/* 2884 */                 if (!shaderline.isConstFloat("shadowMapFov") && !shaderline.isProperty("SHADOWFOV")) {
/*      */                   
/* 2886 */                   if (!shaderline.isConstFloat("shadowDistance") && !shaderline.isProperty("SHADOWHPL")) {
/*      */                     
/* 2888 */                     if (shaderline.isConstFloat("shadowDistanceRenderMul")) {
/*      */                       
/* 2890 */                       shadowDistanceRenderMul = shaderline.getValueFloat();
/* 2891 */                       SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul); continue;
/*      */                     } 
/* 2893 */                     if (shaderline.isConstFloat("shadowIntervalSize")) {
/*      */                       
/* 2895 */                       shadowIntervalSize = shaderline.getValueFloat();
/* 2896 */                       SMCLog.info("Shadow map interval size: " + shadowIntervalSize); continue;
/*      */                     } 
/* 2898 */                     if (shaderline.isConstBool("generateShadowMipmap", true)) {
/*      */                       
/* 2900 */                       Arrays.fill(shadowMipmapEnabled, true);
/* 2901 */                       SMCLog.info("Generate shadow mipmap"); continue;
/*      */                     } 
/* 2903 */                     if (shaderline.isConstBool("generateShadowColorMipmap", true)) {
/*      */                       
/* 2905 */                       Arrays.fill(shadowColorMipmapEnabled, true);
/* 2906 */                       SMCLog.info("Generate shadow color mipmap"); continue;
/*      */                     } 
/* 2908 */                     if (shaderline.isConstBool("shadowHardwareFiltering", true)) {
/*      */                       
/* 2910 */                       Arrays.fill(shadowHardwareFilteringEnabled, true);
/* 2911 */                       SMCLog.info("Hardware shadow filtering enabled."); continue;
/*      */                     } 
/* 2913 */                     if (shaderline.isConstBool("shadowHardwareFiltering0", true)) {
/*      */                       
/* 2915 */                       shadowHardwareFilteringEnabled[0] = true;
/* 2916 */                       SMCLog.info("shadowHardwareFiltering0"); continue;
/*      */                     } 
/* 2918 */                     if (shaderline.isConstBool("shadowHardwareFiltering1", true)) {
/*      */                       
/* 2920 */                       shadowHardwareFilteringEnabled[1] = true;
/* 2921 */                       SMCLog.info("shadowHardwareFiltering1"); continue;
/*      */                     } 
/* 2923 */                     if (shaderline.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
/*      */                       
/* 2925 */                       shadowMipmapEnabled[0] = true;
/* 2926 */                       SMCLog.info("shadowtex0Mipmap"); continue;
/*      */                     } 
/* 2928 */                     if (shaderline.isConstBool("shadowtex1Mipmap", true)) {
/*      */                       
/* 2930 */                       shadowMipmapEnabled[1] = true;
/* 2931 */                       SMCLog.info("shadowtex1Mipmap"); continue;
/*      */                     } 
/* 2933 */                     if (shaderline.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
/*      */                       
/* 2935 */                       shadowColorMipmapEnabled[0] = true;
/* 2936 */                       SMCLog.info("shadowcolor0Mipmap"); continue;
/*      */                     } 
/* 2938 */                     if (shaderline.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
/*      */                       
/* 2940 */                       shadowColorMipmapEnabled[1] = true;
/* 2941 */                       SMCLog.info("shadowcolor1Mipmap"); continue;
/*      */                     } 
/* 2943 */                     if (shaderline.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
/*      */                       
/* 2945 */                       shadowFilterNearest[0] = true;
/* 2946 */                       SMCLog.info("shadowtex0Nearest"); continue;
/*      */                     } 
/* 2948 */                     if (shaderline.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
/*      */                       
/* 2950 */                       shadowFilterNearest[1] = true;
/* 2951 */                       SMCLog.info("shadowtex1Nearest"); continue;
/*      */                     } 
/* 2953 */                     if (shaderline.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
/*      */                       
/* 2955 */                       shadowColorFilterNearest[0] = true;
/* 2956 */                       SMCLog.info("shadowcolor0Nearest"); continue;
/*      */                     } 
/* 2958 */                     if (shaderline.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
/*      */                       
/* 2960 */                       shadowColorFilterNearest[1] = true;
/* 2961 */                       SMCLog.info("shadowcolor1Nearest"); continue;
/*      */                     } 
/* 2963 */                     if (!shaderline.isConstFloat("wetnessHalflife") && !shaderline.isProperty("WETNESSHL")) {
/*      */                       
/* 2965 */                       if (!shaderline.isConstFloat("drynessHalflife") && !shaderline.isProperty("DRYNESSHL")) {
/*      */                         
/* 2967 */                         if (shaderline.isConstFloat("eyeBrightnessHalflife")) {
/*      */                           
/* 2969 */                           eyeBrightnessHalflife = shaderline.getValueFloat();
/* 2970 */                           SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife); continue;
/*      */                         } 
/* 2972 */                         if (shaderline.isConstFloat("centerDepthHalflife")) {
/*      */                           
/* 2974 */                           centerDepthSmoothHalflife = shaderline.getValueFloat();
/* 2975 */                           SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife); continue;
/*      */                         } 
/* 2977 */                         if (shaderline.isConstFloat("sunPathRotation")) {
/*      */                           
/* 2979 */                           sunPathRotation = shaderline.getValueFloat();
/* 2980 */                           SMCLog.info("Sun path rotation: " + sunPathRotation); continue;
/*      */                         } 
/* 2982 */                         if (shaderline.isConstFloat("ambientOcclusionLevel")) {
/*      */                           
/* 2984 */                           aoLevel = Config.limit(shaderline.getValueFloat(), 0.0F, 1.0F);
/* 2985 */                           SMCLog.info("AO Level: " + aoLevel); continue;
/*      */                         } 
/* 2987 */                         if (shaderline.isConstInt("superSamplingLevel")) {
/*      */                           
/* 2989 */                           int i1 = shaderline.getValueInt();
/*      */                           
/* 2991 */                           if (i1 > 1) {
/*      */                             
/* 2993 */                             SMCLog.info("Super sampling level: " + i1 + "x");
/* 2994 */                             superSamplingLevel = i1;
/*      */                             
/*      */                             continue;
/*      */                           } 
/* 2998 */                           superSamplingLevel = 1;
/*      */                           continue;
/*      */                         } 
/* 3001 */                         if (shaderline.isConstInt("noiseTextureResolution")) {
/*      */                           
/* 3003 */                           noiseTextureResolution = shaderline.getValueInt();
/* 3004 */                           noiseTextureEnabled = true;
/* 3005 */                           SMCLog.info("Noise texture enabled");
/* 3006 */                           SMCLog.info("Noise texture resolution: " + noiseTextureResolution); continue;
/*      */                         } 
/* 3008 */                         if (shaderline.isConstIntSuffix("Format")) {
/*      */                           
/* 3010 */                           String s5 = StrUtils.removeSuffix(shaderline.getName(), "Format");
/* 3011 */                           String s7 = shaderline.getValue();
/* 3012 */                           int i2 = getBufferIndexFromString(s5);
/* 3013 */                           int l = getTextureFormatFromString(s7);
/*      */                           
/* 3015 */                           if (i2 >= 0 && l != 0) {
/*      */                             
/* 3017 */                             gbuffersFormat[i2] = l;
/* 3018 */                             SMCLog.info("%s format: %s", new Object[] { s5, s7 });
/*      */                           }  continue;
/*      */                         } 
/* 3021 */                         if (shaderline.isConstBoolSuffix("Clear", false)) {
/*      */                           
/* 3023 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename)) {
/*      */                             
/* 3025 */                             String s4 = StrUtils.removeSuffix(shaderline.getName(), "Clear");
/* 3026 */                             int k1 = getBufferIndexFromString(s4);
/*      */                             
/* 3028 */                             if (k1 >= 0) {
/*      */                               
/* 3030 */                               gbuffersClear[k1] = false;
/* 3031 */                               SMCLog.info("%s clear disabled", new Object[] { s4 });
/*      */                             } 
/*      */                           }  continue;
/*      */                         } 
/* 3035 */                         if (shaderline.isConstVec4Suffix("ClearColor")) {
/*      */                           
/* 3037 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename)) {
/*      */                             
/* 3039 */                             String s3 = StrUtils.removeSuffix(shaderline.getName(), "ClearColor");
/* 3040 */                             int j1 = getBufferIndexFromString(s3);
/*      */                             
/* 3042 */                             if (j1 >= 0) {
/*      */                               
/* 3044 */                               Vector4f vector4f = shaderline.getValueVec4();
/*      */                               
/* 3046 */                               if (vector4f != null) {
/*      */                                 
/* 3048 */                                 gbuffersClearColor[j1] = vector4f;
/* 3049 */                                 SMCLog.info("%s clear color: %s %s %s %s", new Object[] { s3, Float.valueOf(vector4f.getX()), Float.valueOf(vector4f.getY()), Float.valueOf(vector4f.getZ()), Float.valueOf(vector4f.getW()) });
/*      */                                 
/*      */                                 continue;
/*      */                               } 
/* 3053 */                               SMCLog.warning("Invalid color value: " + shaderline.getValue());
/*      */                             } 
/*      */                           } 
/*      */                           continue;
/*      */                         } 
/* 3058 */                         if (shaderline.isProperty("GAUX4FORMAT", "RGBA32F")) {
/*      */                           
/* 3060 */                           gbuffersFormat[7] = 34836;
/* 3061 */                           SMCLog.info("gaux4 format : RGB32AF"); continue;
/*      */                         } 
/* 3063 */                         if (shaderline.isProperty("GAUX4FORMAT", "RGB32F")) {
/*      */                           
/* 3065 */                           gbuffersFormat[7] = 34837;
/* 3066 */                           SMCLog.info("gaux4 format : RGB32F"); continue;
/*      */                         } 
/* 3068 */                         if (shaderline.isProperty("GAUX4FORMAT", "RGB16")) {
/*      */                           
/* 3070 */                           gbuffersFormat[7] = 32852;
/* 3071 */                           SMCLog.info("gaux4 format : RGB16"); continue;
/*      */                         } 
/* 3073 */                         if (shaderline.isConstBoolSuffix("MipmapEnabled", true)) {
/*      */                           
/* 3075 */                           if (ShaderParser.isComposite(filename) || ShaderParser.isDeferred(filename) || ShaderParser.isFinal(filename)) {
/*      */                             
/* 3077 */                             String s2 = StrUtils.removeSuffix(shaderline.getName(), "MipmapEnabled");
/* 3078 */                             int j = getBufferIndexFromString(s2);
/*      */                             
/* 3080 */                             if (j >= 0) {
/*      */                               
/* 3082 */                               int k = program.getCompositeMipmapSetting();
/* 3083 */                               k |= 1 << j;
/* 3084 */                               program.setCompositeMipmapSetting(k);
/* 3085 */                               SMCLog.info("%s mipmap enabled", new Object[] { s2 });
/*      */                             } 
/*      */                           }  continue;
/*      */                         } 
/* 3089 */                         if (shaderline.isProperty("DRAWBUFFERS")) {
/*      */                           
/* 3091 */                           String s1 = shaderline.getValue();
/*      */                           
/* 3093 */                           if (ShaderParser.isValidDrawBuffers(s1)) {
/*      */                             
/* 3095 */                             program.setDrawBufSettings(s1);
/*      */                             
/*      */                             continue;
/*      */                           } 
/* 3099 */                           SMCLog.warning("Invalid draw buffers: " + s1);
/*      */                         } 
/*      */                         
/*      */                         continue;
/*      */                       } 
/*      */                       
/* 3105 */                       drynessHalfLife = shaderline.getValueFloat();
/* 3106 */                       SMCLog.info("Dryness halflife: " + drynessHalfLife);
/*      */                       
/*      */                       continue;
/*      */                     } 
/*      */                     
/* 3111 */                     wetnessHalfLife = shaderline.getValueFloat();
/* 3112 */                     SMCLog.info("Wetness halflife: " + wetnessHalfLife);
/*      */                     
/*      */                     continue;
/*      */                   } 
/*      */                   
/* 3117 */                   shadowMapHalfPlane = shaderline.getValueFloat();
/* 3118 */                   shadowMapIsOrtho = true;
/* 3119 */                   SMCLog.info("Shadow map distance: " + shadowMapHalfPlane);
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */                 
/* 3124 */                 shadowMapFOV = shaderline.getValueFloat();
/* 3125 */                 shadowMapIsOrtho = false;
/* 3126 */                 SMCLog.info("Shadow map field of view: " + shadowMapFOV);
/*      */                 
/*      */                 continue;
/*      */               } 
/*      */               
/* 3131 */               spShadowMapWidth = spShadowMapHeight = shaderline.getValueInt();
/* 3132 */               shadowMapWidth = shadowMapHeight = Math.round(spShadowMapWidth * configShadowResMul);
/* 3133 */               SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/* 3139 */       } catch (Exception exception) {
/*      */         
/* 3141 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 3142 */         exception.printStackTrace();
/* 3143 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 3144 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 3148 */     if (saveFinalShaders)
/*      */     {
/* 3150 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 3153 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 3154 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 3156 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 3158 */       SMCLog.severe("Error compiling fragment shader: " + filename);
/*      */     }
/*      */     
/* 3161 */     printShaderLogInfo(i, filename, list);
/* 3162 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Reader getShaderReader(String filename) {
/* 3168 */     return new InputStreamReader(shaderPack.getResourceAsStream(filename));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveShader(String filename, String code) {
/*      */     try {
/* 3175 */       File file1 = new File(shaderPacksDir, "debug/" + filename);
/* 3176 */       file1.getParentFile().mkdirs();
/* 3177 */       Config.writeFile(file1, code);
/*      */     }
/* 3179 */     catch (IOException ioexception) {
/*      */       
/* 3181 */       Config.warn("Error saving: " + filename);
/* 3182 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void clearDirectory(File dir) {
/* 3188 */     if (dir.exists())
/*      */     {
/* 3190 */       if (dir.isDirectory()) {
/*      */         
/* 3192 */         File[] afile = dir.listFiles();
/*      */         
/* 3194 */         if (afile != null)
/*      */         {
/* 3196 */           for (int i = 0; i < afile.length; i++) {
/*      */             
/* 3198 */             File file1 = afile[i];
/*      */             
/* 3200 */             if (file1.isDirectory())
/*      */             {
/* 3202 */               clearDirectory(file1);
/*      */             }
/*      */             
/* 3205 */             file1.delete();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean printLogInfo(int obj, String name) {
/* 3214 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 3215 */     ARBShaderObjects.glGetObjectParameterARB(obj, 35716, intbuffer);
/* 3216 */     int i = intbuffer.get();
/*      */     
/* 3218 */     if (i > 1) {
/*      */       
/* 3220 */       ByteBuffer bytebuffer = BufferUtils.createByteBuffer(i);
/* 3221 */       intbuffer.flip();
/* 3222 */       ARBShaderObjects.glGetInfoLogARB(obj, intbuffer, bytebuffer);
/* 3223 */       byte[] abyte = new byte[i];
/* 3224 */       bytebuffer.get(abyte);
/*      */       
/* 3226 */       if (abyte[i - 1] == 0)
/*      */       {
/* 3228 */         abyte[i - 1] = 10;
/*      */       }
/*      */       
/* 3231 */       String s = new String(abyte, Charsets.US_ASCII);
/* 3232 */       s = StrUtils.trim(s, " \n\r\t");
/* 3233 */       SMCLog.info("Info log: " + name + "\n" + s);
/* 3234 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 3238 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean printShaderLogInfo(int shader, String name, List<String> listFiles) {
/* 3244 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 3245 */     int i = GL20.glGetShaderi(shader, 35716);
/*      */     
/* 3247 */     if (i <= 1)
/*      */     {
/* 3249 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 3253 */     for (int j = 0; j < listFiles.size(); j++) {
/*      */       
/* 3255 */       String s = listFiles.get(j);
/* 3256 */       SMCLog.info("File: " + (j + 1) + " = " + s);
/*      */     } 
/*      */     
/* 3259 */     String s1 = GL20.glGetShaderInfoLog(shader, i);
/* 3260 */     s1 = StrUtils.trim(s1, " \n\r\t");
/* 3261 */     SMCLog.info("Shader info log: " + name + "\n" + s1);
/* 3262 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDrawBuffers(IntBuffer drawBuffers) {
/* 3268 */     if (drawBuffers == null)
/*      */     {
/* 3270 */       drawBuffers = drawBuffersNone;
/*      */     }
/*      */     
/* 3273 */     if (activeDrawBuffers != drawBuffers) {
/*      */       
/* 3275 */       activeDrawBuffers = drawBuffers;
/* 3276 */       GL20.glDrawBuffers(drawBuffers);
/* 3277 */       checkGLError("setDrawBuffers");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void useProgram(Program program) {
/* 3283 */     checkGLError("pre-useProgram");
/*      */     
/* 3285 */     if (isShadowPass) {
/*      */       
/* 3287 */       program = ProgramShadow;
/*      */     }
/* 3289 */     else if (isEntitiesGlowing) {
/*      */       
/* 3291 */       program = ProgramEntitiesGlowing;
/*      */     } 
/*      */     
/* 3294 */     if (activeProgram != program) {
/*      */       
/* 3296 */       updateAlphaBlend(activeProgram, program);
/* 3297 */       activeProgram = program;
/* 3298 */       int i = program.getId();
/* 3299 */       activeProgramID = i;
/* 3300 */       ARBShaderObjects.glUseProgramObjectARB(i);
/*      */       
/* 3302 */       if (checkGLError("useProgram") != 0) {
/*      */         
/* 3304 */         program.setId(0);
/* 3305 */         i = program.getId();
/* 3306 */         activeProgramID = i;
/* 3307 */         ARBShaderObjects.glUseProgramObjectARB(i);
/*      */       } 
/*      */       
/* 3310 */       shaderUniforms.setProgram(i);
/*      */       
/* 3312 */       if (customUniforms != null)
/*      */       {
/* 3314 */         customUniforms.setProgram(i);
/*      */       }
/*      */       
/* 3317 */       if (i != 0) {
/*      */         
/* 3319 */         IntBuffer intbuffer = program.getDrawBuffers();
/*      */         
/* 3321 */         if (isRenderingDfb)
/*      */         {
/* 3323 */           setDrawBuffers(intbuffer);
/*      */         }
/*      */         
/* 3326 */         activeCompositeMipmapSetting = program.getCompositeMipmapSetting();
/*      */         
/* 3328 */         switch (program.getProgramStage()) {
/*      */           
/*      */           case GBUFFERS:
/* 3331 */             setProgramUniform1i(uniform_texture, 0);
/* 3332 */             setProgramUniform1i(uniform_lightmap, 1);
/* 3333 */             setProgramUniform1i(uniform_normals, 2);
/* 3334 */             setProgramUniform1i(uniform_specular, 3);
/* 3335 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 3336 */             setProgramUniform1i(uniform_watershadow, 4);
/* 3337 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 3338 */             setProgramUniform1i(uniform_shadowtex1, 5);
/* 3339 */             setProgramUniform1i(uniform_depthtex0, 6);
/*      */             
/* 3341 */             if (customTexturesGbuffers != null || hasDeferredPrograms) {
/*      */               
/* 3343 */               setProgramUniform1i(uniform_gaux1, 7);
/* 3344 */               setProgramUniform1i(uniform_gaux2, 8);
/* 3345 */               setProgramUniform1i(uniform_gaux3, 9);
/* 3346 */               setProgramUniform1i(uniform_gaux4, 10);
/*      */             } 
/*      */             
/* 3349 */             setProgramUniform1i(uniform_depthtex1, 11);
/* 3350 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 3351 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 3352 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 3353 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */           
/*      */           case DEFERRED:
/*      */           case COMPOSITE:
/* 3358 */             setProgramUniform1i(uniform_gcolor, 0);
/* 3359 */             setProgramUniform1i(uniform_gdepth, 1);
/* 3360 */             setProgramUniform1i(uniform_gnormal, 2);
/* 3361 */             setProgramUniform1i(uniform_composite, 3);
/* 3362 */             setProgramUniform1i(uniform_gaux1, 7);
/* 3363 */             setProgramUniform1i(uniform_gaux2, 8);
/* 3364 */             setProgramUniform1i(uniform_gaux3, 9);
/* 3365 */             setProgramUniform1i(uniform_gaux4, 10);
/* 3366 */             setProgramUniform1i(uniform_colortex0, 0);
/* 3367 */             setProgramUniform1i(uniform_colortex1, 1);
/* 3368 */             setProgramUniform1i(uniform_colortex2, 2);
/* 3369 */             setProgramUniform1i(uniform_colortex3, 3);
/* 3370 */             setProgramUniform1i(uniform_colortex4, 7);
/* 3371 */             setProgramUniform1i(uniform_colortex5, 8);
/* 3372 */             setProgramUniform1i(uniform_colortex6, 9);
/* 3373 */             setProgramUniform1i(uniform_colortex7, 10);
/* 3374 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 3375 */             setProgramUniform1i(uniform_watershadow, 4);
/* 3376 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 3377 */             setProgramUniform1i(uniform_shadowtex1, 5);
/* 3378 */             setProgramUniform1i(uniform_gdepthtex, 6);
/* 3379 */             setProgramUniform1i(uniform_depthtex0, 6);
/* 3380 */             setProgramUniform1i(uniform_depthtex1, 11);
/* 3381 */             setProgramUniform1i(uniform_depthtex2, 12);
/* 3382 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 3383 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 3384 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 3385 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */           
/*      */           case SHADOW:
/* 3389 */             setProgramUniform1i(uniform_tex, 0);
/* 3390 */             setProgramUniform1i(uniform_texture, 0);
/* 3391 */             setProgramUniform1i(uniform_lightmap, 1);
/* 3392 */             setProgramUniform1i(uniform_normals, 2);
/* 3393 */             setProgramUniform1i(uniform_specular, 3);
/* 3394 */             setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
/* 3395 */             setProgramUniform1i(uniform_watershadow, 4);
/* 3396 */             setProgramUniform1i(uniform_shadowtex0, 4);
/* 3397 */             setProgramUniform1i(uniform_shadowtex1, 5);
/*      */             
/* 3399 */             if (customTexturesGbuffers != null) {
/*      */               
/* 3401 */               setProgramUniform1i(uniform_gaux1, 7);
/* 3402 */               setProgramUniform1i(uniform_gaux2, 8);
/* 3403 */               setProgramUniform1i(uniform_gaux3, 9);
/* 3404 */               setProgramUniform1i(uniform_gaux4, 10);
/*      */             } 
/*      */             
/* 3407 */             setProgramUniform1i(uniform_shadowcolor, 13);
/* 3408 */             setProgramUniform1i(uniform_shadowcolor0, 13);
/* 3409 */             setProgramUniform1i(uniform_shadowcolor1, 14);
/* 3410 */             setProgramUniform1i(uniform_noisetex, 15);
/*      */             break;
/*      */         } 
/* 3413 */         ItemStack itemstack = (mc.thePlayer != null) ? mc.thePlayer.getHeldItem() : null;
/* 3414 */         Item item = (itemstack != null) ? itemstack.getItem() : null;
/* 3415 */         int j = -1;
/* 3416 */         Block block = null;
/*      */         
/* 3418 */         if (item != null) {
/*      */           
/* 3420 */           j = Item.itemRegistry.getIDForObject(item);
/* 3421 */           block = (Block)Block.blockRegistry.getObjectById(j);
/* 3422 */           j = ItemAliases.getItemAliasId(j);
/*      */         } 
/*      */         
/* 3425 */         int k = (block != null) ? block.getLightValue() : 0;
/* 3426 */         setProgramUniform1i(uniform_heldItemId, j);
/* 3427 */         setProgramUniform1i(uniform_heldBlockLightValue, k);
/* 3428 */         setProgramUniform1i(uniform_fogMode, fogEnabled ? fogMode : 0);
/* 3429 */         setProgramUniform1f(uniform_fogDensity, fogEnabled ? fogDensity : 0.0F);
/* 3430 */         setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
/* 3431 */         setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
/* 3432 */         setProgramUniform1i(uniform_worldTime, (int)(worldTime % 24000L));
/* 3433 */         setProgramUniform1i(uniform_worldDay, (int)(worldTime / 24000L));
/* 3434 */         setProgramUniform1i(uniform_moonPhase, moonPhase);
/* 3435 */         setProgramUniform1i(uniform_frameCounter, frameCounter);
/* 3436 */         setProgramUniform1f(uniform_frameTime, frameTime);
/* 3437 */         setProgramUniform1f(uniform_frameTimeCounter, frameTimeCounter);
/* 3438 */         setProgramUniform1f(uniform_sunAngle, sunAngle);
/* 3439 */         setProgramUniform1f(uniform_shadowAngle, shadowAngle);
/* 3440 */         setProgramUniform1f(uniform_rainStrength, rainStrength);
/* 3441 */         setProgramUniform1f(uniform_aspectRatio, renderWidth / renderHeight);
/* 3442 */         setProgramUniform1f(uniform_viewWidth, renderWidth);
/* 3443 */         setProgramUniform1f(uniform_viewHeight, renderHeight);
/* 3444 */         setProgramUniform1f(uniform_near, 0.05F);
/* 3445 */         setProgramUniform1f(uniform_far, (mc.gameSettings.renderDistanceChunks * 16));
/* 3446 */         setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
/* 3447 */         setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
/* 3448 */         setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/* 3449 */         setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
/* 3450 */         setProgramUniform3f(uniform_previousCameraPosition, (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
/* 3451 */         setProgramUniform3f(uniform_cameraPosition, (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
/* 3452 */         setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
/* 3453 */         setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
/* 3454 */         setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
/* 3455 */         setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
/* 3456 */         setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
/* 3457 */         setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
/*      */         
/* 3459 */         if (usedShadowDepthBuffers > 0) {
/*      */           
/* 3461 */           setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
/* 3462 */           setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
/* 3463 */           setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
/* 3464 */           setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
/*      */         } 
/*      */         
/* 3467 */         setProgramUniform1f(uniform_wetness, wetness);
/* 3468 */         setProgramUniform1f(uniform_eyeAltitude, eyePosY);
/* 3469 */         setProgramUniform2i(uniform_eyeBrightness, eyeBrightness & 0xFFFF, eyeBrightness >> 16);
/* 3470 */         setProgramUniform2i(uniform_eyeBrightnessSmooth, Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
/* 3471 */         setProgramUniform2i(uniform_terrainTextureSize, terrainTextureSize[0], terrainTextureSize[1]);
/* 3472 */         setProgramUniform1i(uniform_terrainIconSize, terrainIconSize);
/* 3473 */         setProgramUniform1i(uniform_isEyeInWater, isEyeInWater);
/* 3474 */         setProgramUniform1f(uniform_nightVision, nightVision);
/* 3475 */         setProgramUniform1f(uniform_blindness, blindness);
/* 3476 */         setProgramUniform1f(uniform_screenBrightness, mc.gameSettings.gammaSetting);
/* 3477 */         setProgramUniform1i(uniform_hideGUI, mc.gameSettings.hideGUI ? 1 : 0);
/* 3478 */         setProgramUniform1f(uniform_centerDepthSmooth, centerDepthSmooth);
/* 3479 */         setProgramUniform2i(uniform_atlasSize, atlasSizeX, atlasSizeY);
/*      */         
/* 3481 */         if (customUniforms != null)
/*      */         {
/* 3483 */           customUniforms.update();
/*      */         }
/*      */         
/* 3486 */         checkGLError("end useProgram");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void updateAlphaBlend(Program programOld, Program programNew) {
/* 3493 */     if (programOld.getAlphaState() != null)
/*      */     {
/* 3495 */       GlStateManager.unlockAlpha();
/*      */     }
/*      */     
/* 3498 */     if (programOld.getBlendState() != null)
/*      */     {
/* 3500 */       GlStateManager.unlockBlend();
/*      */     }
/*      */     
/* 3503 */     GlAlphaState glalphastate = programNew.getAlphaState();
/*      */     
/* 3505 */     if (glalphastate != null)
/*      */     {
/* 3507 */       GlStateManager.lockAlpha(glalphastate);
/*      */     }
/*      */     
/* 3510 */     GlBlendState glblendstate = programNew.getBlendState();
/*      */     
/* 3512 */     if (glblendstate != null)
/*      */     {
/* 3514 */       GlStateManager.lockBlend(glblendstate);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform1i(ShaderUniform1i su, int value) {
/* 3520 */     su.setValue(value);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform2i(ShaderUniform2i su, int i0, int i1) {
/* 3525 */     su.setValue(i0, i1);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform1f(ShaderUniform1f su, float value) {
/* 3530 */     su.setValue(value);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniform3f(ShaderUniform3f su, float f0, float f1, float f2) {
/* 3535 */     su.setValue(f0, f1, f2);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setProgramUniformMatrix4ARB(ShaderUniformM4 su, boolean transpose, FloatBuffer matrix) {
/* 3540 */     su.setValue(transpose, matrix);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBufferIndexFromString(String name) {
/* 3545 */     return (!name.equals("colortex0") && !name.equals("gcolor")) ? ((!name.equals("colortex1") && !name.equals("gdepth")) ? ((!name.equals("colortex2") && !name.equals("gnormal")) ? ((!name.equals("colortex3") && !name.equals("composite")) ? ((!name.equals("colortex4") && !name.equals("gaux1")) ? ((!name.equals("colortex5") && !name.equals("gaux2")) ? ((!name.equals("colortex6") && !name.equals("gaux3")) ? ((!name.equals("colortex7") && !name.equals("gaux4")) ? -1 : 7) : 6) : 5) : 4) : 3) : 2) : 1) : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getTextureFormatFromString(String par) {
/* 3550 */     par = par.trim();
/*      */     
/* 3552 */     for (int i = 0; i < formatNames.length; i++) {
/*      */       
/* 3554 */       String s = formatNames[i];
/*      */       
/* 3556 */       if (par.equals(s))
/*      */       {
/* 3558 */         return formatIds[i];
/*      */       }
/*      */     } 
/*      */     
/* 3562 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupNoiseTexture() {
/* 3567 */     if (noiseTexture == null && noiseTexturePath != null)
/*      */     {
/* 3569 */       noiseTexture = loadCustomTexture(15, noiseTexturePath);
/*      */     }
/*      */     
/* 3572 */     if (noiseTexture == null)
/*      */     {
/* 3574 */       noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadEntityDataMap() {
/* 3580 */     mapBlockToEntityData = new IdentityHashMap<>(300);
/*      */     
/* 3582 */     if (mapBlockToEntityData.isEmpty())
/*      */     {
/* 3584 */       for (ResourceLocation resourcelocation : Block.blockRegistry.getKeys()) {
/*      */         
/* 3586 */         Block block = (Block)Block.blockRegistry.getObject(resourcelocation);
/* 3587 */         int i = Block.blockRegistry.getIDForObject(block);
/* 3588 */         mapBlockToEntityData.put(block, Integer.valueOf(i));
/*      */       } 
/*      */     }
/*      */     
/* 3592 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 3596 */       bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
/*      */     }
/* 3598 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3603 */     if (bufferedreader != null) {
/*      */       try {
/*      */         String s1;
/*      */ 
/*      */ 
/*      */         
/* 3609 */         while ((s1 = bufferedreader.readLine()) != null)
/*      */         {
/* 3611 */           Matcher matcher = patternLoadEntityDataMap.matcher(s1);
/*      */           
/* 3613 */           if (matcher.matches()) {
/*      */             
/* 3615 */             String s2 = matcher.group(1);
/* 3616 */             String s = matcher.group(2);
/* 3617 */             int j = Integer.parseInt(s);
/* 3618 */             Block block1 = Block.getBlockFromName(s2);
/*      */             
/* 3620 */             if (block1 != null) {
/*      */               
/* 3622 */               mapBlockToEntityData.put(block1, Integer.valueOf(j));
/*      */               
/*      */               continue;
/*      */             } 
/* 3626 */             SMCLog.warning("Unknown block name %s", new Object[] { s2 });
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 3631 */           SMCLog.warning("unmatched %s\n", new Object[] { s1 });
/*      */         }
/*      */       
/*      */       }
/* 3635 */       catch (Exception var9) {
/*      */         
/* 3637 */         SMCLog.warning("Error parsing mc_Entity_x.txt");
/*      */       } 
/*      */     }
/*      */     
/* 3641 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 3645 */         bufferedreader.close();
/*      */       }
/* 3647 */       catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static IntBuffer fillIntBufferZero(IntBuffer buf) {
/* 3656 */     int i = buf.limit();
/*      */     
/* 3658 */     for (int j = buf.position(); j < i; j++)
/*      */     {
/* 3660 */       buf.put(j, 0);
/*      */     }
/*      */     
/* 3663 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void uninit() {
/* 3668 */     if (isShaderPackInitialized) {
/*      */       
/* 3670 */       checkGLError("Shaders.uninit pre");
/*      */       
/* 3672 */       for (int i = 0; i < ProgramsAll.length; i++) {
/*      */         
/* 3674 */         Program program = ProgramsAll[i];
/*      */         
/* 3676 */         if (program.getRef() != 0) {
/*      */           
/* 3678 */           ARBShaderObjects.glDeleteObjectARB(program.getRef());
/* 3679 */           checkGLError("del programRef");
/*      */         } 
/*      */         
/* 3682 */         program.setRef(0);
/* 3683 */         program.setId(0);
/* 3684 */         program.setDrawBufSettings(null);
/* 3685 */         program.setDrawBuffers(null);
/* 3686 */         program.setCompositeMipmapSetting(0);
/*      */       } 
/*      */       
/* 3689 */       hasDeferredPrograms = false;
/*      */       
/* 3691 */       if (dfb != 0) {
/*      */         
/* 3693 */         EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3694 */         dfb = 0;
/* 3695 */         checkGLError("del dfb");
/*      */       } 
/*      */       
/* 3698 */       if (sfb != 0) {
/*      */         
/* 3700 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3701 */         sfb = 0;
/* 3702 */         checkGLError("del sfb");
/*      */       } 
/*      */       
/* 3705 */       if (dfbDepthTextures != null) {
/*      */         
/* 3707 */         GlStateManager.deleteTextures(dfbDepthTextures);
/* 3708 */         fillIntBufferZero(dfbDepthTextures);
/* 3709 */         checkGLError("del dfbDepthTextures");
/*      */       } 
/*      */       
/* 3712 */       if (dfbColorTextures != null) {
/*      */         
/* 3714 */         GlStateManager.deleteTextures(dfbColorTextures);
/* 3715 */         fillIntBufferZero(dfbColorTextures);
/* 3716 */         checkGLError("del dfbTextures");
/*      */       } 
/*      */       
/* 3719 */       if (sfbDepthTextures != null) {
/*      */         
/* 3721 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3722 */         fillIntBufferZero(sfbDepthTextures);
/* 3723 */         checkGLError("del shadow depth");
/*      */       } 
/*      */       
/* 3726 */       if (sfbColorTextures != null) {
/*      */         
/* 3728 */         GlStateManager.deleteTextures(sfbColorTextures);
/* 3729 */         fillIntBufferZero(sfbColorTextures);
/* 3730 */         checkGLError("del shadow color");
/*      */       } 
/*      */       
/* 3733 */       if (dfbDrawBuffers != null)
/*      */       {
/* 3735 */         fillIntBufferZero(dfbDrawBuffers);
/*      */       }
/*      */       
/* 3738 */       if (noiseTexture != null) {
/*      */         
/* 3740 */         noiseTexture.deleteTexture();
/* 3741 */         noiseTexture = null;
/*      */       } 
/*      */       
/* 3744 */       SMCLog.info("Uninit");
/* 3745 */       shadowPassInterval = 0;
/* 3746 */       shouldSkipDefaultShadow = false;
/* 3747 */       isShaderPackInitialized = false;
/* 3748 */       checkGLError("Shaders.uninit");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResize() {
/* 3754 */     renderDisplayHeight = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResizeShadow() {
/* 3759 */     needResizeShadow = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resize() {
/* 3764 */     renderDisplayWidth = mc.displayWidth;
/* 3765 */     renderDisplayHeight = mc.displayHeight;
/* 3766 */     renderWidth = Math.round(renderDisplayWidth * configRenderResMul);
/* 3767 */     renderHeight = Math.round(renderDisplayHeight * configRenderResMul);
/* 3768 */     setupFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resizeShadow() {
/* 3773 */     needResizeShadow = false;
/* 3774 */     shadowMapWidth = Math.round(spShadowMapWidth * configShadowResMul);
/* 3775 */     shadowMapHeight = Math.round(spShadowMapHeight * configShadowResMul);
/* 3776 */     setupShadowFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupFrameBuffer() {
/* 3781 */     if (dfb != 0) {
/*      */       
/* 3783 */       EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3784 */       GlStateManager.deleteTextures(dfbDepthTextures);
/* 3785 */       GlStateManager.deleteTextures(dfbColorTextures);
/*      */     } 
/*      */     
/* 3788 */     dfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3789 */     GL11.glGenTextures((IntBuffer)dfbDepthTextures.clear().limit(usedDepthBuffers));
/* 3790 */     GL11.glGenTextures((IntBuffer)dfbColorTextures.clear().limit(16));
/* 3791 */     dfbDepthTextures.position(0);
/* 3792 */     dfbColorTextures.position(0);
/* 3793 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3794 */     GL20.glDrawBuffers(0);
/* 3795 */     GL11.glReadBuffer(0);
/*      */     
/* 3797 */     for (int i = 0; i < usedDepthBuffers; i++) {
/*      */       
/* 3799 */       GlStateManager.bindTexture(dfbDepthTextures.get(i));
/* 3800 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3801 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3802 */       GL11.glTexParameteri(3553, 10241, 9728);
/* 3803 */       GL11.glTexParameteri(3553, 10240, 9728);
/* 3804 */       GL11.glTexParameteri(3553, 34891, 6409);
/* 3805 */       GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, (ByteBuffer)null);
/*      */     } 
/*      */     
/* 3808 */     EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 3809 */     GL20.glDrawBuffers(dfbDrawBuffers);
/* 3810 */     GL11.glReadBuffer(0);
/* 3811 */     checkGLError("FT d");
/*      */     
/* 3813 */     for (int k = 0; k < usedColorBuffers; k++) {
/*      */       
/* 3815 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(k));
/* 3816 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3817 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3818 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3819 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3820 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[k], renderWidth, renderHeight, 0, getPixelFormat(gbuffersFormat[k]), 33639, (ByteBuffer)null);
/* 3821 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesFlip.getA(k), 0);
/* 3822 */       checkGLError("FT c");
/*      */     } 
/*      */     
/* 3825 */     for (int l = 0; l < usedColorBuffers; l++) {
/*      */       
/* 3827 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getB(l));
/* 3828 */       GL11.glTexParameteri(3553, 10242, 33071);
/* 3829 */       GL11.glTexParameteri(3553, 10243, 33071);
/* 3830 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3831 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3832 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[l], renderWidth, renderHeight, 0, getPixelFormat(gbuffersFormat[l]), 33639, (ByteBuffer)null);
/* 3833 */       checkGLError("FT ca");
/*      */     } 
/*      */     
/* 3836 */     int i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 3838 */     if (i1 == 36058) {
/*      */       
/* 3840 */       printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");
/*      */       
/* 3842 */       for (int j = 0; j < usedColorBuffers; j++) {
/*      */         
/* 3844 */         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(j));
/* 3845 */         GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3846 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getA(j), 0);
/* 3847 */         checkGLError("FT c");
/*      */       } 
/*      */       
/* 3850 */       i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3852 */       if (i1 == 36053)
/*      */       {
/* 3854 */         SMCLog.info("complete");
/*      */       }
/*      */     } 
/*      */     
/* 3858 */     GlStateManager.bindTexture(0);
/*      */     
/* 3860 */     if (i1 != 36053) {
/*      */       
/* 3862 */       printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + i1 + ")");
/*      */     }
/*      */     else {
/*      */       
/* 3866 */       SMCLog.info("Framebuffer created.");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getPixelFormat(int internalFormat) {
/* 3872 */     switch (internalFormat) {
/*      */       
/*      */       case 33333:
/*      */       case 33334:
/*      */       case 33339:
/*      */       case 33340:
/*      */       case 36208:
/*      */       case 36209:
/*      */       case 36226:
/*      */       case 36227:
/* 3882 */         return 36251;
/*      */     } 
/*      */     
/* 3885 */     return 32993;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setupShadowFrameBuffer() {
/* 3891 */     if (usedShadowDepthBuffers != 0) {
/*      */       
/* 3893 */       if (sfb != 0) {
/*      */         
/* 3895 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3896 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3897 */         GlStateManager.deleteTextures(sfbColorTextures);
/*      */       } 
/*      */       
/* 3900 */       sfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3901 */       EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
/* 3902 */       GL11.glDrawBuffer(0);
/* 3903 */       GL11.glReadBuffer(0);
/* 3904 */       GL11.glGenTextures((IntBuffer)sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
/* 3905 */       GL11.glGenTextures((IntBuffer)sfbColorTextures.clear().limit(usedShadowColorBuffers));
/* 3906 */       sfbDepthTextures.position(0);
/* 3907 */       sfbColorTextures.position(0);
/*      */       
/* 3909 */       for (int i = 0; i < usedShadowDepthBuffers; i++) {
/*      */         
/* 3911 */         GlStateManager.bindTexture(sfbDepthTextures.get(i));
/* 3912 */         GL11.glTexParameterf(3553, 10242, 33071.0F);
/* 3913 */         GL11.glTexParameterf(3553, 10243, 33071.0F);
/* 3914 */         int j = shadowFilterNearest[i] ? 9728 : 9729;
/* 3915 */         GL11.glTexParameteri(3553, 10241, j);
/* 3916 */         GL11.glTexParameteri(3553, 10240, j);
/*      */         
/* 3918 */         if (shadowHardwareFilteringEnabled[i])
/*      */         {
/* 3920 */           GL11.glTexParameteri(3553, 34892, 34894);
/*      */         }
/*      */         
/* 3923 */         GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, (ByteBuffer)null);
/*      */       } 
/*      */       
/* 3926 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 3927 */       checkGLError("FT sd");
/*      */       
/* 3929 */       for (int k = 0; k < usedShadowColorBuffers; k++) {
/*      */         
/* 3931 */         GlStateManager.bindTexture(sfbColorTextures.get(k));
/* 3932 */         GL11.glTexParameterf(3553, 10242, 33071.0F);
/* 3933 */         GL11.glTexParameterf(3553, 10243, 33071.0F);
/* 3934 */         int i1 = shadowColorFilterNearest[k] ? 9728 : 9729;
/* 3935 */         GL11.glTexParameteri(3553, 10241, i1);
/* 3936 */         GL11.glTexParameteri(3553, 10240, i1);
/* 3937 */         GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, 32993, 33639, (ByteBuffer)null);
/* 3938 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, sfbColorTextures.get(k), 0);
/* 3939 */         checkGLError("FT sc");
/*      */       } 
/*      */       
/* 3942 */       GlStateManager.bindTexture(0);
/*      */       
/* 3944 */       if (usedShadowColorBuffers > 0)
/*      */       {
/* 3946 */         GL20.glDrawBuffers(sfbDrawBuffers);
/*      */       }
/*      */       
/* 3949 */       int l = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3951 */       if (l != 36053) {
/*      */         
/* 3953 */         printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + l + ")");
/*      */       }
/*      */       else {
/*      */         
/* 3957 */         SMCLog.info("Shadow framebuffer created.");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginRender(Minecraft minecraft, float partialTicks, long finishTimeNano) {
/* 3964 */     checkGLError("pre beginRender");
/* 3965 */     checkWorldChanged((World)mc.theWorld);
/* 3966 */     mc = minecraft;
/* 3967 */     mc.mcProfiler.startSection("init");
/* 3968 */     entityRenderer = mc.entityRenderer;
/*      */     
/* 3970 */     if (!isShaderPackInitialized) {
/*      */       
/*      */       try {
/*      */         
/* 3974 */         init();
/*      */       }
/* 3976 */       catch (IllegalStateException illegalstateexception) {
/*      */         
/* 3978 */         if (Config.normalize(illegalstateexception.getMessage()).equals("Function is not supported")) {
/*      */           
/* 3980 */           printChatAndLogError("[Shaders] Error: " + illegalstateexception.getMessage());
/* 3981 */           illegalstateexception.printStackTrace();
/* 3982 */           setShaderPack("OFF");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/* 3988 */     if (mc.displayWidth != renderDisplayWidth || mc.displayHeight != renderDisplayHeight)
/*      */     {
/* 3990 */       resize();
/*      */     }
/*      */     
/* 3993 */     if (needResizeShadow)
/*      */     {
/* 3995 */       resizeShadow();
/*      */     }
/*      */     
/* 3998 */     worldTime = mc.theWorld.getWorldTime();
/* 3999 */     diffWorldTime = (worldTime - lastWorldTime) % 24000L;
/*      */     
/* 4001 */     if (diffWorldTime < 0L)
/*      */     {
/* 4003 */       diffWorldTime += 24000L;
/*      */     }
/*      */     
/* 4006 */     lastWorldTime = worldTime;
/* 4007 */     moonPhase = mc.theWorld.getMoonPhase();
/* 4008 */     frameCounter++;
/*      */     
/* 4010 */     if (frameCounter >= 720720)
/*      */     {
/* 4012 */       frameCounter = 0;
/*      */     }
/*      */     
/* 4015 */     systemTime = System.currentTimeMillis();
/*      */     
/* 4017 */     if (lastSystemTime == 0L)
/*      */     {
/* 4019 */       lastSystemTime = systemTime;
/*      */     }
/*      */     
/* 4022 */     diffSystemTime = systemTime - lastSystemTime;
/* 4023 */     lastSystemTime = systemTime;
/* 4024 */     frameTime = (float)diffSystemTime / 1000.0F;
/* 4025 */     frameTimeCounter += frameTime;
/* 4026 */     frameTimeCounter %= 3600.0F;
/* 4027 */     rainStrength = minecraft.theWorld.getRainStrength(partialTicks);
/* 4028 */     float f = (float)diffSystemTime * 0.01F;
/* 4029 */     float f1 = (float)Math.exp(Math.log(0.5D) * f / ((wetness < rainStrength) ? drynessHalfLife : wetnessHalfLife));
/* 4030 */     wetness = wetness * f1 + rainStrength * (1.0F - f1);
/* 4031 */     Entity entity = mc.getRenderViewEntity();
/*      */     
/* 4033 */     if (entity != null) {
/*      */       
/* 4035 */       isSleeping = (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping());
/* 4036 */       eyePosY = (float)entity.posY * partialTicks + (float)entity.lastTickPosY * (1.0F - partialTicks);
/* 4037 */       eyeBrightness = entity.getBrightnessForRender(partialTicks);
/* 4038 */       f1 = (float)diffSystemTime * 0.01F;
/* 4039 */       float f2 = (float)Math.exp(Math.log(0.5D) * f1 / eyeBrightnessHalflife);
/* 4040 */       eyeBrightnessFadeX = eyeBrightnessFadeX * f2 + (eyeBrightness & 0xFFFF) * (1.0F - f2);
/* 4041 */       eyeBrightnessFadeY = eyeBrightnessFadeY * f2 + (eyeBrightness >> 16) * (1.0F - f2);
/* 4042 */       Block block = ActiveRenderInfo.getBlockAtEntityViewpoint((World)mc.theWorld, entity, partialTicks);
/* 4043 */       Material material = block.getMaterial();
/*      */       
/* 4045 */       if (material == Material.water) {
/*      */         
/* 4047 */         isEyeInWater = 1;
/*      */       }
/* 4049 */       else if (material == Material.lava) {
/*      */         
/* 4051 */         isEyeInWater = 2;
/*      */       }
/*      */       else {
/*      */         
/* 4055 */         isEyeInWater = 0;
/*      */       } 
/*      */       
/* 4058 */       if (mc.thePlayer != null) {
/*      */         
/* 4060 */         nightVision = 0.0F;
/*      */         
/* 4062 */         if (mc.thePlayer.isPotionActive(Potion.nightVision))
/*      */         {
/* 4064 */           nightVision = (Config.getMinecraft()).entityRenderer.getNightVisionBrightness((EntityLivingBase)mc.thePlayer, partialTicks);
/*      */         }
/*      */         
/* 4067 */         blindness = 0.0F;
/*      */         
/* 4069 */         if (mc.thePlayer.isPotionActive(Potion.blindness)) {
/*      */           
/* 4071 */           int i = mc.thePlayer.getActivePotionEffect(Potion.blindness).getDuration();
/* 4072 */           blindness = Config.limit(i / 20.0F, 0.0F, 1.0F);
/*      */         } 
/*      */       } 
/*      */       
/* 4076 */       Vec3 vec3 = mc.theWorld.getSkyColor(entity, partialTicks);
/* 4077 */       vec3 = CustomColors.getWorldSkyColor(vec3, currentWorld, entity, partialTicks);
/* 4078 */       skyColorR = (float)vec3.xCoord;
/* 4079 */       skyColorG = (float)vec3.yCoord;
/* 4080 */       skyColorB = (float)vec3.zCoord;
/*      */     } 
/*      */     
/* 4083 */     isRenderingWorld = true;
/* 4084 */     isCompositeRendered = false;
/* 4085 */     isShadowPass = false;
/* 4086 */     isHandRenderedMain = false;
/* 4087 */     isHandRenderedOff = false;
/* 4088 */     skipRenderHandMain = false;
/* 4089 */     skipRenderHandOff = false;
/* 4090 */     bindGbuffersTextures();
/* 4091 */     previousCameraPositionX = cameraPositionX;
/* 4092 */     previousCameraPositionY = cameraPositionY;
/* 4093 */     previousCameraPositionZ = cameraPositionZ;
/* 4094 */     previousProjection.position(0);
/* 4095 */     projection.position(0);
/* 4096 */     previousProjection.put(projection);
/* 4097 */     previousProjection.position(0);
/* 4098 */     projection.position(0);
/* 4099 */     previousModelView.position(0);
/* 4100 */     modelView.position(0);
/* 4101 */     previousModelView.put(modelView);
/* 4102 */     previousModelView.position(0);
/* 4103 */     modelView.position(0);
/* 4104 */     checkGLError("beginRender");
/* 4105 */     ShadersRender.renderShadowMap(entityRenderer, 0, partialTicks, finishTimeNano);
/* 4106 */     mc.mcProfiler.endSection();
/* 4107 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/*      */     
/* 4109 */     for (int j = 0; j < usedColorBuffers; j++)
/*      */     {
/* 4111 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getA(j), 0);
/*      */     }
/*      */     
/* 4114 */     checkGLError("end beginRender");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void bindGbuffersTextures() {
/* 4119 */     if (usedShadowDepthBuffers >= 1) {
/*      */       
/* 4121 */       GlStateManager.setActiveTexture(33988);
/* 4122 */       GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */       
/* 4124 */       if (usedShadowDepthBuffers >= 2) {
/*      */         
/* 4126 */         GlStateManager.setActiveTexture(33989);
/* 4127 */         GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */       } 
/*      */     } 
/*      */     
/* 4131 */     GlStateManager.setActiveTexture(33984);
/*      */     
/* 4133 */     for (int i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 4135 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(i));
/* 4136 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 4137 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 4138 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getB(i));
/* 4139 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 4140 */       GL11.glTexParameteri(3553, 10241, 9729);
/*      */     } 
/*      */     
/* 4143 */     GlStateManager.bindTexture(0);
/*      */     
/* 4145 */     for (int j = 0; j < 4 && 4 + j < usedColorBuffers; j++) {
/*      */       
/* 4147 */       GlStateManager.setActiveTexture(33991 + j);
/* 4148 */       GlStateManager.bindTexture(dfbColorTexturesFlip.getA(4 + j));
/*      */     } 
/*      */     
/* 4151 */     GlStateManager.setActiveTexture(33990);
/* 4152 */     GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */     
/* 4154 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 4156 */       GlStateManager.setActiveTexture(33995);
/* 4157 */       GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */       
/* 4159 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 4161 */         GlStateManager.setActiveTexture(33996);
/* 4162 */         GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */       } 
/*      */     } 
/*      */     
/* 4166 */     for (int k = 0; k < usedShadowColorBuffers; k++) {
/*      */       
/* 4168 */       GlStateManager.setActiveTexture(33997 + k);
/* 4169 */       GlStateManager.bindTexture(sfbColorTextures.get(k));
/*      */     } 
/*      */     
/* 4172 */     if (noiseTextureEnabled) {
/*      */       
/* 4174 */       GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 4175 */       GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */     } 
/*      */     
/* 4178 */     bindCustomTextures(customTexturesGbuffers);
/* 4179 */     GlStateManager.setActiveTexture(33984);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkWorldChanged(World world) {
/* 4184 */     if (currentWorld != world) {
/*      */       
/* 4186 */       World oldworld = currentWorld;
/* 4187 */       currentWorld = world;
/* 4188 */       setCameraOffset(mc.getRenderViewEntity());
/* 4189 */       int i = getDimensionId(oldworld);
/* 4190 */       int j = getDimensionId(world);
/*      */       
/* 4192 */       if (j != i) {
/*      */         
/* 4194 */         boolean flag = shaderPackDimensions.contains(Integer.valueOf(i));
/* 4195 */         boolean flag1 = shaderPackDimensions.contains(Integer.valueOf(j));
/*      */         
/* 4197 */         if (flag || flag1)
/*      */         {
/* 4199 */           uninit();
/*      */         }
/*      */       } 
/*      */       
/* 4203 */       Smoother.resetValues();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getDimensionId(World world) {
/* 4209 */     return (world == null) ? Integer.MIN_VALUE : world.provider.getDimensionId();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginRenderPass(int pass, float partialTicks, long finishTimeNano) {
/* 4214 */     if (!isShadowPass) {
/*      */       
/* 4216 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 4217 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 4218 */       activeDrawBuffers = null;
/* 4219 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/* 4220 */       useProgram(ProgramTextured);
/* 4221 */       checkGLError("end beginRenderPass");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setViewport(int vx, int vy, int vw, int vh) {
/* 4227 */     GlStateManager.colorMask(true, true, true, true);
/*      */     
/* 4229 */     if (isShadowPass) {
/*      */       
/* 4231 */       GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/*      */     }
/*      */     else {
/*      */       
/* 4235 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 4236 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 4237 */       isRenderingDfb = true;
/* 4238 */       GlStateManager.enableCull();
/* 4239 */       GlStateManager.enableDepth();
/* 4240 */       setDrawBuffers(drawBuffersNone);
/* 4241 */       useProgram(ProgramTextured);
/* 4242 */       checkGLError("beginRenderPass");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogMode(int value) {
/* 4248 */     fogMode = value;
/*      */     
/* 4250 */     if (fogEnabled)
/*      */     {
/* 4252 */       setProgramUniform1i(uniform_fogMode, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogColor(float r, float g, float b) {
/* 4258 */     fogColorR = r;
/* 4259 */     fogColorG = g;
/* 4260 */     fogColorB = b;
/* 4261 */     setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setClearColor(float red, float green, float blue, float alpha) {
/* 4266 */     GlStateManager.clearColor(red, green, blue, alpha);
/* 4267 */     clearColorR = red;
/* 4268 */     clearColorG = green;
/* 4269 */     clearColorB = blue;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearRenderBuffer() {
/* 4274 */     if (isShadowPass) {
/*      */       
/* 4276 */       checkGLError("shadow clear pre");
/* 4277 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 4278 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 4279 */       GL20.glDrawBuffers(ProgramShadow.getDrawBuffers());
/* 4280 */       checkFramebufferStatus("shadow clear");
/* 4281 */       GL11.glClear(16640);
/* 4282 */       checkGLError("shadow clear");
/*      */     }
/*      */     else {
/*      */       
/* 4286 */       checkGLError("clear pre");
/*      */       
/* 4288 */       if (gbuffersClear[0]) {
/*      */         
/* 4290 */         Vector4f vector4f = gbuffersClearColor[0];
/*      */         
/* 4292 */         if (vector4f != null)
/*      */         {
/* 4294 */           GL11.glClearColor(vector4f.getX(), vector4f.getY(), vector4f.getZ(), vector4f.getW());
/*      */         }
/*      */         
/* 4297 */         if (dfbColorTexturesFlip.isChanged(0)) {
/*      */           
/* 4299 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, dfbColorTexturesFlip.getB(0), 0);
/* 4300 */           GL20.glDrawBuffers(36064);
/* 4301 */           GL11.glClear(16384);
/* 4302 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064, 3553, dfbColorTexturesFlip.getA(0), 0);
/*      */         } 
/*      */         
/* 4305 */         GL20.glDrawBuffers(36064);
/* 4306 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 4309 */       if (gbuffersClear[1]) {
/*      */         
/* 4311 */         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 4312 */         Vector4f vector4f2 = gbuffersClearColor[1];
/*      */         
/* 4314 */         if (vector4f2 != null)
/*      */         {
/* 4316 */           GL11.glClearColor(vector4f2.getX(), vector4f2.getY(), vector4f2.getZ(), vector4f2.getW());
/*      */         }
/*      */         
/* 4319 */         if (dfbColorTexturesFlip.isChanged(1)) {
/*      */           
/* 4321 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36065, 3553, dfbColorTexturesFlip.getB(1), 0);
/* 4322 */           GL20.glDrawBuffers(36065);
/* 4323 */           GL11.glClear(16384);
/* 4324 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36065, 3553, dfbColorTexturesFlip.getA(1), 0);
/*      */         } 
/*      */         
/* 4327 */         GL20.glDrawBuffers(36065);
/* 4328 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 4331 */       for (int i = 2; i < usedColorBuffers; i++) {
/*      */         
/* 4333 */         if (gbuffersClear[i]) {
/*      */           
/* 4335 */           GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 4336 */           Vector4f vector4f1 = gbuffersClearColor[i];
/*      */           
/* 4338 */           if (vector4f1 != null)
/*      */           {
/* 4340 */             GL11.glClearColor(vector4f1.getX(), vector4f1.getY(), vector4f1.getZ(), vector4f1.getW());
/*      */           }
/*      */           
/* 4343 */           if (dfbColorTexturesFlip.isChanged(i)) {
/*      */             
/* 4345 */             EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getB(i), 0);
/* 4346 */             GL20.glDrawBuffers(36064 + i);
/* 4347 */             GL11.glClear(16384);
/* 4348 */             EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getA(i), 0);
/*      */           } 
/*      */           
/* 4351 */           GL20.glDrawBuffers(36064 + i);
/* 4352 */           GL11.glClear(16384);
/*      */         } 
/*      */       } 
/*      */       
/* 4356 */       setDrawBuffers(dfbDrawBuffers);
/* 4357 */       checkFramebufferStatus("clear");
/* 4358 */       checkGLError("clear");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCamera(float partialTicks) {
/* 4364 */     Entity entity = mc.getRenderViewEntity();
/* 4365 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 4366 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 4367 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 4368 */     updateCameraOffset(entity);
/* 4369 */     cameraPositionX = d0 - cameraOffsetX;
/* 4370 */     cameraPositionY = d1;
/* 4371 */     cameraPositionZ = d2 - cameraOffsetZ;
/* 4372 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 4373 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 4374 */     projection.position(0);
/* 4375 */     projectionInverse.position(0);
/* 4376 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 4377 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 4378 */     modelView.position(0);
/* 4379 */     modelViewInverse.position(0);
/* 4380 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void updateCameraOffset(Entity viewEntity) {
/* 4385 */     double d0 = Math.abs(cameraPositionX - previousCameraPositionX);
/* 4386 */     double d1 = Math.abs(cameraPositionZ - previousCameraPositionZ);
/* 4387 */     double d2 = Math.abs(cameraPositionX);
/* 4388 */     double d3 = Math.abs(cameraPositionZ);
/*      */     
/* 4390 */     if (d0 > 1000.0D || d1 > 1000.0D || d2 > 1000000.0D || d3 > 1000000.0D)
/*      */     {
/* 4392 */       setCameraOffset(viewEntity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setCameraOffset(Entity viewEntity) {
/* 4398 */     if (viewEntity == null) {
/*      */       
/* 4400 */       cameraOffsetX = 0;
/* 4401 */       cameraOffsetZ = 0;
/*      */     }
/*      */     else {
/*      */       
/* 4405 */       cameraOffsetX = (int)viewEntity.posX / 1000 * 1000;
/* 4406 */       cameraOffsetZ = (int)viewEntity.posZ / 1000 * 1000;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCameraShadow(float partialTicks) {
/* 4412 */     Entity entity = mc.getRenderViewEntity();
/* 4413 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 4414 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 4415 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 4416 */     updateCameraOffset(entity);
/* 4417 */     cameraPositionX = d0 - cameraOffsetX;
/* 4418 */     cameraPositionY = d1;
/* 4419 */     cameraPositionZ = d2 - cameraOffsetZ;
/* 4420 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 4421 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 4422 */     projection.position(0);
/* 4423 */     projectionInverse.position(0);
/* 4424 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 4425 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 4426 */     modelView.position(0);
/* 4427 */     modelViewInverse.position(0);
/* 4428 */     GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/* 4429 */     GL11.glMatrixMode(5889);
/* 4430 */     GL11.glLoadIdentity();
/*      */     
/* 4432 */     if (shadowMapIsOrtho) {
/*      */       
/* 4434 */       GL11.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05000000074505806D, 256.0D);
/*      */     }
/*      */     else {
/*      */       
/* 4438 */       GLU.gluPerspective(shadowMapFOV, shadowMapWidth / shadowMapHeight, 0.05F, 256.0F);
/*      */     } 
/*      */     
/* 4441 */     GL11.glMatrixMode(5888);
/* 4442 */     GL11.glLoadIdentity();
/* 4443 */     GL11.glTranslatef(0.0F, 0.0F, -100.0F);
/* 4444 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 4445 */     celestialAngle = mc.theWorld.getCelestialAngle(partialTicks);
/* 4446 */     sunAngle = (celestialAngle < 0.75F) ? (celestialAngle + 0.25F) : (celestialAngle - 0.75F);
/* 4447 */     float f = celestialAngle * -360.0F;
/* 4448 */     float f1 = (shadowAngleInterval > 0.0F) ? (f % shadowAngleInterval - shadowAngleInterval * 0.5F) : 0.0F;
/*      */     
/* 4450 */     if (sunAngle <= 0.5D) {
/*      */       
/* 4452 */       GL11.glRotatef(f - f1, 0.0F, 0.0F, 1.0F);
/* 4453 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 4454 */       shadowAngle = sunAngle;
/*      */     }
/*      */     else {
/*      */       
/* 4458 */       GL11.glRotatef(f + 180.0F - f1, 0.0F, 0.0F, 1.0F);
/* 4459 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 4460 */       shadowAngle = sunAngle - 0.5F;
/*      */     } 
/*      */     
/* 4463 */     if (shadowMapIsOrtho) {
/*      */       
/* 4465 */       float f2 = shadowIntervalSize;
/* 4466 */       float f3 = f2 / 2.0F;
/* 4467 */       GL11.glTranslatef((float)d0 % f2 - f3, (float)d1 % f2 - f3, (float)d2 % f2 - f3);
/*      */     } 
/*      */     
/* 4470 */     float f9 = sunAngle * 6.2831855F;
/* 4471 */     float f10 = (float)Math.cos(f9);
/* 4472 */     float f4 = (float)Math.sin(f9);
/* 4473 */     float f5 = sunPathRotation * 6.2831855F;
/* 4474 */     float f6 = f10;
/* 4475 */     float f7 = f4 * (float)Math.cos(f5);
/* 4476 */     float f8 = f4 * (float)Math.sin(f5);
/*      */     
/* 4478 */     if (sunAngle > 0.5D) {
/*      */       
/* 4480 */       f6 = -f10;
/* 4481 */       f7 = -f7;
/* 4482 */       f8 = -f8;
/*      */     } 
/*      */     
/* 4485 */     shadowLightPositionVector[0] = f6;
/* 4486 */     shadowLightPositionVector[1] = f7;
/* 4487 */     shadowLightPositionVector[2] = f8;
/* 4488 */     shadowLightPositionVector[3] = 0.0F;
/* 4489 */     GL11.glGetFloat(2983, (FloatBuffer)shadowProjection.position(0));
/* 4490 */     SMath.invertMat4FBFA((FloatBuffer)shadowProjectionInverse.position(0), (FloatBuffer)shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
/* 4491 */     shadowProjection.position(0);
/* 4492 */     shadowProjectionInverse.position(0);
/* 4493 */     GL11.glGetFloat(2982, (FloatBuffer)shadowModelView.position(0));
/* 4494 */     SMath.invertMat4FBFA((FloatBuffer)shadowModelViewInverse.position(0), (FloatBuffer)shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
/* 4495 */     shadowModelView.position(0);
/* 4496 */     shadowModelViewInverse.position(0);
/* 4497 */     setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
/* 4498 */     setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
/* 4499 */     setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
/* 4500 */     setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
/* 4501 */     setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
/* 4502 */     setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
/* 4503 */     setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
/* 4504 */     setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
/* 4505 */     setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
/* 4506 */     setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
/* 4507 */     mc.gameSettings.thirdPersonView = 1;
/* 4508 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preCelestialRotate() {
/* 4513 */     GL11.glRotatef(sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
/* 4514 */     checkGLError("preCelestialRotate");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void postCelestialRotate() {
/* 4519 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 4520 */     floatbuffer.clear();
/* 4521 */     GL11.glGetFloat(2982, floatbuffer);
/* 4522 */     floatbuffer.get(tempMat, 0, 16);
/* 4523 */     SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
/* 4524 */     SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
/* 4525 */     System.arraycopy((shadowAngle == sunAngle) ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
/* 4526 */     setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
/* 4527 */     setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
/* 4528 */     setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/*      */     
/* 4530 */     if (customUniforms != null)
/*      */     {
/* 4532 */       customUniforms.update();
/*      */     }
/*      */     
/* 4535 */     checkGLError("postCelestialRotate");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setUpPosition() {
/* 4540 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 4541 */     floatbuffer.clear();
/* 4542 */     GL11.glGetFloat(2982, floatbuffer);
/* 4543 */     floatbuffer.get(tempMat, 0, 16);
/* 4544 */     SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
/* 4545 */     setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
/*      */     
/* 4547 */     if (customUniforms != null)
/*      */     {
/* 4549 */       customUniforms.update();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void genCompositeMipmap() {
/* 4555 */     if (hasGlGenMipmap) {
/*      */       
/* 4557 */       for (int i = 0; i < usedColorBuffers; i++) {
/*      */         
/* 4559 */         if ((activeCompositeMipmapSetting & 1 << i) != 0) {
/*      */           
/* 4561 */           GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[i]);
/* 4562 */           GL11.glTexParameteri(3553, 10241, 9987);
/* 4563 */           GL30.glGenerateMipmap(3553);
/*      */         } 
/*      */       } 
/*      */       
/* 4567 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawComposite() {
/* 4573 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4574 */     drawCompositeQuad();
/* 4575 */     int i = activeProgram.getCountInstances();
/*      */     
/* 4577 */     if (i > 1) {
/*      */       
/* 4579 */       for (int j = 1; j < i; j++) {
/*      */         
/* 4581 */         uniform_instanceId.setValue(j);
/* 4582 */         drawCompositeQuad();
/*      */       } 
/*      */       
/* 4585 */       uniform_instanceId.setValue(0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void drawCompositeQuad() {
/* 4591 */     if (!canRenderQuads()) {
/*      */       
/* 4593 */       GL11.glBegin(5);
/* 4594 */       GL11.glTexCoord2f(0.0F, 0.0F);
/* 4595 */       GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 4596 */       GL11.glTexCoord2f(1.0F, 0.0F);
/* 4597 */       GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 4598 */       GL11.glTexCoord2f(0.0F, 1.0F);
/* 4599 */       GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 4600 */       GL11.glTexCoord2f(1.0F, 1.0F);
/* 4601 */       GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 4602 */       GL11.glEnd();
/*      */     }
/*      */     else {
/*      */       
/* 4606 */       GL11.glBegin(7);
/* 4607 */       GL11.glTexCoord2f(0.0F, 0.0F);
/* 4608 */       GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 4609 */       GL11.glTexCoord2f(1.0F, 0.0F);
/* 4610 */       GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 4611 */       GL11.glTexCoord2f(1.0F, 1.0F);
/* 4612 */       GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 4613 */       GL11.glTexCoord2f(0.0F, 1.0F);
/* 4614 */       GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 4615 */       GL11.glEnd();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderDeferred() {
/* 4621 */     if (!isShadowPass) {
/*      */       
/* 4623 */       boolean flag = checkBufferFlip(ProgramDeferredPre);
/*      */       
/* 4625 */       if (hasDeferredPrograms) {
/*      */         
/* 4627 */         checkGLError("pre-render Deferred");
/* 4628 */         renderComposites(ProgramsDeferred, false);
/* 4629 */         flag = true;
/*      */       } 
/*      */       
/* 4632 */       if (flag) {
/*      */         
/* 4634 */         bindGbuffersTextures();
/*      */         
/* 4636 */         for (int i = 0; i < usedColorBuffers; i++)
/*      */         {
/* 4638 */           EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i, 3553, dfbColorTexturesFlip.getA(i), 0);
/*      */         }
/*      */         
/* 4641 */         if (ProgramWater.getDrawBuffers() != null) {
/*      */           
/* 4643 */           setDrawBuffers(ProgramWater.getDrawBuffers());
/*      */         }
/*      */         else {
/*      */           
/* 4647 */           setDrawBuffers(dfbDrawBuffers);
/*      */         } 
/*      */         
/* 4650 */         GlStateManager.setActiveTexture(33984);
/* 4651 */         mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderCompositeFinal() {
/* 4658 */     if (!isShadowPass) {
/*      */       
/* 4660 */       checkBufferFlip(ProgramCompositePre);
/* 4661 */       checkGLError("pre-render CompositeFinal");
/* 4662 */       renderComposites(ProgramsComposite, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean checkBufferFlip(Program program) {
/* 4668 */     boolean flag = false;
/* 4669 */     Boolean[] aboolean = program.getBuffersFlip();
/*      */     
/* 4671 */     for (int i = 0; i < usedColorBuffers; i++) {
/*      */       
/* 4673 */       if (Config.isTrue(aboolean[i])) {
/*      */         
/* 4675 */         dfbColorTexturesFlip.flip(i);
/* 4676 */         flag = true;
/*      */       } 
/*      */     } 
/*      */     
/* 4680 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void renderComposites(Program[] ps, boolean renderFinal) {
/* 4685 */     if (!isShadowPass) {
/*      */       
/* 4687 */       GL11.glPushMatrix();
/* 4688 */       GL11.glLoadIdentity();
/* 4689 */       GL11.glMatrixMode(5889);
/* 4690 */       GL11.glPushMatrix();
/* 4691 */       GL11.glLoadIdentity();
/* 4692 */       GL11.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
/* 4693 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4694 */       GlStateManager.enableTexture2D();
/* 4695 */       GlStateManager.disableAlpha();
/* 4696 */       GlStateManager.disableBlend();
/* 4697 */       GlStateManager.enableDepth();
/* 4698 */       GlStateManager.depthFunc(519);
/* 4699 */       GlStateManager.depthMask(false);
/* 4700 */       GlStateManager.disableLighting();
/*      */       
/* 4702 */       if (usedShadowDepthBuffers >= 1) {
/*      */         
/* 4704 */         GlStateManager.setActiveTexture(33988);
/* 4705 */         GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */         
/* 4707 */         if (usedShadowDepthBuffers >= 2) {
/*      */           
/* 4709 */           GlStateManager.setActiveTexture(33989);
/* 4710 */           GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */         } 
/*      */       } 
/*      */       
/* 4714 */       for (int i = 0; i < usedColorBuffers; i++) {
/*      */         
/* 4716 */         GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[i]);
/* 4717 */         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(i));
/*      */       } 
/*      */       
/* 4720 */       GlStateManager.setActiveTexture(33990);
/* 4721 */       GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */       
/* 4723 */       if (usedDepthBuffers >= 2) {
/*      */         
/* 4725 */         GlStateManager.setActiveTexture(33995);
/* 4726 */         GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */         
/* 4728 */         if (usedDepthBuffers >= 3) {
/*      */           
/* 4730 */           GlStateManager.setActiveTexture(33996);
/* 4731 */           GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */         } 
/*      */       } 
/*      */       
/* 4735 */       for (int k = 0; k < usedShadowColorBuffers; k++) {
/*      */         
/* 4737 */         GlStateManager.setActiveTexture(33997 + k);
/* 4738 */         GlStateManager.bindTexture(sfbColorTextures.get(k));
/*      */       } 
/*      */       
/* 4741 */       if (noiseTextureEnabled) {
/*      */         
/* 4743 */         GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 4744 */         GlStateManager.bindTexture(noiseTexture.getTextureId());
/*      */       } 
/*      */       
/* 4747 */       if (renderFinal) {
/*      */         
/* 4749 */         bindCustomTextures(customTexturesComposite);
/*      */       }
/*      */       else {
/*      */         
/* 4753 */         bindCustomTextures(customTexturesDeferred);
/*      */       } 
/*      */       
/* 4756 */       GlStateManager.setActiveTexture(33984);
/*      */       
/* 4758 */       for (int l = 0; l < usedColorBuffers; l++)
/*      */       {
/* 4760 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + l, 3553, dfbColorTexturesFlip.getB(l), 0);
/*      */       }
/*      */       
/* 4763 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 4764 */       GL20.glDrawBuffers(dfbDrawBuffers);
/* 4765 */       checkGLError("pre-composite");
/*      */       
/* 4767 */       for (int i1 = 0; i1 < ps.length; i1++) {
/*      */         
/* 4769 */         Program program = ps[i1];
/*      */         
/* 4771 */         if (program.getId() != 0) {
/*      */           
/* 4773 */           useProgram(program);
/* 4774 */           checkGLError(program.getName());
/*      */           
/* 4776 */           if (activeCompositeMipmapSetting != 0)
/*      */           {
/* 4778 */             genCompositeMipmap();
/*      */           }
/*      */           
/* 4781 */           preDrawComposite();
/* 4782 */           drawComposite();
/* 4783 */           postDrawComposite();
/*      */           
/* 4785 */           for (int j = 0; j < usedColorBuffers; j++) {
/*      */             
/* 4787 */             if (program.getToggleColorTextures()[j]) {
/*      */               
/* 4789 */               dfbColorTexturesFlip.flip(j);
/* 4790 */               GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[j]);
/* 4791 */               GlStateManager.bindTexture(dfbColorTexturesFlip.getA(j));
/* 4792 */               EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesFlip.getB(j), 0);
/*      */             } 
/*      */           } 
/*      */           
/* 4796 */           GlStateManager.setActiveTexture(33984);
/*      */         } 
/*      */       } 
/*      */       
/* 4800 */       checkGLError("composite");
/*      */       
/* 4802 */       if (renderFinal) {
/*      */         
/* 4804 */         renderFinal();
/* 4805 */         isCompositeRendered = true;
/*      */       } 
/*      */       
/* 4808 */       GlStateManager.enableLighting();
/* 4809 */       GlStateManager.enableTexture2D();
/* 4810 */       GlStateManager.enableAlpha();
/* 4811 */       GlStateManager.enableBlend();
/* 4812 */       GlStateManager.depthFunc(515);
/* 4813 */       GlStateManager.depthMask(true);
/* 4814 */       GL11.glPopMatrix();
/* 4815 */       GL11.glMatrixMode(5888);
/* 4816 */       GL11.glPopMatrix();
/* 4817 */       useProgram(ProgramNone);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void preDrawComposite() {
/* 4823 */     RenderScale renderscale = activeProgram.getRenderScale();
/*      */     
/* 4825 */     if (renderscale != null) {
/*      */       
/* 4827 */       int i = (int)(renderWidth * renderscale.getOffsetX());
/* 4828 */       int j = (int)(renderHeight * renderscale.getOffsetY());
/* 4829 */       int k = (int)(renderWidth * renderscale.getScale());
/* 4830 */       int l = (int)(renderHeight * renderscale.getScale());
/* 4831 */       GL11.glViewport(i, j, k, l);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void postDrawComposite() {
/* 4837 */     RenderScale renderscale = activeProgram.getRenderScale();
/*      */     
/* 4839 */     if (renderscale != null)
/*      */     {
/* 4841 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void renderFinal() {
/* 4847 */     isRenderingDfb = false;
/* 4848 */     mc.getFramebuffer().bindFramebuffer(true);
/* 4849 */     OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, (mc.getFramebuffer()).framebufferTexture, 0);
/* 4850 */     GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
/*      */     
/* 4852 */     if (EntityRenderer.anaglyphEnable) {
/*      */       
/* 4854 */       boolean flag = (EntityRenderer.anaglyphField != 0);
/* 4855 */       GlStateManager.colorMask(flag, !flag, !flag, true);
/*      */     } 
/*      */     
/* 4858 */     GlStateManager.depthMask(true);
/* 4859 */     GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
/* 4860 */     GL11.glClear(16640);
/* 4861 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4862 */     GlStateManager.enableTexture2D();
/* 4863 */     GlStateManager.disableAlpha();
/* 4864 */     GlStateManager.disableBlend();
/* 4865 */     GlStateManager.enableDepth();
/* 4866 */     GlStateManager.depthFunc(519);
/* 4867 */     GlStateManager.depthMask(false);
/* 4868 */     checkGLError("pre-final");
/* 4869 */     useProgram(ProgramFinal);
/* 4870 */     checkGLError("final");
/*      */     
/* 4872 */     if (activeCompositeMipmapSetting != 0)
/*      */     {
/* 4874 */       genCompositeMipmap();
/*      */     }
/*      */     
/* 4877 */     drawComposite();
/* 4878 */     checkGLError("renderCompositeFinal");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endRender() {
/* 4883 */     if (isShadowPass) {
/*      */       
/* 4885 */       checkGLError("shadow endRender");
/*      */     }
/*      */     else {
/*      */       
/* 4889 */       if (!isCompositeRendered)
/*      */       {
/* 4891 */         renderCompositeFinal();
/*      */       }
/*      */       
/* 4894 */       isRenderingWorld = false;
/* 4895 */       GlStateManager.colorMask(true, true, true, true);
/* 4896 */       useProgram(ProgramNone);
/* 4897 */       RenderHelper.disableStandardItemLighting();
/* 4898 */       checkGLError("endRender end");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginSky() {
/* 4904 */     isRenderingSky = true;
/* 4905 */     fogEnabled = true;
/* 4906 */     setDrawBuffers(dfbDrawBuffers);
/* 4907 */     useProgram(ProgramSkyTextured);
/* 4908 */     pushEntity(-2, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSkyColor(Vec3 v3color) {
/* 4913 */     skyColorR = (float)v3color.xCoord;
/* 4914 */     skyColorG = (float)v3color.yCoord;
/* 4915 */     skyColorB = (float)v3color.zCoord;
/* 4916 */     setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawHorizon() {
/* 4921 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/* 4922 */     float f = (mc.gameSettings.renderDistanceChunks * 16);
/* 4923 */     double d0 = f * 0.9238D;
/* 4924 */     double d1 = f * 0.3826D;
/* 4925 */     double d2 = -d1;
/* 4926 */     double d3 = -d0;
/* 4927 */     double d4 = 16.0D;
/* 4928 */     double d5 = -cameraPositionY;
/* 4929 */     worldrenderer.begin(7, DefaultVertexFormats.field_181705_e);
/* 4930 */     worldrenderer.pos(d2, d5, d3).endVertex();
/* 4931 */     worldrenderer.pos(d2, d4, d3).endVertex();
/* 4932 */     worldrenderer.pos(d3, d4, d2).endVertex();
/* 4933 */     worldrenderer.pos(d3, d5, d2).endVertex();
/* 4934 */     worldrenderer.pos(d3, d5, d2).endVertex();
/* 4935 */     worldrenderer.pos(d3, d4, d2).endVertex();
/* 4936 */     worldrenderer.pos(d3, d4, d1).endVertex();
/* 4937 */     worldrenderer.pos(d3, d5, d1).endVertex();
/* 4938 */     worldrenderer.pos(d3, d5, d1).endVertex();
/* 4939 */     worldrenderer.pos(d3, d4, d1).endVertex();
/* 4940 */     worldrenderer.pos(d2, d4, d0).endVertex();
/* 4941 */     worldrenderer.pos(d2, d5, d0).endVertex();
/* 4942 */     worldrenderer.pos(d2, d5, d0).endVertex();
/* 4943 */     worldrenderer.pos(d2, d4, d0).endVertex();
/* 4944 */     worldrenderer.pos(d1, d4, d0).endVertex();
/* 4945 */     worldrenderer.pos(d1, d5, d0).endVertex();
/* 4946 */     worldrenderer.pos(d1, d5, d0).endVertex();
/* 4947 */     worldrenderer.pos(d1, d4, d0).endVertex();
/* 4948 */     worldrenderer.pos(d0, d4, d1).endVertex();
/* 4949 */     worldrenderer.pos(d0, d5, d1).endVertex();
/* 4950 */     worldrenderer.pos(d0, d5, d1).endVertex();
/* 4951 */     worldrenderer.pos(d0, d4, d1).endVertex();
/* 4952 */     worldrenderer.pos(d0, d4, d2).endVertex();
/* 4953 */     worldrenderer.pos(d0, d5, d2).endVertex();
/* 4954 */     worldrenderer.pos(d0, d5, d2).endVertex();
/* 4955 */     worldrenderer.pos(d0, d4, d2).endVertex();
/* 4956 */     worldrenderer.pos(d1, d4, d3).endVertex();
/* 4957 */     worldrenderer.pos(d1, d5, d3).endVertex();
/* 4958 */     worldrenderer.pos(d1, d5, d3).endVertex();
/* 4959 */     worldrenderer.pos(d1, d4, d3).endVertex();
/* 4960 */     worldrenderer.pos(d2, d4, d3).endVertex();
/* 4961 */     worldrenderer.pos(d2, d5, d3).endVertex();
/* 4962 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preSkyList() {
/* 4967 */     setUpPosition();
/* 4968 */     GL11.glColor3f(fogColorR, fogColorG, fogColorB);
/* 4969 */     drawHorizon();
/* 4970 */     GL11.glColor3f(skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSky() {
/* 4975 */     isRenderingSky = false;
/* 4976 */     setDrawBuffers(dfbDrawBuffers);
/* 4977 */     useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/* 4978 */     popEntity();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginUpdateChunks() {
/* 4983 */     checkGLError("beginUpdateChunks1");
/* 4984 */     checkFramebufferStatus("beginUpdateChunks1");
/*      */     
/* 4986 */     if (!isShadowPass)
/*      */     {
/* 4988 */       useProgram(ProgramTerrain);
/*      */     }
/*      */     
/* 4991 */     checkGLError("beginUpdateChunks2");
/* 4992 */     checkFramebufferStatus("beginUpdateChunks2");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endUpdateChunks() {
/* 4997 */     checkGLError("endUpdateChunks1");
/* 4998 */     checkFramebufferStatus("endUpdateChunks1");
/*      */     
/* 5000 */     if (!isShadowPass)
/*      */     {
/* 5002 */       useProgram(ProgramTerrain);
/*      */     }
/*      */     
/* 5005 */     checkGLError("endUpdateChunks2");
/* 5006 */     checkFramebufferStatus("endUpdateChunks2");
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean shouldRenderClouds(GameSettings gs) {
/* 5011 */     if (!shaderPackLoaded)
/*      */     {
/* 5013 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 5017 */     checkGLError("shouldRenderClouds");
/* 5018 */     return isShadowPass ? configCloudShadow : ((gs.clouds > 0));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginClouds() {
/* 5024 */     fogEnabled = true;
/* 5025 */     pushEntity(-3, 0);
/* 5026 */     useProgram(ProgramClouds);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endClouds() {
/* 5031 */     disableFog();
/* 5032 */     popEntity();
/* 5033 */     useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginEntities() {
/* 5038 */     if (isRenderingWorld)
/*      */     {
/* 5040 */       useProgram(ProgramEntities);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextEntity(Entity entity) {
/* 5046 */     if (isRenderingWorld) {
/*      */       
/* 5048 */       useProgram(ProgramEntities);
/* 5049 */       setEntityId(entity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityId(Entity entity) {
/* 5055 */     if (uniform_entityId.isDefined()) {
/*      */       
/* 5057 */       int i = EntityUtils.getEntityIdByClass(entity);
/* 5058 */       int j = EntityAliases.getEntityAliasId(i);
/*      */       
/* 5060 */       if (j >= 0)
/*      */       {
/* 5062 */         i = j;
/*      */       }
/*      */       
/* 5065 */       uniform_entityId.setValue(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginSpiderEyes() {
/* 5071 */     if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
/*      */       
/* 5073 */       useProgram(ProgramSpiderEyes);
/* 5074 */       GlStateManager.enableAlpha();
/* 5075 */       GlStateManager.alphaFunc(516, 0.0F);
/* 5076 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSpiderEyes() {
/* 5082 */     if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
/*      */       
/* 5084 */       useProgram(ProgramEntities);
/* 5085 */       GlStateManager.disableAlpha();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endEntities() {
/* 5091 */     if (isRenderingWorld) {
/*      */       
/* 5093 */       setEntityId(null);
/* 5094 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginEntitiesGlowing() {
/* 5100 */     if (isRenderingWorld)
/*      */     {
/* 5102 */       isEntitiesGlowing = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endEntitiesGlowing() {
/* 5108 */     if (isRenderingWorld)
/*      */     {
/* 5110 */       isEntitiesGlowing = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityColor(float r, float g, float b, float a) {
/* 5116 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 5118 */       uniform_entityColor.setValue(r, g, b, a);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLivingDamage() {
/* 5124 */     if (isRenderingWorld) {
/*      */       
/* 5126 */       ShadersTex.bindTexture(defaultTexture);
/*      */       
/* 5128 */       if (!isShadowPass)
/*      */       {
/* 5130 */         setDrawBuffers(drawBuffersColorAtt0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLivingDamage() {
/* 5137 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 5139 */       setDrawBuffers(ProgramEntities.getDrawBuffers());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginBlockEntities() {
/* 5145 */     if (isRenderingWorld) {
/*      */       
/* 5147 */       checkGLError("beginBlockEntities");
/* 5148 */       useProgram(ProgramBlock);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextBlockEntity(TileEntity tileEntity) {
/* 5154 */     if (isRenderingWorld) {
/*      */       
/* 5156 */       checkGLError("nextBlockEntity");
/* 5157 */       useProgram(ProgramBlock);
/* 5158 */       setBlockEntityId(tileEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setBlockEntityId(TileEntity tileEntity) {
/* 5164 */     if (uniform_blockEntityId.isDefined()) {
/*      */       
/* 5166 */       int i = getBlockEntityId(tileEntity);
/* 5167 */       uniform_blockEntityId.setValue(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getBlockEntityId(TileEntity tileEntity) {
/* 5173 */     if (tileEntity == null)
/*      */     {
/* 5175 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 5179 */     Block block = tileEntity.getBlockType();
/*      */     
/* 5181 */     if (block == null)
/*      */     {
/* 5183 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 5187 */     int i = Block.getIdFromBlock(block);
/* 5188 */     int j = tileEntity.getBlockMetadata();
/* 5189 */     int k = BlockAliases.getBlockAliasId(i, j);
/*      */     
/* 5191 */     if (k >= 0)
/*      */     {
/* 5193 */       i = k;
/*      */     }
/*      */     
/* 5196 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endBlockEntities() {
/* 5203 */     if (isRenderingWorld) {
/*      */       
/* 5205 */       checkGLError("endBlockEntities");
/* 5206 */       setBlockEntityId(null);
/* 5207 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/* 5208 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLitParticles() {
/* 5214 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginParticles() {
/* 5219 */     useProgram(ProgramTextured);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endParticles() {
/* 5224 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void readCenterDepth() {
/* 5229 */     if (!isShadowPass && centerDepthSmoothEnabled) {
/*      */       
/* 5231 */       tempDirectFloatBuffer.clear();
/* 5232 */       GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
/* 5233 */       centerDepth = tempDirectFloatBuffer.get(0);
/* 5234 */       float f = (float)diffSystemTime * 0.01F;
/* 5235 */       float f1 = (float)Math.exp(Math.log(0.5D) * f / centerDepthSmoothHalflife);
/* 5236 */       centerDepthSmooth = centerDepthSmooth * f1 + centerDepth * (1.0F - f1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginWeather() {
/* 5242 */     if (!isShadowPass) {
/*      */       
/* 5244 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 5246 */         GlStateManager.setActiveTexture(33996);
/* 5247 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 5248 */         GlStateManager.setActiveTexture(33984);
/*      */       } 
/*      */       
/* 5251 */       GlStateManager.enableDepth();
/* 5252 */       GlStateManager.enableBlend();
/* 5253 */       GlStateManager.blendFunc(770, 771);
/* 5254 */       GlStateManager.enableAlpha();
/* 5255 */       useProgram(ProgramWeather);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endWeather() {
/* 5261 */     GlStateManager.disableBlend();
/* 5262 */     useProgram(ProgramTexturedLit);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preWater() {
/* 5267 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 5269 */       GlStateManager.setActiveTexture(33995);
/* 5270 */       checkGLError("pre copy depth");
/* 5271 */       GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 5272 */       checkGLError("copy depth");
/* 5273 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */     
/* 5276 */     ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginWater() {
/* 5281 */     if (isRenderingWorld)
/*      */     {
/* 5283 */       if (!isShadowPass) {
/*      */         
/* 5285 */         renderDeferred();
/* 5286 */         useProgram(ProgramWater);
/* 5287 */         GlStateManager.enableBlend();
/* 5288 */         GlStateManager.depthMask(true);
/*      */       }
/*      */       else {
/*      */         
/* 5292 */         GlStateManager.depthMask(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endWater() {
/* 5299 */     if (isRenderingWorld) {
/*      */       
/* 5301 */       if (isShadowPass);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5306 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void applyHandDepth() {
/* 5312 */     if (configHandDepthMul != 1.0D)
/*      */     {
/* 5314 */       GL11.glScaled(1.0D, 1.0D, configHandDepthMul);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginHand(boolean translucent) {
/* 5320 */     GL11.glMatrixMode(5888);
/* 5321 */     GL11.glPushMatrix();
/* 5322 */     GL11.glMatrixMode(5889);
/* 5323 */     GL11.glPushMatrix();
/* 5324 */     GL11.glMatrixMode(5888);
/*      */     
/* 5326 */     if (translucent) {
/*      */       
/* 5328 */       useProgram(ProgramHandWater);
/*      */     }
/*      */     else {
/*      */       
/* 5332 */       useProgram(ProgramHand);
/*      */     } 
/*      */     
/* 5335 */     checkGLError("beginHand");
/* 5336 */     checkFramebufferStatus("beginHand");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endHand() {
/* 5341 */     checkGLError("pre endHand");
/* 5342 */     checkFramebufferStatus("pre endHand");
/* 5343 */     GL11.glMatrixMode(5889);
/* 5344 */     GL11.glPopMatrix();
/* 5345 */     GL11.glMatrixMode(5888);
/* 5346 */     GL11.glPopMatrix();
/* 5347 */     GlStateManager.blendFunc(770, 771);
/* 5348 */     checkGLError("endHand");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginFPOverlay() {
/* 5353 */     GlStateManager.disableLighting();
/* 5354 */     GlStateManager.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endFPOverlay() {}
/*      */ 
/*      */   
/*      */   public static void glEnableWrapper(int cap) {
/* 5363 */     GL11.glEnable(cap);
/*      */     
/* 5365 */     if (cap == 3553) {
/*      */       
/* 5367 */       enableTexture2D();
/*      */     }
/* 5369 */     else if (cap == 2912) {
/*      */       
/* 5371 */       enableFog();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDisableWrapper(int cap) {
/* 5377 */     GL11.glDisable(cap);
/*      */     
/* 5379 */     if (cap == 3553) {
/*      */       
/* 5381 */       disableTexture2D();
/*      */     }
/* 5383 */     else if (cap == 2912) {
/*      */       
/* 5385 */       disableFog();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglEnableT2D(int cap) {
/* 5391 */     GL11.glEnable(cap);
/* 5392 */     enableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglDisableT2D(int cap) {
/* 5397 */     GL11.glDisable(cap);
/* 5398 */     disableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglEnableFog(int cap) {
/* 5403 */     GL11.glEnable(cap);
/* 5404 */     enableFog();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglDisableFog(int cap) {
/* 5409 */     GL11.glDisable(cap);
/* 5410 */     disableFog();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexture2D() {
/* 5415 */     if (isRenderingSky) {
/*      */       
/* 5417 */       useProgram(ProgramSkyTextured);
/*      */     }
/* 5419 */     else if (activeProgram == ProgramBasic) {
/*      */       
/* 5421 */       useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexture2D() {
/* 5427 */     if (isRenderingSky) {
/*      */       
/* 5429 */       useProgram(ProgramSkyBasic);
/*      */     }
/* 5431 */     else if (activeProgram == ProgramTextured || activeProgram == ProgramTexturedLit) {
/*      */       
/* 5433 */       useProgram(ProgramBasic);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLeash() {
/* 5439 */     programStackLeash.push(activeProgram);
/* 5440 */     useProgram(ProgramBasic);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLeash() {
/* 5445 */     useProgram(programStackLeash.pop());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/* 5450 */     fogEnabled = true;
/* 5451 */     setProgramUniform1i(uniform_fogMode, fogMode);
/* 5452 */     setProgramUniform1f(uniform_fogDensity, fogDensity);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableFog() {
/* 5457 */     fogEnabled = false;
/* 5458 */     setProgramUniform1i(uniform_fogMode, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogDensity(float value) {
/* 5463 */     fogDensity = value;
/*      */     
/* 5465 */     if (fogEnabled)
/*      */     {
/* 5467 */       setProgramUniform1f(uniform_fogDensity, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglFogi(int pname, int param) {
/* 5473 */     GL11.glFogi(pname, param);
/*      */     
/* 5475 */     if (pname == 2917) {
/*      */       
/* 5477 */       fogMode = param;
/*      */       
/* 5479 */       if (fogEnabled)
/*      */       {
/* 5481 */         setProgramUniform1i(uniform_fogMode, fogMode);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLightmap() {
/* 5488 */     lightmapEnabled = true;
/*      */     
/* 5490 */     if (activeProgram == ProgramTextured)
/*      */     {
/* 5492 */       useProgram(ProgramTexturedLit);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLightmap() {
/* 5498 */     lightmapEnabled = false;
/*      */     
/* 5500 */     if (activeProgram == ProgramTexturedLit)
/*      */     {
/* 5502 */       useProgram(ProgramTextured);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getEntityData() {
/* 5508 */     return entityData[entityDataIndex * 2];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getEntityData2() {
/* 5513 */     return entityData[entityDataIndex * 2 + 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData1(int data1) {
/* 5518 */     entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & 0xFFFF | data1 << 16;
/* 5519 */     return data1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData2(int data2) {
/* 5524 */     entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & 0xFFFF0000 | data2 & 0xFFFF;
/* 5525 */     return data2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0, int data1) {
/* 5530 */     entityDataIndex++;
/* 5531 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF | data1 << 16;
/* 5532 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0) {
/* 5537 */     entityDataIndex++;
/* 5538 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF;
/* 5539 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(Block block) {
/* 5544 */     entityDataIndex++;
/* 5545 */     int i = block.getRenderType();
/* 5546 */     entityData[entityDataIndex * 2] = Block.blockRegistry.getIDForObject(block) & 0xFFFF | i << 16;
/* 5547 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popEntity() {
/* 5552 */     entityData[entityDataIndex * 2] = 0;
/* 5553 */     entityData[entityDataIndex * 2 + 1] = 0;
/* 5554 */     entityDataIndex--;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void mcProfilerEndSection() {
/* 5559 */     mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getShaderPackName() {
/* 5564 */     return (shaderPack == null) ? null : ((shaderPack instanceof ShaderPackNone) ? null : shaderPack.getName());
/*      */   }
/*      */ 
/*      */   
/*      */   public static InputStream getShaderPackResourceStream(String path) {
/* 5569 */     return (shaderPack == null) ? null : shaderPack.getResourceAsStream(path);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextAntialiasingLevel() {
/* 5574 */     configAntialiasingLevel += 2;
/* 5575 */     configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
/*      */     
/* 5577 */     if (configAntialiasingLevel > 4)
/*      */     {
/* 5579 */       configAntialiasingLevel = 0;
/*      */     }
/*      */     
/* 5582 */     configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkShadersModInstalled() {
/*      */     try {
/* 5589 */       Class<?> clazz = Class.forName("shadersmod.transform.SMCClassTransformer");
/*      */     }
/* 5591 */     catch (Throwable var1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 5596 */     throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resourcesReloaded() {
/* 5601 */     loadShaderPackResources();
/*      */     
/* 5603 */     if (shaderPackLoaded) {
/*      */       
/* 5605 */       BlockAliases.resourcesReloaded();
/* 5606 */       ItemAliases.resourcesReloaded();
/* 5607 */       EntityAliases.resourcesReloaded();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackResources() {
/* 5613 */     shaderPackResources = new HashMap<>();
/*      */     
/* 5615 */     if (shaderPackLoaded) {
/*      */       
/* 5617 */       List<String> list = new ArrayList<>();
/* 5618 */       String s = "/shaders/lang/";
/* 5619 */       String s1 = "en_US";
/* 5620 */       String s2 = ".lang";
/* 5621 */       list.add(s + s1 + s2);
/*      */       
/* 5623 */       if (!(Config.getGameSettings()).language.equals(s1))
/*      */       {
/* 5625 */         list.add(s + (Config.getGameSettings()).language + s2);
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 5630 */         for (String s3 : list) {
/*      */           
/* 5632 */           InputStream inputstream = shaderPack.getResourceAsStream(s3);
/*      */           
/* 5634 */           if (inputstream != null) {
/*      */             
/* 5636 */             PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/* 5637 */             Lang.loadLocaleData(inputstream, (Map)propertiesOrdered);
/* 5638 */             inputstream.close();
/*      */             
/* 5640 */             for (Object o : propertiesOrdered.keySet())
/*      */             {
/* 5642 */               String s4 = (String)o;
/* 5643 */               String s5 = propertiesOrdered.getProperty(s4);
/* 5644 */               shaderPackResources.put(s4, s5);
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/* 5649 */       } catch (IOException ioexception) {
/*      */         
/* 5651 */         ioexception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String translate(String key, String def) {
/* 5658 */     String s = shaderPackResources.get(key);
/* 5659 */     return (s == null) ? def : s;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isProgramPath(String path) {
/* 5664 */     if (path == null)
/*      */     {
/* 5666 */       return false;
/*      */     }
/* 5668 */     if (path.length() <= 0)
/*      */     {
/* 5670 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5674 */     int i = path.lastIndexOf("/");
/*      */     
/* 5676 */     if (i >= 0)
/*      */     {
/* 5678 */       path = path.substring(i + 1);
/*      */     }
/*      */     
/* 5681 */     Program program = getProgram(path);
/* 5682 */     return (program != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Program getProgram(String name) {
/* 5688 */     return programs.getProgram(name);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setItemToRenderMain(ItemStack itemToRenderMain) {
/* 5693 */     itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setItemToRenderOff(ItemStack itemToRenderOff) {
/* 5698 */     itemToRenderOffTranslucent = isTranslucentBlock(itemToRenderOff);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isItemToRenderMainTranslucent() {
/* 5703 */     return itemToRenderMainTranslucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isItemToRenderOffTranslucent() {
/* 5708 */     return itemToRenderOffTranslucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBothHandsRendered() {
/* 5713 */     return (isHandRenderedMain && isHandRenderedOff);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isTranslucentBlock(ItemStack stack) {
/* 5718 */     if (stack == null)
/*      */     {
/* 5720 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5724 */     Item item = stack.getItem();
/*      */     
/* 5726 */     if (item == null)
/*      */     {
/* 5728 */       return false;
/*      */     }
/* 5730 */     if (!(item instanceof ItemBlock))
/*      */     {
/* 5732 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5736 */     ItemBlock itemblock = (ItemBlock)item;
/* 5737 */     Block block = itemblock.getBlock();
/*      */     
/* 5739 */     if (block == null)
/*      */     {
/* 5741 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 5745 */     EnumWorldBlockLayer enumworldblocklayer = block.getBlockLayer();
/* 5746 */     return (enumworldblocklayer == EnumWorldBlockLayer.TRANSLUCENT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSkipRenderHand() {
/* 5754 */     return skipRenderHandMain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderBothHands() {
/* 5759 */     return (!skipRenderHandMain && !skipRenderHandOff);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSkipRenderHands(boolean skipMain, boolean skipOff) {
/* 5764 */     skipRenderHandMain = skipMain;
/* 5765 */     skipRenderHandOff = skipOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setHandsRendered(boolean handMain, boolean handOff) {
/* 5770 */     isHandRenderedMain = handMain;
/* 5771 */     isHandRenderedOff = handOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isHandRenderedMain() {
/* 5776 */     return isHandRenderedMain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isHandRenderedOff() {
/* 5781 */     return isHandRenderedOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getShadowRenderDistance() {
/* 5786 */     return (shadowDistanceRenderMul < 0.0F) ? -1.0F : (shadowMapHalfPlane * shadowDistanceRenderMul);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setRenderingFirstPersonHand(boolean flag) {
/* 5791 */     isRenderingFirstPersonHand = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderingFirstPersonHand() {
/* 5796 */     return isRenderingFirstPersonHand;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginBeacon() {
/* 5801 */     if (isRenderingWorld)
/*      */     {
/* 5803 */       useProgram(ProgramBeaconBeam);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endBeacon() {
/* 5809 */     if (isRenderingWorld)
/*      */     {
/* 5811 */       useProgram(ProgramBlock);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static World getCurrentWorld() {
/* 5817 */     return currentWorld;
/*      */   }
/*      */ 
/*      */   
/*      */   public static BlockPos getCameraPosition() {
/* 5822 */     return new BlockPos(cameraPositionX, cameraPositionY, cameraPositionZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomUniforms() {
/* 5827 */     return (customUniforms != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean canRenderQuads() {
/* 5832 */     return hasGeometryShaders ? capabilities.GL_NV_geometry_shader4 : true;
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/* 5837 */     shaderPacksDir = new File((Minecraft.getMinecraft()).mcDataDir, "shaderpacks");
/* 5838 */     configFile = new File((Minecraft.getMinecraft()).mcDataDir, "optionsshaders.txt");
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\Shaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */