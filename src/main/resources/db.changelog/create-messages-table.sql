create table messages
(
    id        varchar(255) not null,
    next      varchar(255),
    text      text,
    chat_id   varchar(255),
    user_from varchar(255),
    primary key (id)
);


alter table if exists messages
    add constraint FK64w44ngcpqp99ptcb9werdfmb foreign key (chat_id) references chats;
alter table if exists messages
    add constraint FKjuibkiviyys7w6e8dqx6y3pev foreign key (user_from) references users;