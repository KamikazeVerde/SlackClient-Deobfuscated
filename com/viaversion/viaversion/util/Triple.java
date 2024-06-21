/*    */ package com.viaversion.viaversion.util;
/*    */ 
/*    */ import java.util.Objects;
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
/*    */ public class Triple<A, B, C>
/*    */ {
/*    */   private final A first;
/*    */   private final B second;
/*    */   private final C third;
/*    */   
/*    */   public Triple(A first, B second, C third) {
/* 34 */     this.first = first;
/* 35 */     this.second = second;
/* 36 */     this.third = third;
/*    */   }
/*    */   
/*    */   public A first() {
/* 40 */     return this.first;
/*    */   }
/*    */   
/*    */   public B second() {
/* 44 */     return this.second;
/*    */   }
/*    */   
/*    */   public C third() {
/* 48 */     return this.third;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public A getFirst() {
/* 53 */     return this.first;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public B getSecond() {
/* 58 */     return this.second;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public C getThird() {
/* 63 */     return this.third;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 68 */     return "Triple{" + this.first + ", " + this.second + ", " + this.third + '}';
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 73 */     if (this == o) return true; 
/* 74 */     if (o == null || getClass() != o.getClass()) return false; 
/* 75 */     Triple<?, ?, ?> triple = (Triple<?, ?, ?>)o;
/* 76 */     if (!Objects.equals(this.first, triple.first)) return false; 
/* 77 */     if (!Objects.equals(this.second, triple.second)) return false; 
/* 78 */     return Objects.equals(this.third, triple.third);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 83 */     int result = (this.first != null) ? this.first.hashCode() : 0;
/* 84 */     result = 31 * result + ((this.second != null) ? this.second.hashCode() : 0);
/* 85 */     result = 31 * result + ((this.third != null) ? this.third.hashCode() : 0);
/* 86 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\Triple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */