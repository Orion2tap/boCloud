# GPT Prompts 介绍 (PC)

## 界面

- **会话常驻**
  - 保留一至两个公共会话
  - 按照任务类型和上下文保留一至多个会话
  - GPT4 中按常用模式保留一至多个会话

## 通用规则

1. **简单开始**
   - 从简单的提示开始, 然后在其基础上构建
2. **行动召唤**
   - 用动词如 "Write", "Create" 或 "Summarize" 开始提示, 而不是 "Can you", 中文同理
3. **添加上下文**
   - 为你想要执行的任务添加特定和相关的上下文
4. **添加期望**
   - 为内容添加清晰和直接的期望，比如多长时间等
5. **添加示例**
   - 为模型提供示例, 以指导它遵循某种风格、格式等
6. **清晰和具体性**
   - 避免宽泛或模糊的主题, 不要留下解释的空间, 以避免误解或不完整的答案

## Prompts

使用提示（prompts）是在使用 GPT3.5 或 GPT4 等语言模型时引导模型生成期望输出的一种方法
  
### 基础示例

1. **举例说明**

    ```bash
    解释 xxx 的用法，并给出一个例子。
    ```

2. **格式化输出**

    ```bash
    列出三种水果和它们的颜色，格式如下:
    1. 苹果: 红色
    2. 香蕉: 黄色
    3. 葡萄: 紫色
    ```

### Awesome-ChatGPT-Prompts (提示模板)

#### 项目地址

https://github.com/f/awesome-chatgpt-prompts

#### 导入步骤

- ChatGPT 客户端 -> Preferences -> Control Center
  - User Custom 用户自定义提示
  - Sync Prompts 从官方 URL 同步
  - Sync Custom 从指定 URL 或本地导入
    - 下载地址 https://github.com/f/awesome-chatgpt-prompts/blob/main/prompts.csv

### Prompt Perfect

GPT4 的提示优化插件, 每月少量免费配额

#### 使用方式

- perfect + space + 文本

#### 配合 Prompts

- /p + space + 文本
  