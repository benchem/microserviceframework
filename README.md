# microserviceframework
微服务开发框架

## 统一 webapi 返回格式
## 服务调用验权与转发
## 消息队列调用
## DEMO
- 将建立 Bill（订单服务） \ Stock（库存服务） \ Pay（支付服务） 三个服务；
- Bill 可调用 Stock \ Pay 两个服务；
- Pay 只允许 Pay 服务调用；
- Pay 服务通过 消息队列 通知 Bill 更新状态；
- 权限验证与转发实现在 sns 项目上实现