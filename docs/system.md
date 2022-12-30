## 资源系统

* 资源包元数据格式
  * 示例
    * ```json
      {
        "version": 1,
        "description": "The brace's resources"
      }
      ```
  * `version`
    * Brace的资源包加载器版本
  * `description`
    * 资源包描述

## 聊天对象

* Guild
* Channel
* User

## 聊天组件

* 基本组件
    * 聊天组件的基本类型，有`forString`方法
* 文本组件
    * 仅接受文本输入的组件
* 翻译组件
    * 接受翻译键和参数，会查找资源文件中对应的翻译文本，未找到则返回翻译键
* 图片组件
    * 接受代表图片的byte数组
* 语音组件
    * 接受代表语音的byte数组
* 综合组件
    * 支持文本和翻译组件拼接

## 插件系统
* 插件元数据格式
  * 示例
    * ```json
        {
          "id": "example",
          "name": "Example Plugin",
          "version": "1.0.0",
          "description": "A Example Plugin for ChatBotAPI",
          "main_class": "dev.dubhe.cbapi.example.Example",
          "author": [
            "Gugle"
          ],
          "website": "https://github.com/Dubhe-Studio/ChatBotApi-Example-Plugin",
          "issue": "https://github.com/Dubhe-Studio/ChatBotApi-Example-Plugin/issues",
          "source": "https://github.com/Dubhe-Studio/ChatBotApi-Example-Plugin",
          "depends": {
            "brace": ">=1.0.0"
          }
        }
      ```
  * `id`
      * (必填) 插件的唯一标识符，Brace对标识符相同的插件仅会加载一份
  * `name`
      * (必填) 插件名称，在不久的将来会显示在前端页面上
  * `version`
      * (必填) 插件版本
  * `author`
      * (必填) 插件作者列表
  * `description`
      * 插件描述，在不久的将来会显示在前端页面上
  * `main_calss`
      * (必填) 插件主类
  * `website`
      * 插件网站
  * `issue`
      * 插件反馈地址
  * `source`
      * 插件开源地址
  * `depends`
      * 依赖列表，`key`对应依赖的`id`，`value`对应的是接受的依赖版本范围

## 事件系统

## 命令系统
