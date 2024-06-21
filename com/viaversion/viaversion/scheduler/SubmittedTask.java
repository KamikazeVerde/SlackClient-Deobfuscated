/*    */ package com.viaversion.viaversion.scheduler;
/*    */ 
/*    */ import com.viaversion.viaversion.api.scheduler.Task;
/*    */ import com.viaversion.viaversion.api.scheduler.TaskStatus;
/*    */ import java.util.concurrent.Future;
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
/*    */ public final class SubmittedTask
/*    */   implements Task
/*    */ {
/*    */   private final Future<?> future;
/*    */   
/*    */   public SubmittedTask(Future<?> future) {
/* 29 */     this.future = future;
/*    */   }
/*    */ 
/*    */   
/*    */   public TaskStatus status() {
/* 34 */     return this.future.isDone() ? TaskStatus.STOPPED : TaskStatus.RUNNING;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 39 */     this.future.cancel(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\scheduler\SubmittedTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */