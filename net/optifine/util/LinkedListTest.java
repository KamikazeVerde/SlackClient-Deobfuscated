/*     */ package net.optifine.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.optifine.render.VboRange;
/*     */ 
/*     */ public class LinkedListTest
/*     */ {
/*     */   public static void main(String[] args) throws Exception {
/*  11 */     LinkedList<VboRange> linkedlist = new LinkedList<>();
/*  12 */     List<VboRange> list = new ArrayList<>();
/*  13 */     List<VboRange> list1 = new ArrayList<>();
/*  14 */     Random random = new Random();
/*  15 */     int i = 100;
/*     */     
/*  17 */     for (int j = 0; j < i; j++) {
/*  18 */       VboRange vborange = new VboRange();
/*  19 */       vborange.setPosition(j);
/*  20 */       list.add(vborange);
/*     */     } 
/*     */     
/*  23 */     for (int k = 0; k < 100000; k++) {
/*  24 */       checkLists(list, list1, i);
/*  25 */       checkLinkedList(linkedlist, list1.size());
/*     */       
/*  27 */       if (k % 5 == 0) {
/*  28 */         dbgLinkedList(linkedlist);
/*     */       }
/*     */       
/*  31 */       if (random.nextBoolean()) {
/*  32 */         if (!list.isEmpty()) {
/*  33 */           VboRange vborange3 = list.get(random.nextInt(list.size()));
/*  34 */           LinkedList.Node<VboRange> node2 = vborange3.getNode();
/*     */           
/*  36 */           if (random.nextBoolean()) {
/*  37 */             linkedlist.addFirst(node2);
/*  38 */             dbg("Add first: " + vborange3.getPosition());
/*  39 */           } else if (random.nextBoolean()) {
/*  40 */             linkedlist.addLast(node2);
/*  41 */             dbg("Add last: " + vborange3.getPosition());
/*     */           } else {
/*  43 */             if (list1.isEmpty()) {
/*     */               continue;
/*     */             }
/*     */             
/*  47 */             VboRange vborange1 = list1.get(random.nextInt(list1.size()));
/*  48 */             LinkedList.Node<VboRange> node1 = vborange1.getNode();
/*  49 */             linkedlist.addAfter(node1, node2);
/*  50 */             dbg("Add after: " + vborange1.getPosition() + ", " + vborange3.getPosition());
/*     */           } 
/*     */           
/*  53 */           list.remove(vborange3);
/*  54 */           list1.add(vborange3);
/*     */         }  continue;
/*  56 */       }  if (!list1.isEmpty()) {
/*  57 */         VboRange vborange2 = list1.get(random.nextInt(list1.size()));
/*  58 */         LinkedList.Node<VboRange> node = vborange2.getNode();
/*  59 */         linkedlist.remove(node);
/*  60 */         dbg("Remove: " + vborange2.getPosition());
/*  61 */         list1.remove(vborange2);
/*  62 */         list.add(vborange2);
/*     */       } 
/*     */       continue;
/*     */     } 
/*     */   }
/*     */   private static void dbgLinkedList(LinkedList<VboRange> linkedList) {
/*  68 */     StringBuffer stringbuffer = new StringBuffer();
/*     */     
/*  70 */     linkedList.iterator().forEachRemaining(vboRangeNode -> {
/*     */           LinkedList.Node<VboRange> node = vboRangeNode;
/*     */           
/*     */           if (node.getItem() == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           VboRange vborange = node.getItem();
/*     */           
/*     */           if (stringbuffer.length() > 0) {
/*     */             stringbuffer.append(", ");
/*     */           }
/*     */           stringbuffer.append(vborange.getPosition());
/*     */         });
/*  84 */     dbg("List: " + stringbuffer);
/*     */   }
/*     */   
/*     */   private static void checkLinkedList(LinkedList<VboRange> linkedList, int used) {
/*  88 */     if (linkedList.getSize() != used) {
/*  89 */       throw new RuntimeException("Wrong size, linked: " + linkedList.getSize() + ", used: " + used);
/*     */     }
/*  91 */     int i = 0;
/*     */     
/*  93 */     for (LinkedList.Node<VboRange> node = linkedList.getFirst(); node != null; node = node.getNext()) {
/*  94 */       i++;
/*     */     }
/*     */     
/*  97 */     if (linkedList.getSize() != i) {
/*  98 */       throw new RuntimeException("Wrong count, linked: " + linkedList.getSize() + ", count: " + i);
/*     */     }
/* 100 */     int j = 0;
/*     */     
/* 102 */     for (LinkedList.Node<VboRange> node1 = linkedList.getLast(); node1 != null; node1 = node1.getPrev()) {
/* 103 */       j++;
/*     */     }
/*     */     
/* 106 */     if (linkedList.getSize() != j) {
/* 107 */       throw new RuntimeException("Wrong count back, linked: " + linkedList.getSize() + ", count: " + j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkLists(List<VboRange> listFree, List<VboRange> listUsed, int count) {
/* 114 */     int i = listFree.size() + listUsed.size();
/*     */     
/* 116 */     if (i != count) {
/* 117 */       throw new RuntimeException("Total size: " + i);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void dbg(String str) {
/* 122 */     System.out.println(str);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifin\\util\LinkedListTest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */