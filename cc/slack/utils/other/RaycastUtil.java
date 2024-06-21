/*    */ package cc.slack.utils.other;
/*    */ 
/*    */ import cc.slack.utils.client.mc;
/*    */ import cc.slack.utils.player.RotationUtil;
/*    */ import com.google.common.base.Predicates;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.EntitySelectors;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ 
/*    */ public class RaycastUtil
/*    */ {
/*    */   public static EntityLivingBase rayCast(double range) {
/* 18 */     return rayCast(range, RotationUtil.clientRotation);
/*    */   }
/*    */   
/*    */   public static EntityLivingBase rayCast(double range, float[] rotations) {
/* 22 */     Vec3 eyes = mc.getPlayer().getPositionEyes((mc.getTimer()).renderPartialTicks);
/* 23 */     Vec3 look = mc.getPlayer().getVectorForRotation(rotations[1], rotations[0]);
/* 24 */     Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
/* 25 */     List<Entity> entities = mc.getWorld().getEntitiesInAABBexcluding((Entity)mc.getPlayer(), mc.getPlayer().getEntityBoundingBox().addCoord(look.xCoord * range, look.yCoord * range, look.zCoord * range)
/* 26 */         .expand(1.0D, 1.0D, 1.0D), 
/* 27 */         Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
/* 28 */     EntityLivingBase raycastedEntity = null;
/*    */     
/* 30 */     for (Entity ent : entities) {
/* 31 */       if (!(ent instanceof EntityLivingBase)) return null; 
/* 32 */       EntityLivingBase entity = (EntityLivingBase)ent;
/* 33 */       if (entity == mc.getPlayer())
/* 34 */         continue;  float borderSize = entity.getCollisionBorderSize();
/* 35 */       AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
/* 36 */       MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);
/*    */       
/* 38 */       if (axisalignedbb.isVecInside(eyes)) {
/* 39 */         if (range >= 0.0D) {
/* 40 */           raycastedEntity = entity;
/* 41 */           range = 0.0D;
/*    */         }  continue;
/* 43 */       }  if (movingobjectposition != null) {
/* 44 */         double distance = eyes.distanceTo(movingobjectposition.hitVec);
/*    */         
/* 46 */         if (distance < range || range == 0.0D) {
/* 47 */           if (entity == entity.ridingEntity) {
/* 48 */             if (range == 0.0D) raycastedEntity = entity;  continue;
/*    */           } 
/* 50 */           raycastedEntity = entity;
/* 51 */           range = distance;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 57 */     return raycastedEntity;
/*    */   }
/*    */   
/*    */   public static Vec3 rayCastHitVec(EntityLivingBase entity, double range, float[] rotations) {
/* 61 */     Vec3 eyes = mc.getPlayer().getPositionEyes((mc.getTimer()).renderPartialTicks);
/* 62 */     Vec3 look = mc.getPlayer().getVectorForRotation(rotations[1], rotations[0]);
/* 63 */     Vec3 vec = eyes.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
/*    */     
/* 65 */     float borderSize = entity.getCollisionBorderSize();
/* 66 */     AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
/* 67 */     MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyes, vec);
/*    */     
/* 69 */     if (movingobjectposition == null) return null;
/*    */     
/* 71 */     return movingobjectposition.hitVec;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slac\\utils\other\RaycastUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */