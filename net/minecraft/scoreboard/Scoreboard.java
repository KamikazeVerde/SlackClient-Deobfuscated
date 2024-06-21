/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ChatFormatting;
/*     */ 
/*     */ 
/*     */ public class Scoreboard
/*     */ {
/*  15 */   private final Map<String, ScoreObjective> scoreObjectives = Maps.newHashMap();
/*  16 */   private final Map<IScoreObjectiveCriteria, List<ScoreObjective>> scoreObjectiveCriterias = Maps.newHashMap();
/*  17 */   private final Map<String, Map<ScoreObjective, Score>> entitiesScoreObjectives = Maps.newHashMap();
/*     */ 
/*     */   
/*  20 */   private final ScoreObjective[] objectiveDisplaySlots = new ScoreObjective[19];
/*  21 */   private final Map<String, ScorePlayerTeam> teams = Maps.newHashMap();
/*  22 */   private final Map<String, ScorePlayerTeam> teamMemberships = Maps.newHashMap();
/*  23 */   private static String[] field_178823_g = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreObjective getObjective(String name) {
/*  32 */     return this.scoreObjectives.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreObjective addScoreObjective(String name, IScoreObjectiveCriteria criteria) {
/*  43 */     if (name.length() > 16)
/*     */     {
/*  45 */       throw new IllegalArgumentException("The objective name '" + name + "' is too long!");
/*     */     }
/*     */ 
/*     */     
/*  49 */     ScoreObjective scoreobjective = getObjective(name);
/*     */     
/*  51 */     if (scoreobjective != null)
/*     */     {
/*  53 */       throw new IllegalArgumentException("An objective with the name '" + name + "' already exists!");
/*     */     }
/*     */ 
/*     */     
/*  57 */     scoreobjective = new ScoreObjective(this, name, criteria);
/*  58 */     List<ScoreObjective> list = this.scoreObjectiveCriterias.get(criteria);
/*     */     
/*  60 */     if (list == null) {
/*     */       
/*  62 */       list = Lists.newArrayList();
/*  63 */       this.scoreObjectiveCriterias.put(criteria, list);
/*     */     } 
/*     */     
/*  66 */     list.add(scoreobjective);
/*  67 */     this.scoreObjectives.put(name, scoreobjective);
/*  68 */     onScoreObjectiveAdded(scoreobjective);
/*  69 */     return scoreobjective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ScoreObjective> getObjectivesFromCriteria(IScoreObjectiveCriteria criteria) {
/*  76 */     Collection<ScoreObjective> collection = this.scoreObjectiveCriterias.get(criteria);
/*  77 */     return (collection == null) ? Lists.newArrayList() : Lists.newArrayList(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean entityHasObjective(String name, ScoreObjective p_178819_2_) {
/*  87 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/*  89 */     if (map == null)
/*     */     {
/*  91 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  95 */     Score score = map.get(p_178819_2_);
/*  96 */     return (score != null);
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
/*     */   public Score getValueFromObjective(String name, ScoreObjective objective) {
/* 108 */     if (name.length() > 40)
/*     */     {
/* 110 */       throw new IllegalArgumentException("The player name '" + name + "' is too long!");
/*     */     }
/*     */ 
/*     */     
/* 114 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/* 116 */     if (map == null) {
/*     */       
/* 118 */       map = Maps.newHashMap();
/* 119 */       this.entitiesScoreObjectives.put(name, map);
/*     */     } 
/*     */     
/* 122 */     Score score = map.get(objective);
/*     */     
/* 124 */     if (score == null) {
/*     */       
/* 126 */       score = new Score(this, objective, name);
/* 127 */       map.put(objective, score);
/*     */     } 
/*     */     
/* 130 */     return score;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Score> getSortedScores(ScoreObjective objective) {
/* 136 */     List<Score> list = Lists.newArrayList();
/*     */     
/* 138 */     for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values()) {
/*     */       
/* 140 */       Score score = map.get(objective);
/*     */       
/* 142 */       if (score != null)
/*     */       {
/* 144 */         list.add(score);
/*     */       }
/*     */     } 
/*     */     
/* 148 */     Collections.sort(list, Score.scoreComparator);
/* 149 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ScoreObjective> getScoreObjectives() {
/* 154 */     return this.scoreObjectives.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getObjectiveNames() {
/* 159 */     return this.entitiesScoreObjectives.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObjectiveFromEntity(String name, ScoreObjective objective) {
/* 170 */     if (objective == null) {
/*     */       
/* 172 */       Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.remove(name);
/*     */       
/* 174 */       if (map != null)
/*     */       {
/* 176 */         func_96516_a(name);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 181 */       Map<ScoreObjective, Score> map2 = this.entitiesScoreObjectives.get(name);
/*     */       
/* 183 */       if (map2 != null) {
/*     */         
/* 185 */         Score score = map2.remove(objective);
/*     */         
/* 187 */         if (map2.size() < 1) {
/*     */           
/* 189 */           Map<ScoreObjective, Score> map1 = this.entitiesScoreObjectives.remove(name);
/*     */           
/* 191 */           if (map1 != null)
/*     */           {
/* 193 */             func_96516_a(name);
/*     */           }
/*     */         }
/* 196 */         else if (score != null) {
/*     */           
/* 198 */           func_178820_a(name, objective);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Score> getScores() {
/* 206 */     Collection<Map<ScoreObjective, Score>> collection = this.entitiesScoreObjectives.values();
/* 207 */     List<Score> list = Lists.newArrayList();
/*     */     
/* 209 */     for (Map<ScoreObjective, Score> map : collection)
/*     */     {
/* 211 */       list.addAll(map.values());
/*     */     }
/*     */     
/* 214 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ScoreObjective, Score> getObjectivesForEntity(String name) {
/* 219 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/* 221 */     if (map == null)
/*     */     {
/* 223 */       map = Maps.newHashMap();
/*     */     }
/*     */     
/* 226 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeObjective(ScoreObjective p_96519_1_) {
/* 231 */     this.scoreObjectives.remove(p_96519_1_.getName());
/*     */     
/* 233 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 235 */       if (getObjectiveInDisplaySlot(i) == p_96519_1_)
/*     */       {
/* 237 */         setObjectiveInDisplaySlot(i, null);
/*     */       }
/*     */     } 
/*     */     
/* 241 */     List<ScoreObjective> list = this.scoreObjectiveCriterias.get(p_96519_1_.getCriteria());
/*     */     
/* 243 */     if (list != null)
/*     */     {
/* 245 */       list.remove(p_96519_1_);
/*     */     }
/*     */     
/* 248 */     for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values())
/*     */     {
/* 250 */       map.remove(p_96519_1_);
/*     */     }
/*     */     
/* 253 */     func_96533_c(p_96519_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveInDisplaySlot(int p_96530_1_, ScoreObjective p_96530_2_) {
/* 261 */     this.objectiveDisplaySlots[p_96530_1_] = p_96530_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreObjective getObjectiveInDisplaySlot(int p_96539_1_) {
/* 269 */     return this.objectiveDisplaySlots[p_96539_1_];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam getTeam(String p_96508_1_) {
/* 277 */     return this.teams.get(p_96508_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam createTeam(String p_96527_1_) {
/* 282 */     if (p_96527_1_.length() > 16)
/*     */     {
/* 284 */       throw new IllegalArgumentException("The team name '" + p_96527_1_ + "' is too long!");
/*     */     }
/*     */ 
/*     */     
/* 288 */     ScorePlayerTeam scoreplayerteam = getTeam(p_96527_1_);
/*     */     
/* 290 */     if (scoreplayerteam != null)
/*     */     {
/* 292 */       throw new IllegalArgumentException("A team with the name '" + p_96527_1_ + "' already exists!");
/*     */     }
/*     */ 
/*     */     
/* 296 */     scoreplayerteam = new ScorePlayerTeam(this, p_96527_1_);
/* 297 */     this.teams.put(p_96527_1_, scoreplayerteam);
/* 298 */     broadcastTeamCreated(scoreplayerteam);
/* 299 */     return scoreplayerteam;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTeam(ScorePlayerTeam p_96511_1_) {
/* 309 */     this.teams.remove(p_96511_1_.getRegisteredName());
/*     */     
/* 311 */     for (String s : p_96511_1_.getMembershipCollection())
/*     */     {
/* 313 */       this.teamMemberships.remove(s);
/*     */     }
/*     */     
/* 316 */     func_96513_c(p_96511_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String player, String newTeam) {
/* 327 */     if (player.length() > 40)
/*     */     {
/* 329 */       throw new IllegalArgumentException("The player name '" + player + "' is too long!");
/*     */     }
/* 331 */     if (!this.teams.containsKey(newTeam))
/*     */     {
/* 333 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 337 */     ScorePlayerTeam scoreplayerteam = getTeam(newTeam);
/*     */     
/* 339 */     if (getPlayersTeam(player) != null)
/*     */     {
/* 341 */       removePlayerFromTeams(player);
/*     */     }
/*     */     
/* 344 */     this.teamMemberships.put(player, scoreplayerteam);
/* 345 */     scoreplayerteam.getMembershipCollection().add(player);
/* 346 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removePlayerFromTeams(String p_96524_1_) {
/* 352 */     ScorePlayerTeam scoreplayerteam = getPlayersTeam(p_96524_1_);
/*     */     
/* 354 */     if (scoreplayerteam != null) {
/*     */       
/* 356 */       removePlayerFromTeam(p_96524_1_, scoreplayerteam);
/* 357 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 361 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_) {
/* 371 */     if (getPlayersTeam(p_96512_1_) != p_96512_2_)
/*     */     {
/* 373 */       throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + p_96512_2_.getRegisteredName() + "'.");
/*     */     }
/*     */ 
/*     */     
/* 377 */     this.teamMemberships.remove(p_96512_1_);
/* 378 */     p_96512_2_.getMembershipCollection().remove(p_96512_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getTeamNames() {
/* 384 */     return this.teams.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ScorePlayerTeam> getTeams() {
/* 389 */     return this.teams.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam getPlayersTeam(String p_96509_1_) {
/* 397 */     return this.teamMemberships.get(p_96509_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96532_b(ScoreObjective p_96532_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96533_c(ScoreObjective p_96533_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96536_a(Score p_96536_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96516_a(String p_96516_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_178820_a(String p_178820_1_, ScoreObjective p_178820_2_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamCreated(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendTeamUpdate(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_96513_c(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getObjectiveDisplaySlot(int p_96517_0_) {
/* 450 */     switch (p_96517_0_) {
/*     */       
/*     */       case 0:
/* 453 */         return "list";
/*     */       
/*     */       case 1:
/* 456 */         return "sidebar";
/*     */       
/*     */       case 2:
/* 459 */         return "belowName";
/*     */     } 
/*     */     
/* 462 */     if (p_96517_0_ >= 3 && p_96517_0_ <= 18) {
/*     */       
/* 464 */       ChatFormatting enumchatformatting = ChatFormatting.func_175744_a(p_96517_0_ - 3);
/*     */       
/* 466 */       if (enumchatformatting != null && enumchatformatting != ChatFormatting.RESET)
/*     */       {
/* 468 */         return "sidebar.team." + enumchatformatting.getFriendlyName();
/*     */       }
/*     */     } 
/*     */     
/* 472 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getObjectiveDisplaySlotNumber(String p_96537_0_) {
/* 481 */     if (p_96537_0_.equalsIgnoreCase("list"))
/*     */     {
/* 483 */       return 0;
/*     */     }
/* 485 */     if (p_96537_0_.equalsIgnoreCase("sidebar"))
/*     */     {
/* 487 */       return 1;
/*     */     }
/* 489 */     if (p_96537_0_.equalsIgnoreCase("belowName"))
/*     */     {
/* 491 */       return 2;
/*     */     }
/*     */ 
/*     */     
/* 495 */     if (p_96537_0_.startsWith("sidebar.team.")) {
/*     */       
/* 497 */       String s = p_96537_0_.substring("sidebar.team.".length());
/* 498 */       ChatFormatting enumchatformatting = ChatFormatting.getValueByName(s);
/*     */       
/* 500 */       if (enumchatformatting != null && enumchatformatting.getColorIndex() >= 0)
/*     */       {
/* 502 */         return enumchatformatting.getColorIndex() + 3;
/*     */       }
/*     */     } 
/*     */     
/* 506 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getDisplaySlotStrings() {
/* 512 */     if (field_178823_g == null) {
/*     */       
/* 514 */       field_178823_g = new String[19];
/*     */       
/* 516 */       for (int i = 0; i < 19; i++)
/*     */       {
/* 518 */         field_178823_g[i] = getObjectiveDisplaySlot(i);
/*     */       }
/*     */     } 
/*     */     
/* 522 */     return field_178823_g;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_181140_a(Entity p_181140_1_) {
/* 527 */     if (p_181140_1_ != null && !(p_181140_1_ instanceof net.minecraft.entity.player.EntityPlayer) && !p_181140_1_.isEntityAlive()) {
/*     */       
/* 529 */       String s = p_181140_1_.getUniqueID().toString();
/* 530 */       removeObjectiveFromEntity(s, null);
/* 531 */       removePlayerFromTeams(s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\scoreboard\Scoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */