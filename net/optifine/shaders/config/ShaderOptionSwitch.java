/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lang;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ public class ShaderOptionSwitch
/*     */   extends ShaderOption {
/*  11 */   private static final Pattern PATTERN_DEFINE = Pattern.compile("^\\s*(//)?\\s*#define\\s+([A-Za-z0-9_]+)\\s*(//.*)?$");
/*  12 */   private static final Pattern PATTERN_IFDEF = Pattern.compile("^\\s*#if(n)?def\\s+([A-Za-z0-9_]+)(\\s*)?$");
/*     */ 
/*     */   
/*     */   public ShaderOptionSwitch(String name, String description, String value, String path) {
/*  16 */     super(name, description, value, new String[] { "false", "true" }, value, path);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/*  21 */     return isTrue(getValue()) ? ("#define " + getName() + " // Shader option ON") : ("//#define " + getName() + " // Shader option OFF");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/*  26 */     String s = super.getValueText(val);
/*  27 */     return (s != val) ? s : (isTrue(val) ? Lang.getOn() : Lang.getOff());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/*  32 */     return isTrue(val) ? "§a" : "§c";
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderOption parseOption(String line, String path) {
/*  37 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*     */     
/*  39 */     if (!matcher.matches())
/*     */     {
/*  41 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  45 */     String s = matcher.group(1);
/*  46 */     String s1 = matcher.group(2);
/*  47 */     String s2 = matcher.group(3);
/*     */     
/*  49 */     if (s1 != null && s1.length() > 0) {
/*     */       
/*  51 */       boolean flag = Config.equals(s, "//");
/*  52 */       boolean flag1 = !flag;
/*  53 */       path = StrUtils.removePrefix(path, "/shaders/");
/*  54 */       return new ShaderOptionSwitch(s1, s2, String.valueOf(flag1), path);
/*     */     } 
/*     */ 
/*     */     
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/*  65 */     Matcher matcher = PATTERN_DEFINE.matcher(line);
/*     */     
/*  67 */     if (!matcher.matches())
/*     */     {
/*  69 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  73 */     String s = matcher.group(2);
/*  74 */     return s.matches(getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkUsed() {
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/*  85 */     Matcher matcher = PATTERN_IFDEF.matcher(line);
/*     */     
/*  87 */     if (matcher.matches()) {
/*     */       
/*  89 */       String s = matcher.group(2);
/*     */       
/*  91 */       if (s.equals(getName()))
/*     */       {
/*  93 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  97 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isTrue(String val) {
/* 102 */     return Boolean.parseBoolean(val);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderOptionSwitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */