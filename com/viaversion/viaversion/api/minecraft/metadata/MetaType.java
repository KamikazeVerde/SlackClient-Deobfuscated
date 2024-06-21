/*    */ package com.viaversion.viaversion.api.minecraft.metadata;
/*    */ 
/*    */ import com.viaversion.viaversion.api.type.Type;
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
/*    */ public interface MetaType
/*    */ {
/*    */   Type type();
/*    */   
/*    */   int typeId();
/*    */   
/*    */   static MetaType create(int typeId, Type<?> type) {
/* 44 */     return new MetaTypeImpl(typeId, type);
/*    */   }
/*    */   
/*    */   public static final class MetaTypeImpl implements MetaType {
/*    */     private final int typeId;
/*    */     private final Type<?> type;
/*    */     
/*    */     MetaTypeImpl(int typeId, Type<?> type) {
/* 52 */       this.typeId = typeId;
/* 53 */       this.type = type;
/*    */     }
/*    */ 
/*    */     
/*    */     public int typeId() {
/* 58 */       return this.typeId;
/*    */     }
/*    */ 
/*    */     
/*    */     public Type<?> type() {
/* 63 */       return this.type;
/*    */     }
/*    */ 
/*    */     
/*    */     public String toString() {
/* 68 */       return "MetaType{typeId=" + this.typeId + ", type=" + this.type + '}';
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 76 */       if (this == o) return true; 
/* 77 */       if (o == null || getClass() != o.getClass()) return false; 
/* 78 */       MetaTypeImpl metaType = (MetaTypeImpl)o;
/* 79 */       if (this.typeId != metaType.typeId) return false; 
/* 80 */       return this.type.equals(metaType.type);
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 85 */       int result = this.typeId;
/* 86 */       result = 31 * result + this.type.hashCode();
/* 87 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\minecraft\metadata\MetaType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */