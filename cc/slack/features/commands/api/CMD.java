/*    */ package cc.slack.features.commands.api;
/*    */ 
/*    */ 
/*    */ public abstract class CMD
/*    */ {
/*    */   public abstract void onCommand(String[] paramArrayOfString, String paramString);
/*    */   
/*  8 */   final CMDInfo cmdInfo = getClass().<CMDInfo>getAnnotation(CMDInfo.class); public CMDInfo getCmdInfo() { return this.cmdInfo; }
/*  9 */    private final String name = this.cmdInfo.name(); public String getName() { return this.name; }
/* 10 */    private final String description = this.cmdInfo.description(); public String getDescription() { return this.description; }
/* 11 */    private final String alias = this.cmdInfo.alias(); public String getAlias() { return this.alias; }
/*    */ 
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\cc\slack\features\commands\api\CMD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */