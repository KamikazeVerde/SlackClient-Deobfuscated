/*    */ package com.viaversion.viaversion.libs.kyori.adventure.title;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
/*    */ import org.jetbrains.annotations.ApiStatus.NonExtendable;
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
/*    */ @NonExtendable
/*    */ public interface TitlePart<T>
/*    */ {
/* 43 */   public static final TitlePart<Component> TITLE = new TitlePart<Component>()
/*    */     {
/*    */       public String toString() {
/* 46 */         return "TitlePart.TITLE";
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 55 */   public static final TitlePart<Component> SUBTITLE = new TitlePart<Component>()
/*    */     {
/*    */       public String toString() {
/* 58 */         return "TitlePart.SUBTITLE";
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 67 */   public static final TitlePart<Title.Times> TIMES = new TitlePart<Title.Times>()
/*    */     {
/*    */       public String toString() {
/* 70 */         return "TitlePart.TIMES";
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\title\TitlePart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */