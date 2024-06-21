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
/*    */ public class ItemType
/*    */   extends BaseItemType
/*    */ {
/*    */   public ItemType() {
/* 32 */     super("Item");
/*    */   }
/*    */ 
/*    */   
/*    */   public Item read(ByteBuf buffer) throws Exception {
/* 37 */     short id = buffer.readShort();
/* 38 */     if (id < 0) {
/* 39 */       return null;
/*    */     }
/* 41 */     DataItem dataItem = new DataItem();
/* 42 */     dataItem.setIdentifier(id);
/* 43 */     dataItem.setAmount(buffer.readByte());
/* 44 */     dataItem.setData(buffer.readShort());
/* 45 */     dataItem.setTag((CompoundTag)NBT.read(buffer));
/* 46 */     return (Item)dataItem;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, Item object) throws Exception {
/* 52 */     if (object == null) {
/* 53 */       buffer.writeShort(-1);
/*    */     } else {
/* 55 */       buffer.writeShort(object.identifier());
/* 56 */       buffer.writeByte(object.amount());
/* 57 */       buffer.writeShort(object.data());
/* 58 */       NBT.write(buffer, object.tag());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\ItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */