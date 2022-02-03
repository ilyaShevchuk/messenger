alter table if exists messages
    drop constraint if exists FK64w44ngcpqp99ptcb9werdfmb;
alter table if exists messages
    drop constraint if exists FKjuibkiviyys7w6e8dqx6y3pev;

drop table if exists messages cascade;
