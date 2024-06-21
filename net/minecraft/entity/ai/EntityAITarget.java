/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityOwnable;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.pathfinding.PathEntity;
/*     */ import net.minecraft.pathfinding.PathPoint;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.StringUtils;
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
/*     */ 
/*     */ 
/*     */ public abstract class EntityAITarget
/*     */   extends EntityAIBase
/*     */ {
/*     */   protected final EntityCreature taskOwner;
/*     */   protected boolean shouldCheckSight;
/*     */   private boolean nearbyOnly;
/*     */   private int targetSearchStatus;
/*     */   private int targetSearchDelay;
/*     */   private int targetUnseenTicks;
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight) {
/*  50 */     this(creature, checkSight, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAITarget(EntityCreature creature, boolean checkSight, boolean onlyNearby) {
/*  55 */     this.taskOwner = creature;
/*  56 */     this.shouldCheckSight = checkSight;
/*  57 */     this.nearbyOnly = onlyNearby;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  65 */     EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
/*     */     
/*  67 */     if (entitylivingbase == null)
/*     */     {
/*  69 */       return false;
/*     */     }
/*  71 */     if (!entitylivingbase.isEntityAlive())
/*     */     {
/*  73 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  77 */     Team team = this.taskOwner.getTeam();
/*  78 */     Team team1 = entitylivingbase.getTeam();
/*     */     
/*  80 */     if (team != null && team1 == team)
/*     */     {
/*  82 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  86 */     double d0 = getTargetDistance();
/*     */     
/*  88 */     if (this.taskOwner.getDistanceSqToEntity((Entity)entitylivingbase) > d0 * d0)
/*     */     {
/*  90 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  94 */     if (this.shouldCheckSight)
/*     */     {
/*  96 */       if (this.taskOwner.getEntitySenses().canSee((Entity)entitylivingbase)) {
/*     */         
/*  98 */         this.targetUnseenTicks = 0;
/*     */       }
/* 100 */       else if (++this.targetUnseenTicks > 60) {
/*     */         
/* 102 */         return false;
/*     */       } 
/*     */     }
/*     */     
/* 106 */     return (!(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double getTargetDistance() {
/* 114 */     IAttributeInstance iattributeinstance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.followRange);
/* 115 */     return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/* 123 */     this.targetSearchStatus = 0;
/* 124 */     this.targetSearchDelay = 0;
/* 125 */     this.targetUnseenTicks = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 133 */     this.taskOwner.setAttackTarget(null);
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
/*     */   
/*     */   public static boolean isSuitableTarget(EntityLiving attacker, EntityLivingBase target, boolean includeInvincibles, boolean checkSight) {
/* 147 */     if (target == null)
/*     */     {
/* 149 */       return false;
/*     */     }
/* 151 */     if (target == attacker)
/*     */     {
/* 153 */       return false;
/*     */     }
/* 155 */     if (!target.isEntityAlive())
/*     */     {
/* 157 */       return false;
/*     */     }
/* 159 */     if (!attacker.canAttackClass(target.getClass()))
/*     */     {
/* 161 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 165 */     Team team = attacker.getTeam();
/* 166 */     Team team1 = target.getTeam();
/*     */     
/* 168 */     if (team != null && team1 == team)
/*     */     {
/* 170 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 174 */     if (attacker instanceof IEntityOwnable && StringUtils.isNotEmpty(((IEntityOwnable)attacker).getOwnerId())) {
/*     */       
/* 176 */       if (target instanceof IEntityOwnable && ((IEntityOwnable)attacker).getOwnerId().equals(((IEntityOwnable)target).getOwnerId()))
/*     */       {
/* 178 */         return false;
/*     */       }
/*     */       
/* 181 */       if (target == ((IEntityOwnable)attacker).getOwner())
/*     */       {
/* 183 */         return false;
/*     */       }
/*     */     }
/* 186 */     else if (target instanceof EntityPlayer && !includeInvincibles && ((EntityPlayer)target).capabilities.disableDamage) {
/*     */       
/* 188 */       return false;
/*     */     } 
/*     */     
/* 191 */     return (!checkSight || attacker.getEntitySenses().canSee((Entity)target));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
/* 202 */     if (!isSuitableTarget((EntityLiving)this.taskOwner, target, includeInvincibles, this.shouldCheckSight))
/*     */     {
/* 204 */       return false;
/*     */     }
/* 206 */     if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos((Entity)target)))
/*     */     {
/* 208 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 212 */     if (this.nearbyOnly) {
/*     */       
/* 214 */       if (--this.targetSearchDelay <= 0)
/*     */       {
/* 216 */         this.targetSearchStatus = 0;
/*     */       }
/*     */       
/* 219 */       if (this.targetSearchStatus == 0)
/*     */       {
/* 221 */         this.targetSearchStatus = canEasilyReach(target) ? 1 : 2;
/*     */       }
/*     */       
/* 224 */       if (this.targetSearchStatus == 2)
/*     */       {
/* 226 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 230 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canEasilyReach(EntityLivingBase p_75295_1_) {
/* 239 */     this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
/* 240 */     PathEntity pathentity = this.taskOwner.getNavigator().getPathToEntityLiving((Entity)p_75295_1_);
/*     */     
/* 242 */     if (pathentity == null)
/*     */     {
/* 244 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 248 */     PathPoint pathpoint = pathentity.getFinalPathPoint();
/*     */     
/* 250 */     if (pathpoint == null)
/*     */     {
/* 252 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 256 */     int i = pathpoint.xCoord - MathHelper.floor_double(p_75295_1_.posX);
/* 257 */     int j = pathpoint.zCoord - MathHelper.floor_double(p_75295_1_.posZ);
/* 258 */     return ((i * i + j * j) <= 2.25D);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\entity\ai\EntityAITarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */