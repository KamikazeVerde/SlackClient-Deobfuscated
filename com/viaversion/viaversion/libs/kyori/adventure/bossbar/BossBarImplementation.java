/*    */ package com.viaversion.viaversion.libs.kyori.adventure.bossbar;
/*    */ 
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ @Internal
/*    */ public interface BossBarImplementation
/*    */ {
/*    */   @Internal
/*    */   @NotNull
/*    */   static <I extends BossBarImplementation> I get(@NotNull BossBar bar, @NotNull Class<I> type) {
/* 47 */     return BossBarImpl.ImplementationAccessor.get(bar, type);
/*    */   }
/*    */   
/*    */   @Internal
/*    */   public static interface Provider {
/*    */     @Internal
/*    */     @NotNull
/*    */     BossBarImplementation create(@NotNull BossBar param1BossBar);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\bossbar\BossBarImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */