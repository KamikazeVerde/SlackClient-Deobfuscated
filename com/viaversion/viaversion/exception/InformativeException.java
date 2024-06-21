/*    */ package com.viaversion.viaversion.exception;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class InformativeException
/*    */   extends Exception
/*    */ {
/* 29 */   private final Map<String, Object> info = new HashMap<>();
/*    */   private boolean shouldBePrinted = true;
/*    */   private int sources;
/*    */   
/*    */   public InformativeException(Throwable cause) {
/* 34 */     super(cause);
/*    */   }
/*    */   
/*    */   public InformativeException set(String key, Object value) {
/* 38 */     this.info.put(key, value);
/* 39 */     return this;
/*    */   }
/*    */   
/*    */   public InformativeException addSource(Class<?> sourceClazz) {
/* 43 */     return set("Source " + this.sources++, getSource(sourceClazz));
/*    */   }
/*    */   
/*    */   private String getSource(Class<?> sourceClazz) {
/* 47 */     return sourceClazz.isAnonymousClass() ? (sourceClazz.getName() + " (Anonymous)") : sourceClazz.getName();
/*    */   }
/*    */   
/*    */   public boolean shouldBePrinted() {
/* 51 */     return this.shouldBePrinted;
/*    */   }
/*    */   
/*    */   public void setShouldBePrinted(boolean shouldBePrinted) {
/* 55 */     this.shouldBePrinted = shouldBePrinted;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 60 */     StringBuilder builder = new StringBuilder("Please report this on the Via support Discord or open an issue on the relevant GitHub repository\n");
/* 61 */     boolean first = true;
/* 62 */     for (Map.Entry<String, Object> entry : this.info.entrySet()) {
/* 63 */       if (!first) {
/* 64 */         builder.append(", ");
/*    */       }
/* 66 */       builder.append(entry.getKey()).append(": ").append(entry.getValue());
/* 67 */       first = false;
/*    */     } 
/* 69 */     return builder.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Throwable fillInStackTrace() {
/* 75 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\exception\InformativeException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */