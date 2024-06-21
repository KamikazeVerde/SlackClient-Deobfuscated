/*    */ package com.viaversion.viaversion.unsupported;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.platform.UnsupportedSoftware;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
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
/*    */ 
/*    */ public final class UnsupportedPlugin
/*    */   implements UnsupportedSoftware
/*    */ {
/*    */   private final String name;
/*    */   private final List<String> identifiers;
/*    */   private final String reason;
/*    */   
/*    */   public UnsupportedPlugin(String name, List<String> identifiers, String reason) {
/* 35 */     Preconditions.checkNotNull(name);
/* 36 */     Preconditions.checkNotNull(reason);
/* 37 */     Preconditions.checkArgument(!identifiers.isEmpty());
/* 38 */     this.name = name;
/* 39 */     this.identifiers = Collections.unmodifiableList(identifiers);
/* 40 */     this.reason = reason;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 45 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getReason() {
/* 50 */     return this.reason;
/*    */   }
/*    */ 
/*    */   
/*    */   public final String match() {
/* 55 */     for (String identifier : this.identifiers) {
/* 56 */       if (Via.getPlatform().hasPlugin(identifier)) {
/* 57 */         return identifier;
/*    */       }
/*    */     } 
/* 60 */     return null;
/*    */   }
/*    */   
/*    */   public static final class Builder
/*    */   {
/* 65 */     private final List<String> identifiers = new ArrayList<>();
/*    */     private String name;
/*    */     private String reason;
/*    */     
/*    */     public Builder name(String name) {
/* 70 */       this.name = name;
/* 71 */       return this;
/*    */     }
/*    */     
/*    */     public Builder reason(String reason) {
/* 75 */       this.reason = reason;
/* 76 */       return this;
/*    */     }
/*    */     
/*    */     public Builder addPlugin(String identifier) {
/* 80 */       this.identifiers.add(identifier);
/* 81 */       return this;
/*    */     }
/*    */     
/*    */     public UnsupportedPlugin build() {
/* 85 */       return new UnsupportedPlugin(this.name, this.identifiers, this.reason);
/*    */     }
/*    */   }
/*    */   
/*    */   public static final class Reason {
/*    */     public static final String SECURE_CHAT_BYPASS = "Instead of doing the obvious (or nothing at all), these kinds of plugins completely break chat message handling, usually then also breaking other plugins.";
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\unsupported\UnsupportedPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */