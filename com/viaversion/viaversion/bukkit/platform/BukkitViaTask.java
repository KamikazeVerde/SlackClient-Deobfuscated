/*    */ package com.viaversion.viaversion.bukkit.platform;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BukkitViaTask
/*    */   implements PlatformTask<BukkitTask>
/*    */ {
/*    */   private final BukkitTask task;
/*    */   
/*    */   public BukkitViaTask(BukkitTask task) {
/* 29 */     this.task = task;
/*    */   }
/*    */ 
/*    */   
/*    */   public BukkitTask getObject() {
/* 34 */     return this.task;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 39 */     Preconditions.checkArgument((this.task != null), "Task cannot be cancelled");
/* 40 */     this.task.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\platform\BukkitViaTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */