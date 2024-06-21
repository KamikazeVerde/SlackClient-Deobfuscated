/*     */ package net.minecraft.util;
/*     */ 
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ 
/*     */ public class ChatComponentScore
/*     */   extends ChatComponentStyle
/*     */ {
/*     */   private final String name;
/*     */   private final String objective;
/*  14 */   private String value = "";
/*     */ 
/*     */   
/*     */   public ChatComponentScore(String nameIn, String objectiveIn) {
/*  18 */     this.name = nameIn;
/*  19 */     this.objective = objectiveIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  24 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getObjective() {
/*  29 */     return this.objective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String valueIn) {
/*  39 */     this.value = valueIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnformattedTextForChat() {
/*  48 */     MinecraftServer minecraftserver = MinecraftServer.getServer();
/*     */     
/*  50 */     if (minecraftserver != null && minecraftserver.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value)) {
/*     */       
/*  52 */       Scoreboard scoreboard = minecraftserver.worldServerForDimension(0).getScoreboard();
/*  53 */       ScoreObjective scoreobjective = scoreboard.getObjective(this.objective);
/*     */       
/*  55 */       if (scoreboard.entityHasObjective(this.name, scoreobjective)) {
/*     */         
/*  57 */         Score score = scoreboard.getValueFromObjective(this.name, scoreobjective);
/*  58 */         setValue(String.format("%d", new Object[] { Integer.valueOf(score.getScorePoints()) }));
/*     */       }
/*     */       else {
/*     */         
/*  62 */         this.value = "";
/*     */       } 
/*     */     } 
/*     */     
/*  66 */     return this.value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatComponentScore createCopy() {
/*  74 */     ChatComponentScore chatcomponentscore = new ChatComponentScore(this.name, this.objective);
/*  75 */     chatcomponentscore.setValue(this.value);
/*  76 */     chatcomponentscore.setChatStyle(getChatStyle().createShallowCopy());
/*     */     
/*  78 */     for (IChatComponent ichatcomponent : getSiblings())
/*     */     {
/*  80 */       chatcomponentscore.appendSibling(ichatcomponent.createCopy());
/*     */     }
/*     */     
/*  83 */     return chatcomponentscore;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  88 */     if (this == p_equals_1_)
/*     */     {
/*  90 */       return true;
/*     */     }
/*  92 */     if (!(p_equals_1_ instanceof ChatComponentScore))
/*     */     {
/*  94 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  98 */     ChatComponentScore chatcomponentscore = (ChatComponentScore)p_equals_1_;
/*  99 */     return (this.name.equals(chatcomponentscore.name) && this.objective.equals(chatcomponentscore.objective) && super.equals(p_equals_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return "ScoreComponent{name='" + this.name + '\'' + "objective='" + this.objective + '\'' + ", siblings=" + this.siblings + ", style=" + getChatStyle() + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraf\\util\ChatComponentScore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */