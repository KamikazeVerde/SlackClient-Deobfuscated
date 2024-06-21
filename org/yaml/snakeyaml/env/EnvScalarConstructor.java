/*     */ package org.yaml.snakeyaml.env;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.yaml.snakeyaml.LoaderOptions;
/*     */ import org.yaml.snakeyaml.TypeDescription;
/*     */ import org.yaml.snakeyaml.constructor.AbstractConstruct;
/*     */ import org.yaml.snakeyaml.constructor.Constructor;
/*     */ import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;
/*     */ import org.yaml.snakeyaml.nodes.Node;
/*     */ import org.yaml.snakeyaml.nodes.ScalarNode;
/*     */ import org.yaml.snakeyaml.nodes.Tag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnvScalarConstructor
/*     */   extends Constructor
/*     */ {
/*  42 */   public static final Tag ENV_TAG = new Tag("!ENV");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public static final Pattern ENV_FORMAT = Pattern.compile("^\\$\\{\\s*((?<name>\\w+)((?<separator>:?(-|\\?))(?<value>\\S+)?)?)\\s*\\}$");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnvScalarConstructor() {
/*  54 */     super(new LoaderOptions());
/*  55 */     this.yamlConstructors.put(ENV_TAG, new ConstructEnv());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnvScalarConstructor(TypeDescription theRoot, Collection<TypeDescription> moreTDs, LoaderOptions loadingConfig) {
/*  67 */     super(theRoot, moreTDs, loadingConfig);
/*  68 */     this.yamlConstructors.put(ENV_TAG, new ConstructEnv());
/*     */   }
/*     */   
/*     */   private class ConstructEnv
/*     */     extends AbstractConstruct {
/*     */     public Object construct(Node node) {
/*  74 */       String val = EnvScalarConstructor.this.constructScalar((ScalarNode)node);
/*  75 */       Matcher matcher = EnvScalarConstructor.ENV_FORMAT.matcher(val);
/*  76 */       matcher.matches();
/*  77 */       String name = matcher.group("name");
/*  78 */       String value = matcher.group("value");
/*  79 */       String separator = matcher.group("separator");
/*  80 */       return EnvScalarConstructor.this.apply(name, separator, (value != null) ? value : "", EnvScalarConstructor.this.getEnv(name));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private ConstructEnv() {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String apply(String name, String separator, String value, String environment) {
/*  94 */     if (environment != null && !environment.isEmpty()) {
/*  95 */       return environment;
/*     */     }
/*     */     
/*  98 */     if (separator != null) {
/*     */       
/* 100 */       if (separator.equals("?") && 
/* 101 */         environment == null) {
/* 102 */         throw new MissingEnvironmentVariableException("Missing mandatory variable " + name + ": " + value);
/*     */       }
/*     */ 
/*     */       
/* 106 */       if (separator.equals(":?")) {
/* 107 */         if (environment == null) {
/* 108 */           throw new MissingEnvironmentVariableException("Missing mandatory variable " + name + ": " + value);
/*     */         }
/*     */         
/* 111 */         if (environment.isEmpty()) {
/* 112 */           throw new MissingEnvironmentVariableException("Empty mandatory variable " + name + ": " + value);
/*     */         }
/*     */       } 
/*     */       
/* 116 */       if (separator.startsWith(":")) {
/* 117 */         if (environment == null || environment.isEmpty()) {
/* 118 */           return value;
/*     */         }
/*     */       }
/* 121 */       else if (environment == null) {
/* 122 */         return value;
/*     */       } 
/*     */     } 
/*     */     
/* 126 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEnv(String key) {
/* 136 */     return System.getenv(key);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\env\EnvScalarConstructor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */