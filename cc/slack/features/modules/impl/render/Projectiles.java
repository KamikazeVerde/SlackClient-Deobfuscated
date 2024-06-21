/*     */ package cc.slack.features.modules.impl.render;
/*     */ import cc.slack.events.impl.render.RenderEvent;
/*     */ import cc.slack.features.modules.api.Category;
/*     */ import cc.slack.features.modules.api.Module;
/*     */ import cc.slack.features.modules.api.ModuleInfo;
/*     */ import cc.slack.features.modules.api.settings.Value;
/*     */ import cc.slack.features.modules.api.settings.impl.NumberValue;
/*     */ import cc.slack.utils.client.mc;
/*     */ import io.github.nevalackin.radbus.Listen;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ @ModuleInfo(name = "Projectiles", category = Category.RENDER)
/*     */ public class Projectiles extends Module {
/*  30 */   private NumberValue<Float> color = new NumberValue("Color (H/S/B)", Float.valueOf(190.0F), Float.valueOf(0.0F), Float.valueOf(350.0F), Float.valueOf(10.0F));
/*  31 */   private final ArrayList<Vec3> positions = new ArrayList<>();
/*     */   
/*     */   public Projectiles() {
/*  34 */     addSettings(new Value[] { (Value)this.color });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  39 */     super.onDisable();
/*  40 */     this.positions.clear();
/*     */   }
/*     */   
/*     */   @Listen
/*     */   public void onRender(RenderEvent e) {
/*  45 */     if (e.state != RenderEvent.State.RENDER_3D) {
/*     */       return;
/*     */     }
/*  48 */     this.positions.clear();
/*  49 */     ItemStack itemStack = mc.getPlayer().getCurrentEquippedItem();
/*  50 */     MovingObjectPosition m = null;
/*  51 */     if (itemStack != null && (itemStack.getItem() instanceof net.minecraft.item.ItemSnowball || itemStack.getItem() instanceof net.minecraft.item.ItemEgg || itemStack
/*  52 */       .getItem() instanceof net.minecraft.item.ItemBow || itemStack.getItem() instanceof net.minecraft.item.ItemEnderPearl)) {
/*  53 */       EntityPlayerSP entityPlayerSP = mc.getPlayer();
/*     */       
/*  55 */       float rotationYaw = ((EntityLivingBase)entityPlayerSP).prevRotationYaw + (((EntityLivingBase)entityPlayerSP).rotationYaw - ((EntityLivingBase)entityPlayerSP).prevRotationYaw) * (mc.getTimer()).renderPartialTicks;
/*     */       
/*  57 */       float rotationPitch = ((EntityLivingBase)entityPlayerSP).prevRotationPitch + (((EntityLivingBase)entityPlayerSP).rotationPitch - ((EntityLivingBase)entityPlayerSP).prevRotationPitch) * (mc.getTimer()).renderPartialTicks;
/*  58 */       double posX = ((EntityLivingBase)entityPlayerSP).lastTickPosX + (((EntityLivingBase)entityPlayerSP).posX - ((EntityLivingBase)entityPlayerSP).lastTickPosX) * (mc.getTimer()).renderPartialTicks;
/*     */       
/*  60 */       double posY = ((EntityLivingBase)entityPlayerSP).lastTickPosY + entityPlayerSP.getEyeHeight() + (((EntityLivingBase)entityPlayerSP).posY - ((EntityLivingBase)entityPlayerSP).lastTickPosY) * (mc.getTimer()).renderPartialTicks;
/*  61 */       double posZ = ((EntityLivingBase)entityPlayerSP).lastTickPosZ + (((EntityLivingBase)entityPlayerSP).posZ - ((EntityLivingBase)entityPlayerSP).lastTickPosZ) * (mc.getTimer()).renderPartialTicks;
/*  62 */       posX -= (MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  63 */       posY -= 0.10000000149011612D;
/*  64 */       posZ -= (MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * 0.16F);
/*  65 */       float multipicator = 0.4F;
/*  66 */       if (itemStack.getItem() instanceof net.minecraft.item.ItemBow) {
/*  67 */         multipicator = 1.0F;
/*     */       }
/*     */       
/*  70 */       double motionX = (-MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * multipicator);
/*     */       
/*  72 */       double motionZ = (MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * multipicator);
/*  73 */       double motionY = (-MathHelper.sin(rotationPitch / 180.0F * 3.1415927F) * multipicator);
/*  74 */       double x = motionX;
/*  75 */       double y = motionY;
/*  76 */       double z = motionZ;
/*  77 */       float inaccuracy = 0.0F;
/*  78 */       float velocity = 1.5F;
/*  79 */       if (itemStack.getItem() instanceof net.minecraft.item.ItemBow) {
/*  80 */         int i = mc.getPlayer().getItemInUseDuration() - mc.getPlayer().getItemInUseCount();
/*  81 */         float f = i / 20.0F;
/*  82 */         f = (f * f + f * 2.0F) / 3.0F;
/*  83 */         if (f < 0.1D) {
/*     */           return;
/*     */         }
/*  86 */         if (f > 1.0F) {
/*  87 */           f = 1.0F;
/*     */         }
/*  89 */         velocity = f * 2.0F * 1.5F;
/*     */       } 
/*  91 */       Random rand = new Random();
/*  92 */       float ff = MathHelper.sqrt_double(x * x + y * y + z * z);
/*  93 */       x /= ff;
/*  94 */       y /= ff;
/*  95 */       z /= ff;
/*  96 */       x += rand.nextGaussian() * 0.007499999832361937D * 0.0D;
/*  97 */       y += rand.nextGaussian() * 0.007499999832361937D * 0.0D;
/*  98 */       z += rand.nextGaussian() * 0.007499999832361937D * 0.0D;
/*  99 */       x *= velocity;
/* 100 */       y *= velocity;
/* 101 */       z *= velocity;
/* 102 */       motionX = x;
/* 103 */       motionY = y;
/* 104 */       motionZ = z;
/*     */       
/* 106 */       float prevRotationYaw = (float)(MathHelper.func_181159_b(x, z) * 180.0D / Math.PI);
/*     */       
/* 108 */       float prevRotationPitch = (float)(MathHelper.func_181159_b(y, 
/* 109 */           MathHelper.sqrt_double(x * x + z * z)) * 180.0D / Math.PI);
/* 110 */       boolean b = true;
/* 111 */       int ticksInAir = 0;
/* 112 */       while (b) {
/* 113 */         if (ticksInAir > 300) {
/* 114 */           b = false;
/*     */         }
/* 116 */         ticksInAir++;
/* 117 */         Vec3 vec3 = new Vec3(posX, posY, posZ);
/* 118 */         Vec3 vec4 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
/* 119 */         MovingObjectPosition movingobjectposition = mc.getWorld().rayTraceBlocks(vec3, vec4);
/* 120 */         vec3 = new Vec3(posX, posY, posZ);
/* 121 */         vec4 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
/* 122 */         if (movingobjectposition != null) {
/* 123 */           vec4 = new Vec3(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
/*     */         }
/*     */         
/* 126 */         for (Entity entity : (mc.getWorld()).loadedEntityList) {
/* 127 */           if (entity != mc.getPlayer() && entity instanceof EntityLivingBase) {
/* 128 */             float f2 = 0.3F;
/* 129 */             AxisAlignedBB localAxisAlignedBB = entity.getEntityBoundingBox().expand(0.30000001192092896D, 0.30000001192092896D, 0.30000001192092896D);
/*     */             
/* 131 */             MovingObjectPosition localMovingObjectPosition = localAxisAlignedBB.calculateIntercept(vec3, vec4);
/* 132 */             if (localMovingObjectPosition != null) {
/* 133 */               movingobjectposition = localMovingObjectPosition;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 139 */         if (movingobjectposition != null) {
/* 140 */           b = false;
/*     */         }
/* 142 */         m = movingobjectposition;
/* 143 */         posX += motionX;
/* 144 */         posY += motionY;
/* 145 */         posZ += motionZ;
/* 146 */         float f3 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
/* 147 */         rotationYaw = (float)(MathHelper.func_181159_b(motionX, motionZ) * 180.0D / Math.PI);
/* 148 */         rotationPitch = (float)(MathHelper.func_181159_b(motionY, f3) * 180.0D / Math.PI);
/*     */         
/* 150 */         for (; rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F);
/*     */         
/* 152 */         while (rotationPitch - prevRotationPitch >= 180.0F) {
/* 153 */           prevRotationPitch += 360.0F;
/*     */         }
/* 155 */         while (rotationYaw - prevRotationYaw < -180.0F) {
/* 156 */           prevRotationYaw -= 360.0F;
/*     */         }
/* 158 */         while (rotationYaw - prevRotationYaw >= 180.0F) {
/* 159 */           prevRotationYaw += 360.0F;
/*     */         }
/* 161 */         float f4 = 0.99F;
/* 162 */         float f5 = 0.03F;
/* 163 */         if (itemStack.getItem() instanceof net.minecraft.item.ItemBow) {
/* 164 */           f5 = 0.05F;
/*     */         }
/* 166 */         motionX *= 0.9900000095367432D;
/* 167 */         motionY *= 0.9900000095367432D;
/* 168 */         motionZ *= 0.9900000095367432D;
/* 169 */         motionY -= f5;
/* 170 */         this.positions.add(new Vec3(posX, posY, posZ));
/*     */       } 
/* 172 */       if (this.positions.size() > 1) {
/* 173 */         Color col = Color.getHSBColor(((Float)this.color.getValue()).floatValue() % 360.0F / 360.0F, 1.0F, 1.0F);
/* 174 */         GL11.glEnable(3042);
/* 175 */         GL11.glBlendFunc(770, 771);
/* 176 */         GL11.glEnable(2848);
/* 177 */         GL11.glDisable(3553);
/* 178 */         GlStateManager.disableCull();
/* 179 */         GL11.glDepthMask(false);
/* 180 */         GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col
/* 181 */             .getBlue() / 255.0F, 0.7F);
/* 182 */         GL11.glLineWidth(3.0F);
/* 183 */         Tessellator tessellator = Tessellator.getInstance();
/* 184 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 185 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 186 */         worldrenderer.begin(3, DefaultVertexFormats.field_181705_e);
/* 187 */         for (Vec3 vec5 : this.positions) {
/* 188 */           worldrenderer.pos((float)vec5.xCoord - (mc.getRenderManager()).renderPosX, (float)vec5.yCoord - 
/* 189 */               (mc.getRenderManager()).renderPosY, (float)vec5.zCoord - 
/* 190 */               (mc.getRenderManager()).renderPosZ).endVertex();
/*     */         }
/* 192 */         tessellator.draw();
/* 193 */         if (m != null) {
/* 194 */           GL11.glColor4f(col.getRed() / 255.0F, col.getGreen() / 255.0F, col
/* 195 */               .getBlue() / 255.0F, 0.3F);
/* 196 */           Vec3 hitVec = m.hitVec;
/* 197 */           EnumFacing enumFacing1 = m.sideHit;
/* 198 */           float minX = (float)(hitVec.xCoord - (mc.getRenderManager()).renderPosX);
/* 199 */           float maxX = (float)(hitVec.xCoord - (mc.getRenderManager()).renderPosX);
/* 200 */           float minY = (float)(hitVec.yCoord - (mc.getRenderManager()).renderPosY);
/* 201 */           float maxY = (float)(hitVec.yCoord - (mc.getRenderManager()).renderPosY);
/* 202 */           float minZ = (float)(hitVec.zCoord - (mc.getRenderManager()).renderPosZ);
/* 203 */           float maxZ = (float)(hitVec.zCoord - (mc.getRenderManager()).renderPosZ);
/* 204 */           if (enumFacing1 == EnumFacing.SOUTH) {
/* 205 */             minX -= 0.4F;
/* 206 */             maxX += 0.4F;
/* 207 */             minY -= 0.4F;
/* 208 */             maxY += 0.4F;
/* 209 */             maxZ += 0.02F;
/* 210 */             minZ += 0.05F;
/* 211 */           } else if (enumFacing1 == EnumFacing.NORTH) {
/* 212 */             minX -= 0.4F;
/* 213 */             maxX += 0.4F;
/* 214 */             minY -= 0.4F;
/* 215 */             maxY += 0.4F;
/* 216 */             maxZ -= 0.02F;
/* 217 */             minZ -= 0.05F;
/* 218 */           } else if (enumFacing1 == EnumFacing.EAST) {
/* 219 */             maxX += 0.02F;
/* 220 */             minX += 0.05F;
/* 221 */             minY -= 0.4F;
/* 222 */             maxY += 0.4F;
/* 223 */             minZ -= 0.4F;
/* 224 */             maxZ += 0.4F;
/* 225 */           } else if (enumFacing1 == EnumFacing.WEST) {
/* 226 */             maxX -= 0.02F;
/* 227 */             minX -= 0.05F;
/* 228 */             minY -= 0.4F;
/* 229 */             maxY += 0.4F;
/* 230 */             minZ -= 0.4F;
/* 231 */             maxZ += 0.4F;
/* 232 */           } else if (enumFacing1 == EnumFacing.UP) {
/* 233 */             minX -= 0.4F;
/* 234 */             maxX += 0.4F;
/* 235 */             maxY += 0.02F;
/* 236 */             minY += 0.05F;
/* 237 */             minZ -= 0.4F;
/* 238 */             maxZ += 0.4F;
/* 239 */           } else if (enumFacing1 == EnumFacing.DOWN) {
/* 240 */             minX -= 0.4F;
/* 241 */             maxX += 0.4F;
/* 242 */             maxY -= 0.02F;
/* 243 */             minY -= 0.05F;
/* 244 */             minZ -= 0.4F;
/* 245 */             maxZ += 0.4F;
/*     */           } 
/* 247 */           worldrenderer.begin(7, DefaultVertexFormats.field_181705_e);
/* 248 */           worldrenderer.pos(minX, minY, minZ).endVertex();
/* 249 */           worldrenderer.pos(minX, minY, maxZ).endVertex();
/* 250 */           worldrenderer.pos(minX, maxY, maxZ).endVertex();
/* 251 */           worldrenderer.pos(minX, maxY, minZ).endVertex();
/* 252 */           worldrenderer.pos(minX, minY, maxZ).endVertex();
/* 253 */           worldrenderer.pos(maxX, minY, maxZ).endVertex();
/* 254 */           worldrenderer.pos(maxX, maxY, maxZ).endVertex();
/* 255 */           worldrenderer.pos(minX, maxY, maxZ).endVertex();
/* 256 */           worldrenderer.pos(maxX, minY, maxZ).endVertex();
/* 257 */           worldrenderer.pos(maxX, minY, minZ).endVertex();
/* 258 */           worldrenderer.pos(maxX, maxY, minZ).endVertex();
/* 259 */           worldrenderer.pos(maxX, maxY, maxZ).endVertex();
/* 260 */           worldrenderer.pos(maxX, minY, minZ).endVertex();
/* 261 */           worldrenderer.pos(minX, minY, minZ).endVertex();
/* 262 */           worldrenderer.pos(minX, maxY, minZ).endVertex();
/* 263 */           worldrenderer.pos(maxX, maxY, minZ).endVertex();
/* 264 */           worldrenderer.pos(minX, minY, minZ).endVertex();
/* 265 */           worldrenderer.pos(minX, minY, maxZ).endVertex();
/* 266 */           worldrenderer.pos(maxX, minY, maxZ).endVertex();
/* 267 */           worldrenderer.pos(maxX, minY, minZ).endVertex();
/* 268 */           worldrenderer.pos(minX, maxY, minZ).endVertex();
/* 269 */           worldrenderer.pos(minX, maxY, maxZ).endVertex();
/* 270 */           worldrenderer.pos(maxX, maxY, maxZ).endVertex();
/* 271 */           worldrenderer.pos(maxX, maxY, minZ).endVertex();
/* 272 */           worldrenderer.endVertex();
/* 273 */           tessellator.draw();
/* 274 */           GL11.glLineWidth(2.0F);
/* 275 */           worldrenderer.begin(3, DefaultVertexFormats.field_181705_e);
/* 276 */           worldrenderer.pos(minX, minY, minZ).endVertex();
/* 277 */           worldrenderer.pos(minX, minY, maxZ).endVertex();
/* 278 */           worldrenderer.pos(minX, maxY, maxZ).endVertex();
/* 279 */           worldrenderer.pos(minX, maxY, minZ).endVertex();
/* 280 */           worldrenderer.pos(minX, minY, minZ).endVertex();
/* 281 */           worldrenderer.pos(maxX, minY, minZ).endVertex();
/* 282 */           worldrenderer.pos(maxX, maxY, minZ).endVertex();
/* 283 */           worldrenderer.pos(maxX, maxY, maxZ).endVertex();
/* 284 */           worldrenderer.pos(maxX, minY, maxZ).endVertex();
/* 285 */           worldrenderer.pos(maxX, minY, minZ).endVertex();
/* 286 */           worldrenderer.pos(maxX, minY, maxZ).endVertex();
/* 287 */           worldrenderer.pos(minX, minY, maxZ).endVertex();
/* 288 */           worldrenderer.pos(minX, maxY, maxZ).endVertex();
/* 289 */           worldrenderer.pos(maxX, maxY, maxZ).endVertex();
/* 290 */           worldrenderer.pos(maxX, maxY, minZ).endVertex();
/* 291 */           worldrenderer.pos(minX, maxY, minZ).endVertex();
/* 292 */           worldrenderer.endVertex();
/* 293 */           tessellator.draw();
/*     */         } 
/* 295 */         GL11.glLineWidth(1.0F);
/* 296 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 297 */         GL11.glDepthMask(true);
/* 298 */         GlStateManager.enableCull();
/* 299 */         GL11.glEnable(3553);
/* 300 */         GL11.glEnable(2929);
/* 301 */         GL11.glDisable(3042);
/* 302 */         GL11.glBlendFunc(770, 771);
/* 303 */         GL11.glDisable(2848);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\modules\impl\render\Projectiles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */