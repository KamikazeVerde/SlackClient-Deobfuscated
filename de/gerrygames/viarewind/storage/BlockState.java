/*    */ package de.gerrygames.viarewind.storage;
/*    */ 
/*    */ public class BlockState
/*    */ {
/*    */   public static int extractId(int raw) {
/*  6 */     return raw >> 4;
/*    */   }
/*    */   
/*    */   public static int extractData(int raw) {
/* 10 */     return raw & 0xF;
/*    */   }
/*    */   
/*    */   public static int stateToRaw(int id, int data) {
/* 14 */     return id << 4 | data & 0xF;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewind\storage\BlockState.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */