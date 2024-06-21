/*    */ package com.viaversion.viaversion.bukkit.tasks.protocol1_12to1_11_1;
/*    */ 
/*    */ import com.viaversion.viaversion.bukkit.providers.BukkitInventoryQuickMoveProvider;
/*    */ import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.storage.ItemTransaction;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
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
/*    */ public class BukkitInventoryUpdateTask
/*    */   implements Runnable
/*    */ {
/*    */   private final BukkitInventoryQuickMoveProvider provider;
/*    */   private final UUID uuid;
/*    */   private final List<ItemTransaction> items;
/*    */   
/*    */   public BukkitInventoryUpdateTask(BukkitInventoryQuickMoveProvider provider, UUID uuid) {
/* 36 */     this.provider = provider;
/* 37 */     this.uuid = uuid;
/* 38 */     this.items = Collections.synchronizedList(new ArrayList<>());
/*    */   }
/*    */   
/*    */   public void addItem(short windowId, short slotId, short actionId) {
/* 42 */     ItemTransaction storage = new ItemTransaction(windowId, slotId, actionId);
/* 43 */     this.items.add(storage);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 48 */     Player p = Bukkit.getServer().getPlayer(this.uuid);
/* 49 */     if (p == null) {
/* 50 */       this.provider.onTaskExecuted(this.uuid);
/*    */       return;
/*    */     } 
/*    */     try {
/* 54 */       synchronized (this.items) {
/* 55 */         for (ItemTransaction storage : this.items) {
/* 56 */           Object packet = this.provider.buildWindowClickPacket(p, storage);
/* 57 */           boolean result = this.provider.sendPacketToServer(p, packet);
/* 58 */           if (!result) {
/*    */             break;
/*    */           }
/*    */         } 
/* 62 */         this.items.clear();
/*    */       } 
/*    */     } finally {
/* 65 */       this.provider.onTaskExecuted(this.uuid);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\bukkit\tasks\protocol1_12to1_11_1\BukkitInventoryUpdateTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */