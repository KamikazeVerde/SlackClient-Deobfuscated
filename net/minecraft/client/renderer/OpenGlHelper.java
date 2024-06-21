/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.src.Config;
/*      */ import org.lwjgl.opengl.ARBCopyBuffer;
/*      */ import org.lwjgl.opengl.ARBFramebufferObject;
/*      */ import org.lwjgl.opengl.ARBMultitexture;
/*      */ import org.lwjgl.opengl.ARBShaderObjects;
/*      */ import org.lwjgl.opengl.ARBVertexBufferObject;
/*      */ import org.lwjgl.opengl.ARBVertexShader;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.EXTBlendFuncSeparate;
/*      */ import org.lwjgl.opengl.EXTFramebufferObject;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL13;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ import org.lwjgl.opengl.GL15;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GL31;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import oshi.SystemInfo;
/*      */ import oshi.hardware.Processor;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class OpenGlHelper
/*      */ {
/*      */   public static boolean nvidia;
/*      */   public static boolean field_181063_b;
/*      */   public static int GL_FRAMEBUFFER;
/*      */   public static int GL_RENDERBUFFER;
/*      */   public static int GL_COLOR_ATTACHMENT0;
/*      */   public static int GL_DEPTH_ATTACHMENT;
/*      */   public static int GL_FRAMEBUFFER_COMPLETE;
/*      */   public static int GL_FB_INCOMPLETE_ATTACHMENT;
/*      */   public static int GL_FB_INCOMPLETE_MISS_ATTACH;
/*      */   public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
/*      */   public static int GL_FB_INCOMPLETE_READ_BUFFER;
/*      */   private static int framebufferType;
/*      */   public static boolean framebufferSupported;
/*      */   private static boolean shadersAvailable;
/*      */   private static boolean arbShaders;
/*      */   public static int GL_LINK_STATUS;
/*      */   public static int GL_COMPILE_STATUS;
/*      */   public static int GL_VERTEX_SHADER;
/*      */   public static int GL_FRAGMENT_SHADER;
/*      */   private static boolean arbMultitexture;
/*      */   public static int defaultTexUnit;
/*      */   public static int lightmapTexUnit;
/*      */   public static int GL_TEXTURE2;
/*      */   private static boolean arbTextureEnvCombine;
/*      */   public static int GL_COMBINE;
/*      */   public static int GL_INTERPOLATE;
/*      */   public static int GL_PRIMARY_COLOR;
/*      */   public static int GL_CONSTANT;
/*      */   public static int GL_PREVIOUS;
/*      */   public static int GL_COMBINE_RGB;
/*      */   public static int GL_SOURCE0_RGB;
/*      */   public static int GL_SOURCE1_RGB;
/*      */   public static int GL_SOURCE2_RGB;
/*      */   public static int GL_OPERAND0_RGB;
/*      */   public static int GL_OPERAND1_RGB;
/*      */   public static int GL_OPERAND2_RGB;
/*      */   public static int GL_COMBINE_ALPHA;
/*      */   public static int GL_SOURCE0_ALPHA;
/*      */   public static int GL_SOURCE1_ALPHA;
/*      */   public static int GL_SOURCE2_ALPHA;
/*      */   public static int GL_OPERAND0_ALPHA;
/*      */   public static int GL_OPERAND1_ALPHA;
/*      */   public static int GL_OPERAND2_ALPHA;
/*      */   private static boolean openGL14;
/*      */   public static boolean extBlendFuncSeparate;
/*      */   public static boolean openGL21;
/*      */   public static boolean shadersSupported;
/*   90 */   private static String logText = "";
/*      */   private static String field_183030_aa;
/*      */   public static boolean vboSupported;
/*      */   public static boolean field_181062_Q;
/*      */   private static boolean arbVbo;
/*      */   public static int GL_ARRAY_BUFFER;
/*      */   public static int GL_STATIC_DRAW;
/*   97 */   public static float lastBrightnessX = 0.0F;
/*   98 */   public static float lastBrightnessY = 0.0F;
/*      */   
/*      */   public static boolean openGL31;
/*      */   
/*      */   public static boolean vboRegions;
/*      */   
/*      */   public static int GL_COPY_READ_BUFFER;
/*      */   
/*      */   public static int GL_COPY_WRITE_BUFFER;
/*      */   public static final int GL_QUADS = 7;
/*      */   public static final int GL_TRIANGLES = 4;
/*      */   
/*      */   public static void initializeTextures() {
/*  111 */     Config.initDisplay();
/*  112 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/*  113 */     arbMultitexture = (contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13);
/*  114 */     arbTextureEnvCombine = (contextcapabilities.GL_ARB_texture_env_combine && !contextcapabilities.OpenGL13);
/*  115 */     openGL31 = contextcapabilities.OpenGL31;
/*      */     
/*  117 */     if (openGL31) {
/*      */       
/*  119 */       GL_COPY_READ_BUFFER = 36662;
/*  120 */       GL_COPY_WRITE_BUFFER = 36663;
/*      */     }
/*      */     else {
/*      */       
/*  124 */       GL_COPY_READ_BUFFER = 36662;
/*  125 */       GL_COPY_WRITE_BUFFER = 36663;
/*      */     } 
/*      */     
/*  128 */     boolean flag = (openGL31 || contextcapabilities.GL_ARB_copy_buffer);
/*  129 */     boolean flag1 = contextcapabilities.OpenGL14;
/*  130 */     vboRegions = (flag && flag1);
/*      */     
/*  132 */     if (!vboRegions) {
/*      */       
/*  134 */       List<String> list = new ArrayList<>();
/*      */       
/*  136 */       if (!flag)
/*      */       {
/*  138 */         list.add("OpenGL 1.3, ARB_copy_buffer");
/*      */       }
/*      */       
/*  141 */       if (!flag1)
/*      */       {
/*  143 */         list.add("OpenGL 1.4");
/*      */       }
/*      */       
/*  146 */       String s = "VboRegions not supported, missing: " + Config.listToString(list);
/*  147 */       Config.dbg(s);
/*  148 */       logText += s + "\n";
/*      */     } 
/*      */     
/*  151 */     if (arbMultitexture) {
/*      */       
/*  153 */       logText += "Using ARB_multitexture.\n";
/*  154 */       defaultTexUnit = 33984;
/*  155 */       lightmapTexUnit = 33985;
/*  156 */       GL_TEXTURE2 = 33986;
/*      */     }
/*      */     else {
/*      */       
/*  160 */       logText += "Using GL 1.3 multitexturing.\n";
/*  161 */       defaultTexUnit = 33984;
/*  162 */       lightmapTexUnit = 33985;
/*  163 */       GL_TEXTURE2 = 33986;
/*      */     } 
/*      */     
/*  166 */     if (arbTextureEnvCombine) {
/*      */       
/*  168 */       logText += "Using ARB_texture_env_combine.\n";
/*  169 */       GL_COMBINE = 34160;
/*  170 */       GL_INTERPOLATE = 34165;
/*  171 */       GL_PRIMARY_COLOR = 34167;
/*  172 */       GL_CONSTANT = 34166;
/*  173 */       GL_PREVIOUS = 34168;
/*  174 */       GL_COMBINE_RGB = 34161;
/*  175 */       GL_SOURCE0_RGB = 34176;
/*  176 */       GL_SOURCE1_RGB = 34177;
/*  177 */       GL_SOURCE2_RGB = 34178;
/*  178 */       GL_OPERAND0_RGB = 34192;
/*  179 */       GL_OPERAND1_RGB = 34193;
/*  180 */       GL_OPERAND2_RGB = 34194;
/*  181 */       GL_COMBINE_ALPHA = 34162;
/*  182 */       GL_SOURCE0_ALPHA = 34184;
/*  183 */       GL_SOURCE1_ALPHA = 34185;
/*  184 */       GL_SOURCE2_ALPHA = 34186;
/*  185 */       GL_OPERAND0_ALPHA = 34200;
/*  186 */       GL_OPERAND1_ALPHA = 34201;
/*  187 */       GL_OPERAND2_ALPHA = 34202;
/*      */     }
/*      */     else {
/*      */       
/*  191 */       logText += "Using GL 1.3 texture combiners.\n";
/*  192 */       GL_COMBINE = 34160;
/*  193 */       GL_INTERPOLATE = 34165;
/*  194 */       GL_PRIMARY_COLOR = 34167;
/*  195 */       GL_CONSTANT = 34166;
/*  196 */       GL_PREVIOUS = 34168;
/*  197 */       GL_COMBINE_RGB = 34161;
/*  198 */       GL_SOURCE0_RGB = 34176;
/*  199 */       GL_SOURCE1_RGB = 34177;
/*  200 */       GL_SOURCE2_RGB = 34178;
/*  201 */       GL_OPERAND0_RGB = 34192;
/*  202 */       GL_OPERAND1_RGB = 34193;
/*  203 */       GL_OPERAND2_RGB = 34194;
/*  204 */       GL_COMBINE_ALPHA = 34162;
/*  205 */       GL_SOURCE0_ALPHA = 34184;
/*  206 */       GL_SOURCE1_ALPHA = 34185;
/*  207 */       GL_SOURCE2_ALPHA = 34186;
/*  208 */       GL_OPERAND0_ALPHA = 34200;
/*  209 */       GL_OPERAND1_ALPHA = 34201;
/*  210 */       GL_OPERAND2_ALPHA = 34202;
/*      */     } 
/*      */     
/*  213 */     extBlendFuncSeparate = (contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14);
/*  214 */     openGL14 = (contextcapabilities.OpenGL14 || contextcapabilities.GL_EXT_blend_func_separate);
/*  215 */     framebufferSupported = (openGL14 && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30));
/*      */     
/*  217 */     if (framebufferSupported) {
/*      */       
/*  219 */       logText += "Using framebuffer objects because ";
/*      */       
/*  221 */       if (contextcapabilities.OpenGL30)
/*      */       {
/*  223 */         logText += "OpenGL 3.0 is supported and separate blending is supported.\n";
/*  224 */         framebufferType = 0;
/*  225 */         GL_FRAMEBUFFER = 36160;
/*  226 */         GL_RENDERBUFFER = 36161;
/*  227 */         GL_COLOR_ATTACHMENT0 = 36064;
/*  228 */         GL_DEPTH_ATTACHMENT = 36096;
/*  229 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/*  230 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/*  231 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/*  232 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/*  233 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*      */       }
/*  235 */       else if (contextcapabilities.GL_ARB_framebuffer_object)
/*      */       {
/*  237 */         logText += "ARB_framebuffer_object is supported and separate blending is supported.\n";
/*  238 */         framebufferType = 1;
/*  239 */         GL_FRAMEBUFFER = 36160;
/*  240 */         GL_RENDERBUFFER = 36161;
/*  241 */         GL_COLOR_ATTACHMENT0 = 36064;
/*  242 */         GL_DEPTH_ATTACHMENT = 36096;
/*  243 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/*  244 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/*  245 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/*  246 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/*  247 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*      */       }
/*  249 */       else if (contextcapabilities.GL_EXT_framebuffer_object)
/*      */       {
/*  251 */         logText += "EXT_framebuffer_object is supported.\n";
/*  252 */         framebufferType = 2;
/*  253 */         GL_FRAMEBUFFER = 36160;
/*  254 */         GL_RENDERBUFFER = 36161;
/*  255 */         GL_COLOR_ATTACHMENT0 = 36064;
/*  256 */         GL_DEPTH_ATTACHMENT = 36096;
/*  257 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/*  258 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/*  259 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/*  260 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/*  261 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  266 */       logText += "Not using framebuffer objects because ";
/*  267 */       logText += "OpenGL 1.4 is " + (contextcapabilities.OpenGL14 ? "" : "not ") + "supported, ";
/*  268 */       logText += "EXT_blend_func_separate is " + (contextcapabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
/*  269 */       logText += "OpenGL 3.0 is " + (contextcapabilities.OpenGL30 ? "" : "not ") + "supported, ";
/*  270 */       logText += "ARB_framebuffer_object is " + (contextcapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
/*  271 */       logText += "EXT_framebuffer_object is " + (contextcapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
/*      */     } 
/*      */     
/*  274 */     openGL21 = contextcapabilities.OpenGL21;
/*  275 */     shadersAvailable = (openGL21 || (contextcapabilities.GL_ARB_vertex_shader && contextcapabilities.GL_ARB_fragment_shader && contextcapabilities.GL_ARB_shader_objects));
/*  276 */     logText += "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";
/*      */     
/*  278 */     if (shadersAvailable) {
/*      */       
/*  280 */       if (contextcapabilities.OpenGL21)
/*      */       {
/*  282 */         logText += "OpenGL 2.1 is supported.\n";
/*  283 */         arbShaders = false;
/*  284 */         GL_LINK_STATUS = 35714;
/*  285 */         GL_COMPILE_STATUS = 35713;
/*  286 */         GL_VERTEX_SHADER = 35633;
/*  287 */         GL_FRAGMENT_SHADER = 35632;
/*      */       }
/*      */       else
/*      */       {
/*  291 */         logText += "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
/*  292 */         arbShaders = true;
/*  293 */         GL_LINK_STATUS = 35714;
/*  294 */         GL_COMPILE_STATUS = 35713;
/*  295 */         GL_VERTEX_SHADER = 35633;
/*  296 */         GL_FRAGMENT_SHADER = 35632;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  301 */       logText += "OpenGL 2.1 is " + (contextcapabilities.OpenGL21 ? "" : "not ") + "supported, ";
/*  302 */       logText += "ARB_shader_objects is " + (contextcapabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
/*  303 */       logText += "ARB_vertex_shader is " + (contextcapabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
/*  304 */       logText += "ARB_fragment_shader is " + (contextcapabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
/*      */     } 
/*      */     
/*  307 */     shadersSupported = (framebufferSupported && shadersAvailable);
/*  308 */     String s1 = GL11.glGetString(7936).toLowerCase();
/*  309 */     nvidia = s1.contains("nvidia");
/*  310 */     arbVbo = (!contextcapabilities.OpenGL15 && contextcapabilities.GL_ARB_vertex_buffer_object);
/*  311 */     vboSupported = (contextcapabilities.OpenGL15 || arbVbo);
/*  312 */     logText += "VBOs are " + (vboSupported ? "" : "not ") + "available because ";
/*      */     
/*  314 */     if (vboSupported)
/*      */     {
/*  316 */       if (arbVbo) {
/*      */         
/*  318 */         logText += "ARB_vertex_buffer_object is supported.\n";
/*  319 */         GL_STATIC_DRAW = 35044;
/*  320 */         GL_ARRAY_BUFFER = 34962;
/*      */       }
/*      */       else {
/*      */         
/*  324 */         logText += "OpenGL 1.5 is supported.\n";
/*  325 */         GL_STATIC_DRAW = 35044;
/*  326 */         GL_ARRAY_BUFFER = 34962;
/*      */       } 
/*      */     }
/*      */     
/*  330 */     field_181063_b = s1.contains("ati");
/*      */     
/*  332 */     if (field_181063_b)
/*      */     {
/*  334 */       if (vboSupported) {
/*      */         
/*  336 */         field_181062_Q = true;
/*      */       }
/*      */       else {
/*      */         
/*  340 */         GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  346 */       Processor[] aprocessor = (new SystemInfo()).getHardware().getProcessors();
/*  347 */       field_183030_aa = String.format("%dx %s", new Object[] { Integer.valueOf(aprocessor.length), aprocessor[0] }).replaceAll("\\s+", " ");
/*      */     }
/*  349 */     catch (Throwable throwable) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areShadersSupported() {
/*  357 */     return shadersSupported;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getLogText() {
/*  362 */     return logText;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetProgrami(int program, int pname) {
/*  367 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(program, pname) : GL20.glGetProgrami(program, pname);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glAttachShader(int program, int shaderIn) {
/*  372 */     if (arbShaders) {
/*      */       
/*  374 */       ARBShaderObjects.glAttachObjectARB(program, shaderIn);
/*      */     }
/*      */     else {
/*      */       
/*  378 */       GL20.glAttachShader(program, shaderIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteShader(int p_153180_0_) {
/*  384 */     if (arbShaders) {
/*      */       
/*  386 */       ARBShaderObjects.glDeleteObjectARB(p_153180_0_);
/*      */     }
/*      */     else {
/*      */       
/*  390 */       GL20.glDeleteShader(p_153180_0_);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int glCreateShader(int type) {
/*  399 */     return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(type) : GL20.glCreateShader(type);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glShaderSource(int shaderIn, ByteBuffer string) {
/*  404 */     if (arbShaders) {
/*      */       
/*  406 */       ARBShaderObjects.glShaderSourceARB(shaderIn, string);
/*      */     }
/*      */     else {
/*      */       
/*  410 */       GL20.glShaderSource(shaderIn, string);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glCompileShader(int shaderIn) {
/*  416 */     if (arbShaders) {
/*      */       
/*  418 */       ARBShaderObjects.glCompileShaderARB(shaderIn);
/*      */     }
/*      */     else {
/*      */       
/*  422 */       GL20.glCompileShader(shaderIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetShaderi(int shaderIn, int pname) {
/*  428 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(shaderIn, pname) : GL20.glGetShaderi(shaderIn, pname);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String glGetShaderInfoLog(int shaderIn, int maxLength) {
/*  433 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(shaderIn, maxLength) : GL20.glGetShaderInfoLog(shaderIn, maxLength);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String glGetProgramInfoLog(int program, int maxLength) {
/*  438 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(program, maxLength) : GL20.glGetProgramInfoLog(program, maxLength);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUseProgram(int program) {
/*  443 */     if (arbShaders) {
/*      */       
/*  445 */       ARBShaderObjects.glUseProgramObjectARB(program);
/*      */     }
/*      */     else {
/*      */       
/*  449 */       GL20.glUseProgram(program);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glCreateProgram() {
/*  455 */     return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteProgram(int program) {
/*  460 */     if (arbShaders) {
/*      */       
/*  462 */       ARBShaderObjects.glDeleteObjectARB(program);
/*      */     }
/*      */     else {
/*      */       
/*  466 */       GL20.glDeleteProgram(program);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glLinkProgram(int program) {
/*  472 */     if (arbShaders) {
/*      */       
/*  474 */       ARBShaderObjects.glLinkProgramARB(program);
/*      */     }
/*      */     else {
/*      */       
/*  478 */       GL20.glLinkProgram(program);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetUniformLocation(int programObj, CharSequence name) {
/*  484 */     return arbShaders ? ARBShaderObjects.glGetUniformLocationARB(programObj, name) : GL20.glGetUniformLocation(programObj, name);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1(int location, IntBuffer values) {
/*  489 */     if (arbShaders) {
/*      */       
/*  491 */       ARBShaderObjects.glUniform1ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  495 */       GL20.glUniform1(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1i(int location, int v0) {
/*  501 */     if (arbShaders) {
/*      */       
/*  503 */       ARBShaderObjects.glUniform1iARB(location, v0);
/*      */     }
/*      */     else {
/*      */       
/*  507 */       GL20.glUniform1i(location, v0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform1(int location, FloatBuffer values) {
/*  513 */     if (arbShaders) {
/*      */       
/*  515 */       ARBShaderObjects.glUniform1ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  519 */       GL20.glUniform1(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform2(int location, IntBuffer values) {
/*  525 */     if (arbShaders) {
/*      */       
/*  527 */       ARBShaderObjects.glUniform2ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  531 */       GL20.glUniform2(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform2(int location, FloatBuffer values) {
/*  537 */     if (arbShaders) {
/*      */       
/*  539 */       ARBShaderObjects.glUniform2ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  543 */       GL20.glUniform2(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform3(int location, IntBuffer values) {
/*  549 */     if (arbShaders) {
/*      */       
/*  551 */       ARBShaderObjects.glUniform3ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  555 */       GL20.glUniform3(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform3(int location, FloatBuffer values) {
/*  561 */     if (arbShaders) {
/*      */       
/*  563 */       ARBShaderObjects.glUniform3ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  567 */       GL20.glUniform3(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform4(int location, IntBuffer values) {
/*  573 */     if (arbShaders) {
/*      */       
/*  575 */       ARBShaderObjects.glUniform4ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  579 */       GL20.glUniform4(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniform4(int location, FloatBuffer values) {
/*  585 */     if (arbShaders) {
/*      */       
/*  587 */       ARBShaderObjects.glUniform4ARB(location, values);
/*      */     }
/*      */     else {
/*      */       
/*  591 */       GL20.glUniform4(location, values);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices) {
/*  597 */     if (arbShaders) {
/*      */       
/*  599 */       ARBShaderObjects.glUniformMatrix2ARB(location, transpose, matrices);
/*      */     }
/*      */     else {
/*      */       
/*  603 */       GL20.glUniformMatrix2(location, transpose, matrices);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices) {
/*  609 */     if (arbShaders) {
/*      */       
/*  611 */       ARBShaderObjects.glUniformMatrix3ARB(location, transpose, matrices);
/*      */     }
/*      */     else {
/*      */       
/*  615 */       GL20.glUniformMatrix3(location, transpose, matrices);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
/*  621 */     if (arbShaders) {
/*      */       
/*  623 */       ARBShaderObjects.glUniformMatrix4ARB(location, transpose, matrices);
/*      */     }
/*      */     else {
/*      */       
/*  627 */       GL20.glUniformMatrix4(location, transpose, matrices);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGetAttribLocation(int p_153164_0_, CharSequence p_153164_1_) {
/*  633 */     return arbShaders ? ARBVertexShader.glGetAttribLocationARB(p_153164_0_, p_153164_1_) : GL20.glGetAttribLocation(p_153164_0_, p_153164_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int glGenBuffers() {
/*  638 */     return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBindBuffer(int target, int buffer) {
/*  643 */     if (arbVbo) {
/*      */       
/*  645 */       ARBVertexBufferObject.glBindBufferARB(target, buffer);
/*      */     }
/*      */     else {
/*      */       
/*  649 */       GL15.glBindBuffer(target, buffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBufferData(int target, ByteBuffer data, int usage) {
/*  655 */     if (arbVbo) {
/*      */       
/*  657 */       ARBVertexBufferObject.glBufferDataARB(target, data, usage);
/*      */     }
/*      */     else {
/*      */       
/*  661 */       GL15.glBufferData(target, data, usage);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDeleteBuffers(int buffer) {
/*  667 */     if (arbVbo) {
/*      */       
/*  669 */       ARBVertexBufferObject.glDeleteBuffersARB(buffer);
/*      */     }
/*      */     else {
/*      */       
/*  673 */       GL15.glDeleteBuffers(buffer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean useVbo() {
/*  679 */     return Config.isMultiTexture() ? false : ((Config.isRenderRegions() && !vboRegions) ? false : ((vboSupported && (Minecraft.getMinecraft()).gameSettings.useVbo)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBindFramebuffer(int target, int framebufferIn) {
/*  684 */     if (framebufferSupported)
/*      */     {
/*  686 */       switch (framebufferType) {
/*      */         
/*      */         case 0:
/*  689 */           GL30.glBindFramebuffer(target, framebufferIn);
/*      */           break;
/*      */         
/*      */         case 1:
/*  693 */           ARBFramebufferObject.glBindFramebuffer(target, framebufferIn);
/*      */           break;
/*      */         
/*      */         case 2:
/*  697 */           EXTFramebufferObject.glBindFramebufferEXT(target, framebufferIn);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glBindRenderbuffer(int target, int renderbuffer) {
/*  704 */     if (framebufferSupported)
/*      */     {
/*  706 */       switch (framebufferType) {
/*      */         
/*      */         case 0:
/*  709 */           GL30.glBindRenderbuffer(target, renderbuffer);
/*      */           break;
/*      */         
/*      */         case 1:
/*  713 */           ARBFramebufferObject.glBindRenderbuffer(target, renderbuffer);
/*      */           break;
/*      */         
/*      */         case 2:
/*  717 */           EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glDeleteRenderbuffers(int renderbuffer) {
/*  724 */     if (framebufferSupported)
/*      */     {
/*  726 */       switch (framebufferType) {
/*      */         
/*      */         case 0:
/*  729 */           GL30.glDeleteRenderbuffers(renderbuffer);
/*      */           break;
/*      */         
/*      */         case 1:
/*  733 */           ARBFramebufferObject.glDeleteRenderbuffers(renderbuffer);
/*      */           break;
/*      */         
/*      */         case 2:
/*  737 */           EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffer);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glDeleteFramebuffers(int framebufferIn) {
/*  744 */     if (framebufferSupported)
/*      */     {
/*  746 */       switch (framebufferType) {
/*      */         
/*      */         case 0:
/*  749 */           GL30.glDeleteFramebuffers(framebufferIn);
/*      */           break;
/*      */         
/*      */         case 1:
/*  753 */           ARBFramebufferObject.glDeleteFramebuffers(framebufferIn);
/*      */           break;
/*      */         
/*      */         case 2:
/*  757 */           EXTFramebufferObject.glDeleteFramebuffersEXT(framebufferIn);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int glGenFramebuffers() {
/*  767 */     if (!framebufferSupported)
/*      */     {
/*  769 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  773 */     switch (framebufferType) {
/*      */       
/*      */       case 0:
/*  776 */         return GL30.glGenFramebuffers();
/*      */       
/*      */       case 1:
/*  779 */         return ARBFramebufferObject.glGenFramebuffers();
/*      */       
/*      */       case 2:
/*  782 */         return EXTFramebufferObject.glGenFramebuffersEXT();
/*      */     } 
/*      */     
/*  785 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int glGenRenderbuffers() {
/*  792 */     if (!framebufferSupported)
/*      */     {
/*  794 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  798 */     switch (framebufferType) {
/*      */       
/*      */       case 0:
/*  801 */         return GL30.glGenRenderbuffers();
/*      */       
/*      */       case 1:
/*  804 */         return ARBFramebufferObject.glGenRenderbuffers();
/*      */       
/*      */       case 2:
/*  807 */         return EXTFramebufferObject.glGenRenderbuffersEXT();
/*      */     } 
/*      */     
/*  810 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
/*  817 */     if (framebufferSupported)
/*      */     {
/*  819 */       switch (framebufferType) {
/*      */         
/*      */         case 0:
/*  822 */           GL30.glRenderbufferStorage(target, internalFormat, width, height);
/*      */           break;
/*      */         
/*      */         case 1:
/*  826 */           ARBFramebufferObject.glRenderbufferStorage(target, internalFormat, width, height);
/*      */           break;
/*      */         
/*      */         case 2:
/*  830 */           EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer) {
/*  837 */     if (framebufferSupported)
/*      */     {
/*  839 */       switch (framebufferType) {
/*      */         
/*      */         case 0:
/*  842 */           GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*      */           break;
/*      */         
/*      */         case 1:
/*  846 */           ARBFramebufferObject.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*      */           break;
/*      */         
/*      */         case 2:
/*  850 */           EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderBufferTarget, renderBuffer);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public static int glCheckFramebufferStatus(int target) {
/*  857 */     if (!framebufferSupported)
/*      */     {
/*  859 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  863 */     switch (framebufferType) {
/*      */       
/*      */       case 0:
/*  866 */         return GL30.glCheckFramebufferStatus(target);
/*      */       
/*      */       case 1:
/*  869 */         return ARBFramebufferObject.glCheckFramebufferStatus(target);
/*      */       
/*      */       case 2:
/*  872 */         return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
/*      */     } 
/*      */     
/*  875 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
/*  882 */     if (framebufferSupported)
/*      */     {
/*  884 */       switch (framebufferType) {
/*      */         
/*      */         case 0:
/*  887 */           GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*      */           break;
/*      */         
/*      */         case 1:
/*  891 */           ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*      */           break;
/*      */         
/*      */         case 2:
/*  895 */           EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setActiveTexture(int texture) {
/*  905 */     if (arbMultitexture) {
/*      */       
/*  907 */       ARBMultitexture.glActiveTextureARB(texture);
/*      */     }
/*      */     else {
/*      */       
/*  911 */       GL13.glActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setClientActiveTexture(int texture) {
/*  920 */     if (arbMultitexture) {
/*      */       
/*  922 */       ARBMultitexture.glClientActiveTextureARB(texture);
/*      */     }
/*      */     else {
/*      */       
/*  926 */       GL13.glClientActiveTexture(texture);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setLightmapTextureCoords(int target, float p_77475_1_, float p_77475_2_) {
/*  935 */     if (arbMultitexture) {
/*      */       
/*  937 */       ARBMultitexture.glMultiTexCoord2fARB(target, p_77475_1_, p_77475_2_);
/*      */     }
/*      */     else {
/*      */       
/*  941 */       GL13.glMultiTexCoord2f(target, p_77475_1_, p_77475_2_);
/*      */     } 
/*      */     
/*  944 */     if (target == lightmapTexUnit) {
/*      */       
/*  946 */       lastBrightnessX = p_77475_1_;
/*  947 */       lastBrightnessY = p_77475_2_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBlendFunc(int sFactorRGB, int dFactorRGB, int sfactorAlpha, int dfactorAlpha) {
/*  953 */     if (openGL14) {
/*      */       
/*  955 */       if (extBlendFuncSeparate)
/*      */       {
/*  957 */         EXTBlendFuncSeparate.glBlendFuncSeparateEXT(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*      */       }
/*      */       else
/*      */       {
/*  961 */         GL14.glBlendFuncSeparate(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  966 */       GL11.glBlendFunc(sFactorRGB, dFactorRGB);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFramebufferEnabled() {
/*  972 */     return Config.isFastRender() ? false : (Config.isAntialiasing() ? false : ((framebufferSupported && (Minecraft.getMinecraft()).gameSettings.fboEnable)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBufferData(int p_glBufferData_0_, long p_glBufferData_1_, int p_glBufferData_3_) {
/*  977 */     if (arbVbo) {
/*      */       
/*  979 */       ARBVertexBufferObject.glBufferDataARB(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_3_);
/*      */     }
/*      */     else {
/*      */       
/*  983 */       GL15.glBufferData(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_3_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glBufferSubData(int p_glBufferSubData_0_, long p_glBufferSubData_1_, ByteBuffer p_glBufferSubData_3_) {
/*  989 */     if (arbVbo) {
/*      */       
/*  991 */       ARBVertexBufferObject.glBufferSubDataARB(p_glBufferSubData_0_, p_glBufferSubData_1_, p_glBufferSubData_3_);
/*      */     }
/*      */     else {
/*      */       
/*  995 */       GL15.glBufferSubData(p_glBufferSubData_0_, p_glBufferSubData_1_, p_glBufferSubData_3_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glCopyBufferSubData(int p_glCopyBufferSubData_0_, int p_glCopyBufferSubData_1_, long p_glCopyBufferSubData_2_, long p_glCopyBufferSubData_4_, long p_glCopyBufferSubData_6_) {
/* 1001 */     if (openGL31) {
/*      */       
/* 1003 */       GL31.glCopyBufferSubData(p_glCopyBufferSubData_0_, p_glCopyBufferSubData_1_, p_glCopyBufferSubData_2_, p_glCopyBufferSubData_4_, p_glCopyBufferSubData_6_);
/*      */     }
/*      */     else {
/*      */       
/* 1007 */       ARBCopyBuffer.glCopyBufferSubData(p_glCopyBufferSubData_0_, p_glCopyBufferSubData_1_, p_glCopyBufferSubData_2_, p_glCopyBufferSubData_4_, p_glCopyBufferSubData_6_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String func_183029_j() {
/* 1013 */     return (field_183030_aa == null) ? "<unknown>" : field_183030_aa;
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\OpenGlHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */