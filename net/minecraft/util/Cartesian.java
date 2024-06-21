/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ 
/*     */ public class Cartesian
/*     */ {
/*     */   public static <T> Iterable<T[]> cartesianProduct(Class<T> clazz, Iterable<? extends Iterable<? extends T>> sets) {
/*  18 */     return new Product<>(clazz, toArray(Iterable.class, (Iterable)sets));
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> Iterable<List<T>> cartesianProduct(Iterable<? extends Iterable<? extends T>> sets) {
/*  23 */     return arraysAsLists(cartesianProduct(Object.class, sets));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> Iterable<List<T>> arraysAsLists(Iterable<Object[]> arrays) {
/*  28 */     return Iterables.transform(arrays, new GetList());
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] toArray(Class<? super T> clazz, Iterable<? extends T> it) {
/*  33 */     List<T> list = Lists.newArrayList();
/*     */     
/*  35 */     for (T t : it)
/*     */     {
/*  37 */       list.add(t);
/*     */     }
/*     */     
/*  40 */     return list.toArray(createArray(clazz, list.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] createArray(Class<? super T> p_179319_0_, int p_179319_1_) {
/*  45 */     return (T[])Array.newInstance(p_179319_0_, p_179319_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   static class GetList<T>
/*     */     implements Function<Object[], List<T>>
/*     */   {
/*     */     private GetList() {}
/*     */ 
/*     */     
/*     */     public List<T> apply(Object[] p_apply_1_) {
/*  56 */       return Arrays.asList((T[])p_apply_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Product<T>
/*     */     implements Iterable<T[]>
/*     */   {
/*     */     private final Class<T> clazz;
/*     */     private final Iterable<? extends T>[] iterables;
/*     */     
/*     */     private Product(Class<T> clazz, Iterable<? extends T>[] iterables) {
/*  67 */       this.clazz = clazz;
/*  68 */       this.iterables = iterables;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<T[]> iterator() {
/*  73 */       return (this.iterables.length <= 0) ? Collections.<T[]>singletonList(Cartesian.createArray(this.clazz, 0)).iterator() : (Iterator<T[]>)new ProductIterator(this.clazz, (Iterable[])this.iterables);
/*     */     }
/*     */     
/*     */     static class ProductIterator<T>
/*     */       extends UnmodifiableIterator<T[]>
/*     */     {
/*     */       private int index;
/*     */       private final Iterable<? extends T>[] iterables;
/*     */       private final Iterator<? extends T>[] iterators;
/*     */       private final T[] results;
/*     */       
/*     */       private ProductIterator(Class<T> clazz, Iterable<? extends T>[] iterables) {
/*  85 */         this.index = -2;
/*  86 */         this.iterables = iterables;
/*  87 */         this.iterators = (Iterator<? extends T>[])Cartesian.createArray((Class)Iterator.class, this.iterables.length);
/*     */         
/*  89 */         for (int i = 0; i < this.iterables.length; i++)
/*     */         {
/*  91 */           this.iterators[i] = iterables[i].iterator();
/*     */         }
/*     */         
/*  94 */         this.results = Cartesian.createArray(clazz, this.iterators.length);
/*     */       }
/*     */ 
/*     */       
/*     */       private void endOfData() {
/*  99 */         this.index = -1;
/* 100 */         Arrays.fill((Object[])this.iterators, (Object)null);
/* 101 */         Arrays.fill((Object[])this.results, (Object)null);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 106 */         if (this.index == -2) {
/*     */           
/* 108 */           this.index = 0;
/*     */           
/* 110 */           for (Iterator<? extends T> iterator1 : this.iterators) {
/*     */             
/* 112 */             if (!iterator1.hasNext()) {
/*     */               
/* 114 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 119 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 123 */         if (this.index >= this.iterators.length)
/*     */         {
/* 125 */           for (this.index = this.iterators.length - 1; this.index >= 0; this.index--) {
/*     */             
/* 127 */             Iterator<? extends T> iterator = this.iterators[this.index];
/*     */             
/* 129 */             if (iterator.hasNext()) {
/*     */               break;
/*     */             }
/*     */ 
/*     */             
/* 134 */             if (this.index == 0) {
/*     */               
/* 136 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/* 140 */             iterator = this.iterables[this.index].iterator();
/* 141 */             this.iterators[this.index] = iterator;
/*     */             
/* 143 */             if (!iterator.hasNext()) {
/*     */               
/* 145 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 151 */         return (this.index >= 0);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public T[] next() {
/* 157 */         if (!hasNext())
/*     */         {
/* 159 */           throw new NoSuchElementException();
/*     */         }
/*     */ 
/*     */         
/* 163 */         while (this.index < this.iterators.length) {
/*     */           
/* 165 */           this.results[this.index] = this.iterators[this.index].next();
/* 166 */           this.index++;
/*     */         } 
/*     */         
/* 169 */         return (T[])this.results.clone();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\Cartesian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */