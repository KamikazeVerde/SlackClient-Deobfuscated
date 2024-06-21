/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.ChatFormatting;
/*    */ 
/*    */ public interface IScoreObjectiveCriteria
/*    */ {
/* 11 */   public static final Map<String, IScoreObjectiveCriteria> INSTANCES = Maps.newHashMap();
/* 12 */   public static final IScoreObjectiveCriteria DUMMY = new ScoreDummyCriteria("dummy");
/* 13 */   public static final IScoreObjectiveCriteria TRIGGER = new ScoreDummyCriteria("trigger");
/* 14 */   public static final IScoreObjectiveCriteria deathCount = new ScoreDummyCriteria("deathCount");
/* 15 */   public static final IScoreObjectiveCriteria playerKillCount = new ScoreDummyCriteria("playerKillCount");
/* 16 */   public static final IScoreObjectiveCriteria totalKillCount = new ScoreDummyCriteria("totalKillCount");
/* 17 */   public static final IScoreObjectiveCriteria health = new ScoreHealthCriteria("health");
/* 18 */   public static final IScoreObjectiveCriteria[] field_178792_h = new IScoreObjectiveCriteria[] { new GoalColor("teamkill.", ChatFormatting.BLACK), new GoalColor("teamkill.", ChatFormatting.DARK_BLUE), new GoalColor("teamkill.", ChatFormatting.DARK_GREEN), new GoalColor("teamkill.", ChatFormatting.DARK_AQUA), new GoalColor("teamkill.", ChatFormatting.DARK_RED), new GoalColor("teamkill.", ChatFormatting.DARK_PURPLE), new GoalColor("teamkill.", ChatFormatting.GOLD), new GoalColor("teamkill.", ChatFormatting.GRAY), new GoalColor("teamkill.", ChatFormatting.DARK_GRAY), new GoalColor("teamkill.", ChatFormatting.BLUE), new GoalColor("teamkill.", ChatFormatting.GREEN), new GoalColor("teamkill.", ChatFormatting.AQUA), new GoalColor("teamkill.", ChatFormatting.RED), new GoalColor("teamkill.", ChatFormatting.LIGHT_PURPLE), new GoalColor("teamkill.", ChatFormatting.YELLOW), new GoalColor("teamkill.", ChatFormatting.WHITE) };
/* 19 */   public static final IScoreObjectiveCriteria[] field_178793_i = new IScoreObjectiveCriteria[] { new GoalColor("killedByTeam.", ChatFormatting.BLACK), new GoalColor("killedByTeam.", ChatFormatting.DARK_BLUE), new GoalColor("killedByTeam.", ChatFormatting.DARK_GREEN), new GoalColor("killedByTeam.", ChatFormatting.DARK_AQUA), new GoalColor("killedByTeam.", ChatFormatting.DARK_RED), new GoalColor("killedByTeam.", ChatFormatting.DARK_PURPLE), new GoalColor("killedByTeam.", ChatFormatting.GOLD), new GoalColor("killedByTeam.", ChatFormatting.GRAY), new GoalColor("killedByTeam.", ChatFormatting.DARK_GRAY), new GoalColor("killedByTeam.", ChatFormatting.BLUE), new GoalColor("killedByTeam.", ChatFormatting.GREEN), new GoalColor("killedByTeam.", ChatFormatting.AQUA), new GoalColor("killedByTeam.", ChatFormatting.RED), new GoalColor("killedByTeam.", ChatFormatting.LIGHT_PURPLE), new GoalColor("killedByTeam.", ChatFormatting.YELLOW), new GoalColor("killedByTeam.", ChatFormatting.WHITE) };
/*    */   
/*    */   String getName();
/*    */   
/*    */   int func_96635_a(List<EntityPlayer> paramList);
/*    */   
/*    */   boolean isReadOnly();
/*    */   
/*    */   EnumRenderType getRenderType();
/*    */   
/*    */   public enum EnumRenderType
/*    */   {
/* 31 */     INTEGER("integer"),
/* 32 */     HEARTS("hearts");
/*    */     
/* 34 */     private static final Map<String, EnumRenderType> field_178801_c = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final String field_178798_d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 54 */       for (EnumRenderType iscoreobjectivecriteria$enumrendertype : values())
/*    */       {
/* 56 */         field_178801_c.put(iscoreobjectivecriteria$enumrendertype.func_178796_a(), iscoreobjectivecriteria$enumrendertype);
/*    */       }
/*    */     }
/*    */     
/*    */     EnumRenderType(String p_i45548_3_) {
/*    */       this.field_178798_d = p_i45548_3_;
/*    */     }
/*    */     
/*    */     public String func_178796_a() {
/*    */       return this.field_178798_d;
/*    */     }
/*    */     
/*    */     public static EnumRenderType func_178795_a(String p_178795_0_) {
/*    */       EnumRenderType iscoreobjectivecriteria$enumrendertype = field_178801_c.get(p_178795_0_);
/*    */       return (iscoreobjectivecriteria$enumrendertype == null) ? INTEGER : iscoreobjectivecriteria$enumrendertype;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\minecraft\scoreboard\IScoreObjectiveCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */