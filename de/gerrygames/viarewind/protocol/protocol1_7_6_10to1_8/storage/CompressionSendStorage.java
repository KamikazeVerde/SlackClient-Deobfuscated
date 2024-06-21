/*    */ package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StoredObject;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ 
/*    */ public class CompressionSendStorage extends StoredObject {
/*    */   private boolean removeCompression = false;
/*    */   
/*    */   public CompressionSendStorage(UserConnection user) {
/* 10 */     super(user);
/*    */   }
/*    */   
/*    */   public boolean isRemoveCompression() {
/* 14 */     return this.removeCompression;
/*    */   }
/*    */   
/*    */   public void setRemoveCompression(boolean removeCompression) {
/* 18 */     this.removeCompression = removeCompression;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\protocol\protocol1_7_6_10to1_8\storage\CompressionSendStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */