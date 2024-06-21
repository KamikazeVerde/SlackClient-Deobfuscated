/*     */ package com.viaversion.viaversion.libs.gson;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.gson.internal.ReflectionAccessFilterHelper;
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
/*     */ public interface ReflectionAccessFilter
/*     */ {
/*     */   public enum FilterResult
/*     */   {
/*  46 */     ALLOW,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     INDECISIVE,
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
/*  74 */     BLOCK_INACCESSIBLE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     BLOCK_ALL;
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
/* 105 */   public static final ReflectionAccessFilter BLOCK_INACCESSIBLE_JAVA = new ReflectionAccessFilter() {
/*     */       public ReflectionAccessFilter.FilterResult check(Class<?> rawClass) {
/* 107 */         return ReflectionAccessFilterHelper.isJavaType(rawClass) ? 
/* 108 */           ReflectionAccessFilter.FilterResult.BLOCK_INACCESSIBLE : 
/* 109 */           ReflectionAccessFilter.FilterResult.INDECISIVE;
/*     */       }
/*     */     };
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
/* 130 */   public static final ReflectionAccessFilter BLOCK_ALL_JAVA = new ReflectionAccessFilter() {
/*     */       public ReflectionAccessFilter.FilterResult check(Class<?> rawClass) {
/* 132 */         return ReflectionAccessFilterHelper.isJavaType(rawClass) ? 
/* 133 */           ReflectionAccessFilter.FilterResult.BLOCK_ALL : 
/* 134 */           ReflectionAccessFilter.FilterResult.INDECISIVE;
/*     */       }
/*     */     };
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
/* 154 */   public static final ReflectionAccessFilter BLOCK_ALL_ANDROID = new ReflectionAccessFilter() {
/*     */       public ReflectionAccessFilter.FilterResult check(Class<?> rawClass) {
/* 156 */         return ReflectionAccessFilterHelper.isAndroidType(rawClass) ? 
/* 157 */           ReflectionAccessFilter.FilterResult.BLOCK_ALL : 
/* 158 */           ReflectionAccessFilter.FilterResult.INDECISIVE;
/*     */       }
/*     */     };
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
/* 179 */   public static final ReflectionAccessFilter BLOCK_ALL_PLATFORM = new ReflectionAccessFilter() {
/*     */       public ReflectionAccessFilter.FilterResult check(Class<?> rawClass) {
/* 181 */         return ReflectionAccessFilterHelper.isAnyPlatformType(rawClass) ? 
/* 182 */           ReflectionAccessFilter.FilterResult.BLOCK_ALL : 
/* 183 */           ReflectionAccessFilter.FilterResult.INDECISIVE;
/*     */       }
/*     */     };
/*     */   
/*     */   FilterResult check(Class<?> paramClass);
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\ReflectionAccessFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */