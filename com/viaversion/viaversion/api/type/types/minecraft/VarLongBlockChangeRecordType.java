/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
/*    */ import com.viaversion.viaversion.api.minecraft.BlockChangeRecord1_16_2;
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
/*    */ public class VarLongBlockChangeRecordType
/*    */   extends Type<BlockChangeRecord>
/*    */ {
/*    */   public VarLongBlockChangeRecordType() {
/* 33 */     super(BlockChangeRecord.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockChangeRecord read(ByteBuf buffer) throws Exception {
/* 38 */     long data = Type.VAR_LONG.readPrimitive(buffer);
/* 39 */     short position = (short)(int)(data & 0xFFFL);
/* 40 */     return (BlockChangeRecord)new BlockChangeRecord1_16_2(position >>> 8 & 0xF, position & 0xF, position >>> 4 & 0xF, (int)(data >>> 12L));
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(ByteBuf buffer, BlockChangeRecord object) throws Exception {
/* 45 */     short position = (short)(object.getSectionX() << 8 | object.getSectionZ() << 4 | object.getSectionY());
/* 46 */     Type.VAR_LONG.writePrimitive(buffer, object.getBlockId() << 12L | position);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\VarLongBlockChangeRecordType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */