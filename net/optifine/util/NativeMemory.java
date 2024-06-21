/*    */ package net.optifine.util;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class NativeMemory
/*    */ {
/* 10 */   private static LongSupplier bufferAllocatedSupplier = makeLongSupplier(new String[][] { { "sun.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" }, { "jdk.internal.misc.SharedSecrets", "getJavaNioAccess", "getDirectBufferPool", "getMemoryUsed" } });
/* 11 */   private static LongSupplier bufferMaximumSupplier = makeLongSupplier(new String[][] { { "sun.misc.VM", "maxDirectMemory" }, { "jdk.internal.misc.VM", "maxDirectMemory" } });
/*    */ 
/*    */   
/*    */   public static long getBufferAllocated() {
/* 15 */     return (bufferAllocatedSupplier == null) ? -1L : bufferAllocatedSupplier.getAsLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public static long getBufferMaximum() {
/* 20 */     return (bufferMaximumSupplier == null) ? -1L : bufferMaximumSupplier.getAsLong();
/*    */   }
/*    */ 
/*    */   
/*    */   private static LongSupplier makeLongSupplier(String[][] paths) {
/* 25 */     List<Throwable> list = new ArrayList<>();
/*    */     
/* 27 */     for (int i = 0; i < paths.length; i++) {
/*    */       
/* 29 */       String[] astring = paths[i];
/*    */ 
/*    */       
/*    */       try {
/* 33 */         LongSupplier longsupplier = makeLongSupplier(astring);
/* 34 */         return longsupplier;
/*    */       }
/* 36 */       catch (Throwable throwable) {
/*    */         
/* 38 */         list.add(throwable);
/*    */       } 
/*    */     } 
/*    */     
/* 42 */     for (Throwable throwable1 : list)
/*    */     {
/* 44 */       Config.warn("" + throwable1.getClass().getName() + ": " + throwable1.getMessage());
/*    */     }
/*    */     
/* 47 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   private static LongSupplier makeLongSupplier(String[] path) throws Exception {
/* 52 */     if (path.length < 2)
/*    */     {
/* 54 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 58 */     Class<?> oclass = Class.forName(path[0]);
/* 59 */     Method method = oclass.getMethod(path[1], new Class[0]);
/* 60 */     method.setAccessible(true);
/* 61 */     Object object = null;
/*    */     
/* 63 */     for (int i = 2; i < path.length; i++) {
/*    */       
/* 65 */       String s = path[i];
/* 66 */       object = method.invoke(object, new Object[0]);
/* 67 */       method = object.getClass().getMethod(s, new Class[0]);
/* 68 */       method.setAccessible(true);
/*    */     } 
/*    */     
/* 71 */     final Method method1 = method;
/* 72 */     final Object o = object;
/* 73 */     LongSupplier longsupplier = new LongSupplier()
/*    */       {
/*    */         private boolean disabled = false;
/*    */         
/*    */         public long getAsLong() {
/* 78 */           if (this.disabled)
/*    */           {
/* 80 */             return -1L;
/*    */           }
/*    */ 
/*    */ 
/*    */           
/*    */           try {
/* 86 */             return ((Long)method1.invoke(o, new Object[0])).longValue();
/*    */           }
/* 88 */           catch (Throwable throwable) {
/*    */             
/* 90 */             Config.warn("" + throwable.getClass().getName() + ": " + throwable.getMessage());
/* 91 */             this.disabled = true;
/* 92 */             return -1L;
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 97 */     return longsupplier;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\NativeMemory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */