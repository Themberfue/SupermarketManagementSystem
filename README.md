# BlackMarket 开发者文档

## 1. 概述

**项目简介**：
BlackMarket 是一个用于管理客户信息，管理商品/订单信息，同时可以注册和登录客户，提供添加,删除,查找,修改各种数据的功能。

**功能列表**：

- 仓库管理员和收银员的登录
- 商品信息的增删查改
- 订单信息的增删改查
- 普通客户和Plus客户的登录与注册
- 客户信息的增删改查

## 2. 环境设置

**开发环境**：
- 操作系统：Windows 10
- IDE：IntelliJ IDEA 2023.2.4
- JDK：OpenJDK 17
- 构建工具：Maven 3.6.3

**安装指南**：
1. **安装 JDK 和 Maven**：
    - 下载并安装 OpenJDK 17。
    - 下载并安装 Maven 3.6.3。
2. **下载并安装 IntelliJ IDEA**：
    - 前往 [IntelliJ IDEA 官网](https://www.jetbrains.com/idea/)下载并安装。
3. **使用 Maven 导入项目依赖**：
    - 在IDEA设置中导入maven并且配置好。
4. **连接MySQL**
    ```sh
   mysql -u root -p 
5. **启动应用程序**：
    - 打开 IntelliJ IDEA，导入项目。
    - 运行 `login.java` 文件。

## 3. 系统架构

**架构图**：

+---+ +-----------------+ +---------------+ +-------------+ +---+

| JavaFX 前端 | <----> | 服务层 | <----> | 数据库层 |

+---+ -----------------+ +---------------+ +-------------+ +---+


**模块说明**：
- **JavaFX 前端**：负责用户界面的展示和交互。
- **服务层**：处理业务逻辑，包括客户信息管理和商品信息管理，订单信息管理。
- **数据库层**：持久化存储客户信息和其他数据。

## 4. 数据库设计

**数据库表结构**：


### aorder表

| 字段名           | 类型           | 描述      |
|---------------|--------------|---------|
| order_id      | INT          | 主键，订单ID |
| customer_id   | INT          | 客户ID    |
| customer_name | VARCHAR(255) | 客户姓名    |
| order_date    | DATE         | 订单日期    |
| money         | DECIMAL      | 订单金额    |

### category表

| 字段名                  | 类型  | 描述      |
|----------------------|-----|---------|
| category_id          | INT | 主键，类别ID |
| category_name        | INT | 类别名称    |
| category_description | INT | 类别描述    |

### customer 表

| 字段名           | 类型           | 描述      |
|---------------|--------------|---------|
| customer_id   | INT          | 主键，客户ID |
| customer_name | VARCHAR(255) | 客户名称    |
| phone         | VARCHAR(20)  | 电话号码    |
| email         | VARCHAR(255) | 邮箱      |
| address       | VARCHAR(255) | 地址      |
| is_vip        | BOOLEAN      | 是否 VIP  |
| password      | VARCHAR(255) | 用户密码    |

### inventory表
| 字段名          | 类型           | 描述      |
|--------------|--------------|---------|
| inventory_id | INT          | 主键，库存ID |
| product_id   | INT          | 商品ID    |
| inventory_id | INT          | 主键，库存ID |

### product表
| 字段名                 | 类型           | 描述      |
|---------------------|--------------|---------|
| product_id          | INT          | 主键，产品ID |
| product_name        | VARCHAR(255) | 产品名称    |
| product_description | text         | 产品描述    |
| product_location    | VARCHAR(255) | 产品产地    |
| category_id         | INT          | 产品类别    |
| supplier_id         | INT          | 产品供应商ID |
| price               | DECIMAL      | 产品价格    |
| stocks              | INT          | 产品库存    |

### supplier表
| 字段名           | 类型           | 描述       |
|---------------|--------------|----------|
| supplier_id   | INT          | 主键，供应商ID |
| supplier_name | VARCHAR(255) | 供应商名称    |
| country       | VARCHAR(255) | 供应商国家    |
| city          | VARCHAR(255) | 供应商城市    |

### warehouse_staff表
| 字段名        | 类型           | 描述          |
|------------|--------------|-------------|
| staff_id   | INT          | 主键，仓库管理人员ID |
| staff_name | VARCHAR(255) | 仓库管理人员姓名    |
| position   | VARCHAR(255) | 职位          |
| phone      | VARCHAR(255) | 电话          |
| email      | VARCHAR(255) | 邮箱          |
| hire_date  | DATE         | 入职日期        |
| password   | VARCHAR(255) | 密码          |




## 5. 核心类和方法
### AddData类
#### 方法列表
- `addOrder(int order_id,int cus_id,String cus_name
,String order_date,double money)`：添加订单信息
- `addCustomer(int username,String pwd)`：添加普通客户信息，用于注册界面
- `addPlusCustomer(int username,String pwd)`：添加Plus客户信息，用于注册信息

### DeleteData类
#### 方法列表
- `deleteProduct(String productId)`：通过商品ID删除商品
- `deleteOrder(int order_id)`：通过订单ID删除订单

### UpdateData类
#### 方法列表
- `updateProduct(String product_id,String product_name
  ,String product_desc,String product_loca,int catagory_id
  ,int supplier_id,double price,int stocks)`：更新商品信息
- `updateOrder(int order_id,int cus_id,String cus_name ,String order_date,double money)`：更新订单信息
- `updateCustomerInfo(String customerId,String customerName,String address,String email,String phone)`：更改客户信息
- `updateIsVip(int customerId,boolean isVip)`：更改用户Plus状态

### SearchData类
#### 方法列表
- `SearchProductsBySupplier(String supplier, TableView<Product> table)`：通过供应商查找商品
- `SearchProductsByCatagory(String catagory, TableView<Product> table)`：通过类别查找商品
- `SearchProductsById(String productId)`：通过商品ID查找商品
- `SearchCustomerById(int id, TableView<Customer> table)`：通过用户ID查找客户
- `searchOrderId(String orderId)`：通过订单ID查找订单
- `setTableProductDataView(PreparedStatement ppst, TableView<Product> table)`：商品的数据回显
- `setTableCustomerDataView(PreparedStatement ppst, TableView<Customer> table)`：客户的数据回显
- `setTableOrderDataView(PreparedStatement ppst)`：订单的数据回显
- `NumberOfCustoms()`：普通用户的数量
- `NumberOfPlusCustomer()`：Plus用户的数量

### DataToObject类
#### 方法列表
- `dataToProductById(String product_id)`：将数据库的商品信息通过商品ID转换为商品对象
- `dataToProductByName(String product_name)`：将数据库的商品信息通过商品名字转换为商品对象
- `dataToOrderByOrderId(String order_id)`：将数据库的订单信息通过商品名字转换为商品对象
- `dataToOrderByCustomerId(String customer_id)`：将数据库的客户信息通过客户ID转换为客户对象

### MyAlert类
#### 方法列表
- `alertSet(String action,String isFail,String reason)`：自定义警告和信息窗口弹窗

### MysqlCon类
#### 方法列表
- `Connection()`：得到数据库的连接
- `closeCon(Connection conn, PreparedStatement ppst)`：关闭方法中只含有conn和ppst数据库的连接
- `closeCon(Connection conn, PreparedStatement ppst, ResultSet rs)`：关闭方法中含有rs的数据库连接

### Verify类
#### 方法列表
- `verifyManager()`：验证是否是仓库管理员
- `verifyCashier()`：验证是否是收银员
- `verifyPlusCustomer()`：验证是否是Plus用户
- `verifyCustomer()`：验证是否是普通用户

## 6. 常见问题解答

Q1: 如何处理数据库连接问题？

A1: 请检查以下几点：

确保 MySQL 服务已启动并运行。
确保配置文件中的数据库连接信息（URL、用户名、密码）正确。
检查项目依赖中是否包含 MySQL 连接器。
Q2: 如何调试 JavaFX 应用程序？

A2: 可以通过以下方式调试：

使用 IntelliJ IDEA 中的调试功能，在代码中设置断点。
运行程序并在调试模式下进行单步调试。
使用日志记录工具（如 Log4j）记录调试信息。
## 7. 参考资料
   [Java17官方文档](https://docs.oracle.com/en/java/javase/17/)   

   [JavaFX官方文档](https://dev.mysql.com/doc/)
   
   [MySQL官方文档](https://dev.mysql.com/doc/)
## 8. 开发人员
   [@Pxoolcm](https://blog.csdn.net/2303_79441076)

   [@Themberfue](https://blog.csdn.net/Themberfue?type=blog)

   [@zhaojinlukui](https://blog.csdn.net/ZYM2300837217?type=blog)

   © 2024 BlackMarket(不用GPT就不队). All rights reserved.