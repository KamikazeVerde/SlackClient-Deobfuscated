/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderOptionVariableConst
/*    */   extends ShaderOptionVariable {
/*  9 */   private String type = null;
/* 10 */   private static final Pattern PATTERN_CONST = Pattern.compile("^\\s*const\\s*(float|int)\\s*([A-Za-z0-9_]+)\\s*=\\s*(-?[0-9\\.]+f?F?)\\s*;\\s*(//.*)?$");
/*    */ 
/*    */   
/*    */   public ShaderOptionVariableConst(String name, String type, String description, String value, String[] values, String path) {
/* 14 */     super(name, description, value, values, path);
/* 15 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 20 */     return "const " + this.type + " " + getName() + " = " + getValue() + "; // Shader option " + getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matchesLine(String line) {
/* 25 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 27 */     if (!matcher.matches())
/*    */     {
/* 29 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 33 */     String s = matcher.group(2);
/* 34 */     return s.matches(getName());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ShaderOption parseOption(String line, String path) {
/* 40 */     Matcher matcher = PATTERN_CONST.matcher(line);
/*    */     
/* 42 */     if (!matcher.matches())
/*    */     {
/* 44 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 48 */     String s = matcher.group(1);
/* 49 */     String s1 = matcher.group(2);
/* 50 */     String s2 = matcher.group(3);
/* 51 */     String s3 = matcher.group(4);
/* 52 */     String s4 = StrUtils.getSegment(s3, "[", "]");
/*    */     
/* 54 */     if (s4 != null && s4.length() > 0)
/*    */     {
/* 56 */       s3 = s3.replace(s4, "").trim();
/*    */     }
/*    */     
/* 59 */     String[] astring = parseValues(s2, s4);
/*    */     
/* 61 */     if (s1 != null && s1.length() > 0) {
/*    */       
/* 63 */       path = StrUtils.removePrefix(path, "/shaders/");
/* 64 */       ShaderOption shaderoption = new ShaderOptionVariableConst(s1, s, s3, s2, astring, path);
/* 65 */       return shaderoption;
/*    */     } 
/*    */ 
/*    */     
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderOptionVariableConst.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */