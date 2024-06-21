/*     */ package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;
/*     */ 
/*     */ import com.viaversion.viaversion.api.connection.StorableObject;
/*     */ import com.viaversion.viaversion.api.connection.UserConnection;
/*     */ import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class InventoryTracker
/*     */   implements StorableObject
/*     */ {
/*     */   private String inventory;
/*  29 */   private final Map<Short, Map<Short, Integer>> windowItemCache = new HashMap<>();
/*  30 */   private int itemIdInCursor = 0;
/*     */   private boolean dragging = false;
/*     */   
/*     */   public String getInventory() {
/*  34 */     return this.inventory;
/*     */   }
/*     */   
/*     */   public void setInventory(String inventory) {
/*  38 */     this.inventory = inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetInventory(short windowId) {
/*  43 */     if (this.inventory == null) {
/*  44 */       this.itemIdInCursor = 0;
/*  45 */       this.dragging = false;
/*     */ 
/*     */       
/*  48 */       if (windowId != 0) {
/*  49 */         this.windowItemCache.remove(Short.valueOf(windowId));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getItemId(short windowId, short slot) {
/*  55 */     Map<Short, Integer> itemMap = this.windowItemCache.get(Short.valueOf(windowId));
/*  56 */     if (itemMap == null) {
/*  57 */       return 0;
/*     */     }
/*     */     
/*  60 */     return ((Integer)itemMap.getOrDefault(Short.valueOf(slot), Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   public void setItemId(short windowId, short slot, int itemId) {
/*  64 */     if (windowId == -1 && slot == -1) {
/*     */       
/*  66 */       this.itemIdInCursor = itemId;
/*     */     } else {
/*     */       
/*  69 */       ((Map<Short, Integer>)this.windowItemCache.computeIfAbsent(Short.valueOf(windowId), k -> new HashMap<>())).put(Short.valueOf(slot), Integer.valueOf(itemId));
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
/*     */   public void handleWindowClick(UserConnection user, short windowId, byte mode, short hoverSlot, byte button) {
/*     */     int hoverItem;
/*  82 */     EntityTracker1_9 entityTracker = (EntityTracker1_9)user.getEntityTracker(Protocol1_9To1_8.class);
/*     */ 
/*     */     
/*  85 */     if (hoverSlot == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (hoverSlot == 45) {
/*  91 */       entityTracker.setSecondHand(null);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  96 */     boolean isArmorOrResultSlot = ((hoverSlot >= 5 && hoverSlot <= 8) || hoverSlot == 0);
/*     */     
/*  98 */     switch (mode) {
/*     */ 
/*     */       
/*     */       case 0:
/* 102 */         if (this.itemIdInCursor == 0) {
/*     */           
/* 104 */           this.itemIdInCursor = getItemId(windowId, hoverSlot);
/*     */ 
/*     */           
/* 107 */           setItemId(windowId, hoverSlot, 0);
/*     */           break;
/*     */         } 
/* 110 */         if (hoverSlot == -999) {
/* 111 */           this.itemIdInCursor = 0; break;
/* 112 */         }  if (!isArmorOrResultSlot) {
/* 113 */           int previousItem = getItemId(windowId, hoverSlot);
/*     */ 
/*     */           
/* 116 */           setItemId(windowId, hoverSlot, this.itemIdInCursor);
/*     */ 
/*     */           
/* 119 */           this.itemIdInCursor = previousItem;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 2:
/* 124 */         if (!isArmorOrResultSlot) {
/* 125 */           short hotkeySlot = (short)(button + 36);
/*     */ 
/*     */           
/* 128 */           int sourceItem = getItemId(windowId, hoverSlot);
/* 129 */           int destinationItem = getItemId(windowId, hotkeySlot);
/*     */ 
/*     */           
/* 132 */           setItemId(windowId, hotkeySlot, sourceItem);
/* 133 */           setItemId(windowId, hoverSlot, destinationItem);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case 4:
/* 138 */         hoverItem = getItemId(windowId, hoverSlot);
/*     */         
/* 140 */         if (hoverItem != 0) {
/* 141 */           setItemId(windowId, hoverSlot, 0);
/*     */         }
/*     */         break;
/*     */       
/*     */       case 5:
/* 146 */         switch (button) {
/*     */           case 0:
/*     */           case 4:
/* 149 */             this.dragging = true;
/*     */             break;
/*     */           
/*     */           case 1:
/*     */           case 5:
/* 154 */             if (this.dragging && this.itemIdInCursor != 0 && !isArmorOrResultSlot) {
/* 155 */               int previousItem = getItemId(windowId, hoverSlot);
/*     */ 
/*     */               
/* 158 */               setItemId(windowId, hoverSlot, this.itemIdInCursor);
/*     */ 
/*     */               
/* 161 */               this.itemIdInCursor = previousItem;
/*     */             } 
/*     */             break;
/*     */           case 2:
/*     */           case 6:
/* 166 */             this.dragging = false;
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 176 */     entityTracker.syncShieldWithSword();
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\protocols\protocol1_9to1_8\storage\InventoryTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */