/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.pathfinder.NodeProcessor;
/*     */ 
/*     */ 
/*     */ public class PathFinder
/*     */ {
/*  11 */   private Path path = new Path();
/*     */ 
/*     */   
/*  14 */   private PathPoint[] pathOptions = new PathPoint[32];
/*     */   
/*     */   private NodeProcessor nodeProcessor;
/*     */   
/*     */   public PathFinder(NodeProcessor nodeProcessorIn) {
/*  19 */     this.nodeProcessor = nodeProcessorIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity createEntityPathTo(IBlockAccess blockaccess, Entity entityFrom, Entity entityTo, float dist) {
/*  27 */     return createEntityPathTo(blockaccess, entityFrom, entityTo.posX, (entityTo.getEntityBoundingBox()).minY, entityTo.posZ, dist);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathEntity createEntityPathTo(IBlockAccess blockaccess, Entity entityIn, BlockPos targetPos, float dist) {
/*  35 */     return createEntityPathTo(blockaccess, entityIn, (targetPos.getX() + 0.5F), (targetPos.getY() + 0.5F), (targetPos.getZ() + 0.5F), dist);
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
/*     */   private PathEntity createEntityPathTo(IBlockAccess blockaccess, Entity entityIn, double x, double y, double z, float distance) {
/*  48 */     this.path.clearPath();
/*  49 */     this.nodeProcessor.initProcessor(blockaccess, entityIn);
/*  50 */     PathPoint pathpoint = this.nodeProcessor.getPathPointTo(entityIn);
/*  51 */     PathPoint pathpoint1 = this.nodeProcessor.getPathPointToCoords(entityIn, x, y, z);
/*  52 */     PathEntity pathentity = addToPath(entityIn, pathpoint, pathpoint1, distance);
/*  53 */     this.nodeProcessor.postProcess();
/*  54 */     return pathentity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathEntity addToPath(Entity entityIn, PathPoint pathpointStart, PathPoint pathpointEnd, float maxDistance) {
/*  62 */     pathpointStart.totalPathDistance = 0.0F;
/*  63 */     pathpointStart.distanceToNext = pathpointStart.distanceToSquared(pathpointEnd);
/*  64 */     pathpointStart.distanceToTarget = pathpointStart.distanceToNext;
/*  65 */     this.path.clearPath();
/*  66 */     this.path.addPoint(pathpointStart);
/*  67 */     PathPoint pathpoint = pathpointStart;
/*     */     
/*  69 */     while (!this.path.isPathEmpty()) {
/*     */       
/*  71 */       PathPoint pathpoint1 = this.path.dequeue();
/*     */       
/*  73 */       if (pathpoint1.equals(pathpointEnd))
/*     */       {
/*  75 */         return createEntityPath(pathpointStart, pathpointEnd);
/*     */       }
/*     */       
/*  78 */       if (pathpoint1.distanceToSquared(pathpointEnd) < pathpoint.distanceToSquared(pathpointEnd))
/*     */       {
/*  80 */         pathpoint = pathpoint1;
/*     */       }
/*     */       
/*  83 */       pathpoint1.visited = true;
/*  84 */       int i = this.nodeProcessor.findPathOptions(this.pathOptions, entityIn, pathpoint1, pathpointEnd, maxDistance);
/*     */       
/*  86 */       for (int j = 0; j < i; j++) {
/*     */         
/*  88 */         PathPoint pathpoint2 = this.pathOptions[j];
/*  89 */         float f = pathpoint1.totalPathDistance + pathpoint1.distanceToSquared(pathpoint2);
/*     */         
/*  91 */         if (f < maxDistance * 2.0F && (!pathpoint2.isAssigned() || f < pathpoint2.totalPathDistance)) {
/*     */           
/*  93 */           pathpoint2.previous = pathpoint1;
/*  94 */           pathpoint2.totalPathDistance = f;
/*  95 */           pathpoint2.distanceToNext = pathpoint2.distanceToSquared(pathpointEnd);
/*     */           
/*  97 */           if (pathpoint2.isAssigned()) {
/*     */             
/*  99 */             this.path.changeDistance(pathpoint2, pathpoint2.totalPathDistance + pathpoint2.distanceToNext);
/*     */           }
/*     */           else {
/*     */             
/* 103 */             pathpoint2.distanceToTarget = pathpoint2.totalPathDistance + pathpoint2.distanceToNext;
/* 104 */             this.path.addPoint(pathpoint2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     if (pathpoint == pathpointStart)
/*     */     {
/* 112 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 116 */     return createEntityPath(pathpointStart, pathpoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PathEntity createEntityPath(PathPoint start, PathPoint end) {
/* 125 */     int i = 1;
/*     */     
/* 127 */     for (PathPoint pathpoint = end; pathpoint.previous != null; pathpoint = pathpoint.previous)
/*     */     {
/* 129 */       i++;
/*     */     }
/*     */     
/* 132 */     PathPoint[] apathpoint = new PathPoint[i];
/* 133 */     PathPoint pathpoint1 = end;
/* 134 */     i--;
/*     */     
/* 136 */     for (apathpoint[i] = end; pathpoint1.previous != null; apathpoint[i] = pathpoint1) {
/*     */       
/* 138 */       pathpoint1 = pathpoint1.previous;
/* 139 */       i--;
/*     */     } 
/*     */     
/* 142 */     return new PathEntity(apathpoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\pathfinding\PathFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */