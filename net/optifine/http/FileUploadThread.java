/*    */ package net.optifine.http;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FileUploadThread
/*    */   extends Thread
/*    */ {
/*    */   private String urlString;
/*    */   private Map headers;
/*    */   private byte[] content;
/*    */   private IFileUploadListener listener;
/*    */   
/*    */   public FileUploadThread(String urlString, Map headers, byte[] content, IFileUploadListener listener) {
/* 14 */     this.urlString = urlString;
/* 15 */     this.headers = headers;
/* 16 */     this.content = content;
/* 17 */     this.listener = listener;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 24 */       HttpUtils.post(this.urlString, this.headers, this.content);
/* 25 */       this.listener.fileUploadFinished(this.urlString, this.content, null);
/*    */     }
/* 27 */     catch (Exception exception) {
/*    */       
/* 29 */       this.listener.fileUploadFinished(this.urlString, this.content, exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUrlString() {
/* 35 */     return this.urlString;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getContent() {
/* 40 */     return this.content;
/*    */   }
/*    */ 
/*    */   
/*    */   public IFileUploadListener getListener() {
/* 45 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\http\FileUploadThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */