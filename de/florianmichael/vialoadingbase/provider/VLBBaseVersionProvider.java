/*    */ package de.florianmichael.vialoadingbase.provider;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import com.viaversion.viaversion.protocols.base.BaseVersionProvider;
/*    */ import de.florianmichael.vialoadingbase.ViaLoadingBase;
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
/*    */ public class VLBBaseVersionProvider
/*    */   extends BaseVersionProvider
/*    */ {
/*    */   public int getClosestServerProtocol(UserConnection connection) throws Exception {
/* 28 */     if (connection.isClientSide()) {
/* 29 */       return ViaLoadingBase.getInstance().getTargetVersion().getVersion();
/*    */     }
/* 31 */     return super.getClosestServerProtocol(connection);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\florianmichael\vialoadingbase\provider\VLBBaseVersionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */