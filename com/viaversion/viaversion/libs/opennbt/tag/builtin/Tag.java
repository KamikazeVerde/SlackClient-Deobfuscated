/*    */ package com.viaversion.viaversion.libs.opennbt.tag.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Array;
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
/*    */ public abstract class Tag
/*    */   implements Cloneable
/*    */ {
/*    */   public final void read(DataInput in) throws IOException {
/* 30 */     read(in, TagLimiter.noop(), 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void read(DataInput in, TagLimiter tagLimiter) throws IOException {
/* 41 */     read(in, tagLimiter, 0);
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
/*    */   public String toString() {
/* 75 */     String value = "";
/* 76 */     if (getValue() != null) {
/* 77 */       value = getValue().toString();
/* 78 */       if (getValue().getClass().isArray()) {
/* 79 */         StringBuilder build = new StringBuilder();
/* 80 */         build.append("[");
/* 81 */         for (int index = 0; index < Array.getLength(getValue()); index++) {
/* 82 */           if (index > 0) {
/* 83 */             build.append(", ");
/*    */           }
/*    */           
/* 86 */           build.append(Array.get(getValue(), index));
/*    */         } 
/*    */         
/* 89 */         build.append("]");
/* 90 */         value = build.toString();
/*    */       } 
/*    */     } 
/*    */     
/* 94 */     return getClass().getSimpleName() + " { " + value + " }";
/*    */   }
/*    */   
/*    */   public abstract Object getValue();
/*    */   
/*    */   public abstract void read(DataInput paramDataInput, TagLimiter paramTagLimiter, int paramInt) throws IOException;
/*    */   
/*    */   public abstract void write(DataOutput paramDataOutput) throws IOException;
/*    */   
/*    */   public abstract int getTagId();
/*    */   
/*    */   public abstract Tag clone();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\Tag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */