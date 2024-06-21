/*    */ package com.viaversion.viaversion.libs.kyori.adventure.nbt;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Tokens
/*    */ {
/*    */   static final char COMPOUND_BEGIN = '{';
/*    */   static final char COMPOUND_END = '}';
/*    */   static final char COMPOUND_KEY_TERMINATOR = ':';
/*    */   static final char ARRAY_BEGIN = '[';
/*    */   static final char ARRAY_END = ']';
/*    */   static final char ARRAY_SIGNATURE_SEPARATOR = ';';
/*    */   static final char VALUE_SEPARATOR = ',';
/*    */   static final char SINGLE_QUOTE = '\'';
/*    */   static final char DOUBLE_QUOTE = '"';
/*    */   static final char ESCAPE_MARKER = '\\';
/*    */   static final char TYPE_BYTE = 'b';
/*    */   static final char TYPE_SHORT = 's';
/*    */   static final char TYPE_INT = 'i';
/*    */   static final char TYPE_LONG = 'l';
/*    */   static final char TYPE_FLOAT = 'f';
/*    */   static final char TYPE_DOUBLE = 'd';
/*    */   static final String LITERAL_TRUE = "true";
/*    */   static final String LITERAL_FALSE = "false";
/* 53 */   static final String NEWLINE = System.getProperty("line.separator", "\n");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static final char EOF = '\000';
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static boolean id(char c) {
/* 68 */     return ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '-' || c == '_' || c == '.' || c == '+');
/*    */   }
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
/*    */   static boolean numeric(char c) {
/* 84 */     return ((c >= '0' && c <= '9') || c == '+' || c == '-' || c == 'e' || c == 'E' || c == '.');
/*    */   }
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\kyori\adventure\nbt\Tokens.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */