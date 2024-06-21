/*    */ package org.yaml.snakeyaml.tokens;
/*    */ 
/*    */ import org.yaml.snakeyaml.error.Mark;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class BlockEntryToken
/*    */   extends Token
/*    */ {
/*    */   public BlockEntryToken(Mark startMark, Mark endMark) {
/* 30 */     super(startMark, endMark);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Token.ID getTokenId() {
/* 40 */     return Token.ID.BlockEntry;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\tokens\BlockEntryToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */