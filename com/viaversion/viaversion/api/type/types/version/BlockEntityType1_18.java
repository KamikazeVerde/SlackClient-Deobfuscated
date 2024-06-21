/*    */ package com.viaversion.viaversion.api.type.types.version;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntity;
/*    */ import com.viaversion.viaversion.api.minecraft.blockentity.BlockEntityImpl;
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
/*    */ 
/*    */ public class BlockEntityType1_18
/*    */   extends Type<BlockEntity>
/*    */ {
/*    */   public BlockEntityType1_18() {
/* 34 */     super(BlockEntity.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity read(ByteBuf buffer) throws Exception {
/* 39 */     byte xz = buffer.readByte();
/* 40 */     short y = buffer.readShort();
/* 41 */     int typeId = Type.VAR_INT.readPrimitive(buffer);
/* 42 */     CompoundTag tag = (CompoundTag)Type.NBT.read(buffer);
/* 43 */     return (BlockEntity)new BlockEntityImpl(xz, y, typeId, tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, BlockEntity entity) throws Exception {
/* 48 */     buffer.writeByte(entity.packedXZ());
/* 49 */     buffer.writeShort(entity.y());
/* 50 */     Type.VAR_INT.writePrimitive(buffer, entity.typeId());
/* 51 */     Type.NBT.write(buffer, entity.tag());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\version\BlockEntityType1_18.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */