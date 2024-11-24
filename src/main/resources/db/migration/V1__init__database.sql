create table if not exists url_shortener(
    short_url varchar(255) primary key,
    original_url varchar(255) not null,
    creation_date timestamp with time zone not null,
    expiration_date timestamp with time zone not null
)
