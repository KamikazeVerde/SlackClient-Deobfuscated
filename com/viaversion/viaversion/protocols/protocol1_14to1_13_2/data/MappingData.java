/*    */ package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.Via;
/*    */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*    */ import com.viaversion.viaversion.api.data.MappingDataLoader;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
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
/*    */ public class MappingData
/*    */   extends MappingDataBase
/*    */ {
/*    */   private IntSet motionBlocking;
/*    */   private IntSet nonFullBlocks;
/*    */   
/*    */   public MappingData() {
/* 33 */     super("1.13.2", "1.14");
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadExtras(CompoundTag data) {
/* 38 */     CompoundTag heightmap = MappingDataLoader.loadNBT("heightmap-1.14.nbt");
/* 39 */     IntArrayTag motionBlocking = (IntArrayTag)heightmap.get("motionBlocking");
/* 40 */     this.motionBlocking = (IntSet)new IntOpenHashSet(motionBlocking.getValue());
/*    */     
/* 42 */     if (Via.getConfig().isNonFullBlockLightFix()) {
/* 43 */       IntArrayTag nonFullBlocks = (IntArrayTag)heightmap.get("nonFullBlocks");
/* 44 */       this.nonFullBlocks = (IntSet)new IntOpenHashSet(nonFullBlocks.getValue());
/*    */     } 
/*    */   }
/*    */   
/*    */   public IntSet getMotionBlocking() {
/* 49 */     return this.motionBlocking;
/*    */   }
/*    */   
/*    */   public IntSet getNonFullBlocks() {
/* 53 */     return this.nonFullBlocks;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_14to1_13_2\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */