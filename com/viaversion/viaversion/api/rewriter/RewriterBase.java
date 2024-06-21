/*    */ package com.viaversion.viaversion.api.rewriter;
/*    */ 
/*    */ import com.viaversion.viaversion.api.protocol.Protocol;
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
/*    */ public abstract class RewriterBase<T extends Protocol>
/*    */   implements Rewriter<T>
/*    */ {
/*    */   protected final T protocol;
/*    */   
/*    */   protected RewriterBase(T protocol) {
/* 31 */     this.protocol = protocol;
/*    */   }
/*    */ 
/*    */   
/*    */   public final void register() {
/* 36 */     registerPackets();
/* 37 */     registerRewrites();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void registerPackets() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void registerRewrites() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public T protocol() {
/* 54 */     return this.protocol;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\rewriter\RewriterBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */