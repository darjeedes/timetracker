drop table public.BaseData if exists
drop table BaseData_Context if exists
drop table Context if exists
drop table Context_Issue if exists
drop table Issue if exists
drop table Issue_TimeEntry if exists
drop table TimeEntry if exists
drop sequence if exists hibernate_sequence
create table public.BaseData (id integer not null, issueManagementSystemBaseUrl varchar(255), primary key (id))
create sequence hibernate_sequence start with 1 increment by 1
create table BaseData_Context (BaseData_id integer not null, contexts_id integer not null)
create table Context (id integer not null, name varchar(255), tag varchar(255), primary key (id))
create table Context_Issue (Context_id integer not null, issues_id integer not null)
create table Issue (id integer not null, notes varchar(255), number integer not null, title varchar(255), primary key (id))
create table Issue_TimeEntry (Issue_id integer not null, timeEntries_id integer not null)
create table TimeEntry (id integer not null, endTime timestamp, startTime timestamp, primary key (id))
alter table BaseData_Context add constraint UK_fdpdgfo7xv5a66oob2x9nadq1 unique (contexts_id)
alter table Context_Issue add constraint UK_aw8b006lpg566bgo9x6dbioja unique (issues_id)
alter table Issue_TimeEntry add constraint UK_ovh2hxhdm6jku3l818051ga unique (timeEntries_id)
alter table BaseData_Context add constraint FKeecxwnq4e73xbwywty0e3hd96 foreign key (contexts_id) references Context
alter table BaseData_Context add constraint FK5emv5f57ov7se7qhoin7yqtrw foreign key (BaseData_id) references public.BaseData
alter table Context_Issue add constraint FKj9rs09uoxktfkleiuj75i9vdy foreign key (issues_id) references Issue
alter table Context_Issue add constraint FKne9ew5dqb8os42g7lh0fsd2av foreign key (Context_id) references Context
alter table Issue_TimeEntry add constraint FK59b0sauwa5uoy4x8we8e003o1 foreign key (timeEntries_id) references TimeEntry
alter table Issue_TimeEntry add constraint FKauiv0dlgncao05pgidxvi1i1j foreign key (Issue_id) references Issue
drop table public.BaseData if exists
drop table BaseData_Context if exists
drop table Context if exists
drop table Context_Issue if exists
drop table Issue if exists
drop table Issue_TimeEntry if exists
drop table TimeEntry if exists
drop sequence if exists hibernate_sequence
create table public.BaseData (id integer not null, issueManagementSystemBaseUrl varchar(255), primary key (id))
create sequence hibernate_sequence start with 1 increment by 1
create table BaseData_Context (BaseData_id integer not null, contexts_id integer not null)
create table Context (id integer not null, name varchar(255), tag varchar(255), primary key (id))
create table Context_Issue (Context_id integer not null, issues_id integer not null)
create table Issue (id integer not null, notes varchar(255), number integer not null, title varchar(255), primary key (id))
create table Issue_TimeEntry (Issue_id integer not null, timeEntries_id integer not null)
create table TimeEntry (id integer not null, endTime timestamp, startTime timestamp, primary key (id))
alter table BaseData_Context add constraint UK_fdpdgfo7xv5a66oob2x9nadq1 unique (contexts_id)
alter table Context_Issue add constraint UK_aw8b006lpg566bgo9x6dbioja unique (issues_id)
alter table Issue_TimeEntry add constraint UK_ovh2hxhdm6jku3l818051ga unique (timeEntries_id)
alter table BaseData_Context add constraint FKeecxwnq4e73xbwywty0e3hd96 foreign key (contexts_id) references Context
alter table BaseData_Context add constraint FK5emv5f57ov7se7qhoin7yqtrw foreign key (BaseData_id) references public.BaseData
alter table Context_Issue add constraint FKj9rs09uoxktfkleiuj75i9vdy foreign key (issues_id) references Issue
alter table Context_Issue add constraint FKne9ew5dqb8os42g7lh0fsd2av foreign key (Context_id) references Context
alter table Issue_TimeEntry add constraint FK59b0sauwa5uoy4x8we8e003o1 foreign key (timeEntries_id) references TimeEntry
alter table Issue_TimeEntry add constraint FKauiv0dlgncao05pgidxvi1i1j foreign key (Issue_id) references Issue
