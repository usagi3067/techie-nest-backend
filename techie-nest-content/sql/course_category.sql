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
VALUES ('1-1', '编程语言', '1', 1, 1, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-1', 'C语言', '1-1', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-2', 'Java', '1-1', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-3', 'Python', '1-1', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-4', 'Go语言', '1-1', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-1-5', 'Rust', '1-1', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2', '云计算与大数据', '1', 1, 2, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-1', 'Hadoop', '1-2', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-2', 'Spark', '1-2', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-3', 'AWS云服务', '1-2', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-4', 'Docker', '1-2', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-2-5', 'Kubernetes', '1-2', 1, 5, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3', '人工智能', '1', 1, 3, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-1', '机器学习', '1-3', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-2', '深度学习', '1-3', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-3', '计算机视觉', '1-3', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-3-4', '自然语言处理', '1-3', 1, 4, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4', '软件工程', '1', 1, 4, 0);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-1', '敏捷开发', '1-4', 1, 1, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-2', 'DevOps实践', '1-4', 1, 2, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-3', '软件测试基础', '1-4', 1, 3, 1);
INSERT INTO tn_content.course_category (id, name, parent_id, is_show, order_by, is_leaf)
VALUES ('1-4-4', '项目管理', '1-4', 1, 4, 1);


select
    one.id            one_id,
    one.name          one_name,
    one.parent_id      one_parentid,
    one.order_by       one_orderby,
    two.id            two_id,
    two.name          two_name,
    two.parent_id      two_parentid,
    two.order_by       two_orderby
from course_category one
         inner join course_category two on one.id = two.parent_id
where one.parent_id = 1
  and one.is_show = 1
  and two.is_show = 1
order by one.order_by,
         two.order_by;


# 对于处理具有未知深度的树状数据结构查询， 可以使用递归公用表表达式（CTE）来遍历课程分类的层次结构。查询语句如下：
WITH RECURSIVE r AS (
    SELECT * FROM course_category p WHERE id = '1'
    UNION ALL
    SELECT t.* FROM course_category t INNER JOIN r ON r.id = t.parent_id
)
SELECT * FROM r ORDER BY r.id, r.order_by;