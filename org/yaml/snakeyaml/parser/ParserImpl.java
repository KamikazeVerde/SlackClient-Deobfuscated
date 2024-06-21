/*     */ package org.yaml.snakeyaml.parser;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.LoaderOptions;
/*     */ import org.yaml.snakeyaml.comments.CommentType;
/*     */ import org.yaml.snakeyaml.error.Mark;
/*     */ import org.yaml.snakeyaml.error.YAMLException;
/*     */ import org.yaml.snakeyaml.events.AliasEvent;
/*     */ import org.yaml.snakeyaml.events.CommentEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentEndEvent;
/*     */ import org.yaml.snakeyaml.events.DocumentStartEvent;
/*     */ import org.yaml.snakeyaml.events.Event;
/*     */ import org.yaml.snakeyaml.events.ImplicitTuple;
/*     */ import org.yaml.snakeyaml.events.MappingEndEvent;
/*     */ import org.yaml.snakeyaml.events.MappingStartEvent;
/*     */ import org.yaml.snakeyaml.events.ScalarEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceEndEvent;
/*     */ import org.yaml.snakeyaml.events.SequenceStartEvent;
/*     */ import org.yaml.snakeyaml.events.StreamEndEvent;
/*     */ import org.yaml.snakeyaml.events.StreamStartEvent;
/*     */ import org.yaml.snakeyaml.reader.StreamReader;
/*     */ import org.yaml.snakeyaml.scanner.Scanner;
/*     */ import org.yaml.snakeyaml.scanner.ScannerImpl;
/*     */ import org.yaml.snakeyaml.tokens.AliasToken;
/*     */ import org.yaml.snakeyaml.tokens.AnchorToken;
/*     */ import org.yaml.snakeyaml.tokens.BlockEntryToken;
/*     */ import org.yaml.snakeyaml.tokens.CommentToken;
/*     */ import org.yaml.snakeyaml.tokens.DirectiveToken;
/*     */ import org.yaml.snakeyaml.tokens.ScalarToken;
/*     */ import org.yaml.snakeyaml.tokens.StreamEndToken;
/*     */ import org.yaml.snakeyaml.tokens.StreamStartToken;
/*     */ import org.yaml.snakeyaml.tokens.TagToken;
/*     */ import org.yaml.snakeyaml.tokens.TagTuple;
/*     */ import org.yaml.snakeyaml.tokens.Token;
/*     */ import org.yaml.snakeyaml.util.ArrayStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParserImpl
/*     */   implements Parser
/*     */ {
/* 121 */   private static final Map<String, String> DEFAULT_TAGS = new HashMap<>();
/*     */   
/*     */   static {
/* 124 */     DEFAULT_TAGS.put("!", "!");
/* 125 */     DEFAULT_TAGS.put("!!", "tag:yaml.org,2002:");
/*     */   }
/*     */   
/*     */   protected final Scanner scanner;
/*     */   private Event currentEvent;
/*     */   private final ArrayStack<Production> states;
/*     */   private final ArrayStack<Mark> marks;
/*     */   private Production state;
/*     */   private VersionTagsTuple directives;
/*     */   
/*     */   public ParserImpl(StreamReader reader, LoaderOptions options) {
/* 136 */     this((Scanner)new ScannerImpl(reader, options));
/*     */   }
/*     */   
/*     */   public ParserImpl(Scanner scanner) {
/* 140 */     this.scanner = scanner;
/* 141 */     this.currentEvent = null;
/* 142 */     this.directives = new VersionTagsTuple(null, new HashMap<>(DEFAULT_TAGS));
/* 143 */     this.states = new ArrayStack(100);
/* 144 */     this.marks = new ArrayStack(10);
/* 145 */     this.state = new ParseStreamStart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkEvent(Event.ID choice) {
/* 152 */     peekEvent();
/* 153 */     return (this.currentEvent != null && this.currentEvent.is(choice));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Event peekEvent() {
/* 160 */     if (this.currentEvent == null && 
/* 161 */       this.state != null) {
/* 162 */       this.currentEvent = this.state.produce();
/*     */     }
/*     */     
/* 165 */     return this.currentEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Event getEvent() {
/* 172 */     peekEvent();
/* 173 */     Event value = this.currentEvent;
/* 174 */     this.currentEvent = null;
/* 175 */     return value;
/*     */   }
/*     */   
/*     */   private CommentEvent produceCommentEvent(CommentToken token) {
/* 179 */     Mark startMark = token.getStartMark();
/* 180 */     Mark endMark = token.getEndMark();
/* 181 */     String value = token.getValue();
/* 182 */     CommentType type = token.getCommentType();
/*     */ 
/*     */ 
/*     */     
/* 186 */     return new CommentEvent(type, value, startMark, endMark);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ParseStreamStart
/*     */     implements Production
/*     */   {
/*     */     private ParseStreamStart() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public Event produce() {
/* 200 */       StreamStartToken token = (StreamStartToken)ParserImpl.this.scanner.getToken();
/* 201 */       StreamStartEvent streamStartEvent = new StreamStartEvent(token.getStartMark(), token.getEndMark());
/*     */       
/* 203 */       ParserImpl.this.state = new ParserImpl.ParseImplicitDocumentStart();
/* 204 */       return (Event)streamStartEvent;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseImplicitDocumentStart implements Production {
/*     */     private ParseImplicitDocumentStart() {}
/*     */     
/*     */     public Event produce() {
/* 212 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 213 */         ParserImpl.this.state = new ParseImplicitDocumentStart();
/* 214 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 216 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Directive, Token.ID.DocumentStart, Token.ID.StreamEnd })) {
/* 217 */         Token token = ParserImpl.this.scanner.peekToken();
/* 218 */         Mark startMark = token.getStartMark();
/* 219 */         Mark endMark = startMark;
/* 220 */         DocumentStartEvent documentStartEvent = new DocumentStartEvent(startMark, endMark, false, null, null);
/*     */         
/* 222 */         ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd());
/* 223 */         ParserImpl.this.state = new ParserImpl.ParseBlockNode();
/* 224 */         return (Event)documentStartEvent;
/*     */       } 
/* 226 */       return (new ParserImpl.ParseDocumentStart()).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseDocumentStart implements Production {
/*     */     private ParseDocumentStart() {}
/*     */     
/*     */     public Event produce() {
/* 234 */       while (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentEnd })) {
/* 235 */         ParserImpl.this.scanner.getToken();
/*     */       }
/*     */ 
/*     */       
/* 239 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.StreamEnd })) {
/* 240 */         Token token1 = ParserImpl.this.scanner.peekToken();
/* 241 */         Mark startMark = token1.getStartMark();
/* 242 */         VersionTagsTuple tuple = ParserImpl.this.processDirectives();
/* 243 */         while (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment }))
/*     */         {
/* 245 */           ParserImpl.this.scanner.getToken();
/*     */         }
/* 247 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.StreamEnd })) {
/* 248 */           if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentStart })) {
/* 249 */             throw new ParserException(null, null, "expected '<document start>', but found '" + ParserImpl.this.scanner
/* 250 */                 .peekToken().getTokenId() + "'", ParserImpl.this.scanner
/* 251 */                 .peekToken().getStartMark());
/*     */           }
/* 253 */           token1 = ParserImpl.this.scanner.getToken();
/* 254 */           Mark endMark = token1.getEndMark();
/*     */           
/* 256 */           DocumentStartEvent documentStartEvent = new DocumentStartEvent(startMark, endMark, true, tuple.getVersion(), tuple.getTags());
/* 257 */           ParserImpl.this.states.push(new ParserImpl.ParseDocumentEnd());
/* 258 */           ParserImpl.this.state = new ParserImpl.ParseDocumentContent();
/* 259 */           return (Event)documentStartEvent;
/*     */         } 
/*     */       } 
/*     */       
/* 263 */       StreamEndToken token = (StreamEndToken)ParserImpl.this.scanner.getToken();
/* 264 */       StreamEndEvent streamEndEvent = new StreamEndEvent(token.getStartMark(), token.getEndMark());
/* 265 */       if (!ParserImpl.this.states.isEmpty()) {
/* 266 */         throw new YAMLException("Unexpected end of stream. States left: " + ParserImpl.this.states);
/*     */       }
/* 268 */       if (!ParserImpl.this.marks.isEmpty()) {
/* 269 */         throw new YAMLException("Unexpected end of stream. Marks left: " + ParserImpl.this.marks);
/*     */       }
/* 271 */       ParserImpl.this.state = null;
/* 272 */       return (Event)streamEndEvent;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseDocumentEnd implements Production {
/*     */     private ParseDocumentEnd() {}
/*     */     
/*     */     public Event produce() {
/* 280 */       Token token = ParserImpl.this.scanner.peekToken();
/* 281 */       Mark startMark = token.getStartMark();
/* 282 */       Mark endMark = startMark;
/* 283 */       boolean explicit = false;
/* 284 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.DocumentEnd })) {
/* 285 */         token = ParserImpl.this.scanner.getToken();
/* 286 */         endMark = token.getEndMark();
/* 287 */         explicit = true;
/*     */       } 
/* 289 */       DocumentEndEvent documentEndEvent = new DocumentEndEvent(startMark, endMark, explicit);
/*     */       
/* 291 */       ParserImpl.this.state = new ParserImpl.ParseDocumentStart();
/* 292 */       return (Event)documentEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseDocumentContent implements Production {
/*     */     private ParseDocumentContent() {}
/*     */     
/*     */     public Event produce() {
/* 299 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 300 */         ParserImpl.this.state = new ParseDocumentContent();
/* 301 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 303 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Directive, Token.ID.DocumentStart, Token.ID.DocumentEnd, Token.ID.StreamEnd })) {
/*     */         
/* 305 */         Event event = ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
/* 306 */         ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 307 */         return event;
/*     */       } 
/* 309 */       return (new ParserImpl.ParseBlockNode()).produce();
/*     */     }
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
/*     */   
/*     */   private VersionTagsTuple processDirectives() {
/* 323 */     HashMap<String, String> tagHandles = new HashMap<>(this.directives.getTags());
/* 324 */     for (String key : DEFAULT_TAGS.keySet()) {
/* 325 */       tagHandles.remove(key);
/*     */     }
/*     */     
/* 328 */     this.directives = new VersionTagsTuple(null, tagHandles);
/* 329 */     while (this.scanner.checkToken(new Token.ID[] { Token.ID.Directive })) {
/*     */       
/* 331 */       DirectiveToken token = (DirectiveToken)this.scanner.getToken();
/* 332 */       if (token.getName().equals("YAML")) {
/* 333 */         if (this.directives.getVersion() != null) {
/* 334 */           throw new ParserException(null, null, "found duplicate YAML directive", token
/* 335 */               .getStartMark());
/*     */         }
/* 337 */         List<Integer> value = token.getValue();
/* 338 */         Integer major = value.get(0);
/* 339 */         if (major.intValue() != 1) {
/* 340 */           throw new ParserException(null, null, "found incompatible YAML document (version 1.* is required)", token
/* 341 */               .getStartMark());
/*     */         }
/* 343 */         Integer minor = value.get(1);
/*     */         
/* 345 */         if (minor.intValue() == 0) {
/* 346 */           this.directives = new VersionTagsTuple(DumperOptions.Version.V1_0, tagHandles); continue;
/*     */         } 
/* 348 */         this.directives = new VersionTagsTuple(DumperOptions.Version.V1_1, tagHandles); continue;
/*     */       } 
/* 350 */       if (token.getName().equals("TAG")) {
/* 351 */         List<String> value = token.getValue();
/* 352 */         String handle = value.get(0);
/* 353 */         String prefix = value.get(1);
/* 354 */         if (tagHandles.containsKey(handle)) {
/* 355 */           throw new ParserException(null, null, "duplicate tag handle " + handle, token
/* 356 */               .getStartMark());
/*     */         }
/* 358 */         tagHandles.put(handle, prefix);
/*     */       } 
/*     */     } 
/* 361 */     HashMap<String, String> detectedTagHandles = new HashMap<>();
/* 362 */     if (!tagHandles.isEmpty())
/*     */     {
/* 364 */       detectedTagHandles = new HashMap<>(tagHandles);
/*     */     }
/*     */     
/* 367 */     for (String key : DEFAULT_TAGS.keySet()) {
/*     */       
/* 369 */       if (!tagHandles.containsKey(key)) {
/* 370 */         tagHandles.put(key, DEFAULT_TAGS.get(key));
/*     */       }
/*     */     } 
/*     */     
/* 374 */     return new VersionTagsTuple(this.directives.getVersion(), detectedTagHandles);
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
/*     */   
/*     */   private class ParseBlockNode
/*     */     implements Production
/*     */   {
/*     */     private ParseBlockNode() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Event produce() {
/* 400 */       return ParserImpl.this.parseNode(true, false);
/*     */     }
/*     */   }
/*     */   
/*     */   private Event parseFlowNode() {
/* 405 */     return parseNode(false, false);
/*     */   }
/*     */   
/*     */   private Event parseBlockNodeOrIndentlessSequence() {
/* 409 */     return parseNode(true, true);
/*     */   }
/*     */   
/*     */   private Event parseNode(boolean block, boolean indentlessSequence) {
/*     */     ScalarEvent scalarEvent;
/* 414 */     Mark startMark = null;
/* 415 */     Mark endMark = null;
/* 416 */     Mark tagMark = null;
/* 417 */     if (this.scanner.checkToken(new Token.ID[] { Token.ID.Alias })) {
/* 418 */       AliasToken token = (AliasToken)this.scanner.getToken();
/* 419 */       AliasEvent aliasEvent = new AliasEvent(token.getValue(), token.getStartMark(), token.getEndMark());
/* 420 */       this.state = (Production)this.states.pop();
/*     */     } else {
/* 422 */       String anchor = null;
/* 423 */       TagTuple tagTokenTag = null;
/* 424 */       if (this.scanner.checkToken(new Token.ID[] { Token.ID.Anchor })) {
/* 425 */         AnchorToken token = (AnchorToken)this.scanner.getToken();
/* 426 */         startMark = token.getStartMark();
/* 427 */         endMark = token.getEndMark();
/* 428 */         anchor = token.getValue();
/* 429 */         if (this.scanner.checkToken(new Token.ID[] { Token.ID.Tag })) {
/* 430 */           TagToken tagToken = (TagToken)this.scanner.getToken();
/* 431 */           tagMark = tagToken.getStartMark();
/* 432 */           endMark = tagToken.getEndMark();
/* 433 */           tagTokenTag = tagToken.getValue();
/*     */         } 
/*     */       } else {
/* 436 */         TagToken tagToken = (TagToken)this.scanner.getToken();
/* 437 */         startMark = tagToken.getStartMark();
/* 438 */         tagMark = startMark;
/* 439 */         endMark = tagToken.getEndMark();
/* 440 */         tagTokenTag = tagToken.getValue();
/* 441 */         if (this.scanner.checkToken(new Token.ID[] { Token.ID.Tag }) && this.scanner.checkToken(new Token.ID[] { Token.ID.Anchor })) {
/* 442 */           AnchorToken token = (AnchorToken)this.scanner.getToken();
/* 443 */           endMark = token.getEndMark();
/* 444 */           anchor = token.getValue();
/*     */         } 
/*     */       } 
/* 447 */       String tag = null;
/* 448 */       if (tagTokenTag != null) {
/* 449 */         String handle = tagTokenTag.getHandle();
/* 450 */         String suffix = tagTokenTag.getSuffix();
/* 451 */         if (handle != null) {
/* 452 */           if (!this.directives.getTags().containsKey(handle)) {
/* 453 */             throw new ParserException("while parsing a node", startMark, "found undefined tag handle " + handle, tagMark);
/*     */           }
/*     */           
/* 456 */           tag = (String)this.directives.getTags().get(handle) + suffix;
/*     */         } else {
/* 458 */           tag = suffix;
/*     */         } 
/*     */       } 
/* 461 */       if (startMark == null) {
/* 462 */         startMark = this.scanner.peekToken().getStartMark();
/* 463 */         endMark = startMark;
/*     */       } 
/* 465 */       Event event = null;
/* 466 */       boolean implicit = (tag == null || tag.equals("!"));
/* 467 */       if (indentlessSequence && this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 468 */         endMark = this.scanner.peekToken().getEndMark();
/* 469 */         SequenceStartEvent sequenceStartEvent = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.BLOCK);
/*     */         
/* 471 */         this.state = new ParseIndentlessSequenceEntryKey();
/*     */       }
/* 473 */       else if (this.scanner.checkToken(new Token.ID[] { Token.ID.Scalar })) {
/* 474 */         ImplicitTuple implicitValues; ScalarToken token = (ScalarToken)this.scanner.getToken();
/* 475 */         endMark = token.getEndMark();
/*     */         
/* 477 */         if ((token.getPlain() && tag == null) || "!".equals(tag)) {
/* 478 */           implicitValues = new ImplicitTuple(true, false);
/* 479 */         } else if (tag == null) {
/* 480 */           implicitValues = new ImplicitTuple(false, true);
/*     */         } else {
/* 482 */           implicitValues = new ImplicitTuple(false, false);
/*     */         } 
/*     */         
/* 485 */         scalarEvent = new ScalarEvent(anchor, tag, implicitValues, token.getValue(), startMark, endMark, token.getStyle());
/* 486 */         this.state = (Production)this.states.pop();
/* 487 */       } else if (this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceStart })) {
/* 488 */         endMark = this.scanner.peekToken().getEndMark();
/* 489 */         SequenceStartEvent sequenceStartEvent = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.FLOW);
/*     */         
/* 491 */         this.state = new ParseFlowSequenceFirstEntry();
/* 492 */       } else if (this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingStart })) {
/* 493 */         endMark = this.scanner.peekToken().getEndMark();
/* 494 */         MappingStartEvent mappingStartEvent = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.FLOW);
/*     */         
/* 496 */         this.state = new ParseFlowMappingFirstKey();
/* 497 */       } else if (block && this.scanner.checkToken(new Token.ID[] { Token.ID.BlockSequenceStart })) {
/* 498 */         endMark = this.scanner.peekToken().getStartMark();
/* 499 */         SequenceStartEvent sequenceStartEvent = new SequenceStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.BLOCK);
/*     */         
/* 501 */         this.state = new ParseBlockSequenceFirstEntry();
/* 502 */       } else if (block && this.scanner.checkToken(new Token.ID[] { Token.ID.BlockMappingStart })) {
/* 503 */         endMark = this.scanner.peekToken().getStartMark();
/* 504 */         MappingStartEvent mappingStartEvent = new MappingStartEvent(anchor, tag, implicit, startMark, endMark, DumperOptions.FlowStyle.BLOCK);
/*     */         
/* 506 */         this.state = new ParseBlockMappingFirstKey();
/* 507 */       } else if (anchor != null || tag != null) {
/*     */ 
/*     */         
/* 510 */         scalarEvent = new ScalarEvent(anchor, tag, new ImplicitTuple(implicit, false), "", startMark, endMark, DumperOptions.ScalarStyle.PLAIN);
/*     */         
/* 512 */         this.state = (Production)this.states.pop();
/*     */       } else {
/* 514 */         Token token = this.scanner.peekToken();
/* 515 */         throw new ParserException("while parsing a " + (block ? "block" : "flow") + " node", startMark, "expected the node content, but found '" + token
/* 516 */             .getTokenId() + "'", token
/* 517 */             .getStartMark());
/*     */       } 
/*     */     } 
/*     */     
/* 521 */     return (Event)scalarEvent;
/*     */   }
/*     */   
/*     */   private class ParseBlockSequenceFirstEntry
/*     */     implements Production
/*     */   {
/*     */     private ParseBlockSequenceFirstEntry() {}
/*     */     
/*     */     public Event produce() {
/* 530 */       Token token = ParserImpl.this.scanner.getToken();
/* 531 */       ParserImpl.this.marks.push(token.getStartMark());
/* 532 */       return (new ParserImpl.ParseBlockSequenceEntryKey()).produce();
/*     */     } }
/*     */   
/*     */   private class ParseBlockSequenceEntryKey implements Production {
/*     */     private ParseBlockSequenceEntryKey() {}
/*     */     
/*     */     public Event produce() {
/* 539 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 540 */         ParserImpl.this.state = new ParseBlockSequenceEntryKey();
/* 541 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 543 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 544 */         BlockEntryToken blockEntryToken = (BlockEntryToken)ParserImpl.this.scanner.getToken();
/* 545 */         return (new ParserImpl.ParseBlockSequenceEntryValue(blockEntryToken)).produce();
/*     */       } 
/* 547 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEnd })) {
/* 548 */         Token token1 = ParserImpl.this.scanner.peekToken();
/* 549 */         throw new ParserException("while parsing a block collection", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found '" + token1
/* 550 */             .getTokenId() + "'", token1.getStartMark());
/*     */       } 
/* 552 */       Token token = ParserImpl.this.scanner.getToken();
/* 553 */       SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 554 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 555 */       ParserImpl.this.marks.pop();
/* 556 */       return (Event)sequenceEndEvent;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockSequenceEntryValue
/*     */     implements Production {
/*     */     BlockEntryToken token;
/*     */     
/*     */     public ParseBlockSequenceEntryValue(BlockEntryToken token) {
/* 565 */       this.token = token;
/*     */     }
/*     */     
/*     */     public Event produce() {
/* 569 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 570 */         ParserImpl.this.state = new ParseBlockSequenceEntryValue(this.token);
/* 571 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 573 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry, Token.ID.BlockEnd })) {
/* 574 */         ParserImpl.this.states.push(new ParserImpl.ParseBlockSequenceEntryKey());
/* 575 */         return (new ParserImpl.ParseBlockNode()).produce();
/*     */       } 
/* 577 */       ParserImpl.this.state = new ParserImpl.ParseBlockSequenceEntryKey();
/* 578 */       return ParserImpl.this.processEmptyScalar(this.token.getEndMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseIndentlessSequenceEntryKey
/*     */     implements Production
/*     */   {
/*     */     private ParseIndentlessSequenceEntryKey() {}
/*     */     
/*     */     public Event produce() {
/* 588 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 589 */         ParserImpl.this.state = new ParseIndentlessSequenceEntryKey();
/* 590 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 592 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry })) {
/* 593 */         BlockEntryToken blockEntryToken = (BlockEntryToken)ParserImpl.this.scanner.getToken();
/* 594 */         return (new ParserImpl.ParseIndentlessSequenceEntryValue(blockEntryToken)).produce();
/*     */       } 
/* 596 */       Token token = ParserImpl.this.scanner.peekToken();
/* 597 */       SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 598 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 599 */       return (Event)sequenceEndEvent;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseIndentlessSequenceEntryValue
/*     */     implements Production {
/*     */     BlockEntryToken token;
/*     */     
/*     */     public ParseIndentlessSequenceEntryValue(BlockEntryToken token) {
/* 608 */       this.token = token;
/*     */     }
/*     */     
/*     */     public Event produce() {
/* 612 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 613 */         ParserImpl.this.state = new ParseIndentlessSequenceEntryValue(this.token);
/* 614 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 616 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEntry, Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/*     */         
/* 618 */         ParserImpl.this.states.push(new ParserImpl.ParseIndentlessSequenceEntryKey());
/* 619 */         return (new ParserImpl.ParseBlockNode()).produce();
/*     */       } 
/* 621 */       ParserImpl.this.state = new ParserImpl.ParseIndentlessSequenceEntryKey();
/* 622 */       return ParserImpl.this.processEmptyScalar(this.token.getEndMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockMappingFirstKey implements Production {
/*     */     private ParseBlockMappingFirstKey() {}
/*     */     
/*     */     public Event produce() {
/* 630 */       Token token = ParserImpl.this.scanner.getToken();
/* 631 */       ParserImpl.this.marks.push(token.getStartMark());
/* 632 */       return (new ParserImpl.ParseBlockMappingKey()).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockMappingKey implements Production { private ParseBlockMappingKey() {}
/*     */     
/*     */     public Event produce() {
/* 639 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 640 */         ParserImpl.this.state = new ParseBlockMappingKey();
/* 641 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 643 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 644 */         Token token1 = ParserImpl.this.scanner.getToken();
/* 645 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/* 646 */           ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingValue());
/* 647 */           return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */         } 
/* 649 */         ParserImpl.this.state = new ParserImpl.ParseBlockMappingValue();
/* 650 */         return ParserImpl.this.processEmptyScalar(token1.getEndMark());
/*     */       } 
/*     */       
/* 653 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.BlockEnd })) {
/* 654 */         Token token1 = ParserImpl.this.scanner.peekToken();
/* 655 */         throw new ParserException("while parsing a block mapping", (Mark)ParserImpl.this.marks.pop(), "expected <block end>, but found '" + token1
/* 656 */             .getTokenId() + "'", token1.getStartMark());
/*     */       } 
/* 658 */       Token token = ParserImpl.this.scanner.getToken();
/* 659 */       MappingEndEvent mappingEndEvent = new MappingEndEvent(token.getStartMark(), token.getEndMark());
/* 660 */       ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/* 661 */       ParserImpl.this.marks.pop();
/* 662 */       return (Event)mappingEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseBlockMappingValue implements Production {
/*     */     private ParseBlockMappingValue() {}
/*     */     
/*     */     public Event produce() {
/* 669 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 670 */         Token token1 = ParserImpl.this.scanner.getToken();
/* 671 */         if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 672 */           ParserImpl.this.state = new ParserImpl.ParseBlockMappingValueComment();
/* 673 */           return ParserImpl.this.state.produce();
/* 674 */         }  if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/* 675 */           ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey());
/* 676 */           return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */         } 
/* 678 */         ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey();
/* 679 */         return ParserImpl.this.processEmptyScalar(token1.getEndMark());
/*     */       } 
/* 681 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Scalar })) {
/* 682 */         ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey());
/* 683 */         return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */       } 
/* 685 */       ParserImpl.this.state = new ParserImpl.ParseBlockMappingKey();
/* 686 */       Token token = ParserImpl.this.scanner.peekToken();
/* 687 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseBlockMappingValueComment
/*     */     implements Production {
/* 693 */     List<CommentToken> tokens = new LinkedList<>();
/*     */     
/*     */     public Event produce() {
/* 696 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 697 */         this.tokens.add((CommentToken)ParserImpl.this.scanner.getToken());
/* 698 */         return produce();
/* 699 */       }  if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key, Token.ID.Value, Token.ID.BlockEnd })) {
/* 700 */         if (!this.tokens.isEmpty()) {
/* 701 */           return (Event)ParserImpl.this.produceCommentEvent(this.tokens.remove(0));
/*     */         }
/* 703 */         ParserImpl.this.states.push(new ParserImpl.ParseBlockMappingKey());
/* 704 */         return ParserImpl.this.parseBlockNodeOrIndentlessSequence();
/*     */       } 
/* 706 */       ParserImpl.this.state = new ParserImpl.ParseBlockMappingValueCommentList(this.tokens);
/* 707 */       return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
/*     */     }
/*     */     
/*     */     private ParseBlockMappingValueComment() {}
/*     */   }
/*     */   
/*     */   private class ParseBlockMappingValueCommentList implements Production {
/*     */     List<CommentToken> tokens;
/*     */     
/*     */     public ParseBlockMappingValueCommentList(List<CommentToken> tokens) {
/* 717 */       this.tokens = tokens;
/*     */     }
/*     */     
/*     */     public Event produce() {
/* 721 */       if (!this.tokens.isEmpty()) {
/* 722 */         return (Event)ParserImpl.this.produceCommentEvent(this.tokens.remove(0));
/*     */       }
/* 724 */       return (new ParserImpl.ParseBlockMappingKey()).produce();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ParseFlowSequenceFirstEntry
/*     */     implements Production
/*     */   {
/*     */     private ParseFlowSequenceFirstEntry() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Event produce() {
/* 744 */       Token token = ParserImpl.this.scanner.getToken();
/* 745 */       ParserImpl.this.marks.push(token.getStartMark());
/* 746 */       return (new ParserImpl.ParseFlowSequenceEntry(true)).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowSequenceEntry
/*     */     implements Production {
/*     */     private final boolean first;
/*     */     
/*     */     public ParseFlowSequenceEntry(boolean first) {
/* 755 */       this.first = first;
/*     */     }
/*     */     
/*     */     public Event produce() {
/* 759 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 760 */         ParserImpl.this.state = new ParseFlowSequenceEntry(this.first);
/* 761 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 763 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceEnd })) {
/* 764 */         if (!this.first) {
/* 765 */           if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry })) {
/* 766 */             ParserImpl.this.scanner.getToken();
/* 767 */             if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 768 */               ParserImpl.this.state = new ParseFlowSequenceEntry(true);
/* 769 */               return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */             } 
/*     */           } else {
/* 772 */             Token token1 = ParserImpl.this.scanner.peekToken();
/* 773 */             throw new ParserException("while parsing a flow sequence", (Mark)ParserImpl.this.marks.pop(), "expected ',' or ']', but got " + token1
/* 774 */                 .getTokenId(), token1.getStartMark());
/*     */           } 
/*     */         }
/* 777 */         if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 778 */           Token token1 = ParserImpl.this.scanner.peekToken();
/*     */           
/* 780 */           MappingStartEvent mappingStartEvent = new MappingStartEvent(null, null, true, token1.getStartMark(), token1.getEndMark(), DumperOptions.FlowStyle.FLOW);
/* 781 */           ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingKey();
/* 782 */           return (Event)mappingStartEvent;
/* 783 */         }  if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowSequenceEnd })) {
/* 784 */           ParserImpl.this.states.push(new ParseFlowSequenceEntry(false));
/* 785 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/*     */       } 
/* 788 */       Token token = ParserImpl.this.scanner.getToken();
/* 789 */       SequenceEndEvent sequenceEndEvent = new SequenceEndEvent(token.getStartMark(), token.getEndMark());
/* 790 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 791 */         ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/*     */       } else {
/* 793 */         ParserImpl.this.state = new ParserImpl.ParseFlowEndComment();
/*     */       } 
/* 795 */       ParserImpl.this.marks.pop();
/* 796 */       return (Event)sequenceEndEvent;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowEndComment implements Production { private ParseFlowEndComment() {}
/*     */     
/*     */     public Event produce() {
/* 803 */       CommentEvent commentEvent = ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/* 804 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 805 */         ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/*     */       }
/* 807 */       return (Event)commentEvent;
/*     */     } }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingKey implements Production {
/*     */     private ParseFlowSequenceEntryMappingKey() {}
/*     */     
/*     */     public Event produce() {
/* 814 */       Token token = ParserImpl.this.scanner.getToken();
/* 815 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowSequenceEnd })) {
/* 816 */         ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingValue());
/* 817 */         return ParserImpl.this.parseFlowNode();
/*     */       } 
/* 819 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingValue();
/* 820 */       return ParserImpl.this.processEmptyScalar(token.getEndMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingValue implements Production {
/*     */     private ParseFlowSequenceEntryMappingValue() {}
/*     */     
/*     */     public Event produce() {
/* 828 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 829 */         Token token1 = ParserImpl.this.scanner.getToken();
/* 830 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry, Token.ID.FlowSequenceEnd })) {
/* 831 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowSequenceEntryMappingEnd());
/* 832 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/* 834 */         ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd();
/* 835 */         return ParserImpl.this.processEmptyScalar(token1.getEndMark());
/*     */       } 
/*     */       
/* 838 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntryMappingEnd();
/* 839 */       Token token = ParserImpl.this.scanner.peekToken();
/* 840 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowSequenceEntryMappingEnd implements Production {
/*     */     private ParseFlowSequenceEntryMappingEnd() {}
/*     */     
/*     */     public Event produce() {
/* 848 */       ParserImpl.this.state = new ParserImpl.ParseFlowSequenceEntry(false);
/* 849 */       Token token = ParserImpl.this.scanner.peekToken();
/* 850 */       return (Event)new MappingEndEvent(token.getStartMark(), token.getEndMark());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class ParseFlowMappingFirstKey
/*     */     implements Production
/*     */   {
/*     */     private ParseFlowMappingFirstKey() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Event produce() {
/* 866 */       Token token = ParserImpl.this.scanner.getToken();
/* 867 */       ParserImpl.this.marks.push(token.getStartMark());
/* 868 */       return (new ParserImpl.ParseFlowMappingKey(true)).produce();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowMappingKey
/*     */     implements Production {
/*     */     private final boolean first;
/*     */     
/*     */     public ParseFlowMappingKey(boolean first) {
/* 877 */       this.first = first;
/*     */     }
/*     */     
/*     */     public Event produce() {
/* 881 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 882 */         ParserImpl.this.state = new ParseFlowMappingKey(this.first);
/* 883 */         return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */       } 
/* 885 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingEnd })) {
/* 886 */         if (!this.first) {
/* 887 */           if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry })) {
/* 888 */             ParserImpl.this.scanner.getToken();
/* 889 */             if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 890 */               ParserImpl.this.state = new ParseFlowMappingKey(true);
/* 891 */               return (Event)ParserImpl.this.produceCommentEvent((CommentToken)ParserImpl.this.scanner.getToken());
/*     */             } 
/*     */           } else {
/* 894 */             Token token1 = ParserImpl.this.scanner.peekToken();
/* 895 */             throw new ParserException("while parsing a flow mapping", (Mark)ParserImpl.this.marks.pop(), "expected ',' or '}', but got " + token1
/* 896 */                 .getTokenId(), token1.getStartMark());
/*     */           } 
/*     */         }
/* 899 */         if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Key })) {
/* 900 */           Token token1 = ParserImpl.this.scanner.getToken();
/* 901 */           if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value, Token.ID.FlowEntry, Token.ID.FlowMappingEnd })) {
/* 902 */             ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingValue());
/* 903 */             return ParserImpl.this.parseFlowNode();
/*     */           } 
/* 905 */           ParserImpl.this.state = new ParserImpl.ParseFlowMappingValue();
/* 906 */           return ParserImpl.this.processEmptyScalar(token1.getEndMark());
/*     */         } 
/* 908 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowMappingEnd })) {
/* 909 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingEmptyValue());
/* 910 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/*     */       } 
/* 913 */       Token token = ParserImpl.this.scanner.getToken();
/* 914 */       MappingEndEvent mappingEndEvent = new MappingEndEvent(token.getStartMark(), token.getEndMark());
/* 915 */       ParserImpl.this.marks.pop();
/* 916 */       if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Comment })) {
/* 917 */         ParserImpl.this.state = (Production)ParserImpl.this.states.pop();
/*     */       } else {
/* 919 */         ParserImpl.this.state = new ParserImpl.ParseFlowEndComment();
/*     */       } 
/* 921 */       return (Event)mappingEndEvent;
/*     */     } }
/*     */   
/*     */   private class ParseFlowMappingValue implements Production {
/*     */     private ParseFlowMappingValue() {}
/*     */     
/*     */     public Event produce() {
/* 928 */       if (ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.Value })) {
/* 929 */         Token token1 = ParserImpl.this.scanner.getToken();
/* 930 */         if (!ParserImpl.this.scanner.checkToken(new Token.ID[] { Token.ID.FlowEntry, Token.ID.FlowMappingEnd })) {
/* 931 */           ParserImpl.this.states.push(new ParserImpl.ParseFlowMappingKey(false));
/* 932 */           return ParserImpl.this.parseFlowNode();
/*     */         } 
/* 934 */         ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(false);
/* 935 */         return ParserImpl.this.processEmptyScalar(token1.getEndMark());
/*     */       } 
/*     */       
/* 938 */       ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(false);
/* 939 */       Token token = ParserImpl.this.scanner.peekToken();
/* 940 */       return ParserImpl.this.processEmptyScalar(token.getStartMark());
/*     */     }
/*     */   }
/*     */   
/*     */   private class ParseFlowMappingEmptyValue implements Production {
/*     */     private ParseFlowMappingEmptyValue() {}
/*     */     
/*     */     public Event produce() {
/* 948 */       ParserImpl.this.state = new ParserImpl.ParseFlowMappingKey(false);
/* 949 */       return ParserImpl.this.processEmptyScalar(ParserImpl.this.scanner.peekToken().getStartMark());
/*     */     }
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
/*     */   private Event processEmptyScalar(Mark mark) {
/* 962 */     return (Event)new ScalarEvent(null, null, new ImplicitTuple(true, false), "", mark, mark, DumperOptions.ScalarStyle.PLAIN);
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\org\yaml\snakeyaml\parser\ParserImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */