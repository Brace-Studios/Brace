### 命令注册

* 在插件的主类 `onInitialization()` 方法侦听 `CommandRegisterEvent` 事件，使用该事件传递的 `CommandDispatcher` 注册命令
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
