package dev.dubhe.brace.events;

public @interface SubscribeEvent {
    /**
     * 获取事件优先级
     * @return 事件优先级
     */
    EventPriority priority() default EventPriority.NORMAL;
}
