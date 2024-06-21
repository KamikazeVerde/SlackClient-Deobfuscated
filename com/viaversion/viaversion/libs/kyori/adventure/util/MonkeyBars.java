/*    */ package com.viaversion.viaversion.libs.kyori.adventure.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.EnumSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MonkeyBars
/*    */ {
/*    */   @SafeVarargs
/*    */   @NotNull
/*    */   public static <E extends Enum<E>> Set<E> enumSet(Class<E> type, E... constants) {
/* 55 */     Set<E> set = EnumSet.noneOf(type);
/* 56 */     Collections.addAll(set, constants);
/* 57 */     return Collections.unmodifiableSet(set);
/*    */   }
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
/*    */   @NotNull
/*    */   public static <T> List<T> addOne(@NotNull List<T> oldList, T newElement) {
/* 72 */     if (oldList.isEmpty()) return Collections.singletonList(newElement); 
/* 73 */     List<T> newList = new ArrayList<>(oldList.size() + 1);
/* 74 */     newList.addAll(oldList);
/* 75 */     newList.add(newElement);
/* 76 */     return Collections.unmodifiableList(newList);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventur\\util\MonkeyBars.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */