/*    */ package com.viaversion.viaversion.velocity.platform;
/*    */ 
/*    */ import com.velocitypowered.api.scheduler.ScheduledTask;
/*    */ import com.viaversion.viaversion.api.platform.PlatformTask;
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
/*    */ public class VelocityViaTask
/*    */   implements PlatformTask<ScheduledTask>
/*    */ {
/*    */   private final ScheduledTask task;
/*    */   
/*    */   public VelocityViaTask(ScheduledTask task) {
/* 27 */     this.task = task;
/*    */   }
/*    */ 
/*    */   
/*    */   public ScheduledTask getObject() {
/* 32 */     return this.task;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 37 */     this.task.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\velocity\platform\VelocityViaTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */