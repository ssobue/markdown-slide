-- document table
create table document
(
    title varchar not null,
    page  int     not null,
    body  varchar not null,
    primary key(title, page)
);
