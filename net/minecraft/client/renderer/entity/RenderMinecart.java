/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelMinecart;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
/*  16 */   private static final ResourceLocation minecartTextures = new ResourceLocation("textures/entity/minecart.png");
/*     */ 
/*     */   
/*  19 */   protected ModelBase modelMinecart = (ModelBase)new ModelMinecart();
/*     */ 
/*     */   
/*     */   public RenderMinecart(RenderManager renderManagerIn) {
/*  23 */     super(renderManagerIn);
/*  24 */     this.shadowSize = 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  37 */     GlStateManager.pushMatrix();
/*  38 */     bindEntityTexture(entity);
/*  39 */     long i = entity.getEntityId() * 493286711L;
/*  40 */     i = i * i * 4392167121L + i * 98761L;
/*  41 */     float f = (((float)(i >> 16L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  42 */     float f1 = (((float)(i >> 20L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  43 */     float f2 = (((float)(i >> 24L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  44 */     GlStateManager.translate(f, f1, f2);
/*  45 */     double d0 = ((EntityMinecart)entity).lastTickPosX + (((EntityMinecart)entity).posX - ((EntityMinecart)entity).lastTickPosX) * partialTicks;
/*  46 */     double d1 = ((EntityMinecart)entity).lastTickPosY + (((EntityMinecart)entity).posY - ((EntityMinecart)entity).lastTickPosY) * partialTicks;
/*  47 */     double d2 = ((EntityMinecart)entity).lastTickPosZ + (((EntityMinecart)entity).posZ - ((EntityMinecart)entity).lastTickPosZ) * partialTicks;
/*  48 */     double d3 = 0.30000001192092896D;
/*  49 */     Vec3 vec3 = entity.func_70489_a(d0, d1, d2);
/*  50 */     float f3 = ((EntityMinecart)entity).prevRotationPitch + (((EntityMinecart)entity).rotationPitch - ((EntityMinecart)entity).prevRotationPitch) * partialTicks;
/*     */     
/*  52 */     if (vec3 != null) {
/*     */       
/*  54 */       Vec3 vec31 = entity.func_70495_a(d0, d1, d2, d3);
/*  55 */       Vec3 vec32 = entity.func_70495_a(d0, d1, d2, -d3);
/*     */       
/*  57 */       if (vec31 == null)
/*     */       {
/*  59 */         vec31 = vec3;
/*     */       }
/*     */       
/*  62 */       if (vec32 == null)
/*     */       {
/*  64 */         vec32 = vec3;
/*     */       }
/*     */       
/*  67 */       x += vec3.xCoord - d0;
/*  68 */       y += (vec31.yCoord + vec32.yCoord) / 2.0D - d1;
/*  69 */       z += vec3.zCoord - d2;
/*  70 */       Vec3 vec33 = vec32.addVector(-vec31.xCoord, -vec31.yCoord, -vec31.zCoord);
/*     */       
/*  72 */       if (vec33.lengthVector() != 0.0D) {
/*     */         
/*  74 */         vec33 = vec33.normalize();
/*  75 */         entityYaw = (float)(Math.atan2(vec33.zCoord, vec33.xCoord) * 180.0D / Math.PI);
/*  76 */         f3 = (float)(Math.atan(vec33.yCoord) * 73.0D);
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     GlStateManager.translate((float)x, (float)y + 0.375F, (float)z);
/*  81 */     GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
/*  82 */     GlStateManager.rotate(-f3, 0.0F, 0.0F, 1.0F);
/*  83 */     float f5 = entity.getRollingAmplitude() - partialTicks;
/*  84 */     float f6 = entity.getDamage() - partialTicks;
/*     */     
/*  86 */     if (f6 < 0.0F)
/*     */     {
/*  88 */       f6 = 0.0F;
/*     */     }
/*     */     
/*  91 */     if (f5 > 0.0F)
/*     */     {
/*  93 */       GlStateManager.rotate(MathHelper.sin(f5) * f5 * f6 / 10.0F * entity.getRollingDirection(), 1.0F, 0.0F, 0.0F);
/*     */     }
/*     */     
/*  96 */     int j = entity.getDisplayTileOffset();
/*  97 */     IBlockState iblockstate = entity.getDisplayTile();
/*     */     
/*  99 */     if (iblockstate.getBlock().getRenderType() != -1) {
/*     */       
/* 101 */       GlStateManager.pushMatrix();
/* 102 */       bindTexture(TextureMap.locationBlocksTexture);
/* 103 */       float f4 = 0.75F;
/* 104 */       GlStateManager.scale(f4, f4, f4);
/* 105 */       GlStateManager.translate(-0.5F, (j - 8) / 16.0F, 0.5F);
/* 106 */       func_180560_a(entity, partialTicks, iblockstate);
/* 107 */       GlStateManager.popMatrix();
/* 108 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 109 */       bindEntityTexture(entity);
/*     */     } 
/*     */     
/* 112 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 113 */     this.modelMinecart.render((Entity)entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 114 */     GlStateManager.popMatrix();
/* 115 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(T entity) {
/* 123 */     return minecartTextures;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_180560_a(T minecart, float partialTicks, IBlockState state) {
/* 128 */     GlStateManager.pushMatrix();
/* 129 */     Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlockBrightness(state, minecart.getBrightness(partialTicks));
/* 130 */     GlStateManager.popMatrix();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\client\renderer\entity\RenderMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */