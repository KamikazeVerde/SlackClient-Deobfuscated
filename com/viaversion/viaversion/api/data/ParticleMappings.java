/*    */ package com.viaversion.viaversion.api.data;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
/*    */ import com.viaversion.viaversion.libs.fastutil.ints.IntList;
/*    */ import java.util.List;
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
/*    */ public class ParticleMappings
/*    */   extends FullMappingsBase
/*    */ {
/* 30 */   private final IntList itemParticleIds = (IntList)new IntArrayList(4);
/* 31 */   private final IntList blockParticleIds = (IntList)new IntArrayList(4);
/*    */   
/*    */   public ParticleMappings(List<String> unmappedIdentifiers, List<String> mappedIdentifiers, Mappings mappings) {
/* 34 */     super(unmappedIdentifiers, mappedIdentifiers, mappings);
/* 35 */     addBlockParticle("block");
/* 36 */     addBlockParticle("falling_dust");
/* 37 */     addBlockParticle("block_marker");
/* 38 */     addItemParticle("item");
/*    */   }
/*    */   
/*    */   public boolean addItemParticle(String identifier) {
/* 42 */     int id = id(identifier);
/* 43 */     return (id != -1 && this.itemParticleIds.add(id));
/*    */   }
/*    */   
/*    */   public boolean addBlockParticle(String identifier) {
/* 47 */     int id = id(identifier);
/* 48 */     return (id != -1 && this.blockParticleIds.add(id));
/*    */   }
/*    */   
/*    */   public boolean isBlockParticle(int id) {
/* 52 */     return this.blockParticleIds.contains(id);
/*    */   }
/*    */   
/*    */   public boolean isItemParticle(int id) {
/* 56 */     return this.itemParticleIds.contains(id);
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getBlockId() {
/* 61 */     return id("block");
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getFallingDustId() {
/* 66 */     return id("falling_dust");
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public int getItemId() {
/* 71 */     return id("item");
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\data\ParticleMappings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */