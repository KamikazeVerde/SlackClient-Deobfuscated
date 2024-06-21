/*    */ package com.viaversion.viabackwards.utils;
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
/*    */ public class Block
/*    */ {
/*    */   private final int id;
/*    */   private final short data;
/*    */   
/*    */   public Block(int id, int data) {
/* 26 */     this.id = id;
/* 27 */     this.data = (short)data;
/*    */   }
/*    */   
/*    */   public Block(int id) {
/* 31 */     this.id = id;
/* 32 */     this.data = 0;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 36 */     return this.id;
/*    */   }
/*    */   
/*    */   public int getData() {
/* 40 */     return this.data;
/*    */   }
/*    */   
/*    */   public Block withData(int data) {
/* 44 */     return new Block(this.id, data);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 49 */     if (this == o) return true; 
/* 50 */     if (o == null || getClass() != o.getClass()) return false; 
/* 51 */     Block block = (Block)o;
/* 52 */     if (this.id != block.id) return false; 
/* 53 */     return (this.data == block.data);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     int result = this.id;
/* 59 */     result = 31 * result + this.data;
/* 60 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackward\\utils\Block.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */