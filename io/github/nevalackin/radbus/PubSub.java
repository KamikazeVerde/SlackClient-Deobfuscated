/*    */ package io.github.nevalackin.radbus;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface PubSub<Event>
/*    */ {
/*    */   static <Event> PubSub<Event> newInstance(Consumer<String> errorLogger) {
/* 12 */     return new PubSubImpl<>(errorLogger);
/*    */   }
/*    */   
/*    */   void subscribe(Object paramObject);
/*    */   
/*    */   void unsubscribe(Object paramObject);
/*    */   
/*    */   <T extends Event> void subscribe(Class<T> paramClass, Listener<T> paramListener);
/*    */   
/*    */   void publish(Event paramEvent);
/*    */   
/*    */   void clear();
/*    */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\io\github\nevalackin\radbus\PubSub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */