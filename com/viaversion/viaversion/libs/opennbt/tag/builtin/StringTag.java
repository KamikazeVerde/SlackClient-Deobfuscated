/*    */ package com.viaversion.viaversion.libs.opennbt.tag.builtin;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringTag
/*    */   extends Tag
/*    */ {
/*    */   public static final int ID = 8;
/*    */   private String value;
/*    */   
/*    */   public StringTag() {
/* 20 */     this("");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StringTag(String value) {
/* 29 */     if (value == null) {
/* 30 */       throw new NullPointerException("value cannot be null");
/*    */     }
/* 32 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 37 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setValue(String value) {
/* 46 */     if (value == null) {
/* 47 */       throw new NullPointerException("value cannot be null");
/*    */     }
/* 49 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(DataInput in, TagLimiter tagLimiter, int nestingLevel) throws IOException {
/* 54 */     this.value = in.readUTF();
/* 55 */     tagLimiter.countBytes(2 * this.value.length());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(DataOutput out) throws IOException {
/* 60 */     out.writeUTF(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 65 */     if (this == o) return true; 
/* 66 */     if (o == null || getClass() != o.getClass()) return false; 
/* 67 */     StringTag stringTag = (StringTag)o;
/* 68 */     return this.value.equals(stringTag.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 73 */     return this.value.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public final StringTag clone() {
/* 78 */     return new StringTag(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTagId() {
/* 83 */     return 8;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\opennbt\tag\builtin\StringTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */