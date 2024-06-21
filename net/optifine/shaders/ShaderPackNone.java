/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ShaderPackNone
/*    */   implements IShaderPack
/*    */ {
/*    */   public void close() {}
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/* 13 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 18 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 23 */     return "OFF";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ShaderPackNone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */