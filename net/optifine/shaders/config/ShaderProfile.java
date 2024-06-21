/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.LinkedHashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class ShaderProfile
/*    */ {
/* 11 */   private String name = null;
/* 12 */   private Map<String, String> mapOptionValues = new LinkedHashMap<>();
/* 13 */   private Set<String> disabledPrograms = new LinkedHashSet<>();
/*    */ 
/*    */   
/*    */   public ShaderProfile(String name) {
/* 17 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 22 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addOptionValue(String option, String value) {
/* 27 */     this.mapOptionValues.put(option, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addOptionValues(ShaderProfile prof) {
/* 32 */     if (prof != null)
/*    */     {
/* 34 */       this.mapOptionValues.putAll(prof.mapOptionValues);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyOptionValues(ShaderOption[] options) {
/* 40 */     for (int i = 0; i < options.length; i++) {
/*    */       
/* 42 */       ShaderOption shaderoption = options[i];
/* 43 */       String s = shaderoption.getName();
/* 44 */       String s1 = this.mapOptionValues.get(s);
/*    */       
/* 46 */       if (s1 != null)
/*    */       {
/* 48 */         shaderoption.setValue(s1);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getOptions() {
/* 55 */     Set<String> set = this.mapOptionValues.keySet();
/* 56 */     String[] astring = set.<String>toArray(new String[set.size()]);
/* 57 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue(String key) {
/* 62 */     return this.mapOptionValues.get(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addDisabledProgram(String program) {
/* 67 */     this.disabledPrograms.add(program);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeDisabledProgram(String program) {
/* 72 */     this.disabledPrograms.remove(program);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getDisabledPrograms() {
/* 77 */     return new LinkedHashSet<>(this.disabledPrograms);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addDisabledPrograms(Collection<String> programs) {
/* 82 */     this.disabledPrograms.addAll(programs);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isProgramDisabled(String program) {
/* 87 */     return this.disabledPrograms.contains(program);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */