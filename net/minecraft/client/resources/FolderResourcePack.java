/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileFilter;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.Set;
/*    */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*    */ 
/*    */ public class FolderResourcePack
/*    */   extends AbstractResourcePack
/*    */ {
/*    */   public FolderResourcePack(File resourcePackFileIn) {
/* 17 */     super(resourcePackFileIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected InputStream getInputStreamByName(String name) throws IOException {
/* 22 */     return new BufferedInputStream(new FileInputStream(new File(this.resourcePackFile, name)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean hasResourceName(String name) {
/* 27 */     return (new File(this.resourcePackFile, name)).isFile();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getResourceDomains() {
/* 32 */     Set<String> set = Sets.newHashSet();
/* 33 */     File file1 = new File(this.resourcePackFile, "assets/");
/*    */     
/* 35 */     if (file1.isDirectory())
/*    */     {
/* 37 */       for (File file2 : file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)) {
/*    */         
/* 39 */         String s = getRelativeName(file1, file2);
/*    */         
/* 41 */         if (!s.equals(s.toLowerCase())) {
/*    */           
/* 43 */           logNameNotLowercase(s);
/*    */         }
/*    */         else {
/*    */           
/* 47 */           set.add(s.substring(0, s.length() - 1));
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 52 */     return set;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\resources\FolderResourcePack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */