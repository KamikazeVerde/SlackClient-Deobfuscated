/*    */ package com.viaversion.viaversion.api.type.types.minecraft;
/*    */ 
/*    */ import com.google.common.base.Preconditions;
/*    */ import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
/*    */ import com.viaversion.viaversion.api.type.Type;
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public final class MetaListType
/*    */   extends MetaListTypeTemplate
/*    */ {
/*    */   private final Type<Metadata> type;
/*    */   
/*    */   public MetaListType(Type<Metadata> type) {
/* 36 */     Preconditions.checkNotNull(type);
/* 37 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Metadata> read(ByteBuf buffer) throws Exception {
/* 42 */     List<Metadata> list = new ArrayList<>();
/*    */     
/*    */     while (true) {
/* 45 */       Metadata meta = (Metadata)this.type.read(buffer);
/* 46 */       if (meta != null) {
/* 47 */         list.add(meta);
/*    */       }
/* 49 */       if (meta == null)
/* 50 */         return list; 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void write(ByteBuf buffer, List<Metadata> object) throws Exception {
/* 55 */     for (Metadata metadata : object) {
/* 56 */       this.type.write(buffer, metadata);
/*    */     }
/* 58 */     this.type.write(buffer, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\api\type\types\minecraft\MetaListType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */