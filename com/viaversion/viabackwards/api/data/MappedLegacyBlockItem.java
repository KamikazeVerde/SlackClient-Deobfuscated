/*    */ package com.viaversion.viabackwards.api.data;
/*    */ 
/*    */ import com.viaversion.viabackwards.utils.Block;
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
/*    */ public class MappedLegacyBlockItem
/*    */ {
/*    */   private final int id;
/*    */   private final short data;
/*    */   private final String name;
/*    */   private final Block block;
/*    */   private BlockEntityHandler blockEntityHandler;
/*    */   
/*    */   public MappedLegacyBlockItem(int id, short data, String name, boolean block) {
/* 33 */     this.id = id;
/* 34 */     this.data = data;
/* 35 */     this.name = (name != null) ? ("§f" + name) : null;
/* 36 */     this.block = block ? new Block(id, data) : null;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */   
/*    */   public short getData() {
/* 44 */     return this.data;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 48 */     return this.name;
/*    */   }
/*    */   
/*    */   public boolean isBlock() {
/* 52 */     return (this.block != null);
/*    */   }
/*    */   
/*    */   public Block getBlock() {
/* 56 */     return this.block;
/*    */   }
/*    */   
/*    */   public boolean hasBlockEntityHandler() {
/* 60 */     return (this.blockEntityHandler != null);
/*    */   }
/*    */   
/*    */   public BlockEntityHandler getBlockEntityHandler() {
/* 64 */     return this.blockEntityHandler;
/*    */   }
/*    */   
/*    */   public void setBlockEntityHandler(BlockEntityHandler blockEntityHandler) {
/* 68 */     this.blockEntityHandler = blockEntityHandler;
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface BlockEntityHandler {
/*    */     CompoundTag handleOrNewCompoundTag(int param1Int, CompoundTag param1CompoundTag);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\api\data\MappedLegacyBlockItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */