/*    */ package com.viaversion.viaversion.bukkit.platform;
/*    */ 
/*    */ import com.viaversion.viaversion.api.platform.PlatformTask;
/*    */ import com.viaversion.viaversion.api.scheduler.Task;
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
/*    */ public class BukkitViaTaskTask
/*    */   implements PlatformTask<Task>
/*    */ {
/*    */   private final Task task;
/*    */   
/*    */   public BukkitViaTaskTask(Task task) {
/* 28 */     this.task = task;
/*    */   }
/*    */ 
/*    */   
/*    */   public Task getObject() {
/* 33 */     return this.task;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 38 */     this.task.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\platform\BukkitViaTaskTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */