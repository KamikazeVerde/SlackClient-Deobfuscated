/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.item.DataItem;
/*    */ import com.viaversion.viaversion.api.minecraft.item.Item;
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public class FlatItemType
/*    */   extends BaseItemType
/*    */ {
/*    */   public FlatItemType() {
/* 33 */     super("FlatItem");
/*    */   }
/*    */ 
/*    */   
/*    */   public Item read(ByteBuf buffer) throws Exception {
/* 38 */     short id = buffer.readShort();
/* 39 */     if (id < 0) {
/* 40 */       return null;
/*    */     }
/* 42 */     DataItem dataItem = new DataItem();
/* 43 */     dataItem.setIdentifier(id);
/* 44 */     dataItem.setAmount(buffer.readByte());
/* 45 */     dataItem.setTag((CompoundTag)Type.NBT.read(buffer));
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
/* 57 */       Type.NBT.write(buffer, object.tag());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\FlatItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */