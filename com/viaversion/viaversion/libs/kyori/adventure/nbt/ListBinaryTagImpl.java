/*     */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
/*     */ 
/*     */ import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Spliterator;
/*     */ import java.util.Spliterators;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import org.jetbrains.annotations.Debug.Renderer;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
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
/*     */ 
/*     */ @Renderer(text = "\"ListBinaryTag[type=\" + this.type.toString() + \"]\"", childrenArray = "this.tags.toArray()", hasChildren = "!this.tags.isEmpty()")
/*     */ final class ListBinaryTagImpl
/*     */   extends AbstractBinaryTag
/*     */   implements ListBinaryTag
/*     */ {
/*  43 */   static final ListBinaryTag EMPTY = new ListBinaryTagImpl((BinaryTagType)BinaryTagTypes.END, Collections.emptyList());
/*     */   private final List<BinaryTag> tags;
/*     */   private final BinaryTagType<? extends BinaryTag> elementType;
/*     */   private final int hashCode;
/*     */   
/*     */   ListBinaryTagImpl(BinaryTagType<? extends BinaryTag> elementType, List<BinaryTag> tags) {
/*  49 */     this.tags = Collections.unmodifiableList(tags);
/*  50 */     this.elementType = elementType;
/*  51 */     this.hashCode = tags.hashCode();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BinaryTagType<? extends BinaryTag> elementType() {
/*  56 */     return this.elementType;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  61 */     return this.tags.size();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public BinaryTag get(int index) {
/*  66 */     return this.tags.get(index);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ListBinaryTag set(int index, @NotNull BinaryTag newTag, @Nullable Consumer<? super BinaryTag> removed) {
/*  71 */     return edit(tags -> { BinaryTag oldTag = tags.set(index, newTag); if (removed != null) removed.accept(oldTag);  }newTag
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  76 */         .type());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ListBinaryTag remove(int index, @Nullable Consumer<? super BinaryTag> removed) {
/*  81 */     return edit(tags -> { BinaryTag oldTag = tags.remove(index); if (removed != null) removed.accept(oldTag);  }null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public ListBinaryTag add(BinaryTag tag) {
/*  91 */     noAddEnd(tag);
/*  92 */     if (this.elementType != BinaryTagTypes.END) {
/*  93 */       mustBeSameType(tag, this.elementType);
/*     */     }
/*  95 */     return edit(tags -> tags.add(tag), tag.type());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ListBinaryTag add(Iterable<? extends BinaryTag> tagsToAdd) {
/* 100 */     if (tagsToAdd instanceof Collection && ((Collection)tagsToAdd).isEmpty()) {
/* 101 */       return this;
/*     */     }
/* 103 */     BinaryTagType<?> type = mustBeSameType(tagsToAdd);
/* 104 */     return edit(tags -> { for (BinaryTag tag : tagsToAdd) tags.add(tag);  }(BinaryTagType)type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void noAddEnd(BinaryTag tag) {
/* 113 */     if (tag.type() == BinaryTagTypes.END) {
/* 114 */       throw new IllegalArgumentException(String.format("Cannot add a %s to a %s", new Object[] { BinaryTagTypes.END, BinaryTagTypes.LIST }));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static BinaryTagType<?> mustBeSameType(Iterable<? extends BinaryTag> tags) {
/* 120 */     BinaryTagType<?> type = null;
/* 121 */     for (BinaryTag tag : tags) {
/* 122 */       if (type == null) {
/* 123 */         type = tag.type(); continue;
/*     */       } 
/* 125 */       mustBeSameType(tag, (BinaryTagType)type);
/*     */     } 
/*     */     
/* 128 */     return type;
/*     */   }
/*     */ 
/*     */   
/*     */   static void mustBeSameType(BinaryTag tag, BinaryTagType<? extends BinaryTag> type) {
/* 133 */     if (tag.type() != type) {
/* 134 */       throw new IllegalArgumentException(String.format("Trying to add tag of type %s to list of %s", new Object[] { tag.type(), type }));
/*     */     }
/*     */   }
/*     */   
/*     */   private ListBinaryTag edit(Consumer<List<BinaryTag>> consumer, @Nullable BinaryTagType<? extends BinaryTag> maybeElementType) {
/* 139 */     List<BinaryTag> tags = new ArrayList<>(this.tags);
/* 140 */     consumer.accept(tags);
/* 141 */     BinaryTagType<? extends BinaryTag> elementType = this.elementType;
/*     */     
/* 143 */     if (maybeElementType != null && elementType == BinaryTagTypes.END) {
/* 144 */       elementType = maybeElementType;
/*     */     }
/* 146 */     return new ListBinaryTagImpl(elementType, tags);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<BinaryTag> stream() {
/* 151 */     return this.tags.stream();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<BinaryTag> iterator() {
/* 156 */     final Iterator<BinaryTag> iterator = this.tags.iterator();
/* 157 */     return new Iterator<BinaryTag>()
/*     */       {
/*     */         public boolean hasNext() {
/* 160 */           return iterator.hasNext();
/*     */         }
/*     */ 
/*     */         
/*     */         public BinaryTag next() {
/* 165 */           return iterator.next();
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEachRemaining(Consumer<? super BinaryTag> action) {
/* 170 */           iterator.forEachRemaining(action);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<? super BinaryTag> action) {
/* 177 */     this.tags.forEach(action);
/*     */   }
/*     */ 
/*     */   
/*     */   public Spliterator<BinaryTag> spliterator() {
/* 182 */     return Spliterators.spliterator(this.tags, 1040);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object that) {
/* 187 */     return (this == that || (that instanceof ListBinaryTagImpl && this.tags.equals(((ListBinaryTagImpl)that).tags)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 192 */     return this.hashCode;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public Stream<? extends ExaminableProperty> examinableProperties() {
/* 197 */     return Stream.of(new ExaminableProperty[] {
/* 198 */           ExaminableProperty.of("tags", this.tags), 
/* 199 */           ExaminableProperty.of("type", this.elementType)
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\ListBinaryTagImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */