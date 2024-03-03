drop table if exists `course_category`;
CREATE TABLE `course_category`
(
    `id`        varchar(20) NOT NULL COMMENT '主键',
    `name`      varchar(32) NOT NULL COMMENT '分类名称',
    `parent_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '父结点id（第一级的父节点是0，自关联字段id）',
    `is_show`   tinyint              DEFAULT NULL COMMENT '是否显示',
    `order_by`  int                  DEFAULT NULL COMMENT '排序字段',
    `is_leaf`   tinyint              DEFAULT NULL COMMENT '是否叶子',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT ='课程分类';


INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1', '根结点', '0', 1, 1, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1', '前端开发', '1', 1, 1, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-1', 'HTML/CSS', '1-1', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-10', '其它', '1-1', 1, 10, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-2', 'JavaScript', '1-1', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-3', 'jQuery', '1-1', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-4', 'ExtJS', '1-1', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-5', 'AngularJS', '1-1', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-6', 'ReactJS', '1-1', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-7', 'Bootstrap', '1-1', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-8', 'Node.js', '1-1', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-9', 'Vue', '1-1', 1, 9, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-10', '研发管理', '1', 1, 10, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-10-1', '敏捷开发', '1-10', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-10-2', '软件设计', '1-10', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-10-3', '软件测试', '1-10', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-10-4', '研发管理', '1-10', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-10-5', '其它', '1-10', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11', '系统运维', '1', 1, 11, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-1', 'Linux', '1-11', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-10', '其它', '1-11', 1, 10, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-2', 'Windows', '1-11', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-3', 'UNIX', '1-11', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-4', 'Mac OS', '1-11', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-5', '网络技术', '1-11', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-6', '路由协议', '1-11', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-7', '无线网络', '1-11', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-8', 'Ngnix', '1-11', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-11-9', '邮件服务器', '1-11', 1, 9, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-12', '产品经理', '1', 1, 12, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-12-1', '交互设计', '1-12', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-12-2', '产品设计', '1-12', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-12-3', '原型设计', '1-12', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-12-4', '用户体验', '1-12', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-12-5', '需求分析', '1-12', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-12-6', '其它', '1-12', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13', '企业/办公/职场', '1', 1, 13, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-1', '运营管理', '1-13', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-2', '企业信息化', '1-13', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-3', '网络营销', '1-13', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-4', 'Office/WPS', '1-13', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-5', '招聘/面试', '1-13', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-6', '电子商务', '1-13', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-7', 'CRM', '1-13', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-8', 'ERP', '1-13', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-13-9', '其它', '1-13', 1, 9, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14', '信息安全', '1', 1, 14, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-1', '密码学/加密/破解', '1-14', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-10', '其它', '1-14', 1, 10, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-2', '渗透测试', '1-14', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-3', '社会工程', '1-14', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-4', '漏洞挖掘与利用', '1-14', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-5', '云安全', '1-14', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-6', '防护加固', '1-14', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-7', '代码审计', '1-14', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-8', '移动安全', '1-14', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-14-9', '病毒木马', '1-14', 1, 9, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-15', '测试目录', '1', 1, 15, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-15-1', '测试目录01', '1-15', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2', '移动开发', '1', 1, 2, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-1', '微信开发', '1-2', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-2', 'iOS', '1-2', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-3', '手游开发', '1-2', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-4', 'Swift', '1-2', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-5', 'Android', '1-2', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-6', 'ReactNative', '1-2', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-7', 'Cordova', '1-2', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-8', '其它', '1-2', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3', '编程开发', '1', 1, 3, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-1', 'C/C++', '1-3', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-2', 'Java', '1-3', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-3', '.NET', '1-3', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-4', 'Objective-C', '1-3', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-5', 'Go语言', '1-3', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-6', 'Python', '1-3', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-7', 'Ruby/Rails', '1-3', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-8', '其它', '1-3', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4', '数据库', '1', 1, 4, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-1', 'Oracle', '1-4', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-2', 'MySQL', '1-4', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-3', 'SQL Server', '1-4', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-4', 'DB2', '1-4', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-5', 'NoSQL', '1-4', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-6', 'Mongo DB', '1-4', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-7', 'Hbase', '1-4', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-8', '数据仓库', '1-4', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-9', '其它', '1-4', 1, 9, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5', '人工智能', '1', 1, 5, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5-1', '机器学习', '1-5', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5-2', '深度学习', '1-5', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5-3', '语音识别', '1-5', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5-4', '计算机视觉', '1-5', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5-5', 'NLP', '1-5', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5-6', '强化学习', '1-5', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-5-7', '其它', '1-5', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6', '云计算/大数据', '1', 1, 6, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-1', 'Spark', '1-6', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-2', 'Hadoop', '1-6', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-3', 'OpenStack', '1-6', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-4', 'Docker/K8S', '1-6', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-5', '云计算基础架构', '1-6', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-6', '虚拟化技术', '1-6', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-7', '云平台', '1-6', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-8', 'ELK', '1-6', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-6-9', '其它', '1-6', 1, 9, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7', 'UI设计', '1', 1, 7, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-1', 'Photoshop', '1-7', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-10', 'InDesign', '1-7', 1, 10, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-11', 'Pro/Engineer', '1-7', 1, 11, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-12', 'Cinema 4D', '1-7', 1, 12, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-13', '3D Studio', '1-7', 1, 13, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-14', 'After Effects（AE）', '1-7', 1, 14, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-15', '原画设计', '1-7', 1, 15, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-16', '动画制作', '1-7', 1, 16, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-17', 'Dreamweaver', '1-7', 1, 17, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-18', 'Axure', '1-7', 1, 18, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-19', '其它', '1-7', 1, 19, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-2', '3Dmax', '1-7', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-3', 'Illustrator', '1-7', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-4', 'Flash', '1-7', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-5', 'Maya', '1-7', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-6', 'AUTOCAD', '1-7', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-7', 'UG', '1-7', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-8', 'SolidWorks', '1-7', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-7-9', 'CorelDraw', '1-7', 1, 9, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-8', '游戏开发', '1', 1, 8, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-8-1', 'Cocos', '1-8', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-8-2', 'Unity3D', '1-8', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-8-3', 'Flash', '1-8', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-8-4', 'SpriteKit 2D', '1-8', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-8-5', 'Unreal', '1-8', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-8-6', '其它', '1-8', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9', '智能硬件/物联网', '1', 1, 9, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-1', '无线通信', '1-9', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-10', '物联网技术', '1-9', 1, 10, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-11', '其它', '1-9', 1, 11, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-2', '电子工程', '1-9', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-3', 'Arduino', '1-9', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-4', '体感技术', '1-9', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-5', '智能硬件', '1-9', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-6', '驱动/内核开发', '1-9', 1, 6, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-7', '单片机/工控', '1-9', 1, 7, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-8', 'WinCE', '1-9', 1, 8, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-9-9', '嵌入式', '1-9', 1, 9, 1);
