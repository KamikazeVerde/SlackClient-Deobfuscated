/*     */ package com.viaversion.viaversion.libs.gson.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.math.BigDecimal;
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
/*     */ public final class LazilyParsedNumber
/*     */   extends Number
/*     */ {
/*     */   private final String value;
/*     */   
/*     */   public LazilyParsedNumber(String value) {
/*  35 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int intValue() {
/*     */     try {
/*  41 */       return Integer.parseInt(this.value);
/*  42 */     } catch (NumberFormatException e) {
/*     */       try {
/*  44 */         return (int)Long.parseLong(this.value);
/*  45 */       } catch (NumberFormatException nfe) {
/*  46 */         return (new BigDecimal(this.value)).intValue();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long longValue() {
/*     */     try {
/*  54 */       return Long.parseLong(this.value);
/*  55 */     } catch (NumberFormatException e) {
/*  56 */       return (new BigDecimal(this.value)).longValue();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float floatValue() {
/*  62 */     return Float.parseFloat(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public double doubleValue() {
/*  67 */     return Double.parseDouble(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  72 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/*  81 */     return new BigDecimal(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/*  86 */     throw new InvalidObjectException("Deserialization is unsupported");
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  91 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  96 */     if (this == obj) {
/*  97 */       return true;
/*     */     }
/*  99 */     if (obj instanceof LazilyParsedNumber) {
/* 100 */       LazilyParsedNumber other = (LazilyParsedNumber)obj;
/* 101 */       return (this.value == other.value || this.value.equals(other.value));
/*     */     } 
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\LazilyParsedNumber.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */