/*    */ package com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.data;
/*    */ 
/*    */ import com.viaversion.viaversion.api.data.MappingDataBase;
/*    */ import com.viaversion.viaversion.api.data.MappingDataLoader;
/*    */ import com.viaversion.viaversion.api.minecraft.nbt.BinaryTagIO;
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
/*    */ import java.io.IOException;
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
/*    */ public final class MappingData
/*    */   extends MappingDataBase
/*    */ {
/*    */   private CompoundTag damageTypesRegistry;
/*    */   
/*    */   public MappingData() {
/* 31 */     super("1.19.3", "1.19.4");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void loadExtras(CompoundTag data) {
/*    */     try {
/* 37 */       this.damageTypesRegistry = BinaryTagIO.readInputStream(MappingDataLoader.getResource("damage-types-1.19.4.nbt"));
/* 38 */     } catch (IOException e) {
/* 39 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public CompoundTag damageTypesRegistry() {
/* 44 */     return this.damageTypesRegistry.clone();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_19_4to1_19_3\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */