/*     */ package io.github.nevalackin.radbus;
/*     */ 
/*     */ import java.lang.invoke.CallSite;
/*     */ import java.lang.invoke.LambdaMetafactory;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.invoke.MethodType;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ final class PubSubImpl<Event>
/*     */   implements PubSub<Event> {
/*  18 */   private static final Object GLOBAL_LISTENERS_BACKING_OBJECT = new Object();
/*  19 */   private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
/*     */   
/*  21 */   private static final MethodType LISTENER_INVOKE_TYPE = MethodType.methodType(void.class, Object.class);
/*  22 */   private final Map<Class<?>, List<Listener<Event>>> eventTypeListenerCache = new HashMap<>();
/*  23 */   private final Map<Object, List<TypedListener>> subscriberMap = new HashMap<>();
/*     */   private final Consumer<String> logger;
/*     */   
/*     */   PubSubImpl(Consumer<String> logger) {
/*  27 */     this.logger = logger;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Event> void subscribe(Class<T> event, Listener<T> listener) {
/*  37 */     ((List<Listener<T>>)this.eventTypeListenerCache.computeIfAbsent(event, key -> new ArrayList())).add(listener);
/*     */     
/*  39 */     ((List<TypedListener>)this.subscriberMap.computeIfAbsent(GLOBAL_LISTENERS_BACKING_OBJECT, key -> new ArrayList()))
/*  40 */       .add(new TypedListener(event, listener));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void subscribe(Object subscriber) {
/*  47 */     MethodType factoryType = MethodType.methodType(Listener.class, subscriber.getClass()); byte b; int i;
/*     */     Method[] arrayOfMethod;
/*  49 */     for (i = (arrayOfMethod = subscriber.getClass().getDeclaredMethods()).length, b = 0; b < i; ) { Method method = arrayOfMethod[b];
/*     */       
/*  51 */       if (method.isAnnotationPresent((Class)Listen.class)) {
/*  52 */         int mm = method.getModifiers();
/*     */         
/*  54 */         if (!Modifier.isPublic(mm)) {
/*  55 */           error(String.format("ERROR: %s#%s: Methods marked with `@Listen` must be public.", new Object[] {
/*  56 */                   method.getDeclaringClass().getSimpleName(), method.getName()
/*     */                 }));
/*     */         
/*     */         }
/*  60 */         else if (Modifier.isStatic(mm)) {
/*  61 */           error(String.format("ERROR: %s#%s: Methods marked with `@Listen` must be non-static.", new Object[] {
/*  62 */                   method.getDeclaringClass().getSimpleName(), method.getName()
/*     */                 }));
/*     */         
/*     */         }
/*  66 */         else if (Modifier.isAbstract(mm)) {
/*  67 */           error(String.format("ERROR: %s#%s: Methods marked with `@Listen` must be non-abstract.", new Object[] {
/*  68 */                   method.getDeclaringClass().getSimpleName(), method.getName()
/*     */                 }));
/*     */         
/*     */         }
/*  72 */         else if (Modifier.isNative(mm)) {
/*  73 */           error(String.format("ERROR: %s#%s: Methods marked with `@Listen` must be non-native.", new Object[] {
/*  74 */                   method.getDeclaringClass().getSimpleName(), method.getName()
/*     */                 }));
/*     */         
/*     */         }
/*  78 */         else if (method.getReturnType() != void.class) {
/*  79 */           error(String.format("ERROR: %s#%s: Methods marked with `@Listen` must return `void`.", new Object[] {
/*  80 */                   method.getDeclaringClass().getSimpleName(), method.getName()
/*     */                 }));
/*     */         }
/*     */         else {
/*     */           
/*  85 */           Class[] paramTypes = method.getParameterTypes();
/*  86 */           if (paramTypes.length != 1) {
/*  87 */             error(String.format("ERROR: %s#%s: methods marked with `@Listen` must have only a single parameter.", new Object[] {
/*  88 */                     method.getDeclaringClass().getSimpleName(), method.getName()
/*     */                   }));
/*     */           }
/*     */           else {
/*     */             
/*  93 */             Class<?> event = paramTypes[0];
/*     */ 
/*     */             
/*     */             try {
/*  97 */               MethodHandle handle = LOOKUP.unreflect(method);
/*     */               
/*  99 */               CallSite site = LambdaMetafactory.metafactory(LOOKUP, "invoke", 
/* 100 */                   factoryType, 
/* 101 */                   LISTENER_INVOKE_TYPE, 
/* 102 */                   handle, 
/* 103 */                   MethodType.methodType(void.class, event));
/* 104 */               Listener<Event> listener = site.getTarget().invoke(subscriber);
/*     */               
/* 106 */               ((List<Listener<Event>>)this.eventTypeListenerCache.computeIfAbsent(event, key -> new ArrayList())).add(listener);
/*     */               
/* 108 */               ((List<TypedListener>)this.subscriberMap.computeIfAbsent(subscriber, key -> new ArrayList())).add(new TypedListener(event, listener));
/* 109 */             } catch (IllegalAccessException illegalAccessException) {
/*     */             
/* 111 */             } catch (ClassCastException e) {
/* 112 */               error(String.format("ERROR: %s#%s: the parameter of methods marked `@Listen` must be a subclass of the PubSub<Event> `Event` type parameter.", new Object[] {
/* 113 */                       method.getDeclaringClass().getSimpleName(), method.getName() }));
/* 114 */             } catch (Throwable e) {
/*     */               
/* 116 */               error(String.format("ERROR: %s", new Object[] { e.getCause() }));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */   public void unsubscribe(Object subscriber) {
/* 125 */     this.subscriberMap.remove(subscriber);
/*     */     
/* 127 */     this.eventTypeListenerCache.clear();
/*     */     
/* 129 */     this.subscriberMap.values().forEach(typedListeners -> typedListeners.forEach(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void publish(Event event) {
/* 137 */     List<Listener<Event>> listeners = this.eventTypeListenerCache.get(event.getClass());
/*     */     
/* 139 */     if (listeners == null)
/*     */       return; 
/* 141 */     int listenersSize = listeners.size();
/*     */     
/* 143 */     while (listenersSize > 0) {
/* 144 */       ((Listener<Event>)listeners.get(--listenersSize)).invoke(event);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 151 */     this.subscriberMap.clear();
/* 152 */     this.eventTypeListenerCache.clear();
/*     */   }
/*     */   
/*     */   private void error(String msg) {
/* 156 */     this.logger.accept(msg);
/*     */   }
/*     */   
/*     */   private class TypedListener {
/*     */     private final Class<?> type;
/*     */     private final Listener<Event> listener;
/*     */     
/*     */     public TypedListener(Class<?> type, Listener<Event> listener) {
/* 164 */       this.type = type;
/* 165 */       this.listener = listener;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 170 */       return String.format("TypedListener { type: %s, listener: %s }", new Object[] { this.type.getSimpleName(), this.listener });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\andre\Downloads\Slack.jar!\io\github\nevalackin\radbus\PubSubImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */