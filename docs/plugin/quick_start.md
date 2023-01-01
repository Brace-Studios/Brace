## 快速入门

### 前置

* Java 17（推荐）的 JDK，可以从 [`MircoSoft OpenJDK`](https://learn.microsoft.com/zh-cn/java/openjdk/download) 下载
* 任意 IDE（集成开发环境）：如 [`IntelliJ IDEA`](https://www.jetbrains.com/idea/)

### 配置环境

* 从 [`Brace-Example-Plugin`](https://github.com/Dubhe-Studio/Brace-Example-Plugin) 下载示例插件模板并自行配置

#### **插件元数据**

* 示例：
    ```json
    {
      "id": "example",
      "name": "Example Plugin",
      "version": "1.0.0",
      "info": "A Example Plugin for ChatBotAPI",
      "main_class": "dev.dubhe.cbapi.example.Example",
      "author": [
        "Gugle"
      ],
      "website": "https://github.com/Dubhe-Studio/ChatBotApi-Example-Plugin",
      "issue": "https://github.com/Dubhe-Studio/ChatBotApi-Example-Plugin/issues",
      "source": "https://github.com/Dubhe-Studio/ChatBotApi-Example-Plugin"
    }
    ```
* 解释
    * `id` **(必填)** 插件的唯一标识符，Brace对标识符相同的插件仅会加载一份
    * `name` **(必填)** 插件名称，在不久的将来会显示在前端页面上
    * `version` **(必填)** 插件版本
    * `author` **(必填)** 插件作者列表
    * `info` 插件介绍，在不久的将来会显示在前端页面上
    * `main_calss` **(必填)** 插件主类
    * `website` 插件网站
    * `issue` 插件反馈地址
    * `source` 插件开源地址
