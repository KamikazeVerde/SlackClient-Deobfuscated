/*    */ package org.yaml.snakeyaml.extensions.compactnotation;
/*    */ 
/*    */ import org.yaml.snakeyaml.LoaderOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PackageCompactConstructor
/*    */   extends CompactConstructor
/*    */ {
/*    */   private final String packageName;
/*    */   
/*    */   public PackageCompactConstructor(String packageName) {
/* 23 */     super(new LoaderOptions());
/* 24 */     this.packageName = packageName;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Class<?> getClassForName(String name) throws ClassNotFoundException {
/* 29 */     if (name.indexOf('.') < 0) {
/*    */       try {
/* 31 */         Class<?> clazz = Class.forName(this.packageName + "." + name);
/* 32 */         return clazz;
/* 33 */       } catch (ClassNotFoundException classNotFoundException) {}
/*    */     }
/*    */ 
/*    */     
/* 37 */     return super.getClassForName(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\extensions\compactnotation\PackageCompactConstructor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */