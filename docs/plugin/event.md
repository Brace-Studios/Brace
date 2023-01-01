## 事件系统

### 事件列表

#### [**白泽系统事件**](/src/main/java/dev/dubhe/brace/events/BraceEvents.java)

| 事件名称                  | 事件内容   | 事件用途      |
|-----------------------|--------|-----------|
| `ON_COMMAND_REGISTER` | 命令注册事件 | 向白泽系统注册命令 |

#### [**Guild事件**](/src/main/java/dev/dubhe/brace/events/GuildEvents.java)

| 事件名称           | 事件内容        | 事件用途         |
|----------------|-------------|--------------|
| `MEMBER_JOIN`  | 成员加入Guild事件 | 监测新成员加入Guild |
| `MEMBER_LEAVE` | 成员离开Guild事件 | 监测有成员离开Guild |

#### [**消息事件**](/src/main/java/dev/dubhe/brace/events/MessageEvents.java)

| 事件名称          | 事件内容   | 事件用途        |
|---------------|--------|-------------|
| `NEW_MESSAGE` | 接收消息事件 | 处理白泽新接收到的事件 |

### 事件处理

* 使用对应的事件对象的 `listen` 方法向事件注册侦听器（处理器）
* 例程：
    ```java
    public class EventHandler {
        public static void register() {
            MessageEvents.NEW_MESSAGE.listen(EventHandler::newMsg);
        }
    
        public static void newMsg(TextMessage msg) {
            msg.getMsg().getString();
        }
    }
    ```

### 创建事件

* 创建 [`Event`](/src/main/java/dev/dubhe/brace/events/Event.java) 类的对象
* 使用该对象的 `release` 方法发布事件
