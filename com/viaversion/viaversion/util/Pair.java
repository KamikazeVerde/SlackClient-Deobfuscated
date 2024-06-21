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
/*    */ public class Pair<X, Y>
/*    */ {
/*    */   private final X key;
/*    */   private Y value;
/*    */   
/*    */   public Pair(X key, Y value) {
/* 33 */     this.key = key;
/* 34 */     this.value = value;
/*    */   }
/*    */   
/*    */   public X key() {
/* 38 */     return this.key;
/*    */   }
/*    */   
/*    */   public Y value() {
/* 42 */     return this.value;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public X getKey() {
/* 47 */     return this.key;
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public Y getValue() {
/* 52 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void setValue(Y value) {
/* 60 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "Pair{" + this.key + ", " + this.value + '}';
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 70 */     if (this == o) return true; 
/* 71 */     if (o == null || getClass() != o.getClass()) return false; 
/* 72 */     Pair<?, ?> pair = (Pair<?, ?>)o;
/* 73 */     if (!Objects.equals(this.key, pair.key)) return false; 
/* 74 */     return Objects.equals(this.value, pair.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 79 */     int result = (this.key != null) ? this.key.hashCode() : 0;
/* 80 */     result = 31 * result + ((this.value != null) ? this.value.hashCode() : 0);
/* 81 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversio\\util\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */