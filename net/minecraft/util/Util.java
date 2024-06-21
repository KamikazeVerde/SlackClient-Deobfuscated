/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.FutureTask;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static EnumOS getOSType() {
/* 11 */     String s = System.getProperty("os.name").toLowerCase();
/* 12 */     return s.contains("win") ? EnumOS.WINDOWS : (s.contains("mac") ? EnumOS.OSX : (s.contains("solaris") ? EnumOS.SOLARIS : (s.contains("sunos") ? EnumOS.SOLARIS : (s.contains("linux") ? EnumOS.LINUX : (s.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <V> V func_181617_a(FutureTask<V> p_181617_0_, Logger p_181617_1_) {
/*    */     try {
/* 19 */       p_181617_0_.run();
/* 20 */       return p_181617_0_.get();
/*    */     }
/* 22 */     catch (ExecutionException executionexception) {
/*    */       
/* 24 */       p_181617_1_.fatal("Error executing task", executionexception);
/*    */       
/* 26 */       if (executionexception.getCause() instanceof OutOfMemoryError)
/*    */       {
/* 28 */         OutOfMemoryError outofmemoryerror = (OutOfMemoryError)executionexception.getCause();
/* 29 */         throw outofmemoryerror;
/*    */       }
/*    */     
/* 32 */     } catch (InterruptedException interruptedexception) {
/*    */       
/* 34 */       p_181617_1_.fatal("Error executing task", interruptedexception);
/*    */     } 
/*    */     
/* 37 */     return null;
/*    */   }
/*    */   
/*    */   public enum EnumOS
/*    */   {
/* 42 */     LINUX,
/* 43 */     SOLARIS,
/* 44 */     WINDOWS,
/* 45 */     OSX,
/* 46 */     UNKNOWN;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */