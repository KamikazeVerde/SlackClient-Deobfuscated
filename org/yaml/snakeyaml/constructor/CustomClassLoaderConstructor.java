/*    */ package org.yaml.snakeyaml.constructor;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomClassLoaderConstructor
/*    */   extends Constructor
/*    */ {
/*    */   private final ClassLoader loader;
/*    */   
/*    */   public CustomClassLoaderConstructor(ClassLoader loader, LoaderOptions loadingConfig) {
/* 32 */     this(Object.class, loader, loadingConfig);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomClassLoaderConstructor(Class<? extends Object> theRoot, ClassLoader theLoader, LoaderOptions loadingConfig) {
/* 44 */     super(theRoot, loadingConfig);
/* 45 */     if (theLoader == null) {
/* 46 */       throw new NullPointerException("Loader must be provided.");
/*    */     }
/* 48 */     this.loader = theLoader;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Class<?> getClassForName(String name) throws ClassNotFoundException {
/* 60 */     return Class.forName(name, true, this.loader);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\constructor\CustomClassLoaderConstructor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */