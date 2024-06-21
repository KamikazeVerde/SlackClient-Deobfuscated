/*    */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import java.util.Set;
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
/*    */ public class ClientChunks
/*    */   extends StoredObject
/*    */ {
/* 26 */   private final Set<Long> loadedChunks = Sets.newConcurrentHashSet();
/*    */   
/*    */   public ClientChunks(UserConnection connection) {
/* 29 */     super(connection);
/*    */   }
/*    */   
/*    */   public static long toLong(int msw, int lsw) {
/* 33 */     return (msw << 32L) + lsw + 2147483648L;
/*    */   }
/*    */   
/*    */   public Set<Long> getLoadedChunks() {
/* 37 */     return this.loadedChunks;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\storage\ClientChunks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */