/*    */ package com.viaversion.viaversion.libs.kyori.adventure.audience;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.function.Supplier;
/*    */ import java.util.stream.Collector;
/*    */ import java.util.stream.Collectors;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Audiences
/*    */ {
/*    */   static final Collector<? super Audience, ?, ForwardingAudience> COLLECTOR;
/*    */   
/*    */   static {
/* 35 */     COLLECTOR = Collectors.collectingAndThen(
/* 36 */         Collectors.toCollection(ArrayList::new), audiences -> Audience.audience(Collections.unmodifiableCollection(audiences)));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\audience\Audiences.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */