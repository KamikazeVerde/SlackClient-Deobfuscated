/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import io.netty.buffer.ByteBuf;
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
/*    */ public class FlatVarIntItemArrayType
/*    */   extends BaseItemArrayType
/*    */ {
/*    */   public FlatVarIntItemArrayType() {
/* 31 */     super("Flat Item Array");
/*    */   }
/*    */ 
/*    */   
/*    */   public Item[] read(ByteBuf buffer) throws Exception {
/* 36 */     int amount = SHORT.readPrimitive(buffer);
/* 37 */     Item[] array = new Item[amount];
/* 38 */     for (int i = 0; i < amount; i++) {
/* 39 */       array[i] = (Item)FLAT_VAR_INT_ITEM.read(buffer);
/*    */     }
/* 41 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Item[] object) throws Exception {
/* 46 */     SHORT.writePrimitive(buffer, (short)object.length);
/* 47 */     for (Item o : object)
/* 48 */       FLAT_VAR_INT_ITEM.write(buffer, o); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\FlatVarIntItemArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */