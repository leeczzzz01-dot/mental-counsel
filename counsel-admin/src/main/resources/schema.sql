-- RBAC schema (MySQL 8+) - 无物理外键版本

CREATE DATABASE IF NOT EXISTS mental_counsel DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE mental_counsel;

CREATE TABLE IF NOT EXISTS roles (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  name VARCHAR(64) NOT NULL COMMENT '角色名称',
  code VARCHAR(64) NOT NULL UNIQUE COMMENT '角色编码',
  description VARCHAR(255) COMMENT '角色描述信息',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

CREATE TABLE IF NOT EXISTS users (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  username VARCHAR(64) NOT NULL UNIQUE COMMENT '登录账号',
  password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希值',
  name VARCHAR(64) COMMENT '用户真实姓名或昵称',
  phone VARCHAR(32) COMMENT '联系电话',
  status ENUM('active','disabled') DEFAULT 'active' COMMENT '账号状态',
  openid VARCHAR(128) UNIQUE COMMENT '微信OpenID',
  user_type VARCHAR(20) DEFAULT 'client' COMMENT '用户类型: admin-管理端, client-用户端',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';

CREATE TABLE IF NOT EXISTS menus (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  name VARCHAR(64) NOT NULL COMMENT '菜单名称',
  path VARCHAR(255) NOT NULL COMMENT '前端路由路径',
  icon VARCHAR(64) COMMENT '菜单图标',
  sort INT DEFAULT 0 COMMENT '排序权重',
  parent_id BIGINT NULL COMMENT '父级菜单ID',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

CREATE TABLE IF NOT EXISTS perms (
  id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
  code VARCHAR(128) NOT NULL UNIQUE COMMENT '权限标识码',
  name VARCHAR(128) NOT NULL COMMENT '权限名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='细粒度权限表';

CREATE TABLE IF NOT EXISTS role_menu (
  role_id BIGINT NOT NULL COMMENT '角色ID',
  menu_id BIGINT NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (role_id, menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-菜单关联表';

CREATE TABLE IF NOT EXISTS role_perm (
  role_id BIGINT NOT NULL COMMENT '角色ID',
  perm_id BIGINT NOT NULL COMMENT '权限ID',
  PRIMARY KEY (role_id, perm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色-权限关联表';

CREATE TABLE IF NOT EXISTS user_role (
  user_id BIGINT NOT NULL COMMENT '用户ID',
  role_id BIGINT NOT NULL COMMENT '角色ID',
  PRIMARY KEY (user_id, role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户-角色关联表';

-- 初始数据
INSERT IGNORE INTO roles (name, code, description) VALUES
('系统管理员', 'ADMIN', '全权限'),
('咨询用户', 'CLIENT', '普通微信用户权限');

INSERT IGNORE INTO menus (name, path, icon, sort, parent_id) VALUES
('系统管理', '/system', 'settings', 1, NULL),
('用户管理', '/system/users', 'user', 1, 1),
('角色管理', '/system/roles', 'shield', 2, 1),
('菜单管理', '/system/menus', 'menu', 3, 1);

-- 插入 admin 用户 (密码: 123456 的 BCrypt 哈希)
REPLACE INTO users (id, username, password_hash, name, phone, status) VALUES
(1, 'admin', '$2a$10$wjiNBNfIc7c5sv2aT1hUMudz7sa2YdZn1vLYVh5P.ChUqrBEVtQQC', '管理员', '13800000000', 'active');

-- 绑定 admin 用户到管理员角色
INSERT IGNORE INTO user_role (user_id, role_id) VALUES (1, 1);

-- 绑定管理员角色到所有菜单
INSERT IGNORE INTO role_menu (role_id, menu_id) VALUES (1, 1), (1, 2), (1, 3), (1, 4);

-- 初始权限点
INSERT IGNORE INTO perms (code, name) VALUES
('user:list', '用户列表'),
('user:create', '新增用户'),
('user:update', '编辑用户'),
('user:delete', '删除用户'),
('role:list', '角色列表'),
('role:create', '新增角色'),
('role:update', '编辑角色'),
('role:delete', '删除角色'),
('menu:list', '菜单列表'),
('menu:create', '新增菜单'),
('menu:update', '编辑菜单'),
('menu:delete', '删除菜单');

-- 绑定管理员角色到所有权限
INSERT IGNORE INTO role_perm (role_id, perm_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12);
