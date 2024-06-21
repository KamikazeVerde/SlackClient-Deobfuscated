/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public class FlatItemArrayType
/*    */   extends BaseItemArrayType
/*    */ {
/*    */   public FlatItemArrayType() {
/* 32 */     super("Flat Item Array");
/*    */   }
/*    */ 
/*    */   
/*    */   public Item[] read(ByteBuf buffer) throws Exception {
/* 37 */     int amount = Type.SHORT.readPrimitive(buffer);
/* 38 */     Item[] array = new Item[amount];
/* 39 */     for (int i = 0; i < amount; i++) {
/* 40 */       array[i] = (Item)Type.FLAT_ITEM.read(buffer);
/*    */     }
/* 42 */     return array;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Item[] object) throws Exception {
/* 47 */     Type.SHORT.writePrimitive(buffer, (short)object.length);
/* 48 */     for (Item o : object)
/* 49 */       Type.FLAT_ITEM.write(buffer, o); 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\FlatItemArrayType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */