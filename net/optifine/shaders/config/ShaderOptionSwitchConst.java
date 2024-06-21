/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderOptionSwitchConst
/*    */   extends ShaderOptionSwitch {
/*  9 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*bool\\s*([A-Za-z0-9_]+)\\s*=\\s*(true|false)\\s*;\\s*(//.*)?$");
/*    */ 
/*    */   
/*    */   public ShaderOptionSwitchConst(String name, String description, String value, String path) {
/* 13 */     super(name, description, value, path);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 18 */     return "const bool " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 23 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 25 */     if (!matcher.matches())
/*    */     {
/* 27 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 31 */     String s = matcher.group(1);
/* 32 */     String s1 = matcher.group(2);
/* 33 */     String s2 = matcher.group(3);
/*    */     
/* 35 */     if (s != null && s.length() > 0) {
/*    */       
/* 37 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 38 */       ShaderOption shaderoption = new ShaderOptionSwitchConst(s, s2, s1, path);
/* 39 */       shaderoption.setVisible(false);
/* 40 */       return shaderoption;
/*    */     } 
/*    */ 
/*    */     
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 51 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 53 */     if (!matcher.matches())
/*    */     {
/* 55 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 59 */     String s = matcher.group(1);
/* 60 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean checkUsed() {
/* 66 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderOptionSwitchConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */