/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.world.World;
/*    */ import net.optifine.shaders.Program;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class EntityPickupFX
/*    */   extends EntityFX {
/*    */   private Entity field_174840_a;
/*    */   private Entity field_174843_ax;
/*    */   private int age;
/*    */   private int maxAge;
/*    */   private float field_174841_aA;
/* 21 */   private RenderManager field_174842_aB = Minecraft.getMinecraft().getRenderManager();
/*    */ 
/*    */   
/*    */   public EntityPickupFX(World worldIn, Entity p_i1233_2_, Entity p_i1233_3_, float p_i1233_4_) {
/* 25 */     super(worldIn, p_i1233_2_.posX, p_i1233_2_.posY, p_i1233_2_.posZ, p_i1233_2_.motionX, p_i1233_2_.motionY, p_i1233_2_.motionZ);
/* 26 */     this.field_174840_a = p_i1233_2_;
/* 27 */     this.field_174843_ax = p_i1233_3_;
/* 28 */     this.maxAge = 3;
/* 29 */     this.field_174841_aA = p_i1233_4_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
/* 39 */     Program program = null;
/*    */     
/* 41 */     if (Config.isShaders()) {
/*    */       
/* 43 */       program = Shaders.activeProgram;
/* 44 */       Shaders.nextEntity(this.field_174840_a);
/*    */     } 
/*    */     
/* 47 */     float f = (this.age + partialTicks) / this.maxAge;
/* 48 */     f *= f;
/* 49 */     double d0 = this.field_174840_a.posX;
/* 50 */     double d1 = this.field_174840_a.posY;
/* 51 */     double d2 = this.field_174840_a.posZ;
/* 52 */     double d3 = this.field_174843_ax.lastTickPosX + (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * partialTicks;
/* 53 */     double d4 = this.field_174843_ax.lastTickPosY + (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * partialTicks + this.field_174841_aA;
/* 54 */     double d5 = this.field_174843_ax.lastTickPosZ + (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * partialTicks;
/* 55 */     double d6 = d0 + (d3 - d0) * f;
/* 56 */     double d7 = d1 + (d4 - d1) * f;
/* 57 */     double d8 = d2 + (d5 - d2) * f;
/* 58 */     int i = getBrightnessForRender(partialTicks);
/* 59 */     int j = i % 65536;
/* 60 */     int k = i / 65536;
/* 61 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
/* 62 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 63 */     d6 -= interpPosX;
/* 64 */     d7 -= interpPosY;
/* 65 */     d8 -= interpPosZ;
/* 66 */     this.field_174842_aB.renderEntityWithPosYaw(this.field_174840_a, (float)d6, (float)d7, (float)d8, this.field_174840_a.rotationYaw, partialTicks);
/*    */     
/* 68 */     if (Config.isShaders()) {
/*    */       
/* 70 */       Shaders.setEntityId(null);
/* 71 */       Shaders.useProgram(program);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 80 */     this.age++;
/*    */     
/* 82 */     if (this.age == this.maxAge)
/*    */     {
/* 84 */       setDead();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 90 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\particle\EntityPickupFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */