## 事件系统

### 事件列表

#### [**白泽系统事件**](/src/main/java/dev/dubhe/brace/events/brace)

| 事件名称                   | 事件内容   | 触发条件      |
|------------------------|--------|-----------|
| `CommandRegisterEvent` | 命令注册事件 | 白泽注册命令时触发 |

#### [**Guild事件**](/src/main/java/dev/dubhe/brace/events/guild)

| 事件名称                    | 事件内容        | 触发条件            |
|-------------------------|-------------|-----------------|
| `GuildEvent`            | Guild事件     | 所有与Guild有关的事件   |
| `GuildMemberEvent`      | Guild成员事件   | 所有与Guild成员有关的事件 |
| `GuildMemberJoinEvent`  | 成员加入Guild事件 | 有新成员加入Guild时触发  |
| `GuildMemberLeaveEvent` | 成员离开Guild事件 | 现有成员离开Guild时触发  |

#### [**消息事件**](/src/main/java/dev/dubhe/brace/events/message)

| 事件名称                   | 事件内容    | 触发条件         |
|------------------------|---------|--------------|
| `MessageEvent`         | 消息事件    | 所有与消息相关的事件   |
| `MessageReceivedEvent` | 接收消息事件  | 白泽接收到新的消息时触发 |
| `MessageSendPrevEvent` | 发送消息前事件 | 白泽发送消息前触发    |
| `MessageSendCompEvent` | 发送消息后事件 | 白泽发送消息后触发    |

### 事件处理

* 使用 `BraceServer.getEventManager().listen()` 方法向事件注册侦听器（处理器）
* 例程：
    ```java
  public class Example extends Plugin {
    public void onInitialization() {
        BraceServer.getEventManager().listen(CommandRegisterEvent.class, event -> event.register(ExampleCommand::register));
    }
  }
  ```
  ```java
  public class ExampleCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("example"));
    }
  } 
  ```

### 创建事件

* 创建一个新事件类继承 [`Event`](/src/main/java/dev/dubhe/brace/events/Event.java) 类
* 使用 `BraceServer.getEventManager().release()` 方法发布事件
