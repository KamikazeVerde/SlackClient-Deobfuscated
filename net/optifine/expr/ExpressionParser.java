/*     */ package net.optifine.expr;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Deque;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ 
/*     */ 
/*     */ public class ExpressionParser
/*     */ {
/*     */   private IExpressionResolver expressionResolver;
/*     */   
/*     */   public ExpressionParser(IExpressionResolver expressionResolver) {
/*  19 */     this.expressionResolver = expressionResolver;
/*     */   }
/*     */ 
/*     */   
/*     */   public IExpressionFloat parseFloat(String str) throws ParseException {
/*  24 */     IExpression iexpression = parse(str);
/*     */     
/*  26 */     if (!(iexpression instanceof IExpressionFloat))
/*     */     {
/*  28 */       throw new ParseException("Not a float expression: " + iexpression.getExpressionType());
/*     */     }
/*     */ 
/*     */     
/*  32 */     return (IExpressionFloat)iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpressionBool parseBool(String str) throws ParseException {
/*  38 */     IExpression iexpression = parse(str);
/*     */     
/*  40 */     if (!(iexpression instanceof IExpressionBool))
/*     */     {
/*  42 */       throw new ParseException("Not a boolean expression: " + iexpression.getExpressionType());
/*     */     }
/*     */ 
/*     */     
/*  46 */     return (IExpressionBool)iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IExpression parse(String str) throws ParseException {
/*     */     try {
/*  54 */       Token[] atoken = TokenParser.parse(str);
/*     */       
/*  56 */       if (atoken == null)
/*     */       {
/*  58 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  62 */       Deque<Token> deque = new ArrayDeque<>(Arrays.asList(atoken));
/*  63 */       return parseInfix(deque);
/*     */     
/*     */     }
/*  66 */     catch (IOException ioexception) {
/*     */       
/*  68 */       throw new ParseException(ioexception.getMessage(), ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression parseInfix(Deque<Token> deque) throws ParseException {
/*  74 */     if (deque.isEmpty())
/*     */     {
/*  76 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  80 */     List<IExpression> list = new LinkedList<>();
/*  81 */     List<Token> list1 = new LinkedList<>();
/*  82 */     IExpression iexpression = parseExpression(deque);
/*  83 */     checkNull(iexpression, "Missing expression");
/*  84 */     list.add(iexpression);
/*     */ 
/*     */     
/*     */     while (true) {
/*  88 */       Token token = deque.poll();
/*     */       
/*  90 */       if (token == null)
/*     */       {
/*  92 */         return makeInfix(list, list1);
/*     */       }
/*     */       
/*  95 */       if (token.getType() != TokenType.OPERATOR)
/*     */       {
/*  97 */         throw new ParseException("Invalid operator: " + token);
/*     */       }
/*     */       
/* 100 */       IExpression iexpression1 = parseExpression(deque);
/* 101 */       checkNull(iexpression1, "Missing expression");
/* 102 */       list1.add(token);
/* 103 */       list.add(iexpression1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeInfix(List<IExpression> listExpr, List<Token> listOper) throws ParseException {
/* 110 */     List<FunctionType> list = new LinkedList<>();
/*     */     
/* 112 */     for (Token token : listOper) {
/*     */       
/* 114 */       FunctionType functiontype = FunctionType.parse(token.getText());
/* 115 */       checkNull(functiontype, "Invalid operator: " + token);
/* 116 */       list.add(functiontype);
/*     */     } 
/*     */     
/* 119 */     return makeInfixFunc(listExpr, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression makeInfixFunc(List<IExpression> listExpr, List<FunctionType> listFunc) throws ParseException {
/* 124 */     if (listExpr.size() != listFunc.size() + 1)
/*     */     {
/* 126 */       throw new ParseException("Invalid infix expression, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     }
/* 128 */     if (listExpr.size() == 1)
/*     */     {
/* 130 */       return listExpr.get(0);
/*     */     }
/*     */ 
/*     */     
/* 134 */     int i = Integer.MAX_VALUE;
/* 135 */     int j = Integer.MIN_VALUE;
/*     */     
/* 137 */     for (FunctionType functiontype : listFunc) {
/*     */       
/* 139 */       i = Math.min(functiontype.getPrecedence(), i);
/* 140 */       j = Math.max(functiontype.getPrecedence(), j);
/*     */     } 
/*     */     
/* 143 */     if (j >= i && j - i <= 10) {
/*     */       
/* 145 */       for (int k = j; k >= i; k--)
/*     */       {
/* 147 */         mergeOperators(listExpr, listFunc, k);
/*     */       }
/*     */       
/* 150 */       if (listExpr.size() == 1 && listFunc.size() == 0)
/*     */       {
/* 152 */         return listExpr.get(0);
/*     */       }
/*     */ 
/*     */       
/* 156 */       throw new ParseException("Error merging operators, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 161 */     throw new ParseException("Invalid infix precedence, min: " + i + ", max: " + j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeOperators(List<IExpression> listExpr, List<FunctionType> listFuncs, int precedence) throws ParseException {
/* 168 */     for (int i = 0; i < listFuncs.size(); i++) {
/*     */       
/* 170 */       FunctionType functiontype = listFuncs.get(i);
/*     */       
/* 172 */       if (functiontype.getPrecedence() == precedence) {
/*     */         
/* 174 */         listFuncs.remove(i);
/* 175 */         IExpression iexpression = listExpr.remove(i);
/* 176 */         IExpression iexpression1 = listExpr.remove(i);
/* 177 */         IExpression iexpression2 = makeFunction(functiontype, new IExpression[] { iexpression, iexpression1 });
/* 178 */         listExpr.add(i, iexpression2);
/* 179 */         i--;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private IExpression parseExpression(Deque<Token> deque) throws ParseException {
/*     */     FunctionType functiontype, functiontype1;
/* 186 */     Token token = deque.poll();
/* 187 */     checkNull(token, "Missing expression");
/*     */     
/* 189 */     switch (token.getType()) {
/*     */       
/*     */       case NUMBER:
/* 192 */         return makeConstantFloat(token);
/*     */       
/*     */       case IDENTIFIER:
/* 195 */         functiontype = getFunctionType(token, deque);
/*     */         
/* 197 */         if (functiontype != null)
/*     */         {
/* 199 */           return makeFunction(functiontype, deque);
/*     */         }
/*     */         
/* 202 */         return makeVariable(token);
/*     */       
/*     */       case BRACKET_OPEN:
/* 205 */         return makeBracketed(token, deque);
/*     */       
/*     */       case OPERATOR:
/* 208 */         functiontype1 = FunctionType.parse(token.getText());
/* 209 */         checkNull(functiontype1, "Invalid operator: " + token);
/*     */         
/* 211 */         if (functiontype1 == FunctionType.PLUS)
/*     */         {
/* 213 */           return parseExpression(deque);
/*     */         }
/* 215 */         if (functiontype1 == FunctionType.MINUS) {
/*     */           
/* 217 */           IExpression iexpression1 = parseExpression(deque);
/* 218 */           return makeFunction(FunctionType.NEG, new IExpression[] { iexpression1 });
/*     */         } 
/* 220 */         if (functiontype1 == FunctionType.NOT) {
/*     */           
/* 222 */           IExpression iexpression = parseExpression(deque);
/* 223 */           return makeFunction(FunctionType.NOT, new IExpression[] { iexpression });
/*     */         } 
/*     */         break;
/*     */     } 
/* 227 */     throw new ParseException("Invalid expression: " + token);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IExpression makeConstantFloat(Token token) throws ParseException {
/* 233 */     float f = Config.parseFloat(token.getText(), Float.NaN);
/*     */     
/* 235 */     if (f == Float.NaN)
/*     */     {
/* 237 */       throw new ParseException("Invalid float value: " + token);
/*     */     }
/*     */ 
/*     */     
/* 241 */     return new ConstantFloat(f);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private FunctionType getFunctionType(Token tokens, Deque<Token> deque) throws ParseException {
/* 247 */     Token token = deque.peek();
/*     */     
/* 249 */     if (tokens != null && token.getType() == TokenType.BRACKET_OPEN) {
/*     */       
/* 251 */       FunctionType functiontype1 = FunctionType.parse(token.getText());
/* 252 */       checkNull(functiontype1, "Unknown function: " + token);
/* 253 */       return functiontype1;
/*     */     } 
/*     */ 
/*     */     
/* 257 */     FunctionType functiontype = FunctionType.parse(token.getText());
/*     */     
/* 259 */     if (functiontype == null)
/*     */     {
/* 261 */       return null;
/*     */     }
/* 263 */     if (functiontype.getParameterCount(new IExpression[0]) > 0)
/*     */     {
/* 265 */       throw new ParseException("Missing arguments: " + functiontype);
/*     */     }
/*     */ 
/*     */     
/* 269 */     return functiontype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeFunction(FunctionType type, Deque<Token> deque) throws ParseException {
/* 276 */     if (type.getParameterCount(new IExpression[0]) == 0) {
/*     */       
/* 278 */       Token token = deque.peek();
/*     */       
/* 280 */       if (token == null || token.getType() != TokenType.BRACKET_OPEN)
/*     */       {
/* 282 */         return makeFunction(type, new IExpression[0]);
/*     */       }
/*     */     } 
/*     */     
/* 286 */     Token token1 = deque.poll();
/* 287 */     Deque<Token> deque2 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/* 288 */     IExpression[] aiexpression = parseExpressions(deque2);
/* 289 */     return makeFunction(type, aiexpression);
/*     */   }
/*     */ 
/*     */   
/*     */   private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
/* 294 */     List<IExpression> list = new ArrayList<>();
/*     */ 
/*     */     
/*     */     while (true) {
/* 298 */       Deque<Token> deque2 = getGroup(deque, TokenType.COMMA, false);
/* 299 */       IExpression iexpression = parseInfix(deque2);
/*     */       
/* 301 */       if (iexpression == null) {
/*     */         
/* 303 */         IExpression[] aiexpression = list.<IExpression>toArray(new IExpression[list.size()]);
/* 304 */         return aiexpression;
/*     */       } 
/*     */       
/* 307 */       list.add(iexpression);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IExpression makeFunction(FunctionType type, IExpression[] args) throws ParseException {
/* 313 */     ExpressionType[] aexpressiontype = type.getParameterTypes(args);
/*     */     
/* 315 */     if (args.length != aexpressiontype.length)
/*     */     {
/* 317 */       throw new ParseException("Invalid number of arguments, function: \"" + type.getName() + "\", count arguments: " + args.length + ", should be: " + aexpressiontype.length);
/*     */     }
/*     */ 
/*     */     
/* 321 */     for (int i = 0; i < args.length; i++) {
/*     */       
/* 323 */       IExpression iexpression = args[i];
/* 324 */       ExpressionType expressiontype = iexpression.getExpressionType();
/* 325 */       ExpressionType expressiontype1 = aexpressiontype[i];
/*     */       
/* 327 */       if (expressiontype != expressiontype1)
/*     */       {
/* 329 */         throw new ParseException("Invalid argument type, function: \"" + type.getName() + "\", index: " + i + ", type: " + expressiontype + ", should be: " + expressiontype1);
/*     */       }
/*     */     } 
/*     */     
/* 333 */     if (type.getExpressionType() == ExpressionType.FLOAT)
/*     */     {
/* 335 */       return new FunctionFloat(type, args);
/*     */     }
/* 337 */     if (type.getExpressionType() == ExpressionType.BOOL)
/*     */     {
/* 339 */       return new FunctionBool(type, args);
/*     */     }
/* 341 */     if (type.getExpressionType() == ExpressionType.FLOAT_ARRAY)
/*     */     {
/* 343 */       return new FunctionFloatArray(type, args);
/*     */     }
/*     */ 
/*     */     
/* 347 */     throw new ParseException("Unknown function type: " + type.getExpressionType() + ", function: " + type.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeVariable(Token token) throws ParseException {
/* 354 */     if (this.expressionResolver == null)
/*     */     {
/* 356 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/*     */ 
/*     */     
/* 360 */     IExpression iexpression = this.expressionResolver.getExpression(token.getText());
/*     */     
/* 362 */     if (iexpression == null)
/*     */     {
/* 364 */       throw new ParseException("Model variable not found: " + token);
/*     */     }
/*     */ 
/*     */     
/* 368 */     return iexpression;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
/* 375 */     Deque<Token> deque2 = getGroup(deque, TokenType.BRACKET_CLOSE, true);
/* 376 */     return parseInfix(deque2);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenTypeEnd, boolean tokenEndRequired) throws ParseException {
/* 381 */     Deque<Token> deque3 = new ArrayDeque<>();
/* 382 */     int i = 0;
/* 383 */     Iterator<Token> iterator = deque.iterator();
/*     */     
/* 385 */     while (iterator.hasNext()) {
/*     */       
/* 387 */       Token token = iterator.next();
/* 388 */       iterator.remove();
/*     */       
/* 390 */       if (i == 0 && token.getType() == tokenTypeEnd)
/*     */       {
/* 392 */         return deque3;
/*     */       }
/*     */       
/* 395 */       deque3.add(token);
/*     */       
/* 397 */       if (token.getType() == TokenType.BRACKET_OPEN)
/*     */       {
/* 399 */         i++;
/*     */       }
/*     */       
/* 402 */       if (token.getType() == TokenType.BRACKET_CLOSE)
/*     */       {
/* 404 */         i--;
/*     */       }
/*     */     } 
/*     */     
/* 408 */     if (tokenEndRequired)
/*     */     {
/* 410 */       throw new ParseException("Missing end token: " + tokenTypeEnd);
/*     */     }
/*     */ 
/*     */     
/* 414 */     return deque3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String message) throws ParseException {
/* 420 */     if (obj == null)
/*     */     {
/* 422 */       throw new ParseException(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\net\optifine\expr\ExpressionParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */