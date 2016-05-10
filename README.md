# Static modular java development platform
Based on servlet3.1 module standardization, use spring MVC, JPA, spring data, querydsl, shiro, fastjson, Beetl, infinispan, redis etc. open source technology, build the modular extension of Java framework, and can choose to use extjs6, easyUI, bootstrap with the public function of the backstage management.

# 静态模块化java开发平台
基于servlet3.1的模块化规范，采用spring, spring mvc, jpa, spring data, querydsl, shiro, fastjson, beetl, infinispan, redis等开源技术，搭建了一套可模块化扩展的java框架，并可选择地使用extjs6、easyui、bootstrap实现了后台管理的公共功能。

# Overview（概览）
![Alt Overview（概览）](http://ken.whenling.com/img/javaplatform/overview.jpg)

## Feature
* based on the latest Java technology
* use Java8 syntax
* Custom modular, custom configuration
* major modular ideas are derived from the web specification of the servlet3.1 integration mechanism
* extensible module, by introducing the jar package to decide whether to use the function
* use javaconfig. no spring configuration file
* use querydsl general query
* contains general background management functions

### 特点
* 基于目前最新的一些java技术
* 采用Java8的语法
* 自定义模块化，自定义配置
* 主要模块化的思想是来自于servlet3.1规范的web集成机制
* 可扩展模块，通过引入jar包来决定是否采用该功能
* 采用spring javaconfig.达到0spring配置文件
* 采用querydsl通用查询
* 包含通用的后台管理功能

## Project Description
* application: platform package, the project can be introduced into the package can contain the main function, depends on all the key module package.
* module-base: constitute the basis of the package package.
* module-domain: platform in the field of data manipulation package, depends on the module-base.
* module-cache: constitute the platform cache function package, depends on the module-domain.
* mongodb module-mongodb: function package.
* netty module-netty: function package.
* redis module-redis: function package.
* web constitute the platform of the module-web: function package, dependent on the module-domain.
* bootstrap constitute the platform of the module-web-bootstrap: package, depends on the module-web.
* easyUI constitute the platform of the module-web-easyui: package, depends on the module-web.
* extjs constitute the platform of the module-web-extjs: package, depends on the module-web.
* module-security: constitute the platform security package, depends on the module-web.
* extension-cms: extend the platform package. Contains the function of CMS. The package is the CMS function.
* extension-mall: expansion platform package, including the mall and the payment and other functions. The introduction of the package that has the function of the mall.
* extension-wechat: expansion platform package. WeChat website contains the display and WeChat related functions.
* plugin-base: plug-in package.
* plugin-payment: payment package.

### 工程用途说明
* application: 平台包，项目引入该包即可包含了主要功能，依赖于全部关键module包。
* module-base: 构成平台的基础包。
* module-domain: 构成平台的领域数据操作包，依赖于module-base。
* module-cache: 构成平台的缓存功能包，依赖于module-domain。
* module-mongodb: mongodb功能包。
* module-netty: netty功能包。
* module-redis: redis功能包。
* module-web: 构成平台的web功能包，依赖于module-domain。
* module-web-bootstrap: 构成平台的bootstrap包，依赖于module-web。
* module-web-easyui: 构成平台的easyui包，依赖于module-web。
* module-web-extjs: 构成平台的extjs包，依赖于module-web。
* module-security: 构成平台的安全包，依赖于module-web。
* extension-cms: 扩展平台包。包含cms的功能。引入该包即拥有cms功能。
* extension-mall: 扩展平台包。包含商城及支付等功能。引入该包即拥有商城功能。
* extension-wechat: 扩展平台包。包含微信网站的展示和微信相关功能。
* plugin-base: 插件基础包。
* plugin-payment: 支付插件包。

## Unfinished project
* module-netty
* module-mongodb

### 未完善工程
* module-netty
* module-mongodb

## Detailed introduction
* http://ken.whenling.com

### 详细介绍
* http://ken.whenling.com

## Update log

##### [2016-04-26]
* add plugins

##### [2016-03-19]
* WeChat pack
* modify extjs processing mode

##### [2016-02-09] V1.0.0.RELEASE
* the first edition released

### 更新日志

#####【2016-04-26】
* 增加插件方式

#####【2016-03-19】
* 增加微信包
* 修改extjs的处理方式

#####【2016-02-09】V1.0.0.RELEASE
* 初版发布