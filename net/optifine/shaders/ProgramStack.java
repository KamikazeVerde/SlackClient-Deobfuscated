/*    */ package net.optifine.shaders;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Deque;
/*    */ 
/*    */ public class ProgramStack
/*    */ {
/*  8 */   private Deque<Program> stack = new ArrayDeque<>();
/*    */ 
/*    */   
/*    */   public void push(Program p) {
/* 12 */     this.stack.addLast(p);
/*    */   }
/*    */ 
/*    */   
/*    */   public Program pop() {
/* 17 */     if (this.stack.isEmpty())
/*    */     {
/* 19 */       return Shaders.ProgramNone;
/*    */     }
/*    */ 
/*    */     
/* 23 */     Program program = this.stack.pollLast();
/* 24 */     return program;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\shaders\ProgramStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */