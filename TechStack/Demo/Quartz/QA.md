# QA

### 1. 什么是Quartz？
Quartz是一个开源的作业调度库，它可以集成到任何Java应用程序中。Quartz允许开发者编排作业（job），即在应用程序中定时执行的任务。它非常灵活，支持多种类型的作业调度。

### 2. Quartz的主要组件是什么？

- **Job**：任务本身，即要执行的具体工作。
- **JobDetail**：定义Job的实例。
- **Trigger**：定义触发Job执行的时间规则。每个Job可以有多个Trigger。
- **Scheduler**：调度器，用于注册JobDetail和Trigger。当Trigger触发时，Scheduler就会执行Job。

### 3. 如何在Quartz中实现状态持久化？
通过使用JDBC作业存储（JobStore），Quartz可以把作业（Job）和触发器（Trigger）的信息保存在数据库中。这样即使应用程序重启，这些信息也不会丢失，调度器可以恢复运行。

### 4. 简述Stateful和Stateless Job的区别？

- **Stateless Job**：每次执行都会创建Job的一个新实例，不保留状态。
- **Stateful Job**：在Quartz中使用`@PersistJobDataAfterExecution` 注解来标记，表示在作业执行之间保留状态信息。

### 5. 什么是Cron Trigger？
Cron Trigger用于基于日历的作业调度。与Simple Trigger相比，Cron Trigger更强大，可以表达如“每个星期五中午”或“每个工作日9:30”等复杂的调度计划。

### 6. 如何处理错过的触发器？
在Quartz中，如果一个触发器错过了触发时间（例如，因为系统停机），可以通过配置misfire策略来处理。Quartz提供了多种misfire策略，例如立即执行、忽略、重设后续重复计数等。

### 7. 如何在Quartz中实现集群？
Quartz可以配置为在多个节点上运行，形成一个集群。这是通过使用相同的数据库作为JobStore来实现的，所有节点都使用这个共享数据库来存储调度信息，从而实现高可用性和负载均衡。
