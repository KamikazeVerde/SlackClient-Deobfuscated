/*    */ package com.viaversion.viaversion.dump;
/*    */ 
/*    */ import java.util.List;
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
/*    */ public class PluginInfo
/*    */ {
/*    */   private final boolean enabled;
/*    */   private final String name;
/*    */   private final String version;
/*    */   private final String main;
/*    */   private final List<String> authors;
/*    */   
/*    */   public PluginInfo(boolean enabled, String name, String version, String main, List<String> authors) {
/* 30 */     this.enabled = enabled;
/* 31 */     this.name = name;
/* 32 */     this.version = version;
/* 33 */     this.main = main;
/* 34 */     this.authors = authors;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 38 */     return this.enabled;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 42 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getVersion() {
/* 46 */     return this.version;
/*    */   }
/*    */   
/*    */   public String getMain() {
/* 50 */     return this.main;
/*    */   }
/*    */   
/*    */   public List<String> getAuthors() {
/* 54 */     return this.authors;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\dump\PluginInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */