/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.expr.ExpressionParser;
/*     */ import net.optifine.expr.ExpressionType;
/*     */ import net.optifine.expr.IExpression;
/*     */ import net.optifine.expr.IExpressionBool;
/*     */ import net.optifine.expr.IExpressionFloat;
/*     */ import net.optifine.expr.IExpressionResolver;
/*     */ import net.optifine.expr.ParseException;
/*     */ 
/*     */ public class MacroState
/*     */ {
/*     */   private boolean active = true;
/*  24 */   private Deque<Boolean> dequeState = new ArrayDeque<>();
/*  25 */   private Deque<Boolean> dequeResolved = new ArrayDeque<>();
/*  26 */   private Map<String, String> mapMacroValues = new HashMap<>();
/*  27 */   private static final Pattern PATTERN_DIRECTIVE = Pattern.compile("\\s*#\\s*(\\w+)\\s*(.*)");
/*  28 */   private static final Pattern PATTERN_DEFINED = Pattern.compile("defined\\s+(\\w+)");
/*  29 */   private static final Pattern PATTERN_DEFINED_FUNC = Pattern.compile("defined\\s*\\(\\s*(\\w+)\\s*\\)");
/*  30 */   private static final Pattern PATTERN_MACRO = Pattern.compile("(\\w+)");
/*     */   private static final String DEFINE = "define";
/*     */   private static final String UNDEF = "undef";
/*     */   private static final String IFDEF = "ifdef";
/*     */   private static final String IFNDEF = "ifndef";
/*     */   private static final String IF = "if";
/*     */   private static final String ELSE = "else";
/*     */   private static final String ELIF = "elif";
/*     */   private static final String ENDIF = "endif";
/*  39 */   private static final List<String> MACRO_NAMES = Arrays.asList(new String[] { "define", "undef", "ifdef", "ifndef", "if", "else", "elif", "endif" });
/*     */ 
/*     */   
/*     */   public boolean processLine(String line) {
/*  43 */     Matcher matcher = PATTERN_DIRECTIVE.matcher(line);
/*     */     
/*  45 */     if (!matcher.matches())
/*     */     {
/*  47 */       return this.active;
/*     */     }
/*     */ 
/*     */     
/*  51 */     String s = matcher.group(1);
/*  52 */     String s1 = matcher.group(2);
/*  53 */     int i = s1.indexOf("//");
/*     */     
/*  55 */     if (i >= 0)
/*     */     {
/*  57 */       s1 = s1.substring(0, i);
/*     */     }
/*     */     
/*  60 */     boolean flag = this.active;
/*  61 */     processMacro(s, s1);
/*  62 */     this.active = !this.dequeState.contains(Boolean.FALSE);
/*  63 */     return (this.active || flag);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMacroLine(String line) {
/*  69 */     Matcher matcher = PATTERN_DIRECTIVE.matcher(line);
/*     */     
/*  71 */     if (!matcher.matches())
/*     */     {
/*  73 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  77 */     String s = matcher.group(1);
/*  78 */     return MACRO_NAMES.contains(s);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void processMacro(String name, String param) {
/*  84 */     StringTokenizer stringtokenizer = new StringTokenizer(param, " \t");
/*  85 */     String s = stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken() : "";
/*  86 */     String s1 = stringtokenizer.hasMoreTokens() ? stringtokenizer.nextToken("").trim() : "";
/*     */     
/*  88 */     if (name.equals("define")) {
/*     */       
/*  90 */       this.mapMacroValues.put(s, s1);
/*     */     }
/*  92 */     else if (name.equals("undef")) {
/*     */       
/*  94 */       this.mapMacroValues.remove(s);
/*     */     }
/*  96 */     else if (name.equals("ifdef")) {
/*     */       
/*  98 */       boolean flag6 = this.mapMacroValues.containsKey(s);
/*  99 */       this.dequeState.add(Boolean.valueOf(flag6));
/* 100 */       this.dequeResolved.add(Boolean.valueOf(flag6));
/*     */     }
/* 102 */     else if (name.equals("ifndef")) {
/*     */       
/* 104 */       boolean flag5 = !this.mapMacroValues.containsKey(s);
/* 105 */       this.dequeState.add(Boolean.valueOf(flag5));
/* 106 */       this.dequeResolved.add(Boolean.valueOf(flag5));
/*     */     }
/* 108 */     else if (name.equals("if")) {
/*     */       
/* 110 */       boolean flag4 = eval(param);
/* 111 */       this.dequeState.add(Boolean.valueOf(flag4));
/* 112 */       this.dequeResolved.add(Boolean.valueOf(flag4));
/*     */     }
/* 114 */     else if (!this.dequeState.isEmpty()) {
/*     */       boolean flag3; boolean flag7; boolean flag8; boolean flag; boolean flag1; boolean flag2;
/* 116 */       switch (name) {
/*     */         case "elif":
/* 118 */           flag3 = ((Boolean)this.dequeState.removeLast()).booleanValue();
/* 119 */           flag7 = ((Boolean)this.dequeResolved.removeLast()).booleanValue();
/*     */           
/* 121 */           if (flag7) {
/* 122 */             this.dequeState.add(Boolean.FALSE);
/* 123 */             this.dequeResolved.add(Boolean.valueOf(flag7)); break;
/*     */           } 
/* 125 */           flag8 = eval(param);
/* 126 */           this.dequeState.add(Boolean.valueOf(flag8));
/* 127 */           this.dequeResolved.add(Boolean.valueOf(flag8));
/*     */           break;
/*     */         
/*     */         case "else":
/* 131 */           flag = ((Boolean)this.dequeState.removeLast()).booleanValue();
/* 132 */           flag1 = ((Boolean)this.dequeResolved.removeLast()).booleanValue();
/* 133 */           flag2 = !flag1;
/* 134 */           this.dequeState.add(Boolean.valueOf(flag2));
/* 135 */           this.dequeResolved.add(Boolean.TRUE);
/*     */           break;
/*     */         case "endif":
/* 138 */           this.dequeState.removeLast();
/* 139 */           this.dequeResolved.removeLast();
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean eval(String str) {
/* 147 */     Matcher matcher = PATTERN_DEFINED.matcher(str);
/* 148 */     str = matcher.replaceAll("defined_$1");
/* 149 */     Matcher matcher1 = PATTERN_DEFINED_FUNC.matcher(str);
/* 150 */     str = matcher1.replaceAll("defined_$1");
/* 151 */     boolean flag = false;
/* 152 */     int i = 0;
/*     */ 
/*     */     
/*     */     do {
/* 156 */       flag = false;
/* 157 */       Matcher matcher2 = PATTERN_MACRO.matcher(str);
/*     */       
/* 159 */       while (matcher2.find()) {
/*     */         
/* 161 */         String s = matcher2.group();
/*     */         
/* 163 */         if (s.length() > 0) {
/*     */           
/* 165 */           char c0 = s.charAt(0);
/*     */           
/* 167 */           if ((Character.isLetter(c0) || c0 == '_') && this.mapMacroValues.containsKey(s)) {
/*     */             
/* 169 */             String s1 = this.mapMacroValues.get(s);
/*     */             
/* 171 */             if (s1 == null)
/*     */             {
/* 173 */               s1 = "1";
/*     */             }
/*     */             
/* 176 */             int j = matcher2.start();
/* 177 */             int k = matcher2.end();
/* 178 */             str = str.substring(0, j) + " " + s1 + " " + str.substring(k);
/* 179 */             flag = true;
/* 180 */             i++;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 186 */     } while (flag && i < 100);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 192 */     if (i >= 100) {
/*     */       
/* 194 */       Config.warn("Too many iterations: " + i + ", when resolving: " + str);
/* 195 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 201 */       IExpressionResolver iexpressionresolver = new MacroExpressionResolver(this.mapMacroValues);
/* 202 */       ExpressionParser expressionparser = new ExpressionParser(iexpressionresolver);
/* 203 */       IExpression iexpression = expressionparser.parse(str);
/*     */       
/* 205 */       if (iexpression.getExpressionType() == ExpressionType.BOOL) {
/*     */         
/* 207 */         IExpressionBool iexpressionbool = (IExpressionBool)iexpression;
/* 208 */         boolean flag1 = iexpressionbool.eval();
/* 209 */         return flag1;
/*     */       } 
/* 211 */       if (iexpression.getExpressionType() == ExpressionType.FLOAT) {
/*     */         
/* 213 */         IExpressionFloat iexpressionfloat = (IExpressionFloat)iexpression;
/* 214 */         float f = iexpressionfloat.eval();
/* 215 */         boolean flag2 = (f != 0.0F);
/* 216 */         return flag2;
/*     */       } 
/*     */ 
/*     */       
/* 220 */       throw new ParseException("Not a boolean or float expression: " + iexpression.getExpressionType());
/*     */     
/*     */     }
/* 223 */     catch (ParseException parseexception) {
/*     */       
/* 225 */       Config.warn("Invalid macro expression: " + str);
/* 226 */       Config.warn("Error: " + parseexception.getMessage());
/* 227 */       return false;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\MacroState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */