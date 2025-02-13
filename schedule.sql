create table member (
                        id bigint not null,
                        email varchar(50) not null,
                        name varchar(20) not null,
                        password varchar(100) not null,
                        created_at datetime(6),
                        modified_at datetime(6),
                        primary key (id)
);

create table todo (
                      id bigint not null auto_increment,
                      member_id bigint not null ,
                      title varchar(30) not null,
                      content longtext not null ,
                      created_at datetime(6) not null,
                      modified_at datetime(6) not null,
                      primary key (id)
);

create table comment (
                         id bigint not null auto_increment,
                         member_id bigint not null ,
                         todo_id bigint not null,
                         content varchar(255) not null,
                         modified_at datetime(6) not null,
                         created_at datetime(6) not null,
                         primary key (id)
);

alter table todo
    add constraint FK_member_id
        foreign key (member_id)
            references member (id);

alter table comment
    add constraint FK_todo_id
        foreign key (todo_id)
            references todo (id);

alter table comment
    add constraint FK_member_id
        foreign key (member_id)
            references member (id)
