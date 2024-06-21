/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ import net.minecraft.util.TupleIntJsonSerializable;
/*     */ 
/*     */ public class StatFileWriter
/*     */ {
/*  11 */   protected final Map<StatBase, TupleIntJsonSerializable> statsData = Maps.newConcurrentMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAchievementUnlocked(Achievement achievementIn) {
/*  18 */     return (readStat(achievementIn) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUnlockAchievement(Achievement achievementIn) {
/*  26 */     return (achievementIn.parentAchievement == null || hasAchievementUnlocked(achievementIn.parentAchievement));
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_150874_c(Achievement p_150874_1_) {
/*  31 */     if (hasAchievementUnlocked(p_150874_1_))
/*     */     {
/*  33 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  37 */     int i = 0;
/*     */     
/*  39 */     for (Achievement achievement = p_150874_1_.parentAchievement; achievement != null && !hasAchievementUnlocked(achievement); i++)
/*     */     {
/*  41 */       achievement = achievement.parentAchievement;
/*     */     }
/*     */     
/*  44 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void increaseStat(EntityPlayer player, StatBase stat, int amount) {
/*  50 */     if (!stat.isAchievement() || canUnlockAchievement((Achievement)stat))
/*     */     {
/*  52 */       unlockAchievement(player, stat, readStat(stat) + amount);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int p_150873_3_) {
/*  61 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(statIn);
/*     */     
/*  63 */     if (tupleintjsonserializable == null) {
/*     */       
/*  65 */       tupleintjsonserializable = new TupleIntJsonSerializable();
/*  66 */       this.statsData.put(statIn, tupleintjsonserializable);
/*     */     } 
/*     */     
/*  69 */     tupleintjsonserializable.setIntegerValue(p_150873_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int readStat(StatBase stat) {
/*  79 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(stat);
/*  80 */     return (tupleintjsonserializable == null) ? 0 : tupleintjsonserializable.getIntegerValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends IJsonSerializable> T func_150870_b(StatBase p_150870_1_) {
/*  85 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(p_150870_1_);
/*  86 */     return (tupleintjsonserializable != null) ? (T)tupleintjsonserializable.getJsonSerializableValue() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends IJsonSerializable> T func_150872_a(StatBase p_150872_1_, T p_150872_2_) {
/*  91 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(p_150872_1_);
/*     */     
/*  93 */     if (tupleintjsonserializable == null) {
/*     */       
/*  95 */       tupleintjsonserializable = new TupleIntJsonSerializable();
/*  96 */       this.statsData.put(p_150872_1_, tupleintjsonserializable);
/*     */     } 
/*     */     
/*  99 */     tupleintjsonserializable.setJsonSerializableValue((IJsonSerializable)p_150872_2_);
/* 100 */     return p_150872_2_;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\stats\StatFileWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */