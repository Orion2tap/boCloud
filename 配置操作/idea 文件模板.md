# idea 文件模板

1. **打开 File and Code Templates**：
   - 在 IntelliJ IDEA 中，选择 `File` > `Settings` (对于 Mac OS 是 `IntelliJ IDEA` > `Preferences`)。
   - 在设置窗口中，选择 `Editor` > `File and Code Templates`。

2. **添加或编辑模板**：
   - 在 "File and Code Templates" 窗口中，选择 `Files` 或 `Includes` 选项卡。
   - 对于 Java 类，您可以选择 `Class` 模板进行编辑。
   - 在编辑区域，将版权声明添加到模板的最顶部，如下所示：

     ```java
     /**
      * Copyright (C) 2018-2023
      * All rights reserved, Designed By www.flma.tech
      * 注意：
      * 本软件为www.flma.tech开发研制，未经购买不得使用
      * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
      * 一经发现盗用、分享等行为，将追究法律责任，后果自负
      */
     #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
     ```

3. **设置作者信息模板**：
   - 转到 `Includes` 选项卡。
   - 选择 `File Header` 模板进行编辑。
   - 添加您希望包含的作者信息，如下所示：

     ```java
     /**
      * @author flma
      * @date ${DATE}
      */
     ```

4. **保存更改**：
   - 点击 `Apply` 和 `OK` 保存您的更改。

这样设置之后，当您在任何项目中创建新文件时，这些模板将被应用。请注意，这种方法将全局应用于您的所有 IntelliJ IDEA 项目，而不仅仅是当前项目。目前 IntelliJ IDEA 不支持基于特定项目的文件头模板。如果您需要为不同的项目设置不同的文件头，您可能需要在每次切换项目时手动更改这些模板设置
