/*    */ package org.yaml.snakeyaml.tokens;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.yaml.snakeyaml.error.Mark;
/*    */ import org.yaml.snakeyaml.error.YAMLException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class DirectiveToken<T>
/*    */   extends Token
/*    */ {
/*    */   private final String name;
/*    */   private final List<T> value;
/*    */   
/*    */   public DirectiveToken(String name, List<T> value, Mark startMark, Mark endMark) {
/* 39 */     super(startMark, endMark);
/* 40 */     this.name = name;
/* 41 */     if (value != null && value.size() != 2) {
/* 42 */       throw new YAMLException("Two strings must be provided instead of " + value.size());
/*    */     }
/* 44 */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 53 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<T> getValue() {
/* 62 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Token.ID getTokenId() {
/* 72 */     return Token.ID.Directive;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\tokens\DirectiveToken.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */