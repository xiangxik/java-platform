# 静态模块化java开发平台
基于servlet3.1的模块化规范，采用spring, spring mvc, jpa, spring data, shiro, fastjson, beetl等开源技术，搭建了一套可模块化扩展的java框架，并可选择地使用extjs6、easyui、bootstrap实现了后台管理的公共功能。

### 特点
* 基于目前最新的一些java技术
* 采用Java8的语法。
* 主要模块化的思想是来自于servlet3.1规范的web集成机制
* 可扩展模块，通过引入jar包来决定是否采用该功能
* 采用spring javaconfig.达到0spring配置文件
* 包含通用的后台管理功能

### 工程用途说明
* application-centralize: 平台包，项目引入该包即可包含了主要功能，依赖于全部module包。
* module-base: 构成平台的基础包。
* module-domain: 构成平台的领域数据操作包，依赖于module-base。
* module-web: 构成平台的web功能包，依赖于module-domain。
* module-web-bootstrap: 构成平台的bootstrap包，依赖于module-web。
* module-web-easyui: 构成平台的easyui包，依赖于module-web。
* module-web-extjs: 构成平台的extjs包，依赖于module-web。
* module-security: 构成平台的安全包，依赖于module-web。
* extension-cms: 扩展平台包。包含cms的功能。引入该包即拥有cms功能。
* extension-mall: 扩展平台包。包含商城及支付等功能。引入该包即拥有商城功能。

### 代码地址
* https://github.com/xiangxik/java-platform
* https://git.oschina.net/xiangxik/java-platform

### 详细介绍
* http://ken.whenling.com

### 更新日志
####【2016-02-09】V1.0.0.RELEASE
* 初版发布