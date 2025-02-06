create table member (
                        created_at datetime(6),
                        id bigint not null,
                        modified_at datetime(6),
                        email varchar(255) not null,
                        name varchar(255) not null,
                        primary key (id)
);

create table todo (
                      created_at datetime(6),
                      id bigint not null auto_increment,
                      member_id bigint,
                      modified_at datetime(6),
                      content longtext,
                      title varchar(255) not null,
                      primary key (id)
);

alter table todo
    add constraint FK_member_id
        foreign key (member_id)
            references member (id)