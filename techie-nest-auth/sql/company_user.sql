create table company_user
(
    id         varchar(32) not null
        primary key,
    company_id varchar(32) not null,
    user_id    varchar(32) not null
)