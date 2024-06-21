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
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface BlockEntity
/*    */ {
/*    */   static byte pack(int sectionX, int sectionZ) {
/* 31 */     return (byte)((sectionX & 0xF) << 4 | sectionZ & 0xF);
/*    */   }
/*    */   
/*    */   default byte sectionX() {
/* 35 */     return (byte)(packedXZ() >> 4 & 0xF);
/*    */   }
/*    */   
/*    */   default byte sectionZ() {
/* 39 */     return (byte)(packedXZ() & 0xF);
/*    */   }
/*    */   
/*    */   byte packedXZ();
/*    */   
/*    */   short y();
/*    */   
/*    */   int typeId();
/*    */   
/*    */   CompoundTag tag();
/*    */   
/*    */   BlockEntity withTypeId(int paramInt);
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\blockentity\BlockEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */