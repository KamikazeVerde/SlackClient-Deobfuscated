/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.InputStream;
/*    */ import net.optifine.util.StrUtils;
/*    */ 
/*    */ public class ShaderPackFolder
/*    */   implements IShaderPack
/*    */ {
/*    */   protected File packFile;
/*    */   
/*    */   public ShaderPackFolder(String name, File file) {
/* 15 */     this.packFile = file;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public InputStream getResourceAsStream(String resName) {
/*    */     try {
/* 26 */       String s = StrUtils.removePrefixSuffix(resName, "/", "/");
/* 27 */       File file1 = new File(this.packFile, s);
/* 28 */       return !file1.exists() ? null : new BufferedInputStream(new FileInputStream(file1));
/*    */     }
/* 30 */     catch (Exception var4) {
/*    */       
/* 32 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasDirectory(String name) {
/* 38 */     File file1 = new File(this.packFile, name.substring(1));
/* 39 */     return !file1.exists() ? false : file1.isDirectory();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 44 */     return this.packFile.getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ShaderPackFolder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */