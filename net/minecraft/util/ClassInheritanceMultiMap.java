/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.optifine.util.IteratorCache;
/*     */ 
/*     */ public class ClassInheritanceMultiMap<T>
/*     */   extends AbstractSet<T> {
/*  18 */   private static final Set<Class<?>> field_181158_a = Collections.newSetFromMap(new ConcurrentHashMap<>());
/*  19 */   private final Map<Class<?>, List<T>> map = Maps.newHashMap();
/*  20 */   private final Set<Class<?>> knownKeys = Sets.newIdentityHashSet();
/*     */   private final Class<T> baseClass;
/*  22 */   private final List<T> field_181745_e = Lists.newArrayList();
/*     */   
/*     */   public boolean empty;
/*     */   
/*     */   public ClassInheritanceMultiMap(Class<T> baseClassIn) {
/*  27 */     this.baseClass = baseClassIn;
/*  28 */     this.knownKeys.add(baseClassIn);
/*  29 */     this.map.put(baseClassIn, this.field_181745_e);
/*     */     
/*  31 */     for (Class<?> oclass : field_181158_a)
/*     */     {
/*  33 */       createLookup(oclass);
/*     */     }
/*     */     
/*  36 */     this.empty = (this.field_181745_e.size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createLookup(Class<?> clazz) {
/*  41 */     field_181158_a.add(clazz);
/*  42 */     int i = this.field_181745_e.size();
/*     */     
/*  44 */     for (int j = 0; j < i; j++) {
/*     */       
/*  46 */       T t = this.field_181745_e.get(j);
/*     */       
/*  48 */       if (clazz.isAssignableFrom(t.getClass()))
/*     */       {
/*  50 */         func_181743_a(t, clazz);
/*     */       }
/*     */     } 
/*     */     
/*  54 */     this.knownKeys.add(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> func_181157_b(Class<?> p_181157_1_) {
/*  59 */     if (this.baseClass.isAssignableFrom(p_181157_1_)) {
/*     */       
/*  61 */       if (!this.knownKeys.contains(p_181157_1_))
/*     */       {
/*  63 */         createLookup(p_181157_1_);
/*     */       }
/*     */       
/*  66 */       return p_181157_1_;
/*     */     } 
/*     */ 
/*     */     
/*  70 */     throw new IllegalArgumentException("Don't know how to search for " + p_181157_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(T p_add_1_) {
/*  76 */     for (Class<?> oclass : this.knownKeys) {
/*     */       
/*  78 */       if (oclass.isAssignableFrom(p_add_1_.getClass()))
/*     */       {
/*  80 */         func_181743_a(p_add_1_, oclass);
/*     */       }
/*     */     } 
/*     */     
/*  84 */     this.empty = (this.field_181745_e.size() == 0);
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_181743_a(T p_181743_1_, Class<?> p_181743_2_) {
/*  90 */     List<T> list = this.map.get(p_181743_2_);
/*     */     
/*  92 */     if (list == null) {
/*     */       
/*  94 */       this.map.put(p_181743_2_, Lists.newArrayList(new Object[] { p_181743_1_ }));
/*     */     }
/*     */     else {
/*     */       
/*  98 */       list.add(p_181743_1_);
/*     */     } 
/*     */     
/* 101 */     this.empty = (this.field_181745_e.size() == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object p_remove_1_) {
/* 106 */     T t = (T)p_remove_1_;
/* 107 */     boolean flag = false;
/*     */     
/* 109 */     for (Class<?> oclass : this.knownKeys) {
/*     */       
/* 111 */       if (oclass.isAssignableFrom(t.getClass())) {
/*     */         
/* 113 */         List<T> list = this.map.get(oclass);
/*     */         
/* 115 */         if (list != null && list.remove(t))
/*     */         {
/* 117 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     this.empty = (this.field_181745_e.size() == 0);
/* 123 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object p_contains_1_) {
/* 128 */     return Iterators.contains(getByClass(p_contains_1_.getClass()).iterator(), p_contains_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> Iterable<S> getByClass(final Class<S> clazz) {
/* 133 */     return new Iterable<S>()
/*     */       {
/*     */         public Iterator<S> iterator()
/*     */         {
/* 137 */           List<T> list = (List<T>)ClassInheritanceMultiMap.this.map.get(ClassInheritanceMultiMap.this.func_181157_b(clazz));
/*     */           
/* 139 */           if (list == null)
/*     */           {
/* 141 */             return (Iterator<S>)Iterators.emptyIterator();
/*     */           }
/*     */ 
/*     */           
/* 145 */           Iterator<T> iterator = list.iterator();
/* 146 */           return (Iterator<S>)Iterators.filter(iterator, clazz);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 154 */     return this.field_181745_e.isEmpty() ? (Iterator<T>)Iterators.emptyIterator() : IteratorCache.getReadOnly(this.field_181745_e);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 159 */     return this.field_181745_e.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 164 */     return this.empty;
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\ClassInheritanceMultiMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */