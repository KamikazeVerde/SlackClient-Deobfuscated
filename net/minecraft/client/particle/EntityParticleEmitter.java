/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityParticleEmitter
/*    */   extends EntityFX
/*    */ {
/*    */   private Entity attachedEntity;
/*    */   private int age;
/*    */   private int lifetime;
/*    */   private EnumParticleTypes particleTypes;
/*    */   
/*    */   public EntityParticleEmitter(World worldIn, Entity p_i46279_2_, EnumParticleTypes particleTypesIn) {
/* 17 */     super(worldIn, p_i46279_2_.posX, (p_i46279_2_.getEntityBoundingBox()).minY + (p_i46279_2_.height / 2.0F), p_i46279_2_.posZ, p_i46279_2_.motionX, p_i46279_2_.motionY, p_i46279_2_.motionZ);
/* 18 */     this.attachedEntity = p_i46279_2_;
/* 19 */     this.lifetime = 3;
/* 20 */     this.particleTypes = particleTypesIn;
/* 21 */     onUpdate();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 38 */     for (int i = 0; i < 16; i++) {
/*    */       
/* 40 */       double d0 = (this.rand.nextFloat() * 2.0F - 1.0F);
/* 41 */       double d1 = (this.rand.nextFloat() * 2.0F - 1.0F);
/* 42 */       double d2 = (this.rand.nextFloat() * 2.0F - 1.0F);
/*    */       
/* 44 */       if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D) {
/*    */         
/* 46 */         double d3 = this.attachedEntity.posX + d0 * this.attachedEntity.width / 4.0D;
/* 47 */         double d4 = (this.attachedEntity.getEntityBoundingBox()).minY + (this.attachedEntity.height / 2.0F) + d1 * this.attachedEntity.height / 4.0D;
/* 48 */         double d5 = this.attachedEntity.posZ + d2 * this.attachedEntity.width / 4.0D;
/* 49 */         this.worldObj.spawnParticle(this.particleTypes, false, d3, d4, d5, d0, d1 + 0.2D, d2, new int[0]);
/*    */       } 
/*    */     } 
/*    */     
/* 53 */     this.age++;
/*    */     
/* 55 */     if (this.age >= this.lifetime)
/*    */     {
/* 57 */       setDead();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 63 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityParticleEmitter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */