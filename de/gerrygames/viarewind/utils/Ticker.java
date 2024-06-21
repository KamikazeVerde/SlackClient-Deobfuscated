/*    */ package de.gerrygames.viarewind.utils;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.connection.UserConnection;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class Ticker {
/*    */   public static void init() {
/*  9 */     if (init)
/* 10 */       return;  synchronized (Ticker.class) {
/* 11 */       if (init)
/* 12 */         return;  init = true;
/*    */     } 
/* 14 */     Via.getPlatform().runRepeatingSync(() -> Via.getManager().getConnectionManager().getConnections().forEach(()), 1L);
/*    */   }
/*    */   
/*    */   private static boolean init = false;
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\Ticker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */