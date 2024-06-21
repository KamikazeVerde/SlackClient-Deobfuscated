/*     */ package com.viaversion.viaversion.libs.gson.internal;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Comparator;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ public final class LinkedTreeMap<K, V>
/*     */   extends AbstractMap<K, V>
/*     */   implements Serializable
/*     */ {
/*  45 */   private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
/*     */       public int compare(Comparable<Comparable> a, Comparable b) {
/*  47 */         return a.compareTo(b);
/*     */       }
/*     */     };
/*     */   
/*     */   private final Comparator<? super K> comparator;
/*     */   private final boolean allowNullValues;
/*     */   Node<K, V> root;
/*  54 */   int size = 0;
/*  55 */   int modCount = 0;
/*     */ 
/*     */   
/*     */   final Node<K, V> header;
/*     */   
/*     */   private EntrySet entrySet;
/*     */   
/*     */   private KeySet keySet;
/*     */ 
/*     */   
/*     */   public LinkedTreeMap() {
/*  66 */     this((Comparator)NATURAL_ORDER, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedTreeMap(boolean allowNullValues) {
/*  77 */     this((Comparator)NATURAL_ORDER, allowNullValues);
/*     */   }
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
/*     */   public LinkedTreeMap(Comparator<? super K> comparator, boolean allowNullValues) {
/*  90 */     this
/*     */       
/*  92 */       .comparator = (comparator != null) ? comparator : (Comparator)NATURAL_ORDER;
/*  93 */     this.allowNullValues = allowNullValues;
/*  94 */     this.header = new Node<>(allowNullValues);
/*     */   }
/*     */   
/*     */   public int size() {
/*  98 */     return this.size;
/*     */   }
/*     */   
/*     */   public V get(Object key) {
/* 102 */     Node<K, V> node = findByObject(key);
/* 103 */     return (node != null) ? node.value : null;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 107 */     return (findByObject(key) != null);
/*     */   }
/*     */   
/*     */   public V put(K key, V value) {
/* 111 */     if (key == null) {
/* 112 */       throw new NullPointerException("key == null");
/*     */     }
/* 114 */     if (value == null && !this.allowNullValues) {
/* 115 */       throw new NullPointerException("value == null");
/*     */     }
/* 117 */     Node<K, V> created = find(key, true);
/* 118 */     V result = created.value;
/* 119 */     created.value = value;
/* 120 */     return result;
/*     */   }
/*     */   
/*     */   public void clear() {
/* 124 */     this.root = null;
/* 125 */     this.size = 0;
/* 126 */     this.modCount++;
/*     */ 
/*     */     
/* 129 */     Node<K, V> header = this.header;
/* 130 */     header.next = header.prev = header;
/*     */   }
/*     */   
/*     */   public V remove(Object key) {
/* 134 */     Node<K, V> node = removeInternalByKey(key);
/* 135 */     return (node != null) ? node.value : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Node<K, V> find(K key, boolean create) {
/*     */     Node<K, V> created;
/* 145 */     Comparator<? super K> comparator = this.comparator;
/* 146 */     Node<K, V> nearest = this.root;
/* 147 */     int comparison = 0;
/*     */     
/* 149 */     if (nearest != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 154 */       Comparable<Object> comparableKey = (comparator == NATURAL_ORDER) ? (Comparable<Object>)key : null;
/*     */ 
/*     */ 
/*     */       
/*     */       while (true) {
/* 159 */         comparison = (comparableKey != null) ? comparableKey.compareTo(nearest.key) : comparator.compare(key, nearest.key);
/*     */ 
/*     */         
/* 162 */         if (comparison == 0) {
/* 163 */           return nearest;
/*     */         }
/*     */ 
/*     */         
/* 167 */         Node<K, V> child = (comparison < 0) ? nearest.left : nearest.right;
/* 168 */         if (child == null) {
/*     */           break;
/*     */         }
/*     */         
/* 172 */         nearest = child;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 177 */     if (!create) {
/* 178 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 182 */     Node<K, V> header = this.header;
/*     */     
/* 184 */     if (nearest == null) {
/*     */       
/* 186 */       if (comparator == NATURAL_ORDER && !(key instanceof Comparable)) {
/* 187 */         throw new ClassCastException(key.getClass().getName() + " is not Comparable");
/*     */       }
/* 189 */       created = new Node<>(this.allowNullValues, nearest, key, header, header.prev);
/* 190 */       this.root = created;
/*     */     } else {
/* 192 */       created = new Node<>(this.allowNullValues, nearest, key, header, header.prev);
/* 193 */       if (comparison < 0) {
/* 194 */         nearest.left = created;
/*     */       } else {
/* 196 */         nearest.right = created;
/*     */       } 
/* 198 */       rebalance(nearest, true);
/*     */     } 
/* 200 */     this.size++;
/* 201 */     this.modCount++;
/*     */     
/* 203 */     return created;
/*     */   }
/*     */ 
/*     */   
/*     */   Node<K, V> findByObject(Object key) {
/*     */     try {
/* 209 */       return (key != null) ? find((K)key, false) : null;
/* 210 */     } catch (ClassCastException e) {
/* 211 */       return null;
/*     */     } 
/*     */   }
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
/*     */   Node<K, V> findByEntry(Map.Entry<?, ?> entry) {
/* 225 */     Node<K, V> mine = findByObject(entry.getKey());
/* 226 */     boolean valuesEqual = (mine != null && equal(mine.value, entry.getValue()));
/* 227 */     return valuesEqual ? mine : null;
/*     */   }
/*     */   
/*     */   private boolean equal(Object a, Object b) {
/* 231 */     return Objects.equals(a, b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeInternal(Node<K, V> node, boolean unlink) {
/* 241 */     if (unlink) {
/* 242 */       node.prev.next = node.next;
/* 243 */       node.next.prev = node.prev;
/*     */     } 
/*     */     
/* 246 */     Node<K, V> left = node.left;
/* 247 */     Node<K, V> right = node.right;
/* 248 */     Node<K, V> originalParent = node.parent;
/* 249 */     if (left != null && right != null) {
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
/* 260 */       Node<K, V> adjacent = (left.height > right.height) ? left.last() : right.first();
/* 261 */       removeInternal(adjacent, false);
/*     */       
/* 263 */       int leftHeight = 0;
/* 264 */       left = node.left;
/* 265 */       if (left != null) {
/* 266 */         leftHeight = left.height;
/* 267 */         adjacent.left = left;
/* 268 */         left.parent = adjacent;
/* 269 */         node.left = null;
/*     */       } 
/*     */       
/* 272 */       int rightHeight = 0;
/* 273 */       right = node.right;
/* 274 */       if (right != null) {
/* 275 */         rightHeight = right.height;
/* 276 */         adjacent.right = right;
/* 277 */         right.parent = adjacent;
/* 278 */         node.right = null;
/*     */       } 
/*     */       
/* 281 */       adjacent.height = Math.max(leftHeight, rightHeight) + 1;
/* 282 */       replaceInParent(node, adjacent); return;
/*     */     } 
/* 284 */     if (left != null) {
/* 285 */       replaceInParent(node, left);
/* 286 */       node.left = null;
/* 287 */     } else if (right != null) {
/* 288 */       replaceInParent(node, right);
/* 289 */       node.right = null;
/*     */     } else {
/* 291 */       replaceInParent(node, null);
/*     */     } 
/*     */     
/* 294 */     rebalance(originalParent, false);
/* 295 */     this.size--;
/* 296 */     this.modCount++;
/*     */   }
/*     */   
/*     */   Node<K, V> removeInternalByKey(Object key) {
/* 300 */     Node<K, V> node = findByObject(key);
/* 301 */     if (node != null) {
/* 302 */       removeInternal(node, true);
/*     */     }
/* 304 */     return node;
/*     */   }
/*     */   
/*     */   private void replaceInParent(Node<K, V> node, Node<K, V> replacement) {
/* 308 */     Node<K, V> parent = node.parent;
/* 309 */     node.parent = null;
/* 310 */     if (replacement != null) {
/* 311 */       replacement.parent = parent;
/*     */     }
/*     */     
/* 314 */     if (parent != null) {
/* 315 */       if (parent.left == node) {
/* 316 */         parent.left = replacement;
/*     */       } else {
/* 318 */         assert parent.right == node;
/* 319 */         parent.right = replacement;
/*     */       } 
/*     */     } else {
/* 322 */       this.root = replacement;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rebalance(Node<K, V> unbalanced, boolean insert) {
/* 334 */     for (Node<K, V> node = unbalanced; node != null; node = node.parent) {
/* 335 */       Node<K, V> left = node.left;
/* 336 */       Node<K, V> right = node.right;
/* 337 */       int leftHeight = (left != null) ? left.height : 0;
/* 338 */       int rightHeight = (right != null) ? right.height : 0;
/*     */       
/* 340 */       int delta = leftHeight - rightHeight;
/* 341 */       if (delta == -2) {
/* 342 */         Node<K, V> rightLeft = right.left;
/* 343 */         Node<K, V> rightRight = right.right;
/* 344 */         int rightRightHeight = (rightRight != null) ? rightRight.height : 0;
/* 345 */         int rightLeftHeight = (rightLeft != null) ? rightLeft.height : 0;
/*     */         
/* 347 */         int rightDelta = rightLeftHeight - rightRightHeight;
/* 348 */         if (rightDelta == -1 || (rightDelta == 0 && !insert)) {
/* 349 */           rotateLeft(node);
/*     */         } else {
/* 351 */           assert rightDelta == 1;
/* 352 */           rotateRight(right);
/* 353 */           rotateLeft(node);
/*     */         } 
/* 355 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 359 */       else if (delta == 2) {
/* 360 */         Node<K, V> leftLeft = left.left;
/* 361 */         Node<K, V> leftRight = left.right;
/* 362 */         int leftRightHeight = (leftRight != null) ? leftRight.height : 0;
/* 363 */         int leftLeftHeight = (leftLeft != null) ? leftLeft.height : 0;
/*     */         
/* 365 */         int leftDelta = leftLeftHeight - leftRightHeight;
/* 366 */         if (leftDelta == 1 || (leftDelta == 0 && !insert)) {
/* 367 */           rotateRight(node);
/*     */         } else {
/* 369 */           assert leftDelta == -1;
/* 370 */           rotateLeft(left);
/* 371 */           rotateRight(node);
/*     */         } 
/* 373 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       }
/* 377 */       else if (delta == 0) {
/* 378 */         node.height = leftHeight + 1;
/* 379 */         if (insert) {
/*     */           break;
/*     */         }
/*     */       } else {
/*     */         
/* 384 */         assert delta == -1 || delta == 1;
/* 385 */         node.height = Math.max(leftHeight, rightHeight) + 1;
/* 386 */         if (!insert) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateLeft(Node<K, V> root) {
/* 397 */     Node<K, V> left = root.left;
/* 398 */     Node<K, V> pivot = root.right;
/* 399 */     Node<K, V> pivotLeft = pivot.left;
/* 400 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 403 */     root.right = pivotLeft;
/* 404 */     if (pivotLeft != null) {
/* 405 */       pivotLeft.parent = root;
/*     */     }
/*     */     
/* 408 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 411 */     pivot.left = root;
/* 412 */     root.parent = pivot;
/*     */ 
/*     */     
/* 415 */     root.height = Math.max((left != null) ? left.height : 0, 
/* 416 */         (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/* 417 */     pivot.height = Math.max(root.height, 
/* 418 */         (pivotRight != null) ? pivotRight.height : 0) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rotateRight(Node<K, V> root) {
/* 425 */     Node<K, V> pivot = root.left;
/* 426 */     Node<K, V> right = root.right;
/* 427 */     Node<K, V> pivotLeft = pivot.left;
/* 428 */     Node<K, V> pivotRight = pivot.right;
/*     */ 
/*     */     
/* 431 */     root.left = pivotRight;
/* 432 */     if (pivotRight != null) {
/* 433 */       pivotRight.parent = root;
/*     */     }
/*     */     
/* 436 */     replaceInParent(root, pivot);
/*     */ 
/*     */     
/* 439 */     pivot.right = root;
/* 440 */     root.parent = pivot;
/*     */ 
/*     */     
/* 443 */     root.height = Math.max((right != null) ? right.height : 0, 
/* 444 */         (pivotRight != null) ? pivotRight.height : 0) + 1;
/* 445 */     pivot.height = Math.max(root.height, 
/* 446 */         (pivotLeft != null) ? pivotLeft.height : 0) + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/* 453 */     EntrySet result = this.entrySet;
/* 454 */     return (result != null) ? result : (this.entrySet = new EntrySet());
/*     */   }
/*     */   
/*     */   public Set<K> keySet() {
/* 458 */     KeySet result = this.keySet;
/* 459 */     return (result != null) ? result : (this.keySet = new KeySet());
/*     */   }
/*     */   
/*     */   static final class Node<K, V>
/*     */     implements Map.Entry<K, V> {
/*     */     Node<K, V> parent;
/*     */     Node<K, V> left;
/*     */     Node<K, V> right;
/*     */     Node<K, V> next;
/*     */     Node<K, V> prev;
/*     */     final K key;
/*     */     final boolean allowNullValue;
/*     */     V value;
/*     */     int height;
/*     */     
/*     */     Node(boolean allowNullValue) {
/* 475 */       this.key = null;
/* 476 */       this.allowNullValue = allowNullValue;
/* 477 */       this.next = this.prev = this;
/*     */     }
/*     */ 
/*     */     
/*     */     Node(boolean allowNullValue, Node<K, V> parent, K key, Node<K, V> next, Node<K, V> prev) {
/* 482 */       this.parent = parent;
/* 483 */       this.key = key;
/* 484 */       this.allowNullValue = allowNullValue;
/* 485 */       this.height = 1;
/* 486 */       this.next = next;
/* 487 */       this.prev = prev;
/* 488 */       prev.next = this;
/* 489 */       next.prev = this;
/*     */     }
/*     */     
/*     */     public K getKey() {
/* 493 */       return this.key;
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 497 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V value) {
/* 501 */       if (value == null && !this.allowNullValue) {
/* 502 */         throw new NullPointerException("value == null");
/*     */       }
/* 504 */       V oldValue = this.value;
/* 505 */       this.value = value;
/* 506 */       return oldValue;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 510 */       if (o instanceof Map.Entry) {
/* 511 */         Map.Entry<?, ?> other = (Map.Entry<?, ?>)o;
/* 512 */         return (((this.key == null) ? (other.getKey() == null) : this.key.equals(other.getKey())) && ((this.value == null) ? (other
/* 513 */           .getValue() == null) : this.value.equals(other.getValue())));
/*     */       } 
/* 515 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 519 */       return ((this.key == null) ? 0 : this.key.hashCode()) ^ (
/* 520 */         (this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */     
/*     */     public String toString() {
/* 524 */       return (new StringBuilder()).append(this.key).append("=").append(this.value).toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node<K, V> first() {
/* 531 */       Node<K, V> node = this;
/* 532 */       Node<K, V> child = node.left;
/* 533 */       while (child != null) {
/* 534 */         node = child;
/* 535 */         child = node.left;
/*     */       } 
/* 537 */       return node;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Node<K, V> last() {
/* 544 */       Node<K, V> node = this;
/* 545 */       Node<K, V> child = node.right;
/* 546 */       while (child != null) {
/* 547 */         node = child;
/* 548 */         child = node.right;
/*     */       } 
/* 550 */       return node;
/*     */     }
/*     */   }
/*     */   
/*     */   private abstract class LinkedTreeMapIterator<T> implements Iterator<T> {
/* 555 */     LinkedTreeMap.Node<K, V> next = LinkedTreeMap.this.header.next;
/* 556 */     LinkedTreeMap.Node<K, V> lastReturned = null;
/* 557 */     int expectedModCount = LinkedTreeMap.this.modCount;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final boolean hasNext() {
/* 563 */       return (this.next != LinkedTreeMap.this.header);
/*     */     }
/*     */     
/*     */     final LinkedTreeMap.Node<K, V> nextNode() {
/* 567 */       LinkedTreeMap.Node<K, V> e = this.next;
/* 568 */       if (e == LinkedTreeMap.this.header) {
/* 569 */         throw new NoSuchElementException();
/*     */       }
/* 571 */       if (LinkedTreeMap.this.modCount != this.expectedModCount) {
/* 572 */         throw new ConcurrentModificationException();
/*     */       }
/* 574 */       this.next = e.next;
/* 575 */       return this.lastReturned = e;
/*     */     }
/*     */     
/*     */     public final void remove() {
/* 579 */       if (this.lastReturned == null) {
/* 580 */         throw new IllegalStateException();
/*     */       }
/* 582 */       LinkedTreeMap.this.removeInternal(this.lastReturned, true);
/* 583 */       this.lastReturned = null;
/* 584 */       this.expectedModCount = LinkedTreeMap.this.modCount;
/*     */     }
/*     */   }
/*     */   
/*     */   class EntrySet extends AbstractSet<Map.Entry<K, V>> {
/*     */     public int size() {
/* 590 */       return LinkedTreeMap.this.size;
/*     */     }
/*     */     
/*     */     public Iterator<Map.Entry<K, V>> iterator() {
/* 594 */       return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<Map.Entry<K, V>>() {
/*     */           public Map.Entry<K, V> next() {
/* 596 */             return nextNode();
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 602 */       return (o instanceof Map.Entry && LinkedTreeMap.this.findByEntry((Map.Entry<?, ?>)o) != null);
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 606 */       if (!(o instanceof Map.Entry)) {
/* 607 */         return false;
/*     */       }
/*     */       
/* 610 */       LinkedTreeMap.Node<K, V> node = LinkedTreeMap.this.findByEntry((Map.Entry<?, ?>)o);
/* 611 */       if (node == null) {
/* 612 */         return false;
/*     */       }
/* 614 */       LinkedTreeMap.this.removeInternal(node, true);
/* 615 */       return true;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 619 */       LinkedTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   final class KeySet extends AbstractSet<K> {
/*     */     public int size() {
/* 625 */       return LinkedTreeMap.this.size;
/*     */     }
/*     */     
/*     */     public Iterator<K> iterator() {
/* 629 */       return new LinkedTreeMap<K, V>.LinkedTreeMapIterator<K>() {
/*     */           public K next() {
/* 631 */             return (nextNode()).key;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public boolean contains(Object o) {
/* 637 */       return LinkedTreeMap.this.containsKey(o);
/*     */     }
/*     */     
/*     */     public boolean remove(Object key) {
/* 641 */       return (LinkedTreeMap.this.removeInternalByKey(key) != null);
/*     */     }
/*     */     
/*     */     public void clear() {
/* 645 */       LinkedTreeMap.this.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object writeReplace() throws ObjectStreamException {
/* 656 */     return new LinkedHashMap<>(this);
/*     */   }
/*     */ 
/*     */   
/*     */   private void readObject(ObjectInputStream in) throws IOException {
/* 661 */     throw new InvalidObjectException("Deserialization is unsupported");
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\internal\LinkedTreeMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */