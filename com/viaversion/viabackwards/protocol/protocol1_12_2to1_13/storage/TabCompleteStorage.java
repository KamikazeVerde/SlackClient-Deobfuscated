/*    */ package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;
/*    */ 
/*    */ import com.viaversion.viaversion.api.connection.StorableObject;
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
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
/*    */ public class TabCompleteStorage
/*    */   implements StorableObject
/*    */ {
/* 28 */   private final Map<UUID, String> usernames = new HashMap<>();
/* 29 */   private final Set<String> commands = new HashSet<>();
/*    */   private int lastId;
/*    */   private String lastRequest;
/*    */   private boolean lastAssumeCommand;
/*    */   
/*    */   public Map<UUID, String> usernames() {
/* 35 */     return this.usernames;
/*    */   }
/*    */   
/*    */   public Set<String> commands() {
/* 39 */     return this.commands;
/*    */   }
/*    */   
/*    */   public int lastId() {
/* 43 */     return this.lastId;
/*    */   }
/*    */   
/*    */   public void setLastId(int lastId) {
/* 47 */     this.lastId = lastId;
/*    */   }
/*    */   
/*    */   public String lastRequest() {
/* 51 */     return this.lastRequest;
/*    */   }
/*    */   
/*    */   public void setLastRequest(String lastRequest) {
/* 55 */     this.lastRequest = lastRequest;
/*    */   }
/*    */   
/*    */   public boolean isLastAssumeCommand() {
/* 59 */     return this.lastAssumeCommand;
/*    */   }
/*    */   
/*    */   public void setLastAssumeCommand(boolean lastAssumeCommand) {
/* 63 */     this.lastAssumeCommand = lastAssumeCommand;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viabackwards\protocol\protocol1_12_2to1_13\storage\TabCompleteStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */