/*    */ package com.viaversion.viaversion.api.minecraft.item;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*    */ public interface Item
/*    */ {
/*    */   int identifier();
/*    */   
/*    */   void setIdentifier(int paramInt);
/*    */   
/*    */   int amount();
/*    */   
/*    */   void setAmount(int paramInt);
/*    */   
/*    */   default short data() {
/* 64 */     return 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   default void setData(short data) {
/* 74 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   CompoundTag tag();
/*    */   
/*    */   void setTag(CompoundTag paramCompoundTag);
/*    */   
/*    */   Item copy();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\item\Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */