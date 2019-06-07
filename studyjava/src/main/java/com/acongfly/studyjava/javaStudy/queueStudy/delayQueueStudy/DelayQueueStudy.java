package com.acongfly.studyjava.javaStudy.queueStudy.delayQueueStudy;

import java.util.concurrent.DelayQueue;

/**
 * @author shicongyang
 * @Description: DelayQueue带有延迟时向的 Queue,其中的元素只有当其指定的延迟时间到了，才能够从队列中获取到该元素，
 * DelayQueue中的元素必须实现Delayed接口， DelayQueue是一个没有大小限制的队列，应用场景很多，
 * 比如对缓存超时的数据进行移除、任务超时的处理、空闲连接的关闭等等。
 * @date 2018/6/6 10:15
 */
public class DelayQueueStudy {
    /**
     * DelayQueue<E extends Delayed>，一个无界阻塞队列，只有在延迟期满时才能从中提取元素。
     * 该队列的头部是延迟期满后保存时间最长的Delayed元素。如果延迟都还没有期满，则队列没有头部，并且 poll 将返回 null。
     * 当一个元素的 getDelay(TimeUnit.NANOSECONDS) 方法返回一个小于等于 0 的值时，将发生到期。
     * 即使无法使用 take 或 poll 移除未到期的元素，也不会将这些元素作为正常元素对待。
     * 例如，size 方法同时返回到期和未到期元素的计数。此队列不允许使用 null 元素。
     */

    public static void main(String[] args) {
        DelayQueue<Message> messages = new DelayQueue<>();
        Message message = new Message(1, "hello", 3000);
        Message message2 = new Message(1, "hello1", 4000);
        Message message3 = new Message(1, "hello2", 5000);

        messages.offer(message);
        messages.offer(message2);
        messages.offer(message3);
        new Thread(new Consumer(messages)).start();


    }
}
