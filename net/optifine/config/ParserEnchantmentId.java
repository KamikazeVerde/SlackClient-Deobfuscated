/*    */ package net.optifine.config;
/*    */ 
/*    */ import net.minecraft.enchantment.Enchantment;
/*    */ 
/*    */ public class ParserEnchantmentId
/*    */   implements IParserInt
/*    */ {
/*    */   public int parse(String str, int defVal) {
/*  9 */     Enchantment enchantment = Enchantment.getEnchantmentByLocation(str);
/* 10 */     return (enchantment == null) ? defVal : enchantment.effectId;
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\config\ParserEnchantmentId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */