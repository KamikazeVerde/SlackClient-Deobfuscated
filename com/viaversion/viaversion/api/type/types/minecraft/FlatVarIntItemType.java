/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
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
/*    */ public class FlatVarIntItemType
/*    */   extends BaseItemType
/*    */ {
/*    */   public FlatVarIntItemType() {
/* 32 */     super("FlatVarIntItem");
/*    */   }
/*    */ 
/*    */   
/*    */   public Item read(ByteBuf buffer) throws Exception {
/* 37 */     boolean present = buffer.readBoolean();
/* 38 */     if (!present) {
/* 39 */       return null;
/*    */     }
/* 41 */     DataItem dataItem = new DataItem();
/* 42 */     dataItem.setIdentifier(VAR_INT.readPrimitive(buffer));
/* 43 */     dataItem.setAmount(buffer.readByte());
/* 44 */     dataItem.setTag((CompoundTag)NBT.read(buffer));
/* 45 */     return (Item)dataItem;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Item object) throws Exception {
/* 51 */     if (object == null) {
/* 52 */       buffer.writeBoolean(false);
/*    */     } else {
/* 54 */       buffer.writeBoolean(true);
/* 55 */       VAR_INT.writePrimitive(buffer, object.identifier());
/* 56 */       buffer.writeByte(object.amount());
/* 57 */       NBT.write(buffer, object.tag());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\FlatVarIntItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */