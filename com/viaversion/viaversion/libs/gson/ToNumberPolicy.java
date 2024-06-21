/*    */ package com.viaversion.viaversion.libs.gson;
/*    */ 
/*    */ import com.viaversion.viaversion.libs.gson.internal.LazilyParsedNumber;
/*    */ import com.viaversion.viaversion.libs.gson.stream.JsonReader;
/*    */ import com.viaversion.viaversion.libs.gson.stream.MalformedJsonException;
/*    */ import java.io.IOException;
/*    */ import java.math.BigDecimal;
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
/*    */ public enum ToNumberPolicy
/*    */   implements ToNumberStrategy
/*    */ {
/* 39 */   DOUBLE {
/*    */     public Double readNumber(JsonReader in) throws IOException {
/* 41 */       return Double.valueOf(in.nextDouble());
/*    */     }
/*    */   },
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 50 */   LAZILY_PARSED_NUMBER {
/*    */     public Number readNumber(JsonReader in) throws IOException {
/* 52 */       return (Number)new LazilyParsedNumber(in.nextString());
/*    */     }
/*    */   },
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
/* 65 */   LONG_OR_DOUBLE {
/*    */     public Number readNumber(JsonReader in) throws IOException, JsonParseException {
/* 67 */       String value = in.nextString();
/*    */       try {
/* 69 */         return Long.valueOf(Long.parseLong(value));
/* 70 */       } catch (NumberFormatException longE) {
/*    */         try {
/* 72 */           Double d = Double.valueOf(value);
/* 73 */           if ((d.isInfinite() || d.isNaN()) && !in.isLenient()) {
/* 74 */             throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + "; at path " + in.getPreviousPath());
/*    */           }
/* 76 */           return d;
/* 77 */         } catch (NumberFormatException doubleE) {
/* 78 */           throw new JsonParseException("Cannot parse " + value + "; at path " + in.getPreviousPath(), doubleE);
/*    */ 
/*    */         
/*    */         }
/*    */ 
/*    */       
/*    */       }
/*    */     
/*    */     }
/*    */   },
/* 88 */   BIG_DECIMAL {
/*    */     public BigDecimal readNumber(JsonReader in) throws IOException {
/* 90 */       String value = in.nextString();
/*    */       try {
/* 92 */         return new BigDecimal(value);
/* 93 */       } catch (NumberFormatException e) {
/* 94 */         throw new JsonParseException("Cannot parse " + value + "; at path " + in.getPreviousPath(), e);
/*    */       } 
/*    */     }
/*    */   };
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\com\viaversion\viaversion\libs\gson\ToNumberPolicy.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */