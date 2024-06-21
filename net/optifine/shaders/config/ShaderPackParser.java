/*      */ package net.optifine.shaders.config;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.CharArrayWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.minecraft.src.Config;
/*      */ import net.optifine.expr.ExpressionFloatArrayCached;
/*      */ import net.optifine.expr.ExpressionFloatCached;
/*      */ import net.optifine.expr.ExpressionParser;
/*      */ import net.optifine.expr.ExpressionType;
/*      */ import net.optifine.expr.IExpression;
/*      */ import net.optifine.expr.IExpressionBool;
/*      */ import net.optifine.expr.IExpressionFloat;
/*      */ import net.optifine.expr.IExpressionFloatArray;
/*      */ import net.optifine.expr.IExpressionResolver;
/*      */ import net.optifine.expr.ParseException;
/*      */ import net.optifine.render.GlAlphaState;
/*      */ import net.optifine.render.GlBlendState;
/*      */ import net.optifine.shaders.IShaderPack;
/*      */ import net.optifine.shaders.Program;
/*      */ import net.optifine.shaders.SMCLog;
/*      */ import net.optifine.shaders.ShaderUtils;
/*      */ import net.optifine.shaders.Shaders;
/*      */ import net.optifine.shaders.uniform.CustomUniform;
/*      */ import net.optifine.shaders.uniform.CustomUniforms;
/*      */ import net.optifine.shaders.uniform.ShaderExpressionResolver;
/*      */ import net.optifine.shaders.uniform.UniformType;
/*      */ import net.optifine.util.StrUtils;
/*      */ 
/*      */ public class ShaderPackParser {
/*   51 */   private static final Pattern PATTERN_VERSION = Pattern.compile("^\\s*#version\\s+.*$");
/*   52 */   private static final Pattern PATTERN_INCLUDE = Pattern.compile("^\\s*#include\\s+\"([A-Za-z0-9_/\\.]+)\".*$");
/*   53 */   private static final Set<String> setConstNames = makeSetConstNames();
/*   54 */   private static final Map<String, Integer> mapAlphaFuncs = makeMapAlphaFuncs();
/*   55 */   private static final Map<String, Integer> mapBlendFactors = makeMapBlendFactors();
/*      */ 
/*      */   
/*      */   public static ShaderOption[] parseShaderPackOptions(IShaderPack shaderPack, String[] programNames, List<Integer> listDimensions) {
/*   59 */     if (shaderPack == null)
/*      */     {
/*   61 */       return new ShaderOption[0];
/*      */     }
/*      */ 
/*      */     
/*   65 */     Map<String, ShaderOption> map = new HashMap<>();
/*   66 */     collectShaderOptions(shaderPack, "/shaders", programNames, map);
/*   67 */     Iterator<Integer> iterator = listDimensions.iterator();
/*      */     
/*   69 */     while (iterator.hasNext()) {
/*      */       
/*   71 */       int i = ((Integer)iterator.next()).intValue();
/*   72 */       String s = "/shaders/world" + i;
/*   73 */       collectShaderOptions(shaderPack, s, programNames, map);
/*      */     } 
/*      */     
/*   76 */     Collection<ShaderOption> collection = map.values();
/*   77 */     ShaderOption[] ashaderoption = collection.<ShaderOption>toArray(new ShaderOption[collection.size()]);
/*   78 */     Comparator<ShaderOption> comparator = new Comparator<ShaderOption>()
/*      */       {
/*      */         public int compare(ShaderOption o1, ShaderOption o2)
/*      */         {
/*   82 */           return o1.getName().compareToIgnoreCase(o2.getName());
/*      */         }
/*      */       };
/*   85 */     Arrays.sort(ashaderoption, comparator);
/*   86 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void collectShaderOptions(IShaderPack shaderPack, String dir, String[] programNames, Map<String, ShaderOption> mapOptions) {
/*   92 */     for (int i = 0; i < programNames.length; i++) {
/*      */       
/*   94 */       String s = programNames[i];
/*      */       
/*   96 */       if (!s.equals("")) {
/*      */         
/*   98 */         String s1 = dir + "/" + s + ".vsh";
/*   99 */         String s2 = dir + "/" + s + ".fsh";
/*  100 */         collectShaderOptions(shaderPack, s1, mapOptions);
/*  101 */         collectShaderOptions(shaderPack, s2, mapOptions);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void collectShaderOptions(IShaderPack sp, String path, Map<String, ShaderOption> mapOptions) {
/*  108 */     String[] astring = getLines(sp, path);
/*      */     
/*  110 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  112 */       String s = astring[i];
/*  113 */       ShaderOption shaderoption = getShaderOption(s, path);
/*      */       
/*  115 */       if (shaderoption != null && !shaderoption.getName().startsWith(ShaderMacros.getPrefixMacro()) && (!shaderoption.checkUsed() || isOptionUsed(shaderoption, astring))) {
/*      */         
/*  117 */         String s1 = shaderoption.getName();
/*  118 */         ShaderOption shaderoption1 = mapOptions.get(s1);
/*      */         
/*  120 */         if (shaderoption1 != null) {
/*      */           
/*  122 */           if (!Config.equals(shaderoption1.getValueDefault(), shaderoption.getValueDefault())) {
/*      */             
/*  124 */             Config.warn("Ambiguous shader option: " + shaderoption.getName());
/*  125 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption1.getPaths()) + ": " + shaderoption1.getValueDefault());
/*  126 */             Config.warn(" - in " + Config.arrayToString((Object[])shaderoption.getPaths()) + ": " + shaderoption.getValueDefault());
/*  127 */             shaderoption1.setEnabled(false);
/*      */           } 
/*      */           
/*  130 */           if (shaderoption1.getDescription() == null || shaderoption1.getDescription().length() <= 0)
/*      */           {
/*  132 */             shaderoption1.setDescription(shaderoption.getDescription());
/*      */           }
/*      */           
/*  135 */           shaderoption1.addPaths(shaderoption.getPaths());
/*      */         }
/*      */         else {
/*      */           
/*  139 */           mapOptions.put(s1, shaderoption);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isOptionUsed(ShaderOption so, String[] lines) {
/*  147 */     for (int i = 0; i < lines.length; i++) {
/*      */       
/*  149 */       String s = lines[i];
/*      */       
/*  151 */       if (so.isUsedInLine(s))
/*      */       {
/*  153 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  157 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] getLines(IShaderPack sp, String path) {
/*      */     try {
/*  164 */       List<String> list = new ArrayList<>();
/*  165 */       String s = loadFile(path, sp, 0, list, 0);
/*      */       
/*  167 */       if (s == null)
/*      */       {
/*  169 */         return new String[0];
/*      */       }
/*      */ 
/*      */       
/*  173 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(s.getBytes());
/*  174 */       String[] astring = Config.readLines(bytearrayinputstream);
/*  175 */       return astring;
/*      */     
/*      */     }
/*  178 */     catch (IOException ioexception) {
/*      */       
/*  180 */       Config.dbg(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*  181 */       return new String[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderOption getShaderOption(String line, String path) {
/*  187 */     ShaderOption shaderoption = null;
/*      */     
/*  189 */     if (shaderoption == null)
/*      */     {
/*  191 */       shaderoption = ShaderOptionSwitch.parseOption(line, path);
/*      */     }
/*      */     
/*  194 */     if (shaderoption == null)
/*      */     {
/*  196 */       shaderoption = ShaderOptionVariable.parseOption(line, path);
/*      */     }
/*      */     
/*  199 */     if (shaderoption != null)
/*      */     {
/*  201 */       return shaderoption;
/*      */     }
/*      */ 
/*      */     
/*  205 */     if (shaderoption == null)
/*      */     {
/*  207 */       shaderoption = ShaderOptionSwitchConst.parseOption(line, path);
/*      */     }
/*      */     
/*  210 */     if (shaderoption == null)
/*      */     {
/*  212 */       shaderoption = ShaderOptionVariableConst.parseOption(line, path);
/*      */     }
/*      */     
/*  215 */     return (shaderoption != null && setConstNames.contains(shaderoption.getName())) ? shaderoption : null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<String> makeSetConstNames() {
/*  221 */     Set<String> set = new HashSet<>();
/*  222 */     set.add("shadowMapResolution");
/*  223 */     set.add("shadowMapFov");
/*  224 */     set.add("shadowDistance");
/*  225 */     set.add("shadowDistanceRenderMul");
/*  226 */     set.add("shadowIntervalSize");
/*  227 */     set.add("generateShadowMipmap");
/*  228 */     set.add("generateShadowColorMipmap");
/*  229 */     set.add("shadowHardwareFiltering");
/*  230 */     set.add("shadowHardwareFiltering0");
/*  231 */     set.add("shadowHardwareFiltering1");
/*  232 */     set.add("shadowtex0Mipmap");
/*  233 */     set.add("shadowtexMipmap");
/*  234 */     set.add("shadowtex1Mipmap");
/*  235 */     set.add("shadowcolor0Mipmap");
/*  236 */     set.add("shadowColor0Mipmap");
/*  237 */     set.add("shadowcolor1Mipmap");
/*  238 */     set.add("shadowColor1Mipmap");
/*  239 */     set.add("shadowtex0Nearest");
/*  240 */     set.add("shadowtexNearest");
/*  241 */     set.add("shadow0MinMagNearest");
/*  242 */     set.add("shadowtex1Nearest");
/*  243 */     set.add("shadow1MinMagNearest");
/*  244 */     set.add("shadowcolor0Nearest");
/*  245 */     set.add("shadowColor0Nearest");
/*  246 */     set.add("shadowColor0MinMagNearest");
/*  247 */     set.add("shadowcolor1Nearest");
/*  248 */     set.add("shadowColor1Nearest");
/*  249 */     set.add("shadowColor1MinMagNearest");
/*  250 */     set.add("wetnessHalflife");
/*  251 */     set.add("drynessHalflife");
/*  252 */     set.add("eyeBrightnessHalflife");
/*  253 */     set.add("centerDepthHalflife");
/*  254 */     set.add("sunPathRotation");
/*  255 */     set.add("ambientOcclusionLevel");
/*  256 */     set.add("superSamplingLevel");
/*  257 */     set.add("noiseTextureResolution");
/*  258 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderProfile[] parseProfiles(Properties props, ShaderOption[] shaderOptions) {
/*  263 */     String s = "profile.";
/*  264 */     List<ShaderProfile> list = new ArrayList<>();
/*      */     
/*  266 */     for (Object e : props.keySet()) {
/*      */       
/*  268 */       String s1 = (String)e;
/*  269 */       if (s1.startsWith(s)) {
/*      */         
/*  271 */         String s2 = s1.substring(s.length());
/*  272 */         props.getProperty(s1);
/*  273 */         Set<String> set = new HashSet<>();
/*  274 */         ShaderProfile shaderprofile = parseProfile(s2, props, set, shaderOptions);
/*      */         
/*  276 */         if (shaderprofile != null)
/*      */         {
/*  278 */           list.add(shaderprofile);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  283 */     if (list.size() <= 0)
/*      */     {
/*  285 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  289 */     ShaderProfile[] ashaderprofile = list.<ShaderProfile>toArray(new ShaderProfile[list.size()]);
/*  290 */     return ashaderprofile;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, IExpressionBool> parseProgramConditions(Properties props, ShaderOption[] shaderOptions) {
/*  296 */     String s = "program.";
/*  297 */     Pattern pattern = Pattern.compile("program\\.([^.]+)\\.enabled");
/*  298 */     Map<String, IExpressionBool> map = new HashMap<>();
/*      */     
/*  300 */     for (Object e : props.keySet()) {
/*      */       
/*  302 */       String s1 = (String)e;
/*  303 */       Matcher matcher = pattern.matcher(s1);
/*      */       
/*  305 */       if (matcher.matches()) {
/*      */         
/*  307 */         String s2 = matcher.group(1);
/*  308 */         String s3 = props.getProperty(s1).trim();
/*  309 */         IExpressionBool iexpressionbool = parseOptionExpression(s3, shaderOptions);
/*      */         
/*  311 */         if (iexpressionbool == null) {
/*      */           
/*  313 */           SMCLog.severe("Error parsing program condition: " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/*  317 */         map.put(s2, iexpressionbool);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  322 */     return map;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static IExpressionBool parseOptionExpression(String val, ShaderOption[] shaderOptions) {
/*      */     try {
/*  329 */       ShaderOptionResolver shaderoptionresolver = new ShaderOptionResolver(shaderOptions);
/*  330 */       ExpressionParser expressionparser = new ExpressionParser(shaderoptionresolver);
/*  331 */       IExpressionBool iexpressionbool = expressionparser.parseBool(val);
/*  332 */       return iexpressionbool;
/*      */     }
/*  334 */     catch (ParseException parseexception) {
/*      */       
/*  336 */       SMCLog.warning(parseexception.getClass().getName() + ": " + parseexception.getMessage());
/*  337 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Set<String> parseOptionSliders(Properties props, ShaderOption[] shaderOptions) {
/*  343 */     Set<String> set = new HashSet<>();
/*  344 */     String s = props.getProperty("sliders");
/*      */     
/*  346 */     if (s == null)
/*      */     {
/*  348 */       return set;
/*      */     }
/*      */ 
/*      */     
/*  352 */     String[] astring = Config.tokenize(s, " ");
/*      */     
/*  354 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  356 */       String s1 = astring[i];
/*  357 */       ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*      */       
/*  359 */       if (shaderoption == null) {
/*      */         
/*  361 */         Config.warn("Invalid shader option: " + s1);
/*      */       }
/*      */       else {
/*      */         
/*  365 */         set.add(s1);
/*      */       } 
/*      */     } 
/*      */     
/*  369 */     return set;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderProfile parseProfile(String name, Properties props, Set<String> parsedProfiles, ShaderOption[] shaderOptions) {
/*  375 */     String s = "profile.";
/*  376 */     String s1 = s + name;
/*      */     
/*  378 */     if (parsedProfiles.contains(s1)) {
/*      */       
/*  380 */       Config.warn("[Shaders] Profile already parsed: " + name);
/*  381 */       return null;
/*      */     } 
/*      */ 
/*      */     
/*  385 */     parsedProfiles.add(name);
/*  386 */     ShaderProfile shaderprofile = new ShaderProfile(name);
/*  387 */     String s2 = props.getProperty(s1);
/*  388 */     String[] astring = Config.tokenize(s2, " ");
/*      */     
/*  390 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  392 */       String s3 = astring[i];
/*      */       
/*  394 */       if (s3.startsWith(s)) {
/*      */         
/*  396 */         String s4 = s3.substring(s.length());
/*  397 */         ShaderProfile shaderprofile1 = parseProfile(s4, props, parsedProfiles, shaderOptions);
/*      */         
/*  399 */         if (shaderprofile != null)
/*      */         {
/*  401 */           shaderprofile.addOptionValues(shaderprofile1);
/*  402 */           shaderprofile.addDisabledPrograms(shaderprofile1.getDisabledPrograms());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  407 */         String[] astring1 = Config.tokenize(s3, ":=");
/*      */         
/*  409 */         if (astring1.length == 1) {
/*      */           
/*  411 */           String s7 = astring1[0];
/*  412 */           boolean flag = true;
/*      */           
/*  414 */           if (s7.startsWith("!")) {
/*      */             
/*  416 */             flag = false;
/*  417 */             s7 = s7.substring(1);
/*      */           } 
/*      */           
/*  420 */           String s5 = "program.";
/*      */           
/*  422 */           if (s7.startsWith(s5)) {
/*      */             
/*  424 */             String s6 = s7.substring(s5.length());
/*      */             
/*  426 */             if (!Shaders.isProgramPath(s6))
/*      */             {
/*  428 */               Config.warn("Invalid program: " + s6 + " in profile: " + shaderprofile.getName());
/*      */             }
/*  430 */             else if (flag)
/*      */             {
/*  432 */               shaderprofile.removeDisabledProgram(s6);
/*      */             }
/*      */             else
/*      */             {
/*  436 */               shaderprofile.addDisabledProgram(s6);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/*  441 */             ShaderOption shaderoption1 = ShaderUtils.getShaderOption(s7, shaderOptions);
/*      */             
/*  443 */             if (!(shaderoption1 instanceof ShaderOptionSwitch))
/*      */             {
/*  445 */               Config.warn("[Shaders] Invalid option: " + s7);
/*      */             }
/*      */             else
/*      */             {
/*  449 */               shaderprofile.addOptionValue(s7, String.valueOf(flag));
/*  450 */               shaderoption1.setVisible(true);
/*      */             }
/*      */           
/*      */           } 
/*  454 */         } else if (astring1.length != 2) {
/*      */           
/*  456 */           Config.warn("[Shaders] Invalid option value: " + s3);
/*      */         }
/*      */         else {
/*      */           
/*  460 */           String s8 = astring1[0];
/*  461 */           String s9 = astring1[1];
/*  462 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s8, shaderOptions);
/*      */           
/*  464 */           if (shaderoption == null) {
/*      */             
/*  466 */             Config.warn("[Shaders] Invalid option: " + s3);
/*      */           }
/*  468 */           else if (!shaderoption.isValidValue(s9)) {
/*      */             
/*  470 */             Config.warn("[Shaders] Invalid value: " + s3);
/*      */           }
/*      */           else {
/*      */             
/*  474 */             shaderoption.setVisible(true);
/*  475 */             shaderprofile.addOptionValue(s8, s9);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  481 */     return shaderprofile;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Map<String, ScreenShaderOptions> parseGuiScreens(Properties props, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/*  487 */     Map<String, ScreenShaderOptions> map = new HashMap<>();
/*  488 */     parseGuiScreen("screen", props, map, shaderProfiles, shaderOptions);
/*  489 */     return map.isEmpty() ? null : map;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean parseGuiScreen(String key, Properties props, Map<String, ScreenShaderOptions> map, ShaderProfile[] shaderProfiles, ShaderOption[] shaderOptions) {
/*  494 */     String s = props.getProperty(key);
/*      */     
/*  496 */     if (s == null)
/*      */     {
/*  498 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  502 */     List<ShaderOption> list = new ArrayList<>();
/*  503 */     Set<String> set = new HashSet<>();
/*  504 */     String[] astring = Config.tokenize(s, " ");
/*      */     
/*  506 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/*  508 */       String s1 = astring[i];
/*      */       
/*  510 */       if (s1.equals("<empty>")) {
/*      */         
/*  512 */         list.add(null);
/*      */       }
/*  514 */       else if (set.contains(s1)) {
/*      */         
/*  516 */         Config.warn("[Shaders] Duplicate option: " + s1 + ", key: " + key);
/*      */       }
/*      */       else {
/*      */         
/*  520 */         set.add(s1);
/*      */         
/*  522 */         if (s1.equals("<profile>")) {
/*      */           
/*  524 */           if (shaderProfiles == null)
/*      */           {
/*  526 */             Config.warn("[Shaders] Option profile can not be used, no profiles defined: " + s1 + ", key: " + key);
/*      */           }
/*      */           else
/*      */           {
/*  530 */             ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderProfiles, shaderOptions);
/*  531 */             list.add(shaderoptionprofile);
/*      */           }
/*      */         
/*  534 */         } else if (s1.equals("*")) {
/*      */           
/*  536 */           ShaderOption shaderoption1 = new ShaderOptionRest("<rest>");
/*  537 */           list.add(shaderoption1);
/*      */         }
/*  539 */         else if (s1.startsWith("[") && s1.endsWith("]")) {
/*      */           
/*  541 */           String s3 = StrUtils.removePrefixSuffix(s1, "[", "]");
/*      */           
/*  543 */           if (!s3.matches("^[a-zA-Z0-9_]+$"))
/*      */           {
/*  545 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/*      */           }
/*  547 */           else if (!parseGuiScreen("screen." + s3, props, map, shaderProfiles, shaderOptions))
/*      */           {
/*  549 */             Config.warn("[Shaders] Invalid screen: " + s1 + ", key: " + key);
/*      */           }
/*      */           else
/*      */           {
/*  553 */             ShaderOptionScreen shaderoptionscreen = new ShaderOptionScreen(s3);
/*  554 */             list.add(shaderoptionscreen);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  559 */           ShaderOption shaderoption = ShaderUtils.getShaderOption(s1, shaderOptions);
/*      */           
/*  561 */           if (shaderoption == null) {
/*      */             
/*  563 */             Config.warn("[Shaders] Invalid option: " + s1 + ", key: " + key);
/*  564 */             list.add(null);
/*      */           }
/*      */           else {
/*      */             
/*  568 */             shaderoption.setVisible(true);
/*  569 */             list.add(shaderoption);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  575 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/*  576 */     String s2 = props.getProperty(key + ".columns");
/*  577 */     int j = Config.parseInt(s2, 2);
/*  578 */     ScreenShaderOptions screenshaderoptions = new ScreenShaderOptions(key, ashaderoption, j);
/*  579 */     map.put(key, screenshaderoptions);
/*  580 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BufferedReader resolveIncludes(BufferedReader reader, String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/*  586 */     String s = "/";
/*  587 */     int i = filePath.lastIndexOf("/");
/*      */     
/*  589 */     if (i >= 0)
/*      */     {
/*  591 */       s = filePath.substring(0, i);
/*      */     }
/*      */     
/*  594 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/*  595 */     int j = -1;
/*  596 */     Set<ShaderMacro> set = new LinkedHashSet<>();
/*  597 */     int k = 1;
/*      */ 
/*      */     
/*      */     while (true) {
/*  601 */       String s1 = reader.readLine();
/*      */       
/*  603 */       if (s1 == null) {
/*      */         
/*  605 */         char[] achar = chararraywriter.toCharArray();
/*      */         
/*  607 */         if (j >= 0 && set.size() > 0) {
/*      */           
/*  609 */           StringBuilder stringbuilder = new StringBuilder();
/*      */           
/*  611 */           for (ShaderMacro shadermacro : set) {
/*      */             
/*  613 */             stringbuilder.append("#define ");
/*  614 */             stringbuilder.append(shadermacro.getName());
/*  615 */             stringbuilder.append(" ");
/*  616 */             stringbuilder.append(shadermacro.getValue());
/*  617 */             stringbuilder.append("\n");
/*      */           } 
/*      */           
/*  620 */           String s7 = stringbuilder.toString();
/*  621 */           StringBuilder stringbuilder1 = new StringBuilder(new String(achar));
/*  622 */           stringbuilder1.insert(j, s7);
/*  623 */           String s9 = stringbuilder1.toString();
/*  624 */           achar = s9.toCharArray();
/*      */         } 
/*      */         
/*  627 */         CharArrayReader chararrayreader = new CharArrayReader(achar);
/*  628 */         return new BufferedReader(chararrayreader);
/*      */       } 
/*      */       
/*  631 */       if (j < 0) {
/*      */         
/*  633 */         Matcher matcher = PATTERN_VERSION.matcher(s1);
/*      */         
/*  635 */         if (matcher.matches()) {
/*      */           
/*  637 */           String s2 = ShaderMacros.getFixedMacroLines() + ShaderMacros.getOptionMacroLines();
/*  638 */           String s3 = s1 + "\n" + s2;
/*  639 */           String s4 = "#line " + (k + 1) + " " + fileIndex;
/*  640 */           s1 = s3 + s4;
/*  641 */           j = chararraywriter.size() + s3.length();
/*      */         } 
/*      */       } 
/*      */       
/*  645 */       Matcher matcher1 = PATTERN_INCLUDE.matcher(s1);
/*      */       
/*  647 */       if (matcher1.matches()) {
/*      */         
/*  649 */         String s6 = matcher1.group(1);
/*  650 */         boolean flag = s6.startsWith("/");
/*  651 */         String s8 = flag ? ("/shaders" + s6) : (s + "/" + s6);
/*      */         
/*  653 */         if (!listFiles.contains(s8))
/*      */         {
/*  655 */           listFiles.add(s8);
/*      */         }
/*      */         
/*  658 */         int l = listFiles.indexOf(s8) + 1;
/*  659 */         s1 = loadFile(s8, shaderPack, l, listFiles, includeLevel);
/*      */         
/*  661 */         if (s1 == null)
/*      */         {
/*  663 */           throw new IOException("Included file not found: " + filePath);
/*      */         }
/*      */         
/*  666 */         if (s1.endsWith("\n"))
/*      */         {
/*  668 */           s1 = s1.substring(0, s1.length() - 1);
/*      */         }
/*      */         
/*  671 */         String s5 = "#line 1 " + l + "\n";
/*      */         
/*  673 */         if (s1.startsWith("#version "))
/*      */         {
/*  675 */           s5 = "";
/*      */         }
/*      */         
/*  678 */         s1 = s5 + s1 + "\n#line " + (k + 1) + " " + fileIndex;
/*      */       } 
/*      */       
/*  681 */       if (j >= 0 && s1.contains(ShaderMacros.getPrefixMacro())) {
/*      */         
/*  683 */         ShaderMacro[] ashadermacro = findMacros(s1, ShaderMacros.getExtensions());
/*      */         
/*  685 */         for (int i1 = 0; i1 < ashadermacro.length; i1++) {
/*      */           
/*  687 */           ShaderMacro shadermacro1 = ashadermacro[i1];
/*  688 */           set.add(shadermacro1);
/*      */         } 
/*      */       } 
/*      */       
/*  692 */       chararraywriter.write(s1);
/*  693 */       chararraywriter.write("\n");
/*  694 */       k++;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderMacro[] findMacros(String line, ShaderMacro[] macros) {
/*  700 */     List<ShaderMacro> list = new ArrayList<>();
/*      */     
/*  702 */     for (int i = 0; i < macros.length; i++) {
/*      */       
/*  704 */       ShaderMacro shadermacro = macros[i];
/*      */       
/*  706 */       if (line.contains(shadermacro.getName()))
/*      */       {
/*  708 */         list.add(shadermacro);
/*      */       }
/*      */     } 
/*      */     
/*  712 */     ShaderMacro[] ashadermacro = list.<ShaderMacro>toArray(new ShaderMacro[list.size()]);
/*  713 */     return ashadermacro;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String loadFile(String filePath, IShaderPack shaderPack, int fileIndex, List<String> listFiles, int includeLevel) throws IOException {
/*  718 */     if (includeLevel >= 10)
/*      */     {
/*  720 */       throw new IOException("#include depth exceeded: " + includeLevel + ", file: " + filePath);
/*      */     }
/*      */ 
/*      */     
/*  724 */     includeLevel++;
/*  725 */     InputStream inputstream = shaderPack.getResourceAsStream(filePath);
/*      */     
/*  727 */     if (inputstream == null)
/*      */     {
/*  729 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  733 */     InputStreamReader inputstreamreader = new InputStreamReader(inputstream, StandardCharsets.US_ASCII);
/*  734 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/*  735 */     bufferedreader = resolveIncludes(bufferedreader, filePath, shaderPack, fileIndex, listFiles, includeLevel);
/*  736 */     CharArrayWriter chararraywriter = new CharArrayWriter();
/*      */ 
/*      */     
/*      */     while (true) {
/*  740 */       String s = bufferedreader.readLine();
/*      */       
/*  742 */       if (s == null)
/*      */       {
/*  744 */         return chararraywriter.toString();
/*      */       }
/*      */       
/*  747 */       chararraywriter.write(s);
/*  748 */       chararraywriter.write("\n");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CustomUniforms parseCustomUniforms(Properties props) {
/*  756 */     String s = "uniform";
/*  757 */     String s1 = "variable";
/*  758 */     String s2 = s + ".";
/*  759 */     String s3 = s1 + ".";
/*  760 */     Map<String, IExpression> map = new HashMap<>();
/*  761 */     List<CustomUniform> list = new ArrayList<>();
/*      */     
/*  763 */     for (Object e : props.keySet()) {
/*      */       
/*  765 */       String s4 = (String)e;
/*  766 */       String[] astring = Config.tokenize(s4, ".");
/*      */       
/*  768 */       if (astring.length == 3) {
/*      */         
/*  770 */         String s5 = astring[0];
/*  771 */         String s6 = astring[1];
/*  772 */         String s7 = astring[2];
/*  773 */         String s8 = props.getProperty(s4).trim();
/*      */         
/*  775 */         if (map.containsKey(s7)) {
/*      */           
/*  777 */           SMCLog.warning("Expression already defined: " + s7); continue;
/*      */         } 
/*  779 */         if (s5.equals(s) || s5.equals(s1)) {
/*      */           
/*  781 */           SMCLog.info("Custom " + s5 + ": " + s7);
/*  782 */           CustomUniform customuniform = parseCustomUniform(s5, s7, s6, s8, map);
/*      */           
/*  784 */           if (customuniform != null) {
/*      */             
/*  786 */             map.put(s7, customuniform.getExpression());
/*      */             
/*  788 */             if (!s5.equals(s1))
/*      */             {
/*  790 */               list.add(customuniform);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  797 */     if (list.size() <= 0)
/*      */     {
/*  799 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  803 */     CustomUniform[] acustomuniform = list.<CustomUniform>toArray(new CustomUniform[list.size()]);
/*  804 */     CustomUniforms customuniforms = new CustomUniforms(acustomuniform, map);
/*  805 */     return customuniforms;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomUniform parseCustomUniform(String kind, String name, String type, String src, Map<String, IExpression> mapExpressions) {
/*      */     try {
/*  813 */       UniformType uniformtype = UniformType.parse(type);
/*      */       
/*  815 */       if (uniformtype == null) {
/*      */         
/*  817 */         SMCLog.warning("Unknown " + kind + " type: " + uniformtype);
/*  818 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  822 */       ShaderExpressionResolver shaderexpressionresolver = new ShaderExpressionResolver(mapExpressions);
/*  823 */       ExpressionParser expressionparser = new ExpressionParser((IExpressionResolver)shaderexpressionresolver);
/*  824 */       IExpression iexpression = expressionparser.parse(src);
/*  825 */       ExpressionType expressiontype = iexpression.getExpressionType();
/*      */       
/*  827 */       if (!uniformtype.matchesExpressionType(expressiontype)) {
/*      */         
/*  829 */         SMCLog.warning("Expression type does not match " + kind + " type, expression: " + expressiontype + ", " + kind + ": " + uniformtype + " " + name);
/*  830 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  834 */       iexpression = makeExpressionCached(iexpression);
/*  835 */       CustomUniform customuniform = new CustomUniform(name, uniformtype, iexpression);
/*  836 */       return customuniform;
/*      */ 
/*      */     
/*      */     }
/*  840 */     catch (ParseException parseexception) {
/*      */       
/*  842 */       SMCLog.warning(parseexception.getClass().getName() + ": " + parseexception.getMessage());
/*  843 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static IExpression makeExpressionCached(IExpression expr) {
/*  849 */     return (expr instanceof IExpressionFloat) ? (IExpression)new ExpressionFloatCached((IExpressionFloat)expr) : ((expr instanceof IExpressionFloatArray) ? (IExpression)new ExpressionFloatArrayCached((IExpressionFloatArray)expr) : expr);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void parseAlphaStates(Properties props) {
/*  854 */     for (Object e : props.keySet()) {
/*      */       
/*  856 */       String s = (String)e;
/*  857 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/*  859 */       if (astring.length == 2) {
/*      */         
/*  861 */         String s1 = astring[0];
/*  862 */         String s2 = astring[1];
/*      */         
/*  864 */         if (s1.equals("alphaTest")) {
/*      */           
/*  866 */           Program program = Shaders.getProgram(s2);
/*      */           
/*  868 */           if (program == null) {
/*      */             
/*  870 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  874 */           String s3 = props.getProperty(s).trim();
/*  875 */           GlAlphaState glalphastate = parseAlphaState(s3);
/*      */           
/*  877 */           if (glalphastate != null)
/*      */           {
/*  879 */             program.setAlphaState(glalphastate);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static GlAlphaState parseAlphaState(String str) {
/*  889 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/*  891 */     if (astring.length == 1) {
/*      */       
/*  893 */       String s = astring[0];
/*      */       
/*  895 */       if (s.equals("off") || s.equals("false"))
/*      */       {
/*  897 */         return new GlAlphaState(false);
/*      */       }
/*      */     }
/*  900 */     else if (astring.length == 2) {
/*      */       
/*  902 */       String s2 = astring[0];
/*  903 */       String s1 = astring[1];
/*  904 */       Integer integer = mapAlphaFuncs.get(s2);
/*  905 */       float f = Config.parseFloat(s1, -1.0F);
/*      */       
/*  907 */       if (integer != null && f >= 0.0F)
/*      */       {
/*  909 */         return new GlAlphaState(true, integer.intValue(), f);
/*      */       }
/*      */     } 
/*      */     
/*  913 */     SMCLog.severe("Invalid alpha test: " + str);
/*  914 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void parseBlendStates(Properties props) {
/*  919 */     for (Object e : props.keySet()) {
/*      */       
/*  921 */       String s = (String)e;
/*  922 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/*  924 */       if (astring.length == 2) {
/*      */         
/*  926 */         String s1 = astring[0];
/*  927 */         String s2 = astring[1];
/*      */         
/*  929 */         if (s1.equals("blend")) {
/*      */           
/*  931 */           Program program = Shaders.getProgram(s2);
/*      */           
/*  933 */           if (program == null) {
/*      */             
/*  935 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/*  939 */           String s3 = props.getProperty(s).trim();
/*  940 */           GlBlendState glblendstate = parseBlendState(s3);
/*      */           
/*  942 */           if (glblendstate != null)
/*      */           {
/*  944 */             program.setBlendState(glblendstate);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static GlBlendState parseBlendState(String str) {
/*  954 */     String[] astring = Config.tokenize(str, " ");
/*      */     
/*  956 */     if (astring.length == 1) {
/*      */       
/*  958 */       String s = astring[0];
/*      */       
/*  960 */       if (s.equals("off") || s.equals("false"))
/*      */       {
/*  962 */         return new GlBlendState(false);
/*      */       }
/*      */     }
/*  965 */     else if (astring.length == 2 || astring.length == 4) {
/*      */       
/*  967 */       String s4 = astring[0];
/*  968 */       String s1 = astring[1];
/*  969 */       String s2 = s4;
/*  970 */       String s3 = s1;
/*      */       
/*  972 */       if (astring.length == 4) {
/*      */         
/*  974 */         s2 = astring[2];
/*  975 */         s3 = astring[3];
/*      */       } 
/*      */       
/*  978 */       Integer integer = mapBlendFactors.get(s4);
/*  979 */       Integer integer1 = mapBlendFactors.get(s1);
/*  980 */       Integer integer2 = mapBlendFactors.get(s2);
/*  981 */       Integer integer3 = mapBlendFactors.get(s3);
/*      */       
/*  983 */       if (integer != null && integer1 != null && integer2 != null && integer3 != null)
/*      */       {
/*  985 */         return new GlBlendState(true, integer.intValue(), integer1.intValue(), integer2.intValue(), integer3.intValue());
/*      */       }
/*      */     } 
/*      */     
/*  989 */     SMCLog.severe("Invalid blend mode: " + str);
/*  990 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void parseRenderScales(Properties props) {
/*  995 */     for (Object e : props.keySet()) {
/*      */       
/*  997 */       String s = (String)e;
/*  998 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/* 1000 */       if (astring.length == 2) {
/*      */         
/* 1002 */         String s1 = astring[0];
/* 1003 */         String s2 = astring[1];
/*      */         
/* 1005 */         if (s1.equals("scale")) {
/*      */           
/* 1007 */           Program program = Shaders.getProgram(s2);
/*      */           
/* 1009 */           if (program == null) {
/*      */             
/* 1011 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/* 1015 */           String s3 = props.getProperty(s).trim();
/* 1016 */           RenderScale renderscale = parseRenderScale(s3);
/*      */           
/* 1018 */           if (renderscale != null)
/*      */           {
/* 1020 */             program.setRenderScale(renderscale);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static RenderScale parseRenderScale(String str) {
/* 1030 */     String[] astring = Config.tokenize(str, " ");
/* 1031 */     float f = Config.parseFloat(astring[0], -1.0F);
/* 1032 */     float f1 = 0.0F;
/* 1033 */     float f2 = 0.0F;
/*      */     
/* 1035 */     if (astring.length > 1) {
/*      */       
/* 1037 */       if (astring.length != 3) {
/*      */         
/* 1039 */         SMCLog.severe("Invalid render scale: " + str);
/* 1040 */         return null;
/*      */       } 
/*      */       
/* 1043 */       f1 = Config.parseFloat(astring[1], -1.0F);
/* 1044 */       f2 = Config.parseFloat(astring[2], -1.0F);
/*      */     } 
/*      */     
/* 1047 */     if (Config.between(f, 0.0F, 1.0F) && Config.between(f1, 0.0F, 1.0F) && Config.between(f2, 0.0F, 1.0F))
/*      */     {
/* 1049 */       return new RenderScale(f, f1, f2);
/*      */     }
/*      */ 
/*      */     
/* 1053 */     SMCLog.severe("Invalid render scale: " + str);
/* 1054 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void parseBuffersFlip(Properties props) {
/* 1060 */     for (Object e : props.keySet()) {
/*      */       
/* 1062 */       String s = (String)e;
/* 1063 */       String[] astring = Config.tokenize(s, ".");
/*      */       
/* 1065 */       if (astring.length == 3) {
/*      */         
/* 1067 */         String s1 = astring[0];
/* 1068 */         String s2 = astring[1];
/* 1069 */         String s3 = astring[2];
/*      */         
/* 1071 */         if (s1.equals("flip")) {
/*      */           
/* 1073 */           Program program = Shaders.getProgram(s2);
/*      */           
/* 1075 */           if (program == null) {
/*      */             
/* 1077 */             SMCLog.severe("Invalid program name: " + s2);
/*      */             
/*      */             continue;
/*      */           } 
/* 1081 */           Boolean[] aboolean = program.getBuffersFlip();
/* 1082 */           int i = Shaders.getBufferIndexFromString(s3);
/*      */           
/* 1084 */           if (i >= 0 && i < aboolean.length) {
/*      */             
/* 1086 */             String s4 = props.getProperty(s).trim();
/* 1087 */             Boolean obool = Config.parseBoolean(s4, null);
/*      */             
/* 1089 */             if (obool == null) {
/*      */               
/* 1091 */               SMCLog.severe("Invalid boolean value: " + s4);
/*      */               
/*      */               continue;
/*      */             } 
/* 1095 */             aboolean[i] = obool;
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 1100 */           SMCLog.severe("Invalid buffer name: " + s3);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Map<String, Integer> makeMapAlphaFuncs() {
/* 1110 */     Map<String, Integer> map = new HashMap<>();
/* 1111 */     map.put("NEVER", new Integer(512));
/* 1112 */     map.put("LESS", new Integer(513));
/* 1113 */     map.put("EQUAL", new Integer(514));
/* 1114 */     map.put("LEQUAL", new Integer(515));
/* 1115 */     map.put("GREATER", new Integer(516));
/* 1116 */     map.put("NOTEQUAL", new Integer(517));
/* 1117 */     map.put("GEQUAL", new Integer(518));
/* 1118 */     map.put("ALWAYS", new Integer(519));
/* 1119 */     return Collections.unmodifiableMap(map);
/*      */   }
/*      */ 
/*      */   
/*      */   private static Map<String, Integer> makeMapBlendFactors() {
/* 1124 */     Map<String, Integer> map = new HashMap<>();
/* 1125 */     map.put("ZERO", new Integer(0));
/* 1126 */     map.put("ONE", new Integer(1));
/* 1127 */     map.put("SRC_COLOR", new Integer(768));
/* 1128 */     map.put("ONE_MINUS_SRC_COLOR", new Integer(769));
/* 1129 */     map.put("DST_COLOR", new Integer(774));
/* 1130 */     map.put("ONE_MINUS_DST_COLOR", new Integer(775));
/* 1131 */     map.put("SRC_ALPHA", new Integer(770));
/* 1132 */     map.put("ONE_MINUS_SRC_ALPHA", new Integer(771));
/* 1133 */     map.put("DST_ALPHA", new Integer(772));
/* 1134 */     map.put("ONE_MINUS_DST_ALPHA", new Integer(773));
/* 1135 */     map.put("CONSTANT_COLOR", new Integer(32769));
/* 1136 */     map.put("ONE_MINUS_CONSTANT_COLOR", new Integer(32770));
/* 1137 */     map.put("CONSTANT_ALPHA", new Integer(32771));
/* 1138 */     map.put("ONE_MINUS_CONSTANT_ALPHA", new Integer(32772));
/* 1139 */     map.put("SRC_ALPHA_SATURATE", new Integer(776));
/* 1140 */     return Collections.unmodifiableMap(map);
/*      */   }
/*      */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderPackParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */