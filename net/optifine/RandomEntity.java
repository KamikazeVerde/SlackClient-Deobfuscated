/*    */ package net.optifine;
/*    */ 
/*    */ import java.util.UUID;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class RandomEntity
/*    */   implements IRandomEntity
/*    */ {
/*    */   private Entity entity;
/*    */   
/*    */   public int getId() {
/* 15 */     UUID uuid = this.entity.getUniqueID();
/* 16 */     long i = uuid.getLeastSignificantBits();
/* 17 */     int j = (int)(i & 0x7FFFFFFFL);
/* 18 */     return j;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSpawnPosition() {
/* 23 */     return (this.entity.getDataWatcher()).spawnPosition;
/*    */   }
/*    */ 
/*    */   
/*    */   public BiomeGenBase getSpawnBiome() {
/* 28 */     return (this.entity.getDataWatcher()).spawnBiome;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 33 */     return this.entity.hasCustomName() ? this.entity.getCustomNameTag() : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHealth() {
/* 38 */     if (!(this.entity instanceof EntityLiving))
/*    */     {
/* 40 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 44 */     EntityLiving entityliving = (EntityLiving)this.entity;
/* 45 */     return (int)entityliving.getHealth();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMaxHealth() {
/* 51 */     if (!(this.entity instanceof EntityLiving))
/*    */     {
/* 53 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 57 */     EntityLiving entityliving = (EntityLiving)this.entity;
/* 58 */     return (int)entityliving.getMaxHealth();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getEntity() {
/* 64 */     return this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEntity(Entity entity) {
/* 69 */     this.entity = entity;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\RandomEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */