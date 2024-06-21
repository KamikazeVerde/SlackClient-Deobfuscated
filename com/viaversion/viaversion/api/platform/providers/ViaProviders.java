/*    */ package com.viaversion.viaversion.api.platform.providers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ public class ViaProviders
/*    */ {
/* 32 */   private final Map<Class<? extends Provider>, Provider> providers = new HashMap<>();
/* 33 */   private final List<Class<? extends Provider>> lonelyProviders = new ArrayList<>();
/*    */   
/*    */   public void require(Class<? extends Provider> provider) {
/* 36 */     this.lonelyProviders.add(provider);
/*    */   }
/*    */   
/*    */   public <T extends Provider> void register(Class<T> provider, T value) {
/* 40 */     this.providers.put(provider, (Provider)value);
/*    */   }
/*    */   
/*    */   public <T extends Provider> void use(Class<T> provider, T value) {
/* 44 */     this.lonelyProviders.remove(provider);
/* 45 */     this.providers.put(provider, (Provider)value);
/*    */   }
/*    */   
/*    */   public <T extends Provider> T get(Class<T> provider) {
/* 49 */     Provider rawProvider = this.providers.get(provider);
/* 50 */     if (rawProvider != null) {
/* 51 */       return (T)rawProvider;
/*    */     }
/* 53 */     if (this.lonelyProviders.contains(provider)) {
/* 54 */       throw new IllegalStateException("There was no provider for " + provider + ", one is required!");
/*    */     }
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\platform\providers\ViaProviders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */