/*    */ package com.viaversion.viaversion.api.minecraft.blockentity;
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
/*    */ public final class BlockEntityImpl
/*    */   implements BlockEntity
/*    */ {
/*    */   private final byte packedXZ;
/*    */   private final short y;
/*    */   private final int typeId;
/*    */   private final CompoundTag tag;
/*    */   
/*    */   public BlockEntityImpl(byte packedXZ, short y, int typeId, CompoundTag tag) {
/* 34 */     this.packedXZ = packedXZ;
/* 35 */     this.y = y;
/* 36 */     this.typeId = typeId;
/* 37 */     this.tag = tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte packedXZ() {
/* 42 */     return this.packedXZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public short y() {
/* 47 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public int typeId() {
/* 52 */     return this.typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag tag() {
/* 57 */     return this.tag;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity withTypeId(int typeId) {
/* 62 */     return new BlockEntityImpl(this.packedXZ, this.y, typeId, this.tag);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\blockentity\BlockEntityImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */