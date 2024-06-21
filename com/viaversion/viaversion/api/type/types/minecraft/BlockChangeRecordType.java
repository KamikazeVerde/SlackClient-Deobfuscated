/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_8;
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
/*    */ public class BlockChangeRecordType
/*    */   extends Type<BlockChangeRecord>
/*    */ {
/*    */   public BlockChangeRecordType() {
/* 33 */     super(BlockChangeRecord.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockChangeRecord read(ByteBuf buffer) throws Exception {
/* 38 */     short position = Type.SHORT.readPrimitive(buffer);
/* 39 */     int blockId = Type.VAR_INT.readPrimitive(buffer);
/* 40 */     return (BlockChangeRecord)new BlockChangeRecord1_8(position >> 12 & 0xF, position & 0xFF, position >> 8 & 0xF, blockId);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, BlockChangeRecord object) throws Exception {
/* 45 */     Type.SHORT.writePrimitive(buffer, (short)(object.getSectionX() << 12 | object.getSectionZ() << 8 | object.getY()));
/* 46 */     Type.VAR_INT.writePrimitive(buffer, object.getBlockId());
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\BlockChangeRecordType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */