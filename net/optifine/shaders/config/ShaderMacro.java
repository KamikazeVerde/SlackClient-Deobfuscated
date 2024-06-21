/*    */ package net.optifine.shaders.config;
/*    */ 
/*    */ 
/*    */ public class ShaderMacro
/*    */ {
/*    */   private String name;
/*    */   private String value;
/*    */   
/*    */   public ShaderMacro(String name, String value) {
/* 10 */     this.name = name;
/* 11 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 16 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 21 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSourceLine() {
/* 26 */     return "#define " + this.name + " " + this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return getSourceLine();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\config\ShaderMacro.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */