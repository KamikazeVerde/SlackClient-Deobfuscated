/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ final class ListTagBuilder<T extends BinaryTag>
/*    */   implements ListBinaryTag.Builder<T>
/*    */ {
/*    */   @Nullable
/*    */   private List<BinaryTag> tags;
/*    */   private BinaryTagType<? extends BinaryTag> elementType;
/*    */   
/*    */   ListTagBuilder() {
/* 36 */     this((BinaryTagType)BinaryTagTypes.END);
/*    */   }
/*    */   
/*    */   ListTagBuilder(BinaryTagType<? extends BinaryTag> type) {
/* 40 */     this.elementType = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public ListBinaryTag.Builder<T> add(BinaryTag tag) {
/* 45 */     ListBinaryTagImpl.noAddEnd(tag);
/*    */     
/* 47 */     if (this.elementType == BinaryTagTypes.END) {
/* 48 */       this.elementType = tag.type();
/*    */     }
/*    */     
/* 51 */     ListBinaryTagImpl.mustBeSameType(tag, this.elementType);
/* 52 */     if (this.tags == null) {
/* 53 */       this.tags = new ArrayList<>();
/*    */     }
/* 55 */     this.tags.add(tag);
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public ListBinaryTag.Builder<T> add(Iterable<? extends T> tagsToAdd) {
/* 61 */     for (BinaryTag binaryTag : tagsToAdd) {
/* 62 */       add(binaryTag);
/*    */     }
/* 64 */     return this;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ListBinaryTag build() {
/* 69 */     if (this.tags == null) return ListBinaryTag.empty(); 
/* 70 */     return new ListBinaryTagImpl(this.elementType, new ArrayList<>(this.tags));
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ListTagBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */