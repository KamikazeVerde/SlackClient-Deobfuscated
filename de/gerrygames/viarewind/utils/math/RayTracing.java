/*    */ package de.gerrygames.viarewind.utils.math;
/*    */ 
/*    */ public class RayTracing
/*    */ {
/*    */   public static Vector3d trace(Ray3d ray, AABB aabb, double distance) {
/*  6 */     Vector3d invDir = new Vector3d(1.0D / ray.dir.x, 1.0D / ray.dir.y, 1.0D / ray.dir.z);
/*    */     
/*  8 */     boolean signDirX = (invDir.x < 0.0D);
/*  9 */     boolean signDirY = (invDir.y < 0.0D);
/* 10 */     boolean signDirZ = (invDir.z < 0.0D);
/*    */     
/* 12 */     Vector3d bbox = signDirX ? aabb.max : aabb.min;
/* 13 */     double tmin = (bbox.x - ray.start.x) * invDir.x;
/* 14 */     bbox = signDirX ? aabb.min : aabb.max;
/* 15 */     double tmax = (bbox.x - ray.start.x) * invDir.x;
/* 16 */     bbox = signDirY ? aabb.max : aabb.min;
/* 17 */     double tymin = (bbox.y - ray.start.y) * invDir.y;
/* 18 */     bbox = signDirY ? aabb.min : aabb.max;
/* 19 */     double tymax = (bbox.y - ray.start.y) * invDir.y;
/*    */     
/* 21 */     if (tmin > tymax || tymin > tmax) return null;
/*    */     
/* 23 */     if (tymin > tmin) tmin = tymin;
/*    */     
/* 25 */     if (tymax < tmax) tmax = tymax;
/*    */     
/* 27 */     bbox = signDirZ ? aabb.max : aabb.min;
/* 28 */     double tzmin = (bbox.z - ray.start.z) * invDir.z;
/* 29 */     bbox = signDirZ ? aabb.min : aabb.max;
/* 30 */     double tzmax = (bbox.z - ray.start.z) * invDir.z;
/*    */     
/* 32 */     if (tmin > tzmax || tzmin > tmax) return null;
/*    */     
/* 34 */     if (tzmin > tmin) tmin = tzmin;
/*    */     
/* 36 */     if (tzmax < tmax) tmax = tzmax;
/*    */     
/* 38 */     return (tmin <= distance && tmax > 0.0D) ? ray.start.clone().add(ray.dir.clone().normalize().multiply(tmin)) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\de\gerrygames\viarewin\\utils\math\RayTracing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */