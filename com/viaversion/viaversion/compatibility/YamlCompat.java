/*    */ package com.viaversion.viaversion.compatibility;
/*    */ 
/*    */ import org.yaml.snakeyaml.DumperOptions;
/*    */ import org.yaml.snakeyaml.constructor.SafeConstructor;
/*    */ import org.yaml.snakeyaml.representer.Representer;
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
/*    */ public interface YamlCompat
/*    */ {
/*    */   Representer createRepresenter(DumperOptions paramDumperOptions);
/*    */   
/*    */   SafeConstructor createSafeConstructor();
/*    */   
/*    */   static boolean isVersion1() {
/*    */     try {
/* 32 */       SafeConstructor.class.getDeclaredConstructor(new Class[0]);
/* 33 */       return true;
/* 34 */     } catch (NoSuchMethodException e) {
/* 35 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\compatibility\YamlCompat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */