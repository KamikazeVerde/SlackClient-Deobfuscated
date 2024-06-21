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
/*    */ 
/*    */ public final class AliasToken
/*    */   extends Token
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public AliasToken(String value, Mark startMark, Mark endMark) {
/* 33 */     super(startMark, endMark);
/* 34 */     if (value == null) {
/* 35 */       throw new NullPointerException("alias is expected");
/*    */     }
/* 37 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 46 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public Token.ID getTokenId() {
/* 51 */     return Token.ID.Alias;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\tokens\AliasToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */